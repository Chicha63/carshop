package com.chicha.carshop.data.repos;

import com.chicha.carshop.data.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


}
