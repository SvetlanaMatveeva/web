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

import main.DAO.MessageDAO;

@SpringBootTest
@Transactional
@ContextConfiguration(classes = HibernateConfig.class)
public class MessageDAOTest {

    @Autowired
    private MessageDAO messageDAO;

    @Test
    public void testGetAllMessageByTime_MessagesExist() {
        // Arrange
        Section section1 = new Section();
        section1.setId(4L);
        section1.setName("Семья");

        Theme theme1 = new Theme();
        theme1.setId(10L);
        theme1.setName("Воспитание детей");
        theme1.setSection(section1);

        User user4 = new User();
        user4.setLogin("user4");
        user4.setPassword("letmein");
        user4.setRegistrationDate(LocalDate.of(2024, 2, 4));
        user4.setRights(1);

        User user6 = new User();
        user6.setLogin("user6");
        user6.setPassword("hello123");
        user6.setRegistrationDate(LocalDate.of(2024, 2, 7));
        user6.setRights(1);

        Message message1 = new Message();
        message1.setId(10L);
        message1.setText("Как воспитывать ребенка?");
        message1.setUser(user6);
        message1.setTheme(theme1);
        message1.setHeader("Советы родителям");
        message1.setReceipt(LocalDateTime.of(2024, 2, 17, 14, 30));

        Message message2 = new Message();
        message2.setId(20L);
        message2.setText("Как воспитание влияет на личность?");
        message2.setUser(user4);
        message2.setTheme(theme1);
        message2.setHeader("Психология детства");
        message2.setReceipt(LocalDateTime.of(2024, 2, 17, 19, 30));

        List<Message> expectedMessages = Arrays.asList(message1, message2);

        // Act
        List<Message> actualMessages = messageDAO.getAllMessageByTime(theme1);

        // Assert
        assertNotNull(actualMessages);
        assertEquals(expectedMessages.size(), actualMessages.size());
    }

    @Test
    void testGetAllMessageByTime_NoMessagesExistInTheme() {
        // Arrange
        Section section1 = new Section();
        section1.setId(5L);
        section1.setName("Наука");

        Theme theme1 = new Theme();
        theme1.setId(16L);
        theme1.setName("Научные открытия");
        theme1.setSection(section1);

        // Act
        List<Message> actualMessages = messageDAO.getAllMessageByTime(theme1);

        // Assert
        assertTrue(actualMessages.isEmpty());
    }
}