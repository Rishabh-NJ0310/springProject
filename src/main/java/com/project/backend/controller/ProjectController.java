package com.project.backend.controller;

import com.project.backend.model.Project;
import com.project.backend.model.ProjectSkill;
import com.project.backend.service.ProjectService;
import com.project.backend.service.ProjectSkillService;
import com.project.backend.dto.ProjectDTO;
import com.project.backend.dto.SkillRequirementDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for handling project-related operations
 */
@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private ProjectSkillService projectSkillService;
    
    /**
     * Get all projects
     * @return List of all projects
     */
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }
    
    /**
     * Get a project by ID
     * @param id The project ID
     * @return The project if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectService.getProjectById(id);
        return project.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    /**
     * Create a new project
     * @param projectDTO The project data
     * @return The created project
     */
    @PostMapping
    public ResponseEntity<Project> createProject(@Valid @RequestBody ProjectDTO projectDTO) {
        Project newProject = projectService.createProject(projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProject);
    }
    
    /**
     * Update a project
     * @param id The project ID
     * @param projectDTO The updated project data
     * @return The updated project
     */
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectDTO projectDTO) {
        try {
            Project updatedProject = projectService.updateProject(id, projectDTO);
            return ResponseEntity.ok(updatedProject);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Delete a project
     * @param id The project ID
     * @return No content if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get all skill requirements for a project
     * @param id The project ID
     * @return List of project skills
     */
    @GetMapping("/{id}/skills")
    public ResponseEntity<List<ProjectSkill>> getProjectSkills(@PathVariable Long id) {
        try {
            List<ProjectSkill> skills = projectSkillService.getSkillsByProjectId(id);
            return ResponseEntity.ok(skills);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Add a skill requirement to a project
     * @param id The project ID
     * @param requirementDTO The skill requirement data
     * @return The added project skill
     */
    @PostMapping("/{id}/skills")
    public ResponseEntity<ProjectSkill> addSkillToProject(
            @PathVariable Long id, 
            @Valid @RequestBody SkillRequirementDTO requirementDTO) {
        try {
            ProjectSkill projectSkill = projectSkillService.addSkillToProject(id, requirementDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(projectSkill);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Update a skill requirement for a project
     * @param projectId The project ID
     * @param skillId The skill ID
     * @param requirementDTO The updated skill requirement data
     * @return The updated project skill
     */
    @PutMapping("/{projectId}/skills/{skillId}")
    public ResponseEntity<ProjectSkill> updateProjectSkill(
            @PathVariable Long projectId,
            @PathVariable Long skillId,
            @Valid @RequestBody SkillRequirementDTO requirementDTO) {
        try {
            ProjectSkill updatedSkill = projectSkillService.updateProjectSkill(projectId, skillId, requirementDTO);
            return ResponseEntity.ok(updatedSkill);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Remove a skill requirement from a project
     * @param projectId The project ID
     * @param skillId The skill ID
     * @return No content if successful
     */
    @DeleteMapping("/{projectId}/skills/{skillId}")
    public ResponseEntity<Void> removeSkillFromProject(
            @PathVariable Long projectId, 
            @PathVariable Long skillId) {
        try {
            projectSkillService.removeSkillFromProject(projectId, skillId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get projects by status
     * @param status The project status
     * @return List of projects with the specified status
     */
    @GetMapping("/by-status")
    public ResponseEntity<List<Project>> getProjectsByStatus(@RequestParam String status) {
        List<Project> projects = projectService.getProjectsByStatus(status);
        return ResponseEntity.ok(projects);
    }
    
    /**
     * Get active projects
     * @return List of active projects
     */
    @GetMapping("/active")
    public ResponseEntity<List<Project>> getActiveProjects() {
        List<Project> projects = projectService.getActiveProjects();
        return ResponseEntity.ok(projects);
    }
    
    /**
     * Get projects with resource shortages
     * @return List of projects that need more resources
     */
    @GetMapping("/resource-shortage")
    public ResponseEntity<List<Project>> getProjectsWithResourceShortage() {
        List<Project> projects = projectService.getProjectsWithResourceShortage();
        return ResponseEntity.ok(projects);
    }
    
    /**
     * Search projects
     * @param term The search term
     * @return List of matching projects
     */
    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProjects(@RequestParam String term) {
        List<Project> projects = projectService.searchProjects(term);
        return ResponseEntity.ok(projects);
    }
    
    /**
     * Get project statistics
     * @return Map of statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getProjectStats() {
        Map<String, Object> stats = projectService.getProjectStatistics();
        return ResponseEntity.ok(stats);
    }
}