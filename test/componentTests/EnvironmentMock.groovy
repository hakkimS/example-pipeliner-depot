package componentTests

class EnvironmentMock {
    Map<String, String> environment

    final String gitlabMergeRequestDescription = ""
    final String JOB_NAME = ""
    final String BUILD_NUMBER = "1"
    final String gitlabSourceNamespace = ""

    public EnvironmentMock(String mergeRequestMessage, Map<String, String> environment) {
        this.gitlabMergeRequestDescription = mergeRequestMessage
        this.environment = environment
    }

    public getEnvironment() {
        return environment
    }
}
