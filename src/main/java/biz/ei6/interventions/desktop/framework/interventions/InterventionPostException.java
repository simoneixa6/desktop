package biz.ei6.interventions.desktop.framework.interventions;

/**
 *
 * @author Eixa6
 */
public class InterventionPostException extends Exception {

    public InterventionPostException(String erreur, Exception e) {
        super(erreur,e);
    }

}
