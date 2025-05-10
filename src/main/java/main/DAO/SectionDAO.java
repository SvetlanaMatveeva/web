package main.DAO;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import main.java_entities.Section;

import java.util.List;

@Repository
@Transactional
public class SectionDAO extends GenericDAO<Section, Long> {

    public SectionDAO(){
        super(Section.class);
    }

    public List<Section> getAllSectionByName(String sectionName) {
        Session session = getCurrentSession();

        Query<Section> query = (sectionName != null && !sectionName.isEmpty())
                ? session.createQuery("FROM Section WHERE LOWER(name) LIKE LOWER(:Name)", Section.class)
                .setParameter("Name", "%" + sectionName + "%") :
                session.createQuery("FROM Section", Section.class);

        return query.list();
    }
}
