package org.project.entite;

import jakarta.persistence.*;
import lombok.Data;
import org.project.Enum.TypeRequest;

@Entity
@Data
@Table(name = "requests", schema = "public")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private TypeRequest status;

    @ManyToOne(cascade = CascadeType.ALL)
    private Task task_id;
    @ManyToOne
    private User user_id;

}
