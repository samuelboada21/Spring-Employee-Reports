package com.gestion.controller;

import com.gestion.model.Employee;
import com.gestion.model.service.EmployeeService;
import com.gestion.util.pagination.PageRender;
import com.gestion.util.reports.EmployeeExporterExcel;
import com.gestion.util.reports.EmployeeExporterPdf;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author SamuelBoada
 */
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/view/{id}")
    public String viewDetailsEmployee(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Employee employee = employeeService.findOne(id);
        if (employee == null) {
            flash.addFlashAttribute("error", "Employee does not exist in the database");
            return "redirect:/employeelist";
        }

        model.put("employee", employee);
        model.put("title", "Employee details: " + employee.getFirstName());
        return "view";
    }

    @GetMapping({"/", "/employeelist", ""})
    public String listEmployees(@RequestParam(name = "page", defaultValue = "0") int page, Model model, Authentication authentication) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Employee> employees = employeeService.findAll(pageRequest);
        PageRender<Employee> pageRender = new PageRender<>("/employeelist", employees);

        model.addAttribute("title", "Employees List");
        model.addAttribute("employees", employees);
        model.addAttribute("page", pageRender);

        boolean isAdmin = false;
        if (authentication != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    isAdmin = true;
                    break;
                }
            }
        }
        model.addAttribute("isAdmin", isAdmin);
        
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }

        return "employeelist";
    }

//    @GetMapping("/login")
//    public String showLoginForm() {
//        return "login";
//    }

    @GetMapping("/form")
    public String viewAddEmployeeForm(Map<String, Object> model) {
        Employee employee = new Employee();
        model.put("employee", employee);
        model.put("title", "Employee registration");

        return "form";
    }

    @PostMapping("/form")
    public String saveEmployee(@Valid Employee employee, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("title", "Employee registration");
            return "form";
        }

        String message = (employee.getId() != null) ? "Employee has been successfully updated" : "Employee has been successfully registered";

        employeeService.save(employee);
        status.setComplete();
        flash.addFlashAttribute("succes", message);
        return "redirect:/employeelist";
    }

    @GetMapping("/form/{id}")
    public String updateEmployee(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Employee employee = null;
        if (id > 0) {
            employee = employeeService.findOne(id);
            if (employee == null) {
                flash.addFlashAttribute("error", "Employee ID does not exist in the database");
                return "redirect:/employeelist";
            }
        } else {
            flash.addFlashAttribute("error", "Employee ID cannot be zero");
            return "redirect:/employeelist";
        }

        model.put("employee", employee);
        model.put("title", "Employee Update");

        return "form";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            employeeService.delete(id);
            flash.addFlashAttribute("success", "Employee successfully removed");
        }
        return "redirect:/employeelist";
    }

    @GetMapping("/exportPdf")
    public void exportEmployeePdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDate = dateFormatter.format(new Date());

        String header = "Content-Disposition";
        String value = "attachment; filename=Employees_" + currentDate + ".pdf";

        response.setHeader(header, value);

        List<Employee> employees = employeeService.findAll();
        EmployeeExporterPdf exporter = new EmployeeExporterPdf(employees);
        exporter.export(response);
    }

    @GetMapping("/exportExcel")
    public void exportEmployeeExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDate = dateFormatter.format(new Date());

        String header = "Content-Disposition";
        String value = "attachment; filename=Employees_" + currentDate + ".xlsx";

        response.setHeader(header, value);

        List<Employee> employees = employeeService.findAll();
        EmployeeExporterExcel exporter = new EmployeeExporterExcel(employees);
        exporter.export(response);
    }
}
