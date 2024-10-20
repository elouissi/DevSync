package org.project.service;

import org.project.entite.User;
import org.project.repositorie.UserRepository;
import org.project.Enum.TypeRole;
import org.project.Util.PasswordUtil;

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
        if (user != null && user.getEmail() != null && !user.getEmail().isEmpty()) {
            String hashedPassword = PasswordUtil.hashPassword(user.getMot_de_pass());
            user.setMot_de_pass(hashedPassword);
            userRepository.createUser(user);
        } else {
            throw new IllegalArgumentException("L'utilisateur ou l'email est invalide");
        }
    }

    public void deleteUser(Long id) {
        User user = userRepository.getUserById(id.intValue());
        if (user != null) {
            userRepository.deleteUser(id);
        } else {
            throw new IllegalArgumentException("Utilisateur introuvable");
        }
    }

    public void updateUser(User user) {
        if (user != null ) {
            User existingUser = userRepository.getUserById(user.getId());
            if (existingUser != null) {
                existingUser.setName(user.getName());
                existingUser.setEmail(user.getEmail());

                if (user.getMot_de_pass() != null && !user.getMot_de_pass().isEmpty()) {
                    existingUser.setMot_de_pass(PasswordUtil.hashPassword(user.getMot_de_pass()));
                }

                existingUser.setRole(user.getRole());
                userRepository.updateUser(existingUser);
            } else {
                throw new IllegalArgumentException("Utilisateur introuvable");
            }
        } else {
            throw new IllegalArgumentException("Données utilisateur invalides ou identifiant manquant");
        }
    }

    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }
}
