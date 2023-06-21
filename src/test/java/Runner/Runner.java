package Runner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Parameters;


@CucumberOptions(
        features = "src/test/resources/firstTest.feature",
        glue = {"StepDefinitions"}
)
@Parameters()
public class Runner extends AbstractTestNGCucumberTests {

}
