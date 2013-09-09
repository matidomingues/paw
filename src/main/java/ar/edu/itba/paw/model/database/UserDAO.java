package ar.edu.itba.paw.model.database;

import ar.edu.itba.paw.model.User;

import java.util.Set;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: facundo
 * Date: 07/09/13
 * Time: 20:04
 * To change this template use File | Settings | File Templates.
 */
public interface UserDAO {
    User getUserByUsername(String username);

    boolean registerUser(String username, String password, String name,
                         String surname, String description);

    Set<User> find(String username);

    Set<User> getAll();

    boolean updateUser(User user);
}
