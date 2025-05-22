package gr.aueb.cf.schoolapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TeacherReadOnlyDTO extends PersonReadOnlyDTO{
    public TeacherReadOnlyDTO(Long id, String uuid, String firstname, String lastname, String vat, String email, String cityUuid) {
        super(id, uuid, firstname, lastname, vat, email, cityUuid);
    }
}
