package com.project.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee_skills")
public class EmployeeSkill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_skill_id")
    private Long employeeSkillId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;
    
    @Column(name = "proficiency_level")
    private Integer proficiencyLevel;
    
    @Column(name = "acquired_date")
    private LocalDate acquiredDate;
    
    // Constructors
    public EmployeeSkill() {
    }
    
    public EmployeeSkill(Employee employee, Skill skill, Integer proficiencyLevel) {
        this.employee = employee;
        this.skill = skill;
        this.proficiencyLevel = proficiencyLevel;
        this.acquiredDate = LocalDate.now();
    }
    
    // Getters and Setters
    public Long getEmployeeSkillId() {
        return employeeSkillId;
    }
    
    public void setEmployeeSkillId(Long employeeSkillId) {
        this.employeeSkillId = employeeSkillId;
    }
    
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    public Skill getSkill() {
        return skill;
    }
    
    public void setSkill(Skill skill) {
        this.skill = skill;
    }
    
    public Integer getProficiencyLevel() {
        return proficiencyLevel;
    }
    
    public void setProficiencyLevel(Integer proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }
    
    public LocalDate getAcquiredDate() {
        return acquiredDate;
    }
    
    public void setAcquiredDate(LocalDate acquiredDate) {
        this.acquiredDate = acquiredDate;
    }
    
    @Override
    public String toString() {
        return "EmployeeSkill{" +
                "employeeSkillId=" + employeeSkillId +
                ", employee=" + (employee != null ? employee.getEmployeeId() : null) +
                ", skill=" + (skill != null ? skill.getName() : null) +
                ", proficiencyLevel=" + proficiencyLevel +
                '}';
    }
}