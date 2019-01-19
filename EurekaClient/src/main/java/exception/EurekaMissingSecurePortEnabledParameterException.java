package exception;

public class EurekaMissingSecurePortEnabledParameterException extends Exception {


    public EurekaMissingSecurePortEnabledParameterException(){
        super("Missing Parameter: securePortEnabled");
    }

}
