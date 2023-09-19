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

@Component
public class CustomEmployeeRepo {

    @Autowired
    MongoClient mongoClient;

    @Autowired
    MongoConverter mongoConverter;

    public List<Employee> searchByName(String nameToBeSearched){


        /*
         * Requires the MongoDB Java Driver.
         * https://mongodb.github.io/mongo-java-driver
         */

//        MongoClient mongoClient = new MongoClient(
//                new MongoClientURI(
//                        ""
//                )
//        );
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



}
