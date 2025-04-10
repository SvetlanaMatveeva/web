package web_forum.DAO.Abstract;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import web_forum.DAO.MessageDAO;
import web_forum.classes.Message;
import web_forum.classes.Theme;

import java.util.List;

@Repository
public class MessageAbstractDAO extends CommonAbstractDAO<Message, Long> implements MessageDAO {

    public MessageAbstractDAO(){
        super(Message.class);
    }

    @Override
    public List<Message> getAllMessageByTime(Theme ThemeName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Message> query = session.createQuery(
                    "SELECT M FROM Message M " +
                            "JOIN M.theme T " +
                            "WHERE M.theme = :ThemeName " +
                            "ORDER BY M.receipt", Message.class)
                    .setParameter("ThemeName", ThemeName);
            return query.getResultList();
        }
    }
}
