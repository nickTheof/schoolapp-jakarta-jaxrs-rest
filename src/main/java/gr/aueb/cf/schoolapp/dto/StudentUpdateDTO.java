package gr.aueb.cf.schoolapp.dto;

public class StudentUpdateDTO extends PersonUpdateDTO{
    public StudentUpdateDTO(String uuid, String firstname, String lastname, String email, String cityUuid) {
        super(uuid, firstname, lastname, email, cityUuid);
    }
}
