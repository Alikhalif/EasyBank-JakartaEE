package org.youcode.easybankjakartaee.services;

import org.youcode.easybankjakartaee.dao.EmployeeDAO;
import org.youcode.easybankjakartaee.entities.Employee;

import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private final EmployeeDAO employeDao;

    public EmployeeService(EmployeeDAO employeDao) {
        this.employeDao = employeDao;
    }

    public Optional<Employee> addEmploye(Employee employe) {
        Optional<Employee> optionalEmploye = employeDao.create(employe);
        if (optionalEmploye.isPresent()) {
            return optionalEmploye;
        } else {
            return Optional.empty();
        }
    }

    public Boolean deleteEmploye(Integer matricule) {
        Boolean res = employeDao.delete(matricule);
        return res;

    }

    public Optional<Employee> updateEmploye(Employee Employe) {
        Optional<Employee> optionalEmploye = employeDao.update(Employe);
        if (optionalEmploye.isPresent()) {
            return optionalEmploye;
        } else {
            return Optional.empty();
        }
    }

    public Optional<Employee> getEmploye(Integer matricule) {
        Optional<Employee> optionalEmploye = employeDao.findByID(matricule);
        if (optionalEmploye.isPresent()) {
            return optionalEmploye;
        } else {
            return Optional.empty();
        }
    }

    public List<Employee> getEmployees() {
        List<Employee> Employees = employeDao.getAll();
        return Employees;
    }
}
