package HR_Manager.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.processing.Generated;
import java.util.Collection;

@Document(collection = "Employee")
//if we have to use two fields for indexing , we have to use compound indexing, we can use this in find by age and name
//@CompoundIndexes({
//        @CompoundIndex(name = "name_age", def = "{'name' : 1, 'age': 1}")
//})
public class Employee implements UserDetails {

    @Id //it will be generated automatically by mongo
    private Long employeeID;

//    @Indexed(name = "name_index", direction = IndexDirection.DESCENDING) // is se index create krne k lie use kia jata h but it doesnt create automatically , for this we have to put some config to enable auto index creation, i am wirting this below
    //even if we want to search using a single letter or prefix , index is better
    //if we want to give a complete word and then search against a word , then @TextIndexed is better in this case , koi text createria ka obj bhi use hoga isme
    private String name;
//    db.person.find({"firstName":"firstName_2500"}).explain("executionStats")
    //inddex banane k baad ye query use ki , to mujhe check ye kirna h k jab me findByName krun , to kia yahi query use hogi ya kuch alag likha hgoa , agr yahih ogi to mtlb repo methods se index use me ajata h , which is what I am trying to find , which is confirmed by gpt
//find using query: { "name" : "Salman"} fields: Document{{}} , thats the query our repo writes , so pretty much same IG , we will compare these queries usgin chat gpt
    private int age;
    //@field()//this is if we want to give another name to this attribute
    private int cnic;

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
//    @DBRef //ye ham relation me use krenge jab agar hame koi doc isme save krana h mtlb kisi or class ka obj agr is class ka obj(field ) bane, also, which should already exist






    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCnic() {
        return cnic;
    }

    public void setCnic(int cnic) {
        this.cnic = cnic;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", cnic=" + cnic +
                '}';
    }


@JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    @JsonIgnore

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore

    @Override
    public boolean isEnabled() {
        return true;
    }

//this is for indexing
//    public class MongoConfig extends AbstractMongoClientConfiguration {
//
//        // rest of the config goes here
//
//        @Override
//        protected boolean autoIndexCreation() {
//            return true;
//        }
//    }


// now as we save obj in db, it also has field called _class, it is done by spring to help to identify docs but if we remove, we might have to help spring identify by ourselves
//to remove we have to have use mongoConvertor

}
