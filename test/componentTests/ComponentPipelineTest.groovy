package componentTests

import com.daimler.pipeliner.Logger

import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameter
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized.class)
class ComponentPipelineTest extends PipelineTestBaseClass {
    
    @Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        // Test try to set exposed valies in the pipeline
        return [
            // MR description, expected stages, unexpected stages
            [[""], [], []],
            [["runUnitTests = false"], [], ["Unit tests"]],
            [["runUnitTests = true"], ["Unit tests"], []]
        ].collect { it as Object[] }
    }

    @Test
    void component_pipeline_test() {
        EnvironmentMock env = new EnvironmentMock("--\n" + mergeRequestMessage, defaultEnvVariables)
        runJenkinsfileTestCodeWithEnvironment(env, "testJenkinsfiles/componentPipelineJenkinsfile.groovy")

        assertExpectedStages()
        assertUnexpectedStages()
    }
}
