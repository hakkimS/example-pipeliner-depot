package unitTests

class Job {
    Build getLastSuccessfulBuild() {
        return new Build(this)
    }
}

class Build {
    public int number
    public String currentResult = "SUCCESS"
    static public String result = null
    public String log = ""
    static private int lastNumber = 0
    private Job parent

    Build(parent) {
        this.parent = parent
        this.number++
    }

    Build getRawBuild() {
        return this
    }

    Job getParent() {
        return parent
    }

    String getProjectName() {
        return "Some project name"
    }
}
