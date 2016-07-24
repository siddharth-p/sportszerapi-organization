package sportszer.api.organization.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.SaveBehavior;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import sportszer.api.organization.bean.Organization;;

/**
 * Sportszer Organization Data Access
 * 
 * @author Siddharth Pandey
 * @since July 20, 2016
 */
@Repository
public class OrganizationDAO {

	// TODO Inject env specific AmazonDynamoDBClient bean
	//private AmazonDynamoDBClient client = new AmazonDynamoDBClient().withEndpoint("http://localhost:8000");
	private AmazonDynamoDBClient client = new AmazonDynamoDBClient().withRegion(Regions.US_WEST_2);

	/**
	 * @return all Sportszer Organizations
	 */
	public Organization[] retrieveOrganizations() {

		DynamoDBMapper dbMapper = new DynamoDBMapper(client);

		List<Organization> organizationList = dbMapper.scan(Organization.class, new DynamoDBScanExpression());
		Organization[] organization = new Organization[organizationList.size()];

		return organizationList.toArray(organization);
	}

	/**
	 * @return a specific Sportszer Organization, searched by name
	 */
	public List<Organization> retrieveOrganizationByName(String organizationName) {
		DynamoDBMapper mapper = new DynamoDBMapper(client);

		Organization retrievedOrganization = new Organization();
		retrievedOrganization.setName(organizationName);

		DynamoDBQueryExpression<Organization> queryExpression = new DynamoDBQueryExpression<Organization>();
		queryExpression.setIndexName("name");
		queryExpression.setHashKeyValues(retrievedOrganization);
		queryExpression.setConsistentRead(false);

		List<Organization> organizations = mapper.query(Organization.class, queryExpression);

		System.out.println(organizations);

		return organizations;
	}

	/**
	 * Add a Sportszer Organization
	 * 
	 * @param organization
	 */
	public void addOrganization(Organization organization) {
		DynamoDBMapper dbMapper = new DynamoDBMapper(client);
		dbMapper.save(organization);
	}

	/**
	 * Update a Sportszer Organization
	 * 
	 * @param organization
	 */
	public void updateOrganization(Organization organization) {
		DynamoDBMapper dbMapper = new DynamoDBMapper(client);
		dbMapper.save(organization, new DynamoDBMapperConfig(SaveBehavior.CLOBBER));
	}

	/**
	 * Delete a Sportszer Organization
	 * 
	 * @param organization
	 */
	public void deleteOrganization(Organization organization) {
		DynamoDBMapper dbMapper = new DynamoDBMapper(client);
		dbMapper.delete(organization);
	}
}