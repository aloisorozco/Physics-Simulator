/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ToggleSwitch;

import Controller.LensController;
import View.LensView;
import java.util.Objects;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 *
 * @author Github/TheItachiUchiha & alois
 */
//class was imported from github
//and modified to fit the lens simulation

public class ToggleSwitch extends HBox {
	private int fModifier;
        private boolean disable;
	private final Label label = new Label();
	private final Button button = new Button();
	
        public void setDisableVar(boolean disable){
            this.disable=disable;
            if (this.disable){
                button.setOnAction((e) -> {
			System.out.println("nothing");
		});
		label.setOnMouseClicked((e) -> {
			System.out.println("nothing");
		});
            }
            else{
                button.setOnAction((e) -> {
			switchedOn.set(!switchedOn.get());
		});
		label.setOnMouseClicked((e) -> {
			switchedOn.set(!switchedOn.get());
		});
            }
        }
        
	private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);
	public SimpleBooleanProperty switchOnProperty() { return switchedOn; }
	
	private void init() {
		
		label.setText("Converging");
                fModifier=1;
		disable=false;
                
		getChildren().addAll(label, button);	
		button.setOnAction((e) -> {
			switchedOn.set(!switchedOn.get());
		});
		label.setOnMouseClicked((e) -> {
			switchedOn.set(!switchedOn.get());
		});
		setStyle();
		bindProperties();
	}
	
	private void setStyle() {
		//Default Width
		setWidth(100);
                label.setFont(new Font(12));
		label.setAlignment(Pos.CENTER);
		setStyle("-fx-background-color: lightblue; -fx-text-fill:black; -fx-background-radius: 4;");
		setAlignment(Pos.CENTER_LEFT);
	}
	
	private void bindProperties() {
		label.prefWidthProperty().bind(widthProperty().divide(2));
		label.prefHeightProperty().bind(heightProperty());
		button.prefWidthProperty().bind(widthProperty().divide(2));
		button.prefHeightProperty().bind(heightProperty());
	}
	
	public ToggleSwitch(boolean isSingle) {
		init();
                
                
                switchedOn.addListener((a,b,c) -> {
		    if (c) {
                	label.setText("Diverging");
                        fModifier=-1;
                	setStyle("-fx-background-color: lightblue;");
                	label.toFront();
            	    }
            	    else {
            		label.setText("Converging");
                        fModifier=1;
        		setStyle("-fx-background-color: lightblue;");
                	button.toFront();
            	    }
                    LensController.setSystem();
                    LensView.updateLensImage(this, this.fModifier);
                    LensController.update();
                        
                });
                
		
	}

        public int getFModifier() {
            return fModifier;
        }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.fModifier;
        hash = 41 * hash + Objects.hashCode(this.label);
        hash = 41 * hash + Objects.hashCode(this.button);
        hash = 41 * hash + Objects.hashCode(this.switchedOn);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ToggleSwitch other = (ToggleSwitch) obj;
        if (this.fModifier != other.fModifier) {
            return false;
        }
        if (!Objects.equals(this.label, other.label)) {
            return false;
        }
        if (!Objects.equals(this.button, other.button)) {
            return false;
        }
        if (!Objects.equals(this.switchedOn, other.switchedOn)) {
            return false;
        }
        return true;
    }
        
     
}