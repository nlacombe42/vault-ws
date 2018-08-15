package net.nlacombe.vault.vaultws.api.webservice;

import net.nlacombe.vault.vaultws.api.dto.Category;
import net.nlacombe.vault.vaultws.api.dto.CategoryCreationRequest;
import net.nlacombe.vault.vaultws.api.meta.VaultWsPathConstants;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path(VaultWsPathConstants.V1_URL_PATH + "/categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CategoryWebService
{
	@GET
	List<Category> getCategories();

	@POST
	Category createCategory(CategoryCreationRequest categoryCreationRequest);
}
