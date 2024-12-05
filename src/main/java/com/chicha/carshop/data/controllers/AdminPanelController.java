package com.chicha.carshop.data.controllers;
import com.chicha.carshop.data.Accessright;
import com.chicha.carshop.data.DTO.EmployeeDTO;
import com.chicha.carshop.data.Employee;
import com.chicha.carshop.data.repos.EmployeeRepository;
import com.chicha.carshop.data.services.EmployeeService;
import com.chicha.carshop.security.AuthResponse;
import com.chicha.carshop.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminPanelController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.convertToEntity(employeeDTO);
        String email = employee.getEmail();
        String password = employee.getPassword();
        String fullname = employee.getFullname();
        Accessright accessRight = employee.getAccessright();
        Employee isExist = employeeRepository.findByEmail(email);
        if (isExist != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Employee createdEmployee = new Employee();
        createdEmployee.setFullname(fullname);
        createdEmployee.setAccessright(accessRight);
        createdEmployee.setEmail(email);
        createdEmployee.setPassword(passwordEncoder.encode(password));
        Employee savedEmployee = employeeService.saveEmployee(createdEmployee);
        Authentication auth = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = JwtProvider.generateToken(auth);

        AuthResponse authenticatorResponse = new AuthResponse();
        authenticatorResponse.setJwt(token);
        authenticatorResponse.setStatus(true);
        authenticatorResponse.setMessage("Success");
        return new ResponseEntity<AuthResponse>(authenticatorResponse, HttpStatus.OK);

    }

}
