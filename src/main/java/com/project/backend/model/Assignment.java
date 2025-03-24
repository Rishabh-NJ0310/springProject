package com.project.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "assignments")
public class Assignment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long assignmentId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Column(name = "allocation_percentage")
    private Float allocationPercentage;
    
    @Column(name = "status")
    private String status;
    
    // Constructors
    public Assignment() {
    }
    
    public Assignment(Employee employee, Project project, LocalDate startDate, LocalDate endDate, Float allocationPercentage) {
        this.employee = employee;
        this.project = project;
        this.startDate = startDate;
        this.endDate = endDate;
        this.allocationPercentage = allocationPercentage;
        this.status = "Scheduled";
    }
    
    // Getters and Setters
    public Long getAssignmentId() {
        return assignmentId;
    }
    
    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }
    
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    public Project getProject() {
        return project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public Float getAllocationPercentage() {
        return allocationPercentage;
    }
    
    public void setAllocationPercentage(Float allocationPercentage) {
        this.allocationPercentage = allocationPercentage;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentId=" + assignmentId +
                ", employee=" + (employee != null ? employee.getEmployeeId() : null) +
                ", project=" + (project != null ? project.getProjectId() : null) +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", allocationPercentage=" + allocationPercentage +
                '}';
    }
}