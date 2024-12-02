package com.example.demoemp.controller;

import com.example.demoemp.dto.EmployeeRequestDTO;
import com.example.demoemp.dto.EmployeeResponseDTO;
import com.example.demoemp.service.EmployeeService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Collections;
import com.example.demoemp.dto.ApiResponse;
import com.example.demoemp.exception.EmployeeAlreadyExistsException;
import com.example.demoemp.exception.EmployeeNotFoundException;



@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> createEmployee(
            @Valid @RequestBody EmployeeRequestDTO request) {
        try {
            EmployeeResponseDTO createdEmployee = employeeService.createEmployee(request);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                    "Employee created successfully", 
                    createdEmployee
                ));
        } catch (EmployeeAlreadyExistsException ex) {
            throw ex; // Will be handled by GlobalExceptionHandler
        }
    }
    
    
        @GetMapping("/list")
        public ResponseEntity<ApiResponse<List<EmployeeResponseDTO>>> getAllEmployees() {
            try {
                List<EmployeeResponseDTO> employees = employeeService.listEmployees();
                return ResponseEntity.ok(
                    ApiResponse.success(
                        "Employees retrieved successfully", 
                        employees
                    )
                );
            } catch (EmployeeNotFoundException ex) {
                return ResponseEntity.ok(
                    ApiResponse.success(
                        "No employees found", 
                        Collections.emptyList()
                    )
                );
            }
        }

        @GetMapping("/get/{id}")
        public ResponseEntity<ApiResponse<EmployeeResponseDTO>> getEmployeeById(@PathVariable Long id) {
            try {
                EmployeeResponseDTO employee = employeeService.getEmployeeById(id);
                return ResponseEntity.ok(
                    ApiResponse.success(
                        "Employee retrieved successfully",
                        employee
                    )
                );
            } catch (EmployeeNotFoundException ex) {
                System.out.println("Employee not found with id: " + id);
                return ResponseEntity.ok(
                    ApiResponse.success(
                        "Employee not found",
                        null
                    )
                );
            }
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<ApiResponse<Void>> deleteEmployeeById(@PathVariable Long id) {
            try {
                employeeService.deleteEmployeeById(id);
                return ResponseEntity.ok(
                    ApiResponse.success(
                        "Employee deleted successfully",
                        null
                    )
                );
            } catch (EmployeeNotFoundException ex) {
                return ResponseEntity.ok(
                    ApiResponse.success(
                        "Employee not found",
                        null
                    )
                );
            }
        }

        @PutMapping("/update/{id}")
        public ResponseEntity<ApiResponse<EmployeeResponseDTO>> updateEmployee(
                @PathVariable Long id,
                @Valid @RequestBody EmployeeRequestDTO request) {
            try {
                EmployeeResponseDTO updatedEmployee = employeeService.updateEmployee(id, request);
                return ResponseEntity.ok(
                    ApiResponse.success(
                        "Employee updated successfully",
                        updatedEmployee
                    )
                );
            } catch (EmployeeNotFoundException ex) {
                return ResponseEntity.ok(
                    ApiResponse.success(
                        "Employee not found",
                        null
                    )
                );
            }
        }
    }
 

