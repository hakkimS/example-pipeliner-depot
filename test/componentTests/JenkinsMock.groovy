package componentTests

/*
* Enumeration that simulates hudson.model.Result class
*/
enum Result {
    SUCCESS, UNSTABLE, FAILURE, NOT_BUILT, ABORTED

    public static boolean isCurrentResultWorseThanResult(String currentResult, String result) {
        if (result == null) return true;
        return (currentResult as Result) > (result as Result);
    }
}
