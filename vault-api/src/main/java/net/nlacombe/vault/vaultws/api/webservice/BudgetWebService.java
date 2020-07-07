package net.nlacombe.vault.vaultws.api.webservice;

import net.nlacombe.vault.vaultws.api.dto.Budget;
import net.nlacombe.vault.vaultws.api.dto.BudgetUpdateRequest;
import net.nlacombe.vault.vaultws.api.dto.BudgetWithTransactions;
import net.nlacombe.vault.vaultws.api.dto.Category;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetCreationRequest;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetsInfo;
import net.nlacombe.vault.vaultws.api.meta.VaultWsPathConstants;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;

@Path(VaultWsPathConstants.V1_URL_PATH + "/budgets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface BudgetWebService
{
	@POST
	@Path("/month")
	Budget createMonthBudget(MonthBudgetCreationRequest monthBudgetCreationRequest);

	@GET
	@Path("/month/{monthIsoString}/info")
	MonthBudgetsInfo getMonthBudgetsInfo(@PathParam("monthIsoString") String monthIsoString);

	@GET
	@Path("/month/{monthIsoString}/unbudgetedCategories")
	List<Category> getUnbudgetedCategories(@PathParam("monthIsoString") String monthIsoString);

	@GET
	@Path("/{budgetId}")
	BudgetWithTransactions getBudgetWithTransactions(@PathParam("budgetId") int budgetId);

	@PUT
	@Path("/{budgetId}")
	void updateBudget(@PathParam("budgetId") int budgetId, BudgetUpdateRequest budgetUpdateRequest);
}
