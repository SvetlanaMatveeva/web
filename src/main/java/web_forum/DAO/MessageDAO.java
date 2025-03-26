package web_forum.DAO;

import web_forum.classes.Message;
import web_forum.classes.Section;
import web_forum.classes.Theme;

import java.util.List;

public interface MessageDAO extends CommonDAO<Message, Long> {

    List<Message> getAllMessageByTime(Theme ThemeName);  //Section SectionName,

}
