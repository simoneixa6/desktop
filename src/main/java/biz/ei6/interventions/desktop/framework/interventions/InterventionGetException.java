package biz.ei6.interventions.desktop.framework.interventions;

/**
 *
 * @author Eixa6
 */
public class InterventionGetException extends Exception {
    public InterventionGetException(String erreur, Exception e) {
        super(erreur,e);
    }
}
