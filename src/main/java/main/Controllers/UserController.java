package main.Controllers;

import jakarta.validation.Valid;
import main.DAO.UserDAO;
import main.DAO.SectionDAO;
import main.java_entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    SectionDAO sectionDAO;

    @GetMapping("/users")
    public String listUsers(Model model,
                                @RequestParam(value = "login", required = false) String login,
                                @RequestParam(value = "sections", required = false) List<String> sectionNames,
                                @RequestParam(value = "startTime", required = false) String startTimeStr,
                                @RequestParam(value = "endTime", required = false) String endTimeStr) {

        List<Object[]> users;

        List<String> sectionNamesNew = (sectionNames != null && !sectionNames.isEmpty())
                ? sectionNames
                : null;

        LocalDateTime startTime = (startTimeStr != null && !startTimeStr.isEmpty())
                ? LocalDateTime.parse(startTimeStr) : LocalDateTime.of(1970, 1, 1, 0, 0);

        LocalDateTime endTime = (endTimeStr != null && !endTimeStr.isEmpty())
                ? LocalDateTime.parse(endTimeStr) : LocalDateTime.now();

        users = userDAO.getAllUserBySection_Period_Login(login, sectionNamesNew, startTime, endTime);

        model.addAttribute("users", users);
        model.addAttribute("login", login);

        model.addAttribute("allSections", sectionDAO.getAll());
        model.addAttribute("selectedSections", sectionNamesNew != null ? sectionNamesNew : List.of());

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
    public String addClientForm(Model model) {
        model.addAttribute("newUser", new User());
        return "admin_panel_add_user";
    }

    @PostMapping("/admin_panel_add_user")
    @PreAuthorize("hasRole('MODERATOR')")
    public String adminPanelAddUser(
            @Valid @ModelAttribute("newUser") User newUser,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

            if (result.hasErrors()) {
                String errorMessage = result.getFieldErrors().stream()
                        .map(error -> error.getField() + ": " + error.getDefaultMessage())
                        .collect(Collectors.joining("<br>"));
                redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            }

            try {
                userDAO.save(newUser);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении клиента: " + e.getMessage());
                return "redirect:/admin_panel_add_user";
            }

            return "redirect:/users";
    }

    @GetMapping("/users/{login}/admin_panel_delete_user")
    @PreAuthorize("hasRole('MODERATOR')")
    public String deleteUser(Model model, @PathVariable String login) {
        userDAO.deleteById(login);
        return "redirect:/users";
    }
}
