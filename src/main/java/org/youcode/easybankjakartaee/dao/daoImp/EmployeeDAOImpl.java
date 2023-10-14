package org.youcode.easybankjakartaee.dao.daoImp;

import org.youcode.easybankjakartaee.connection.DB;
import org.youcode.easybankjakartaee.dao.EmployeeDAO;
import org.youcode.easybankjakartaee.entities.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDAOImpl implements EmployeeDAO {
    private final Connection conn;

    public EmployeeDAOImpl() {
        conn = DB.getInstance().getConnection();
    }
    public EmployeeDAOImpl(Connection connection) {
        conn = connection;
    }

    @Override
    public Optional<Employee> create(Employee employee) {
        String insertSQL = "INSERT INTO employees (firstName, lastName, birthDate, phone, address, recruitmentDate, email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING matricule";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(employee.getBirthDate()));
            preparedStatement.setString(4, employee.getPhone());
            preparedStatement.setString(5, employee.getAddress());
            preparedStatement.setDate(6, java.sql.Date.valueOf(employee.getRecruitmentDate()));
            preparedStatement.setString(7, employee.getEmail());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                //throw new EmployeeException("Creating employee failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int matricule = generatedKeys.getInt(1);
                    employee.setMatricule(matricule);
                } else {
                    //throw new EmployeeException("Creating employee failed, no ID obtained.");
                }
            }

            return Optional.of(employee);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Employee> update(Employee employee) {
        String updateSQL = "UPDATE employees " +
                "SET firstName = ?, lastName = ?, birthDate = ?, phone = ?, address = ?, " +
                "recruitmentDate = ?, email = ? " +
                "WHERE matricule = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setDate(3, java.sql.Date.valueOf(employee.getBirthDate()));
            preparedStatement.setString(4, employee.getPhone());
            preparedStatement.setString(5, employee.getAddress());
            preparedStatement.setDate(6, java.sql.Date.valueOf(employee.getRecruitmentDate()));
            preparedStatement.setString(7, employee.getEmail());
            preparedStatement.setInt(8, employee.getMatricule());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                //throw new EmployeeException("Updating employee failed, no rows affected.");
            }
            return Optional.of(employee);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(Integer matricule) {
        String deleteSQL = "DELETE FROM employees WHERE matricule = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, matricule);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Employee> findByID(Integer matricule) {
        String selectSQL = "SELECT * FROM employees WHERE matricule = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
            preparedStatement.setInt(1, matricule);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setMatricule(resultSet.getInt("matricule"));
                    employee.setFirstName(resultSet.getString("firstName"));
                    employee.setLastName(resultSet.getString("lastName"));
                    employee.setBirthDate(resultSet.getDate("birthDate").toLocalDate());
                    employee.setPhone(resultSet.getString("phone"));
                    employee.setAddress(resultSet.getString("address"));
                    employee.setRecruitmentDate(resultSet.getDate("recruitmentDate").toLocalDate());
                    employee.setEmail(resultSet.getString("email"));

                    return Optional.of(employee);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM employees";

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAllSQL)) {

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setMatricule(resultSet.getInt("matricule"));
                employee.setFirstName(resultSet.getString("firstName"));
                employee.setLastName(resultSet.getString("lastName"));
                employee.setBirthDate(resultSet.getDate("birthDate").toLocalDate());
                employee.setPhone(resultSet.getString("phone"));
                employee.setAddress(resultSet.getString("address"));
                employee.setRecruitmentDate(resultSet.getDate("recruitmentDate").toLocalDate());
                employee.setEmail(resultSet.getString("email"));

                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    @Override
    public boolean deleteAll() {
        boolean deleted = false;
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM employees");
            int rows = ps.executeUpdate();

            if (rows > 0) {
                deleted = true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public List<Employee> findByAttribute(String searchValue){
        List<Employee> employees = new ArrayList<>();

        String selectByAttributeSQL = "SELECT * FROM employees WHERE " +
                "matricule::TEXT LIKE ? OR " +
                "firstName LIKE ? OR " +
                "lastName LIKE ? OR " +
                "birthDate::TEXT LIKE ? OR " +
                "phone LIKE ? OR " +
                "address LIKE ? OR " +
                "recruitmentDate::TEXT LIKE ? OR " +
                "email LIKE ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(selectByAttributeSQL)) {
            String wildcardSearchValue = "%" + searchValue + "%";
            for (int i = 1; i <= 8; i++) {
                preparedStatement.setString(i, wildcardSearchValue);
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setMatricule(resultSet.getInt("matricule"));
                    employee.setFirstName(resultSet.getString("firstName"));
                    employee.setLastName(resultSet.getString("lastName"));
                    employee.setBirthDate(resultSet.getDate("birthDate").toLocalDate());
                    employee.setPhone(resultSet.getString("phone"));
                    employee.setAddress(resultSet.getString("address"));
                    employee.setRecruitmentDate(resultSet.getDate("recruitmentDate").toLocalDate());
                    employee.setEmail(resultSet.getString("email"));

                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            //throw new EmployeeException("Error searching for employees by attribute.");
        }

        return employees;
    }




}
