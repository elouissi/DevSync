package org.project.entite;

import jakarta.persistence.*;
import lombok.Data;

import org.project.Enum.TypeStatus;

import java.time.LocalDate;

@Entity
@Table(name = "tasks", schema = "public")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "titre", nullable = false)
    private String titre;
    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TypeStatus status;

    @ManyToOne
    @JoinColumn(name = "created_user_id", nullable = false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedTo;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;


}
