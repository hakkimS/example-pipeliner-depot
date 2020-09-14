package unitTests

import com.daimler.pipeliner.Logger

/**
 * Mock class for the script environment in Jenkins. Contains
 * mocks for all the Jenkins methods used by stages. This way
 * we don't need to run a real Jenkins just for unitTests.
 */
class ScriptEnvironment {

    // Save all calls and parameters for veryfying behaviour
    public ArrayList<ArrayList<String>> callList = new ArrayList<ArrayList<String>>()

    public scm = new Object()
    public Build currentBuild
    public Job currentJob = new Job()
    public String working_directory = "/home/jenkins/workspace-unit-test"

    ScriptEnvironment() {
        Logger.init(this)
        currentBuild = new Build(currentJob)
    }

    private void log(String m, a = null, clo = null) {
        callList.add([m, a.inspect()])
        if(clo) clo.call()
    }

    void node(String name, Closure clo) {
        log("node", name, clo)
    }

    void vs(String name, Closure clo) {
        log("vs", name, clo)
    }

    void stage(String name, Closure clo) {
        log("stage", name, clo)
    }

    void sh(String cmd) {
        log("sh", cmd)
    }

    /**
     * Mock of Jenkins sh with named parameters.
     *
     * Named parameters change the output type.
     */
    def sh(Map map) {
        log("sh", map.inspect())

        if (map.containsKey('retirnStdout')) {
            return ""
        }

        if (map.containsKey('retunStatus')) {
            return 0
        }
    }

    void checkout(Object scm) {
        log("checkout", scm)
    }

    void archiveArtifacts(String pattern) {
        log("archiveArtifacts", pattern)
    }

    void archiveArtifacts(Map patternMap) {
        log("archiveArtifacts", patternMap)
    }
    
    void junit(String pattern) {
        log("junit", pattern)
    }

    void xunit(Map map) {
        log("xunit", map)
    }

    void step(Map map) {
        log("step", map)
    }

    void retry(int n, Closure clo) {
        log("retry", n, clo)
    }

    void error(String msg) {
        Logger.error(msg)
        throw new Exception(msg)
    }

    String pwd() {
        log("pwd")
        return workindDir;
    }

    void cleanWs() {
        log("cleanWs")
    }

    /*
     * Helper method to inspect calls
     * @param call_filter String the name of the call
     * @return new array with only founnd strings
     */
    ArrayList<ArrayList<String>> filter_calls(String call_filter) {
        return callList.findAll {f -> call_filter == f[0] }
    }

    /*
     * Helper method to inspect calls with args
     */
    ArrayList<ArrayList<String>> filter_calls_with_arguments(String call_filter, String arg_filter) {
        return filter_call_list(filter_calls(call_filter), arg_filter)
    }

    ArrayList<ArrayList<String>> filter_call_list(ArrayList<ArrayList<String>> list, String filter) {
        return list.findAll { args ->
            for (String arg : args) {
                if (arg.contains(filter)) {
                    return true
                }
            }
            return false
        }
    }
}
