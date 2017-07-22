package net.nlacombe.vault.vaultws.api.webservice;

import net.nlacombe.vault.vaultws.api.dto.Transaction;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("transactions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface TransactionWebService
{
	@POST
	Transaction createTransaction(Transaction transaction);
}
