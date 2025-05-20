package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.Student;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StudentDAOImpl extends AbstractDAO<Student> implements IStudentDAO {
    public StudentDAOImpl() {
        this.setPersistenceClass(Student.class);
    }
}
