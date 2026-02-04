package org.example.repository;

import org.example.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public Optional<User> findById(String uuid){
        if (uuid == null){
            throw new IllegalArgumentException("UUID cannot be null.");
        }
        for (User user : users ){
            if(user.getId().equals(uuid.strip())){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
    public void save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        String userId = user.getId().strip();
        for (User existing : users) {
            if (existing.getId().strip().equals(userId)) {
                throw new IllegalArgumentException("User with id " + userId + " already exists.");
            }
        }
        users.add(user);
    }

    public void deleteById(String uuid){
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        users.removeIf(u -> u.getId().equals(uuid.strip()));
    }

    public List<User> getAll(){
        return new ArrayList<>(users);
    }
}
