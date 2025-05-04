package main;

import main.configs.HibernateConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import main.java_entities.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import main.DAO.FileDAO;

@SpringBootTest
@Transactional
@ContextConfiguration(classes = HibernateConfig.class)
public class FileDAOTest {

    @Autowired
    private FileDAO fileDAO;

    @Test
    public void testGetAllFileByTime_FilesExist() {
        // Arrange
        Section section1 = new Section();
        section1.setId(3L);
        section1.setName("Путешествия");

        Theme theme1 = new Theme();
        theme1.setId(9L);
        theme1.setName("Культура и традиции стран");
        theme1.setSection(section1);

        User user8 = new User();
        user8.setLogin("user4");
        user8.setPassword("letmein");
        user8.setRegistrationDate(LocalDate.of(2024, 2, 4));
        user8.setRights(1);

        Message message1 = new Message();
        message1.setId(9L);
        message1.setText("Культурные различия стран");
        message1.setUser(user8);
        message1.setTheme(theme1);
        message1.setHeader("Интересные факты");
        message1.setReceipt(LocalDateTime.of(2024, 2, 17, 14, 0));

        File file1 = new File();
        file1.setId(5L);
        file1.setSavePath("/uploads/files/manual.pdf");
        //System.getProperty("user.dir") + "/uploads/files/manual.pdf"
        file1.setMessage(message1);

        List<File> expectedFiles = List.of(file1);

        // Act
        List<File> actualFiles = fileDAO.getAllFileByMessage(message1);

        // Assert
        assertNotNull(actualFiles);
        assertEquals(expectedFiles.size(), actualFiles.size());
    }

    @Test
    void testGetAllFileByTime_NoFilesExistInMessage() {
        // Arrange
        Section section1 = new Section();
        section1.setId(5L);
        section1.setName("Наука");

        Theme theme1 = new Theme();
        theme1.setId(14L);
        theme1.setName("Космические исследования");
        theme1.setSection(section1);

        User user7 = new User();
        user7.setLogin("user7");
        user7.setPassword("password1");
        user7.setRegistrationDate(LocalDate.of(2024, 2, 8));
        user7.setRights(1);

        Message message1 = new Message();
        message1.setId(14L);
        message1.setText("Исследование космоса");
        message1.setUser(user7);
        message1.setTheme(theme1);
        message1.setHeader("Будущее астрономии");
        message1.setReceipt(LocalDateTime.of(2024, 2, 17, 16, 30));

        // Act
        List<File> actualFiles = fileDAO.getAllFileByMessage(message1);

        // Assert
        assertTrue(actualFiles.isEmpty());
    }
}