package gr.aueb.cf.schoolapp.dto;

import jakarta.validation.constraints.NotNull;

public record CityInsertDTO(
        @NotNull(message = "Το όνομα της πόλης είναι υποχρεωτικό")
        String name
) {
}
