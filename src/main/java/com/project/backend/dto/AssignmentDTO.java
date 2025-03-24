package com.project.backend.dto;

import java.util.Date;

public class AssignmentDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private Long projectId;
    private String projectName;
    private Date startDate;
    private Date endDate;
    private String role; // Role in the project
    private int allocation; // Percentage of time allocated (e.g., 50%, 100%)
    private String status; // Assigned, Completed, In Progress
    private boolean isAutoAssigned; // Whether assigned by the system or manually
    private String assignmentNotes;
    private Date assignedDate;

    // Default constructor
    public AssignmentDTO() {
    }

    // Parameterized constructor
    public AssignmentDTO(Long id, Long employeeId, String employeeName, Long projectId, String projectName,
                        Date startDate, Date endDate, String role, int allocation, String status, 
                        boolean isAutoAssigned, Date assignedDate) {
        this.id = id;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.projectId = projectId;
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.role = role;
        this.allocation = allocation;
        this.status = status;
        this.isAutoAssigned = isAutoAssigned;
        this.assignedDate = assignedDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getAllocation() {
        return allocation;
    }

    public void setAllocation(int allocation) {
        this.allocation = allocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAutoAssigned() {
        return isAutoAssigned;
    }

    public void setAutoAssigned(boolean autoAssigned) {
        isAutoAssigned = autoAssigned;
    }

    public String getAssignmentNotes() {
        return assignmentNotes;
    }

    public void setAssignmentNotes(String assignmentNotes) {
        this.assignmentNotes = assignmentNotes;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    // Helper method to check if assignment is active at a given date
    public boolean isActiveAt(Date date) {
        if (date == null || startDate == null) {
            return false;
        }
        
        if (endDate == null) {
            return !date.before(startDate);
        }
        
        return !date.before(startDate) && !date.after(endDate);
    }

    @Override
    public String toString() {
        return "AssignmentDTO{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", role='" + role + '\'' +
                ", allocation=" + allocation +
                ", status='" + status + '\'' +
                '}';
    }
}
