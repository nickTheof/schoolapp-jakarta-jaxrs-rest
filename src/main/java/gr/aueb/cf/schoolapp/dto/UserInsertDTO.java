package gr.aueb.cf.schoolapp.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserInsertDTO {

    @Email(message = "Invalid username")
    private String username;

    @NotNull(message = "Το όνομα είναι υποχρεωτικό πεδίο.")
    @Size(min = 2, max = 255, message = "Το όνομα πρέπει να είναι μεταξύ 2-255 χαρακτήρων.")
    private String firstname;

    @NotNull(message = "Το επώνυμο είναι υποχρεωτικό πεδίο.")
    @Size(min = 2, max = 255, message = "Το επώνυμο πρέπει να είναι μεταξύ 2-255 χαρακτήρων.")
    private String lastname;

    // Coding!25
    @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[@#$!%&*]).{8,}$",
            message = "Invalid Password")
    private String password;

    @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\\d)(?=.*?[@#$!%&*]).{8,}$",
            message = "Invalid Password")
    private String confirmPassword;

    @NotEmpty(message = "Role can not be empty")
    @Pattern(regexp = "^(EDITOR|READER|ADMIN)$", message = "Role must be READER, EDITOR, or ADMIN")
    private String role;
}
