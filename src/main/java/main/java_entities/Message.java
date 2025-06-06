package main.java_entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "web_message")
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mes_text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User user;

    @ManyToOne
    @JoinColumn(name = "theme_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Theme theme;

    @Column(name = "mes_header", nullable = false)
    private String header;

    @Column(name = "receipt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT current_timestamp")
    private LocalDateTime receipt = LocalDateTime.now();
}
