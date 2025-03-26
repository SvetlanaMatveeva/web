package web_forum.classes;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "web_section", uniqueConstraints = @UniqueConstraint(columnNames = "sec_name"))
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Section implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "sec_name", nullable = false)
    private String name;
}