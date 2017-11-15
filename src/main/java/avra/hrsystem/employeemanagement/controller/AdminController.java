package avra.hrsystem.employeemanagement.controller;

import avra.hrsystem.employeemanagement.model.Employee;
import avra.hrsystem.employeemanagement.repository.EmployeeRepository;
import avra.hrsystem.employeemanagement.service.DevService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private DevService devService;
    private EmployeeRepository employeeRepository;

    public AdminController(DevService devService, EmployeeRepository employeeRepository) {
        this.devService = devService;
        this.employeeRepository = employeeRepository;
        List<Employee> list=employeeRepository.findAll();
        System.out.println("s");
    }

    @GetMapping
    public void foo(){
        System.out.println("GET: foo");
    }
}
