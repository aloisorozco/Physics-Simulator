/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.LensController;
import static Controller.LensController.convergingLensImg;
import static Controller.LensController.divergingLensImg;
import static Controller.LensController.dls;
import static Controller.LensController.focalLength1Slider;
import static Controller.LensController.focalLength2Slider;
import static Controller.LensController.getLensDistance;
import static Controller.LensController.imgDistanceText;
import static Controller.LensController.imgHeightText;
import static Controller.LensController.magText;
import static Controller.LensController.objDistanceSlider;
import static Controller.LensController.objHeightSlider;
import static Controller.LensController.objImage;
import static Controller.LensController.imgImage;
import static Controller.LensController.lens1Image;
import static Controller.LensController.lens1PositionSlider;
import static Controller.LensController.lens1TypeSwitch;
import static Controller.LensController.lens2Image;
import static Controller.LensController.lens2PositionSlider;
import static Controller.LensController.lens2TypeSwitch;
import static Controller.LensController.pencilImg;
import static Controller.LensController.pencilInvertedImg;
import static Controller.LensController.focalLength1Text;
import static Controller.LensController.focalLength2Text;
import static Controller.LensController.lens1PositionText;
import static Controller.LensController.lens2PositionText;
import static Controller.LensController.lens2CheckBox;
import static Controller.LensController.lensScene;
import static Controller.LensController.objDistanceText;
import static Controller.LensController.objHeightText;
import static Controller.LensController.helpScene;
import static Controller.LensController.sls;


import Model.DoubleLensSystem;
import Model.LensSystem;
import Model.SingleLensSystem;
import ToggleSwitch.ToggleSwitch;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
public class LensView {
    public static double ratio=7.5;
    public static int[] midPoint = {300,200};
    
    static String cssLayout = "-fx-border-color: grey;\n" +
                   "-fx-border-insets: 5;\n" +
                   "-fx-border-width: 3;\n" +
                   "-fx-background-color: ghostwhite";
    static Insets ins5 = new Insets(5,5,5,5);
    static DecimalFormat df = new DecimalFormat("#.#");
    public static Pane root;
    
