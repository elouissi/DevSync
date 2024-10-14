package org.project.entite;

import jakarta.persistence.*;
import lombok.Data;
import org.project.Enum.TypeRequest;
import org.project.entite.Task;
import org.project.entite.User;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "requests" ,schema="public" )
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private TypeRequest status;

    @Column(name = "created_at", nullable = false)
    private LocalDate created_at;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
