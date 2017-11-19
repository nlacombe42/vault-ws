package net.nlacombe.vault.vaultws.webservice.impl;

import net.nlacombe.authlib.spring.AuthUtil;
import net.nlacombe.vault.vaultws.api.dto.Category;
import net.nlacombe.vault.vaultws.api.webservice.CategoryWebService;
import net.nlacombe.vault.vaultws.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryWebServiceImpl implements CategoryWebService
{
	private CategoryService categoryService;

	@Inject
	public CategoryWebServiceImpl(CategoryService categoryService)
	{
		this.categoryService = categoryService;
	}

	@PreAuthorize("isAuthenticated()")
	@Override
	public List<Category> getCategories()
	{
		int userId = AuthUtil.getAuthenticatedUser().getUserId();

		return categoryService.getCategories(userId).collect(Collectors.toList());
	}
}
