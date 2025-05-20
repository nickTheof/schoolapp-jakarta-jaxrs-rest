package gr.aueb.cf.schoolapp.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher extends AbstractPerson implements IdentifiableEntity{

    public Teacher(String firstname, String lastname, String vat, String email, String uuid, LocalDateTime createdAt, LocalDateTime updatedAt, Long id, City city) {
        super(firstname, lastname, vat, email, uuid, createdAt, updatedAt);
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
