package com.project.backend.repository;

import com.project.backend.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    
    /**
     * Find all leave requests for a specific employee
     * @param employeeId The ID of the employee
     * @return A list of leave requests
     */
    List<LeaveRequest> findByEmployeeId(Long employeeId);
    
    /**
     * Find leave requests for a specific employee with a specific status
     * @param employeeId The ID of the employee
     * @param status The status to search for
     * @return A list of leave requests
     */
    List<LeaveRequest> findByEmployeeIdAndStatus(Long employeeId, String status);
    
    /**
     * Find pending leave requests for all employees
     * @return A list of pending leave requests
     */
    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.status = 'PENDING' ORDER BY lr.startDate ASC")
    List<LeaveRequest> findPendingLeaveRequests();
    
    /**
     * Find all approved leave requests that overlap with a specific date range
     * @param startDate The start date of the range
     * @param endDate The end date of the range
     * @return A list of approved leave requests that overlap with the specified range
     */
    @Query("SELECT lr FROM LeaveRequest lr " +
           "WHERE lr.status = 'APPROVED' " +
           "AND ((lr.startDate BETWEEN :startDate AND :endDate) " +
           "     OR (lr.endDate BETWEEN :startDate AND :endDate) " +
           "     OR (:startDate BETWEEN lr.startDate AND lr.endDate))")
    List<LeaveRequest> findApprovedLeaveRequestsInRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
    
    /**
     * Check if an employee has approved leave during a specific period
     * @param employeeId The ID of the employee
     * @param startDate The start date of the period
     * @param endDate The end date of the period
     * @return True if the employee has approved leave during the period, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(lr) > 0 THEN true ELSE false END " +
           "FROM LeaveRequest lr " +
           "WHERE lr.employee.id = :employeeId " +
           "AND lr.status = 'APPROVED' " +
           "AND ((lr.startDate BETWEEN :startDate AND :endDate) " +
           "     OR (lr.endDate BETWEEN :startDate AND :endDate) " +
           "     OR (:startDate BETWEEN lr.startDate AND lr.endDate))")
    boolean hasApprovedLeaveInPeriod(
            @Param("employeeId") Long employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
    
    /**
     * Find employees who are currently on leave
     * @param currentDate The current date
     * @return A list of leave requests for employees currently on leave
     */
    @Query("SELECT lr FROM LeaveRequest lr " +
           "WHERE lr.status = 'APPROVED' " +
           "AND :currentDate BETWEEN lr.startDate AND lr.endDate")
    List<LeaveRequest> findEmployeesCurrentlyOnLeave(@Param("currentDate") LocalDate currentDate);
    
    /**
     * Find leave requests with conflicts (overlapping periods for the same employee)
     * @return A list of leave requests with conflicts
     */
    @Query("SELECT lr1 FROM LeaveRequest lr1, LeaveRequest lr2 " +
           "WHERE lr1.id <> lr2.id " +
           "AND lr1.employee.id = lr2.employee.id " +
           "AND lr1.status IN ('PENDING', 'APPROVED') " +
           "AND lr2.status IN ('PENDING', 'APPROVED') " +
           "AND ((lr1.startDate BETWEEN lr2.startDate AND lr2.endDate) " +
           "     OR (lr1.endDate BETWEEN lr2.startDate AND lr2.endDate) " +
           "     OR (lr2.startDate BETWEEN lr1.startDate AND lr1.endDate))")
    List<LeaveRequest> findLeaveRequestsWithConflicts();
}