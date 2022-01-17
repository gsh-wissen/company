package com.demo.company.service;

import com.demo.company.client.CompanyClient;
import com.demo.company.exception.ResourceNotFoundException;
import com.demo.company.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    CompanyClient companyClient;

    public List<Employee> findAllEmployees() {
        return companyClient.getAllEmployees();
    }

    public ResponseEntity<Employee> findEmployeeById(Long employeeId) {
        return companyClient.getEmployeeById(employeeId);
    }

    public Employee createEmployee(Employee employee) {
        return companyClient.createEmployee(employee);
    }

    public ResponseEntity<Employee> updateEmployee(Long employeeId, Employee employeeDetails) throws ResourceNotFoundException {

        Employee employee = companyClient.getEmployeeById(employeeId).getBody();
        if(employee !=null){
            employee.setEmailId(employeeDetails.getEmailId());
            employee.setLastName(employeeDetails.getLastName());
            employee.setFirstName(employeeDetails.getFirstName());
            return companyClient.updateEmployee(employeeId, employee);
        }else{
            throw new ResourceNotFoundException("Employee not found for this id :: " + employeeId);
        }
    }

    public Map<String, Boolean> deleteEmployee(Long employeeId) throws ResourceNotFoundException {
        Employee employee = companyClient.getEmployeeById(employeeId).getBody();
        if (employee != null) {
            return companyClient.deleteEmployee(employeeId);
        }else{
            throw new ResourceNotFoundException("Employee not found for this id :: " + employeeId);
        }
    }
}
