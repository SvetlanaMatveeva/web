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
import web_forum.classes.Message;
import web_forum.classes.Section;
import web_forum.classes.Theme;
import web_forum.classes.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;

import web_forum.DAO.Abstract.UserAbstractDAO;

@ExtendWith(MockitoExtension.class)
public class UserDAOTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<User> query;

    @Mock
    private Query<Object[]> query1;

    @InjectMocks
    private UserAbstractDAO userAbstractDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        doNothing().when(session).close();
    }

    @Test
    public void testGetAllUserByLogin_UsersExist() {
        // Arrange
        String userName = "ser1";
        User user1 = new User("user1", "pass123", LocalDate.of(2024, 2, 1), 1);
        User user10 = new User("user10", "ilovepostgres", LocalDate.of(2024, 2, 12), 1);
        User user11 = new User("user11", "xyz987", LocalDate.of(2024, 2, 13), 1);
        User user12 = new User("user12", "postgresql!", LocalDate.of(2024, 2, 15), 1);
        User user13 = new User("user13", "randompass", LocalDate.of(2024, 2, 16), 1);

        List<User> expectedUsers = Arrays.asList(user1, user10, user11, user12, user13);

        when(session.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userAbstractDAO.getAllUserByLogin(userName);

        // Assert
        assertNotNull(actualUsers);
        assertEquals(expectedUsers.size(), actualUsers.size());
        assertTrue(actualUsers.containsAll(expectedUsers));
        assertTrue(expectedUsers.containsAll(actualUsers));
        verify(session, times(1)).createQuery(anyString(), eq(User.class));
        verify(query, times(1)).setParameter(anyString(), anyString());
        verify(query, times(1)).getResultList();
        verify(session, times(1)).close();
    }

    @Test
    public void testGetAllUserByName_UsersExist_FullMatch() {
        // Arrange
        String userName = "user7";
        User user1 = new User("user7", "password1", LocalDate.of(2024, 2, 8), 1);

        List<User> expectedUsers = List.of(user1);

        when(session.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userAbstractDAO.getAllUserByLogin(userName);

        // Assert
        assertNotNull(actualUsers);
        assertEquals(expectedUsers.size(), actualUsers.size());
        assertTrue(actualUsers.containsAll(expectedUsers));
        assertTrue(expectedUsers.containsAll(actualUsers));
        verify(session, times(1)).createQuery(anyString(), eq(User.class));
        verify(query, times(1)).setParameter(anyString(), anyString());
        verify(query, times(1)).getResultList();
        verify(session, times(1)).close();
    }

    @Test
    void testGetAllUserByName_NoUsersExist() {
        // Arrange
        String userName = "new_user";

        when(session.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<User> actualUsers = userAbstractDAO.getAllUserByLogin(userName);

        // Assert
        assertTrue(actualUsers.isEmpty());
        verify(session, times(1)).createQuery(anyString(), eq(User.class));
        verify(query, times(1)).setParameter(anyString(), anyString());
        verify(query, times(1)).getResultList();
        verify(session, times(1)).close();
    }

    @Test
    public void testGetAllUserBySection_UsersExist_OneSection() {
        // Arrange
        Section section1 = new Section(1L, "Технологии");
        User user1 = new User("user1", "pass123", LocalDate.of(2024, 2, 1), 1);
        User user2 = new User("user2", "secure456", LocalDate.of(2024, 2, 2), 1);
        User user3 = new User("user3", "mypassword", LocalDate.of(2024, 2, 3), 1);
        User user13 = new User("user13", "randompass", LocalDate.of(2024, 2, 16), 1);

        List<User> expectedUsers = Arrays.asList(user1, user2, user3, user13);

        when(session.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter(eq("SectionNames"), anyList())).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userAbstractDAO.getAllUserBySection(List.of(section1));

        // Assert
        assertNotNull(actualUsers);
        assertEquals(expectedUsers.size(), actualUsers.size());
        assertTrue(actualUsers.containsAll(expectedUsers));
        assertTrue(expectedUsers.containsAll(actualUsers));
        verify(session, times(1)).createQuery(anyString(), eq(User.class));
        verify(query, times(1)).setParameter(anyString(), eq(List.of(section1)));
        verify(query, times(1)).getResultList();
        verify(session, times(1)).close();
    }

    @Test
    public void testGetAllUserBySection_UsersExist_MoreSections() {
        // Arrange
        Section section1 = new Section(2L, "Природа");
        Section section2 = new Section(3L, "Путешествия");

        User user2 = new User("user2", "secure456", LocalDate.of(2024, 2, 2), 1);
        User user3 = new User("user3", "mypassword", LocalDate.of(2024, 2, 3), 1);
        User user4 = new User("user4", "letmein", LocalDate.of(2024, 2, 4), 1);
        User user5 = new User("user5", "qwerty", LocalDate.of(2024, 2, 5), 1);
        User user6 = new User("user6", "hello123", LocalDate.of(2024, 2, 7), 1);
        User user7 = new User("user7", "password1", LocalDate.of(2024, 2, 8), 1);
        User user8 = new User("user8", "123abc", LocalDate.of(2024, 2, 9), 1);

        List<User> expectedUsers = Arrays.asList(user2, user3, user4, user5, user6, user7, user8);

        when(session.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter(eq("SectionNames"), anyList())).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userAbstractDAO.getAllUserBySection(Arrays.asList(section1, section2));

        // Assert
        assertNotNull(actualUsers);
        assertEquals(expectedUsers.size(), actualUsers.size());
        assertTrue(actualUsers.containsAll(expectedUsers));
        assertTrue(expectedUsers.containsAll(actualUsers));
        verify(session, times(1)).createQuery(anyString(), eq(User.class));
        verify(query, times(1)).setParameter(anyString(), eq(Arrays.asList(section1, section2)));
        verify(query, times(1)).getResultList();
        verify(session, times(1)).close();
    }

    @Test
    void testGetAllUserBySection_NoUsersExist() {
        // Arrange
        Section section1 = new Section(6L, "Творчество");

        when(session.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter(eq("SectionNames"), anyList())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<User> actualUsers = userAbstractDAO.getAllUserBySection(List.of(section1));

        // Assert
        assertTrue(actualUsers.isEmpty());
        verify(session, times(1)).createQuery(anyString(), eq(User.class));
        verify(query, times(1)).setParameter(anyString(), eq(List.of(section1)));
        verify(query, times(1)).getResultList();
        verify(session, times(1)).close();
    }

    @Test
    public void testGetAllUserByPeriod_UsersExist() {
        // Arrange
        LocalDateTime startTime = LocalDateTime.of(2022, 8, 30,  23, 59);
        LocalDateTime finishTime = LocalDateTime.of(2024, 2, 17, 11, 15);

        User user1 = new User("user1", "pass123", LocalDate.of(2024, 2, 1), 1);
        User user2 = new User("user2", "secure456", LocalDate.of(2024, 2, 2), 1);
        User user3 = new User("user3", "mypassword", LocalDate.of(2024, 2, 3), 1);

        Section section1 = new Section(4L, "Семья");
        Theme theme1 = new Theme(10L, "Воспитание детей", section1);
        Message message1 = new Message(21L, "Новая тема", user3, theme1, "Советы родителям", LocalDateTime.of(2024, 2, 17, 10, 15));

        Object[] result1 = new Object[]{user3, 2L};
        Object[] result2 = new Object[]{user2, 1L};
        Object[] result3 = new Object[]{user1, 1L};

        List<Object[]> expectedResults = Arrays.asList(result1, result2, result3);

        when(session.createQuery(anyString(), eq(Object[].class))).thenReturn(query1);
        when(query1.setParameter(eq("StartTime"), eq(startTime))).thenReturn(query1);
        when(query1.setParameter(eq("FinishTime"), eq(finishTime))).thenReturn(query1);
        when(query1.getResultList()).thenReturn(expectedResults);

        // Act
        List<Object[]> actualResults = userAbstractDAO.getAllUserByPeriod(startTime, finishTime);

        // Assert
        assertNotNull(actualResults);
        assertEquals(expectedResults.size(), actualResults.size());
        assertTrue(actualResults.containsAll(expectedResults));
        assertTrue(expectedResults.containsAll(actualResults));
        assertEquals(result1, actualResults.get(0));
        verify(session, times(1)).createQuery(anyString(), eq(Object[].class));
        verify(query1).setParameter(eq("StartTime"), eq(startTime));
        verify(query1).setParameter(eq("FinishTime"), eq(finishTime));
        verify(query1, times(1)).getResultList();
        verify(session, times(1)).close();
    }

    @Test
    void testGetAllUserByPeriod_NoUsersExist() {
        // Arrange
        LocalDateTime startTime = LocalDateTime.of(2025, 2, 17, 11, 15);
        LocalDateTime finishTime = LocalDateTime.of(2029, 8, 30,  23, 59);

        when(session.createQuery(anyString(), eq(Object[].class))).thenReturn(query1);
        when(query1.setParameter(eq("StartTime"), eq(startTime))).thenReturn(query1);
        when(query1.setParameter(eq("FinishTime"), eq(finishTime))).thenReturn(query1);
        when(query1.getResultList()).thenReturn(Collections.emptyList());

        // Act
        List<Object[]> actualResults = userAbstractDAO.getAllUserByPeriod(startTime, finishTime);

        // Assert
        assertTrue(actualResults.isEmpty());
        verify(session, times(1)).createQuery(anyString(), eq(Object[].class));
        verify(query1).setParameter(eq("StartTime"), eq(startTime));
        verify(query1).setParameter(eq("FinishTime"), eq(finishTime));
        verify(query1, times(1)).getResultList();
        verify(session, times(1)).close();
    }
}