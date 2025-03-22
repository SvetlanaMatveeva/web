package web_forum.DAO;

import web_forum.classes.User;

import java.time.LocalDateTime;
import java.util.List;

public interface UserDAO extends CommonDAO<User, String> {

    List<User> getAllUserByLogin(String UserLogin);
    List<User> getAllUserBySection(List<String> SectionNames);
    List<Object[]> getAllUserByPeriod(LocalDateTime StartTime, LocalDateTime FinishTime);

}
