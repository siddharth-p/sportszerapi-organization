package sportszer.dynamodb.table.scripts;

import java.util.ArrayList;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class CreateOrganizationTable {

	private static AmazonDynamoDBClient client = new AmazonDynamoDBClient().withEndpoint("http://localhost:8000");
	//private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(new ProfileCredentialsProvider()).withRegion(Regions.US_WEST_2);

	public static void main(String[] args) throws Exception {

		DynamoDB dynamoDB = new DynamoDB(client);

		String tableName = "Organization";

		System.out.println("Attempting to create table; please wait...");

		// partition key
		ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
		keySchema.add(new KeySchemaElement()
							.withAttributeName("organizationId")
							.withKeyType(KeyType.HASH));

		// global secondary index
		GlobalSecondaryIndex globalSecondaryIndex = new GlobalSecondaryIndex()
															.withIndexName("name")
															.withKeySchema(new KeySchemaElement()
																				.withAttributeName("name")
																				.withKeyType(KeyType.HASH))
															.withProvisionedThroughput(new ProvisionedThroughput()
																							.withReadCapacityUnits(5L)
																							.withWriteCapacityUnits(5L))
															.withProjection(new Projection()
																				.withProjectionType(ProjectionType.KEYS_ONLY));

		// attributes
		ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
		attributeDefinitions.add(new AttributeDefinition()
										.withAttributeName("organizationId")
										.withAttributeType(ScalarAttributeType.S));
		attributeDefinitions.add(new AttributeDefinition()
										.withAttributeName("name")
										.withAttributeType(ScalarAttributeType.S));

		// request
		CreateTableRequest request = new CreateTableRequest()
											.withTableName(tableName)
											.withKeySchema(keySchema)
											.withGlobalSecondaryIndexes(globalSecondaryIndex)
											.withAttributeDefinitions(attributeDefinitions)
											.withProvisionedThroughput(new ProvisionedThroughput()
																			.withReadCapacityUnits(5L)
																			.withWriteCapacityUnits(5L));

		Table table = dynamoDB.createTable(request);
		table.waitForActive();

		System.out.println("Success. Table status: " + table.getDescription().getTableStatus());
	}
}