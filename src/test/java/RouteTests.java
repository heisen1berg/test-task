import core.data.CalculateRequestEntity;
import core.route.RouteConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;


@RunWith(SpringRunner.class)
@WebFluxTest
@ContextConfiguration(classes = RouteConfig.class)
public class RouteTests {

    @Autowired
    private WebTestClient webClient;

    // GET Test-case
    @Test
    public void get() throws Exception {

        webClient.get().uri("/calculate", new CalculateRequestEntity("fun1", "fun2", 2, "out")).accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CalculateRequestEntity.class);
    }

}
