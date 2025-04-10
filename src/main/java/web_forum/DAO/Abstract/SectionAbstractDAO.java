package web_forum.DAO.Abstract;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import web_forum.DAO.SectionDAO;
import web_forum.classes.Section;

import java.util.List;

@Repository
public class SectionAbstractDAO extends CommonAbstractDAO<Section, Long> implements SectionDAO {

    public SectionAbstractDAO(){
        super(Section.class);
    }

    @Override
    public List<Section> getAllSectionByName(String sectionName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Section> query = session.createQuery("FROM Section WHERE LOWER(name) LIKE LOWER(:Name)", Section.class)
                    .setParameter("Name", likeExpr(sectionName));
            return query.getResultList();
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}
