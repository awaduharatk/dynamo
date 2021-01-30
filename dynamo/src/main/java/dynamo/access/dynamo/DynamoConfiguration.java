package dynamo.access.dynamo;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class DynamoConfiguration {

    @Value("${localIp:#{null}}")
    public String localIp;

    @Value("${region}")
    public String region;

    @Bean
    public AmazonDynamoDB dynamoClient() {
        if (!StringUtils.isEmpty(localIp)) {
            System.out.println("local  " + localIp);
            return AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(localIp, region))
                    .build();
        } else {
            System.out.println("l");
            return AmazonDynamoDBClientBuilder.standard()
                    .withClientConfiguration(createDynamoDBClientConfiguration())
                    .withRegion(region)
                    .build();
        }
    }

    private static ClientConfiguration createDynamoDBClientConfiguration() {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setMaxConnections(80);
        return clientConfiguration;
    }
}
