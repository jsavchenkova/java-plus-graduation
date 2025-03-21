package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.category.CategoryDto;
import ru.practicum.ewm.category.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDto categoryToCategoryDto(Category category);
}
