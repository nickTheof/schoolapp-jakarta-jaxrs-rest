package gr.aueb.cf.schoolapp.dto;

public record UserReadOnlyDTO(
        Long id,
        String username,
        String firstname,
        String lastname,
        String password,
        String role
) {}


