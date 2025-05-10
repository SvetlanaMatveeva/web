package main.Controllers;

import jakarta.validation.Valid;
import main.DAO.ThemeDAO;
import main.DAO.SectionDAO;
import main.java_entities.Section;
import main.java_entities.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ThemeController {

    @Autowired
    ThemeDAO themeDAO;

    @Autowired
    SectionDAO sectionDAO;

    @GetMapping("/sections/{name}")
    public String listThemes(Model model,
                             @PathVariable String name,
                             @RequestParam(value = "th_name", required = false) String th_name) {

        Section section = sectionDAO.getAllSectionByName(name).getFirst();
        List<Theme> themes = themeDAO.getAllThemeByName(section, th_name);

        model.addAttribute("themes", themes);
        model.addAttribute("th_name", th_name);

        return "themes";
    }

    @GetMapping("/sections/{sec_name}/user_panel_add_theme")
    @PreAuthorize("hasRole('USER')")
    public String addThemeForm(Model model, @PathVariable String sec_name) {
        model.addAttribute("newTheme", new Theme());
        return "user_panel_add_theme";
    }

    @PostMapping("/sections/{sec_name}/user_panel_add_theme")
    @PreAuthorize("hasRole('USER')")
    public String userPanelAddTheme(
            @Valid @ModelAttribute("newTheme") Theme newTheme,
            @PathVariable String sec_name,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }

        try {
            Section section = sectionDAO.getAllSectionByName(sec_name).getFirst();
            newTheme.setSection(section);
            themeDAO.save(newTheme);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении темы: " + e.getMessage());
            return "redirect:/sections/{sec_name}/user_panel_add_theme";
        }

        return "redirect:/sections/{sec_name}";
    }

    @GetMapping("/sections/{name}/admin_panel_delete_theme")
    @PreAuthorize("hasRole('MODERATOR')")
    public String deleteUser(Model model,
                             @PathVariable String name,
                             @RequestParam(value = "th_name", required = false) String th_name) {

        Section section = sectionDAO.getAllSectionByName(name).getFirst();
        Theme theme = themeDAO.getAllThemeByName(section, th_name).getFirst();
        themeDAO.deleteById(theme.getId());
        return "redirect:/sections/{name}";
    }
}
