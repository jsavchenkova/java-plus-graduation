package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.compilation.CompilationDtoResponse;
import ru.practicum.ewm.compilation.model.Compilation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CompilationMapper {
	CompilationMapper INSTANCE = Mappers.getMapper(CompilationMapper.class);

	CompilationDtoResponse compilationToCompilationDtoResponse(Compilation compilation);

	List<CompilationDtoResponse> mapListCompilations(List<Compilation> compilations);
}
