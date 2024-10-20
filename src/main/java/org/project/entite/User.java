package org.project.entite;

import jakarta.persistence.*;
import lombok.Data;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private TypeRole role;

    private int Jeton_Monsuel = 2;
    private int Jeton_Annuel = 1;


    public User() {}


}