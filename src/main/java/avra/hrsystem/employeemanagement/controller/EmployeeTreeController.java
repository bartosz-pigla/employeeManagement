package avra.hrsystem.employeemanagement.controller;

import avra.hrsystem.employeemanagement.exceptions.WrongFieldException;
import avra.hrsystem.employeemanagement.model.Employee;
import avra.hrsystem.employeemanagement.repository.EmployeeRepository;
import avra.hrsystem.employeemanagement.service.DevService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/tree")
public class EmployeeTreeController {
    private EmployeeRepository employeeRepository;

    public EmployeeTreeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> getTree(){
        List<Employee> tree= employeeRepository.getTree();
        return tree;
    }

    @GetMapping("/orderBy/{field}")
    public List<Employee> getOrderedTree(@PathVariable String field){
        List<Employee> tree=null;

        if(field==null || field.equals("")){
            tree= employeeRepository.getTree();
        }
        else{
            tree= employeeRepository.getTree(field);
        }

        return tree;
    }
}
