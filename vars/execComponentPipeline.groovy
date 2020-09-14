import net.jeena.pipelinerdepot.pipelines.example.ComponentPipeline
import com.daimler.pipeliner.*

/**
 * execComponentPipeline
 * This script exposes the ComponentPipeline runner to Jenkinsfiles.
 * It can be invoked from a Jenkinsfile by writing 'execComponentPipeline()'
 */

// stageOverrides empty if not specified in the Jenkinsfile
def call(Map stageOverrides = [:]) {
    // Initialize logger to use Jenkins
    Logger.init(this)

    // Initialize input output map used to communicate between pipelines
    // Not used here
    Map ioMap = [:]

    // Fetch merge requeste message and environment dict.
    String message = env.gitlabMergeRequestDescription
    Map environment = env.getEnvironment()

    // Override variables from Jenkinsfile with parameter values defined
    // in the job.
    if (params) {
        params.each { k, v ->
            environment[k] = v.toString()
        }
    }

    // Initialize and run the pipeline
    ComponentPipeline componentPipeline = new ComponentPipeline(this,
                                                                message,
                                                                environment,
                                                                stageOverrides,
                                                                ioMap);
    // Returns a populated ioMap for communication with the next pipeline
    // not used for anything here.
    ioMap = componentPipeline.run()
}
