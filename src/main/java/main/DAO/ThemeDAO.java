package main.DAO;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import main.java_entities.Theme;
import main.java_entities.Section;

import java.util.List;

@Repository
@Transactional
public class ThemeDAO extends GenericDAO<Theme, Long> {

    public ThemeDAO(){ super(Theme.class); }

    public List<Theme> getAllThemeByName(Section section, String ThemeName) {
        Session session = getCurrentSession();
        Query<Theme> query = session.createQuery("FROM Theme WHERE section = :Section AND LOWER(name) LIKE LOWER(:Name)", Theme.class)
                .setParameter("Section", section)
                .setParameter("Name", "%" + ThemeName + "%");
        return query.list();
    }
}
