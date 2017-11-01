package net.nlacombe.vault.vaultws.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZoneId;

@Entity
@Table(name = "user_config")
public class UserConfigEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userConfigId;

	private int userId;

	private ZoneId timezone;

	public int getUserConfigId()
	{
		return userConfigId;
	}

	public void setUserConfigId(int userConfigId)
	{
		this.userConfigId = userConfigId;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public ZoneId getTimezone()
	{
		return timezone;
	}

	public void setTimezone(ZoneId timezone)
	{
		this.timezone = timezone;
	}
}
