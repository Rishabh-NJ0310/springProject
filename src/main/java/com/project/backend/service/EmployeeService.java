package com.project.backend.service;

import com.project.backend.model.Employee;
import com.project.backend.model.Skill;
import com.project.backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Get all employees
     * @return List of all employees
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Get employee by ID
     * @param id Employee ID
     * @return Optional containing employee if found
     */
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    /**
     * Create a new employee
     * @param employee Employee to create
     * @return Created employee
     */
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * Update an existing employee
     * @param id Employee ID
     * @param employeeDetails Updated employee details
     * @return Updated employee
     */
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        
        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setPosition(employeeDetails.getPosition());
        employee.setAvailable(employeeDetails.isAvailable());
        
        if (employeeDetails.getSkills() != null) {
            employee.setSkills(employeeDetails.getSkills());
        }
        
        return employeeRepository.save(employee);
    }

    /**
     * Delete an employee
     * @param id Employee ID
     */
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }

    /**
     * Update employee skills
     * @param id Employee ID
     * @param skills Set of skills to update
     * @return Updated employee
     */
    public Employee updateEmployeeSkills(Long id, Set<Skill> skills) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employee.setSkills(skills);
        return employeeRepository.save(employee);
    }

    /**
     * Find employees by skill
     * @param skillId Skill ID to search for
     * @return List of employees with the specified skill
     */
    public List<Employee> findEmployeesBySkill(Long skillId) {
        return employeeRepository.findBySkillsId(skillId);
    }

    /**
     * Update employee availability
     * @param id Employee ID
     * @param isAvailable Availability status
     * @return Updated employee
     */
    public Employee updateEmployeeAvailability(Long id, boolean isAvailable) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employee.setAvailable(isAvailable);
        return employeeRepository.save(employee);
    }

    /**
     * Find available employees
     * @return List of employees who are available
     */
    public List<Employee> findAvailableEmployees() {
        return employeeRepository.findByIsAvailableTrue();
    }
}