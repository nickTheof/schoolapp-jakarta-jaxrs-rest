package gr.aueb.cf.schoolapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class City extends AbstractEntity implements IdentifiableEntity{
    public City(Integer id, String uuid, String name) {
        super(uuid, null, null);
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Getter(AccessLevel.PROTECTED)
    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private Set<Teacher> teachers = new HashSet<>();

    @Getter(AccessLevel.PROTECTED)
    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();


    public Set<Teacher> getAllTeachers() {
        return Collections.unmodifiableSet(teachers);
    }

    public Set<Student> getAllStudents() {
        return Collections.unmodifiableSet(students);
    }

    public void addTeacher(Teacher teacher) {
        if (teacher == null) return;
        teachers.add(teacher);
        teacher.setCity(this);
    }
    public void removeTeacher(Teacher teacher) {
        if (teacher == null || teachers.isEmpty()) return;
        teachers.remove(teacher);
        teacher.setCity(null);
    }

    public void addStudent(Student student) {
        if (student == null) return;
        students.add(student);
        student.setCity(this);
    }


    public void removeStudent(Student student) {
        if (student == null || students.isEmpty()) return;
        students.remove(student);
        student.setCity(null);
    }
}
