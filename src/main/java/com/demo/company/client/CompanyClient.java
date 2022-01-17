package com.demo.company.client;

import com.demo.company.model.Employee;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Headers("Content-Type: application/json")
@FeignClient(name = "${feign.name}", url = "${feign.url}")
public interface CompanyClient {

    @GetMapping("/employees")
    List<Employee> getAllEmployees();

    @GetMapping("/employees/{id}")
    ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId);

    @PostMapping("/employees")
    Employee createEmployee(@RequestBody Employee employee);

    @PutMapping("/employees/{id}")
    ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @RequestBody Employee employee);

    @DeleteMapping("/employees/{id}")
    Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId);
}
