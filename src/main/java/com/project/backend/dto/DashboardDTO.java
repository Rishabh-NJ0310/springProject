package com.project.backend.dto;

import java.util.List;
import java.util.Map;

public class DashboardDTO {
    // Summary counts
    private int totalEmployees;
    private int totalProjects;
    private int activeProjects;
    private int pendingLeaveRequests;
    
    // Employee allocation statistics
    private int allocatedEmployees;
    private int unallocatedEmployees;
    private double averageEmployeeUtilization; // Percentage
    
    // Project statistics
    private Map<String, Integer> projectsByStatus; // e.g., {"Active": 10, "Completed": 5}
    private Map<String, Integer> employeesByDepartment;
    private Map<String, Integer> skillDistribution;
    
    // Top skills in demand
    private List<SkillDTO> topSkillsInDemand;
    
    // Leave statistics
    private int approvedLeaves;
    private int rejectedLeaves;
    private int ongoingLeaves;
    private int upcomingLeaves;
    
    // Recent activities
    private List<Map<String, Object>> recentAssignments;
    private List<Map<String, Object>> recentLeaveRequests;
    
    // Skill shortage information
    private List<Map<String, Object>> skillShortages;

    // Default constructor
    public DashboardDTO() {
    }

    // Getters and Setters
    public int getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(int totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public int getTotalProjects() {
        return totalProjects;
    }

    public void setTotalProjects(int totalProjects) {
        this.totalProjects = totalProjects;
    }

    public int getActiveProjects() {
        return activeProjects;
    }

    public void setActiveProjects(int activeProjects) {
        this.activeProjects = activeProjects;
    }

    public int getPendingLeaveRequests() {
        return pendingLeaveRequests;
    }

    public void setPendingLeaveRequests(int pendingLeaveRequests) {
        this.pendingLeaveRequests = pendingLeaveRequests;
    }

    public int getAllocatedEmployees() {
        return allocatedEmployees;
    }

    public void setAllocatedEmployees(int allocatedEmployees) {
        this.allocatedEmployees = allocatedEmployees;
    }

    public int getUnallocatedEmployees() {
        return unallocatedEmployees;
    }

    public void setUnallocatedEmployees(int unallocatedEmployees) {
        this.unallocatedEmployees = unallocatedEmployees;
    }

    public double getAverageEmployeeUtilization() {
        return averageEmployeeUtilization;
    }

    public void setAverageEmployeeUtilization(double averageEmployeeUtilization) {
        this.averageEmployeeUtilization = averageEmployeeUtilization;
    }

    public Map<String, Integer> getProjectsByStatus() {
        return projectsByStatus;
    }

    public void setProjectsByStatus(Map<String, Integer> projectsByStatus) {
        this.projectsByStatus = projectsByStatus;
    }

    public Map<String, Integer> getEmployeesByDepartment() {
        return employeesByDepartment;
    }

    public void setEmployeesByDepartment(Map<String, Integer> employeesByDepartment) {
        this.employeesByDepartment = employeesByDepartment;
    }

    public Map<String, Integer> getSkillDistribution() {
        return skillDistribution;
    }

    public void setSkillDistribution(Map<String, Integer> skillDistribution) {
        this.skillDistribution = skillDistribution;
    }

    public List<SkillDTO> getTopSkillsInDemand() {
        return topSkillsInDemand;
    }

    public void setTopSkillsInDemand(List<SkillDTO> topSkillsInDemand) {
        this.topSkillsInDemand = topSkillsInDemand;
    }

    public int getApprovedLeaves() {
        return approvedLeaves;
    }

    public void setApprovedLeaves(int approvedLeaves) {
        this.approvedLeaves = approvedLeaves;
    }

    public int getRejectedLeaves() {
        return rejectedLeaves;
    }

    public void setRejectedLeaves(int rejectedLeaves) {
        this.rejectedLeaves = rejectedLeaves;
    }

    public int getOngoingLeaves() {
        return ongoingLeaves;
    }

    public void setOngoingLeaves(int ongoingLeaves) {
        this.ongoingLeaves = ongoingLeaves;
    }

    public int getUpcomingLeaves() {
        return upcomingLeaves;
    }

    public void setUpcomingLeaves(int upcomingLeaves) {
        this.upcomingLeaves = upcomingLeaves;
    }

    public List<Map<String, Object>> getRecentAssignments() {
        return recentAssignments;
    }

    public void setRecentAssignments(List<Map<String, Object>> recentAssignments) {
        this.recentAssignments = recentAssignments;
    }

    public List<Map<String, Object>> getRecentLeaveRequests() {
        return recentLeaveRequests;
    }

    public void setRecentLeaveRequests(List<Map<String, Object>> recentLeaveRequests) {
        this.recentLeaveRequests = recentLeaveRequests;
    }

    public List<Map<String, Object>> getSkillShortages() {
        return skillShortages;
    }

    public void setSkillShortages(List<Map<String, Object>> skillShortages) {
        this.skillShortages = skillShortages;
    }
}