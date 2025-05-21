package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.Student;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class StudentDAOImpl extends AbstractDAO<Student> implements IStudentDAO {
    public StudentDAOImpl() {
        this.setPersistenceClass(Student.class);
    }

    @Override
    public void deleteByUuid(String uuid) {
        EntityManager em = getEntityManager();
        getByField("uuid", uuid).ifPresent(em::remove);
    }

}
