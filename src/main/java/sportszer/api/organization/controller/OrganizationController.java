package sportszer.api.organization.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import sportszer.api.organization.bean.Organization;
import sportszer.api.organization.dao.OrganizationDAO;

/**
 * Defines Sportszer Organization API
 * 
 * @author Siddharth Pandey
 * @since July 20, 2016
 */
@RestController
@Api(value = "Organization API")
@RequestMapping("/v1")
public class OrganizationController {

	@Autowired
	private OrganizationDAO organizationDAO;

	@ApiOperation(value = "Retrieve Organizations")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Organization.class) })
	@RequestMapping(method = RequestMethod.GET, path = "/organizations", produces = "application/json")	
	public HttpEntity<Organization[]> retrieveOrganizations() {
		return new ResponseEntity<Organization[]>(organizationDAO.retrieveOrganizations(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Retrieve Organizations")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Organization.class) })
	@RequestMapping(method = RequestMethod.GET, path = "/organizations/{name}", produces = "application/json")	
	public HttpEntity<List<Organization>> retrieveOrganizationByName(@PathVariable String name) {
		return new ResponseEntity<List<Organization>>(organizationDAO.retrieveOrganizationByName(name), HttpStatus.OK);
	}

	@ApiOperation(value = "Add Organization")	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success") })
	@RequestMapping(method = RequestMethod.POST, path = "/organizations", consumes = "application/json")
	public HttpStatus addOrganization(@RequestBody Organization organization) {
		organizationDAO.addOrganization(organization);
		return HttpStatus.OK;
	}

	@ApiOperation(value = "Update Organization")	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success") })
	@RequestMapping(method = RequestMethod.PUT, path = "/organizations", consumes = "application/json")
	public HttpStatus updateOrganization(@RequestBody Organization organization) {
		organizationDAO.updateOrganization(organization);
		return HttpStatus.OK;
	}

	@ApiOperation(value = "Delete Organization")	
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success") })
	@RequestMapping(method = RequestMethod.DELETE, path = "/organizations", consumes = "application/json")
	public HttpStatus deleteOrganization(@RequestBody Organization organization) {
		organizationDAO.deleteOrganization(organization);
		return HttpStatus.OK;
	}
}
