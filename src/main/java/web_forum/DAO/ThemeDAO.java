package web_forum.DAO;

import web_forum.classes.Theme;

import java.util.List;

public interface ThemeDAO extends CommonDAO<Theme, Long> {

    List<Theme> getAllThemeByName(String ThemeName);

}
