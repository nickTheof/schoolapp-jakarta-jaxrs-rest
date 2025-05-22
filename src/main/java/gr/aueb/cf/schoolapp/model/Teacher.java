package gr.aueb.cf.schoolapp.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher extends AbstractPerson implements IdentifiableEntity{

    public Teacher(Long id, String firstname, String lastname, String vat, String email, City city) {
        super(firstname, lastname, vat, email);
        this.id = id;
        this.city = city;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;
}
