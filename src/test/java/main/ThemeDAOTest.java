package main;

import main.configs.HibernateConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import main.java_entities.Section;
import main.java_entities.Theme;

import java.util.Arrays;
import java.util.List;

import main.DAO.ThemeDAO;

@SpringBootTest
@Transactional
@ContextConfiguration(classes = HibernateConfig.class)
public class ThemeDAOTest {

    @Autowired
    private ThemeDAO themeDAO;

    @Test
    public void testGetAllThemeByName_ThemesExist() {
        // Arrange
        String themeName = "И";

        Section section1 = new Section();
        section1.setId(1L);
        section1.setName("Технологии");

        Theme theme1 = new Theme();
        theme1.setId(1L);
        theme1.setName("Искусственный интеллект");
        theme1.setSection(section1);

        Theme theme2 = new Theme();
        theme2.setId(3L);
        theme2.setName("Инновационные гаджеты");
        theme2.setSection(section1);

        List<Theme> expectedThemes = Arrays.asList(theme1, theme2);

        // Act
        List<Theme> actualThemes = themeDAO.getAllThemeByName(section1, themeName);

        // Assert
        assertNotNull(actualThemes);
        assertEquals(expectedThemes.size(), actualThemes.size());
    }

    @Test
    public void testGetAllThemeByName_ThemesExist_FullMatch() {
        // Arrange
        String themeName = "Жизнь дикой природы";

        Section section1 = new Section();
        section1.setId(2L);
        section1.setName("Природа");

        Theme theme1 = new Theme();
        theme1.setId(5L);
        theme1.setName("Жизнь дикой природы");
        theme1.setSection(section1);

        List<Theme> expectedThemes = List.of(theme1);

        // Act
        List<Theme> actualThemes = themeDAO.getAllThemeByName(section1, themeName);

        // Assert
        assertNotNull(actualThemes);
        assertEquals(expectedThemes.size(), actualThemes.size());
    }

    @Test
    void testGetAllThemeByName_NoThemesExistInSection() {
        // Arrange
        String themeName = "Генная инженерия";

        Section section1 = new Section();
        section1.setId(4L);
        section1.setName("Семья");

        // Act
        List<Theme> actualThemes = themeDAO.getAllThemeByName(section1, themeName);

        // Assert
        assertTrue(actualThemes.isEmpty());
    }

    @Test
    void testGetAllThemeByName_NoThemesExist() {
        // Arrange
        String themeName = "Семейный отдых";

        Section section1 = new Section();
        section1.setId(4L);
        section1.setName("Семья");

        // Act
        List<Theme> actualThemes = themeDAO.getAllThemeByName(section1, themeName);

        // Assert
        assertTrue(actualThemes.isEmpty());
    }
}