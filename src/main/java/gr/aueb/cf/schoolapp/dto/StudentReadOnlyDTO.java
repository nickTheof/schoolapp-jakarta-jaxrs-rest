package gr.aueb.cf.schoolapp.dto;

public class StudentReadOnlyDTO extends PersonReadOnlyDTO{
    public StudentReadOnlyDTO(Long id, String uuid, String firstname, String lastname, String vat, String email, String cityUuid) {
        super(id, uuid, firstname, lastname, vat, email, cityUuid);
    }
}
