package HR_Manager.Respositories;

import HR_Manager.Entities.Employee;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends MongoRepository<Employee, Long> {


    Employee findByEmailIgnoreCase(String email);
    List<Employee> findByAge(int age);
    List<Employee> findByNameAndAge(String name, int age );
    List<Employee> findByName(String name );
    List<Employee> findByNameStartingWith(String prefix);


//    @Query, we use this to write our own query
//@Aggregation // used for grouping, for example to count all the no of employes based on their ages, groping empployes based non same country


}