    //sets the gui of the lens simulation
    public static void setLayout(Stage stage){
        
        
        BorderPane borderPane = new BorderPane();
        
        //RIGHT SETTINGS BOX LAYOUT
        VBox rightSettingsBox = new VBox();
        rightSettingsBox.setSpacing(10);
        rightSettingsBox.setPadding(ins5);
        rightSettingsBox.setPrefWidth(200);
        rightSettingsBox.setPrefHeight(400);
        rightSettingsBox.setStyle(cssLayout);
        
        //LEFT SETTINGS BOX LAYOUT
        VBox leftSettingsBox = new VBox();
        leftSettingsBox.setSpacing(10);
        leftSettingsBox.setPadding(ins5);
        leftSettingsBox.setPrefWidth(200);
        leftSettingsBox.setPrefHeight(400);
        leftSettingsBox.setStyle(cssLayout);
        
        //SETUP OF VARIABLES' TEXTS & SLIDERS
        VBox lens1TypeBox = new VBox();
        lens1TypeBox.setPadding(ins5);
        lens1TypeBox.setSpacing(5);
        Text lens1TypeText = new Text("Lens Type:");
        lens1TypeSwitch = new ToggleSwitch(false);
        lens1TypeBox.getChildren().addAll(lens1TypeText, lens1TypeSwitch);
        
        VBox lens2TypeBox = new VBox();
        lens2TypeBox.setPadding(ins5);
        lens2TypeBox.setSpacing(5);
        Text lens2TypeText = new Text("Lens Type:");
        lens2TypeSwitch = new ToggleSwitch(false);
        lens2TypeBox.getChildren().addAll(lens2TypeText, lens2TypeSwitch);
        
        VBox focalLength1Box = new VBox();
        focalLength1Box.setPadding(ins5);
        focalLength1Box.setSpacing(5);
        focalLength1Slider = new Slider(2,15,5);
        focalLength1Slider.setShowTickMarks(true);
        focalLength1Slider.setShowTickLabels(true);
        focalLength1Text = new Text("Focal Length:   "+df.format(focalLength1Slider.getValue())+"cm");
        focalLength1Box.getChildren().addAll(focalLength1Text, focalLength1Slider);
        
        VBox focalLength2Box = new VBox();
        focalLength2Box.setPadding(ins5);
        focalLength2Box.setSpacing(5);
        focalLength2Slider = new Slider(2,15,5);
        focalLength2Slider.setShowTickMarks(true);
        focalLength2Slider.setShowTickLabels(true);
        focalLength2Text = new Text("Focal Length:   "+df.format(focalLength2Slider.getValue())+"cm");
        focalLength2Box.getChildren().addAll(focalLength2Text, focalLength2Slider);
        
        VBox lens1PositionBox = new VBox();
        lens1PositionBox.setPadding(ins5);
        lens1PositionBox.setSpacing(5);
        lens1PositionSlider = new Slider(0,5,2);
        lens1PositionText = new Text("Lens Position:");
        lens1PositionBox.getChildren().addAll(lens1PositionText,lens1PositionSlider);
        
        VBox lens2PositionBox = new VBox();
        lens2PositionBox.setPadding(ins5);
        lens2PositionBox.setSpacing(5);
        lens2PositionSlider = new Slider(0,5,2);
        //lens2PositionSlider.setShowTickMarks(true);
        lens2PositionText = new Text("Lens Position:");
        lens2PositionBox.getChildren().addAll(lens2PositionText,lens2PositionSlider);
        
        VBox lens1Box = new VBox();
        lens1Box.setPadding(ins5);
        Text lens1Title = new Text("Lens 1");
        VBox lens1TitleBox = new VBox();
        lens1TitleBox.setPadding(ins5);
        lens1TitleBox.setAlignment(Pos.TOP_LEFT);
        lens1TitleBox.getChildren().addAll(lens1Title);
        lens1Title.setFont(new Font("Arial", 15));
        lens1Title.setFill(Color.DARKSLATEGREY);
        lens1Box.getChildren().addAll(lens1TitleBox,lens1TypeBox,lens1PositionBox,focalLength1Box);
        
        ArrayList<Text> lens2Texts = new ArrayList<>();
        VBox lens2Box = new VBox();
        lens2Box.setPadding(ins5);
        Text lens2Title = new Text("Lens 2");
        lens2CheckBox = new CheckBox("");
        lens2CheckBox.setSelected(true);
        lens2CheckBox.setPrefSize(10, 10);
        HBox lens2TitleBox = new HBox();
        lens2TitleBox.setPadding(ins5);
        lens2TitleBox.setSpacing(20);
        lens2TitleBox.setAlignment(Pos.TOP_LEFT);
        lens2TitleBox.getChildren().addAll(lens2Title,lens2CheckBox);
        lens2Title.setFont(new Font("Arial", 15));
        lens2Title.setFill(Color.DARKSLATEGREY);
        lens2Box.getChildren().addAll(lens2TitleBox,lens2TypeBox,lens2PositionBox,focalLength2Box);
        
        VBox objDistanceBox = new VBox();
        objDistanceBox.setPadding(ins5);
        objDistanceBox.setSpacing(5);
        objDistanceSlider = new Slider(2,20,10);
        objDistanceSlider.setShowTickMarks(true);
        objDistanceSlider.setShowTickLabels(true);
        objDistanceText = new Text("Object Distance:   "+df.format(objDistanceSlider.getValue())+"cm");
        objDistanceBox.getChildren().addAll(objDistanceText,objDistanceSlider);
        
        VBox objHeightBox = new VBox();
        objHeightBox.setPadding(ins5);
        objHeightBox.setSpacing(5);
        objHeightSlider = new Slider(4,12,6);
        objHeightSlider.setShowTickMarks(true);
        objHeightSlider.setShowTickLabels(true);
        objHeightText = new Text("Object Height:   "+df.format(objHeightSlider.getValue())+"cm");
        objHeightBox.getChildren().addAll(objHeightText,objHeightSlider);
        
        LensController.sls = new SingleLensSystem(objDistanceSlider.getValue(),objHeightSlider.getValue(),lens1TypeSwitch.getFModifier()*focalLength1Slider.getValue());
        LensController.dls = new DoubleLensSystem(objDistanceSlider.getValue(),objHeightSlider.getValue(),lens1TypeSwitch.getFModifier()*focalLength1Slider.getValue(),
                lens2TypeSwitch.getFModifier()*focalLength2Slider.getValue(),getLensDistance());
        
        VBox resultsBox = new VBox();
        resultsBox.setSpacing(10);
        resultsBox.setPadding(new Insets(0,5,0,5));
        imgDistanceText = new Text("Image Distance:   "+df.format(LensController.dls.getImgDistance())+"cm");
        imgHeightText = new Text("Image Height:   "+df.format(LensController.dls.getImgHeight())+"cm");
        magText = new Text("Magnification:   "+df.format(LensController.dls.getMag()));
        
        resultsBox.getChildren().addAll(imgDistanceText, imgHeightText,magText);
        
        leftSettingsBox.getChildren().addAll(lens1Box,objDistanceBox,objHeightBox);
        rightSettingsBox.getChildren().addAll(lens2Box,resultsBox);
        
        
        root = new Pane();
        
        root.setStyle(cssLayout);
        setShapes();
        
        Text title = new Text("Lens Simulation");
        title.setFont(new Font("Arial", 20));
        title.setFill(Color.DARKSLATEGREY);
        VBox titleBox = new VBox(title);
        titleBox.setPadding(ins5);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setStyle("-fx-background-color: ghostwhite");
        
        VBox topBox = new VBox();
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem mainScreenBtn = new MenuItem("Menu");
        fileMenu.getItems().add(mainScreenBtn);
        EventHandler<ActionEvent> mainScreenEvent = (ActionEvent e) -> {
            Controller.LensController.setMainScreen();
        };
        mainScreenBtn.setOnAction(mainScreenEvent);
        Menu helpMenu = new Menu("Help");
        MenuItem helpBtn = new MenuItem("Help");
        helpMenu.getItems().add(helpBtn);
        EventHandler<ActionEvent> helpEvent = (ActionEvent e) -> {
            Controller.LensController.setHelpScreen(stage);
        };
        helpBtn.setOnAction(helpEvent);
        menuBar.getMenus().addAll(fileMenu,helpMenu);
        topBox.getChildren().addAll(menuBar,titleBox);

        
        borderPane.setTop(topBox);
        borderPane.setCenter(root);
        borderPane.setLeft(leftSettingsBox);
        borderPane.setRight(rightSettingsBox);
        
        lensScene = new Scene(borderPane, 1015,475);
        stage.setTitle("Lens Simulation");
        stage.setScene(lensScene);
        stage.show();
    }
    
