package gr.aueb.cf.schoolapp.dto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PersonReadOnlyDTO {
    private Long id;
    private String uuid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String firstname;
    private String lastname;
    private String vat;
    private String email;
    private Integer city_id;
}
