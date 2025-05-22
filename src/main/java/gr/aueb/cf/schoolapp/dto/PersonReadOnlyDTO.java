package gr.aueb.cf.schoolapp.dto;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PersonReadOnlyDTO {
    private Long id;
    private String uuid;
    private String firstname;
    private String lastname;
    private String vat;
    private String email;
    private String cityUuid;
}
