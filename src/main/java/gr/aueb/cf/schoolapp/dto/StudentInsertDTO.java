package gr.aueb.cf.schoolapp.dto;

public class StudentInsertDTO extends PersonInsertDTO{
    public StudentInsertDTO(String firstname, String lastname, String vat, String email, Integer city_id) {
        super(firstname, lastname, vat, email, city_id);
    }
}
