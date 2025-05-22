package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.dao.ICityDAO;
import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dto.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.City;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.service.util.JPAHelper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class StudentServiceImpl implements IStudentService{
    private final IStudentDAO studentDAO;
    private final ICityDAO cityDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public StudentReadOnlyDTO getStudentById(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.startTransaction();
            StudentReadOnlyDTO readOnlyDTO = studentDAO.getById(id).map(Mapper::mapToStudentReadOnlyDTO).orElseThrow(() -> new EntityNotFoundException("Student", "Student with id " + id + " was not found"));
            JPAHelper.commitTransaction();
            LOGGER.info("Student with id={} was found", id );
            return readOnlyDTO;
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Student with id={} was not found", id );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public StudentReadOnlyDTO getStudentByUuid(String uuid) throws EntityNotFoundException {
        try {
            JPAHelper.startTransaction();
            StudentReadOnlyDTO readOnlyDTO = studentDAO.getByField("uuid", uuid).map(Mapper::mapToStudentReadOnlyDTO).orElseThrow(() -> new EntityNotFoundException("Student", "Student with uuid " + uuid + " was not found"));
            JPAHelper.commitTransaction();
            LOGGER.info("Student with uuid={} was found", uuid );
            return readOnlyDTO;
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Student with uuid={} was not found", uuid );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<StudentReadOnlyDTO> getAllStudents() {
        try {
            JPAHelper.startTransaction();
            List<StudentReadOnlyDTO> readOnlyDTOS = Mapper.studentsToReadOnlyDTOs(studentDAO.getAll());
            JPAHelper.commitTransaction();
            return readOnlyDTOS;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<StudentReadOnlyDTO> getStudentsByCriteria(Map<String, Object> criteria) {
        try {
            JPAHelper.startTransaction();
            List<StudentReadOnlyDTO> readOnlyDTOS = Mapper.studentsToReadOnlyDTOs(studentDAO.getByCriteria(Student.class, criteria));
            JPAHelper.commitTransaction();
            return readOnlyDTOS;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<StudentReadOnlyDTO> getStudentsByCriteriaPaginated(Map<String, Object> criteria, Integer page, Integer size) {
        try {
            JPAHelper.startTransaction();
            List<StudentReadOnlyDTO> readOnlyDTOS = Mapper.studentsToReadOnlyDTOs(studentDAO.getByCriteriaPaginated(Student.class, criteria, page, size));
            JPAHelper.commitTransaction();
            return readOnlyDTOS;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public long countStudentsByCriteria(Map<String, Object> criteria) {
        try {
            JPAHelper.startTransaction();
            long count = studentDAO.countByCriteria(criteria);
            JPAHelper.commitTransaction();
            return count;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public StudentReadOnlyDTO insertStudent(StudentInsertDTO insertDTO) throws EntityAlreadyExistsException, EntityNotFoundException, EntityInvalidArgumentException {
        try {
            JPAHelper.startTransaction();
            City city= cityDAO.getByField("uuid", insertDTO.cityUuid()).orElseThrow(() -> new EntityNotFoundException("City", "City with uuid " + insertDTO.cityUuid() + " was not found" ));
            Student student = Mapper.mapToStudent(insertDTO, city);
            if (studentDAO.getByField("vat", insertDTO.vat()).isPresent()) {
                throw new EntityAlreadyExistsException("Student", "Student with vat: " + insertDTO.vat() + " already exists");
            }

            if (studentDAO.getByField("email", insertDTO.email()).isPresent()) {
                throw new EntityAlreadyExistsException("Student", "Student with email: " + insertDTO.email() + " already exists");
            }

            StudentReadOnlyDTO readOnlyDTO = studentDAO.insert(student)
                    .map(Mapper::mapToStudentReadOnlyDTO)
                    .orElseThrow(() -> new EntityInvalidArgumentException("Student", "Student with VAT=" + insertDTO.vat() + " not inserted"));
            JPAHelper.commitTransaction();
            LOGGER.info("Student with id={}, uuid={}, email={}, vat={},  firstname={}, lastname={} inserted",
                    student.getId(), student.getUuid(), student.getEmail(), student.getVat(), student.getLastname(), student.getFirstname());
            return readOnlyDTO;
        } catch (EntityInvalidArgumentException | EntityAlreadyExistsException | EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            LOGGER.error("Failed to insert student vat={}, firstname={}, lastname={}, Reason={}",
                    insertDTO.vat(), insertDTO.firstname(), insertDTO.lastname(), e.getCause(), e);
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public StudentReadOnlyDTO updateStudent(StudentUpdateDTO updateDTO) throws EntityNotFoundException, EntityAlreadyExistsException, EntityInvalidArgumentException {
        try {
            JPAHelper.startTransaction();
            City city= cityDAO.getByField("uuid", updateDTO.cityUuid()).orElseThrow(() -> new EntityNotFoundException("City", "City with uuid " + updateDTO.cityUuid() + " was not found" ));
            Student fetchedStudent = studentDAO.getByField("uuid", updateDTO.uuid()).orElseThrow(() -> new EntityNotFoundException("Student", "Student with uuid: "
                    + updateDTO.uuid() + " not found"));
            Student student = Mapper.mapToStudent(updateDTO, city);
            student.setId(fetchedStudent.getId());
            student.setVat(fetchedStudent.getVat());
            student.setUuid(fetchedStudent.getUuid());
            Optional<Student> fetchByEmail = studentDAO.getByField("email", updateDTO.email());
            if (fetchByEmail.isPresent() && !fetchByEmail.get().getUuid().equals(updateDTO.uuid())) {
                throw new EntityAlreadyExistsException("Student", "Student with email " + updateDTO.email() + " already exists");
            }

            StudentReadOnlyDTO readOnlyDTO = studentDAO.update(student)
                    .map(Mapper::mapToStudentReadOnlyDTO)
                    .orElseThrow(() -> new EntityInvalidArgumentException("Student", "Student with uuid=" + updateDTO.uuid() + " Error during update"));

            JPAHelper.commitTransaction();
            LOGGER.info("Student with id={}, uuid={}, email={}, vat={},  firstname={}, lastname={} updated",
                    student.getId(), student.getUuid(), student.getEmail(), student.getVat(), student.getLastname(), student.getFirstname());
            return readOnlyDTO;
        } catch (EntityNotFoundException | EntityInvalidArgumentException | EntityAlreadyExistsException e) {
            JPAHelper.rollbackTransaction();
            LOGGER.error("Failed to insert student uuid={}, firstname={}, lastname={}, Reason={}",
                    updateDTO.uuid(), updateDTO.firstname(), updateDTO.lastname(), e.getCause(), e);
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public void deleteStudent(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.startTransaction();
            studentDAO.getById(id).orElseThrow(() -> new EntityNotFoundException("Student", "Student with id " + id + " was not found"));
            studentDAO.delete(id);
            JPAHelper.commitTransaction();
            LOGGER.info("Student with id={} was deleted", id);
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            LOGGER.error("Error in delete. Fail to delete student with id={}.", id, e);
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public void deleteStudent(String uuid) throws EntityNotFoundException {
        try {
            JPAHelper.startTransaction();
            studentDAO.getByField("uuid", uuid).orElseThrow(() -> new EntityNotFoundException("Student", "Student with uuid " + uuid + " was not found"));
            studentDAO.deleteByUuid(uuid);
            JPAHelper.commitTransaction();
            LOGGER.info("Student with uuid={} was deleted", uuid);
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            LOGGER.error("Error in delete. Fail to delete student with uuid={}.", uuid, e);
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }
}
