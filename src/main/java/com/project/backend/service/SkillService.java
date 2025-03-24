package com.project.backend.service;

import com.project.backend.model.Skill;
import com.project.backend.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    @Autowired
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    /**
     * Get all skills
     * @return List of all skills
     */
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    /**
     * Get skill by ID
     * @param id Skill ID
     * @return Optional containing skill if found
     */
    public Optional<Skill> getSkillById(Long id) {
        return skillRepository.findById(id);
    }

    /**
     * Create a new skill
     * @param skill Skill to create
     * @return Created skill
     */
    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    /**
     * Update an existing skill
     * @param id Skill ID
     * @param skillDetails Updated skill details
     * @return Updated skill
     */
    public Skill updateSkill(Long id, Skill skillDetails) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + id));
        
        skill.setName(skillDetails.getName());
        skill.setDescription(skillDetails.getDescription());
        skill.setCategory(skillDetails.getCategory());
        
        return skillRepository.save(skill);
    }

    /**
     * Delete a skill
     * @param id Skill ID
     */
    public void deleteSkill(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + id));
        skillRepository.delete(skill);
    }

    /**
     * Find skills by category
     * @param category Category to search for
     * @return List of skills in the specified category
     */
    public List<Skill> findSkillsByCategory(String category) {
        return skillRepository.findByCategory(category);
    }

    /**
     * Find skills by name containing a keyword
     * @param keyword Keyword to search for
     * @return List of skills with names containing the keyword
     */
    public List<Skill> findSkillsByNameContaining(String keyword) {
        return skillRepository.findByNameContainingIgnoreCase(keyword);
    }
}