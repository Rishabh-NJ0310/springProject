package com.project.backend.repository;

import com.project.backend.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    
    /**
     * Find a skill by its name
     * @param name The name of the skill
     * @return An optional containing the skill if found
     */
    Optional<Skill> findByName(String name);
    
    /**
     * Find skills by category
     * @param category The category to search for
     * @return A list of skills in the specified category
     */
    List<Skill> findByCategory(String category);
    
    /**
     * Get all unique skill categories
     * @return A list of unique skill categories
     */
    @Query("SELECT DISTINCT s.category FROM Skill s ORDER BY s.category")
    List<String> findAllCategories();
    
    /**
     * Find skills that are in high demand (required by many projects)
     * @param threshold Minimum number of projects requiring the skill to be considered "high demand"
     * @return List of skills in high demand
     */
    @Query("SELECT s, COUNT(ps.project) as projectCount " + 
           "FROM Skill s JOIN ProjectSkill ps ON s.id = ps.skill.id " +
           "GROUP BY s.id HAVING COUNT(ps.project) >= :threshold " +
           "ORDER BY projectCount DESC")
    List<Object[]> findHighDemandSkills(@Param("threshold") long threshold);
    
    /**
     * Find skills that are rare among employees
     * @param threshold Maximum number of employees with the skill to be considered "rare"
     * @return List of rare skills
     */
    @Query("SELECT s, COUNT(es.employee) as employeeCount " + 
           "FROM Skill s LEFT JOIN EmployeeSkill es ON s.id = es.skill.id " +
           "GROUP BY s.id HAVING COUNT(es.employee) <= :threshold " +
           "ORDER BY employeeCount ASC")
    List<Object[]> findRareSkills(@Param("threshold") long threshold);
    
    /**
     * Search skills by name or category
     * @param searchTerm The search term to look for
     * @return A list of matching skills
     */
    @Query("SELECT s FROM Skill s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(s.category) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Skill> searchSkills(@Param("searchTerm") String searchTerm);
}