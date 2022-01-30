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
public interface DoubleLensSystemI {

    double getF1();

    double getF2();

    double getImgDistance();

    double getImgHeight();

    double getLensDistance();

    double getMag();

    double getObjDistance();

    double getObjHeight();

    void setVariables(double objDistance, double objHeight, double f1, double f2, double lensDistance);

    String toString();
    
}
