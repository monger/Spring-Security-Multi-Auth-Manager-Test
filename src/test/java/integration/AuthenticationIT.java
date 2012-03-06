package integration;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.lang.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthenticationIT {
    private static final String SUPER_USERNAME = "stanFox";
    private static final String SUPER_PASSWORD = "stan-password";
    private static final String LOSER_USERNAME = "navin";
    private static final String LOSER_PASSWORD = "navin-password";
    private static final String BAD_USERNAME = "xoxoxoxoxo";
    private static final String BAD_PASSWORD = "i-dont-exist";

    /**
     * Successful if the non-existing user gets redirected to the authentication failure page
     */
    @Test(groups = "full-integration")
    public void loginWithBadUserTest() {
        WebClient client = new WebClient();

        try {
            HtmlPage loginPage = client.getPage("http://localhost:8080/login");
            loginPage.getElementById("txtUsername").setAttribute("value", BAD_USERNAME);
            loginPage.getElementById("txtPassword").setAttribute("value", BAD_PASSWORD);
            HtmlPage result = loginPage.getElementById("btnSubmit").click();

            if (!StringUtils.endsWith(result.getUrl().getPath(), "failure")) {
                Assert.fail("The ROLE_LOSER user should be directed to /failure but instead was presented with: " + result.getUrl().getPath());
            }
        } catch (Exception e) {
            Assert.fail("caught exception: " + e.getClass().getName() + " -- " + e.getMessage());
        }
        client.closeAllWindows();
    }

    /**
     * Successful when the ROLE_LOSER_USER user get authenticated but then presented with an HTTP 403 status (not authorized)
     */
    @Test(groups = "full-integration")
    public void loginWithLoserUserTest() {
        WebClient client = new WebClient();
        int httpStatusCode = 0;
                
        try {
            HtmlPage loginPage = client.getPage("http://localhost:8080/login");
            loginPage.getElementById("txtUsername").setAttribute("value", LOSER_USERNAME);
            loginPage.getElementById("txtPassword").setAttribute("value", LOSER_PASSWORD);
            HtmlPage result = loginPage.getElementById("btnSubmit").click();
            httpStatusCode = result.getWebResponse().getStatusCode();
            if (StringUtils.endsWith(result.getUrl().getPath(), "failure")) {
                Assert.fail("The ROLE_LOSER_USER user should be authenticated and then denied access to the index page, NOT redirected to /failure.");
            }
        } catch (FailingHttpStatusCodeException fhsce) {
            httpStatusCode = fhsce.getResponse().getStatusCode();
        } catch (Exception e) {
            Assert.fail("caught exception: " + e.getClass().getName() + " -- " + e.getMessage());
        }
        client.closeAllWindows();
        Assert.assertEquals(httpStatusCode, 403);
    }

    /**
     * Successful when the ROLE_SUPER_USER user can log in and can see the index page (gets an HTTP status 200)
     */
    @Test(groups = "full-integration")
    public void loginWithSuperUserTest() {
        WebClient client = new WebClient();
        int httpStatusCode = 0;

        try {
            HtmlPage loginPage = client.getPage("http://localhost:8080/login");
            loginPage.getElementById("txtUsername").setAttribute("value", SUPER_USERNAME);
            loginPage.getElementById("txtPassword").setAttribute("value", SUPER_PASSWORD);
            HtmlPage result = loginPage.getElementById("btnSubmit").click();
            httpStatusCode = result.getWebResponse().getStatusCode();
            if (StringUtils.endsWith(result.getUrl().getPath(), "failure")) {
                Assert.fail("The ROLE_SUPER_USER user should be authenticated, NOT redirected to /failure.");
            }
        } catch (Exception e) {
            Assert.fail("caught exception: " + e.getClass().getName() + " -- " + e.getMessage());
        }
        client.closeAllWindows();
        Assert.assertEquals(httpStatusCode, 200);
    }
}
