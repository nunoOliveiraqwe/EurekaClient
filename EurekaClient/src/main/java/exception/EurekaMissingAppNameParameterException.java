package exception;

public class EurekaMissingAppNameParameterException extends Exception {


    public EurekaMissingAppNameParameterException() {
        super("Missing Parameter: appName");
    }
}
