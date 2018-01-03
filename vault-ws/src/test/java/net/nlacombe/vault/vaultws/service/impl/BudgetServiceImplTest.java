package net.nlacombe.vault.vaultws.service.impl;

import net.nlacombe.commonlib.util.DateUtil;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetsInfo;
import net.nlacombe.vault.vaultws.domain.UserConfig;
import net.nlacombe.vault.vaultws.mapper.BudgetMapper;
import net.nlacombe.vault.vaultws.repositorty.BudgetRepository;
import net.nlacombe.vault.vaultws.service.CategoryService;
import net.nlacombe.vault.vaultws.service.TransactionService;
import net.nlacombe.vault.vaultws.service.UserConfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BudgetServiceImplTest
{

	@InjectMocks
	private BudgetServiceImpl budgetService;

	@Mock
	private TransactionService transactionService;

	@Mock
	private UserConfigService userConfigService;

	@Mock
	private BudgetRepository budgetRepository;

	@Mock
	private CategoryService categoryService;

	@Mock
	private BudgetMapper budgetMapper;

	@Test
	public void returns_month_current_amount_on_getting_month_info()
	{
		int userId = 1;
		ZoneId userConfigZone = ZoneId.of("America/New_York");
		YearMonth yearMonth = YearMonth.of(2017, 12);
		Instant startOfMonth = getStartOfMonth(yearMonth, userConfigZone);
		Instant endOfMonth = getLastSecondBeforeNextMonth(yearMonth, userConfigZone);
		BigDecimal currentAmount = new BigDecimal(42.24);

		givenUserConfig(userId, userConfigZone);
		when(budgetRepository.getTotalPlannedMaxAmount(userId, startOfMonth, endOfMonth)).thenReturn(new BigDecimal(2000));
		when(transactionService.getCategoriesTotal(anyInt(), anyCollection(), any(Instant.class), any(Instant.class))).thenReturn(new BigDecimal(200));
		when(transactionService.getTotalAmount(userId, startOfMonth, endOfMonth)).thenReturn(currentAmount);

		MonthBudgetsInfo monthBudgetsInfo = budgetService.getMonthBudgetsInfo(userId, yearMonth);

		assertThat(monthBudgetsInfo).isNotNull();
		assertThat(monthBudgetsInfo.getMonthStats()).isNotNull();
		assertThat(monthBudgetsInfo.getMonthStats().getCurrentAmount()).isEqualTo(currentAmount);
	}

	private void givenUserConfig(int userId, ZoneId zoneId)
	{
		when(userConfigService.getUserConfig(userId)).thenReturn(new UserConfig(1, userId, zoneId));
	}

	private Instant getStartOfMonth(YearMonth yearMonth, ZoneId zoneId)
	{
		return DateUtil.getStartOfMonth(yearMonth, TimeZone.getTimeZone(zoneId));
	}

	private Instant getLastSecondBeforeNextMonth(YearMonth yearMonth, ZoneId zoneId)
	{
		return DateUtil.getLastSecondBeforeNextMonth(yearMonth, TimeZone.getTimeZone(zoneId));
	}
}
