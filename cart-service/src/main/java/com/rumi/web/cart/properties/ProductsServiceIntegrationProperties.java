package com.rumi.web.cart.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.core-service")
@Data
public class ProductsServiceIntegrationProperties {
    private String url;
    private Integer connect;
    private Integer read;
    private Integer write;
}
