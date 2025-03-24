package com.project.backend.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "skills")
public class Skill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long skillId;
    
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "category")
    private String category;
    
    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeSkill> employees = new HashSet<>();
    
    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectSkill> projects = new HashSet<>();
    
    // Constructors
    public Skill() {
    }
    
    public Skill(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }
    
    // Getters and Setters
    public Long getSkillId() {
        return skillId;
    }
    
    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public Set<EmployeeSkill> getEmployees() {
        return employees;
    }
    
    public void setEmployees(Set<EmployeeSkill> employees) {
        this.employees = employees;
    }
    
    public Set<ProjectSkill> getProjects() {
        return projects;
    }
    
    public void setProjects(Set<ProjectSkill> projects) {
        this.projects = projects;
    }
    
    @Override
    public String toString() {
        return "Skill{" +
                "skillId=" + skillId +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}