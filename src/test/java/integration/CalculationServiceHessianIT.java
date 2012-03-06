package integration;

import com.caucho.hessian.client.HessianConnectionException;
import com.caucho.hessian.client.HessianProxyFactory;
import com.mongermethod.multi_auth_manager_test.service.CalculationService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;

public class CalculationServiceHessianIT {
    private static final String LIMITED_USERNAME = "blair";
    private static final String LIMITED_PASSWORD = "blair-password";
    private static final String LOSER_USERNAME = "julian";
    private static final String LOSER_PASSWORD = "julian-password";
    private static final String BAD_USERNAME = "xoxoxoxoxo";
    private static final String BAD_PASSWORD = "i-dont-exist";

    /**
     * Successful if a non-existing user is denied access to the service
     */
    @Test(groups = "full-integration")
    public void testHessianServiceWithBadUser() {
        HessianProxyFactory proxyFactory = new HessianProxyFactory();
        proxyFactory.setUser(BAD_USERNAME);
        proxyFactory.setPassword(BAD_PASSWORD);

        try {
            CalculationService calculationService = (CalculationService) proxyFactory.create(CalculationService.class, "http://localhost:8080/remoting/Calculation");
            BigDecimal result = calculationService.fractionToPercentage(1, 2);
            Assert.fail("Why can I access a secured service?  I'm not even a real user.");
        } catch (MalformedURLException e) {
            Assert.fail("Could not connect to the Hessian service: ", e);
        } catch (HessianConnectionException hce) {
            // This is what we want
        }
    }

    /**
     * Successful if a user with a non-qualifying role is denied access to the privileged service
     */
    @Test(groups = "full-integration")
    public void testHessianServiceWithLoserUser() {
        HessianProxyFactory proxyFactory = new HessianProxyFactory();
        proxyFactory.setUser(LOSER_USERNAME);
        proxyFactory.setPassword(LOSER_PASSWORD);

        try {
            CalculationService calculationService = (CalculationService) proxyFactory.create(CalculationService.class, "http://localhost:8080/remoting/Calculation");
            BigDecimal result = calculationService.fractionToPercentage(1, 2);
            Assert.fail("Why can I access this service?  My user role should not have access.");
        } catch (MalformedURLException e) {
            Assert.fail("Could not connect to the Hessian service: ", e);
        } catch (HessianConnectionException hce) {
            // This is what we want
        }
    }

    /**
     * With the prior two tests responsible for testing secure access to the service, this test actually tests the
     * functionality of the service itself -- just like the unit test for this service.  However, with the integration
     * test, we're making sure that we're getting back the values we expect with Hessian doing the serialization/
     * deserialization.
     */
    @Test(groups = "full-integration")
    public void testHessianService() {
        HessianProxyFactory proxyFactory = new HessianProxyFactory();
        proxyFactory.setUser(LIMITED_USERNAME);
        proxyFactory.setPassword(LIMITED_PASSWORD);

        try {
            CalculationService calculationService = (CalculationService) proxyFactory.create(CalculationService.class, "http://localhost:8080/remoting/Calculation");
            Assert.assertNotNull(calculationService);

            BigDecimal result = calculationService.fractionToPercentage(1, 2);
            Assert.assertEquals(result, new BigDecimal("0.5"));
        } catch (MalformedURLException e) {
            Assert.fail("Could not connect to the Hessian service: ", e);
        }
    }
}
