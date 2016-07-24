package sportszer.dynamodb.table.scripts;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

public class DeleteOrganizationTable {

	private static AmazonDynamoDBClient client = new AmazonDynamoDBClient().withEndpoint("http://localhost:8000");
	//private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(new ProfileCredentialsProvider()).withRegion(Regions.US_WEST_2);

	public static void main(String[] args) throws Exception {

		DynamoDB dynamoDB = new DynamoDB(client);

		String tableName = "Organization";

		System.out.println("Attempting to delete table; please wait...");

		Table table = dynamoDB.getTable(tableName);
		table.delete();

		table.waitForDelete();
		System.out.println("Success.");
	}
}
