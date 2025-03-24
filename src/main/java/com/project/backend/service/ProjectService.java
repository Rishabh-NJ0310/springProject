package com.project.backend.service;

import com.project.backend.model.Project;
import com.project.backend.model.Skill;
import com.project.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /**
     * Get all projects
     * @return List of all projects
     */
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /**
     * Get project by ID
     * @param id Project ID
     * @return Optional containing project if found
     */
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    /**
     * Create a new project
     * @param project Project to create
     * @return Created project
     */
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    /**
     * Update an existing project
     * @param id Project ID
     * @param projectDetails Updated project details
     * @return Updated project
     */
    public Project updateProject(Long id, Project projectDetails) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        
        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());
        project.setStartDate(projectDetails.getStartDate());
        project.setEndDate(projectDetails.getEndDate());
        project.setStatus(projectDetails.getStatus());
        
        if (projectDetails.getRequiredSkills() != null) {
            project.setRequiredSkills(projectDetails.getRequiredSkills());
        }
        
        return projectRepository.save(project);
    }

    /**
     * Delete a project
     * @param id Project ID
     */
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        projectRepository.delete(project);
    }

    /**
     * Update project required skills
     * @param id Project ID
     * @param skills Set of required skills
     * @return Updated project
     */
    public Project updateProjectSkills(Long id, Set<Skill> skills) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        project.setRequiredSkills(skills);
        return projectRepository.save(project);
    }

    /**
     * Find projects by status
     * @param status Project status to search for
     * @return List of projects with the specified status
     */
    public List<Project> findProjectsByStatus(String status) {
        return projectRepository.findByStatus(status);
    }

    /**
     * Find active projects
     * @return List of active projects
     */
    public List<Project> findActiveProjects() {
        return projectRepository.findByStatus("ACTIVE");
    }

    /**
     * Find projects requiring specific skill
     * @param skillId Skill ID to search for
     * @return List of projects requiring the specified skill
     */
    public List<Project> findProjectsByRequiredSkill(Long skillId) {
        return projectRepository.findByRequiredSkillsId(skillId);
    }
}