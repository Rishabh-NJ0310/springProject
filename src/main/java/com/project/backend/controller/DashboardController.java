package com.project.backend.controller;

import com.project.backend.dto.DashboardDTO;
import com.project.backend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * REST controller for dashboard reporting operations
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * Get summary dashboard data
     * @return Dashboard summary data
     */
    @GetMapping("/summary")
    public ResponseEntity<DashboardDTO> getDashboardSummary() {
        return ResponseEntity.ok(dashboardService.getDashboardSummary());
    }

    /**
     * Get project allocation report
     * @return Report of current project allocations
     */
    @GetMapping("/report/project-allocation")
    public ResponseEntity<Map<String, Object>> getProjectAllocationReport() {
        return ResponseEntity.ok(dashboardService.getProjectAllocationReport());
    }

    /**
     * Get skill distribution report
     * @return Report of skill distribution across employees
     */
    @GetMapping("/report/skill-distribution")
    public ResponseEntity<Map<String, Object>> getSkillDistributionReport() {
        return ResponseEntity.ok(dashboardService.getSkillDistributionReport());
    }

    /**
     * Get leave status report
     * @return Report of current leave statuses
     */
    @GetMapping("/report/leave-status")
    public ResponseEntity<Map<String, Object>> getLeaveStatusReport() {
        return ResponseEntity.ok(dashboardService.getLeaveStatusReport());
    }

    /**
     * Get resource utilization report
     * @return Report of employee utilization
     */
    @GetMapping("/report/resource-utilization")
    public ResponseEntity<Map<String, Object>> getResourceUtilizationReport() {
        return ResponseEntity.ok(dashboardService.getResourceUtilizationReport());
    }

    /**
     * Get project assignment report for date range
     * @param startDate Start date for the report
     * @param endDate End date for the report
     * @return Assignment report for the specified date range
     */
    @GetMapping("/report/assignments")
    public ResponseEntity<Map<String, Object>> getAssignmentReport(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(dashboardService.getAssignmentReport(startDate, endDate));
    }

    /**
     * Get advanced reports (Good to Have feature)
     * @param reportType The type of advanced report to generate
     * @param parameters Additional parameters for the report
     * @return The requested advanced report
     */
    @PostMapping("/report/advanced/{reportType}")
    public ResponseEntity<Map<String, Object>> getAdvancedReport(
            @PathVariable String reportType,
            @RequestBody Map<String, Object> parameters) {
        return ResponseEntity.ok(dashboardService.getAdvancedReport(reportType, parameters));
    }

    /**
     * Export report as a specific format (Good to Have feature)
     * @param reportType The type of report to export
     * @param format The format to export (PDF, CSV, etc.)
     * @param parameters Additional parameters for the report
     * @return The exported report data
     */
    @PostMapping("/report/export/{reportType}/{format}")
    public ResponseEntity<byte[]> exportReport(
            @PathVariable String reportType,
            @PathVariable String format,
            @RequestBody Map<String, Object> parameters) {
        byte[] reportData = dashboardService.exportReport(reportType, format, parameters);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + reportType + "." + format.toLowerCase())
                .body(reportData);
    }
}