package com.project.backend.repository;
import com.project.backend.model.Assignment;
import com.project.backend.model.Employee;
import com.project.backend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
    /**
     * Find all assignments for a specific employee
     * @param employeeId The ID of the employee
     * @return A list of assignments
     */
    List<Assignment> findByEmployeeId(Long employeeId);
    
    /**
     * Find all current assignments for a specific employee
     * @param employeeId The ID of the employee
     * @param currentDate The current date
     * @return A list of current assignments
     */
    @Query("SELECT a FROM Assignment a " +
           "WHERE a.employee.id = :employeeId " +
           "AND :currentDate BETWEEN a.startDate AND a.endDate")
    List<Assignment> findCurrentAssignmentsByEmployeeId(
            @Param("employeeId") Long employeeId,
            @Param("currentDate") LocalDate currentDate);
    
    /**
     * Find all assignments for a specific project
     * @param projectId The ID of the project
     * @return A list of assignments
     */
    List<Assignment> findByProjectId(Long projectId);
    
    /**
     * Find current assignments for a specific project
     * @param projectId The ID of the project
     * @param currentDate The current date
     * @return A list of current assignments
     */
    @Query("SELECT a FROM Assignment a " +
           "WHERE a.project.id = :projectId " +
           "AND :currentDate BETWEEN a.startDate AND a.endDate")
    List<Assignment> findCurrentAssignmentsByProjectId(
            @Param("projectId") Long projectId,
            @Param("currentDate") LocalDate currentDate);
    
    /**
     * Find assignments where an employee is working on a specific project
     * @param employeeId The ID of the employee
     * @param projectId The ID of the project
     * @return A list of assignments
     */
    List<Assignment> findByEmployeeIdAndProjectId(Long employeeId, Long projectId);
    
    /**
     * Check if an employee is available for a new assignment during a specific period
     * @param employeeId The ID of the employee
     * @param startDate The start date of the potential assignment
     * @param endDate The end date of the potential assignment
     * @return True if the employee is available, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(a) = 0 THEN true ELSE false END " +
           "FROM Assignment a " +
           "WHERE a.employee.id = :employeeId " +
           "AND ((a.startDate BETWEEN :startDate AND :endDate) " +
           "     OR (a.endDate BETWEEN :startDate AND :endDate) " +
           "     OR (:startDate BETWEEN a.startDate AND a.endDate))")
    boolean isEmployeeAvailableForAssignment(
            @Param("employeeId") Long employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
    
    /**
     * Find employees assigned to a project with a specific skill
     * @param projectId The ID of the project
     * @param skillId The ID of the skill
     * @return A list of employees assigned to the project who have the specified skill
     */
    @Query("SELECT DISTINCT a.employee FROM Assignment a " +
           "JOIN EmployeeSkill es ON a.employee.id = es.employee.id " +
           "WHERE a.project.id = :projectId " +
           "AND es.skill.id = :skillId " +
           "AND CURRENT_DATE BETWEEN a.startDate AND a.endDate")
    List<Employee> findEmployeesOnProjectWithSkill(
            @Param("projectId") Long projectId,
            @Param("skillId") Long skillId);
    
    /**
     * Find all employees who are overallocated (assigned to multiple projects simultaneously)
     * @param currentDate The current date
     * @param threshold The maximum number of simultaneous projects before being considered overallocated
     * @return A list of employees and their assignment counts
     */
    @Query("SELECT a.employee, COUNT(DISTINCT a.project) as projectCount " +
           "FROM Assignment a " +
           "WHERE :currentDate BETWEEN a.startDate AND a.endDate " +
           "GROUP BY a.employee.id " +
           "HAVING COUNT(DISTINCT a.project) > :threshold " +
           "ORDER BY projectCount DESC")
    List<Object[]> findOverallocatedEmployees(
            @Param("currentDate") LocalDate currentDate,
            @Param("threshold") long threshold);
}
