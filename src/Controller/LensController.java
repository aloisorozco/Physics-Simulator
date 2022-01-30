/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.LensView;
import View.LensView;
import Model.DoubleLensSystem;
import Model.LensSystem;
import Model.SingleLensSystem;
import ToggleSwitch.ToggleSwitch;
import java.text.DecimalFormat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author alois
 */
public class LensController {
    static DecimalFormat df = new DecimalFormat("#.#");
    
    public static Scene lensScene;
    public static Scene helpScene;
    
    public static boolean isSingle;
    public static DoubleLensSystem dls;
    public static SingleLensSystem sls;
    
    public static CheckBox lens2CheckBox;
    public static Text focalLength1Text;
    public static Text focalLength2Text;
    public static Text lensDistanceText;
    public static Text lens1PositionText;
    public static Text lens2PositionText;
    public static Text objDistanceText;
    public static Text objHeightText;
    public static Text imgDistanceText;
    public static Text imgHeightText;
    public static Text magText;
    
    public static ToggleSwitch lens1TypeSwitch;
    public static Slider focalLength1Slider;
    public static ToggleSwitch lens2TypeSwitch;
    public static Slider focalLength2Slider;
    public static Slider lensDistanceSlider;
    public static Slider lens1PositionSlider;
    public static Slider lens2PositionSlider;
    public static Slider objDistanceSlider;
    public static Slider objHeightSlider;
    
    public static Image pencilImg;
    public static Image pencilInvertedImg;
    public static Image convergingLensImg;
    public static Image divergingLensImg;
    
    public static ImageView objImage;
    public static ImageView imgImage;
    public static ImageView lens1Image;
    public static ImageView lens2Image;
    
    public static int[] midPoint = {300,200};
    public static double ratio=7.5;

    
    //initializes the lens simulation
    public static void init(Stage stage){
        setIsSingle(false);
        LensView.setLayout(stage);
        setListeners();
        
    }
    
    //called when a slider's value has changed and the simulation has to update
    public static void update(){
        enableOrDisableSliders();
        LensView.updateShapes();
        updateResults();
    }
    
    //updates the results like the image distance, height, and magnification on the right hand side of the gui
    public static void updateResults(){
        LensSystem temp = LensController.getSystem();
        imgDistanceText.setText("Image Distance:   "+df.format(LensController.getSystem().getImgDistance())+"cm");
        imgHeightText.setText("Image Height:   "+df.format(LensController.getSystem().getImgHeight())+"cm");
        magText.setText("Magnification:   "+df.format(LensController.getSystem().getMag()));
        
    }
    
    //gets the System object
    public static LensSystem getSystem(){
        //checks if the current system displayed is a single lens system or double lens system
        if (isSingle){
            return sls;
        }
        else{
            return dls;
        }
    }
    
    //sets the system to store system's information and calculate the image distance, height, and magnification
    public static void setSystem(){
        if (isSingle){
            setSls(objDistanceSlider.getValue(), objHeightSlider.getValue(),lens1TypeSwitch.getFModifier()*focalLength1Slider.getValue());
        }
        else{
            setDls(objDistanceSlider.getValue(), objHeightSlider.getValue(),lens1TypeSwitch.getFModifier()*focalLength1Slider.getValue(),
                    lens2TypeSwitch.getFModifier()*focalLength2Slider.getValue(),getLensDistance());
        }
    }
    
    //checks which eventhandlers should be set depending on the lens system type
    public static void setHandlers(){
        if(LensController.isSingle){
            setHandlersForSls();
        }
        else{
            setHandlersForDls();
        }
    }
    
