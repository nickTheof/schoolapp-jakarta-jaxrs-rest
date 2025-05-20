package gr.aueb.cf.schoolapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends AbstractPerson implements IdentifiableEntity{

    public Student(String firstname, String lastname, String vat, String email, String uuid, LocalDateTime createdAt, LocalDateTime updatedAt, Long id, City city) {
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
