/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author alois
 */
public interface SingleLensSystemI {

    double getF();

    double getImgDistance();

    double getImgHeight();

    double getMag();

    double getObjDistance();

    double getObjHeight();

    void setVariables(double objDistance, double objHeight, double f);

    String toString();
    
}