    //checks the system type then calls appropriate updateshapes function
    public static void updateShapes(){
        if (LensController.isSingle){
            updateShapesForSls();
        }
        else{
            updateShapesForDls();
        }
        
    }
    
    //redraws single lens system when system variables change
    public static void updateShapesForSls(){
        double lens1Position=midPoint[0];
        double objX = lens1Position-(ratio*objDistanceSlider.getValue());
        double objEndY = midPoint[1];
        double objStartY = objEndY-(ratio*objHeightSlider.getValue());
        
        objImage.setX(objX-(objImage.getFitWidth()/2));
        objImage.setY(objStartY);
        objImage.setFitHeight(ratio*objHeightSlider.getValue());
        
        double imgX = lens1Position+(ratio*sls.getImgDistance());
        double imgEndY = midPoint[1];
        double imgStartY = imgEndY-(ratio*sls.getImgHeight());
        
        imgImage.setX(imgX-(imgImage.getFitWidth()/2));
        if (ratio*sls.getImgHeight()>=0){
            imgImage.setImage(pencilImg);
            if (ratio*sls.getImgHeight()<=100){
                imgImage.setY(imgStartY);
                imgImage.setFitHeight(ratio*sls.getImgHeight());
            }
            else{
                imgImage.setY(imgEndY-100);
                imgImage.setFitHeight(100);
            }
            
        }
        else if (ratio*sls.getImgHeight()<0){
            imgImage.setImage(pencilInvertedImg);
            imgImage.setY(imgEndY);
            
            if (ratio*sls.getImgHeight()>=-100){
                
                imgImage.setFitHeight(-ratio*sls.getImgHeight());
            }
            else {
                imgImage.setFitHeight(100);
            }
            
        }
        
        
        if (root.getChildren().contains(lens2Image)){
            root.getChildren().remove(lens2Image);
            
        }
        
        lens1Image.setX(lens1Position-(lens1Image.getFitWidth()/2));
        
    }
    
