package unit;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/spring-config.xml"})
public abstract class TestBootstrap extends AbstractTestNGSpringContextTests {
        public TestBootstrap() {
            super();
        }
}
