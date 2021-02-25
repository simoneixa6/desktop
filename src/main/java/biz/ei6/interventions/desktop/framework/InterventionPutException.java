/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.framework;

/**
 *
 * @author Eixa6
 */
public class InterventionPutException extends Exception {

    public InterventionPutException(String erreur, Exception e) {
        super(erreur,e);
    }
    
}
