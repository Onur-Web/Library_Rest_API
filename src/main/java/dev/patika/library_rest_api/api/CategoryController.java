package dev.patika.library_rest_api.api;

import dev.patika.library_rest_api.business.abstracts.IBookService;
import dev.patika.library_rest_api.business.abstracts.ICategoryService;
import dev.patika.library_rest_api.core.config.modelMapper.IModelMapperService;
import dev.patika.library_rest_api.core.result.Result;
import dev.patika.library_rest_api.core.result.ResultData;
import dev.patika.library_rest_api.core.utilies.ResultHelper;
import dev.patika.library_rest_api.dto.request.category.CategorySaveRequest;
import dev.patika.library_rest_api.dto.request.category.CategoryUpdateRequest;
import dev.patika.library_rest_api.dto.response.CursorResponse;
import dev.patika.library_rest_api.dto.response.category.CategoryResponse;
import dev.patika.library_rest_api.entities.Book;
import dev.patika.library_rest_api.entities.Category;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final ICategoryService categoryService;
    private final IModelMapperService modelMapper;
    private final IBookService bookService;

    public CategoryController(
            ICategoryService categoryService,
            IModelMapperService modelMapper,
            IBookService bookService
    ) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.bookService = bookService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<CategoryResponse> save(@Valid @RequestBody CategorySaveRequest categorySaveRequest) {
        Category saveCategory = this.modelMapper.forRequest().map(categorySaveRequest, Category.class);

        Book book = this.bookService.get(categorySaveRequest.getBookId());
        saveCategory.setBook(book);

        this.categoryService.save(saveCategory);
        return ResultHelper.created(this.modelMapper.forResponse().map(saveCategory, CategoryResponse.class));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CategoryResponse> update(@Valid @RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        Category updateCategory = this.modelMapper.forRequest().map(categoryUpdateRequest, Category.class);
        this.categoryService.update(updateCategory);
        return ResultHelper.success(this.modelMapper.forResponse().map(updateCategory, CategoryResponse.class));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CategoryResponse> get(@PathVariable("id") int id) {
        Category category = this.categoryService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(category, CategoryResponse.class));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<CategoryResponse>> cursor(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "2") int pageSize
    ) {
        Page<Category> categoryPage = this.categoryService.cursor(page, pageSize);
        Page<CategoryResponse> categoryResponsePage = categoryPage
                .map(category -> this.modelMapper.forResponse().map(category, CategoryResponse.class));

        CursorResponse<CategoryResponse> cursor = new CursorResponse<>();
        cursor.setItems(categoryResponsePage.getContent());
        cursor.setPageNumber(categoryResponsePage.getNumber());
        cursor.setPageSize(categoryResponsePage.getSize());
        cursor.setTotalElements(categoryResponsePage.getTotalElements());

        return ResultHelper.success(cursor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") int id) {
        this.categoryService.delete(id);
        return ResultHelper.ok();
    }

}
