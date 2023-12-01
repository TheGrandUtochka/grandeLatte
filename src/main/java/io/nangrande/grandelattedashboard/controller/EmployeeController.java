package io.nangrande.grandelattedashboard.controller;
import io.nangrande.grandelattedashboard.model.Employee;
import io.nangrande.grandelattedashboard.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import io.nangrande.grandelattedashboard.shared.GenericResponse;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping
    public GenericResponse registerEmployee(@Valid @RequestBody Employee employee) {
        employeeService.save(employee);
        return new GenericResponse("Пользователь сохранён");
    }
}
