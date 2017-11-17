package avra.hrsystem.employeemanagement.controller;

import avra.hrsystem.employeemanagement.model.Employee;
import avra.hrsystem.employeemanagement.model.dto.ErrorMessage;
import avra.hrsystem.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
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
        employeeRepository.save(employee);
    }

    @PutMapping
    public ResponseEntity<Void> put(@RequestBody @Valid Employee employee){

        if(employeeRepository.update(employee)){
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(null);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(null);
        }
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> delete(@PathVariable Integer employeeId){
        if(employeeRepository.remove(employeeId)){
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(null);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(null);
        }
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> get(@PathVariable Integer employeeId){
        try{
            Employee employee = employeeRepository.findOne(employeeId);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(employee);
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(null);
        }
    }

    @GetMapping("/leader/{subordinateId}")
    public ResponseEntity<Integer> getLeaderOfSubordinate(@PathVariable Integer subordinateId){
        Integer leaderId=employeeRepository.findLeaderIdOfSubordinate(subordinateId);
        if(leaderId!=null && leaderId>0){
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(leaderId);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(leaderId);
        }
    }

    @PutMapping("/unAssignAllSubordinatesunAssignAllSubordinates/{id}")
    public ResponseEntity<Void> unAssignAllSubordinatesunAssignAllSubordinates(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(null);
    }

    @PutMapping("/assign/{subordinateId}/{leaderId}")
    public ResponseEntity<Void> assign(@PathVariable Integer subordinateId,@PathVariable Integer leaderId){
        if((subordinateId.intValue()!=leaderId.intValue())&&employeeRepository.assignSubordinateToLeader(subordinateId,leaderId)){
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(null);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(null);
        }
    }

    @PutMapping("/unassign/{subordinateId}")
    public ResponseEntity<Void> unassign(@PathVariable Integer subordinateId){
        if(employeeRepository.unAssignSubordinateToLeader(subordinateId)){
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(null);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(null);
        }
    }

    @PutMapping("/unassignAllSubordinates/{leaderId}")
    public ResponseEntity<Void> unAssignAllSubordinates(@PathVariable Integer leaderId){
        if(employeeRepository.unAssignAllSubordinates(leaderId)){
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(null);
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(null);
        }
    }
}