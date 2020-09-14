package unitTests

import com.daimler.pipeliner.ScriptUtils
import net.jeena.pipelinerdepot.stages.example.ComponentStages
import groovy.util.Eval
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.io.IOException

class ComponentStagesTest {
    private ScriptEnvironment script
    private ScriptUtils utils
    private ComponentStages componentStages
    Map stageOverriders

    Map<String, String> env = [:]

    @Before
    void setUp() {
        script = new ScriptEnvironment()
        utils = new ScriptUtils(script, env)
        componentStages = new ComponentStages(script, env)
    }

    @Test
    void test_stageBuild() {
        componentStages.stageBuild("a", "b", "c")

        assert script.filter_calls_with_arguments("sh", "cmake").size() > 0
    }

    @Test
    void test_stageUnitTests() {
        componentStages.stageUnitTests("a")

        assert script.filter_calls_with_arguments("sh", "cmake").size() > 0
        assert script.filter_calls("junit").size() > 0
    }
}
