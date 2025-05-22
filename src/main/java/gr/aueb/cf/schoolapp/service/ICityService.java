package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.core.exceptions.EntityAlreadyExistsException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.dto.CityInsertDTO;
import gr.aueb.cf.schoolapp.dto.CityReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.CityUpdateDTO;


import java.util.List;
import java.util.Map;


public interface ICityService {
    List<CityReadOnlyDTO> getAllCities();
    CityReadOnlyDTO insertCity(CityInsertDTO insertDTO) throws EntityAlreadyExistsException, EntityInvalidArgumentException;
    CityReadOnlyDTO updateCity(CityUpdateDTO updateDTO) throws EntityNotFoundException, EntityInvalidArgumentException, EntityAlreadyExistsException;
    void deleteCity(Integer id) throws EntityNotFoundException;
    void deleteCity(String uuid) throws EntityNotFoundException, EntityInvalidArgumentException;
    CityReadOnlyDTO getCityById(Integer id) throws EntityNotFoundException;
    CityReadOnlyDTO getCityByUuid(String uuid) throws EntityNotFoundException;
    long getCitiesCountByCriteria(Map<String, Object> criteria);
    List<CityReadOnlyDTO> getCitiesByCriteria(Map<String, Object> criteria);
    List<CityReadOnlyDTO> getCitiesByCriteriaPaginated(Map<String, Object> criteria, Integer page, Integer size);
}
