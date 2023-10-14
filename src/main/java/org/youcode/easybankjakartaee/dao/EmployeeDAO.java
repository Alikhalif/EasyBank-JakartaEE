package org.youcode.easybankjakartaee.dao;

import org.youcode.easybankjakartaee.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDAO {
    Optional<Employee> create(Employee employee);
    Optional<Employee> update(Employee employee);
    boolean delete(Integer matricule);
    Optional<Employee> findByID(Integer matricule);
    List<Employee> getAll() ;
    boolean deleteAll();
    List<Employee> findByAttribute(String searchValue);
}
