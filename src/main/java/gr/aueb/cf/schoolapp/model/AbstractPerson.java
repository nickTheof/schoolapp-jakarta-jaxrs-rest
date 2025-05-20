package gr.aueb.cf.schoolapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractPerson extends AbstractEntity{
    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true, updatable = false)
    private String vat;

    @Column(nullable = false)
    private String fatherName;

    @Column(nullable = false)
    private String phoneNum;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String zipcode;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String streetNum;

}
