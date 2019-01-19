package exception;

public class EurekaMissingSecurePortException extends Exception {

    public EurekaMissingSecurePortException(){
        super("Missing Parameter: securePort");
    }


}
