package HR_Manager.Respositories;

import HR_Manager.Entities.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends MongoRepository<Employee, Long> {

        List<Employee> findByAge(int age);
        List<Employee> findByNameAndAge(String name, int age );
//        List<Employee> findByAgeAndName(int age , String name);




}
