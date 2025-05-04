package main.java_entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "web_section", uniqueConstraints = @UniqueConstraint(columnNames = "sec_name"))
@Getter
@Setter
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sec_name", nullable = false)
    private String name;
}