package dev.patika.library_rest_api.api;


import dev.patika.library_rest_api.business.abstracts.IAuthorService;
import dev.patika.library_rest_api.business.abstracts.IBookService;
import dev.patika.library_rest_api.business.abstracts.ICategoryService;
import dev.patika.library_rest_api.business.abstracts.IPublisherService;
import dev.patika.library_rest_api.core.config.modelMapper.IModelMapperService;
import dev.patika.library_rest_api.core.result.Result;
import dev.patika.library_rest_api.core.result.ResultData;
import dev.patika.library_rest_api.core.utilies.ResultHelper;
import dev.patika.library_rest_api.dto.request.book.BookSaveRequest;
import dev.patika.library_rest_api.dto.request.book.BookUpdateRequest;
import dev.patika.library_rest_api.dto.response.CursorResponse;
import dev.patika.library_rest_api.dto.response.book.BookResponse;
import dev.patika.library_rest_api.entities.Author;
import dev.patika.library_rest_api.entities.Book;
import dev.patika.library_rest_api.entities.Category;
import dev.patika.library_rest_api.entities.Publisher;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/books")
public class BookController {

    private final IBookService bookService;
    private final IModelMapperService modelMapper;
    private final IAuthorService authorService;
    private final IPublisherService publisherService;
    private final ICategoryService categoryService;

    public BookController(IBookService bookService, IModelMapperService modelMapper, IAuthorService authorService, IPublisherService publisherService, ICategoryService categoryService) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.authorService = authorService;
        this.publisherService = publisherService;
        this.categoryService = categoryService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<BookResponse> save(@Valid @RequestBody BookSaveRequest bookSaveRequest) {
        Book saveBook = this.modelMapper.forRequest().map(bookSaveRequest, Book.class);

        Author author = this.authorService.get(bookSaveRequest.getAuthorId());
        saveBook.setAuthor(author);

        Publisher publisher = this.publisherService.get(bookSaveRequest.getPublisherId());
        saveBook.setPublisher(publisher);

        List<Category> categories = new ArrayList<>();
        for (int categoryId : bookSaveRequest.getCategoryIds()) {
            Category category = this.categoryService.get(categoryId);
            categories.add(category);
        }
        saveBook.setCategories(categories);

        this.bookService.save(saveBook);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveBook, BookResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<BookResponse> get(@PathVariable("id") int id) {
        Book book = this.bookService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(book, BookResponse.class));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<BookResponse> update(@Valid @RequestBody BookUpdateRequest bookUpdateRequest) {
        Book updateBook = this.modelMapper.forRequest().map(bookUpdateRequest, Book.class);
        this.bookService.update(updateBook);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateBook, BookResponse.class));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<BookResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "2") int pageSize
    ) {
        Page<Book> bookPage = this.bookService.cursor(page, pageSize);
        Page<BookResponse> bookResponsePage = bookPage
                .map(book -> this.modelMapper.forResponse().map(book, BookResponse.class));

        CursorResponse<BookResponse> cursor = new CursorResponse<>();
        cursor.setItems(bookResponsePage.getContent());
        cursor.setPageNumber(bookResponsePage.getNumber());
        cursor.setPageSize(bookResponsePage.getSize());
        cursor.setTotalElements(bookResponsePage.getTotalElements());

        return ResultHelper.success(cursor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        this.bookService.delete(id);
        return ResultHelper.ok();
    }

}
