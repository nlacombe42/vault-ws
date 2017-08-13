package net.nlacombe.vault.vaultws.api.webservice;

import net.nlacombe.vault.vaultws.api.dto.Transaction;
import net.nlacombe.vault.vaultws.api.meta.VaultWsPathConstants;
import net.nlacombe.wsutils.restexception.exception.NotFoundRestException;
import org.springframework.cloud.netflix.feign.FeignClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@FeignClient(serviceId = "vault-ws", path = VaultWsPathConstants.API_PATH_PREFIX)
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
}
