package com.project.backend.service;

import com.project.backend.model.Employee;
import com.project.backend.model.Project;
import com.project.backend.model.Assignment;
import com.project.backend.model.Skill;
import com.project.backend.repository.EmployeeRepository;
import com.project.backend.repository.AssignmentRepository;
import com.project.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AllocationService {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final AssignmentRepository assignmentRepository;

    @Autowired
    public AllocationService(
            EmployeeRepository employeeRepository,
            ProjectRepository projectRepository,
            AssignmentRepository assignmentRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.assignmentRepository = assignmentRepository;
    }

    /**
     * Find suitable employees for a project based on required skills
     * @param projectId Project ID
     * @return List of suitable employees sorted by skill match
     */
    public List<Employee> findSuitableEmployeesForProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        
        Set<Skill> requiredSkills = project.getRequiredSkills();
        List<Employee> availableEmployees = employeeRepository.findByIsAvailableTrue();
        
        // Score employees based on how many required skills they have
        List<Employee> suitableEmployees = availableEmployees.stream()
                .filter(employee -> {
                    Set<Skill> employeeSkills = employee.getSkills();
                    // Employee must have at least one of the required skills
                    return employeeSkills.stream()
                            .anyMatch(requiredSkills::contains);
                })
                .sorted((e1, e2) -> {
                    // Count matching skills for each employee
                    long e1MatchCount = e1.getSkills().stream()
                            .filter(requiredSkills::contains)
                            .count();
                    long e2MatchCount = e2.getSkills().stream()
                            .filter(requiredSkills::contains)
                            .count();
                    return Long.compare(e2MatchCount, e1MatchCount); // Descending order
                })
                .collect(Collectors.toList());
        
        return suitableEmployees;
    }

    /**
     * Assign an employee to a project
     * @param employeeId Employee ID
     * @param projectId Project ID
     * @param startDate Assignment start date
     * @param endDate Assignment end date
     * @return Created project assignment
     */
    public Assignment assignEmployeeToProject(Long employeeId, Long projectId, 
                                                LocalDate startDate, LocalDate endDate) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));
        
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        
        // Validate employee availability
        if (!employee.isAvailable()) {
            throw new RuntimeException("Employee is not available for assignment");
        }
        
        Assignment assignment = new Assignment();
        assignment.setEmployee(employee);
        assignment.setProject(project);
        assignment.setStartDate(startDate);
        assignment.setEndDate(endDate);
        assignment.setStatus("ACTIVE");
        
        // Update employee availability
        employee.setAvailable(false);
        employeeRepository.save(employee);
        
        return assignmentRepository.save(assignment);
    }

    /**
     * Complete a project assignment
     * @param assignmentId Assignment ID
     * @return Updated project assignment
     */
    public Assignment completeAssignment(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found with id: " + assignmentId));
        
        assignment.setStatus("COMPLETED");
        assignment.setEndDate(LocalDate.now());
        
        // Update employee availability
        Employee employee = assignment.getEmployee();
        employee.setAvailable(true);
        employeeRepository.save(employee);
        
        return assignmentRepository.save(assignment);
    }

    /**
     * Get all assignments for a project
     * @param projectId Project ID
     * @return List of assignments for the project
     */
    public List<Assignment> getAssignmentsByProject(Long projectId) {
        return assignmentRepository.findByProjectId(projectId);
    }

    /**
     * Get all assignments for an employee
     * @param employeeId Employee ID
     * @return List of assignments for the employee
     */
    public List<Assignment> getAssignmentsByEmployee(Long employeeId) {
        return assignmentRepository.findByEmployeeId(employeeId);
    }

    /**
     * Get active assignments for an employee
     * @param employeeId Employee ID
     * @return List of active assignments for the employee
     */
    public List<Assignment> getActiveAssignmentsByEmployee(Long employeeId) {
        return assignmentRepository.findByEmployeeIdAndStatus(employeeId, "ACTIVE");
    }

    /**
     * Get all assignments by status
     * @param status Status to filter by
     * @return List of assignments with the specified status
     */
    public List<Assignment> getAssignmentsByStatus(String status) {
        return assignmentRepository.findByStatus(status);
    }

    /**
     * Automatically allocate employees to a project
     * @param projectId Project ID
     * @param requiredEmployeeCount Number of employees needed
     * @param startDate Assignment start date
     * @param endDate Assignment end date
     * @return List of created assignments
     */
    public List<Assignment> autoAllocateEmployeesToProject(
            Long projectId, int requiredEmployeeCount, LocalDate startDate, LocalDate endDate) {
        
        List<Employee> suitableEmployees = findSuitableEmployeesForProject(projectId);
        List<Assignment> createdAssignments = new ArrayList<>();
        
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        
        // Assign the top N suitable employees
        for (int i = 0; i < Math.min(requiredEmployeeCount, suitableEmployees.size()); i++) {
            Employee employee = suitableEmployees.get(i);
                
            Assignment assignment = new Assignment();
            assignment.setEmployee(employee);
            assignment.setProject(project);
            assignment.setStartDate(startDate);
            assignment.setEndDate(endDate);
            assignment.setStatus("ACTIVE");
            
            // Update employee availability
            employee.setAvailable(false);
            employeeRepository.save(employee);
            
            createdAssignments.add(assignmentRepository.save(assignment));
        }
        
        return createdAssignments;
    }
}