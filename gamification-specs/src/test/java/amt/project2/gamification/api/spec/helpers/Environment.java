package amt.project2.gamification.api.spec.helpers;
import amt.project2.gamification.api.dto.DefaultApiTest;


import java.io.IOException;
import java.util.Properties;
import java.util.TimeZone;

public class Environment {

    private DefaultApiTest api = new DefaultApiTest();

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("amt.project2.gamification.server.url");
        api.getApiClient().setBasePath(url);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public DefaultApiTest getApi() {
        return api;
    }



}
