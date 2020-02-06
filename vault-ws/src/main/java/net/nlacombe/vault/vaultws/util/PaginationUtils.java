package net.nlacombe.vault.vaultws.util;

import net.nlacombe.vault.vaultws.api.dto.PaginationRequest;
import org.springframework.data.domain.PageRequest;

public class PaginationUtils
{
	public static PageRequest toPageRequest(PaginationRequest paginationRequest)
	{
		return PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getSize());
	}
}
