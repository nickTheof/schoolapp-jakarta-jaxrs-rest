package gr.aueb.cf.schoolapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AbstractPerson extends AbstractEntity{
    public AbstractPerson(String firstname, String lastname, String vat, String email, String uuid, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(uuid, createdAt, updatedAt);
        this.firstname = firstname;
        this.lastname = lastname;
        this.vat = vat;
        this.email = email;
    }

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true, updatable = false)
    private String vat;

    @Column(nullable = false, unique = true)
    private String email;



}
