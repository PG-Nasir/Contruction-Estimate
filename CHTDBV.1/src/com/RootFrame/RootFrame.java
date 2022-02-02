package com.RootFrame;

import java.awt.Graphics;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JDesktopPane;

import com.ComShare.Notification;
import com.ComShare.SessionBean;
import com.ContructionModule.Billing;
import com.ContructionModule.DivisionCodeCreate;
import com.ContructionModule.EstimateCreate;
import com.ContructionModule.ItemCodeCreate;
import com.ContructionModule.UnitCreate;
import com.Setting.AuthenticationCreate;
import com.Setting.ChangePassword;
import com.Setting.ModuleCreate;
import com.Setting.ModuleItemCreate;
import com.Setting.UserCreate;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class RootFrame extends Application{
	SessionBean sessionBeam=new SessionBean();
	public Scene scene=null;
	public Stage mainStage=null;
	Pane pane =null;
	Pane paneChild =new Pane();;
	Screen screen = Screen.getPrimary();
	Rectangle2D bounds = screen.getVisualBounds();

	private ToggleGroup gpmodule=new ToggleGroup();
	private ToggleGroup gpchild=new ToggleGroup();
	private ToggleGroup gpChildItem=new ToggleGroup();
	private Button btnListOfModule=new Button("List Of Module");
	private RadioButton btnSetting=new RadioButton("Setting");
	private RadioButton btnConstructionModule=new RadioButton("Construction Module");

	private RadioButton btnForm=new RadioButton("Forms");
	private RadioButton btnReport=new RadioButton("Statement Reports");

	private Button btnLogout;
	//.............Setting.......................
	private RadioButton btnModuleCreate=new RadioButton("Module Create");
	private RadioButton btnModuleItemCreate=new RadioButton("Module Item Create");
	
	private RadioButton btnUserCreate=new RadioButton("User Create");
	private RadioButton btnAuthentication=new RadioButton("Authentication");
	private RadioButton btnChangePassord=new RadioButton("Change Password");
	
	//...........Consturction.............................
	private RadioButton btnUnitCreate=new RadioButton("Unit Create");
	private RadioButton btnDivisionCreate=new RadioButton("Division Create");
	private RadioButton btnItemCreate=new RadioButton("Item Create");
	private RadioButton btnEstimateCreate=new RadioButton("Estimate Create");
	private RadioButton btnBilling=new RadioButton("Billing");
	public RootFrame(SessionBean sessionbeam) {
		this.sessionBeam=sessionbeam;
	}
	public void start(Stage primaryStage) throws Exception {

		this.mainStage=primaryStage;
		pane=new Pane();
		scene = new Scene(pane,bounds.getWidth(),bounds.getHeight());
		mainStage.setScene(scene);
		pane.setId("RootSense");
		scene.getStylesheets().add(getClass().getResource("/StyleFile/common.css").toExternalForm());
		//mainStage.setResizable(false);
		mainStage.centerOnScreen();
		mainStage.setY(3);
		mainStage.setTitle(sessionBeam.getSystemName()+" Software Developed By ::-"+sessionBeam.getDeveloperCompanyName()+" || "+sessionBeam.getWeb());
		mainStage.getIcons().add(new Image(getClass().getResourceAsStream("/Icons/cursor.jpg")));
		mainStage.show();
		mainStage.setOnCloseRequest(x -> Platform.exit());
		addCmp();
		btnActionEvent();
		btnSystemActionEvent();
		btnConstructionEvent();
	}
	private void btnActionEvent(){
		btnSetting.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				paneChild.getChildren().removeAll(paneChild.getChildren());
				SettingModuleItem();
			}
		});
		btnConstructionModule.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				paneChild.getChildren().removeAll(paneChild.getChildren());
				ConstructionModuleItem();
			}
		});
		btnForm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FormReportRevalided();
			}
		});
		btnReport.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FormReportRevalided();
			}
		});
	}
	private void FormReportRevalided(){
		paneChild.getChildren().removeAll(paneChild.getChildren());
		if(btnSetting.isSelected()){
			SettingModuleItem();
		}
		if(btnConstructionModule.isSelected()){
			ConstructionModuleItem();
		}
	}
	private void btnConstructionEvent(){
		btnUnitCreate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				UnitCreate MC=new UnitCreate(sessionBeam);
				try {
					MC.start(MC.mainItemStage);
					btnUnitCreate.setSelected(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
/*				UnitCreate MC=new UnitCreate(sessionBeam);
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.initOwner(mainStage);
				Scene dialogScene = new Scene(MC.pane, 820, 400);
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialogScene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
				dialog.setTitle("Unit Create");
				dialog.setScene(dialogScene);
				dialog.show();
				btnUnitCreate.setSelected(false);*/
				
			}
		});
		btnDivisionCreate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				DivisionCodeCreate division=new DivisionCodeCreate(sessionBeam);
				try {
					division.start(division.mainItemStage);
					btnDivisionCreate.setSelected(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
/*				DivisionCodeCreate MC=new DivisionCodeCreate(sessionBeam);
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.initOwner(mainStage);
				Scene dialogScene = new Scene(MC.pane, 1020, 400);
				dialogScene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
				dialog.setTitle("Division Create");
				dialog.setScene(dialogScene);
				dialog.show();
				btnDivisionCreate.setSelected(false);*/
			}
		});
		btnItemCreate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				ItemCodeCreate ItemCodeC=new ItemCodeCreate(sessionBeam);
				try {
					ItemCodeC.start(ItemCodeC.mainItemStage);
					btnItemCreate.setSelected(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
/*				ItemCodeCreate MC=new ItemCodeCreate(sessionBeam);
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.initOwner(mainStage);
				Scene dialogScene = new Scene(MC.pane, 1300, 480);
				dialogScene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
				dialog.setTitle("Item Create");
				dialog.setScene(dialogScene);
				dialog.show();
				btnItemCreate.setSelected(false);
				MC.loadDivisionCode();*/
			}
		});
		
		btnEstimateCreate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				EstimateCreate estimateCreate=new EstimateCreate(sessionBeam);
				try {
					estimateCreate.start(estimateCreate.mainItemStage);
					btnItemCreate.setSelected(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
/*				EstimateCreate MC=new EstimateCreate(sessionBeam);
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.initOwner(mainStage);
				Scene dialogScene = new Scene(MC.pane, 1350, 660);
				dialogScene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
				dialog.setTitle("Estimate Create");
				dialog.setScene(dialogScene);
				dialog.show();*/
				
				btnEstimateCreate.setSelected(false);
			}
		});
		
		btnBilling.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				Billing billingCreate=new Billing(sessionBeam);
				try {
					billingCreate.start(billingCreate.mainItemStage);
					btnBilling.setSelected(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
/*				Billing billing=new Billing(sessionBeam);
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.initOwner(mainStage);
				Scene dialogScene = new Scene(billing.pane, 1250, 640);
				dialogScene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
				dialog.setTitle("Billing");
				dialog.setScene(dialogScene);
				dialog.show();*/
				btnBilling.setSelected(false);
			}
		});
	}
	private void btnSystemActionEvent(){
		

		
		btnModuleCreate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				ModuleCreate modelC=new ModuleCreate(sessionBeam);
				try {
					modelC.start(modelC.mainItemStage);
					btnModuleCreate.setSelected(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
/*				ModuleCreate MC=new ModuleCreate(sessionBeam);
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.initOwner(mainStage);
				Scene dialogScene = new Scene(MC.pane, 820, 400);
				dialogScene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
				dialog.setTitle("Module Create");
				dialog.setScene(dialogScene);
				dialog.show();
				btnModuleCreate.setSelected(false);*/
			}
		});
		btnModuleItemCreate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				ModuleItemCreate MIC=new ModuleItemCreate(sessionBeam);
				try {
					MIC.start(MIC.mainItemStage);
					btnModuleItemCreate.setSelected(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
/*				ModuleItemCreate MIC=new ModuleItemCreate(sessionBeam);
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.initOwner(mainStage);
				Scene dialogScene = new Scene(MIC.pane, 950, 400);
				dialogScene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
				dialog.setTitle("Module Item Create");
				dialog.setScene(dialogScene);
				dialog.show();
				btnModuleItemCreate.setSelected(false);*/
			}
		});
		btnUserCreate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				UserCreate UC=new UserCreate(sessionBeam);
				try {
					UC.start(UC.mainItemStage);
					btnUserCreate.setSelected(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
/*				UserCreate UC=new UserCreate(sessionBeam);
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.initOwner(mainStage);
				Scene dialogScene = new Scene(UC.pane, 820, 400);
				dialogScene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
				dialog.setTitle("User Create");
				dialog.setScene(dialogScene);
				dialog.show();
				btnUserCreate.setSelected(false);*/
			}
		});
		btnAuthentication.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				AuthenticationCreate AC=new AuthenticationCreate(sessionBeam);
				try {
					AC.start(AC.mainItemStage);
					btnAuthentication.setSelected(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
/*				
				AuthenticationCreate AC=new AuthenticationCreate(sessionBeam);
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.initOwner(mainStage);
				Scene dialogScene = new Scene(AC.pane, 960, 400);
				dialogScene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
				dialog.setTitle("Authentication");
				dialog.setScene(dialogScene);
				dialog.show();
				btnAuthentication.setSelected(false);*/
			}
		});
		btnChangePassord.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ChangePassword AC=new ChangePassword(sessionBeam);
				try {
					AC.start(AC.mainItemStage);
					btnChangePassord.setSelected(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
/*				ChangePassword AC=new ChangePassword(sessionBeam);
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.initOwner(mainStage);
				Scene dialogScene = new Scene(AC.pane, 340, 155);
				dialogScene.getStylesheets().add(getClass().getResource("/StyleFile/ModuleItem.css").toExternalForm());
				dialog.setTitle("Change Password");
				dialog.setScene(dialogScene);
				dialog.show();
				btnChangePassord.setSelected(false);*/
			}
		});
		
	}
	
	private void ConstructionModuleItem(){
		int y=10;

		if(btnForm.isSelected()){
			
			Label lblSetup=new Label("Setup");
			lblSetup.setMinWidth(200);
			lblSetup.setMinHeight(25);
			lblSetup.setLayoutX(20);
			lblSetup.setLayoutY(y);
			lblSetup.setId("ItemCatTitle");
			y+=30;
			
			btnUnitCreate.setMinWidth(180);
			btnUnitCreate.setMinHeight(28);
			btnUnitCreate.setLayoutX(20);
			btnUnitCreate.setLayoutY(y);
			btnUnitCreate.setToggleGroup(gpChildItem);
			y+=24;

			btnDivisionCreate.setMinWidth(180);
			btnDivisionCreate.setMinHeight(28);
			btnDivisionCreate.setLayoutX(20);
			btnDivisionCreate.setLayoutY(y);
			btnDivisionCreate.setToggleGroup(gpChildItem);
			y+=24;
			
			btnItemCreate.setMinWidth(180);
			btnItemCreate.setMinHeight(28);
			btnItemCreate.setLayoutX(20);
			btnItemCreate.setLayoutY(y);
			btnItemCreate.setToggleGroup(gpChildItem);
			y+=40;

			Label lblTransaction=new Label("Transaction");
			lblTransaction.setMinWidth(200);
			lblTransaction.setMinHeight(25);
			lblTransaction.setLayoutX(20);
			lblTransaction.setLayoutY(y);
			lblTransaction.setId("ItemCatTitle");
			y+=24;
			
			btnEstimateCreate.setMinWidth(180);
			btnEstimateCreate.setMinHeight(28);
			btnEstimateCreate.setLayoutX(20);
			btnEstimateCreate.setLayoutY(y);
			btnEstimateCreate.setToggleGroup(gpChildItem);
			y+=24;

			btnBilling.setMinWidth(180);
			btnBilling.setMinHeight(28);
			btnBilling.setLayoutX(20);
			btnBilling.setLayoutY(y);
			btnBilling.setToggleGroup(gpChildItem);
			y+=24;
			paneChild.getChildren().addAll(lblSetup,btnUnitCreate,btnDivisionCreate,btnItemCreate,lblTransaction,btnEstimateCreate,btnBilling);

		}
	}
	
	private void SettingModuleItem(){
		int y=10;

		if(btnForm.isSelected()){
			
			btnModuleCreate.setMinWidth(180);
			btnModuleCreate.setMinHeight(28);
			btnModuleCreate.setLayoutX(20);
			btnModuleCreate.setLayoutY(y);
			btnModuleCreate.setToggleGroup(gpChildItem);
			y+=24;

			btnModuleItemCreate.setMinWidth(180);
			btnModuleItemCreate.setMinHeight(28);
			btnModuleItemCreate.setLayoutX(20);
			btnModuleItemCreate.setLayoutY(y);
			btnModuleItemCreate.setToggleGroup(gpChildItem);
			y+=40;


			Label lblSecuritty=new Label("Security");
			lblSecuritty.setMinWidth(200);
			lblSecuritty.setMinHeight(25);
			lblSecuritty.setLayoutX(20);
			lblSecuritty.setLayoutY(y);
			lblSecuritty.setId("ItemCatTitle");
			y+=24;

			btnUserCreate.setMinWidth(180);
			btnUserCreate.setMinHeight(28);
			btnUserCreate.setLayoutX(20);
			btnUserCreate.setLayoutY(y);
			btnUserCreate.setToggleGroup(gpChildItem);
			y+=24;

			btnAuthentication.setMinWidth(180);
			btnAuthentication.setMinHeight(28);
			btnAuthentication.setLayoutX(20);
			btnAuthentication.setLayoutY(y);
			btnAuthentication.setToggleGroup(gpChildItem);
			y+=24;

			btnChangePassord.setMinWidth(180);
			btnChangePassord.setMinHeight(28);
			btnChangePassord.setLayoutX(20);
			btnChangePassord.setLayoutY(y);
			btnChangePassord.setToggleGroup(gpChildItem);

			paneChild.getChildren().addAll(btnModuleCreate,btnModuleItemCreate,lblSecuritty,btnUserCreate,btnAuthentication,btnChangePassord);

		}
	}
	
	private void addCmp(){
		Label lblCompanyIcon=new Label("");
		lblCompanyIcon.setLayoutX(20);
		lblCompanyIcon.setLayoutY(2);
		lblCompanyIcon.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/Icons/logo.png"))));

		Label lblClientName=new Label(sessionBeam.getClientCompanyName());
		lblClientName.setLayoutX(120);
		lblClientName.setLayoutY(10);
		lblClientName.setId("ClientName");


		Label lblClientContact=new Label(sessionBeam.getClientPhone());
		lblClientContact.setLayoutX(120);
		lblClientContact.setLayoutY(35);
		lblClientContact.setId("cleintInfo");

		Label lblClientAddress=new Label(sessionBeam.getClientAddress());
		lblClientAddress.setLayoutX(120);
		lblClientAddress.setLayoutY(50);
		lblClientAddress.setId("cleintInfo");

		Label lblUserName=new Label("User :");
		lblUserName.setLayoutX(bounds.getWidth()-180);
		lblUserName.setLayoutY(10);
		lblUserName.setId("cleintInfo");

		Label lblUserValue=new Label(sessionBeam.getUserName());
		lblUserValue.setLayoutX(bounds.getWidth()-145);
		lblUserValue.setLayoutY(10);

		Label lblDepartment=new Label("Department :");
		lblDepartment.setLayoutX(bounds.getWidth()-220);
		lblDepartment.setLayoutY(25);
		lblDepartment.setId("cleintInfo");

		Label lblDepartmentValue=new Label(sessionBeam.getDepartment());
		lblDepartmentValue.setLayoutX(bounds.getWidth()-145);
		lblDepartmentValue.setLayoutY(25);

		Label lblLoginMachine=new Label("Device :");
		lblLoginMachine.setLayoutX(bounds.getWidth()-192);
		lblLoginMachine.setLayoutY(40);
		lblLoginMachine.setId("cleintInfo");

		Label lblLoginMachineValue=new Label(sessionBeam.getPCName());
		lblLoginMachineValue.setLayoutX(bounds.getWidth()-145);
		lblLoginMachineValue.setLayoutY(40);

		Label lblLogOn=new Label("Logged on at :");
		lblLogOn.setLayoutX(bounds.getWidth()-228);
		lblLogOn.setLayoutY(55);
		lblLogOn.setId("cleintInfo");

		Label lblLogOnValue=new Label(sessionBeam.getLoginTime());
		lblLogOnValue.setLayoutX(bounds.getWidth()-145);
		lblLogOnValue.setLayoutY(55);

		Label lblBlackLine=new Label("");
		lblBlackLine.setLayoutX(10);
		lblBlackLine.setLayoutY(80);
		lblBlackLine.setId("BlackLine");

		lblBlackLine.setMinWidth(bounds.getWidth()-10);
		lblBlackLine.setMinHeight(35);

		btnLogout=new Button("Logout");
		btnLogout.setMinWidth(70);
		btnLogout.setMinHeight(28);
		btnLogout.setLayoutX(bounds.getWidth()-75);
		btnLogout.setLayoutY(84);
		btnLogout.setId("btnLogout");


		btnListOfModule.setMinWidth(180);
		btnListOfModule.setMinHeight(32);
		btnListOfModule.setLayoutX(bounds.getWidth()-180);
		btnListOfModule.setLayoutY(120);
		btnListOfModule.setId("btnModule");
		btnListOfModule.setAlignment(Pos.BASELINE_LEFT);

		int y=158;
		btnSetting.setMinWidth(180);
		btnSetting.setMinHeight(28);
		btnSetting.setLayoutX(bounds.getWidth()-180);
		btnSetting.setLayoutY(y);
		btnSetting.setId("btnModule");
		btnSetting.setToggleGroup(gpmodule);
		//btnSetting.setSelected(true);

		y+=32;		

		btnConstructionModule.setMinWidth(180);
		btnConstructionModule.setMinHeight(28);
		btnConstructionModule.setLayoutX(bounds.getWidth()-180);
		btnConstructionModule.setLayoutY(y);
		btnConstructionModule.setId("btnModule");
		btnConstructionModule.setToggleGroup(gpmodule);
		y+=32;
		
		Label lblListOfModule=new Label("");
		lblListOfModule.setLayoutX(10);
		lblListOfModule.setLayoutY(120);
		lblListOfModule.setId("BlackLine");

		lblListOfModule.setMinWidth(bounds.getWidth()-1100);
		lblListOfModule.setMinHeight(35);


		btnForm.setMinWidth(160);
		btnForm.setMinHeight(28);
		btnForm.setLayoutX(20);
		btnForm.setLayoutY(120);
		//btnForm.setId("btnModule");
		btnForm.setToggleGroup(gpchild);
		btnForm.setId("form");
		btnForm.setSelected(true);

		btnReport.setMinWidth(160);
		btnReport.setMinHeight(28);
		btnReport.setLayoutX(100);
		btnReport.setLayoutY(120);
		//btnForm.setId("btnModule");
		btnReport.setToggleGroup(gpchild);
		btnReport.setId("form");


		//Label lblListOfModuleItem=new Label("");
		paneChild.setLayoutX(10);
		paneChild.setLayoutY(160);
		paneChild.setId("ModuleItemBg");

		paneChild.setMinWidth(bounds.getWidth()-1100);
		paneChild.setMinHeight(530);



		Label lblcenterBg=new Label(sessionBeam.getAddress());
		lblcenterBg.setLayoutX(bounds.getWidth()-670);
		lblcenterBg.setLayoutY(630);
		lblcenterBg.setId("cleintInfo");

		Label lblDeveoperWeb=new Label("Web : "+sessionBeam.getWeb());
		lblDeveoperWeb.setLayoutX(bounds.getWidth()-670);
		lblDeveoperWeb.setLayoutY(645);
		lblDeveoperWeb.setId("cleintInfo");

		Label lblDeveoperEmail=new Label("E-mail : "+sessionBeam.getEmail());
		lblDeveoperEmail.setLayoutX(bounds.getWidth()-670);
		lblDeveoperEmail.setLayoutY(660);
		lblDeveoperEmail.setId("cleintInfo");
		
		pane.getChildren().addAll(lblCompanyIcon,lblClientName,lblClientContact,lblClientAddress,
				lblUserName,lblUserValue,lblDepartment,lblDepartmentValue,lblLoginMachine,
				lblLoginMachineValue,lblLogOn,lblLogOnValue,lblBlackLine,btnLogout,btnListOfModule,btnSetting,btnConstructionModule,
				lblListOfModule,btnForm,btnReport,paneChild,lblcenterBg,lblDeveoperWeb,lblDeveoperEmail
				);
		pane.setId("bg");
	}
}
