
package com.gestion.controller;

import com.gestion.model.Employee;
import com.gestion.model.service.EmployeeService;
import com.gestion.util.pagination.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author SamuelBoada
 */

@Controller
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping({"/","/listar",""})
    public String listEmployees(@RequestParam(name = "page", defaultValue = "0") int page, Model model){
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Employee> employees = employeeService.findAll(pageRequest);
        PageRender<Employee> pageRender = new PageRender<>("/listar", employees);
        
        model.addAttribute("title", "Employees List");
        model.addAttribute("employees", employees);
        model.addAttribute("page", pageRender);
        
        return "listar";
    }
}
