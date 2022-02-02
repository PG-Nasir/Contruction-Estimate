package com.ComShare;

import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.DoubleStringConverter;

public class NumberField extends TextField{
	Pattern validDoubleText = Pattern.compile("-?((\\d*)|(\\d+\\.\\d*))");
	public NumberField(){
		   TextFormatter<Double> textFormatter = new TextFormatter<Double>(new DoubleStringConverter(), 0.0, 
		            change -> {
		                String newText = change.getControlNewText() ;
		                if (validDoubleText.matcher(newText).matches()) {
		                    return change ;
		                } else return null ;
		            });

		   this.setTextFormatter(textFormatter);
	}
	

}