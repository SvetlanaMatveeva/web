package web_forum.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import web_forum.classes.Section;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;

import web_forum.DAO.Abstract.SectionAbstractDAO;

@ExtendWith(MockitoExtension.class)
public class SectionDAOTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Section> query;

    @InjectMocks
    private SectionAbstractDAO sectionAbstractDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        doNothing().when(session).close();
    }

    @Test
    public void testGetAllSectionByName_SectionsExist() {
        // Arrange
        String sectionName = "П";
        Section section1 = new Section(2L, "Природа");
        Section section2 = new Section(3L, "Путешествия");

        List<Section> expectedSections = Arrays.asList(section1, section2);

        when(session.createQuery(anyString(), eq(Section.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedSections);

        // Act
        List<Section> actualSections = sectionAbstractDAO.getAllSectionByName(sectionName);

        // Assert
        assertNotNull(actualSections);
        assertEquals(expectedSections.size(), actualSections.size());
        assertTrue(actualSections.containsAll(expectedSections));
        assertTrue(expectedSections.containsAll(actualSections));
        verify(session, times(1)).createQuery(anyString(), eq(Section.class));
        verify(query, times(1)).setParameter(anyString(), anyString());
        verify(query, times(1)).getResultList();
        verify(session, times(1)).close();
    }

    @Test
    public void testGetAllSectionByName_SectionsExist_FullMatch() {
        // Arrange
        String sectionName = "Cемья";
        Section section1 = new Section(4L, "Cемья");

        List<Section> expectedSections = List.of(section1);

        when(session.createQuery(anyString(), eq(Section.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedSections);

        // Act
        List<Section> actualSections = sectionAbstractDAO.getAllSectionByName(sectionName);

        // Assert
        assertNotNull(actualSections);
        assertEquals(expectedSections.size(), actualSections.size());
        assertTrue(actualSections.containsAll(expectedSections));
        assertTrue(expectedSections.containsAll(actualSections));
        verify(session, times(1)).createQuery(anyString(), eq(Section.class));
        verify(query, times(1)).setParameter(anyString(), anyString());
        verify(query, times(1)).getResultList();
        verify(session, times(1)).close();
    }

    @Test
    void testGetAllSectionByName_NoSectionsExist() {
        // Arrange
        String sectionName = "Творчество";

        when(session.createQuery(anyString(), eq(Section.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<Section> actualSections = sectionAbstractDAO.getAllSectionByName(sectionName);

        // Assert
        assertTrue(actualSections.isEmpty());
        verify(session, times(1)).createQuery(anyString(), eq(Section.class));
        verify(query, times(1)).setParameter(anyString(), anyString());
        verify(query, times(1)).getResultList();
        verify(session, times(1)).close();
    }
}