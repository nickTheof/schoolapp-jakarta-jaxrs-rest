package gr.aueb.cf.schoolapp.dto;

public class StudentInsertDTO extends PersonInsertDTO{
    public StudentInsertDTO(String firstname, String lastname, String vat, String email, String cityUuid) {
        super(firstname, lastname, vat, email, cityUuid);
    }
}
