package com.project.backend.service;

import com.project.backend.model.*;
import com.project.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final AssignmentRepository assignmentRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final SkillRepository skillRepository;

    @Autowired
    public DashboardService(
            EmployeeRepository employeeRepository,
            ProjectRepository projectRepository,
            AssignmentRepository assignmentRepository,
            LeaveRequestRepository leaveRequestRepository,
            SkillRepository skillRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.assignmentRepository = assignmentRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.skillRepository = skillRepository;
    }

    /**
     * Get overall workforce statistics
     * @return Map containing workforce statistics
     */
    public Map<String, Object> getWorkforceStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        long totalEmployees = employeeRepository.count();
        long availableEmployees = employeeRepository.countByIsAvailableTrue();
        long onLeaveEmployees = leaveRequestRepository.countByStatusAndEndDateIsAfter("APPROVED", LocalDate.now());
        long assignedEmployees = assignmentRepository.countByStatusAndEndDateIsAfter("ACTIVE", LocalDate.now());
        
        statistics.put("totalEmployees", totalEmployees);
        statistics.put("availableEmployees", availableEmployees);
        statistics.put("onLeaveEmployees", onLeaveEmployees);
        statistics.put("assignedEmployees", assignedEmployees);
        statistics.put("availabilityRatio", (double) availableEmployees / totalEmployees);
        
        return statistics;
    }

    /**
     * Get project statistics
     * @return Map containing project statistics
     */
    public Map<String, Object> getProjectStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        long totalProjects = projectRepository.count();
        long activeProjects = projectRepository.countByStatus("ACTIVE");
        long completedProjects = projectRepository.countByStatus("COMPLETED");
        long plannedProjects = projectRepository.countByStatus("PLANNED");
        
        statistics.put("totalProjects", totalProjects);
        statistics.put("activeProjects", activeProjects);
        statistics.put("completedProjects", completedProjects);
        statistics.put("plannedProjects", plannedProjects);
        
        // Get project allocation stats
        List<Project> activeProjectsList = projectRepository.findByStatus("ACTIVE");
        Map<String, Long> projectAllocationMap = new HashMap<>();
        
        for (Project project : activeProjectsList) {
            long assignedCount = assignmentRepository.countByProjectIdAndStatus(project.getId(), "ACTIVE");
            projectAllocationMap.put(project.getName(), assignedCount);
        }
        
        statistics.put("projectAllocation", projectAllocationMap);
        
        return statistics;
    }

    /**
     * Get skill distribution statistics
     * @return Map containing skill distribution statistics
     */
    public Map<String, Object> getSkillDistribution() {
        Map<String, Object> statistics = new HashMap<>();
        
        List<Skill> allSkills = skillRepository.findAll();
        Map<String, Long> skillDistributionMap = new HashMap<>();
        Map<String, List<String>> skillCategoryMap = new HashMap<>();
        
        for (Skill skill : allSkills) {
            long employeeCount = employeeRepository.countBySkillsId(skill.getId());
            skillDistributionMap.put(skill.getName(), employeeCount);
            
            // Group skills by category
            if (!skillCategoryMap.containsKey(skill.getCategory())) {
                skillCategoryMap.put(skill.getCategory(), new ArrayList<>());
            }
            skillCategoryMap.get(skill.getCategory()).add(skill.getName());
        }
        
        statistics.put("skillDistribution", skillDistributionMap);
        statistics.put("skillsByCategory", skillCategoryMap);
        
        // Find most in-demand skills (those required by most projects)
        Map<String, Long> skillDemandMap = new HashMap<>();
        for (Skill skill : allSkills) {
            long projectCount = projectRepository.countByRequiredSkillsId(skill.getId());
            skillDemandMap.put(skill.getName(), projectCount);
        }
        
        // Sort by demand (descending)
        Map<String, Long> sortedSkillDemand = skillDemandMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        
        statistics.put("skillDemand", sortedSkillDemand);
        
        return statistics;
    }

    /**
     * Get leave statistics
     * @return Map containing leave statistics
     */
    public Map<String, Object> getLeaveStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        long pendingLeaves = leaveRequestRepository.countByStatus("PENDING");
        long approvedLeaves = leaveRequestRepository.countByStatus("APPROVED");
        long rejectedLeaves = leaveRequestRepository.countByStatus("REJECTED");
        
        statistics.put("pendingLeaves", pendingLeaves);
        statistics.put("approvedLeaves", approvedLeaves);
        statistics.put("rejectedLeaves", rejectedLeaves);
        
        // Get leaves by month
        Map<String, Long> leavesByMonth = new HashMap<>();
        LocalDate now = LocalDate.now();
        
        for (int i = 0; i < 6; i++) {
            LocalDate month = now.minusMonths(i);
            String monthKey = month.getMonth().toString() + " " + month.getYear();
            
            LocalDate startOfMonth = month.withDayOfMonth(1);
            LocalDate endOfMonth = month.withDayOfMonth(month.lengthOfMonth());
            
            long leavesCount = leaveRequestRepository.countByStatusAndStartDateBetween(
                    "APPROVED", startOfMonth, endOfMonth);
            
            leavesByMonth.put(monthKey, leavesCount);
        }
        
        statistics.put("leavesByMonth", leavesByMonth);
        
        return statistics;
    }

    /**
     * Get allocation metrics
     * @return Map containing allocation metrics
     */
    public Map<String, Object> getAllocationMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        // Calculate allocation efficiency (% of available employees allocated to projects)
        long availableEmployees = employeeRepository.countByIsAvailableTrue();
        long totalEmployees = employeeRepository.count();
        long allocatedEmployees = assignmentRepository.countByStatusAndEndDateIsAfter("ACTIVE", LocalDate.now());
        
        double allocationRate = totalEmployees > 0 ? (double) allocatedEmployees / totalEmployees : 0;
        metrics.put("allocationRate", allocationRate);
        
        // Get skill gap analysis (skills required by projects but lacking in workforce)
        List<Skill> allSkills = skillRepository.findAll();
        Map<String, Long> skillGapMap = new HashMap<>();
        
        for (Skill skill : allSkills) {
            long projectsRequiring = projectRepository.countByRequiredSkillsIdAndStatus(skill.getId(), "ACTIVE");
            long employeesWithSkill = employeeRepository.countBySkillsId(skill.getId());
            
            if (projectsRequiring > 0 && employeesWithSkill < projectsRequiring) {
                skillGapMap.put(skill.getName(), projectsRequiring - employeesWithSkill);
            }
        }
        
        metrics.put("skillGap", skillGapMap);
        
        // Project resource utilization
        List<Project> activeProjects = projectRepository.findByStatus("ACTIVE");
        Map<String, Double> projectUtilizationMap = new HashMap<>();
        
        for (Project project : activeProjects) {
            // Calculate how many required skills are actually covered by assigned employees
            Set<Skill> requiredSkills = project.getRequiredSkills();
            List<ProjectAssignment> assignments = assignmentRepository.findByProjectIdAndStatus(project.getId(), "ACTIVE");
            
            Set<Skill> coveredSkills = new HashSet<>();
            for (ProjectAssignment assignment : assignments) {
                coveredSkills.addAll(assignment.getEmployee().getSkills().stream()
                        .filter(requiredSkills::contains)
                        .collect(Collectors.toSet()));
            }
            
            double utilization = requiredSkills.size() > 0 ? 
                    (double) coveredSkills.size() / requiredSkills.size() : 1.0;
            projectUtilizationMap.put(project.getName(), utilization);
        }
        
        metrics.put("projectUtilization", projectUtilizationMap);
        
        return metrics;
    }

    /**
     * Get dashboard summary (key metrics for quick view)
     * @return Map containing dashboard summary
     */
    public Map<String, Object> getDashboardSummary() {
        Map<String, Object> summary = new HashMap<>();
        
        // Key workforce metrics
        long totalEmployees = employeeRepository.count();
        long availableEmployees = employeeRepository.countByIsAvailableTrue();
        
        // Key project metrics
        long activeProjects = projectRepository.countByStatus("ACTIVE");
        long pendingLeaves = leaveRequestRepository.countByStatus("PENDING");
        
        // Upcoming deadlines (projects ending within 7 days)
        LocalDate now = LocalDate.now();
        LocalDate nextWeek = now.plusDays(7);
        List<Project> upcomingDeadlines = projectRepository.findByStatusAndEndDateBetween(
                "ACTIVE", now, nextWeek);
        
        // Top skills in demand
        Map<String, Object> skillDistribution = getSkillDistribution();
        @SuppressWarnings("unchecked")
        Map<String, Long> skillDemand = (Map<String, Long>) skillDistribution.get("skillDemand");
        
        // Limit to top 5 skills
        Map<String, Long> topSkills = skillDemand.entrySet().stream()
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        
        summary.put("totalEmployees", totalEmployees);
        summary.put("availableEmployees", availableEmployees);
        summary.put("activeProjects", activeProjects);
        summary.put("pendingLeaves", pendingLeaves);
        summary.put("upcomingDeadlines", upcomingDeadlines);
        summary.put("topSkillsInDemand", topSkills);
        
        return summary;
    }

    /**
     * Get resource allocation recommendations
     * @return List of recommendation objects
     */
    public List<Map<String, Object>> getResourceAllocationRecommendations() {
        List<Map<String, Object>> recommendations = new ArrayList<>();
        
        // Find projects with missing skills
        List<Project> activeProjects = projectRepository.findByStatus("ACTIVE");
        
        for (Project project : activeProjects) {
            Set<Skill> requiredSkills = project.getRequiredSkills();
            List<ProjectAssignment> assignments = assignmentRepository.findByProjectIdAndStatus(project.getId(), "ACTIVE");
            
            // Find which skills are covered by current assignments
            Set<Skill> coveredSkills = new HashSet<>();
            for (ProjectAssignment assignment : assignments) {
                coveredSkills.addAll(assignment.getEmployee().getSkills().stream()
                        .filter(requiredSkills::contains)
                        .collect(Collectors.toSet()));
            }
            
            // Find missing skills
            Set<Skill> missingSkills = new HashSet<>(requiredSkills);
            missingSkills.removeAll(coveredSkills);
            
            if (!missingSkills.isEmpty()) {
                // Find employees with missing skills who are available
                for (Skill skill : missingSkills) {
                    List<Employee> availableEmployees = employeeRepository.findByIsAvailableTrueAndSkillsId(skill.getId());
                    
                    if (!availableEmployees.isEmpty()) {
                        Map<String, Object> recommendation = new HashMap<>();
                        recommendation.put("projectId", project.getId());
                        recommendation.put("projectName", project.getName());
                        recommendation.put("missingSkill", skill.getName());
                        recommendation.put("recommendedEmployees", availableEmployees);
                        recommendation.put("recommendationType", "MISSING_SKILL");
                        
                        recommendations.add(recommendation);
                    }
                }
            }
        }
        
        // Find employees who should be reallocated (idle but skilled)
        List<Employee> availableEmployees = employeeRepository.findByIsAvailableTrue();
        for (Employee employee : availableEmployees) {
            Set<Skill> employeeSkills = employee.getSkills();
            
            if (!employeeSkills.isEmpty()) {
                // Find projects that need these skills
                List<Project> matchingProjects = new ArrayList<>();
                
                for (Project project : activeProjects) {
                    Set<Skill> requiredSkills = project.getRequiredSkills();
                    
                    // Check if employee has any of the required skills
                    if (employeeSkills.stream().anyMatch(requiredSkills::contains)) {
                        matchingProjects.add(project);
                    }
                }
                
                if (!matchingProjects.isEmpty()) {
                    Map<String, Object> recommendation = new HashMap<>();
                    recommendation.put("employeeId", employee.getId());
                    recommendation.put("employeeName", employee.getName());
                    recommendation.put("availableSkills", employeeSkills);
                    recommendation.put("matchingProjects", matchingProjects);
                    recommendation.put("recommendationType", "IDLE_EMPLOYEE");
                    
                    recommendations.add(recommendation);
                }
            }
        }
        
        return recommendations;
    }
}