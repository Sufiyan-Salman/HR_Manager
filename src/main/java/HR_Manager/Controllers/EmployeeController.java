package HR_Manager.Controllers;


import HR_Manager.Entities.Employee;
import HR_Manager.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


//    @GetMapping("/Employee/{id}")
//    public ResponseEntity getEmployee(@PathVariable(value = "id") Long employeeId){
//        System.out.println("getting empl against id");
//        return ResponseEntity.ok(employeeService.getEmployee(employeeId));
//    }
    @GetMapping("/Employee/{name}")
    public ResponseEntity searchByName(@PathVariable(value = "name") String name){
        System.out.println("searching using names");
        return ResponseEntity.ok(employeeService.searchEmployeesByName(name));
    }

    @GetMapping("/Employee/")
    public ResponseEntity getAllEmployees(){
        System.out.println("getting all employess");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PutMapping("/Employee/")
    public ResponseEntity updateEmployee(@RequestBody Employee employee){
        System.out.println("updating empl");
        employeeService.updateEmployee(employee);
        return ResponseEntity.ok(Map.of("status","Updated Successfully!"));

    }
    @PostMapping("/Employee/")
    public ResponseEntity addEmployee(@RequestBody Employee employee){
        System.out.println("adding empl");
        employeeService.addEmployee(employee);
        return ResponseEntity.ok(Map.of("status","Added Successfully!"));

    }

    @DeleteMapping("/Employee/{id}")
    public ResponseEntity deleteEmployee(@PathVariable(value = "id") Long employeeId) {

        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok(Map.of("status","Deleted Successfully!"));



    }


}
