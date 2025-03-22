package web_forum.DAO.Abstract;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import web_forum.DAO.ThemeDAO;
import web_forum.classes.Theme;

import java.util.List;

@Repository
public class ThemeAbstractDAO extends CommonAbstractDAO<Theme, Long> implements ThemeDAO {

    public ThemeAbstractDAO(){ super(Theme.class); }

    @Override
    public List<Theme> getAllThemeByName(String ThemeName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Theme> query = session.createQuery("FROM Theme WHERE name LIKE :Name", Theme.class)
                    .setParameter("Name", likeExpr(ThemeName));
            return query.getResultList();
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }
}
