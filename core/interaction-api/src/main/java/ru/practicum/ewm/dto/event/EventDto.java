package ru.practicum.ewm.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.ewm.dto.category.CategoryDto;
import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.dto.user.UserDto;

import java.time.LocalDateTime;

@Data
@Builder
public class EventDto {
	private Long id;
	private String annotation;
	private Integer confirmedRequests;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createdOn;
	private String description;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime eventDate;
	private Boolean paid;
	private Integer participantLimit;
	private LocalDateTime publishedOn;
	private Boolean requestModeration;
	private String state;
	private String title;
	private Double rating;
	private CategoryDto category;
	private UserDto initiator;
	private LocationDto location;

}
