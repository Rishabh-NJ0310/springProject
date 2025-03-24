package com.project.backend.repository;

import com.project.backend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    /**
     * Find an employee by their email
     * @param email The email to search for
     * @return An optional containing the employee if found
     */
    Optional<Employee> findByEmail(String email);
    
    /**
     * Find employees by skill ID
     * @param skillId The ID of the skill to search for
     * @return A list of employees with the specified skill
     */
    @Query("SELECT e FROM Employee e JOIN e.skills es WHERE es.skill.id = :skillId")
    List<Employee> findBySkillId(@Param("skillId") Long skillId);
    
    /**
     * Find available employees (not on leave) with specific skills
     * @param skillIds List of skill IDs required
     * @param startDate Start date for availability check
     * @param endDate End date for availability check
     * @return List of available employees with the required skills
     */
    @Query("SELECT DISTINCT e FROM Employee e " +
        "JOIN e.skills es " +
        "WHERE es.skill.id IN :skillIds " +
        "AND e.id NOT IN (" +
        "    SELECT lr.employee.id FROM LeaveRequest lr " +
        "    WHERE lr.status = 'APPROVED' " +
        "    AND ((lr.startDate BETWEEN :startDate AND :endDate) " +
        "         OR (lr.endDate BETWEEN :startDate AND :endDate) " +
        "         OR (:startDate BETWEEN lr.startDate AND lr.endDate))" +
        ")")
    List<Employee> findAvailableEmployeesWithSkills(
            @Param("skillIds") List<Long> skillIds,
            @Param("startDate") java.time.LocalDate startDate,
            @Param("endDate") java.time.LocalDate endDate);
    
    /**
     * Search employees by name or email
     * @param searchTerm The search term to look for in name or email
     * @return A list of matching employees
     */
    @Query("SELECT e FROM Employee e WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
        "OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
        "OR LOWER(e.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Employee> searchEmployees(@Param("searchTerm") String searchTerm);
}