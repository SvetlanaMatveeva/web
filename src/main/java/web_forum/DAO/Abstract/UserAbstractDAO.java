package web_forum.DAO.Abstract;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import web_forum.DAO.UserDAO;
import web_forum.classes.User;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public class UserAbstractDAO extends CommonAbstractDAO<User, String> implements UserDAO {

    public UserAbstractDAO(){
        super(User.class);
    }

    @Override
    public List<User> getAllUserByLogin(String userLogin) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE login LIKE :gotLogin", User.class)
                    .setParameter("gotLogin", likeExpr(userLogin));
            return query.getResultList();
        }
    }

    public List<User> getAllUserBySection(List<String> SectionNames) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                    "SELECT DISTINCT U FROM User U " +
                            "JOIN Message M ON M.user = U " +
                            "JOIN Theme T ON T = M.theme " +
                            "JOIN Section S ON S = T.section " +
                            "WHERE S.name IN :SectionNames", User.class)
                    .setParameter("SectionNames", SectionNames);
            return query.getResultList();
        }
    }

    public List<Object[]> getAllUserByPeriod(LocalDateTime StartTime, LocalDateTime FinishTime) {
        try (Session session = sessionFactory.openSession()) {
            Query<Object[]> query = session.createQuery(
                    "SELECT U, COUNT(M) FROM User U " +
                            "JOIN Message M ON M.user = U " +
                            "WHERE M.receipt BETWEEN :StartTime AND :FinishTime " +
                            "GROUP BY U " +
                            "ORDER BY COUNT(M) DESC", Object[].class)
                    .setParameter("StartTime", StartTime)
                    .setParameter("FinishTime", FinishTime);
            return query.getResultList();
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}
