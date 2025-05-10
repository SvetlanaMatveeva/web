package main.Controllers;

import jakarta.validation.Valid;
import main.DAO.SectionDAO;
import main.java_entities.Section;
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
public class SectionController {

    @Autowired
    SectionDAO sectionDAO;

    @GetMapping("/sections")
    public String listSections(Model model,
                                @RequestParam(value = "name", required = false) String name) {

        List<Section> sections = sectionDAO.getAllSectionByName(name);

        model.addAttribute("sections", sections);
        model.addAttribute("name", name);

        return "sections";
    }

    @GetMapping("/admin_panel_add_section")
    @PreAuthorize("hasRole('MODERATOR')")
    public String addSectionForm(Model model) {
        model.addAttribute("newSection", new Section());
        return "admin_panel_add_section";
    }

    @PostMapping("/admin_panel_add_section")
    @PreAuthorize("hasRole('MODERATOR')")
    public String adminPanelAddSection(
            @Valid @ModelAttribute("newSection") Section newSection,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            String errorMessage = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("<br>"));
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }

        try {
            sectionDAO.save(newSection);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при добавлении раздела: " + e.getMessage());
            return "redirect:/admin_panel_add_section";
        }

        return "redirect:/sections";
    }

    @GetMapping("/sections/admin_panel_delete_section")
    @PreAuthorize("hasRole('MODERATOR')")
    public String deleteUser(Model model,
                             @RequestParam(value = "name", required = false) String name) {

        Section section = sectionDAO.getAllSectionByName(name).getFirst();
        sectionDAO.deleteById(section.getId());
        return "redirect:/sections";
    }
}
