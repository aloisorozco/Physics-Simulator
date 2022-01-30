/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

//import Model.LensSystem;

/**
 *
 * @author alois
 */
public class DoubleLensSystem extends LensSystem implements DoubleLensSystemI{
    double objDistance;
    double objHeight;
    double f1;
    double f2;
    double lensDistance;
    
    public DoubleLensSystem(double objDistance, double objHeight, double f1, double f2, double lensDistance){
        
        setVariables(objDistance, objHeight,f1,f2, lensDistance);
    }
    
    @Override
    public void setVariables(double objDistance, double objHeight, double f1, double f2, double lensDistance){
        this.objDistance=objDistance;
        this.objHeight=objHeight;
        this.f1=f1;
        this.f2=f2;
        imgDistance=LensSystem.calculateImgDistanceInDoubleLens(objDistance, f1,f2,lensDistance);
        imgHeight=calculateImgHeightInDoubleLens(objDistance, objHeight,lensDistance, f1,f2);
        mag=calculateMagInDoubleLens(objHeight,imgHeight);
    }
    
    
    @Override
    public double getObjDistance() {
        return objDistance;
    }

    @Override
    public double getObjHeight() {
        return objHeight;
    }

    @Override
    public double getF1() {
        return f1;
    }

    @Override
    public double getF2() {
        return f2;
    }

    @Override
    public double getLensDistance() {
        return lensDistance;
    }
    @Override
    public double getImgDistance() {
        return imgDistance;
    }
    @Override
    public double getImgHeight() {
        return imgHeight;
    }
    @Override
    public double getMag() {
        return mag;
    }
    
    @Override
    public String toString() {
        return "DoubleLensSystem{" + "objDistance=" + objDistance + ", objHeight=" + objHeight + ", f1=" + f1 + ", f2=" + f2 + ", lensDistance=" + lensDistance + ", imgDistance=" + imgDistance + ", imgHeight=" + imgHeight + ", mag=" + mag + '}';
    }
    
    
}
