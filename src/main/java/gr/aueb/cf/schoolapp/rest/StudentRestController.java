package gr.aueb.cf.schoolapp.rest;

import gr.aueb.cf.schoolapp.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.dto.*;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.service.IStudentService;
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
@Path("/students")
@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class StudentRestController {
    private final IStudentService studentService;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudents() {
        List<StudentReadOnlyDTO> readOnlyDTOS = studentService.getAllStudents();
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
    public Response insertStudent(StudentInsertDTO insertDTO, @Context UriInfo uriInfo) throws EntityInvalidArgumentException, EntityAlreadyExistsException, EntityNotFoundException {
        List<String> errors = ValidatorUtil.validateDTO(insertDTO);
        if (!errors.isEmpty()) {
            throw new EntityInvalidArgumentException("Student", String.join("\n", errors));
        }
        StudentReadOnlyDTO readOnlyDTO = studentService.insertStudent(insertDTO);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(readOnlyDTO.getUuid()))
                .build();
        return Response
                .created(uri)
                .entity(readOnlyDTO)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @GET
    @Path("/{studentUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentByUuid(@PathParam("studentUuid") String uuid) throws EntityNotFoundException {
        StudentReadOnlyDTO readOnlyDTO = studentService.getStudentByUuid(uuid);
        return Response
                .status(Response.Status.OK)
                .entity(readOnlyDTO)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @PUT
    @Path("/{studentUuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(@PathParam("studentUuid") String uuid, StudentUpdateDTO updateDTO) throws EntityInvalidArgumentException, EntityAlreadyExistsException, EntityNotFoundException {
        if (!uuid.equals(updateDTO.getUuid())) throw new EntityInvalidArgumentException("Student", "Different uuid info in path and body");
        List<String> errors = ValidatorUtil.validateDTO(updateDTO);
        if (!errors.isEmpty()) {
            throw new EntityInvalidArgumentException("Student", String.join("\n", errors));
        }
        StudentReadOnlyDTO readOnlyDTO = studentService.updateStudent(updateDTO);
        return Response
                .status(Response.Status.OK)
                .entity(readOnlyDTO)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @DELETE
    @Path("/{studentUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStudent(@PathParam("studentUuid") String uuid) throws EntityNotFoundException {
        StudentReadOnlyDTO readOnlyDTO = studentService.getStudentByUuid(uuid);
        studentService.deleteStudent(uuid);
        return Response
                .status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(readOnlyDTO)
                .build();
    }

    @GET
    @Path("/filtered")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilteredStudents(
            @QueryParam("firstname") @DefaultValue("") String firstname,
            @QueryParam("lastname") @DefaultValue("") String lastname
    ) {
        TeacherFiltersDTO filtersDTO = new TeacherFiltersDTO(firstname, lastname);
        Map<String, Object> criteria = Mapper.mapToCriteria(filtersDTO);
        List<StudentReadOnlyDTO> readOnlyDTOS = studentService.getStudentsByCriteria(criteria);
        return Response
                .status(Response.Status.OK)
                .entity(readOnlyDTOS)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }


    @GET
    @Path("/paginated")
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedResult<StudentReadOnlyDTO> getPaginatedStudents(
            @QueryParam("firstname") @DefaultValue("") String firstname,
            @QueryParam("lastname") @DefaultValue("") String lastname,
            @QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("size") @DefaultValue("50") Integer size
    ) throws EntityInvalidArgumentException {
        TeacherFiltersDTO filtersDTO = new TeacherFiltersDTO(firstname, lastname);
        Map<String, Object> criteria = Mapper.mapToCriteria(filtersDTO);
        if (page < 0) throw new EntityInvalidArgumentException("PageInvalidNumber", "Invalid Page Number");
        if (size < 0) throw new EntityInvalidArgumentException("SizeInvalidNumber", "Invalid Size Number");

        List<StudentReadOnlyDTO> readOnlyDTOS = studentService.getStudentsByCriteriaPaginated(criteria, page, size);
        long totalItems = studentService.countStudentsByCriteria(criteria);
        int totalPages = (int) Math.ceil((double) totalItems / size);
        return new PaginatedResult<>(readOnlyDTOS, page, size, totalPages, totalItems);
    }
}
