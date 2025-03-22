package web_forum.classes;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "web_theme", uniqueConstraints = @UniqueConstraint(columnNames = {"sec_id", "th_name"}))
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Theme implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "th_name", nullable = false)
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sec_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Section section;
}