package main.java_entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "web_file", uniqueConstraints = @UniqueConstraint(columnNames = {"mes_id", "save_path"}))
@Getter
@Setter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "save_path", nullable = false)
    private String savePath;

    @ManyToOne
    @JoinColumn(name = "mes_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Message message;
}