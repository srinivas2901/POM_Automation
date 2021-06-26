import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        strict = true,
        features = {"D:/EclipseWorkspace/massmutual/src/test/resources/FeatureFiles/balance.feature:34"},
        plugin = {"json:D:/EclipseWorkspace/massmutual/target/cucumber-parallel/1.json"},
        monochrome = true,
        glue = {"bindings"})
public class Parallel01IT {
}
