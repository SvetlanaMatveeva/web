package web_forum.DAO;

import web_forum.classes.Section;

import java.util.List;

public interface SectionDAO extends CommonDAO<Section, Long> {

    List<Section> getAllSectionByName(String SectionName);
}
