package HR_Manager.Configurations;

import HR_Manager.Entities.Employee;
import HR_Manager.Respositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    EmployeeRepo employeeRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        in our case , username is email
       System.out.println("came here in load by username");
       Employee user = this.employeeRepo.findByEmailIgnoreCase(username); //interface repo me ek alag se method banana prega with name findByUsername(String username)
        System.out.println("user id is: "+user.getEmployeeID());
        if (user == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No such user exist");// we can use either of them
        }
        return user;
//}
    }
}
