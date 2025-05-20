package gr.aueb.cf.schoolapp.mapper;

import gr.aueb.cf.schoolapp.core.enums.Role;
import gr.aueb.cf.schoolapp.dto.*;
import gr.aueb.cf.schoolapp.model.AbstractPerson;
import gr.aueb.cf.schoolapp.model.City;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.security.SecUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Mapper {
    private Mapper() {

    }

    public static Teacher mapToTeacher(TeacherInsertDTO dto, City city) {
        return new Teacher(dto.getFirstname(), dto.getLastname(), dto.getVat(), dto.getEmail(), null, null, null, null, city);
    }

    public static Teacher mapToTeacher(TeacherUpdateDTO dto, City city) {
        return new Teacher(dto.getFirstname(), dto.getLastname(), dto.getVat(), dto.getEmail(), null, null, null, dto.getId(), city);
    }

    public static TeacherReadOnlyDTO mapToTeacherReadOnlyDTO(Teacher teacher) {
        return new TeacherReadOnlyDTO(teacher.getId(), teacher.getUuid(), teacher.getCreatedAt(), teacher.getUpdatedAt(), teacher.getFirstname(), teacher.getLastname(), teacher.getVat(), teacher.getEmail(), teacher.getCity().getId());
    }

    public static List<TeacherReadOnlyDTO> teachersToReadOnlyDTOs(List<Teacher> teachers) {
        return teachers.stream()
                .map(Mapper::mapToTeacherReadOnlyDTO)
                .collect(Collectors.toList());
    }

    public static Map<String , Object> mapToCriteria(TeacherFiltersDTO filtersDTO) {
        Map<String , Object> filters = new HashMap<>();

        if (filtersDTO.getFirstname() != null && !filtersDTO.getFirstname().isEmpty()) {
            filters.put("firstname", filtersDTO.getFirstname());
        }

        if (filtersDTO.getLastname() != null && !filtersDTO.getLastname().isEmpty()) {
            filters.put("lastname", filtersDTO.getLastname());
        }
        return filters;
    }

    public static User mapToUser(UserInsertDTO dto) {
        return new User(null, dto.getFirstname(), dto.getLastname(), dto.getUsername(), SecUtil.hashPassword(dto.getPassword()), true,
                Role.valueOf(dto.getRole()));
    }

    public static UserReadOnlyDTO mapToUserReadOnlyDTO(User user) {
        return new UserReadOnlyDTO(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getPassword(),
                user.getRole().name());
    }
}
