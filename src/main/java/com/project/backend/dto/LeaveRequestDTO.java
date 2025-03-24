package com.project.backend.dto;

import java.util.Date;

public class LeaveRequestDTO{
    private Long id;
    private Long employeeId;
    private String employeeName; // For display purposes
    private Date startDate;
    private Date endDate;
    private String leaveType; // Sick, Vacation, Personal, etc.
    private String status; // Pending, Approved, Rejected
    private String reason;
    private String adminComments;
    private Date requestDate;
    private Date responseDate;

    // Default constructor
    public LeaveRequestDTO() {
    }

    // Parameterized constructor
    public LeaveRequestDTO(Long id, Long employeeId, String employeeName, Date startDate, Date endDate, 
                        String leaveType, String status, String reason, Date requestDate) {
        this.id = id;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leaveType;
        this.status = status;
        this.reason = reason;
        this.requestDate = requestDate;
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

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAdminComments() {
        return adminComments;
    }

    public void setAdminComments(String adminComments) {
        this.adminComments = adminComments;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    // Helper method to calculate the total leave days
    public int getTotalLeaveDays() {
        if (startDate == null || endDate == null) {
            return 0;
        }
        
        long diff = endDate.getTime() - startDate.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24)) + 1; // +1 to include both start and end days
    }

    @Override
    public String toString() {
        return "LeaveRequestDTO{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", leaveType='" + leaveType + '\'' +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                ", requestDate=" + requestDate +
                '}';
    }
}