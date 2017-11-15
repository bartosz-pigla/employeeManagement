package avra.hrsystem.employeemanagement.controller;

import avra.hrsystem.employeemanagement.model.Employee;
import avra.hrsystem.employeemanagement.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class EmployeeManagementController {
    private EmployeeRepository employeeRepository;

    public EmployeeManagementController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> read(@RequestParam(required = false)Integer pageSize, @RequestParam(required = false)Integer pageNumber){
        if(pageSize!=null && pageSize>0){
            if(pageNumber!=null && pageNumber>0){
                return employeeRepository.findAll(new PageRequest((pageNumber == null) ? 0 : pageNumber - 1, pageSize)).getContent();
            }
            else{
                return employeeRepository.findAll(new PageRequest(0, pageSize)).getContent();
            }
        }
        else{
            return employeeRepository.findAll();
        }
    }
}
