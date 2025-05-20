package gr.aueb.cf.schoolapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherReadOnlyDTO extends PersonReadOnlyDTO{
    public TeacherReadOnlyDTO(Long id, String uuid, LocalDateTime createdAt, LocalDateTime updatedAt, String firstname, String lastname, String vat, String email, Integer city_id) {
        super(id, uuid, createdAt, updatedAt, firstname, lastname, vat, email, city_id);
    }
}
