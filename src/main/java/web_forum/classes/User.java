package web_forum.classes;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "web_user")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User implements CommonEntity<String> {
    @Id
    @NotNull
    private String login;

    @NotNull
    @Column(name = "acc_password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "registration_date", nullable = false, columnDefinition = "DATE DEFAULT current_date")
    private LocalDate registrationDate = LocalDate.now();

    @NotNull
    @Column(name = "rights", nullable = false, columnDefinition = "INT DEFAULT 1")
    @Min(0)
    @Max(1)
    private Integer rights;

    @Override
    public String getId() {
        return login;
    }

    @Override
    public void setId(String id) {
        this.login = id;
    }
}