    private static double initX;
    private static double initY;
    private static double lens1X;
    private static double lens2X;
    //sets the eventhandlers for drag functionality for a double lens system
    public static void setHandlersForDls(){
        objImage.setCursor(Cursor.HAND);
        objImage.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                double dragX = me.getSceneX();
                double dragY = me.getSceneY();
                //calculate new position of the image
                double newXPosition = initX + dragX;
                double newYPosition = dragY;
                double lens1Position = midPoint[0]-(ratio*lens1PositionSlider.getMax()+37.5)+(ratio*lens1PositionSlider.getValue());
                //if new position do not exceeds borders of the rectangle, translate to this position

                if ((newXPosition <= (lens1Position-(ratio*objDistanceSlider.getMin()))) && (newXPosition >= (lens1Position-(ratio*objDistanceSlider.getMax())))) {
                    //sets the objDistance slider to the updated value
                    objDistanceSlider.setValue((lens1Position-newXPosition)/ratio);
                }
                if ((newYPosition <= (midPoint[1]-(ratio*objHeightSlider.getMin()))) && (newYPosition >=(midPoint[1]-(ratio*objHeightSlider.getMax())))) {
                    //sets the objHeight slider to the updated value
                    objHeightSlider.setValue((midPoint[1]-newYPosition)/ratio);
                }
            }
        });
        objImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                
                objImage.toFront();
            }
        });
        objImage.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                //when mouse is pressed, store initial position
                initX = objImage.getTranslateX()-200;
                initY = objImage.getTranslateY();
            }
        });
        
        lens1Image.setCursor(Cursor.HAND);
        lens1Image.setPickOnBounds(true);
        lens1Image.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                //when mouse is pressed, store initial position
                lens1X = lens1Image.getTranslateX()-200;
            }
        });
        lens1Image.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                double dragX = me.getSceneX();
                double dragY = me.getSceneY();
                //calculate new position of the image
                double newXPosition = lens1X+dragX;
               
                if ((newXPosition <= (midPoint[0]-37.5)) && (newXPosition >= (midPoint[0]-(ratio*lens1PositionSlider.getMax()+37.5)))) {
                    //sets the lens1Position slider to the updated value
                    lens1PositionSlider.setValue((newXPosition-(midPoint[0]-(ratio*lens1PositionSlider.getMax()+37.5)))/ratio);
                    
                    
                }
                
            }
        });
        
        lens2Image.setCursor(Cursor.HAND);
        lens2Image.setPickOnBounds(true);
        lens2Image.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                //when mouse is pressed, store initial position
                lens2X = lens2Image.getTranslateX()-200;
            }
        });
        lens2Image.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                double dragX = me.getSceneX();
                double dragY = me.getSceneY();
                //calculate new position of the circle
                double newXPosition = lens2X+dragX;
                //double lens2Position = midPoint[0]+(ratio*lens2PositionSlider.getValue());
                
                if ((newXPosition <= (midPoint[0]+(ratio*lens2PositionSlider.getMax()))) && (newXPosition >= midPoint[0])) {
                    //sets the lens2Position slider to the updated value
                    lens2PositionSlider.setValue((newXPosition-midPoint[0])/ratio);
                    
                }
                
            }
        });
    }

    //sets the eventhandlers for drag functionality for a single lens system
    public static void setHandlersForSls(){
        objImage.setCursor(Cursor.HAND);
        objImage.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                double dragX = me.getSceneX();
                double dragY = me.getSceneY();
                //calculate new position of the image
                double newXPosition = initX + dragX;
                double newYPosition = dragY;
                double lens1Position = midPoint[0];
                
                
                if ((newXPosition <= (lens1Position-(ratio*objDistanceSlider.getMin()))) && (newXPosition >= (lens1Position-(ratio*objDistanceSlider.getMax())))) {
                    
                    objDistanceSlider.setValue((lens1Position-newXPosition)/ratio);
                }
                if ((newYPosition <= (midPoint[1]-(ratio*objHeightSlider.getMin()))) && (newYPosition >=(midPoint[1]-(ratio*objHeightSlider.getMax())))) {
                    
                    objHeightSlider.setValue((midPoint[1]-newYPosition)/ratio);
                }
            }
        });
        objImage.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                
                objImage.toFront();
            }
        });
        objImage.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                //when mouse is pressed, store initial position
                initX = objImage.getTranslateX()-200;
                initY = objImage.getTranslateY();
            }
        });
        lens1Image.setCursor(Cursor.DEFAULT);
        lens1Image.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                //set to do nothing since there's only one lens
                
            }
        });
        lens1Image.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                //set to do nothing since there's only one lens
                //and one lens should remain in the middle
                
            }
        });
    }
    
    //disables sliders in a single lens system since these variables should not be able to be changed
    public static void enableOrDisableSliders(){
        lens1PositionSlider.setDisable(LensController.isSingle);
        lens2PositionSlider.setDisable(LensController.isSingle);
        focalLength2Slider.setDisable(LensController.isSingle);
        lens2TypeSwitch.setDisableVar(LensController.isSingle);
        
    }
    
    //sets the sliders' listeners
    public static void setListeners(){
        //calls the update function if a sliders' values changes
        lens2CheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                setIsSingle(!newValue.booleanValue());
                setSystem();
                setHandlers();
                update();
            }
        });
        focalLength1Slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
			Number oldValue, Number newValue) {
                setSystem();
                focalLength1Text.setText("Focal Length:   "+df.format(newValue.doubleValue())+"cm");
                update();
                
                
            }
        });
        focalLength2Slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
			Number oldValue, Number newValue) {
		setSystem();
                
                focalLength2Text.setText("Focal Length:   "+df.format(newValue.doubleValue())+"cm");
                update();
                
            }
        });
        lens1PositionSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
			Number oldValue, Number newValue) {
                setSystem();
                update();
                
                
            }
        });
        lens2PositionSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
			Number oldValue, Number newValue) {
                setSystem();
                
                update();
                
                
            }
        });
        
        objDistanceSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
			Number oldValue, Number newValue) {
		
                setSystem();
                
                objDistanceText.setText("Object Distance:   "+df.format(newValue.doubleValue())+"cm");
                update();
            }
        });
        objHeightSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
			Number oldValue, Number newValue) {
		
                setSystem();
                objHeightText.setText("Object Height:   "+df.format(newValue.doubleValue())+"cm");
                update();
            }
        });
    }
    
    public static void setMainScreen(){
        //@ANDREW
        
    }
    
    //called when the user selects to help button in the menubar
    public static void setHelpScreen(Stage stage){
        LensView.setHelpLayout(stage);
    }
    
    //resets the lens simulation scene when the user goes back to the lens simulation
    public static void setLensScene(Stage stage){
        stage.setScene(lensScene);
    }
    
    //calculates and returns the lens distance
    public static double getLensDistance(){
        return (37.5+lens1PositionSlider.getMax()-lens1PositionSlider.getValue())+(lens2PositionSlider.getValue());

    }
    
    //sets static variables
    public static void setIsSingle(boolean isSingle){
        LensController.isSingle=isSingle;
    }
    
    public static void setDls(double objDistance, double objHeight, double lens1FLength, double lens2FLength, double lensDistance) {
        dls.setVariables(objDistance,objHeight,lens1FLength,lens2FLength,lensDistance);
    }

    private static void setSls(double objDistance, double objHeight, double lens1FLength) {
        sls.setVariables(objDistance, objHeight, lens1FLength);
    }

    
}
