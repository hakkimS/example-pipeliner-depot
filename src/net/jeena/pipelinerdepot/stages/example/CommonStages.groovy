package net.jeena.pipelinerdepot.stages.example

import com.daimler.pipeliner.Logger
import com.daimler.pipeliner.ScriptUtils
import hudson.EnvVars
import hudson.plugins.git.GitSCM
import com.cloudbees.groovy.cps.NonCPS

/**
 * Contains stages that can be reused across pipelines
 */

class CommonStages {
    def script
    Map env
    ScriptUtils utils
    
    /**
     * Constructor
     * @param script reference to the Jenkins scripted environment
     * @param env Map for Jenkins environment variables
     */
    CommonStages(script, Map env) {
        this.script = script
        this.utils = new ScriptUtils(script, env)
        this.env = env
    }

    /**
     * stageCheckout checks out the associated git repository inicializes it
     * and updates any submodules contained.
     *
     * @param env Map of Jenkins env variables
     * @param args String array of optional arguments for submodule update
     */
    def stageCheckout(Map env) {
        script.stage("Checkout") {
            script.checkout script.scm
            script.sh "git submodule update --init --recursive"
        }
    }

    def stageCleanup() {
        script.cleanWs()
    }

}
