package gr.aueb.cf.schoolapp.dto;

public class StudentUpdateDTO extends PersonUpdateDTO{
    public StudentUpdateDTO(Long id, String firstname, String lastname, String vat, String email, Integer city_id) {
        super(id, firstname, lastname, vat, email, city_id);
    }
}
