package com.project.backend.controller;

import com.project.backend.model.Skill;
import com.project.backend.model.Employee;
import com.project.backend.model.Project;
import com.project.backend.service.SkillService;
import com.project.backend.dto.SkillDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for handling skill-related operations
 */
@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "*")
public class SkillController {

    @Autowired
    private SkillService skillService;
    
    /**
     * Get all skills
     * @return List of all skills
     */
    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }
    
    /**
     * Get a skill by ID
     * @param id The skill ID
     * @return The skill if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable Long id) {
        Optional<Skill> skill = skillService.getSkillById(id);
        return skill.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    /**
     * Create a new skill
     * @param skillDTO The skill data
     * @return The created skill
     */
    @PostMapping
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody SkillDTO skillDTO) {
        Skill newSkill = skillService.createSkill(skillDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSkill);
    }
    
    /**
     * Update a skill
     * @param id The skill ID
     * @param skillDTO The updated skill data
     * @return The updated skill
     */
    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @Valid @RequestBody SkillDTO skillDTO) {
        try {
            Skill updatedSkill = skillService.updateSkill(id, skillDTO);
            return ResponseEntity.ok(updatedSkill);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Delete a skill
     * @param id The skill ID
     * @return No content if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        try {
            skillService.deleteSkill(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get all employees with a specific skill
     * @param id The skill ID
     * @return List of employees with the skill
     */
    @GetMapping("/{id}/employees")
    public ResponseEntity<List<Employee>> getEmployeesWithSkill(@PathVariable Long id) {
        try {
            List<Employee> employees = skillService.getEmployeesWithSkill(id);
            return ResponseEntity.ok(employees);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get all projects requiring a specific skill
     * @param id The skill ID
     * @return List of projects requiring the skill
     */
    @GetMapping("/{id}/projects")
    public ResponseEntity<List<Project>> getProjectsRequiringSkill(@PathVariable Long id) {
        try {
            List<Project> projects = skillService.getProjectsRequiringSkill(id);
            return ResponseEntity.ok(projects);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get all skill categories
     * @return List of unique skill categories
     */
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllSkillCategories() {
        List<String> categories = skillService.getAllSkillCategories();
        return ResponseEntity.ok(categories);
    }
    
    /**
     * Get skills by category
     * @param category The category to filter by
     * @return List of skills in the specified category
     */
    @GetMapping("/by-category")
    public ResponseEntity<List<Skill>> getSkillsByCategory(@RequestParam String category) {
        List<Skill> skills = skillService.getSkillsByCategory(category);
        return ResponseEntity.ok(skills);
    }
    
    /**
     * Get high demand skills
     * @param threshold Minimum number of projects requiring the skill to be considered "high demand"
     * @return List of skills in high demand
     */
    @GetMapping("/high-demand")
    public ResponseEntity<List<Map<String, Object>>> getHighDemandSkills(
            @RequestParam(defaultValue = "3") int threshold) {
        List<Map<String, Object>> highDemandSkills = skillService.getHighDemandSkills(threshold);
        return ResponseEntity.ok(highDemandSkills);
    }
    
    /**
     * Get rare skills
     * @param threshold Maximum number of employees with the skill to be considered "rare"
     * @return List of rare skills
     */
    @GetMapping("/rare")
    public ResponseEntity<List<Map<String, Object>>> getRareSkills(
            @RequestParam(defaultValue = "2") int threshold) {
        List<Map<String, Object>> rareSkills = skillService.getRareSkills(threshold);
        return ResponseEntity.ok(rareSkills);
    }
    
    /**
     * Get skill gaps
     * @param threshold Maximum number of employees with the skill to be considered a "gap"
     * @return List of skills with gaps
     */
    @GetMapping("/gaps")
    public ResponseEntity<List<Map<String, Object>>> getSkillGaps(
            @RequestParam(defaultValue = "2") int threshold) {
        List<Map<String, Object>> skillGaps = skillService.getSkillGaps(threshold);
        return ResponseEntity.ok(skillGaps);
    }
    
    /**
     * Search skills
     * @param term The search term
     * @return List of matching skills
     */
    @GetMapping("/search")
    public ResponseEntity<List<Skill>> searchSkills(@RequestParam String term) {
        List<Skill> skills = skillService.searchSkills(term);
        return ResponseEntity.ok(skills);
    }
}