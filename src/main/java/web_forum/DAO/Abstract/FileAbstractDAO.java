package web_forum.DAO.Abstract;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import web_forum.DAO.FileDAO;
import web_forum.classes.File;
import web_forum.classes.Message;

import java.util.List;

@Repository
public class FileAbstractDAO extends CommonAbstractDAO<File, Long> implements FileDAO {

    public FileAbstractDAO(){
        super(File.class);
    }

    @Override
    public List<File> getAllFileByMessage(Message Message) {
        try (Session session = sessionFactory.openSession()) {
            Query<File> query = session.createQuery(
                    "FROM File F WHERE message = :Message", File.class)
                    .setParameter("Message", Message);
            return query.getResultList();
        }
    }
}
