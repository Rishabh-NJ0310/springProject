package com.project.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "join_date")
    private LocalDate joinDate;
    
    @Column(name = "role")
    private String role;
    
    @Column(name = "is_admin")
    private Boolean isAdmin = false;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeSkill> skills = new HashSet<>();
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Assignment> assignments = new HashSet<>();
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LeaveRequest> leaveRequests = new HashSet<>();
    
    @OneToMany(mappedBy = "approvedBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LeaveRequest> approvedLeaveRequests = new HashSet<>();
    
    // Constructors
    public Employee() {
    }
    
    public Employee(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    // Getters and Setters
    public Long getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public LocalDate getJoinDate() {
        return joinDate;
    }
    
    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public Boolean getIsAdmin() {
        return isAdmin;
    }
    
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public Set<EmployeeSkill> getSkills() {
        return skills;
    }
    
    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }
    
    public Set<Assignment> getAssignments() {
        return assignments;
    }
    
    public void setAssignments(Set<Assignment> assignments) {
        this.assignments = assignments;
    }
    
    public Set<LeaveRequest> getLeaveRequests() {
        return leaveRequests;
    }
    
    public void setLeaveRequests(Set<LeaveRequest> leaveRequests) {
        this.leaveRequests = leaveRequests;
    }
    
    public Set<LeaveRequest> getApprovedLeaveRequests() {
        return approvedLeaveRequests;
    }
    
    public void setApprovedLeaveRequests(Set<LeaveRequest> approvedLeaveRequests) {
        this.approvedLeaveRequests = approvedLeaveRequests;
    }
    
    // Helper methods
    public void addSkill(EmployeeSkill skill) {
        skills.add(skill);
        skill.setEmployee(this);
    }
    
    public void removeSkill(EmployeeSkill skill) {
        skills.remove(skill);
        skill.setEmployee(null);
    }
    
    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
        assignment.setEmployee(this);
    }
    
    public void removeAssignment(Assignment assignment) {
        assignments.remove(assignment);
        assignment.setEmployee(null);
    }
    
    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}