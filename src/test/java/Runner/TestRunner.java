package Runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/Features",   // ← whole folder, not one file
    glue = "StepDef",
    tags = "@delete",
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",  // ← was target/cucumber/
        "json:target/cucumber-reports/cucumber.json",  // ← was target/cucumber/
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    monochrome = true
)
public class TestRunner {
}
