package com.project.backend.repository;

import com.project.backend.model.Project;
import com.project.backend.model.ProjectSkill;
import com.project.backend.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectSkillRepository extends JpaRepository<ProjectSkill, Long> {
    
    /**
     * Find all skill requirements for a specific project
     * @param projectId The ID of the project
     * @return A list of project skills
     */
    List<ProjectSkill> findByProjectId(Long projectId);
    
    /**
     * Find all projects requiring a specific skill
     * @param skillId The ID of the skill
     * @return A list of project skills
     */
    List<ProjectSkill> findBySkillId(Long skillId);
    
    /**
     * Find the specific project-skill relationship
     * @param projectId The ID of the project
     * @param skillId The ID of the skill
     * @return An optional containing the project skill if found
     */
    Optional<ProjectSkill> findByProjectIdAndSkillId(Long projectId, Long skillId);
    
    /**
     * Get skill gaps - skills required by projects that few employees have
     * @param threshold Maximum number of employees with the skill to be considered a "gap"
     * @return List of skills and their shortage counts
     */
    @Query("SELECT ps.skill, " +
           "COUNT(DISTINCT ps.project) as projectNeedCount, " +
           "COUNT(DISTINCT es.employee) as employeeCount " +
           "FROM ProjectSkill ps " +
           "LEFT JOIN EmployeeSkill es ON ps.skill.id = es.skill.id " +
           "GROUP BY ps.skill.id " +
           "HAVING COUNT(DISTINCT es.employee) <= :threshold " +
           "ORDER BY projectNeedCount DESC, employeeCount ASC")
    List<Object[]> findSkillGaps(@Param("threshold") long threshold);
    
    /**
     * Find projects that match an employee's skills
     * @param employeeId The ID of the employee
     * @return A list of projects sorted by skill match percentage
     */
    @Query("SELECT p, " +
           "COUNT(DISTINCT ps.skill.id) as requiredSkillCount, " +
           "COUNT(DISTINCT es.skill.id) as matchedSkillCount " +
           "FROM Project p " +
           "JOIN ProjectSkill ps ON p.id = ps.project.id " +
           "LEFT JOIN EmployeeSkill es ON ps.skill.id = es.skill.id AND es.employee.id = :employeeId " +
           "WHERE p.status = 'IN_PROGRESS' " +
           "GROUP BY p.id " +
           "HAVING COUNT(DISTINCT es.skill.id) > 0 " +
           "ORDER BY (COUNT(DISTINCT es.skill.id) * 1.0 / COUNT(DISTINCT ps.skill.id)) DESC")
    List<Object[]> findProjectMatchesForEmployee(@Param("employeeId") Long employeeId);
    
    /**
     * Delete all skill requirements for a specific project
     * @param projectId The ID of the project
     */
    void deleteByProjectId(Long projectId);
    
    /**
     * Get the required number of employees for a specific skill in a project
     * @param projectId The ID of the project
     * @param skillId The ID of the skill
     * @return The required number of employees
     */
    @Query("SELECT ps.requiredCount FROM ProjectSkill ps " +
           "WHERE ps.project.id = :projectId AND ps.skill.id = :skillId")
    Integer getRequiredCountForSkill(
            @Param("projectId") Long projectId,
            @Param("skillId") Long skillId);
}