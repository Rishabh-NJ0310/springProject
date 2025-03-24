package com.project.backend.controller;

import com.project.backend.model.Employee;
import com.project.backend.model.EmployeeSkill;
import com.project.backend.service.EmployeeService;
import com.project.backend.service.EmployeeSkillService;
import com.project.backend.dto.EmployeeDTO;
import com.project.backend.dto.SkillDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for handling employee-related operations
 */
@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private EmployeeSkillService employeeSkillService;
    
    /**
     * Get all employees
     * @return List of all employees
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    
    /**
     * Get an employee by ID
     * @param id The employee ID
     * @return The employee if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    /**
     * Create a new employee
     * @param employeeDTO The employee data
     * @return The created employee
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee newEmployee = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }
    
    /**
     * Update an employee
     * @param id The employee ID
     * @param employeeDTO The updated employee data
     * @return The updated employee
     */
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
            return ResponseEntity.ok(updatedEmployee);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Delete an employee
     * @param id The employee ID
     * @return No content if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get all skills for an employee
     * @param id The employee ID
     * @return List of employee skills
     */
    @GetMapping("/{id}/skills")
    public ResponseEntity<List<EmployeeSkill>> getEmployeeSkills(@PathVariable Long id) {
        try {
            List<EmployeeSkill> skills = employeeSkillService.getSkillsByEmployeeId(id);
            return ResponseEntity.ok(skills);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Add a skill to an employee
     * @param id The employee ID
     * @param skillDTO The skill data
     * @return The added employee skill
     */
    @PostMapping("/{id}/skills")
    public ResponseEntity<EmployeeSkill> addSkillToEmployee(
            @PathVariable Long id, 
            @Valid @RequestBody SkillDTO skillDTO) {
        try {
            EmployeeSkill employeeSkill = employeeSkillService.addSkillToEmployee(id, skillDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeSkill);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Remove a skill from an employee
     * @param employeeId The employee ID
     * @param skillId The skill ID
     * @return No content if successful
     */
    @DeleteMapping("/{employeeId}/skills/{skillId}")
    public ResponseEntity<Void> removeSkillFromEmployee(
            @PathVariable Long employeeId, 
            @PathVariable Long skillId) {
        try {
            employeeSkillService.removeSkillFromEmployee(employeeId, skillId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Search employees
     * @param term The search term
     * @return List of matching employees
     */
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam String term) {
        List<Employee> employees = employeeService.searchEmployees(term);
        return ResponseEntity.ok(employees);
    }
    
    /**
     * Find employees with specific skills
     * @param skillIds List of skill IDs
     * @return List of employees with the specified skills
     */
    @GetMapping("/by-skills")
    public ResponseEntity<List<Employee>> findEmployeesBySkills(@RequestParam List<Long> skillIds) {
        List<Employee> employees = employeeService.findEmployeesBySkills(skillIds);
        return ResponseEntity.ok(employees);
    }
    
    /**
     * Get employee statistics
     * @return Map of statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getEmployeeStats() {
        Map<String, Object> stats = employeeService.getEmployeeStatistics();
        return ResponseEntity.ok(stats);
    }
}