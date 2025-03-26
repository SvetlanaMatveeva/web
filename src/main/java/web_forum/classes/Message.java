package web_forum.classes;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "web_message")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Message implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mes_text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "theme_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Theme theme;

    @NotNull
    @Column(name = "mes_header", nullable = false)
    private String header;

    @NotNull
    @Column(name = "receipt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT current_timestamp")
    private LocalDateTime receipt = LocalDateTime.now();
}
