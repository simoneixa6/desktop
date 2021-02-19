/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.ei6.interventions.desktop.lib.data;

import biz.ei6.interventions.desktop.lib.domain.Intervention;
import java.util.ArrayList;

/**
 * @author Eixa6
 */
public interface InterventionsDataSource {
    void add(Intervention intervention);
    ArrayList<Intervention> readAll();
    void update( Intervention intervention );
    void remove( String id ); 
}
