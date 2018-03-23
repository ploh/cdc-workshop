package io.reflectoring;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.loader.PactBrokerAuth;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;


@RunWith(SpringRestPactRunner.class)
@PactBroker(host = "adesso.pact.dius.com.au/", port = "443", protocol = "https",
    authentication = @PactBrokerAuth(username = "Vm6YWrQURJ1T7mDIRiKwfexCAc4HbU",
        password = "aLerJwBhpEcN0Wm88Wgvs45AR9dXpc"))
@Provider("userservice-stefanb")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    properties = {"server.port=8080"}
)
public class UserControllerTest {

    @TestTarget
    public final Target target = new HttpTarget(8080);

    @MockBean
    private UserRepository userRepository;

    @State({"provider accepts a new person"})
    public void toCreatePersonState() {
        User user = new User();
        user.setId(42L);
        user.setFirstName("Arthur");
        user.setLastName("Dent");
        when(userRepository.findOne(eq(42L))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
    }

}
