package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.Teacher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;


@ApplicationScoped
public class TeacherDAOImpl extends AbstractDAO<Teacher> implements ITeacherDAO {
    public TeacherDAOImpl() {
        this.setPersistenceClass(Teacher.class);
    }

    @Override
    public void deleteByUuid(String uuid) {
        EntityManager em = getEntityManager();
        getByField("uuid", uuid).ifPresent(em::remove);
    }
}
