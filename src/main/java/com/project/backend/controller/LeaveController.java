package com.project.backend.controller;

import com.project.backend.model.LeaveRequest;
import com.project.backend.service.LeaveRequestService;
import com.project.backend.dto.LeaveRequestDTO;
import com.project.backend.dto.LeaveStatusUpdateDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for handling leave request operations
 */
@RestController
@RequestMapping("/api/leave-requests")
@CrossOrigin(origins = "*")
public class LeaveController {

    @Autowired
    private LeaveRequestService leaveRequestService;
    
    /**
     * Get all leave requests
     * @return List of all leave requests
     */
    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAllLeaveRequests() {
        return ResponseEntity.ok(leaveRequestService.getAllLeaveRequests());
    }
    
    /**
     * Get a leave request by ID
     * @param id The leave request ID
     * @return The leave request if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequest> getLeaveRequestById(@PathVariable Long id) {
        Optional<LeaveRequest> leaveRequest = leaveRequestService.getLeaveRequestById(id);
        return leaveRequest.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    /**
     * Create a new leave request
     * @param leaveRequestDTO The leave request data
     * @return The created leave request
     */
    @PostMapping
    public ResponseEntity<LeaveRequest> createLeaveRequest(@Valid @RequestBody LeaveRequestDTO leaveRequestDTO) {
        try {
            LeaveRequest newLeaveRequest = leaveRequestService.createLeaveRequest(leaveRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newLeaveRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    /**
     * Update a leave request
     * @param id The leave request ID
     * @param leaveRequestDTO The updated leave request data
     * @return The updated leave request
     */
    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequest> updateLeaveRequest(
            @PathVariable Long id, 
            @Valid @RequestBody LeaveRequestDTO leaveRequestDTO) {
        try {
            LeaveRequest updatedLeaveRequest = leaveRequestService.updateLeaveRequest(id, leaveRequestDTO);
            return ResponseEntity.ok(updatedLeaveRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Delete a leave request
     * @param id The leave request ID
     * @return No content if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable Long id) {
        try {
            leaveRequestService.deleteLeaveRequest(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Update the status of a leave request
     * @param id The leave request ID
     * @param statusUpdateDTO The status update data
     * @return The updated leave request
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<LeaveRequest> updateLeaveRequestStatus(
            @PathVariable Long id, 
            @Valid @RequestBody LeaveStatusUpdateDTO statusUpdateDTO) {
        try {
            LeaveRequest updatedLeaveRequest = leaveRequestService.updateLeaveRequestStatus(
                    id, statusUpdateDTO.getStatus(), statusUpdateDTO.getComments());
            return ResponseEntity.ok(updatedLeaveRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    /**
     * Get leave requests for a specific employee
     * @param employeeId The employee ID
     * @return List of leave requests for the employee
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequest>> getLeaveRequestsByEmployeeId(@PathVariable Long employeeId) {
        List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestsByEmployeeId(employeeId);
        return ResponseEntity.ok(leaveRequests);
    }
    
    /**
     * Get leave requests with a specific status
     * @param status The status to filter by
     * @return List of leave requests with the specified status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<LeaveRequest>> getLeaveRequestsByStatus(@PathVariable String status) {
        List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestsByStatus(status);
        return ResponseEntity.ok(leaveRequests);
    }
    
    /**
     * Get pending leave requests
     * @return List of pending leave requests
     */
    @GetMapping("/pending")
    public ResponseEntity<List<LeaveRequest>> getPendingLeaveRequests() {
        List<LeaveRequest> pendingRequests = leaveRequestService.getPendingLeaveRequests();
        return ResponseEntity.ok(pendingRequests);
    }
    
    /**
     * Get leave requests in a specific date range
     * @param startDate The start date
     * @param endDate The end date
     * @return List of leave requests in the date range
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<LeaveRequest>> getLeaveRequestsInDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<LeaveRequest> leaveRequests = leaveRequestService.getLeaveRequestsInDateRange(startDate, endDate);
        return ResponseEntity.ok(leaveRequests);
    }
    
    /**
     * Get employees currently on leave
     * @return List of leave requests for employees currently on leave
     */
    @GetMapping("/current")
    public ResponseEntity<List<LeaveRequest>> getEmployeesCurrentlyOnLeave() {
        List<LeaveRequest> currentLeaves = leaveRequestService.getEmployeesCurrentlyOnLeave();
        return ResponseEntity.ok(currentLeaves);
    }
    
    /**
     * Check if an employee has leave during a specific period
     * @param employeeId The employee ID
     * @param startDate The start date
     * @param endDate The end date
     * @return True if the employee has leave during the period, false otherwise
     */
    @GetMapping("/check-availability")
    public ResponseEntity<Map<String, Boolean>> checkEmployeeAvailability(
            @RequestParam Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        boolean hasLeave = leaveRequestService.hasLeaveInPeriod(employeeId, startDate, endDate);
        return ResponseEntity.ok(Map.of("hasLeave", hasLeave));
    }
    
    /**
     * Get leave statistics
     * @return Map of statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getLeaveStatistics() {
        Map<String, Object> stats = leaveRequestService.getLeaveStatistics();
        return ResponseEntity.ok(stats);
    }
}