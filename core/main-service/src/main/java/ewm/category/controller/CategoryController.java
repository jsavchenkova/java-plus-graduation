package ewm.category.controller;

import ewm.dto.category.CategoryDto;
import ewm.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ewm.client.CategoryClient;

import java.util.List;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController implements CategoryClient {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategories(
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return categoryService.getAll(from, size);
    }

    @GetMapping("/{categoryId}")
    public CategoryDto getCategory(@PathVariable Long categoryId) {
        return categoryService.getById(categoryId);
    }
}
