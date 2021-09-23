package application;

import java.nio.charset.Charset;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles(
        profiles = {"test"}
)
public abstract class AbstractTest {
    protected Charset charset = Charset.forName("utf8");
    protected MediaType contentType;
    protected MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    public AbstractTest() {
        this.contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), this.charset);
    }

    public String readFileAsString(String filePath) throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream(filePath));
    }

    @BeforeClass
    public static void setErrorLogging() {
        LoggingSystem.get(ClassLoader.getSystemClassLoader()).setLogLevel("ROOT", LogLevel.OFF);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    private void cleanupDatabase(TestContext testContext) throws LiquibaseException {
        ApplicationContext app = testContext.getApplicationContext();
        SpringLiquibase springLiquibase = (SpringLiquibase)app.getBean(SpringLiquibase.class);
        springLiquibase.setDropFirst(true);
        springLiquibase.afterPropertiesSet();
    }
}