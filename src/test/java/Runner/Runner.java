package Runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)

@CucumberOptions(
		features ="src\\test\\resources\\Features\\Add.feature",
		glue="StepDef",
		plugin = {"pretty","html:target/cucumber/report.html"}
		) 
public class Runner {
	
}