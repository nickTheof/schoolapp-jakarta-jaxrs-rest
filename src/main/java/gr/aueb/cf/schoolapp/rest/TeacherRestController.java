package gr.aueb.cf.schoolapp.rest;

import gr.aueb.cf.schoolapp.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.dto.*;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.service.ITeacherService;
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
import java.util.Map;

@ApplicationScoped
@Path("/teachers")
@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class TeacherRestController {
    private final ITeacherService teacherService;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTeachers() {
        List<TeacherReadOnlyDTO> teacherReadOnlyDTOS = teacherService.getAllTeachers();
        return Response
                .status(Response.Status.OK)
                .entity(teacherReadOnlyDTOS)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertTeacher(TeacherInsertDTO insertDTO, @Context UriInfo uriInfo) throws EntityInvalidArgumentException, EntityAlreadyExistsException, EntityNotFoundException {
        List<String> errors = ValidatorUtil.validateDTO(insertDTO);
        if (!errors.isEmpty()) {
            throw new EntityInvalidArgumentException("Teacher", String.join("\n", errors));
        }
        TeacherReadOnlyDTO readOnlyDTO = teacherService.insertTeacher(insertDTO);
        URI uri = uriInfo
                .getAbsolutePathBuilder()
                .path(String.valueOf(readOnlyDTO.getUuid()))
                .build();

        return Response
                .created(uri)
                .entity(readOnlyDTO)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @GET
    @Path("/{teacherUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacherByUuid(@PathParam("teacherUuid") String uuid) throws EntityNotFoundException {
        TeacherReadOnlyDTO readOnlyDTO = teacherService.getTeacherByUuid(uuid);
        return Response
                .status(Response.Status.OK)
                .entity(readOnlyDTO)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @PUT
    @Path("/{teacherUuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTeacher(@PathParam("teacherUuid") String uuid, TeacherUpdateDTO updateDTO) throws EntityInvalidArgumentException, EntityAlreadyExistsException, EntityNotFoundException {
        if (!uuid.equals(updateDTO.getUuid())) throw new EntityInvalidArgumentException("Teacher", "Different uuid info in path and body");
        List<String> errors = ValidatorUtil.validateDTO(updateDTO);
        if (!errors.isEmpty()) {
            throw new EntityInvalidArgumentException("Teacher", String.join("\n", errors));
        }
        TeacherReadOnlyDTO readOnlyDTO = teacherService.updateTeacher(updateDTO);
        return Response
                .status(Response.Status.OK)
                .entity(readOnlyDTO)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @DELETE
    @Path("/{teacherUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTeacher(@PathParam("teacherUuid") String uuid) throws EntityNotFoundException {
        TeacherReadOnlyDTO readOnlyDTO = teacherService.getTeacherByUuid(uuid);
        teacherService.deleteTeacher(uuid);
        return Response
                .status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(readOnlyDTO)
                .build();
    }

    @GET
    @Path("/filtered")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilteredTeachers(@QueryParam("firstname") @DefaultValue("") String firstname, @QueryParam("lastname") @DefaultValue("") String lastname) {
        TeacherFiltersDTO filtersDTO = new TeacherFiltersDTO(firstname, lastname);
        Map<String, Object> criteria = Mapper.mapToCriteria(filtersDTO);
        List<TeacherReadOnlyDTO> readOnlyDTOS = teacherService.getTeachersByCriteria(criteria);
        return Response
                .status(Response.Status.OK)
                .entity(readOnlyDTOS)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @GET
    @Path("/paginated")
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedResult<TeacherReadOnlyDTO> getPaginatedTeachers(
            @QueryParam("firstname") @DefaultValue("") String firstname,
            @QueryParam("lastname") @DefaultValue("") String lastname,
            @QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("size") @DefaultValue("50") Integer size
    ) throws EntityInvalidArgumentException {
        TeacherFiltersDTO filtersDTO = new TeacherFiltersDTO(firstname, lastname);
        Map<String, Object> criteria = Mapper.mapToCriteria(filtersDTO);

        if (page < 0) throw new EntityInvalidArgumentException("PageInvalidNumber", "Invalid Page Number");
        if (size < 0) throw new EntityInvalidArgumentException("SizeInvalidNumber", "Invalid Size Number");

        List<TeacherReadOnlyDTO> readOnlyDTOS = teacherService.getTeachersByCriteriaPaginated(criteria, page, size);
        long totalItems = teacherService.countTeachersByCriteria(criteria);
        int totalPages = (int) Math.ceil((double) totalItems / size);
        return new PaginatedResult<>(readOnlyDTOS, page, size, totalPages, totalItems);
    }
}
