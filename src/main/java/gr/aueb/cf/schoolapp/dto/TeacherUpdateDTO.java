package gr.aueb.cf.schoolapp.dto;

public class TeacherUpdateDTO extends PersonUpdateDTO{
    public TeacherUpdateDTO(Long id, String firstname, String lastname, String vat, String email, Integer city_id) {
        super(id, firstname, lastname, vat, email, city_id);
    }
}
