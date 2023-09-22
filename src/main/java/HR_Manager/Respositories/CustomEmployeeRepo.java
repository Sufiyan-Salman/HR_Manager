package HR_Manager.Respositories;


import HR_Manager.Entities.Employee;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
//import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


//not currently in use
@Component
public class CustomEmployeeRepo {
    //this file was created for using aggregation of mongodb

    @Autowired
    MongoClient mongoClient;

    @Autowired
    MongoConverter mongoConverter; // as we are getting documents format and we want object so what we do is we use this , this converts doc to our obj

    public List<Employee> searchByName(String nameToBeSearched){

//i used indexed and then used this aggregate to search by name thinking that indexes only work if we do this, but thats not the case , if I have annotated my field Indexed and enabled auto index creation and spring auto creates the index and then when even if I use mongorepo method : findByName , mongo will autmatically use the needed index , hence no need to explicitly write these
        //aggregation is only used complex queries where we need more filtering , grouping , and data processing , for simple queries , mongorepo and custom queeries are enough
        List<Employee> employeesList= new ArrayList<>();

        MongoDatabase database = mongoClient.getDatabase("HR_Manager");
        MongoCollection<Document> collection = database.getCollection("Employee");

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                        new Document("index", "name_Index")
                                .append("text",
                                new Document("query", nameToBeSearched).append("path", List.of("name")))),
                new Document("$sort",
                        new Document("age", -1L)),
                new Document("$limit", 5L)));


        result.forEach(document -> employeesList.add(mongoConverter.read(Employee.class, document)));
        return employeesList;
    }


//    public List<Employee> searchByNameAndAge(int age, String nameToBeSearched){
//
//
//        List<Employee> employeesList= new ArrayList<>();
//
//        MongoDatabase database = mongoClient.getDatabase("HR_Manager");
//        MongoCollection<Document> collection = database.getCollection("Employee");
//
//        Pattern regexPattern = Pattern.compile( nameToBeSearched + ".*", Pattern.CASE_INSENSITIVE);
//
//        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
////        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
//                        new Document("index", "name_Index")
//                                .append("text",
////                                new Document("query", regexPattern).append("path", List.of("name")))), //ye bas name field me search krega
//                                        new Document("query", List.of(nameToBeSearched , age)).append("path", List.of("name","age")))), //ye bas name field me search krega
//                new Document("$sort",
//                        new Document("age", -1L)),
//                new Document("$limit", 5L)));
//
//
//        result.forEach(document -> employeesList.add(mongoConverter.read(Employee.class, document)));
//        return employeesList;
//    }


    //for auto completition , I have also seen autocomplete to be written in aggregate framework

}
