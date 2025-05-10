package main.Controllers;

import jakarta.validation.Valid;
import main.DAO.*;
import main.java_entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MessageController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    MessageDAO messageDAO;

    @Autowired
    FileDAO fileDAO;

    @Autowired
    ThemeDAO themeDAO;

    @Autowired
    SectionDAO sectionDAO;

    @GetMapping("/sections/{name}/{th_name}")
    public String listMessages(Model model,
                               @PathVariable String name,
                               @PathVariable String th_name) {

        Section section = sectionDAO.getAllSectionByName(name).getFirst();
        Theme theme = themeDAO.getAllThemeByName(section, th_name).getFirst();
        List<Message> messages = messageDAO.getAllMessageByTime(theme);

        model.addAttribute("messages", messages);
        model.addAttribute("theme", theme);
        model.addAttribute("fileDAO", fileDAO);

        return "messages";
    }

    @GetMapping("/sections/{name}/{th_name}/user_panel_add_message")
    @PreAuthorize("hasRole('USER')")
    public String addMessageForm(Model model,
                               @PathVariable String name,
                               @PathVariable String th_name) {
        model.addAttribute("newMessage", new Message());
        return "user_panel_add_message";
    }

    @PostMapping("/sections/{name}/{th_name}/user_panel_add_message")
    @PreAuthorize("hasRole('USER')")
    public String userPanelAddMessage(
            @Valid @ModelAttribute("newMessage") Message newMessage,
            @PathVariable String name,
            @PathVariable String th_name,
            BindingResult result,
            @RequestParam(value = "file", required = false) MultipartFile file,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }

        try {
            Section section = sectionDAO.getAllSectionByName(name).getFirst();
            Theme theme = themeDAO.getAllThemeByName(section, th_name).getFirst();
            newMessage.setTheme(theme);

            String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userDAO.getById(userLogin);
            newMessage.setUser(user);
            messageDAO.save(newMessage);

            if (!file.isEmpty()) {
                String uploadDir = System.getProperty("user.home") + "/uploads/files/";

                //String uploadDir = "src/main/resources/static/uploads/files/";
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                java.io.File dest = new java.io.File(uploadDir + fileName);
                file.transferTo(dest);

                File messageFile = new File();
                messageFile.setMessage(newMessage);
                messageFile.setSavePath("/uploads/files/" + fileName);
                fileDAO.save(messageFile);
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении сообщения: " + e.getMessage());
            return "redirect:/sections/{name}/{th_name}/user_panel_add_message";
        }

        return "redirect:/sections/{name}/{th_name}";
    }

    @GetMapping("/sections/{name}/{th_name}/admin_panel_delete_message")
    @PreAuthorize("hasRole('MODERATOR')")
    public String deleteUser(Model model,
                             @PathVariable String name,
                             @PathVariable String th_name,
                             @RequestParam(value = "mes_id", required = false) Long mes_id) {
        messageDAO.deleteById(mes_id);
        return "redirect:/sections/{name}/{th_name}";
    }
}