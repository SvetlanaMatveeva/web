package main.DAO;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import main.java_entities.Message;
import main.java_entities.Theme;

import java.util.List;

@Repository
@Transactional
public class MessageDAO extends GenericDAO<Message, Long> {

    public MessageDAO(){
        super(Message.class);
    }

    public List<Message> getAllMessageByTime(Theme ThemeName) {
        Session session = getCurrentSession();
        Query<Message> query = session.createQuery(
                "SELECT M FROM Message M " +
                        "JOIN M.theme T " +
                        "WHERE M.theme = :ThemeName " +
                        "ORDER BY M.receipt", Message.class)
                .setParameter("ThemeName", ThemeName);
        return query.list();
    }
}