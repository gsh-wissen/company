package com.demo.company.test.service;

import com.demo.company.client.CompanyClient;
import com.demo.company.exception.ResourceNotFoundException;
import com.demo.company.model.Employee;
import com.demo.company.service.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import javax.xml.ws.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceImplTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    ObjectMapper om = new ObjectMapper();

    private Employee employee;
    List<Employee> employeeList;

    @MockBean
    CompanyClient companyClient;

    @Autowired
    EmployeeServiceImpl employeeService;

    @Before
    public void init(){
        employee = new Employee();
        employee.setFirstName("girish");
        employee.setLastName("shankar");
        employee.setEmailId("girish.shanakr@gmail.com");
        employeeList = Arrays.asList(employee);
    }

    @Test
    public void findAllEmployeesTest(){
        when(companyClient.getAllEmployees()).thenReturn(employeeList);
        List<Employee> list = employeeService.findAllEmployees();
        Assert.assertNotNull(list);
    }

    @Test(expected = FeignException.class)
    public void findAllEmployeesFeignExceptionTest(){
        when(companyClient.getAllEmployees()).thenThrow(FeignException.class);
        List<Employee> list = employeeService.findAllEmployees();
        Assert.assertNull(list);
    }

    @Test
    public void findEmployeeByIdTest(){
        ResponseEntity<Employee> responseEntity = new ResponseEntity<Employee>(employee, HttpStatus.OK);
        when(companyClient.getEmployeeById(Mockito.anyLong())).thenReturn(responseEntity);
        ResponseEntity<Employee> response = employeeService.findEmployeeById(Long.valueOf(1));
        Assert.assertNotNull(response);
    }

    @Test(expected = Exception.class)
    public void findEmployeeByIdExceptionTest() throws ResourceNotFoundException{
        when(companyClient.getEmployeeById(Mockito.anyLong())).thenThrow(ResourceNotFoundException.class);
        ResponseEntity<Employee> response = employeeService.findEmployeeById(Long.valueOf(1));
        Assert.assertNull(response);
    }

    @Test
    public void createEmployeeTest(){
        when(companyClient.createEmployee(Mockito.any())).thenReturn(employee);
        Employee emp = employeeService.createEmployee(employee);
        Assert.assertNotNull(emp);
    }

    @Test
    public void updateEmployeeTest() throws ResourceNotFoundException {
        this.employee.setId(1L);
        ResponseEntity<Employee> responseEntity = new ResponseEntity<Employee>(employee, HttpStatus.OK);
        when(companyClient.getEmployeeById(Mockito.anyLong())).thenReturn(responseEntity);
        when(companyClient.updateEmployee(Mockito.anyLong(), Mockito.any())).thenReturn(responseEntity);
        ResponseEntity<Employee> response = employeeService.updateEmployee(1L, employee);
        Assert.assertNotNull(response);
    }

    @Test
    public void deleteEmployee() throws ResourceNotFoundException {
        Map<String, Boolean> responseMap = new HashMap<>();
        responseMap.put("deleted", Boolean.TRUE);
        this.employee.setId(1L);
        ResponseEntity<Employee> responseEntity = new ResponseEntity<Employee>(employee, HttpStatus.OK);
        when(companyClient.getEmployeeById(Mockito.anyLong())).thenReturn(responseEntity);
        when(companyClient.deleteEmployee(Mockito.anyLong())).thenReturn(responseMap);
        Map<String, Boolean> response = employeeService.deleteEmployee(1L);
        Assert.assertNotNull(response);
    }

}
