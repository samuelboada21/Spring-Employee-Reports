
package com.gestion.model.service;

import com.gestion.model.Employee;
import com.gestion.repository.EmployeeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author SamuelBoada
 */
@Service
public class EmployeeServiceImp implements EmployeeService{
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Employee> findAll(Pageable pageable) {
       return employeeRepository.findAll(pageable);
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findOne(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
    
}
