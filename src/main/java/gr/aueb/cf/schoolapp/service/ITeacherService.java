package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.TeacherUpdateDTO;

import java.util.List;
import java.util.Map;

public interface ITeacherService {
    TeacherReadOnlyDTO getTeacherById(Long id) throws EntityNotFoundException;
    TeacherReadOnlyDTO getTeacherByUuid(String uuid) throws EntityNotFoundException;
    List<TeacherReadOnlyDTO> getAllTeachers();
    List<TeacherReadOnlyDTO> getTeachersByCriteria(Map<String, Object> criteria);
    List<TeacherReadOnlyDTO> getTeachersByCriteriaPaginated(Map<String, Object> criteria, Integer page, Integer size);
    long countTeachersByCriteria(Map<String, Object> criteria);
    TeacherReadOnlyDTO insertTeacher(TeacherInsertDTO insertDTO) throws EntityNotFoundException, EntityAlreadyExistsException, EntityInvalidArgumentException;
    TeacherReadOnlyDTO updateTeacher(TeacherUpdateDTO updateDTO) throws EntityNotFoundException, EntityAlreadyExistsException, EntityInvalidArgumentException;
    void deleteTeacher(Long id) throws EntityNotFoundException;
    void deleteTeacher(String uuid) throws EntityNotFoundException;

}
