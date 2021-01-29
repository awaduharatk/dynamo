package dynamo.access.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    public AmazonDynamoDB dynamoClient;

    @Value("${tableName}")
    public String tableName;

    @Value("${pKeys}")
    public List<String> pKeys;

    @Override
    public void run(String... args) {
        DynamoDB dynamoDB = new DynamoDB(dynamoClient);
        Table table = dynamoDB.getTable("tableName");

        ScanRequest scanRequest = new ScanRequest()
                .withTableName(tableName);

        ScanResult result = dynamoClient.scan(scanRequest);
        System.out.println("データ件数:" + result.getItems().size());

        for (Map<String, AttributeValue> item : result.getItems()) {
            delete(item);
        }
    }

    public void delete(Map<String, AttributeValue> item) {
        Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();
        for (String pKey : pKeys) {
            key.put(pKey, item.get(pKey));
        }
        DeleteItemRequest deleteItemRequest = new DeleteItemRequest()
                .withTableName(tableName)
                .withKey(key);
        DeleteItemResult deleteItemResult = dynamoClient.deleteItem(deleteItemRequest);
    }

}


