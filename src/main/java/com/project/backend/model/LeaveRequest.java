package com.project.backend.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "leave_requests")
public class LeaveRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_request_id")
    private Long leaveRequestId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    
    @Column(name = "leave_type", nullable = false)
    private String leaveType;
    
    @Column(name = "reason")
    private String reason;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;
    
    @Column(name = "approval_date")
    private LocalDate approvalDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private Employee approvedBy;
    
    // Constructors
    public LeaveRequest() {
    }
    
    public LeaveRequest(Employee employee, LocalDate startDate, LocalDate endDate, String leaveType, String reason) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = leaveType;
        this.reason = reason;
        this.status = "Pending";
        this.requestDate = LocalDate.now();
    }
    
    // Getters and Setters
    public Long getLeaveRequestId() {
        return leaveRequestId;
    }
    
    public void setLeaveRequestId(Long leaveRequestId) {
        this.leaveRequestId = leaveRequestId;
    }
    
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
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
    
    public String getLeaveType() {
        return leaveType;
    }
    
    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDate getRequestDate() {
        return requestDate;
    }
    
    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }
    
    public LocalDate getApprovalDate() {
        return approvalDate;
    }
    
    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }
    
    public Employee getApprovedBy() {
        return approvedBy;
    }
    
    public void setApprovedBy(Employee approvedBy) {
        this.approvedBy = approvedBy;
    }
    
    // Helper method to get duration of leave in days
    public long getLeaveDurationDays() {
        return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }
    
    @Override
    public String toString() {
        return "LeaveRequest{" +
                "leaveRequestId=" + leaveRequestId +
                ", employee=" + (employee != null ? employee.getEmployeeId() : null) +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", leaveType='" + leaveType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}