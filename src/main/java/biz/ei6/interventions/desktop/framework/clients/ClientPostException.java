package biz.ei6.interventions.desktop.framework.clients;

/**
 *
 * @author Eixa6
 */
public class ClientPostException extends Exception {
 
        public ClientPostException(String erreur, Exception e) {
        super(erreur,e);
    }
    
}
