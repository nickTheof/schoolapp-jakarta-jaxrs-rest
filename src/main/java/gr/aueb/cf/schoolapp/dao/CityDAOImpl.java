package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.City;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class CityDAOImpl extends AbstractDAO<City> implements ICityDAO{
    public CityDAOImpl() {
        this.setPersistenceClass(City.class);
    }

    @Override
    public void deleteByUuid(String uuid) {
        EntityManager em = getEntityManager();
        getByField("uuid", uuid).ifPresent(em::remove);
    }
}
