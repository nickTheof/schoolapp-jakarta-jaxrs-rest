package gr.aueb.cf.schoolapp.dto;


public record StudentReadOnlyDTO (
        Long id,
        String uuid,
        String firstname,
        String lastname,
        String vat,
        String email,
        String cityUuid
) {}


