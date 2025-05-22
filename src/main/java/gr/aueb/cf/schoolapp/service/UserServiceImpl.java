package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.core.exceptions.AppServerException;
import gr.aueb.cf.schoolapp.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dto.UserInsertDTO;
import gr.aueb.cf.schoolapp.dto.UserReadOnlyDTO;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.util.JPAHelper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class UserServiceImpl implements IUserService{
    private final IUserDAO userDAO;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public UserReadOnlyDTO insertUser(UserInsertDTO dto) throws AppServerException {

        try {
            JPAHelper.startTransaction();
            User user = Mapper.mapToUser(dto);

            UserReadOnlyDTO readOnlyDTO = userDAO.insert(user)
                    .map(Mapper::mapToUserReadOnlyDTO)
                    .orElseThrow(() -> new AppServerException("User", "USer with username: " + dto.username() + "not inserted"));
            JPAHelper.commitTransaction();
            LOGGER.info("User with username= {} inserted", dto.username());
            return readOnlyDTO;
        } catch (AppServerException e) {
            JPAHelper.rollbackTransaction();
            LOGGER.error("User with username={} not inserted.", dto.username(), e);
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public UserReadOnlyDTO getUserByUsername(String username) throws EntityNotFoundException {
        try {
            JPAHelper.startTransaction();

            UserReadOnlyDTO readOnlyDTO = userDAO.getByUsername(username)
                    .map(Mapper::mapToUserReadOnlyDTO)
                    .orElseThrow(() -> new EntityNotFoundException("User", "User with username: " + username + "not found."));
            JPAHelper.commitTransaction();
            return readOnlyDTO;
        } catch (EntityNotFoundException e) {
            LOGGER.warn("User with username={} not found.", username, e);
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public boolean isUserValid(String username, String password) {
        try {
            JPAHelper.startTransaction();

            boolean isValid = userDAO.isUserValid(username, password);
            JPAHelper.commitTransaction();
            return isValid;
        }
        finally {
            JPAHelper.closeEntityManager();
        }
    }

    @Override
    public boolean isEmailExists(String username) {
        try {
            JPAHelper.startTransaction();

            boolean isEMailExists = userDAO.isEmailExists(username);
            JPAHelper.commitTransaction();
            return isEMailExists;
        }
        finally {
            JPAHelper.closeEntityManager();
        }
    }

}
