package unitTests

import com.daimler.pipeliner.ScriptUtils
import net.jeena.pipelinerdepot.stages.example.CommonStages
import groovy.util.Eval
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.io.IOException

class CommonStagesTest {
    private ScriptEnvironment script
    private ScriptUtils utils
    private CommonStages commonStages
    Map stageOverriders

    Map<String, String> env = [:]

    @Before
    void setUp() {
        script = new ScriptEnvironment()
        utils = new ScriptUtils(script, env)
        commonStages = new CommonStages(script, env)
    }

    @Test
    void test_stageCheckout() {
        commonStages.stageCheckout()

        assert script.filter_calls("checkout").size() > 0
        assert script.filter_calls_with_arguments("sh", "git").size() > 0
    }

    @Test
    void test_stageCleanup() {
        commonStages.stageCleanup()

        assert script.filter_calls("cleanWs").size() == 1
    }
}
