package org.project.entite;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.project.Enum.TypeRole;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "mot_de_pass", nullable = false)
    private String mot_de_pass;
    @Column(name = "role", nullable = false)
    private TypeRole role;

    private int Jeton_Monsuel = 2;
    private int Jeton_Annuel = 1;


    public User() {}


}