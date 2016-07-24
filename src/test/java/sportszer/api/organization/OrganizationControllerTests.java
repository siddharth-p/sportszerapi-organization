package sportszer.api.organization;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sportszer.api.organization.bean.Organization;
import sportszer.api.organization.dao.OrganizationDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SportszerapiOrganizationApplication.class, locations = { "/mock-applicationContext.xml" })
@WebAppConfiguration
public class OrganizationControllerTests {

	private MockMvc mvc;

	@Autowired
	private OrganizationDAO mockOrganizationDAO;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		EasyMock.reset(mockOrganizationDAO);
	}

	@After
	public void tearDOwn() {
		EasyMock.verify(mockOrganizationDAO);
	}

	@Test
	public void testRetrieveOrganizations() throws Exception {
		setRetrieveOrganizationsMockExpectations();

		mvc.perform(MockMvcRequestBuilders.get("/v1/organizations"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(getJSON(getOrganizations())));
	}

	@Test
	public void testRetrieveOrganizationByName() throws Exception {
		setRetrieveOrganizationByNameMockExpectations();

		mvc.perform(MockMvcRequestBuilders.get("/v1/organizations/cubs"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(getJSON(getOrganizationList(getOrganizationByName("cubs")))));
	}
	
	@Test
	public void testAddOrganization() throws Exception {
		Organization organization = new Organization("1234", "Cubs");
		setAddOrganizationMockExpectations(organization);		

		mvc.perform(MockMvcRequestBuilders.post("/v1/organizations")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getJSON(organization)))				
				.andExpect(status().isOk());
	}
	
	@Test
	public void testUpdateOrgzanization() throws Exception {
		Organization organization = new Organization("1234", "Cubs");
		setUpdateOrganizationMockExpectations(organization);		

		mvc.perform(MockMvcRequestBuilders.put("/v1/organizations")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getJSON(organization)))				
				.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteOrgzanization() throws Exception {
		Organization organization = new Organization("1234", "Cubs");
		setDeleteOrganizationMockExpectations(organization);

		mvc.perform(MockMvcRequestBuilders.delete("/v1/organizations")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getJSON(organization)))				
				.andExpect(status().isOk());
	}
	
	@Test
	public void testThrowException() throws Exception {
		Organization organization = new Organization("1234", "Cubs");
		setUpdateOrganizationMockExpectationsToThrowException(organization);		

		mvc.perform(MockMvcRequestBuilders.put("/v1/organizations")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getJSON(organization)))				
				.andExpect(status().isInternalServerError());
	}

	private void setRetrieveOrganizationsMockExpectations() {
		EasyMock.expect(mockOrganizationDAO.retrieveOrganizations()).andReturn(getOrganizations());
		EasyMock.replay(mockOrganizationDAO);
	}

	private void setRetrieveOrganizationByNameMockExpectations() {
		EasyMock.expect(mockOrganizationDAO.retrieveOrganizationByName("cubs"))
				.andReturn(getOrganizationList(getOrganizationByName("cubs")));
		EasyMock.replay(mockOrganizationDAO);
	}
	
	private void setAddOrganizationMockExpectations(Organization organization) {
		mockOrganizationDAO.addOrganization(organization);
		EasyMock.expectLastCall();
		EasyMock.replay(mockOrganizationDAO);
	}
	
	private void setUpdateOrganizationMockExpectations(Organization organization) {
		mockOrganizationDAO.updateOrganization(organization);
		EasyMock.expectLastCall();
		EasyMock.replay(mockOrganizationDAO);
	}
	
	private void setUpdateOrganizationMockExpectationsToThrowException(Organization organization) {
		mockOrganizationDAO.updateOrganization(organization);
		EasyMock.expectLastCall().andThrow(new RuntimeException());
		EasyMock.replay(mockOrganizationDAO);
	}
	
	private void setDeleteOrganizationMockExpectations(Organization organization) {
		mockOrganizationDAO.deleteOrganization(organization);
		EasyMock.expectLastCall();
		EasyMock.replay(mockOrganizationDAO);
	}
	
	private List<Organization> getOrganizationList(Organization... organizations) {
		List<Organization> organizationList = new ArrayList<>();

		for (Organization organization : organizations) {
			organizationList.add(organization);
		}

		return organizationList;
	}

	private Organization[] getOrganizations() {
		Organization[] organizations = new Organization[2];
		organizations[0] = new Organization("1234", "Cubs");
		organizations[1] = new Organization("1235", "Cardinals");

		return organizations;
	}
	
	private Organization getOrganizationByName(String name) {
		return new Organization("1234", name);
	}

	private String getJSON(Object object) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
}
