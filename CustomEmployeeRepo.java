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

@Component
public class CustomEmployeeRepo {

    @Autowired
    MongoClient mongoClient;

    @Autowired
    MongoConverter mongoConverter;

    public List<Employee> searchByName(String nameToBeSearched){


        List<Employee> employeesList= new ArrayList<>();

        MongoDatabase database = mongoClient.getDatabase("HR_Manager");
        MongoCollection<Document> collection = database.getCollection("Employee");

//        Pattern regexPattern = Pattern.compile( nameToBeSearched + ".*", Pattern.CASE_INSENSITIVE);

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
//        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                        new Document("index", "name_Index")
                                .append("text",
//                                new Document("query", regexPattern).append("path", List.of("name")))), //ye bas name field me search krega
                                new Document("query", nameToBeSearched+"*").append("path", List.of("name")))), //ye bas name field me search krega
                new Document("$sort",
                        new Document("age", -1L)),
                new Document("$limit", 5L)));


        result.forEach(document -> employeesList.add(mongoConverter.read(Employee.class, document)));
        return employeesList;
    }

    public List<Employee> searchByNameAndAge(int age, String nameToBeSearched){


        List<Employee> employeesList= new ArrayList<>();

        MongoDatabase database = mongoClient.getDatabase("HR_Manager");
        MongoCollection<Document> collection = database.getCollection("Employee");

        Pattern regexPattern = Pattern.compile( nameToBeSearched + ".*", Pattern.CASE_INSENSITIVE);

        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
//        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                        new Document("index", "name_Index")
                                .append("text",
//                                new Document("query", regexPattern).append("path", List.of("name")))), //ye bas name field me search krega
                                new Document("query", List.of(nameToBeSearched , age)).append("path", List.of("name","age")))), //ye bas name field me search krega
                new Document("$sort",
                        new Document("age", -1L)),
                new Document("$limit", 5L)));


        result.forEach(document -> employeesList.add(mongoConverter.read(Employee.class, document)));
        return employeesList;
    }


}
