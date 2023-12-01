package io.nangrande.grandelattedashboard.repository;

import io.nangrande.grandelattedashboard.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
