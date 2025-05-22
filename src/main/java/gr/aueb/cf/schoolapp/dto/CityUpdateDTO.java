package gr.aueb.cf.schoolapp.dto;

import jakarta.validation.constraints.NotNull;

public record CityUpdateDTO(
//        @NotNull(message = "Το id της πόλης είναι υποχρεωτικό")
//        Integer id,
        @NotNull(message = "Το uuid της πόλης είναι υποχρεωτικό")
        String uuid,
        @NotNull(message = "Το όνομα της πόλης είναι υποχρεωτικό")
        String name
) {
}
