package unit;


import com.mongermethod.multi_auth_manager_test.service.CalculationService;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;

public class CalculationServiceTest extends TestBootstrap {
    @Resource
    private CalculationService calculationService;

    @Test(groups = "comprehensive-unit")
    public void testCalculationService() {
        Assert.assertNotNull(calculationService);
        BigDecimal result = calculationService.fractionToPercentage(1, 4);
        Assert.assertEquals(result, new BigDecimal("0.25"));
    }
}
