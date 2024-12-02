package com.example.demoemp.service.impl;

import com.example.demoemp.dto.EmployeeRequestDTO;
import com.example.demoemp.dto.EmployeeResponseDTO;
import com.example.demoemp.entity.Employee;
import com.example.demoemp.exception.EmployeeAlreadyExistsException;
import com.example.demoemp.repository.EmployeeRepository;
import com.example.demoemp.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import com.example.demoemp.exception.EmployeeNotFoundException;
import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Value("${app.max.employees}")
    private int maxEmployees;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO) {
        if (employeeRepository.count() >= maxEmployees) {
            throw new RuntimeException("Maximum number of employees reached");
        }

        if (employeeRepository.existsByEmail(requestDTO.getEmail())) {
            throw new EmployeeAlreadyExistsException("Employee with email " + requestDTO.getEmail() + " already exists");
        }

        Employee employee = new Employee();
        BeanUtils.copyProperties(requestDTO, employee);
        
        employee = employeeRepository.save(employee);
        
        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        BeanUtils.copyProperties(employee, responseDTO);
        return responseDTO;
    }
    
    @Override
    public List<EmployeeResponseDTO> listEmployees(){
        try {
            List<Employee> employees = employeeRepository.findAll();
            
            if (employees.isEmpty()) {
                throw new EmployeeNotFoundException("No employees found in the system");
            }
    
            return employees.stream()
                    .map(employee -> {
                        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
                        BeanUtils.copyProperties(employee, responseDTO);
                        return responseDTO;
                    })
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException("Error occurred while fetching employees: " + e.getMessage());
        }
    }

    // get employee by id
    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    
    EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
    BeanUtils.copyProperties(employee, responseDTO);
    return responseDTO;
    }


// delete employee by id
public void deleteEmployeeById(Long id) {
    Employee employee = employeeRepository.findById(id)
    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    employeeRepository.deleteById(id);
}

// update employee by id
public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO requestDTO) {
    Employee employee = employeeRepository.findById(id)
    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    BeanUtils.copyProperties(requestDTO, employee);

    employee = employeeRepository.save(employee);

    EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
    BeanUtils.copyProperties(employee, responseDTO);
    return responseDTO;
}
}