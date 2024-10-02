package org.project.service;

import org.project.entite.User;
import org.project.repositorie.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public void createUser(User user) {
        userRepository.createUser(user);
    }
    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }
    public User getUserById(int id){
      return   userRepository.getUserById(id);
    }
}
