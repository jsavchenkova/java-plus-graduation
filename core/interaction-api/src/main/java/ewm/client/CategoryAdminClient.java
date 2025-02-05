package ewm.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ewm.dto.category.CategoryDto;
import ewm.dto.category.CreateCategoryDto;

@FeignClient(name = "category-admin-client")
public interface CategoryAdminClient {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryDto addCategory(@RequestBody @Valid CreateCategoryDto createCategoryDto) ;

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCategory(@PathVariable Long categoryId);

    @PatchMapping("/{categoryId}")
    CategoryDto updateCategory(@PathVariable Long categoryId, @RequestBody @Valid CreateCategoryDto createCategoryDto);
}
