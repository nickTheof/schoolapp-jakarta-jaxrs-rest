package gr.aueb.cf.schoolapp.mapper;

import gr.aueb.cf.schoolapp.core.enums.Role;
import gr.aueb.cf.schoolapp.dto.*;
import gr.aueb.cf.schoolapp.model.*;
import gr.aueb.cf.schoolapp.security.SecUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Mapper {
    private Mapper() {

    }

    public static Teacher mapToTeacher(TeacherInsertDTO dto, City city) {
        return new Teacher(null, dto.firstname(), dto.lastname(), dto.vat(), dto.email(), city);
    }

    public static Teacher mapToTeacher(TeacherUpdateDTO dto, City city) {
        return new Teacher(null, dto.firstname(), dto.lastname(), null, dto.email(), city);
    }

    public static TeacherReadOnlyDTO mapToTeacherReadOnlyDTO(Teacher teacher) {
        return new TeacherReadOnlyDTO(teacher.getId(), teacher.getUuid(), teacher.getFirstname(), teacher.getLastname(), teacher.getVat(), teacher.getEmail(), teacher.getCity().getUuid());
    }

    public static List<TeacherReadOnlyDTO> teachersToReadOnlyDTOs(List<Teacher> teachers) {
        return teachers.stream()
                .map(Mapper::mapToTeacherReadOnlyDTO)
                .collect(Collectors.toList());
    }

    public static Student mapToStudent(StudentInsertDTO dto, City city) {
        return new Student(null, dto.firstname(), dto.lastname(), dto.vat(), dto.email(), city);
    }

    public static Student mapToStudent(StudentUpdateDTO dto, City city) {
        return new Student(null, dto.firstname(), dto.lastname(), null, dto.email(), city);
    }

    public static StudentReadOnlyDTO mapToStudentReadOnlyDTO(Student student) {
        return new StudentReadOnlyDTO(student.getId(), student.getUuid(), student.getFirstname(), student.getLastname(), student.getVat(), student.getEmail(), student.getCity().getUuid());
    }

    public static List<StudentReadOnlyDTO> studentsToReadOnlyDTOs(List<Student> students) {
        return students.stream()
                .map(Mapper::mapToStudentReadOnlyDTO)
                .collect(Collectors.toList());
    }

    public static Map<String , Object> mapToCriteria(TeacherFiltersDTO filtersDTO) {
        Map<String , Object> filters = new HashMap<>();

        if (filtersDTO.firstname() != null && !filtersDTO.firstname().isEmpty()) {
            filters.put("firstname", filtersDTO.firstname());
        }

        if (filtersDTO.lastname() != null && !filtersDTO.lastname().isEmpty()) {
            filters.put("lastname", filtersDTO.lastname());
        }
        return filters;
    }

    public static Map<String , Object> mapToCriteria(StudentFiltersDTO filtersDTO) {
        Map<String , Object> filters = new HashMap<>();

        if (filtersDTO.firstname() != null && !filtersDTO.firstname().isEmpty()) {
            filters.put("firstname", filtersDTO.firstname());
        }

        if (filtersDTO.lastname() != null && !filtersDTO.lastname().isEmpty()) {
            filters.put("lastname", filtersDTO.lastname());
        }
        return filters;
    }

    public static City mapToCity(CityInsertDTO insertDTO) {
        return new City(null, null, insertDTO.name());
    }

    public static City mapToCity(CityUpdateDTO updateDTO) {
        return new City(null, updateDTO.uuid(), updateDTO.name());
    }


    public static CityReadOnlyDTO mapToCityReadOnlyDTO(City city) {
        return new CityReadOnlyDTO(city.getId(), city.getUuid(), city.getName());
    }

    public static List<CityReadOnlyDTO> mapToCityReadOnlyDTOs(List<City> cities) {
        return cities.stream().map(Mapper::mapToCityReadOnlyDTO).collect(Collectors.toList());
    }

    public static User mapToUser(UserInsertDTO dto) {
        return new User(null, dto.firstname(), dto.lastname(), dto.username(), SecUtil.hashPassword(dto.password()), true,
                Role.valueOf(dto.role()));
    }

    public static UserReadOnlyDTO mapToUserReadOnlyDTO(User user) {
        return new UserReadOnlyDTO(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getPassword(),
                user.getRole().name());
    }
}
