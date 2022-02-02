package com.ComShare;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import com.RootFrame.RootFrame;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Login extends Application {
	private Pane pane;
	private TextField txtUsername;
	private PasswordField txtPassword;
	private Button btnLogin;
	static SessionBean session=new SessionBean();
	Stage PrimaryStag;
	public Login(){
		pane =new Pane();;
		addCmp();
		btnActionEvent();
	}
	private void btnActionEvent(){
		btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				try {
					//dGroupData();
					btnLoginEvent();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	private void dGroupData() throws ClassNotFoundException, SQLException{
		String selectStmt = "select ItemCode,ItemId from tbItemInfo group by ItemCode having count(*)>1";
		ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
		
		ArrayList list=new ArrayList<>();
		
		while(rs.next()){
			list.add(rs.getString("ItemId"));
		}

		for(int a=0;a<list.size();a++){
			String dQuery="delete from tbItemInfo where ItemId='"+list.get(a)+"'";
			DBUtil.dbExecuteUpdate(dQuery);
		}
	}
	
	private void btnLoginEvent() throws ClassNotFoundException, SQLException, UnknownHostException {
		
		if(!txtUsername.getText().trim().toString().isEmpty()){
			if(!txtPassword.getText().trim().toString().isEmpty()){
				
				session.setPassword(txtPassword.getText().trim().toString());
				
				String selectStmt = "select * from TbLogin where userName='"+txtUsername.getText().trim().toString()+"' and password='"+session.getPassword()+"' and active='1'";
				ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
				while(rs.next()){
					OpenHomePage(rs.getString("user_id"),rs.getString("username"),rs.getString("Department"));
				}
			}
			else{
				new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide valid password.");
			}
		}
		else{
			new Notification(Pos.TOP_CENTER, "Warning graphic", "Warning!", "Provide valid username.");
		}
		
	}
	private void OpenHomePage(String userId,String userName,String Department) throws UnknownHostException {
		session.setUserId(userId);
		session.setUserName(userName);
		session.setDepartment(Department);
		session.setUserIp(Inet4Address.getLocalHost().getHostAddress());
		session.setPCName(InetAddress.getLocalHost().getHostName());
		session.setLoginTime(new SimpleDateFormat("dd-MM-yyyy hh:mm:s a").format(new Date()));
		DeveloperCompany();
		CompanyInfo();
		RootFrame RF=new RootFrame(session);
		PrimaryStag.setScene(RF.scene);
		try {
			RF.start(PrimaryStag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}
	private void DeveloperCompany(){
		try {
			String selectStmt = "select * from TbDeveloperInfo";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				session.setDeveloperCompanyName(rs.getString("CompanyName"));
				session.setEmail(rs.getString("Email"));
				session.setTele(rs.getString("TeleNo"));
				session.setWeb(rs.getString("Web"));
				session.setHelpLine(rs.getString("HelpLine"));
				session.setAddress(rs.getString("Address"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}
	private void CompanyInfo(){
		try {
			String selectStmt = "select * from TbCompanyInfo";
			ResultSet rs = DBUtil.dbExecuteQuery(selectStmt);
			while(rs.next()){
				session.setClientCompanyName(rs.getString("CompanyName"));
				session.setClientPhone(rs.getString("TeleNo"));
				session.setClientAddress(rs.getString("Address").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Notification(Pos.TOP_CENTER, "Error graphic", "Error!", e.getMessage());
		}
	}
	public void start(Stage primaryStage) {
		try {
			this.PrimaryStag=primaryStage;
			Scene scene = new Scene(pane,500,220);
			PrimaryStag.setScene(scene);
			PrimaryStag.setTitle("Login...");
			//scene.getStylesheets().add("StyleFile/application.css");
			scene.getStylesheets().add("StyleFile/style.css");
			PrimaryStag.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	private void addCmp(){

		Label lblSystemDeloped=new Label("System Developed:");
		lblSystemDeloped.setLayoutX(5);
		lblSystemDeloped.setLayoutY(38);


		Label lblCompanyName=new Label(" Cursor Software Ltd.");
		lblCompanyName.setLayoutX(122);
		lblCompanyName.setLayoutY(36);
		lblCompanyName.setId("Company");
		lblCompanyName.setTextFill(Color.web("#008080"));

		Label lblUserName=new Label("Username");
		lblUserName.setLayoutX(55);
		lblUserName.setLayoutY(63);

		txtUsername=new TextField();
		txtUsername.setLayoutX(122);
		txtUsername.setLayoutY(60);
		txtUsername.setMinWidth(160);
		txtUsername.setText("Admin");

		Label lblPassword=new Label("Password");
		lblPassword.setLayoutX(55);
		lblPassword.setLayoutY(90);

		txtPassword=new PasswordField();
		txtPassword.setLayoutX(122);
		txtPassword.setLayoutY(90);
		txtPassword.setMinWidth(163);
		txtPassword.setText("sa");

		btnLogin=new Button("Login");
		btnLogin.setLayoutX(122);
		btnLogin.setLayoutY(120);
		btnLogin.setMinWidth(80);
		btnLogin.setMinHeight(30);

		Label lblBorder=new Label("Phone:#+8801770004154\n             +8801812351155\nEmail:#info@cursorbd.com\nAddress:#82/A,Road # 2/1,\nBlock # B Sugandha R/A \nPanchlaish \nChittagong, Bangladesh");
		lblBorder.setLayoutX(320);
		lblBorder.setLayoutY(45);
		lblBorder.setMinWidth(175);
		lblBorder.setMinHeight(140);
		lblBorder.setId("DevBorder");
		pane.getChildren().addAll(lblSystemDeloped,lblCompanyName,lblUserName,txtUsername,lblPassword,txtPassword,btnLogin,lblBorder);
		final Control ob[] = {txtUsername,txtPassword,btnLogin};	
		new FocusMoveByEnter(ob);
	}
	public static void main(String[] args) throws SQLException {
		launch(args);
	}
}
