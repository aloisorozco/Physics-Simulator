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
public class LensSystem implements LensSystemI {
    protected double imgDistance;
    protected double imgHeight;
    protected double mag;
    public LensSystem(){
        
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
    
    //calculates the image distance using the focal length formula
    public static double calculateImgDistance(double objDistance, double f) {
        return 1 / ((1 / f) - (1 / objDistance));
    }
    
    //calculates the image height using the magnification formula
    public static double calculateImgHeight(double objHeight, double objDistance, double imgDistance) {
        return ((-imgDistance) / objDistance) * objHeight;
    }
    
    //calculates the magnification using the magnification formula
    public static double calculateMag(double imgHeight, double objHeight) {
            return (double)imgHeight / (double)objHeight;
    }
    
    //first step in calculating the image distance in double lens system
    //calculates the first image distance and translates it to the object distance for the second lens
    public static double calculateObjDistanceInDoubleLens(double objDistance, double lensDistance,double f1){
        return lensDistance-calculateImgDistance(objDistance,f1);
    }
    
    //calculates the magnification using the magnification formula
    public static double calculateMagInDoubleLens(double objHeight, double imgHeight){
        return (imgHeight/objHeight);
    }
    
    //second step in calculating the image distance in double lens system
    //calculates the final image distance using the focal length formula function
    public static double calculateImgDistanceInDoubleLens(double objDistance, double f1, double f2, double lensDistance){
        return calculateImgDistance(calculateObjDistanceInDoubleLens(objDistance,lensDistance, f1),f2);
    }
    
    //calculates the image height in double lens system
    //two step process just like the image distance
    //first calculates the first "image" height,
    //translates it to the second lens and then calculates the final image height
    public static double calculateImgHeightInDoubleLens(double objDistance, double objHeight, double lensDistance,double f1, double f2){
        double firstImgHeight = calculateImgHeight(objHeight,objDistance,calculateImgDistance(objDistance, f1));
        double firstImgDistance = calculateObjDistanceInDoubleLens(objDistance, lensDistance, f1);
        return calculateImgHeight(firstImgHeight, firstImgDistance,calculateImgDistance(firstImgDistance, f2));
    }
}
