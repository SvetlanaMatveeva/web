package main.DAO;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import main.java_entities.File;
import main.java_entities.Message;

import java.util.List;

@Repository
@Transactional
public class FileDAO extends GenericDAO<File, Long> {

    public FileDAO(){
        super(File.class);
    }

    public List<File> getAllFileByMessage(Message Message) {
        Session session = getCurrentSession();
        Query<File> query = session.createQuery(
                "FROM File F WHERE message = :Message", File.class)
                .setParameter("Message", Message);
        return query.list();
    }
}