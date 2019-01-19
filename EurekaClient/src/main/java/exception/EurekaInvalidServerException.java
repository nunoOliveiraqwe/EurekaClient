package exception;

public class EurekaInvalidServerException extends Exception {

    public EurekaInvalidServerException(String server){
        super("Invalid Eureka Server "+server);
    }

}
