package gr.aueb.cf.schoolapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PersonUpdateDTO {
    @NotNull(message = "Το uuid είναι υποχρεωτικό πεδίο.")
    private String uuid;

    @NotNull(message = "Το όνομα είναι υποχρεωτικό πεδίο.")
    @Size(min = 2, max = 255, message = "Το όνομα πρέπει να είναι μεταξύ 2-255 χαρακτήρων.")
    private String firstname;

    @NotNull(message = "Το επώνυμο είναι υποχρεωτικό πεδίο.")
    @Size(min = 2, max = 255, message = "Το όνομα πρέπει να είναι μεταξύ 2-255 χαρακτήρων.")
    private String lastname;

    @NotNull(message = "Το email είναι υποχρεωτικό πεδίο.")
    @Email(message = "Το email πρέπει να έχει έγκυρη μορφή.")
    private String email;

    @NotNull(message = "Η πόλη είναι υποχρεωτικό πεδίο.")
    private String cityUuid;
}
