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

    UUID authenticate(String username, String password);

    boolean registerUser(String username, String password, String name,
                         String surname, String description);

    User getUserBySession(UUID uuid);

    Set<User> find(String username);

    Set<User> getAll();

    void updateUser(User user);
}
