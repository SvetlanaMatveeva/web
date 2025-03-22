package web_forum.classes;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "web_file", uniqueConstraints = @UniqueConstraint(columnNames = {"mes_id", "save_path"}))
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class File implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "save_path", nullable = false)
    private String savePath;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "mes_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Message message;
}