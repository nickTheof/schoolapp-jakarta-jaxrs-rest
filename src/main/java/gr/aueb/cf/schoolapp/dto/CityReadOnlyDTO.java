package gr.aueb.cf.schoolapp.dto;

import java.time.LocalDateTime;

public record CityReadOnlyDTO(
    Integer id,
    String uuid,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String name
) {
}
