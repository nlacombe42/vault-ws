package net.nlacombe.vault.vaultws.api.webservice;

import net.nlacombe.vault.vaultws.api.dto.Budget;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetCreationRequest;
import net.nlacombe.vault.vaultws.api.meta.VaultWsPathConstants;
import org.springframework.cloud.netflix.feign.FeignClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@FeignClient(serviceId = "vault-ws", path = VaultWsPathConstants.API_PATH_PREFIX)
@Path(VaultWsPathConstants.V1_URL_PATH + "/budgets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface BudgetWebService
{
	@POST
	@Path("/month")
	Budget createMonthBudget(MonthBudgetCreationRequest monthBudgetCreationRequest);

	@GET
	List<Budget> getBudgets(@QueryParam("startDate") String startDateText, @QueryParam("endDate") String endDateText);
}
