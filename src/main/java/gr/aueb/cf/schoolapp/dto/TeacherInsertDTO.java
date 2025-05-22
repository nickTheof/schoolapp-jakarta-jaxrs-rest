package gr.aueb.cf.schoolapp.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TeacherInsertDTO extends PersonInsertDTO{
    public TeacherInsertDTO(String firstname, String lastname, String vat, String email, String cityUuid) {
        super(firstname, lastname, vat, email, cityUuid);
    }
}
