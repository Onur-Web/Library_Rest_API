package dev.patika.library_rest_api.api;

import dev.patika.library_rest_api.business.abstracts.IBookBorrowingService;
import dev.patika.library_rest_api.business.abstracts.IBookService;
import dev.patika.library_rest_api.core.config.modelMapper.IModelMapperService;
import dev.patika.library_rest_api.core.result.Result;
import dev.patika.library_rest_api.core.result.ResultData;
import dev.patika.library_rest_api.core.utilies.ResultHelper;
import dev.patika.library_rest_api.dto.request.bookBorrowing.BookBorrowingSaveRequest;
import dev.patika.library_rest_api.dto.request.bookBorrowing.BookBorrowingUpdateRequest;
import dev.patika.library_rest_api.dto.response.CursorResponse;
import dev.patika.library_rest_api.dto.response.bookBorrowing.BookBorrowingResponse;
import dev.patika.library_rest_api.entities.Book;
import dev.patika.library_rest_api.entities.BookBorrowing;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/book_borrowings")
public class BookBorrowingController {

    private final IBookBorrowingService bookBorrowingService;
    private final IModelMapperService modelMapper;
    private final IBookService bookService;

    public BookBorrowingController(
            IBookBorrowingService bookBorrowingService,
            IModelMapperService modelMapper,
            IBookService bookService
    ) {
        this.bookBorrowingService = bookBorrowingService;
        this.modelMapper = modelMapper;
        this.bookService = bookService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<BookBorrowingResponse> save(@Valid @RequestBody BookBorrowingSaveRequest bookBorrowingSaveRequest) {
        BookBorrowing saveBookBorrowing = this.modelMapper.forRequest().map(bookBorrowingSaveRequest, BookBorrowing.class);

        Book book = this.bookService.get(bookBorrowingSaveRequest.getBookId());
        saveBookBorrowing.setBook(book);

        this.bookBorrowingService.save(saveBookBorrowing);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveBookBorrowing, BookBorrowingResponse.class));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<BookBorrowingResponse> update(@Valid @RequestBody BookBorrowingUpdateRequest bookBorrowingUpdateRequest) {
        BookBorrowing updateBookBorrowing = this.modelMapper.forRequest().map(bookBorrowingUpdateRequest, BookBorrowing.class);
        this.bookBorrowingService.update(updateBookBorrowing);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateBookBorrowing, BookBorrowingResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<BookBorrowingResponse> get(@PathVariable("id") int id) {
        BookBorrowing bookBorrowing = this.bookBorrowingService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(bookBorrowing, BookBorrowingResponse.class));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<BookBorrowingResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "2") int pageSize
    ) {
        Page<BookBorrowing> bookBorrowingPage = this.bookBorrowingService.cursor(page, pageSize);
        Page<BookBorrowingResponse> bookBorrowingResponsePage = bookBorrowingPage
                .map(bookBorrowing -> this.modelMapper.forResponse().map(bookBorrowing, BookBorrowingResponse.class));

        CursorResponse<BookBorrowingResponse> cursor = new CursorResponse<>();
        cursor.setItems(bookBorrowingResponsePage.getContent());
        cursor.setPageNumber(bookBorrowingResponsePage.getNumber());
        cursor.setPageSize(bookBorrowingResponsePage.getSize());
        cursor.setTotalElements(bookBorrowingResponsePage.getTotalElements());

        return ResultHelper.success(cursor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        this.bookBorrowingService.delete(id);
        return ResultHelper.ok();
    }

}
