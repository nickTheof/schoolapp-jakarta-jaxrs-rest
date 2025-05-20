package gr.aueb.cf.schoolapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public abstract class PersonFiltersDTO {
    private String firstname;
    private String lastname;
}
