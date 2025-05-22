package gr.aueb.cf.schoolapp.dto;

import jakarta.validation.constraints.*;

public record UserInsertDTO(
        @Email(message = "Invalid username")
        String username,

        @NotNull(message = "Το όνομα είναι υποχρεωτικό πεδίο.")
        @Size(min = 2, max = 255, message = "Το όνομα πρέπει να είναι μεταξύ 2-255 χαρακτήρων.")
        String firstname,

        @NotNull(message = "Το επώνυμο είναι υποχρεωτικό πεδίο.")
        @Size(min = 2, max = 255, message = "Το επώνυμο πρέπει να είναι μεταξύ 2-255 χαρακτήρων.")
        String lastname,

        @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[@#$!%&*]).{8,}$", message = "Invalid Password")
        String password,

        @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[@#$!%&*]).{8,}$", message = "Invalid Password")
        String confirmPassword,

        @NotEmpty(message = "Role can not be empty")
        @Pattern(regexp = "^(EDITOR|READER|ADMIN)$", message = "Role must be READER, EDITOR, or ADMIN")
        String role
) { }



