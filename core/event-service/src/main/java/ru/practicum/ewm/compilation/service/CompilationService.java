package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.dto.compilation.CompilationDto;
import ru.practicum.ewm.dto.compilation.CompilationDtoResponse;
import ru.practicum.ewm.dto.compilation.CompilationDtoUpdate;

import java.util.List;

public interface CompilationService {
	CompilationDtoResponse createCompilation(CompilationDto compilationDto);

	void deleteCompilation(Long compId);

	CompilationDtoResponse updateCompilation(Long compId, CompilationDtoUpdate compilationDto);

	List<CompilationDtoResponse> getCompilations(Boolean pinned, Integer from, Integer size);

	CompilationDtoResponse getCompilation(Long compId);
}
