package main;

import main.configs.HibernateConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import main.java_entities.Message;
import main.java_entities.Section;
import main.java_entities.Theme;
import main.java_entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import main.DAO.UserDAO;

@SpringBootTest
@Transactional
@ContextConfiguration(classes = HibernateConfig.class)
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testGetAllUserByLogin_UsersExist() {
        // Arrange
        String userName = "ser1";
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("$2a$10$Fg/aGg6tqN/jSh9qexl.5.loTuNwNrE8YimNMXfYSI6j20WtrhCkS");
        user1.setRegistrationDate(LocalDate.of(2024, 2, 1));
        user1.setRights(1);

        User user10 = new User();
        user10.setLogin("user10");
        user10.setPassword("ilovepostgres");
        user10.setRegistrationDate(LocalDate.of(2024, 2, 12));
        user10.setRights(1);

        User user11 = new User();
        user11.setLogin("user11");
        user11.setPassword("xyz987");
        user11.setRegistrationDate(LocalDate.of(2024, 2, 13));
        user11.setRights(1);

        User user12 = new User();
        user12.setLogin("user12");
        user12.setPassword("postgresql!");
        user12.setRegistrationDate(LocalDate.of(2024, 2, 15));
        user12.setRights(1);

        User user13 = new User();
        user13.setLogin("user13");
        user13.setPassword("randompass");
        user13.setRegistrationDate(LocalDate.of(2024, 2, 16));
        user13.setRights(1);
        List<User> expectedUsers = Arrays.asList(user1, user10, user11, user12, user13);

        // Act
        List<User> actualUsers = userDAO.getAllUserByLogin(userName);

        // Assert
        assertNotNull(actualUsers);
        assertEquals(expectedUsers.size(), actualUsers.size());
    }

    @Test
    public void testGetAllUserByName_UsersExist_FullMatch() {
        // Arrange
        String userName = "user7";

        User user1 = new User();
        user1.setLogin("user7");
        user1.setPassword("password1");
        user1.setRegistrationDate(LocalDate.of(2024, 2, 8));
        user1.setRights(1);

        List<User> expectedUsers = List.of(user1);

        // Act
        List<User> actualUsers = userDAO.getAllUserByLogin(userName);

        // Assert
        assertNotNull(actualUsers);
        assertEquals(expectedUsers.size(), actualUsers.size());
    }

    @Test
    void testGetAllUserByName_NoUsersExist() {
        // Arrange
        String userName = "new_user";

        // Act
        List<User> actualUsers = userDAO.getAllUserByLogin(userName);

        // Assert
        assertTrue(actualUsers.isEmpty());
    }

    @Test
    public void testGetAllUserBySection_UsersExist_OneSection() {
        // Arrange
        Section section1 = new Section();
        section1.setId(1L);
        section1.setName("Технологии");

        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("$2a$10$Fg/aGg6tqN/jSh9qexl.5.loTuNwNrE8YimNMXfYSI6j20WtrhCkS");
        user1.setRegistrationDate(LocalDate.of(2024, 2, 1));
        user1.setRights(1);

        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("secure456");
        user2.setRegistrationDate(LocalDate.of(2024, 2, 2));
        user2.setRights(1);

        User user3 = new User();
        user3.setLogin("user3");
        user3.setPassword("mypassword");
        user3.setRegistrationDate(LocalDate.of(2024, 2, 3));
        user3.setRights(1);

        User user13 = new User();
        user13.setLogin("user13");
        user13.setPassword("randompass");
        user13.setRegistrationDate(LocalDate.of(2024, 2, 16));
        user13.setRights(1);

        List<User> expectedUsers = Arrays.asList(user1, user2, user3, user13);

        // Act
        List<User> actualUsers = userDAO.getAllUserBySection(List.of(section1));

        // Assert
        assertNotNull(actualUsers);
        assertEquals(expectedUsers.size(), actualUsers.size());
    }

    @Test
    public void testGetAllUserBySection_UsersExist_MoreSections() {
        // Arrange
        Section section1 = new Section();
        section1.setId(2L);
        section1.setName("Природа");

        Section section2 = new Section();
        section2.setId(3L);
        section2.setName("Путешествия");

        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("secure456");
        user2.setRegistrationDate(LocalDate.of(2024, 2, 2));
        user2.setRights(1);

        User user3 = new User();
        user3.setLogin("user3");
        user3.setPassword("mypassword");
        user3.setRegistrationDate(LocalDate.of(2024, 2, 3));
        user3.setRights(1);

        User user4 = new User();
        user4.setLogin("user4");
        user4.setPassword("letmein");
        user4.setRegistrationDate(LocalDate.of(2024, 2, 4));
        user4.setRights(1);

        User user5 = new User();
        user5.setLogin("user5");
        user5.setPassword("qwerty");
        user5.setRegistrationDate(LocalDate.of(2024, 2, 5));
        user5.setRights(1);

        User user6 = new User();
        user6.setLogin("user6");
        user6.setPassword("hello123");
        user6.setRegistrationDate(LocalDate.of(2024, 2, 7));
        user6.setRights(1);

        User user7 = new User();
        user7.setLogin("user7");
        user7.setPassword("password1");
        user7.setRegistrationDate(LocalDate.of(2024, 2, 8));
        user7.setRights(1);

        User user8 = new User();
        user8.setLogin("user8");
        user8.setPassword("123abc");
        user8.setRegistrationDate(LocalDate.of(2024, 2, 9));
        user8.setRights(1);

        List<User> expectedUsers = Arrays.asList(user2, user3, user4, user5, user6, user7, user8);

        // Act
        List<User> actualUsers = userDAO.getAllUserBySection(Arrays.asList(section1, section2));

        // Assert
        assertNotNull(actualUsers);
        assertEquals(expectedUsers.size(), actualUsers.size());
    }

    @Test
    void testGetAllUserBySection_NoUsersExist() {
        // Arrange
        Section section1 = new Section();
        section1.setId(6L);
        section1.setName("Творчество");

        // Act
        List<User> actualUsers = userDAO.getAllUserBySection(List.of(section1));

        // Assert
        assertTrue(actualUsers.isEmpty());
    }

    @Test
    public void testGetAllUserByPeriod_UsersExist() {
        // Arrange
        LocalDateTime startTime = LocalDateTime.of(2022, 8, 30,  23, 59);
        LocalDateTime finishTime = LocalDateTime.of(2024, 2, 17, 11, 15);

        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("$2a$10$Fg/aGg6tqN/jSh9qexl.5.loTuNwNrE8YimNMXfYSI6j20WtrhCkS");
        user1.setRegistrationDate(LocalDate.of(2024, 2, 1));
        user1.setRights(1);

        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("secure456");
        user2.setRegistrationDate(LocalDate.of(2024, 2, 2));
        user2.setRights(1);

        User user3 = new User();
        user3.setLogin("user3");
        user3.setPassword("mypassword");
        user3.setRegistrationDate(LocalDate.of(2024, 2, 3));
        user3.setRights(1);

        Section section1 = new Section();
        section1.setId(4L);
        section1.setName("Семья");

        Theme theme1 = new Theme();
        theme1.setId(10L);
        theme1.setName("Воспитание детей");
        theme1.setSection(section1);

        Message message1 = new Message();
        message1.setId(21L);
        message1.setText("Новая тема");
        message1.setUser(user3);
        message1.setTheme(theme1);
        message1.setHeader("Советы родителям");
        message1.setReceipt(LocalDateTime.of(2024, 2, 17, 10, 15));

        Object[] result1 = new Object[]{user3, 2L};
        Object[] result2 = new Object[]{user2, 1L};
        Object[] result3 = new Object[]{user1, 1L};

        List<Object[]> expectedResults = Arrays.asList(result1, result2, result3);

        // Act
        List<Object[]> actualResults = userDAO.getAllUserByPeriod(startTime, finishTime);

        // Assert
        assertNotNull(actualResults);
        assertEquals(expectedResults.size(), actualResults.size());
    }

    @Test
    void testGetAllUserByPeriod_NoUsersExist() {
        // Arrange
        LocalDateTime startTime = LocalDateTime.of(2025, 2, 17, 11, 15);
        LocalDateTime finishTime = LocalDateTime.of(2029, 8, 30,  23, 59);

        // Act
        List<Object[]> actualResults = userDAO.getAllUserByPeriod(startTime, finishTime);

        // Assert
        assertTrue(actualResults.isEmpty());
    }
}