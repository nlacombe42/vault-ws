package net.nlacombe.vault.vaultws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(basePackageClasses = {VaultWsApplication.class, Jsr310JpaConverters.class})
@SpringBootApplication
@EnableDiscoveryClient
public class VaultWsApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(VaultWsApplication.class);
	}
}
