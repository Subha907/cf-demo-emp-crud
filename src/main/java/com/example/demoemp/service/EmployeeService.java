package com.example.demoemp.service;
import com.example.demoemp.dto.EmployeeRequestDTO;
import com.example.demoemp.dto.EmployeeResponseDTO;
import java.util.List;

public interface EmployeeService {
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO);
    List<EmployeeResponseDTO> listEmployees();
    EmployeeResponseDTO getEmployeeById(Long id);
    void deleteEmployeeById(Long id);
    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO requestDTO);
}