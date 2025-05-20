package gr.aueb.cf.schoolapp.dto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public abstract class PersonReadOnlyDTO {
    private Long id;
    private String uuid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String firstname;
    private String lastname;
    private String vat;
    private String fatherName;
    private String phoneNum;
    private String email;
    private String zipcode;
    private String street;
    private String streetNum;
    private Integer city_id;
}
