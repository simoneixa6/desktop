package biz.ei6.interventions.desktop.framework.clients;

/**
 *
 * @author Eixa6
 */
public class ClientPutException extends Exception {

    public ClientPutException(String erreur, Exception e) {
        super(erreur, e);
    }
}
