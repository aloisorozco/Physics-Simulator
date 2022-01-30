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
public class SingleLensSystem extends LensSystem implements SingleLensSystemI{
    private double objDistance;
    private double objHeight;
    private double f;
    
    public SingleLensSystem(double objDistance, double objHeight, double f){
        setVariables(objDistance, objHeight, f);
    }
    @Override
    public void setVariables(double objDistance, double objHeight, double f){
        this.objDistance=objDistance;
        this.objHeight=objHeight;
        this.f=f;
        imgDistance=calculateImgDistance(objDistance, f);
        imgHeight=calculateImgHeight(objHeight, objDistance, imgDistance);
        mag=calculateMag(imgHeight, objHeight);
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
    public double getF() {
        return f;
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
        return "SingleLensSystem{" + "objDistance=" + objDistance + ", objHeight=" + objHeight + ", f=" + f + ", imgDistance=" + imgDistance + ", imgHeight=" + imgHeight + ", mag=" + mag + '}';
    }
    
}
