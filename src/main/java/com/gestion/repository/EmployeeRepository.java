
package com.gestion.repository;

import com.gestion.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Samuel Boada
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    
}
