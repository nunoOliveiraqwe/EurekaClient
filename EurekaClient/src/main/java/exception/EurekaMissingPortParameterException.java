package exception;

public class EurekaMissingPortParameterException extends Exception {

    public EurekaMissingPortParameterException(){
        super("Missing Parameter: port");
    }


}
