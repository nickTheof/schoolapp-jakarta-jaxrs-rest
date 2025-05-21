package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.dao.ICityDAO;
import gr.aueb.cf.schoolapp.dto.CityInsertDTO;
import gr.aueb.cf.schoolapp.dto.CityReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.CityUpdateDTO;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.City;
import gr.aueb.cf.schoolapp.service.util.JPAHelper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class CityServiceImpl implements ICityService{
    private final ICityDAO cityDAO;
    private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);

    @Override
    public List<CityReadOnlyDTO> getAllCities() {
        try {
            JPAHelper.startTransaction();
            List<CityReadOnlyDTO> cityReadOnlyDTOS = Mapper.mapToCityReadOnlyDTOs(cityDAO.getAll());
            JPAHelper.commitTransaction();
            return cityReadOnlyDTOS;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public CityReadOnlyDTO insertCity(CityInsertDTO insertDTO) throws EntityAlreadyExistsException, EntityInvalidArgumentException {
       try {
           JPAHelper.startTransaction();
           City toInsert = Mapper.mapToCity(insertDTO);
           if (cityDAO.getByField("name", insertDTO.name()).isPresent()) {
               throw new EntityAlreadyExistsException("City", "City with name " + insertDTO.name() + " already exists");
           }
           CityReadOnlyDTO cityReadOnlyDTO = cityDAO.insert(toInsert)
                   .map(Mapper::mapToCityReadOnlyDTO)
                   .orElseThrow(() -> new EntityInvalidArgumentException("City", "City with name " + insertDTO.name() + " was not inserted"));
           JPAHelper.commitTransaction();
           LOGGER.info("City with id={}, uuid={}, name={} was inserted successfully", cityReadOnlyDTO.id(), cityReadOnlyDTO.uuid(), cityReadOnlyDTO.name());
           return cityReadOnlyDTO;
       } catch (EntityAlreadyExistsException | EntityInvalidArgumentException e) {
           JPAHelper.rollbackTransaction();
           LOGGER.error("Fail to insert city with name = {} Reason ={}.", insertDTO.name(), e.getCause(), e);
           throw e;
       } finally {
           JPAHelper.closeEntityManager();
       }
    }

    @Override
    public CityReadOnlyDTO updateCity(CityUpdateDTO updateDTO) throws EntityNotFoundException, EntityInvalidArgumentException {
        try {
            JPAHelper.startTransaction();
            City toUpdate = Mapper.mapToCity(updateDTO);
            cityDAO.getById(toUpdate.getId()).orElseThrow(() -> new EntityNotFoundException("City", "City with id " + toUpdate.getId() + " was not found"));
            CityReadOnlyDTO readOnlyDTO = cityDAO.update(toUpdate).map(Mapper::mapToCityReadOnlyDTO).orElseThrow(() -> new EntityInvalidArgumentException("City", "City with id " + toUpdate.getId() + " was not updated. Error during update"));
            JPAHelper.commitTransaction();
            LOGGER.info("City with id={}, name={} updated successfully", readOnlyDTO.id(), readOnlyDTO.name());
            return readOnlyDTO;
        } catch (EntityNotFoundException | EntityInvalidArgumentException e) {
            LOGGER.warn("Update error. City with id={} was not updated", updateDTO.id(), e);
            JPAHelper.rollbackTransaction();
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public void deleteCity(Integer id) throws EntityNotFoundException {
        try{
            JPAHelper.startTransaction();
            cityDAO.getById(id).orElseThrow(() -> new EntityNotFoundException("City", "City with id " + id + " was not found"));
            cityDAO.delete(id);
            JPAHelper.commitTransaction();
            LOGGER.info("Teacher with id={} was deleted.", id);
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            LOGGER.error("Teacher with id={} was not deleted", id, e);
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public CityReadOnlyDTO getCityById(Integer id) throws EntityNotFoundException {
        try {
            JPAHelper.startTransaction();
            CityReadOnlyDTO cityReadOnlyDTO = cityDAO.getById(id).map(Mapper::mapToCityReadOnlyDTO).orElseThrow(()-> new EntityNotFoundException("City", "City with id " + id + " not found"));
            JPAHelper.commitTransaction();
            LOGGER.info("City with id={} was found", id);
            return cityReadOnlyDTO;
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Warning. City with id={} was not found", id, e);
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public CityReadOnlyDTO getCityByUuid(String uuid) throws EntityNotFoundException {
        try {
          JPAHelper.startTransaction();
          CityReadOnlyDTO cityReadOnlyDTO = cityDAO.getByField("uuid", uuid).map(Mapper::mapToCityReadOnlyDTO).orElseThrow(()-> new EntityNotFoundException("City", "City with uuid " + uuid + " not found"));
          JPAHelper.commitTransaction();
          LOGGER.info("City with uuid={} was found.", uuid);
          return cityReadOnlyDTO;
        } catch (EntityNotFoundException e) {
            LOGGER.warn("City with uuid={} was not found", uuid, e);
            throw e;
        } finally {
            JPAHelper.rollbackTransaction();
        }
    }

    @Override
    public long getCitiesCountByCriteria(Map<String, Object> criteria) {
        try {
            JPAHelper.startTransaction();
            long count = cityDAO.countByCriteria(criteria);
            JPAHelper.commitTransaction();
            return count;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<CityReadOnlyDTO> getCitiesByCriteria(Map<String, Object> criteria) {
        try {
            JPAHelper.startTransaction();
            List<CityReadOnlyDTO> cityReadOnlyDTOS = Mapper.mapToCityReadOnlyDTOs(cityDAO.getByCriteria(criteria));
            JPAHelper.commitTransaction();
            return cityReadOnlyDTOS;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public List<CityReadOnlyDTO> getCitiesByCriteriaPaginated(Map<String, Object> criteria, Integer page, Integer size) {
        try {
            JPAHelper.startTransaction();
            List<CityReadOnlyDTO> readOnlyDTOS = Mapper.mapToCityReadOnlyDTOs(cityDAO.getByCriteriaPaginated(City.class, criteria, page, size));
            JPAHelper.commitTransaction();
            return readOnlyDTOS;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }
}
