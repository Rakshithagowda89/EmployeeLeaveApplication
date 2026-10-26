
package com.example.demo.Controller;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.EmployeeLeave;
import com.example.demo.Service.EmployeeLeaveService;

@RestController
@RequestMapping("/leaves")
public class EmployeeLeaveController {

    @Autowired
    private EmployeeLeaveService service;

    // CREATE
    @PostMapping
    public EmployeeLeave addLeave(
            @Valid @RequestBody EmployeeLeave leave) {
        return service.addLeave(leave);
    }

    // READ ALL
    @GetMapping
    public List<EmployeeLeave> getAllLeaves() {
        return service.getAllLeaves();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public EmployeeLeave getLeaveById(@PathVariable Long id) {
        return service.getLeaveById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public EmployeeLeave updateLeave(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeLeave leave) {
        return service.updateLeave(id, leave);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteLeave(@PathVariable Long id) {
        return service.deleteLeave(id);
    }

    // PARTIAL NAME MATCH
    @GetMapping("/search")
    public List<EmployeeLeave> searchByName(
            @RequestParam String name) {
        return service.searchByName(name);
    }

    // FILTER BY LEAVE TYPE
    @GetMapping("/filter/type")
    public List<EmployeeLeave> filterByType(
            @RequestParam String type) {
        return service.filterByLeaveType(type);
    }

    // FILTER BY STATUS
    @GetMapping("/filter/status")
    public List<EmployeeLeave> filterByStatus(
            @RequestParam String status) {
        return service.filterByStatus(status);
    }

    // SORT BY EMPLOYEE NAME
    @GetMapping("/sort/name")
    public List<EmployeeLeave> sortByName() {
        return service.sortByName();
    }
   
    // APPROVE
    @PatchMapping("/{id}/approve")
    public EmployeeLeave approveLeave(@PathVariable Long id) {
        return service.approveLeave(id);
    }

    // REJECT
    @PatchMapping("/{id}/reject")
    public EmployeeLeave rejectLeave(@PathVariable Long id) {
        return service.rejectLeave(id);
    }
}
