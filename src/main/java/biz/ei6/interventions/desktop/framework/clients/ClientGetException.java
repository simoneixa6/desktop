package biz.ei6.interventions.desktop.framework.clients;

/**
 *
 * @author Eixa6
 */
public class ClientGetException extends Exception {

    public ClientGetException(String erreur, Exception e) {
        super(erreur, e);
    }

}
