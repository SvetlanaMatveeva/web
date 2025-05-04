package main.java_entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "web_user")
@Getter
@Setter
public class User {
    @Id
    private String login;

    @Column(name = "acc_password", nullable = false)
    private String password;

    @Column(name = "registration_date", nullable = false, columnDefinition = "DATE DEFAULT current_date")
    private LocalDate registrationDate = LocalDate.now();

    @Column(name = "rights", nullable = false, columnDefinition = "INT DEFAULT 1")
    private Integer rights;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login);
    }
}
