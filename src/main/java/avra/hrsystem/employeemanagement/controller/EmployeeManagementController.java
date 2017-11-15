package avra.hrsystem.employeemanagement.controller;

import avra.hrsystem.employeemanagement.model.Employee;
import avra.hrsystem.employeemanagement.model.dto.ErrorMessage;
import avra.hrsystem.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/employee")
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

    @PostMapping
    public void create(@RequestBody @Valid Employee employee){
        System.out.println(employee);
    }
}
