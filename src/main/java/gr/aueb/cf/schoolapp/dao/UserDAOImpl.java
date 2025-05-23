package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.security.SecUtil;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UserDAOImpl extends AbstractDAO<User> implements IUserDAO {

    public UserDAOImpl() {
        this.setPersistenceClass(User.class);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return getByField("username", username);
    }

    @Override
    public boolean isUserValid(String username, String password) {
        boolean isValid = false;
        Optional<User> user = getByUsername(username);
        if (user.isPresent()) {
            isValid = SecUtil.verifyPassword(password, user.get().getPassword());
        }
        return isValid;
    }

    @Override
    public boolean isEmailExists(String username) {
        boolean emailExists = false;
        Optional<User> user = getByUsername(username);
        if (user.isPresent()) {
            emailExists = true;
        }
        return emailExists;
    }
}
