package net.nlacombe.vault.vaultws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/secret.properties")
public class BaseConfig
{
}
