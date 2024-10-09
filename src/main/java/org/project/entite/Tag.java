package org.project.entite;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tags", schema = "public")
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
//    @ManyToMany(mappedBy = "tags")
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Task> tasks;



}
