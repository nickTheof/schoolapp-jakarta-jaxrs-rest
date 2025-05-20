package gr.aueb.cf.schoolapp.dto;

import java.time.LocalDateTime;

public class StudentReadOnlyDTO extends PersonReadOnlyDTO{
    public StudentReadOnlyDTO(Long id, String uuid, LocalDateTime createdAt, LocalDateTime updatedAt, String firstname, String lastname, String vat, String email, Integer city_id) {
        super(id, uuid, createdAt, updatedAt, firstname, lastname, vat, email, city_id);
    }
}
