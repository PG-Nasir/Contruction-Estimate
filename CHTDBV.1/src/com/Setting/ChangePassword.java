package com.Setting;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import com.ComShare.CommonButton;
import com.ComShare.DBUtil;
import com.ComShare.FocusMoveByEnter;
import com.ComShare.FxComboBox;
import com.ComShare.Notification;
import com.ComShare.SessionBean;
import com.ComShare.TextRead;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ChangePassword extends JFXPanel{
	SessionBean sessionBeam=new SessionBean();

	public Pane pane =new Pane();
	public Scene scene=new Scene(pane,340, 155);;
	public Stage mainItemStage=new Stage();


	Button btnChange=new Button("Change");

	TextField txtUsername;
	PasswordField txtOldPassword,txtNewPassword;

	public ChangePassword(SessionBean sessionbeam){
		this.sessionBeam=sessionbeam;

	}
	
	public void start(Stage primaryStage) throws Exception {
		scene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
		this.mainItemStage=primaryStage;
		mainItemStage.setScene(scene);
		mainItemStage.show();
		mainItemStage.setAlwaysOnTop(true);
		mainItemStage.setTitle("Change Password");
		addCmp();
		btnActionEvent();
	}
	
	@SuppressWarnings("unchecked")
	private void btnActionEvent(){



		btnChange.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				btnChangeEvent();
			}
		});

	}

	private void btnChangeEvent(){
		if(!txtOldPassword.getText().trim().toString().isEmpty()){
			if(!txtNewPassword.getText().trim().toString().isEmpty()){
				if(checkValidUserPassword()){
					sessionBeam.setPassword(txtNewPassword.getText().trim().toString());
					try {
						String sql="update tblogin set password='"+sessionBeam.getPassword()+"' where username='"+txtUsername.getText().trim().toString()+"'";
						DBUtil.dbExecuteUpdate(sql);
						new Notification(Pos.TOP_CENTER, "Information graphic", "Information!", "Password change successfully");
					} catch (Exception e) {
						e.printStackTrace();
						new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
					}
				}
				else{
					new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!!","Invalid Username and password");
				}
			}
			else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!!","Provide new password");
			}
		}
		else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!!","Provide old password");
		}
	}

	private boolean checkValidUserPassword(){

		try {
			sessionBeam.setPassword(txtOldPassword.getText().trim().toString());

			String selectStmt = "select * from tblogin where username='"+txtUsername.getText().trim().toString()+"' and password='"+sessionBeam.getPassword()+"' ";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
		return false; 

	}
	@SuppressWarnings("unchecked")
	private void addCmp(){


		Label lblUsername=new Label("User Name");
		lblUsername.setLayoutX(10);
		lblUsername.setLayoutY(10);

		txtUsername=new TextField();
		txtUsername.setLayoutX(100);
		txtUsername.setLayoutY(10);
		txtUsername.setMaxWidth(120);
		txtUsername.setMinHeight(28);
		txtUsername.setEditable(false);
		txtUsername.setText(sessionBeam.getUserName());

		Label lblOldPassword=new Label("Old Password");
		lblOldPassword.setLayoutX(10);
		lblOldPassword.setLayoutY(45);

		txtOldPassword=new PasswordField();
		txtOldPassword.setLayoutX(100);
		txtOldPassword.setLayoutY(40);
		txtOldPassword.setMinWidth(200);
		txtOldPassword.setMinHeight(28);

		Label lblNewPassword=new Label("New Password");
		lblNewPassword.setLayoutX(10);
		lblNewPassword.setLayoutY(75);

		txtNewPassword=new PasswordField();
		txtNewPassword.setLayoutX(100);
		txtNewPassword.setLayoutY(70);
		txtNewPassword.setMinWidth(200);
		txtNewPassword.setMinHeight(28);


		btnChange.setLayoutX(100);
		btnChange.setLayoutY(110);
		btnChange.setMinWidth(100);
		btnChange.setMinHeight(30);

		pane.getChildren().addAll(lblUsername,txtUsername,lblOldPassword,txtOldPassword,lblNewPassword,txtNewPassword,btnChange);
	}


}