    //redraws double lens system when system variables change
    public static void updateShapesForDls(){
        double lens1Position = midPoint[0]-(ratio*lens1PositionSlider.getMax()+37.5)+(ratio*lens1PositionSlider.getValue());
        double objX = lens1Position-(ratio*objDistanceSlider.getValue());
        double objEndY = midPoint[1];
        double objStartY = objEndY-(ratio*objHeightSlider.getValue());
        
        objImage.setX(objX-(objImage.getFitWidth()/2));
        objImage.setY(objStartY);
        objImage.setFitHeight(ratio*objHeightSlider.getValue());
        
        double lens2Position = midPoint[0]+(ratio*lens2PositionSlider.getValue());
        double imgX = lens2Position+(ratio*dls.getImgDistance());
        double imgEndY = midPoint[1];
        double imgStartY = imgEndY-(ratio*dls.getImgHeight());
        
        imgImage.setX(imgX-(imgImage.getFitWidth()/2));
        if (ratio*dls.getImgHeight()>=0){
            imgImage.setImage(pencilImg);
            if (ratio*dls.getImgHeight()<=100){
                imgImage.setY(imgStartY);
                imgImage.setFitHeight(ratio*dls.getImgHeight());
            }
            else{
                imgImage.setY(imgEndY-100);
                imgImage.setFitHeight(100);
            }
        }
        else if (ratio*dls.getImgHeight()<0){
            imgImage.setImage(pencilInvertedImg);
            imgImage.setY(imgEndY);
            if (ratio*dls.getImgHeight()>=-100){
                imgImage.setFitHeight(-ratio*dls.getImgHeight());
            }
            else{
                imgImage.setFitHeight(100);
            }
            
        }
        
        if (!root.getChildren().contains(lens2Image)){
            root.getChildren().add(lens2Image);
        }
        
        lens1Image.setX(lens1Position-(lens1Image.getFitWidth()/2));
        lens2Image.setX(lens2Position-(lens2Image.getFitWidth()/2));
        
    }
    
    //updates the lens image: converging or diverging depending on toggleswitch
    public static void updateLensImage(ToggleSwitch toggleSwitch, int fModifier){
        if (toggleSwitch.equals(lens1TypeSwitch)){
            if (fModifier==1){
                lens1Image.setImage(convergingLensImg);
            }
            else if (fModifier==-1){
                lens1Image.setImage(divergingLensImg);
            }
        }
        else if (toggleSwitch.equals(lens2TypeSwitch)){
            if (fModifier==1){
                lens2Image.setImage(convergingLensImg);
            }
            else if (fModifier==-1){
                lens2Image.setImage(divergingLensImg);
            }
        }
    }
    
