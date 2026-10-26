
package com.example.demo.Model;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeLeave {

    private Long leaveId;

    @NotNull(message = "Employee ID is required")
    @Min(value = 1, message = "Employee ID must be positive")
    private Long employeeId;

    @NotBlank(message = "Employee name is required")
    @Size(min = 3, max = 50,
          message = "Employee name must be between 3 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z ]+$",
             message = "Employee name must contain only letters and spaces")
    private String employeeName;

    @NotBlank(message = "Leave type is mandatory")
    @Pattern(regexp = "(?i)Casual|Sick|Earned|Unpaid",
             message = "Leave type must be Casual, Sick, Earned or Unpaid")
    private String leaveType;

    @Min(value = 1, message = "Leave days should be minimum 1")
    @Max(value = 10, message = "Leave days cannot exceed 10")
    private int numberOfDays;

    @NotBlank(message = "Reason is required")
    @Size(min = 5, max = 250,
          message = "Reason must be between 5 and 250 characters")
    private String reason;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date cannot be in the past")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "End date cannot be in the past")
    private LocalDate endDate;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;

    @Pattern(regexp = "(?i)Pending|Approved|Rejected",
             message = "Status must be Pending, Approved or Rejected")
    private String status = "Pending";
}
