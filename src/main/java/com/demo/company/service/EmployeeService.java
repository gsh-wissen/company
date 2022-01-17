package com.demo.company.service;

import com.demo.company.exception.ResourceNotFoundException;
import com.demo.company.model.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    List<Employee> findAllEmployees();
    ResponseEntity<Employee> findEmployeeById(Long employeeId) throws ResourceNotFoundException;
    Employee createEmployee(Employee employee);
    ResponseEntity<Employee> updateEmployee(Long employeeId, Employee employeeDetails) throws ResourceNotFoundException;
    Map<String, Boolean> deleteEmployee(Long employeeId) throws ResourceNotFoundException;
}
