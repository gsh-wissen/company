package com.demo.company.test.controller;

import com.demo.company.model.Employee;
import com.demo.company.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeService;

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    ObjectMapper om = new ObjectMapper();
    private Employee employee;
    List<Employee> employeeList;

    @Before
    public void beforeMethod(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        employee = new Employee();
        employee.setFirstName("girish");
        employee.setLastName("shankar");
        employee.setEmailId("girish.shanakr@gmail.com");
        employeeList = Arrays.asList(employee);
    }

    @Test
    public void getAllEmployeesTest() throws Exception {
        when(employeeService.findAllEmployees()).thenReturn(this.employeeList);
        mockMvc.perform(get("/company/api/employees")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getEmployeeByIdTest() throws Exception {
        when(employeeService.findEmployeeById(Mockito.anyLong()))
                .thenReturn(new ResponseEntity<Employee>(HttpStatus.OK));
        mockMvc.perform(get("/company/api/employees/1")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void createEmployeeTest() throws Exception {

        String jsonRequest = om.writeValueAsString(this.employee);
        when(employeeService.createEmployee(ArgumentMatchers.any())).thenReturn(this.employee);
        mockMvc.perform(post("/company/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void updateEmployeeTest() throws Exception {
        Employee emp = new Employee();
        emp.setId(1);
        emp.setFirstName("abc");
        emp.setLastName("xyz");
        emp.setEmailId("abc@xyz.com");
        String jsonRequest = om.writeValueAsString(emp);
        when(employeeService.updateEmployee(ArgumentMatchers.anyLong(),ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        mockMvc.perform(put("/company/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEmployeeTest() throws Exception {
        Map<String, Boolean> responseMap = new HashMap<>();
        responseMap.put("deleted", Boolean.TRUE);
        employee.setId(1);
        when(employeeService.deleteEmployee(ArgumentMatchers.anyLong()))
                .thenReturn(responseMap);
        mockMvc.perform(delete("/company/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
