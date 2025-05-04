package main;

import main.configs.HibernateConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import main.java_entities.Section;

import java.util.Arrays;
import java.util.List;

import main.DAO.SectionDAO;

@SpringBootTest
@Transactional
@ContextConfiguration(classes = HibernateConfig.class)
public class SectionDAOTest {

    @Autowired
    private SectionDAO sectionDAO;

    @Test
    public void testGetAllSectionByName_SectionsExist() {
        // Arrange
        String sectionName = "П";

        Section section1 = new Section();
        section1.setId(2L);
        section1.setName("Природа");

        Section section2 = new Section();
        section2.setId(3L);
        section2.setName("Путешествия");

        List<Section> expectedSections = Arrays.asList(section1, section2);

        // Act
        List<Section> actualSections = sectionDAO.getAllSectionByName(sectionName);

        // Assert
        assertNotNull(actualSections);
        assertEquals(expectedSections.size(), actualSections.size());
    }

    @Test
    public void testGetAllSectionByName_SectionsExist_FullMatch() {
        // Arrange
        String sectionName = "Семья";

        Section section1 = new Section();
        section1.setId(4L);
        section1.setName("Семья");

        List<Section> expectedSections = List.of(section1);

        // Act
        List<Section> actualSections = sectionDAO.getAllSectionByName(sectionName);

        // Assert
        assertNotNull(actualSections);
        assertEquals(expectedSections.size(), actualSections.size());
    }

    @Test
    void testGetAllSectionByName_NoSectionsExist() {
        // Arrange
        String sectionName = "Творчество";

        // Act
        List<Section> actualSections = sectionDAO.getAllSectionByName(sectionName);

        // Assert
        assertTrue(actualSections.isEmpty());
    }
}