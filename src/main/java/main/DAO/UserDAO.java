package main.DAO;

import main.java_entities.User;
import main.java_entities.Section;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.time.LocalDateTime;

@Repository
@Transactional
public class UserDAO extends GenericDAO<User, String> {

    public UserDAO(){
        super(User.class);
    }

    public List<User> getAllUserByLogin(String userLogin) {
        Session session = getCurrentSession();
        Query<User> query = session.createQuery("FROM User WHERE login LIKE :gotLogin", User.class)
                .setParameter("gotLogin", "%" + userLogin + "%");
        return query.list();
    }

    public List<User> getAllUserBySection(List<Section> sections) {
        Session session = getCurrentSession();

        Query<User> query = session.createQuery(
                        "SELECT DISTINCT U FROM User U " +
                                "JOIN Message M ON M.user = U " +
                                "JOIN Theme T ON T = M.theme " +
                                "JOIN Section S ON S = T.section " +
                                "WHERE S IN :SectionNames", User.class)
                .setParameter("SectionNames", sections);
        return query.list();
    }

    public List<Object[]> getAllUserByPeriod(LocalDateTime StartTime, LocalDateTime FinishTime) {
        Session session = getCurrentSession();
        Query<Object[]> query = session.createQuery(
                        "SELECT U, COUNT(M) FROM User U " +
                                "JOIN Message M ON M.user = U " +
                                "WHERE M.receipt BETWEEN :StartTime AND :FinishTime " +
                                "GROUP BY U " +
                                "ORDER BY COUNT(M) DESC", Object[].class)
                .setParameter("StartTime", StartTime)
                .setParameter("FinishTime", FinishTime);
        return query.list();
    }

    public List<Object[]> getAllUserBySection_Period(List<String> sectionNames, LocalDateTime StartTime, LocalDateTime FinishTime) {
        Session session = getCurrentSession();

        List<Section> sections = session.createQuery("FROM Section WHERE name IN :sectionNames", Section.class)
                .setParameter("sectionNames", "%" + sectionNames + "%").list();

        Query<Object[]> query = session.createQuery(
                        "SELECT DISTINCT U, COUNT(M) FROM User U " +
                                "JOIN Message M ON M.user = U " +
                                "JOIN Theme T ON T = M.theme " +
                                "JOIN Section S ON S = T.section " +
                                "WHERE S IN :SectionNames " +
                                "AND M.receipt BETWEEN :StartTime AND :FinishTime " +
                                "GROUP BY U " +
                                "ORDER BY COUNT(M) DESC", Object[].class)
                .setParameter("SectionNames", sections)
                .setParameter("StartTime", StartTime)
                .setParameter("FinishTime", FinishTime);
        return query.list();
    }

    public List<Object[]> getAllUserBySection_Period_Login(String userLogin, List<String> sectionNames, LocalDateTime StartTime, LocalDateTime FinishTime) {
        Session session = getCurrentSession();

        List<Section> sections = session.createQuery("FROM Section WHERE name IN :sectionNames", Section.class)
                .setParameter("sectionNames", sectionNames).list();

        Query<Object[]> query = null;

        if (userLogin != null && !userLogin.isEmpty()) {
            if (sectionNames != null && !sectionNames.isEmpty()) {
                query = session.createQuery(
                                "SELECT DISTINCT U, COUNT(M) FROM User U " +
                                        "JOIN Message M ON M.user = U " +
                                        "JOIN Theme T ON T = M.theme " +
                                        "JOIN Section S ON S = T.section " +
                                        "WHERE U.login LIKE :gotLogin " +
                                        "AND S IN :SectionNames " +
                                        "AND M.receipt BETWEEN :StartTime AND :FinishTime " +
                                        "GROUP BY U " +
                                        "ORDER BY COUNT(M) DESC", Object[].class)
                        .setParameter("gotLogin", "%" + userLogin + "%")
                        .setParameter("SectionNames", sections)
                        .setParameter("StartTime", StartTime)
                        .setParameter("FinishTime", FinishTime);
            } else {
                query = session.createQuery(
                                "SELECT DISTINCT U, 0 FROM User U " +
                                        "WHERE U.login LIKE :gotLogin", Object[].class)
                        .setParameter("gotLogin", "%" + userLogin + "%");
            }
        } else {
            if (sectionNames != null && !sectionNames.isEmpty()) {
                query = session.createQuery(
                                "SELECT DISTINCT U, COUNT(M) FROM User U " +
                                        "JOIN Message M ON M.user = U " +
                                        "JOIN Theme T ON T = M.theme " +
                                        "JOIN Section S ON S = T.section " +
                                        "WHERE S IN :SectionNames " +
                                        "AND M.receipt BETWEEN :StartTime AND :FinishTime " +
                                        "GROUP BY U " +
                                        "ORDER BY COUNT(M) DESC", Object[].class)
                        .setParameter("SectionNames", sections)
                        .setParameter("StartTime", StartTime)
                        .setParameter("FinishTime", FinishTime);
            } else {
                query = session.createQuery(
                                "SELECT DISTINCT U, 0 FROM User U ", Object[].class);
            }
        }
        return query.list();
    }
}