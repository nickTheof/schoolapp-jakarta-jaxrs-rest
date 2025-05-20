package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.City;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CityDAOImpl extends AbstractDAO<City> implements ICityDAO{
    public CityDAOImpl() {
        this.setPersistenceClass(City.class);
    }
}
