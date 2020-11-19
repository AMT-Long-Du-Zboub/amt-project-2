package io.avalia.fruits.api.spec.helpers;

import io.avalia.fruits.api.DefaultApiTest;

import java.io.IOException;
import java.util.Properties;
import java.util.TimeZone;

public class Environment {

    private DefaultApiTest api = new DefaultApiTest();

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("io.avalia.fruits.server.url");
        api.getApiClient().setBasePath(url);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public DefaultApiTest getApi() {
        return api;
    }



}
