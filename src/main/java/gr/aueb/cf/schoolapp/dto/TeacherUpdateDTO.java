package gr.aueb.cf.schoolapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;

public record TeacherUpdateDTO(
    @NotNull(message = "Το uuid είναι υποχρεωτικό πεδίο.")
    String uuid,

    @NotNull(message = "Το όνομα είναι υποχρεωτικό πεδίο.")
    @Size(min = 2, max = 255, message = "Το όνομα πρέπει να είναι μεταξύ 2-255 χαρακτήρων.")
    String firstname,

    @NotNull(message = "Το επώνυμο είναι υποχρεωτικό πεδίο.")
    @Size(min = 2, max = 255, message = "Το όνομα πρέπει να είναι μεταξύ 2-255 χαρακτήρων.")
    String lastname,

    @NotNull(message = "Το email είναι υποχρεωτικό πεδίο.")
    @Email(message = "Το email πρέπει να έχει έγκυρη μορφή.")
    String email,

    @NotNull(message = "Η πόλη είναι υποχρεωτικό πεδίο.")
    String cityUuid
){}
