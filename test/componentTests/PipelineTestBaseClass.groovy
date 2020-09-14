package componentTests

import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameter


import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue
import static org.junit.Assert.assertEquals

import static com.lesfurets.jenkins.unit.global.lib.LibraryConfiguration.library
import static com.lesfurets.jenkins.unit.global.lib.LocalSource.localSource

import static com.lesfurets.jenkins.unit.MethodCall.callArgsToString
import com.lesfurets.jenkins.unit.BasePipelineTest

@RunWith(Parameterized.class)
abstract class PipelineTestBaseClass extends BasePipelineTest {
    @Parameter(0)
    public ArrayList<String> mergeRequestArguments

    @Parameter(1)
    public ArrayList<String> expectedStages = []

    @Parameter(2)
    public ArrayList<String> unexpectedStages = []

    public static String testResourcesPath = 'test/resources/'

    DockerMock dm = new DockerMock()
    String mergeRequestMessage = "--"

    Map defaultEnvVariables = [
        BUILD_TAG: "mybuildtag",
        BUILD_NUMBER: "1",
        BUILD_URL: "example.org",
        JOB_NAME: "test"
    ]

    @Override
    @Before
    void setUp() throws Exception {
        scriptRoots += 'jobs'
        super.setUp()
        String clonePath = 'build/libs'
        def depotLibrary = library()
            .name('example-pipeliner-depot')
            .retriever(localSource('build/libs'))
            .targetPath(clonePath)
            .defaultVersion("master")
            .allowOverride(true)
            .implicit(false)
            .build()

        helper.registerSharedLibrary(depotLibrary)

        // Register Jenkins methods for any component test here
        helper.registerAllowedMethod("pwd", [], {"example"})
        helper.registerAllowedMethod("junit", [String.class], null)
        //helper.registerAllowedMethod("sh", [Map.class] { Map map ->
        //    if (map['script'].contains('echo $USER')) return 'jenkins'
        //    return null
        //})
        helper.registerAllowedMethod("timestamps", [Closure.class], null)
        helper.registerAllowedMethod("lock", [String.class, Closure.class], null)
        helper.registerAllowedMethod("cleanWs", [], null)

        binding.setProperty("docker", dm)
        binding.setVariable("params", [:])
        binding.setVariable('scm', [$class: 'GitSCM', branches: [name:"feature_test"]])

        for (String mergeRequestArgument in mergeRequestArguments) {
            mergeRequestMessage += "\n" + mergeRequestArgument
        }
    }

    protected void assertExpectedStages() {
        List stages = helper.callStack.findAll { call ->
            call.methodName == "stage"
        }

        for (String expectedStage in expectedStages) {
            assertTrue("Was expecting stage: " + expectedStage, stages.any { call ->
                callArgsToString(call).contains(expectedStage)
            })
        }
    }

    protected void assertUnexpectedStages() {
        List stages = helper.callStack.findAll { call ->
            call.methodName == "stage"
        }

        for (String unexpectedStage in unexpectedStages) {
            assertFalse("Was not expecting stage: " + unexpectedStage, stages.any { call ->
                callArgsToString(call).contains(unexpectedStage)
            })
        }
    }

    public void runJenkinsfileTestCodeWithEnvironment(EnvironmentMock em, String scriptPath) {
        binding.setVariable('env', em)
        runScript(scriptPath)

        // from https://git.swf.daimler.com/swf/sysadmin-scripts/-/tree/master/crowd
        def currentBuild = binding.getVariable('currentBuild')
        if(Result.isCurrentResultWorseThanResult(
            currentBuild.currentResult, currentBuild.result)) {
            currentBuild.result = currentBuild.currentResult
        }
    }
}
