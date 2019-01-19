package exception;

public class EurekaMissingConfigFileException extends Exception {

    public EurekaMissingConfigFileException(){
        super("Missing Config File EurekaParameters in Resources folder");
    }

}
