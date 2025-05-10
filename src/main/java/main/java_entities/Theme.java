package main.java_entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "web_theme", uniqueConstraints = @UniqueConstraint(columnNames = {"sec_id", "th_name"}))
@Getter
@Setter
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "th_name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "sec_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Section section;
}