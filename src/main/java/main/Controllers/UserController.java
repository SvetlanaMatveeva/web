package main.Controllers;

import main.DAO.UserDAO;
import main.DAO.SectionDAO;
import main.java_entities.Section;
import main.java_entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    SectionDAO sectionDAO;

    @GetMapping("/users")
    public String listCustomers(Model model,
                                @RequestParam(value = "login", required = false) String login,
                                @RequestParam(value = "sections", required = false) List<String> sectionNames,
                                @RequestParam(value = "startTime", required = false) String startTimeStr,
                                @RequestParam(value = "endTime", required = false) String endTimeStr) {

        List<Object[]> users;

        List<String> sectionNamesNew = (sectionNames != null && !sectionNames.isEmpty())
                ? sectionNames : sectionDAO.getAll().stream()
                .map(Section::getName)
                .toList();

        LocalDateTime startTime = (startTimeStr != null && !startTimeStr.isEmpty())
                ? LocalDateTime.parse(startTimeStr) : LocalDateTime.of(1970, 1, 1, 0, 0);

        LocalDateTime endTime = (endTimeStr != null && !endTimeStr.isEmpty())
                ? LocalDateTime.parse(endTimeStr) : LocalDateTime.now();

        users = userDAO.getAllUserBySection_Period_Login(login, sectionNamesNew, startTime, endTime);

        model.addAttribute("users", users);
        model.addAttribute("login", login);

        //model.addAttribute("userResults", results);
        model.addAttribute("allSections", sectionDAO.getAll());
        model.addAttribute("selectedSections", sectionNamesNew);
        model.addAttribute("startTime", startTimeStr);
        model.addAttribute("endTime", endTimeStr);
        return "users";
    }

    @GetMapping("/users/{login}")
    public String showUser(@PathVariable String login, Model model) {
        User user = userDAO.getById(login);
        if (user == null) {
            return "error";
        }
        model.addAttribute("user", user);
        return "user_login";
    }

    @GetMapping("/admin_panel_add_user")
    @PreAuthorize("hasRole('MODERATOR')")
    public String adminPanel() {
        return "admin_panel_add_user";
    }
}
