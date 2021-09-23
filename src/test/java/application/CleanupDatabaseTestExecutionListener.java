package application;


import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class CleanupDatabaseTestExecutionListener extends AbstractTestExecutionListener {
    private boolean alreadyCleared = false;

    public CleanupDatabaseTestExecutionListener() {
    }

    public final int getOrder() {
        return 2001;
    }

    public void prepareTestInstance(TestContext testContext) throws Exception {
        if (!this.alreadyCleared) {
            this.cleanupDatabase(testContext);
        }

        this.alreadyCleared = true;
    }

    public void afterTestMethod(TestContext testContext) throws Exception {
        if (testContext.getTestMethod().getAnnotation(ClearContext.class) != null) {
            this.cleanupDatabase(testContext);
        }

        super.afterTestMethod(testContext);
    }

    public void afterTestClass(TestContext testContext) throws Exception {
        this.cleanupDatabase(testContext);
    }

    private void cleanupDatabase(TestContext testContext) throws LiquibaseException {
        ApplicationContext app = testContext.getApplicationContext();
        SpringLiquibase springLiquibase = (SpringLiquibase)app.getBean(SpringLiquibase.class);
        springLiquibase.setDropFirst(true);
        springLiquibase.afterPropertiesSet();
    }
}
