package org.youcode.easybankjakartaee.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.youcode.easybankjakartaee.dao.EmployeeDAO;
import org.youcode.easybankjakartaee.dao.daoImp.EmployeeDAOImpl;
import org.youcode.easybankjakartaee.entities.Employee;
import org.youcode.easybankjakartaee.services.EmployeeService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "EmployeeServlet", value = "/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    EmployeeDAO employeDao = new EmployeeDAOImpl();
    EmployeeService employeService = new EmployeeService(employeDao);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "edit":
                showUpdateForm(request, response);
                break;
            case "get":
                getEmploye(request, response);
                break;
            case "insert":
                insertEmploye(request, response);
                break;
            case "delete":
                deleteEmploye(request, response);
                break;
            case "update":
                updateEmploye(request, response);
                break;
            default:
                getEmployes(request, response);
                break;
        }
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer matricule = Integer.parseInt(request.getParameter("matricule"));
        Employee existingEmploye;
        try {
            Optional<Employee> employe = employeService.getEmploye(matricule);
            if (employe.isPresent()) {
                existingEmploye = employe.get();
                RequestDispatcher dispatcher = request.getRequestDispatcher("updateEmploye.jsp");
                request.setAttribute("employe", existingEmploye);
                dispatcher.forward(request, response);
            } else {
                String errorMessage = "There is no employe with this matricule.";
                request.setAttribute("errorMessage", errorMessage);
                RequestDispatcher dispatcher = request.getRequestDispatcher("updateEmploye.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertEmploye(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        LocalDate birthDate = LocalDate.parse(request.getParameter("birthDate"));
        String Phone = request.getParameter("mobile");
        LocalDate recruitmentDate = LocalDate.parse(request.getParameter("recruitmentDate"));
        String email = request.getParameter("email");
        Employee employe = new Employee(firstName, lastName, birthDate, Phone,null, recruitmentDate, email);
        Optional<Employee> optionalEmploye = employeService.addEmploye(employe);
        String message = null;
        if (optionalEmploye.isPresent()) {
            message = "Employe Inserted Successfuly";
        } else {
            message = "Employe Not Inserted";
        }
        request.setAttribute("message", message);
        getEmployes(request, response);
    }

    private void getEmployes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Employee> employes = employeService.getEmployees();
            request.setAttribute("employes", employes);
            RequestDispatcher dispatcher = request.getRequestDispatcher("employe.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getEmploye(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer matricule = Integer.parseInt(request.getParameter("matricule"));
        Optional<Employee> optionalEmploye = employeService.getEmploye(matricule);
        if (optionalEmploye.isPresent()) {
            Employee employe = optionalEmploye.get();
            request.setAttribute("employe", employe);
            RequestDispatcher dispatcher = request.getRequestDispatcher("employe.jsp");
            dispatcher.forward(request, response);
        } else {
            String message = "There is no Employe with this matricule";
            request.setAttribute("message", message);
            RequestDispatcher dispatcher = request.getRequestDispatcher("employe.jsp");
            dispatcher.forward(request, response);
        }

    }

    private void deleteEmploye(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer matricule = Integer.parseInt(request.getParameter("matricule"));
        Boolean res = employeService.deleteEmploye(matricule);
        String message = null;
        if (res){
            message = "Employe Deleted Successfuly";
        }else {
            message = "Employe Not Deleted";
        }
        request.setAttribute("message", message);
        getEmployes(request, response);
    }

    private void updateEmploye(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String matricule = request.getParameter("matricule");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        LocalDate birthDate = LocalDate.parse(request.getParameter("birthDate"));
        String mobile = request.getParameter("mobile");
        LocalDate recruitmentDate = LocalDate.parse(request.getParameter("recruitmentDate"));
        String email = request.getParameter("email");
        Employee employe = new Employee(firstName, lastName, birthDate, mobile, matricule, recruitmentDate, email);
        Optional<Employee> optionalEmploye = employeService.updateEmploye(employe);
        String message = null;
        if (optionalEmploye.isPresent()) {
            message = "Employe Updated Successfuly";
        } else {
            message = "Employe Not Updated";
        }
        request.setAttribute("message", message);
        getEmployes(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
