package runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
// 1. Dónde están los archivos .feature
@SelectClasspathResource("features")

// 2. Dónde están tus Steps (¡Corregido según tu captura de pantalla!)
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "org.example.calcularcuota.steps")

// 3. Generar el reporte
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:build/reports/cucumber.html")
public class RunCucumberTest {
}