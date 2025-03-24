package com.project.backend.controller;

import com.project.backend.dto.AssignmentDTO;
import com.project.backend.service.AllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for employee-project allocation operations
 */
@RestController
@RequestMapping("/api/allocation")
public class AllocationController {

    @Autowired
    private AllocationService allocationService;

    /**
     * Get suggested employees for a project based on skills
     * @param projectId The ID of the project to get suggestions for
     * @return A list of employees matching the project skills
     */
    @GetMapping("/suggestions/{projectId}")
    public ResponseEntity<List<Map<String, Object>>> getSuggestedEmployees(@PathVariable Long projectId) {
        return ResponseEntity.ok(allocationService.getSuggestedEmployeesForProject(projectId));
    }

    /**
     * Assign employees to a project
     * @param projectId The ID of the project
     * @param employeeIds List of employee IDs to assign
     * @return The created assignment records
     */
    @PostMapping("/assign/{projectId}")
    public ResponseEntity<List<AssignmentDTO>> assignEmployeesToProject(
            @PathVariable Long projectId,
            @RequestBody List<Long> employeeIds) {
        return new ResponseEntity<>(allocationService.assignEmployeesToProject(projectId, employeeIds), HttpStatus.CREATED);
    }

    /**
     * Get all current assignments
     * @return List of current project assignments
     */
    @GetMapping("/assignments")
    public ResponseEntity<List<AssignmentDTO>> getAllAssignments() {
        return ResponseEntity.ok(allocationService.getAllAssignments());
    }

    /**
     * Get assignments for a specific project
     * @param projectId The ID of the project
     * @return List of assignments for the specified project
     */
    @GetMapping("/assignments/project/{projectId}")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(allocationService.getAssignmentsByProject(projectId));
    }

    /**
     * Get assignments for a specific employee
     * @param employeeId The ID of the employee
     * @return List of assignments for the specified employee
     */
    @GetMapping("/assignments/employee/{employeeId}")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(allocationService.getAssignmentsByEmployee(employeeId));
    }

    /**
     * Update an assignment
     * @param assignmentId The ID of the assignment to update
     * @param assignmentDTO The updated assignment data
     * @return The updated assignment
     */
    @PutMapping("/assignments/{assignmentId}")
    public ResponseEntity<AssignmentDTO> updateAssignment(
            @PathVariable Long assignmentId,
            @RequestBody AssignmentDTO assignmentDTO) {
        return ResponseEntity.ok(allocationService.updateAssignment(assignmentId, assignmentDTO));
    }

    /**
     * Delete an assignment
     * @param assignmentId The ID of the assignment to delete
     * @return No content response
     */
    @DeleteMapping("/assignments/{assignmentId}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long assignmentId) {
        allocationService.deleteAssignment(assignmentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Manually assign an employee to a project - Good to Have functionality
     * @param projectId The ID of the project
     * @param employeeId The ID of the employee
     * @param assignmentDTO Assignment details
     * @return The created assignment
     */
    @PostMapping("/manual-assign/{projectId}/{employeeId}")
    public ResponseEntity<AssignmentDTO> manuallyAssignEmployeeToProject(
            @PathVariable Long projectId,
            @PathVariable Long employeeId,
            @RequestBody AssignmentDTO assignmentDTO) {
        return new ResponseEntity<>(allocationService.manuallyAssignEmployee(projectId, employeeId, assignmentDTO), HttpStatus.CREATED);
    }

    /**
     * Get past assignments for an employee - Good to Have functionality
     * @param employeeId The ID of the employee
     * @return List of past assignments for the employee
     */
    @GetMapping("/past-assignments/{employeeId}")
    public ResponseEntity<List<AssignmentDTO>> getEmployeePastAssignments(@PathVariable Long employeeId) {
        return ResponseEntity.ok(allocationService.getEmployeePastAssignments(employeeId));
    }
}