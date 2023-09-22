package HR_Manager.Controllers;


import HR_Manager.Entities.Employee;
import HR_Manager.Jwt.JwtRequest;
import HR_Manager.Jwt.JwtResponse;
import HR_Manager.Jwt.JwtService;
import HR_Manager.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    //========================
    //for login and jwt generation
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    JwtService jwtService;

    @PostMapping("/Employee/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        // authenticatnig provided username and email
        this.doAuthenticate(request.getEmail(), request.getPassword());

        // extracting user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.jwtService.generateToken(userDetails);

        // creating JWT token
        JwtResponse response= new JwtResponse(token , userDetails.getUsername());
//        JwtResponse response = JwtResponse.builder()
//                .jwtToken(token)
//                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }


//    @PostMapping("/Employee/login") //yahn se jwt token generate hoga
//    public ResponseEntity login(@RequestBody JwtRequest jwtRequest){
//        System.out.println("adding empl");
////        employeeService.addEmployee(employee);
//        return ResponseEntity.ok(Map.of("status","Added Successfully!"));
//
//    }



    // login ends here
    //=====================

    @PostMapping("/Employee/signup")
    public ResponseEntity addEmployee(@RequestBody Employee employee){
        System.out.println("adding empl");
        employeeService.addEmployee(employee);
        return ResponseEntity.ok(Map.of("status","Added Successfully!"));

    }

//    @GetMapping("/Employee/{id}")
//    public ResponseEntity getEmployee(@PathVariable(value = "id") Long employeeId){
//        System.out.println("getting empl against id");
//        return ResponseEntity.ok(employeeService.getEmployee(employeeId));
//    }
    @GetMapping("/Employee/{name}")
    public ResponseEntity searchByName(@PathVariable(value = "name") String name){
        System.out.println("searching using names");
        return ResponseEntity.ok(employeeService.searchEmployeesByName2(name));
//        return ResponseEntity.ok(employeeService.searchEmployeesByName(name));
    }
    //
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

    @PutMapping("/Employee/")
    public ResponseEntity updateEmployee(@RequestBody Employee employee){
        System.out.println("updating empl");
        employeeService.updateEmployee(employee);
        return ResponseEntity.ok(Map.of("status","Updated Successfully!"));

    }
    //====
    //    @GetMapping("/Employee/{age}")
    //    public ResponseEntity getAllEmployeesByAge(@PathVariable(value = "age") int age){
    //        System.out.println("getting all employees by age");
    //        return ResponseEntity.ok(employeeService.getAllEmployeesByAge(age));
    //    }
    //====

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }
    @DeleteMapping("/Employee/{id}")
    public ResponseEntity deleteEmployee(@PathVariable(value = "id") Long employeeId) {

        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok(Map.of("status","Deleted Successfully!"));



    }


}
