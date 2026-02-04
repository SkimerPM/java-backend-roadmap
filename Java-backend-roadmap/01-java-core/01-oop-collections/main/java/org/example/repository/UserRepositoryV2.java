package org.example.repository;
import org.example.entities.User;

import java.util.*;


public class UserRepositoryV2 {
    private final Map<String, User> usersById = new HashMap<>();

    public void save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (usersById.containsKey(user.getId())) {
            throw new IllegalArgumentException("User with id " + user.getId() + " already exists");
        }
        usersById.put(user.getId(), user);
    }

    public Optional<User> findById(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        return Optional.ofNullable(usersById.get(userId.strip()));
    }

    public List<User> findAll() {
        return new ArrayList<>(usersById.values());
    }

    public void deleteById(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        usersById.remove(userId.strip());
    }
}
