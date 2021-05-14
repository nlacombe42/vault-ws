package net.nlacombe.vault.vaultws.api.webservice;

import net.nlacombe.vault.vaultws.api.dto.CategorizeRequest;
import net.nlacombe.vault.vaultws.api.dto.PaginationResponse;
import net.nlacombe.vault.vaultws.api.dto.SearchTransactionsRequest;
import net.nlacombe.vault.vaultws.api.dto.SplitTransactionRequest;
import net.nlacombe.vault.vaultws.api.dto.Transaction;
import net.nlacombe.vault.vaultws.api.meta.VaultWsPathConstants;
import net.nlacombe.wsutils.restexception.exception.NotFoundRestException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;

@Path(VaultWsPathConstants.V1_URL_PATH + "/transactions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface TransactionWebService
{
	@POST
	Transaction createTransaction(Transaction transaction);

	/**
	 * @throws NotFoundRestException when the account is not found or not associated to the user.
	 */
	@GET
	@Path("/count")
	int countTransactions(@QueryParam("accountId") int accountId,
						  @QueryParam("datetime") String datetime,
						  @QueryParam("description") String description,
						  @QueryParam("amount") BigDecimal amount) throws NotFoundRestException;

	@POST
	@Path("/search")
	PaginationResponse<Transaction> searchTransactions(SearchTransactionsRequest searchTransactionsRequest);

	@GET
	@Path("/uncategorized")
	List<Transaction> getUncategorizedTransactions();

	@PUT
	@Path("/{transactionId}/category")
	void categorizeTransaction(@PathParam("transactionId") int transactionId, CategorizeRequest categorizeRequest);

	@GET
	@Path("/{transactionId}")
	Transaction getTransaction(@PathParam("transactionId") int transactionId);

	@DELETE
	@Path("/{transactionId}")
	void deleteTransaction(@PathParam("transactionId") int transactionId);

	@DELETE
	@Path("/temporary")
	void deleteTemporaryTransactions();

	@POST
	@Path("/split")
	void splitTransaction(SplitTransactionRequest splitTransactionRequest);
}
