package com.project.backend.repository;

import com.project.backend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    /**
     * Find projects by status
     * @param status The status to search for
     * @return A list of projects with the specified status
     */
    List<Project> findByStatus(String status);
    
    /**
     * Find active projects (IN_PROGRESS)
     * @return A list of active projects
     */
    @Query("SELECT p FROM Project p WHERE p.status = 'IN_PROGRESS'")
    List<Project> findActiveProjects();
    
    /**
     * Find projects requiring a specific skill
     * @param skillId The ID of the required skill
     * @return A list of projects requiring the specified skill
     */
    @Query("SELECT p FROM Project p JOIN p.requiredSkills ps WHERE ps.skill.id = :skillId")
    List<Project> findByRequiredSkillId(@Param("skillId") Long skillId);
    
    /**
     * Find projects with current resource shortages
     * @return List of projects that need more resources
     */
    @Query("SELECT p FROM Project p " +
        "WHERE (SELECT COUNT(a) FROM Assignment a WHERE a.project.id = p.id) < " +
        "(SELECT COUNT(ps) FROM ProjectSkill ps WHERE ps.project.id = p.id)")
    List<Project> findProjectsWithResourceShortage();
    
    /**
     * Find projects ending soon (within the next two weeks)
     * @param currentDate The current date
     * @param twoWeeksLater Two weeks from the current date
     * @return List of projects ending soon
     */
    @Query("SELECT p FROM Project p WHERE p.endDate BETWEEN :currentDate AND :twoWeeksLater AND p.status = 'IN_PROGRESS'")
    List<Project> findProjectsEndingSoon(
            @Param("currentDate") LocalDate currentDate,
            @Param("twoWeeksLater") LocalDate twoWeeksLater);
    
    /**
     * Search projects by name or description
     * @param searchTerm The search term to look for
     * @return A list of matching projects
     */
    @Query("SELECT p FROM Project p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
        "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Project> searchProjects(@Param("searchTerm") String searchTerm);
}