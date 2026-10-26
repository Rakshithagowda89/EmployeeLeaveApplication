
package com.example.demo.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.example.demo.Exception.LeaveNotFoundException;
import com.example.demo.Model.EmployeeLeave;

@Service
public class EmployeeLeaveService {

    private final List<EmployeeLeave> leaves = new ArrayList<>();
    private long nextId = 1L;

    // CREATE
    public EmployeeLeave addLeave(EmployeeLeave leave) {
        validateDates(leave);

        if (leave.getStatus() == null || leave.getStatus().isBlank()) {
            leave.setStatus("Pending");
        }

        leave.setStatus(leave.getStatus().trim().toLowerCase(Locale.ROOT));
        leave.setStatus(capitalize(leave.getStatus()));

        leave.setLeaveId(nextId++);
        leaves.add(leave);

        return leave;
    }

    // READ ALL
    public List<EmployeeLeave> getAllLeaves() {
        return new ArrayList<>(leaves);
    }

    // READ BY LEAVE ID
    public EmployeeLeave getLeaveById(Long id) {
        return leaves.stream()
                .filter(l -> Objects.equals(l.getLeaveId(), id))
                .findFirst()
                .orElseThrow(() ->
                    new LeaveNotFoundException(
                        "Leave application not found with ID: " + id));
    }

    // UPDATE
    public EmployeeLeave updateLeave(Long id, EmployeeLeave updatedLeave) {
        validateDates(updatedLeave);

        EmployeeLeave existing = getLeaveById(id);

        existing.setEmployeeId(updatedLeave.getEmployeeId());
        existing.setEmployeeName(updatedLeave.getEmployeeName());
        existing.setLeaveType(updatedLeave.getLeaveType());
        existing.setNumberOfDays(updatedLeave.getNumberOfDays());
        existing.setReason(updatedLeave.getReason());
        existing.setStartDate(updatedLeave.getStartDate());
        existing.setEndDate(updatedLeave.getEndDate());
        existing.setEmail(updatedLeave.getEmail());

        if (updatedLeave.getStatus() != null
                && !updatedLeave.getStatus().isBlank()) {
            existing.setStatus(capitalize(
                updatedLeave.getStatus().trim().toLowerCase(Locale.ROOT)));
        }

        return existing;
    }

    // DELETE
    public String deleteLeave(Long id) {
        EmployeeLeave leave = getLeaveById(id);
        leaves.remove(leave);
        return "Leave application deleted successfully";
    }

    // PARTIAL MATCHING: EMPLOYEE NAME
    public List<EmployeeLeave> searchByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                "Search name must not be blank");
        }

        String search = name.trim().toLowerCase(Locale.ROOT);

        return leaves.stream()
                .filter(l -> l.getEmployeeName()
                    .toLowerCase(Locale.ROOT).contains(search))
                .toList();
    }

    // FILTER BY LEAVE TYPE
    public List<EmployeeLeave> filterByLeaveType(String type) {
        return leaves.stream()
                .filter(l -> l.getLeaveType().equalsIgnoreCase(type.trim()))
                .toList();
    }

    // FILTER BY STATUS
    public List<EmployeeLeave> filterByStatus(String status) {
        return leaves.stream()
                .filter(l -> l.getStatus().equalsIgnoreCase(status.trim()))
                .toList();
    }

    // SORT BY EMPLOYEE NAME: A TO Z
    public List<EmployeeLeave> sortByName() {
        return leaves.stream()
                .sorted(Comparator.comparing(
                    EmployeeLeave::getEmployeeName,
                    String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    // APPROVE LEAVE
    public EmployeeLeave approveLeave(Long id) {
        EmployeeLeave leave = getLeaveById(id);
        leave.setStatus("Approved");
        return leave;
    }

    // REJECT LEAVE
    public EmployeeLeave rejectLeave(Long id) {
        EmployeeLeave leave = getLeaveById(id);
        leave.setStatus("Rejected");
        return leave;
    }

    // BUSINESS VALIDATION
    private void validateDates(EmployeeLeave leave) {
        if (leave.getStartDate() != null
                && leave.getEndDate() != null
                && leave.getEndDate().isBefore(leave.getStartDate())) {
            throw new IllegalArgumentException(
                "End date cannot be before start date");
        }
    }

    private String capitalize(String value) {
        if (value == null || value.isBlank()) {
            return value;
        }
        return value.substring(0, 1).toUpperCase(Locale.ROOT)
                + value.substring(1).toLowerCase(Locale.ROOT);
    }
}
