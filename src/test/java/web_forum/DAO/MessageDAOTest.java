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
import web_forum.classes.User;
import web_forum.classes.Theme;
import web_forum.classes.Section;
import web_forum.classes.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;


import web_forum.DAO.Abstract.MessageAbstractDAO;

@ExtendWith(MockitoExtension.class)
public class MessageDAOTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Message> query;

    @InjectMocks
    private MessageAbstractDAO messageAbstractDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        doNothing().when(session).close();
    }

    @Test
    public void testGetAllMessageByTime_MessagesExist() {
        // Arrange
        Section section1 = new Section(4L, "Семья");
        Theme theme1 = new Theme(10L, "Воспитание детей", section1);
        User user4 = new User("user4", "letmein", LocalDate.of(2024, 2, 4), 1);
        User user6 = new User("user6", "hello123",LocalDate.of(2024, 2, 7),1);
        Message message1 = new Message(10L, "Как воспитывать ребенка?", user6, theme1, "Советы родителям", LocalDateTime.of(2024, 2, 17, 14, 30));
        Message message2 = new Message(20L, "Как воспитание влияет на личность?", user4, theme1, "Психология детства", LocalDateTime.of(2024, 2, 17, 19, 30));

        List<Message> expectedMessages = Arrays.asList(message1, message2);

        when(session.createQuery(anyString(), eq(Message.class))).thenReturn(query);
        when(query.setParameter(eq("ThemeName"), any(Theme.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedMessages);

        // Act
        List<Message> actualMessages = messageAbstractDAO.getAllMessageByTime(theme1);

        // Assert
        assertNotNull(actualMessages);
        assertEquals(expectedMessages, actualMessages);
        verify(session, times(1)).createQuery(anyString(), eq(Message.class));
        verify(query).setParameter(eq("ThemeName"), eq(theme1));
        verify(query, times(1)).getResultList();
        verify(session, times(1)).close();
    }

    @Test
    void testGetAllMessageByTime_NoMessagesExistInTheme() {
        // Arrange
        Section section1 = new Section(5L, "Наука");
        Theme theme1 = new Theme(16L, "Научные открытия", section1);

        when(session.createQuery(anyString(), eq(Message.class))).thenReturn(query);
        when(query.setParameter(eq("ThemeName"), any(Theme.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<Message> actualMessages = messageAbstractDAO.getAllMessageByTime(theme1);

        // Assert
        assertTrue(actualMessages.isEmpty());
        verify(session, times(1)).createQuery(anyString(), eq(Message.class));
        verify(query).setParameter(eq("ThemeName"), eq(theme1));
        verify(query, times(1)).getResultList();
        verify(session, times(1)).close();
    }
}