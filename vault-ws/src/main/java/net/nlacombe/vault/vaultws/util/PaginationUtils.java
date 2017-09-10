package net.nlacombe.vault.vaultws.util;

import net.nlacombe.vault.vaultws.api.dto.PaginationRequest;
import org.springframework.data.domain.PageRequest;

public class PaginationUtils
{
	public static PageRequest toPageRequest(PaginationRequest paginationRequest)
	{
		return new PageRequest(paginationRequest.getPageNumber(), paginationRequest.getSize());
	}
}
