package gr.aueb.cf.schoolapp.rest;

import gr.aueb.cf.schoolapp.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.dto.CityInsertDTO;
import gr.aueb.cf.schoolapp.dto.CityReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.CityUpdateDTO;
import gr.aueb.cf.schoolapp.service.ICityService;
import gr.aueb.cf.schoolapp.validator.ValidatorUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

@ApplicationScoped
@Path("/cities")
@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class CityRestController {
    private final ICityService cityService;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCities() {
        List<CityReadOnlyDTO> readOnlyDTOS = cityService.getAllCities();
        return Response
                .status(Response.Status.OK)
                .entity(readOnlyDTOS)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCity(CityInsertDTO insertDTO, @Context UriInfo uriInfo) throws EntityInvalidArgumentException, EntityAlreadyExistsException {
        List<String> errors = ValidatorUtil.validateDTO(insertDTO);
        if (!errors.isEmpty()) throw new EntityInvalidArgumentException("City", String.join("\n", errors));
        CityReadOnlyDTO readOnlyDTO = cityService.insertCity(insertDTO);
        URI uri = uriInfo
                .getAbsolutePathBuilder()
                .path(String.valueOf(readOnlyDTO.id()))
                .build();
        return Response
                .created(uri)
                .entity(readOnlyDTO)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @GET
    @Path("/{cityUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCityByUuid(@PathParam("cityUuid") String uuid) throws EntityNotFoundException {
        CityReadOnlyDTO readOnlyDTO = cityService.getCityByUuid(uuid);
        return Response
                .status(Response.Status.OK)
                .entity(readOnlyDTO)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @PUT
    @Path("/{cityUuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCity(@PathParam("cityUuid") String uuid, CityUpdateDTO updateDTO) throws EntityInvalidArgumentException, EntityNotFoundException, EntityAlreadyExistsException {
        if (!uuid.equals(updateDTO.uuid())) throw new EntityInvalidArgumentException("City", "Different uuid info in path and body");
        List<String> errors = ValidatorUtil.validateDTO(updateDTO);
        if (!errors.isEmpty()) {
            throw new EntityInvalidArgumentException("City", String.join("\n", errors));
        }
        CityReadOnlyDTO readOnlyDTO = cityService.updateCity(updateDTO);
        return Response.status(Response.Status.OK).entity(readOnlyDTO).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    @DELETE
    @Path("/{cityUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCity(@PathParam("cityUuid") String uuid) throws EntityNotFoundException, EntityInvalidArgumentException {
        CityReadOnlyDTO readOnlyDTO = cityService.getCityByUuid(uuid);
        cityService.deleteCity(uuid);
        return Response
                .status(Response.Status.OK)
                .entity(readOnlyDTO)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }


}
