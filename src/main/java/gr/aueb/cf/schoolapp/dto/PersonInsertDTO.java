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
@Builder
public abstract class PersonInsertDTO {
        @NotNull(message = "Το όνομα είναι υποχρεωτικό πεδίο.")
        @Size(min = 2, max = 255, message = "Το όνομα πρέπει να είναι μεταξύ 2-255 χαρακτήρων.")
        private String firstname;

        @NotNull(message = "Το επώνυμο είναι υποχρεωτικό πεδίο.")
        @Size(min = 2, max = 255, message = "Το όνομα πρέπει να είναι μεταξύ 2-255 χαρακτήρων.")
        private String lastname;

        @NotNull(message = "Το ΑΦΜ είναι υποχρεωτικό πεδίο.")
        @Pattern(regexp = "^\\d{9}$", message = "Το ΑΦΜ πρέπει να αποτελείται από 9 ψηφία.")
        private String vat;

        @NotNull(message = "Το επώνυμο είναι υποχρεωτικό πεδίο.")
        @Size(min = 2, max = 255, message = "Το όνομα πρέπει να είναι μεταξύ 2-255 χαρακτήρων.")
        private String fatherName;


        @NotNull(message = "Ο αριθμός τηλεφώνου είναι υποχρεωτικό πεδίο.")
        @Size(min=10, max = 20, message = "Ο αριθμός τηλεφώνου πρέπει να είναι μεταξύ 10-20 χαρακτήρων.")
        private String phoneNum;

        @NotNull(message = "Το email είναι υποχρεωτικό πεδίο.")
        @Email(message = "Το email πρέπει να έχει έγκυρη μορφή.")
        private String email;

        @NotNull(message = "To TK είναι υποχρεωτικό πεδίο.")
        @Size(min=5, max = 20, message = "Το ΤΚ πρέπει να είναι μεταξύ 5-20 χαρακτήρων.")
        private String zipcode;

        @NotNull(message = "Η διεύθυνση είναι υποχρεωτικό πεδίο.")
        @Size(min = 2, max = 255, message = "Η διεύθυνση πρέπει να είναι μεταξύ 2-255 χαρακτήρων.")
        private String street;

        @NotNull(message = "Ο αριθμός διευθύνσεως είναι υποχρεωτικό πεδίο.")
        @Pattern(regexp = "^\\d+[A-Z]*$", message = "Ο αριθμός διευθύνσεως πρέπει να έχει έγκυρη μορφή.")
        private String streetNum;

        @NotNull(message = "Η πόλη είναι υποχρεωτικό πεδίο.")
        private Integer city_id;
}


