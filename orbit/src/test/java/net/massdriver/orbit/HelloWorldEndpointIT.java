package net.massdriver.orbit;

import io.restassured.RestAssured;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;

public class HelloWorldEndpointIT {
    @Ignore @Test
    public void testVoid() {
        RestAssured.when()
                .get("/rest/hello")
                .then()
                    .assertThat()
                    .body("$", equalTo("Hello World"));
    }
}