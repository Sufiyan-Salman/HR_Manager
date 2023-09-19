package HR_Manager.Services;

import HR_Manager.Entities.Employee;
import HR_Manager.Respositories.CustomEmployeeRepo;
import HR_Manager.Respositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    CustomEmployeeRepo customEmployeeRepo;

    public void addEmployee(Employee employee){
        if (employeeExists(employee.getEmployeeID())) throw new ResponseStatusException(HttpStatus.CONFLICT, "User Already Exists!");
        employeeRepo.save(employee);
    }
    public boolean employeeExists(Long employeeId){
        Optional<Employee> employeeToBeFetched= employeeRepo.findById(employeeId);
        if (!employeeToBeFetched.isPresent() ) {
            return false;
        }
        return true;
    }
    public void updateEmployee(Employee employee){
        Employee employeeToBeUpdated= getEmployee(employee.getEmployeeID());
        employeeRepo.save(employee);
    }

    public Employee getEmployee( Long employeeID){
        System.out.println("get empl is called");
        Optional<Employee> employeeToBeFetched= employeeRepo.findById(employeeID);
        if (employeeToBeFetched.isEmpty()) {
            System.out.println("empl is null");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return employeeToBeFetched.get();
    }

    public void deleteEmployee(Long employeeID){
        Employee employeeToBeDeleted= getEmployee(employeeID);
        employeeRepo.delete(employeeToBeDeleted);
    }

    public List<Employee> getAllEmployees()
    {
        return employeeRepo.findAll();
    }
    public List<Employee> searchEmployeesByName(String name){
        return customEmployeeRepo.searchByName(name);
    }

}
