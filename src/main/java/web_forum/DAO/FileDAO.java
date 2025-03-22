package web_forum.DAO;

import web_forum.classes.File;
import web_forum.classes.Message;

import java.util.List;

public interface FileDAO extends CommonDAO<File, Long> {

    List<File> getAllFileByMessage(Message Message);

}
