package com.chicha.carshop.data.services;

import com.chicha.carshop.data.Employee;
import com.chicha.carshop.data.repos.EmployeeRepository;

import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    Optional<Employee> getEmployeeById(int id) {
        return employeeRepository.findById(id);
    }

    Employee updateEmployee(Employee newEmployee, int id) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee = newEmployee;
                    return employeeRepository.save(employee);
                })
                .orElseGet(()-> employeeRepository.save(newEmployee));
    }
}
