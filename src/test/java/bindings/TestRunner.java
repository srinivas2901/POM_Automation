package bindings;


import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/FeatureFiles",
				 format = {"pretty","json:target/cucumber.json", "html:target/CucumberHTMLReport"},
				 monochrome= true,
				 tags = {"@Regression"},
				 dryRun=false
				)


				
public class TestRunner{

}
