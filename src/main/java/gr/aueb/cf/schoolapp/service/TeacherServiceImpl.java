package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.dao.ICityDAO;
import gr.aueb.cf.schoolapp.dao.ITeacherDAO;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.City;
import gr.aueb.cf.schoolapp.model.Teacher;
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
public class TeacherServiceImpl implements ITeacherService{
    private final ITeacherDAO teacherDAO;
    private final ICityDAO cityDAO;
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);

    @Override
    public TeacherReadOnlyDTO getTeacherById(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.startTransaction();
            TeacherReadOnlyDTO readOnlyDTO = teacherDAO.getById(id).map(Mapper::mapToTeacherReadOnlyDTO).orElseThrow(() -> new EntityNotFoundException("Teacher", "Teacher with id " + id + " was not found"));
            JPAHelper.commitTransaction();
            LOGGER.info("Teacher with id={} was found", id );
            return readOnlyDTO;
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Teacher with id={} was not found", id );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public TeacherReadOnlyDTO getTeacherByUuid(String uuid) throws EntityNotFoundException {
        try {
            JPAHelper.startTransaction();
            TeacherReadOnlyDTO readOnlyDTO = teacherDAO.getByField("uuid", uuid).map(Mapper::mapToTeacherReadOnlyDTO).orElseThrow(() -> new EntityNotFoundException("Teacher", "Teacher with uuid " + uuid + " was not found"));
            JPAHelper.commitTransaction();
            LOGGER.info("Teacher with uuid={} was found", uuid );
            return readOnlyDTO;
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Teacher with uuid={} was not found", uuid );
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<TeacherReadOnlyDTO> getAllTeachers() {
        try {
            JPAHelper.startTransaction();
            List<TeacherReadOnlyDTO> readOnlyDTOS = Mapper.teachersToReadOnlyDTOs(teacherDAO.getAll());
            JPAHelper.commitTransaction();
            return readOnlyDTOS;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<TeacherReadOnlyDTO> getTeachersByCriteria(Map<String, Object> criteria) {
        try {
            JPAHelper.startTransaction();
            List<TeacherReadOnlyDTO> readOnlyDTOS = Mapper.teachersToReadOnlyDTOs(teacherDAO.getByCriteria(Teacher.class, criteria));
            JPAHelper.commitTransaction();
            return readOnlyDTOS;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<TeacherReadOnlyDTO> getTeachersByCriteriaPaginated(Map<String, Object> criteria, Integer page, Integer size) {
        try {
            JPAHelper.startTransaction();
            List<TeacherReadOnlyDTO> readOnlyDTOS = Mapper.teachersToReadOnlyDTOs(teacherDAO.getByCriteriaPaginated(Teacher.class, criteria, page, size));
            JPAHelper.commitTransaction();
            return readOnlyDTOS;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public long countTeachersByCriteria(Map<String, Object> criteria) {
        try {
            JPAHelper.startTransaction();
            long count = teacherDAO.countByCriteria(criteria);
            JPAHelper.commitTransaction();
            return count;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public TeacherReadOnlyDTO insertTeacher(TeacherInsertDTO insertDTO) throws EntityAlreadyExistsException, EntityNotFoundException, EntityInvalidArgumentException {
        try {
            JPAHelper.startTransaction();
            City city= cityDAO.getByField("uuid", insertDTO.getCityUuid()).orElseThrow(() -> new EntityNotFoundException("City", "City with uuid " + insertDTO.getCityUuid() + " was not found" ));
            Teacher teacher = Mapper.mapToTeacher(insertDTO, city);
            if (teacherDAO.getByField("vat", insertDTO.getVat()).isPresent()) {
                throw new EntityAlreadyExistsException("Teacher", "Teacher with vat: " + insertDTO.getVat() + " already exists");
            }

            if (teacherDAO.getByField("email", insertDTO.getEmail()).isPresent()) {
                throw new EntityAlreadyExistsException("Teacher", "Teacher with email: " + insertDTO.getEmail() + " already exists");
            }

            TeacherReadOnlyDTO readOnlyDTO = teacherDAO.insert(teacher)
                    .map(Mapper::mapToTeacherReadOnlyDTO)
                    .orElseThrow(() -> new EntityInvalidArgumentException("Teacher", "Teacher with VAT=" + insertDTO.getVat() + " not inserted"));
            JPAHelper.commitTransaction();
            LOGGER.info("Teacher with id={}, uuid={}, email={}, vat={},  firstname={}, lastname={} inserted",
                    teacher.getId(), teacher.getUuid(), teacher.getEmail(), teacher.getVat(), teacher.getLastname(), teacher.getFirstname());
            return readOnlyDTO;
        } catch (EntityInvalidArgumentException | EntityAlreadyExistsException | EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            LOGGER.error("Failed to insert teacher vat={}, firstname={}, lastname={}, Reason={}",
                    insertDTO.getVat(), insertDTO.getFirstname(), insertDTO.getLastname(), e.getCause(), e);
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public TeacherReadOnlyDTO updateTeacher(TeacherUpdateDTO updateDTO) throws EntityNotFoundException, EntityInvalidArgumentException, EntityAlreadyExistsException {
        try {
            JPAHelper.startTransaction();
            City city= cityDAO.getByField("uuid", updateDTO.getCityUuid()).orElseThrow(() -> new EntityNotFoundException("City", "City with uuid " + updateDTO.getCityUuid() + " was not found" ));
            Teacher fetchedTeacher = teacherDAO.getByField("uuid", updateDTO.getUuid()).orElseThrow(() -> new EntityNotFoundException("Teacher", "Teacher with uuid: "
                    + updateDTO.getUuid() + " not found"));
            Teacher teacher = Mapper.mapToTeacher(updateDTO, city);
            teacher.setId(fetchedTeacher.getId());
            teacher.setVat(fetchedTeacher.getVat());
            teacher.setUuid(fetchedTeacher.getUuid());
            Optional<Teacher> fetchByEmail = teacherDAO.getByField("email", updateDTO.getEmail());
            if (fetchByEmail.isPresent() && !fetchByEmail.get().getUuid().equals(updateDTO.getUuid())) {
                throw new EntityAlreadyExistsException("Teacher", "Teacher with email " + updateDTO.getEmail() + " already exists");
            }

            TeacherReadOnlyDTO readOnlyDTO = teacherDAO.update(teacher)
                    .map(Mapper::mapToTeacherReadOnlyDTO)
                    .orElseThrow(() -> new EntityInvalidArgumentException("Teacher", "Teacher with uuid=" + updateDTO.getUuid() + " Error during update"));

            JPAHelper.commitTransaction();
            LOGGER.info("Teacher with id={}, uuid={}, email={}, vat={},  firstname={}, lastname={} updated",
                    teacher.getId(), teacher.getUuid(), teacher.getEmail(), teacher.getVat(), teacher.getLastname(), teacher.getFirstname());
            return readOnlyDTO;
        } catch (EntityNotFoundException | EntityInvalidArgumentException | EntityAlreadyExistsException e) {
            JPAHelper.rollbackTransaction();
            LOGGER.error("Failed to insert teacher uuid={}, firstname={}, lastname={}, Reason={}",
                    updateDTO.getUuid(), updateDTO.getFirstname(), updateDTO.getLastname(), e.getCause(), e);
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public void deleteTeacher(Long id) throws EntityNotFoundException {
        try {
            JPAHelper.startTransaction();
            teacherDAO.getById(id).orElseThrow(() -> new EntityNotFoundException("Teacher", "Teacher with id " + id + " was not found"));
            teacherDAO.delete(id);
            JPAHelper.commitTransaction();
            LOGGER.info("Teacher with id={} was deleted", id);
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            LOGGER.error("Error in delete. Fail to delete teacher with id={}.", id, e);
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public void deleteTeacher(String uuid) throws EntityNotFoundException {
        try {
            JPAHelper.startTransaction();
            teacherDAO.getByField("uuid", uuid).orElseThrow(() -> new EntityNotFoundException("Teacher", "Teacher with uuid " + uuid + " was not found"));
            teacherDAO.deleteByUuid(uuid);
            JPAHelper.commitTransaction();
            LOGGER.info("Teacher with uuid={} was deleted", uuid);
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            LOGGER.error("Error in delete. Fail to delete teacher with uuid={}.", uuid, e);
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }
}
