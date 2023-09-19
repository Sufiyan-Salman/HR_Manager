package HR_Manager;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.connection.ConnectionPoolSettings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class HR_Manager {
    public static void main(String[] args) {

//        System.out.println("Hello world!");

        SpringApplication.run(HR_Manager.class, args);
    }
//    @Override
//    public MongoClient mongoClient() {
//        ConnectionString connectionString = new ConnectionString(String.format(CONNECTION_STRING, host, port, database));
//        MongoClientSettings clientSettings = MongoClientSettings.builder()
//                .retryWrites(true)
//                .applyConnectionString(connectionString)
//                .applyToConnectionPoolSettings((ConnectionPoolSettings.Builder builder) -> {
//                    builder.maxSize(100) //connections count
//                            .minSize(5)
//                            .maxConnectionLifeTime(30, TimeUnit.MINUTES)
//                            .maxConnectionIdleTime( maxIdleTime, TimeUnit.MILLISECONDS);
//                })
//                .applyToSocketSettings(builder -> {
//                    builder.connectTimeout(2000, TimeUnit.MILLISECONDS);
//                })
//                .applicationName(appName)
//                .build();
//
//        return MongoClients.create(clientSettings);
//    }
}