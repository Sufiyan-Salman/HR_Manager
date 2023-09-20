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

    //we have to create an api which will take query parameters
//    age and name , if one is present , lets say age , so find by age , or if name , so we have to search by its initialls
    // if both are present , search by both
//    /============
    @GetMapping("/Employee")
    public ResponseEntity search(
            @RequestParam(name = "age", required = false) Integer age,
            @RequestParam(name = "name", required = false) String name) {
        if (age != null  && name != null) {
            System.out.println("came in search method with both age and name" );
            // Both "age" and "name" parameters are present, call method A
            return ResponseEntity.ok(employeeService.searchEmployeesByNameAndAge(age, name));
        } else if (age != null ) {
            System.out.println("came in search method with only age " );
            // Only "age" parameter is present, call method B
            return ResponseEntity.ok(employeeService.getAllEmployeesByAge(age));
        } else if (name != null) {
            System.out.println("came in search method with only name" );
            // Only "name" parameter is present, call method C
            return ResponseEntity.ok(employeeService.searchEmployeesByName(name));
        } else {
            // No parameters are present
            return ResponseEntity.badRequest().build();
        }
    }

//    /============


    @GetMapping("/Employee/")
    public ResponseEntity getAllEmployees(){
        System.out.println("getting all employess");
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
//    @GetMapping("/Employee/{age}")
//    public ResponseEntity getAllEmployeesByAge(@PathVariable(value = "age") int age){
//        System.out.println("getting all employees by age");
//        return ResponseEntity.ok(employeeService.getAllEmployeesByAge(age));
//    }

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
