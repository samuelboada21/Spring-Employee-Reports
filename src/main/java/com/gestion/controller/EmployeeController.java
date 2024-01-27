
package com.gestion.controller;

import com.gestion.model.Employee;
import com.gestion.model.service.EmployeeService;
import com.gestion.util.pagination.PageRender;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public String viewDetailsEmployee(@PathVariable(value = "id") Long id, Map<String,Object> model, RedirectAttributes flash){
        Employee employee = employeeService.findOne(id);
        if(employee==null){
            flash.addFlashAttribute("error", "Employee does not exist in the database");
            return "redirect:/employeelist";
        }
        
        model.put("employee", employee);
        model.put("title", "Employee details: "+employee.getFirstName());
        return "view";
    }
    
    @GetMapping({"/","/employeelist",""})
    public String listEmployees(@RequestParam(name = "page", defaultValue = "0") int page, Model model){
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Employee> employees = employeeService.findAll(pageRequest);
        PageRender<Employee> pageRender = new PageRender<>("/employeelist", employees);
        
        model.addAttribute("title", "Employees List");
        model.addAttribute("employees", employees);
        model.addAttribute("page", pageRender);
        
        return "employeelist";
    }
    
    @GetMapping("/form")
    public String viewAddEmployeeForm(Map<String, Object> model){
        Employee employee = new Employee();
        model.put("employee", employee);
        model.put("title", "Employee registration");
        
        return "form";
    }
    
    @PostMapping("/form")
    public String saveEmployee(@Valid Employee employee, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status){
        if(result.hasErrors()){
            model.addAttribute("title", "Employee registration");
            return "form";
        }
        
        String message = (employee.getId() != null) ? "Employee has been successfully updated" : "Employee has been successfully registered" ;
        
        employeeService.save(employee);
        status.setComplete();
        flash.addFlashAttribute("succes", message);
        return "redirect:/employeelist";
    }
    
    @GetMapping("/form/{id}")
    public String updateEmployee(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash){
        Employee employee = null;
        if(id>0){
            employee = employeeService.findOne(id);
            if(employee == null){
                flash.addFlashAttribute("error", "Employee ID does not exist in the database");
                return "redirect:/employeelist";
            }
        }else{
            flash.addFlashAttribute("error", "Employee ID cannot be zero");
            return "redirect:/employeelist";
        }
        
        model.put("employee", employee);
        model.put("title", "Employee Update");
        
        return "form";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable(value = "id") Long id, RedirectAttributes flash){
        if(id>0){
            employeeService.delete(id);
            flash.addFlashAttribute("success", "Employee successfully removed");
        }
        return "redirect:/employeelist";
    }
}
