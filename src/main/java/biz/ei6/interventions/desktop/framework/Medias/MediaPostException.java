package biz.ei6.interventions.desktop.framework.medias;


/**
 *
 * @author Eixa6
 */
public class MediaPostException extends Exception {
 
        public MediaPostException(String erreur, Exception e) {
        super(erreur,e);
    }
    
}
