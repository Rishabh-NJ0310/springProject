package com.project.backend.repository;

import com.project.backend.model.Employee;
import com.project.backend.model.EmployeeSkill;
import com.project.backend.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, Long> {
    
    /**
     * Find all skills for a specific employee
     * @param employeeId The ID of the employee
     * @return A list of employee skills
     */
    List<EmployeeSkill> findByEmployeeId(Long employeeId);
    
    /**
     * Find all employees with a specific skill
     * @param skillId The ID of the skill
     * @return A list of employee skills
     */
    List<EmployeeSkill> findBySkillId(Long skillId);
    
    /**
     * Find the specific employee-skill relationship
     * @param employeeId The ID of the employee
     * @param skillId The ID of the skill
     * @return An optional containing the employee skill if found
     */
    Optional<EmployeeSkill> findByEmployeeIdAndSkillId(Long employeeId, Long skillId);
    
    /**
     * Find all employees with all the required skills
     * @param skillIds The list of required skill IDs
     * @return A list of employees who have all the specified skills
     */
    @Query("SELECT es.employee FROM EmployeeSkill es " +
           "WHERE es.skill.id IN :skillIds " +
           "GROUP BY es.employee.id " +
           "HAVING COUNT(DISTINCT es.skill.id) = :skillCount")
    List<Employee> findEmployeesWithAllSkills(
            @Param("skillIds") List<Long> skillIds,
            @Param("skillCount") long skillCount);
    
    /**
     * Find the most common skills among employees
     * @param limit The maximum number of skills to return
     * @return A list of pairs containing skills and their occurrence count
     */
    @Query("SELECT es.skill, COUNT(es.employee) as employeeCount " +
           "FROM EmployeeSkill es " +
           "GROUP BY es.skill.id " +
           "ORDER BY employeeCount DESC")
    List<Object[]> findMostCommonSkills(@Param("limit") int limit);
    
    /**
     * Delete all skills for a specific employee
     * @param employeeId The ID of the employee
     */
    void deleteByEmployeeId(Long employeeId);
    
    /**
     * Check if an employee has a specific skill
     * @param employeeId The ID of the employee
     * @param skillId The ID of the skill
     * @return True if the employee has the skill, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(es) > 0 THEN true ELSE false END " +
           "FROM EmployeeSkill es " +
           "WHERE es.employee.id = :employeeId AND es.skill.id = :skillId")
    boolean employeeHasSkill(@Param("employeeId") Long employeeId, @Param("skillId") Long skillId);
}