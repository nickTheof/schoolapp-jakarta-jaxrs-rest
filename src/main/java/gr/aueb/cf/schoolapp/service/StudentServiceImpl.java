package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dto.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.StudentUpdateDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class StudentServiceImpl implements IStudentService{
    private final IStudentDAO studentDAO;

    @Override
    public StudentReadOnlyDTO getStudentById(Long id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public StudentReadOnlyDTO getStudentByUuid(String uuid) throws EntityNotFoundException {
        return null;
    }

    @Override
    public List<StudentReadOnlyDTO> getAllStudents() {
        return List.of();
    }

    @Override
    public List<StudentReadOnlyDTO> getStudentsByCriteria(Map<String, Object> criteria) {
        return List.of();
    }

    @Override
    public List<StudentReadOnlyDTO> getStudentsByCriteriaPaginated(Map<String, Object> criteria, Integer page, Integer size) {
        return List.of();
    }

    @Override
    public long countStudentsByCriteria(Map<String, Object> criteria) {
        return 0;
    }

    @Override
    public StudentReadOnlyDTO insertStudent(StudentInsertDTO insertDTO) throws EntityAlreadyExistsException, EntityInvalidArgumentException {
        return null;
    }

    @Override
    public StudentReadOnlyDTO updateStudent(StudentUpdateDTO updateDTO) throws EntityNotFoundException, EntityInvalidArgumentException {
        return null;
    }

    @Override
    public void deleteStudent(Long id) {

    }

    @Override
    public void deleteStudent(String uuid) {

    }
}
