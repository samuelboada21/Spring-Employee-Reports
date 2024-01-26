
package com.gestion.model.service;

import com.gestion.model.Employee;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author SamuelBoada
 */
public interface EmployeeService {
    
    public List<Employee> findAll();
    
    public Page<Employee> findAll(Pageable pageable);
    
    public void save(Employee employee);
    
    public Employee findOne(Long id);
    
    public void delete(Long id);
}