    //draws the shapes when the system is first set up
    public static void setShapes(){
        double lens1Position = midPoint[0]-(ratio*lens1PositionSlider.getMax()+37.5)+(ratio*lens1PositionSlider.getValue());
        double objX = lens1Position-(ratio*objDistanceSlider.getValue());
        double objEndY = midPoint[1];
        double objStartY = objEndY-(ratio*objHeightSlider.getValue());
        
        pencilImg = new Image("file:Images/pencil.png");
        pencilInvertedImg = new Image("file:Images/pencilInverted.png");
        
        objImage = new ImageView(pencilImg);
        objImage.setPreserveRatio(false);
        objImage.setX(objX-(objImage.getFitWidth()/2));
        objImage.setY(objStartY);
        objImage.setFitHeight(ratio*objHeightSlider.getValue());
        
        
        double lens2Position = midPoint[0]+(ratio*lens2PositionSlider.getValue());
        double imgX = lens2Position+(ratio*dls.getImgDistance());
        double imgEndY = midPoint[1];
        double imgStartY = imgEndY-(ratio*dls.getImgHeight());
        
        imgImage = new ImageView(pencilImg);
        imgImage.setPreserveRatio(false);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setContrast(0.5);
        colorAdjust.setBrightness(-0.5);
        colorAdjust.setSaturation(0.2);
        imgImage.setEffect(colorAdjust);
        imgImage.setX(imgX-(imgImage.getFitWidth()/2));
        imgImage.setY(imgStartY);
        imgImage.setFitHeight(ratio*dls.getImgHeight());
        
        
        convergingLensImg = new Image("file:Images/converging.png");
        divergingLensImg = new Image("file:Images/diverging.png");
        
        lens1Image = new ImageView(convergingLensImg);
        lens1Image.setX(lens1Position-(lens1Image.getFitWidth()/2));
        lens1Image.setY(125);
        
        lens2Image = new ImageView(convergingLensImg);
        lens2Image.setX(lens2Position-(lens2Image.getFitWidth()/2));
        lens2Image.setY(125);
        
        setLegend();
        LensController.setHandlersForDls();
      
        root.getChildren().addAll(lens1Image,lens2Image,objImage,imgImage);
        
    }
    
    //draws the legend that shows the ratio
    public static void setLegend(){
        Text legendText = new Text("5cm");
        legendText.setFont(new Font(12));
        legendText.setX(32);
        legendText.setY(380);
        Line legendLine = new Line(25,390,25+(5*ratio),390);
        root.getChildren().addAll(legendText,legendLine);
    }
    
    //sets the help page scene
    static boolean isHelpSceneSet = false;
    public static void setHelpLayout(Stage stage){
        
        if (!isHelpSceneSet){
            isHelpSceneSet = true;
            
            VBox box = new VBox();
            
            VBox vbox = new VBox();
            vbox.setPadding(ins5);
            vbox.setSpacing(10);
            vbox.setAlignment(Pos.CENTER);
            vbox.setStyle("-fx-background-color: ghostwhite");

            MenuBar menuBar = new MenuBar();
            Menu backMenu = new Menu("Back");
            MenuItem backMenuBtn = new MenuItem("Back");
            backMenu.getItems().add(backMenuBtn);
            menuBar.getMenus().add(backMenu);
            EventHandler<ActionEvent> backEvent = (ActionEvent e) -> {
                Controller.LensController.setLensScene(stage);
            };
            backMenuBtn.setOnAction(backEvent);

            Text title = new Text("Help");
            title.setFont(new Font("Arial", 20));
            title.setFill(Color.DARKSLATEGREY);

            Image helpImg = new Image("file:Images/helpImg.png");
            ImageView helpImage= new ImageView(helpImg);
            helpImage.setPreserveRatio(true);
            helpImage.setFitWidth(760);

            Text helpText = new Text("Use the sliders or drag the pencil object and lenses to see the results of the lens system");
            helpText.setFont(new Font("Arial", 20));
            helpText.setFill(Color.DARKSLATEGREY);

            vbox.getChildren().addAll(title,helpImage,helpText);
            box.getChildren().addAll(menuBar,vbox);

            helpScene = new Scene(box,1015,475);
            
        }
        stage.setScene(helpScene);
    }
    
}
