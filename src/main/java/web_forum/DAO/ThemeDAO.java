package web_forum.DAO;

import web_forum.classes.Theme;
import web_forum.classes.Section;

import java.util.List;

public interface ThemeDAO extends CommonDAO<Theme, Long> {

    List<Theme> getAllThemeByName(Section section, String ThemeName);

}
