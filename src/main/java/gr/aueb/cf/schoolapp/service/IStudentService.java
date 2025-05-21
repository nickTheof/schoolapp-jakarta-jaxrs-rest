package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.dto.*;

import java.util.List;
import java.util.Map;

public interface IStudentService {
    StudentReadOnlyDTO getStudentById(Long id) throws EntityNotFoundException;
    StudentReadOnlyDTO getStudentByUuid(String uuid) throws EntityNotFoundException;
    List<StudentReadOnlyDTO> getAllStudents();
    List<StudentReadOnlyDTO> getStudentsByCriteria(Map<String, Object> criteria);
    List<StudentReadOnlyDTO> getStudentsByCriteriaPaginated(Map<String, Object> criteria, Integer page, Integer size);
    long countStudentsByCriteria(Map<String, Object> criteria);
    StudentReadOnlyDTO insertStudent(StudentInsertDTO insertDTO) throws EntityAlreadyExistsException, EntityInvalidArgumentException;
    StudentReadOnlyDTO updateStudent(StudentUpdateDTO updateDTO) throws EntityNotFoundException, EntityInvalidArgumentException;
    void deleteStudent(Long id);
    void deleteStudent(String uuid);
}

