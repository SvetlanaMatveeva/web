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
import web_forum.classes.Theme;
import web_forum.classes.Section;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;


import web_forum.DAO.Abstract.ThemeAbstractDAO;

@ExtendWith(MockitoExtension.class)
public class ThemeDAOTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Theme> query;

    @InjectMocks
    private ThemeAbstractDAO themeAbstractDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        doNothing().when(session).close();
    }

    @Test
    public void testGetAllThemeByName_ThemesExist() {
        // Arrange
        String themeName = "Ин";
        Section section1 = new Section(1L, "Технологии");
        Theme theme1 = new Theme(1L, "Искусственный интеллект", section1);
        Theme theme2 = new Theme(3L, "Инновационные гаджеты", section1);

        List<Theme> expectedThemes = Arrays.asList(theme1, theme2);

        when(session.createQuery(anyString(), eq(Theme.class))).thenReturn(query);
        when(query.setParameter(eq("Section"), any(Section.class))).thenReturn(query);
        when(query.setParameter(eq("Name"), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedThemes);

        // Act
        List<Theme> actualThemes = themeAbstractDAO.getAllThemeByName(section1, themeName);

        // Assert
        assertNotNull(actualThemes);
        assertEquals(expectedThemes.size(), actualThemes.size());
        assertTrue(actualThemes.containsAll(expectedThemes));
        assertTrue(expectedThemes.containsAll(actualThemes));
        verify(session, times(1)).createQuery(anyString(), eq(Theme.class));
        verify(query, times(1)).setParameter(anyString(), anyString());
        verify(query, times(1)).getResultList();
        verify(session, times(1)).close();
    }

    @Test
    public void testGetAllThemeByName_ThemesExist_FullMatch() {
        // Arrange
        String themeName = "Жизнь дикой природы";
        Section section1 = new Section(2L, "Природа");
        Theme theme1 = new Theme(5L, "Жизнь дикой природы", section1);

        List<Theme> expectedThemes = List.of(theme1);

        when(session.createQuery(anyString(), eq(Theme.class))).thenReturn(query);
        when(query.setParameter(eq("Section"), any(Section.class))).thenReturn(query);
        when(query.setParameter(eq("Name"), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedThemes);

        // Act
        List<Theme> actualThemes = themeAbstractDAO.getAllThemeByName(section1, themeName);

        // Assert
        assertNotNull(actualThemes);
        assertEquals(expectedThemes.size(), actualThemes.size());
        assertTrue(actualThemes.containsAll(expectedThemes));
        assertTrue(expectedThemes.containsAll(actualThemes));
        verify(session, times(1)).createQuery(anyString(), eq(Theme.class));
        verify(query, times(1)).setParameter(anyString(), anyString());
        verify(query, times(1)).getResultList();
        verify(session, times(1)).close();
    }

    @Test
    void testGetAllThemeByName_NoThemesExistInSection() {
        // Arrange
        String themeName = "Генная инженерия";
        Section section1 = new Section(4L, "Семья");

        when(session.createQuery(anyString(), eq(Theme.class))).thenReturn(query);
        when(query.setParameter(eq("Section"), any(Section.class))).thenReturn(query);
        when(query.setParameter(eq("Name"), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<Theme> actualThemes = themeAbstractDAO.getAllThemeByName(section1, themeName);

        // Assert
        assertTrue(actualThemes.isEmpty());
        verify(session, times(1)).createQuery(anyString(), eq(Theme.class));
        verify(query, times(1)).setParameter(anyString(), anyString());
        verify(query, times(1)).getResultList();
        verify(session, times(1)).close();
    }

    @Test
    void testGetAllThemeByName_NoThemesExist() {
        // Arrange
        String themeName = "Семейный отдых";
        Section section1 = new Section(4L, "Семья");

        when(session.createQuery(anyString(), eq(Theme.class))).thenReturn(query);
        when(query.setParameter(eq("Section"), any(Section.class))).thenReturn(query);
        when(query.setParameter(eq("Name"), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<Theme> actualThemes = themeAbstractDAO.getAllThemeByName(section1, themeName);

        // Assert
        assertTrue(actualThemes.isEmpty());
        verify(session, times(1)).createQuery(anyString(), eq(Theme.class));
        verify(query, times(1)).setParameter(anyString(), anyString());
        verify(query, times(1)).getResultList();
        verify(session, times(1)).close();
    }
}