package exception;

public class EurekaInvalidParameterException extends Exception {

    public EurekaInvalidParameterException(String ex){
        super("Invalid parameter "+ex);
    }

}
