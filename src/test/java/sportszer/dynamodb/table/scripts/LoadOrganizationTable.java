package sportszer.dynamodb.table.scripts;

import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper.FailedBatch;

import sportszer.api.organization.bean.Organization;

public class LoadOrganizationTable {

	private static AmazonDynamoDBClient client = new AmazonDynamoDBClient().withEndpoint("http://localhost:8000");
	//private static AmazonDynamoDBClient client = new AmazonDynamoDBClient(new ProfileCredentialsProvider()).withRegion(Regions.US_WEST_2);
	
	public static void main(String[] args) {

		DynamoDBMapper mapper = new DynamoDBMapper(client);

		Organization organization1 = new Organization();
		organization1.setName("Cardinals");
		
		Organization organization2 = new Organization();
		organization2.setName("Bat Busters");
		
		List<FailedBatch> failedBatchs = mapper.batchSave(Arrays.asList(organization1, organization2));
		
		if(failedBatchs.isEmpty()){
			System.out.println("2 Organization records loaded");	
		}else{
			System.out.println("Error occurred while loading Organization records");
		}		
	}
}
