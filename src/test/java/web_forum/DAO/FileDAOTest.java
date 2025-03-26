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
import web_forum.classes.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;


import web_forum.DAO.Abstract.FileAbstractDAO;

@ExtendWith(MockitoExtension.class)
public class FileDAOTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<File> query;

    @InjectMocks
    private FileAbstractDAO messageAbstractDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        doNothing().when(session).close();
    }

    @Test
    public void testGetAllFileByTime_FilesExist() {
        // Arrange
        Section section1 = new Section(3L, "Путешествия");
        Theme theme1 = new Theme(9L, "Культура и традиции стран", section1);
        User user8 = new User("user4", "letmein", LocalDate.of(2024, 2, 4), 1);
        Message message1 = new Message(9L, "Культурные различия стран", user8, theme1, "Интересные факты", LocalDateTime.of(2024, 2, 17, 14, 0));
        File file1 = new File(5L, "/uploads/files/manual.pdf", message1);

        List<File> expectedFiles = List.of(file1);

        when(session.createQuery(anyString(), eq(File.class))).thenReturn(query);
        when(query.setParameter(eq("Message"), any(Message.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedFiles);

        // Act
        List<File> actualFiles = messageAbstractDAO.getAllFileByMessage(message1);

        // Assert
        assertNotNull(actualFiles);
        assertEquals(expectedFiles.size(), actualFiles.size());
        assertTrue(actualFiles.containsAll(expectedFiles));
        assertTrue(expectedFiles.containsAll(actualFiles));
        verify(session, times(1)).createQuery(anyString(), eq(File.class));
        verify(query).setParameter(eq("Message"), eq(message1));
        verify(query, times(1)).getResultList();
        verify(session, times(1)).close();
    }

    @Test
    void testGetAllFileByTime_NoFilesExistInMessage() {
        // Arrange
        Section section1 = new Section(5L, "Наука");
        Theme theme1 = new Theme(14L, "Космические исследования", section1);
        User user7 = new User("user7", "password1", LocalDate.of(2024, 2, 8), 1);
        Message message1 = new Message(14L, "Исследование космоса", user7, theme1, "Будущее астрономии", LocalDateTime.of(2024, 2, 17, 16, 30));

        when(session.createQuery(anyString(), eq(File.class))).thenReturn(query);
        when(query.setParameter(eq("Message"), any(Message.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<File> actualFiles = messageAbstractDAO.getAllFileByMessage(message1);

        // Assert
        assertTrue(actualFiles.isEmpty());
        verify(session, times(1)).createQuery(anyString(), eq(File.class));
        verify(query).setParameter(eq("Message"), eq(message1));
        verify(query, times(1)).getResultList();
        verify(session, times(1)).close();
    }
}