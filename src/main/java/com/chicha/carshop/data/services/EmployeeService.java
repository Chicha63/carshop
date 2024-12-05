package com.chicha.carshop.data.services;

import com.chicha.carshop.data.Accessright;
import com.chicha.carshop.data.DTO.EmployeeDTO;
import com.chicha.carshop.data.Employee;
import com.chicha.carshop.data.repos.AccessRightRepository;
import com.chicha.carshop.data.repos.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AccessRightRepository accessRightRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee convertToEntity(EmployeeDTO dto) {
        Accessright accessright = accessRightRepository.findById(dto.getAccessright()).orElse(null);
        return Employee.builder()
                .fullname(dto.getFullname())
                .accessright(accessright)
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public Optional<Employee> getEmployeeById(int id) {
        return employeeRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(username);
        System.out.println(employee);
        if (employee == null) {
            throw new UsernameNotFoundException("Email not found");
        }

        System.out.println("Loaded user: " + employee.getEmail());
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(
                employee.getEmail(),
                employee.getPassword(),
                authorities
        );
    }
}
