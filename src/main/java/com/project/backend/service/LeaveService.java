package com.project.backend.service;

import com.project.backend.model.Employee;
import com.project.backend.model.LeaveRequest;
import com.project.backend.repository.EmployeeRepository;
import com.project.backend.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public LeaveService(LeaveRequestRepository leaveRequestRepository, EmployeeRepository employeeRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Get all leave requests
     * @return List of all leave requests
     */
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    /**
     * Get leave request by ID
     * @param id Leave request ID
     * @return Optional containing leave request if found
     */
    public Optional<LeaveRequest> getLeaveRequestById(Long id) {
        return leaveRequestRepository.findById(id);
    }

    /**
     * Create a new leave request
     * @param leaveRequest Leave request to create
     * @return Created leave request
     */
    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        // Set initial status to PENDING
        leaveRequest.setStatus("PENDING");
        return leaveRequestRepository.save(leaveRequest);
    }

    /**
     * Submit a leave request
     * @param employeeId Employee ID
     * @param startDate Leave start date
     * @param endDate Leave end date
     * @param reason Reason for leave
     * @return Created leave request
     */
    public LeaveRequest submitLeaveRequest(Long employeeId, LocalDate startDate, LocalDate endDate, String reason) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));
        
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployee(employee);
        leaveRequest.setStartDate(startDate);
        leaveRequest.setEndDate(endDate);
        leaveRequest.setReason(reason);
        leaveRequest.setStatus("PENDING");
        
        return leaveRequestRepository.save(leaveRequest);
    }

    /**
     * Approve a leave request
     * @param id Leave request ID
     * @return Updated leave request
     */
    public LeaveRequest approveLeaveRequest(Long id) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found with id: " + id));
        
        leaveRequest.setStatus("APPROVED");
        
        // Update employee availability
        Employee employee = leaveRequest.getEmployee();
        employee.setAvailable(false);
        employeeRepository.save(employee);
        
        return leaveRequestRepository.save(leaveRequest);
    }

    /**
     * Reject a leave request
     * @param id Leave request ID
     * @param rejectReason Reason for rejection
     * @return Updated leave request
     */
    public LeaveRequest rejectLeaveRequest(Long id, String rejectReason) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found with id: " + id));
        
        leaveRequest.setStatus("REJECTED");
        leaveRequest.setRejectReason(rejectReason);
        
        return leaveRequestRepository.save(leaveRequest);
    }

    /**
     * Get all leave requests for an employee
     * @param employeeId Employee ID
     * @return List of leave requests for the employee
     */
    public List<LeaveRequest> getLeaveRequestsByEmployee(Long employeeId) {
        return leaveRequestRepository.findByEmployeeId(employeeId);
    }

    /**
     * Get all pending leave requests
     * @return List of pending leave requests
     */
    public List<LeaveRequest> getPendingLeaveRequests() {
        return leaveRequestRepository.findByStatus("PENDING");
    }

    /**
     * Get all leave requests by status
     * @param status Status to filter by
     * @return List of leave requests with the specified status
     */
    public List<LeaveRequest> getLeaveRequestsByStatus(String status) {
        return leaveRequestRepository.findByStatus(status);
    }

    /**
     * Cancel a leave request
     * @param id Leave request ID
     * @return Updated leave request
     */
    public LeaveRequest cancelLeaveRequest(Long id) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found with id: " + id));
        
        // Only pending requests can be canceled
        if (!"PENDING".equals(leaveRequest.getStatus())) {
            throw new RuntimeException("Only pending leave requests can be canceled");
        }
        
        leaveRequest.setStatus("CANCELED");
        return leaveRequestRepository.save(leaveRequest);
    }
}