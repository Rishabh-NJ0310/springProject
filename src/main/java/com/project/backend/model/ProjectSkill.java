package com.project.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "project_skills")
public class ProjectSkill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_skill_id")
    private Long projectSkillId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;
    
    @Column(name = "importance_level")
    private Integer importanceLevel;
    
    // Constructors
    public ProjectSkill() {
    }
    
    public ProjectSkill(Project project, Skill skill, Integer importanceLevel) {
        this.project = project;
        this.skill = skill;
        this.importanceLevel = importanceLevel;
    }
    
    // Getters and Setters
    public Long getProjectSkillId() {
        return projectSkillId;
    }
    
    public void setProjectSkillId(Long projectSkillId) {
        this.projectSkillId = projectSkillId;
    }
    
    public Project getProject() {
        return project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    public Skill getSkill() {
        return skill;
    }
    
    public void setSkill(Skill skill) {
        this.skill = skill;
    }
    
    public Integer getImportanceLevel() {
        return importanceLevel;
    }
    
    public void setImportanceLevel(Integer importanceLevel) {
        this.importanceLevel = importanceLevel;
    }
    
    @Override
    public String toString() {
        return "ProjectSkill{" +
                "projectSkillId=" + projectSkillId +
                ", project=" + (project != null ? project.getName() : null) +
                ", skill=" + (skill != null ? skill.getName() : null) +
                ", importanceLevel=" + importanceLevel +
                '}';
    }
}