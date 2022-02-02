package com.ComShare;

import java.util.StringTokenizer;

public class SessionBean {
	String UserName="",UserId="",UserIp="",PCName="",Department="",LoginTime="";
	String Password="";
	String ClientCompanyName="",ClientAddress="",ClientPhone="";
	String DeveloperCompanyName="",Tele="",Email="",Web="",HelpLine="",Address="",SystemName="";
	
	
	private String VoterName;
	private String VoterId;
	private String VoterSlipNo;
	private String VoterFatherName;
	private String VoterMotherName;
	private String VoterDistrict;
	private String VoterSeatNo;
	public void setUserName(String userName) {
		this.UserName = userName;
	}
	public String getUserName() {

		return UserName;
	}

	public void setPassword(String passWord) {
		this.Password = passWord;
	}
	
	public String getPassword() {
		int i=0;
		String EncrypString="";
		while(i<Password.length()){
			char ch=Password.charAt(i);
			int ascii = (int) ch;
			ascii=ascii-2;
			ascii=ascii+5;
			EncrypString=EncrypString+ascii+"$*S";
			i++;
		}
		return EncrypString;
	}

	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		Department = department;
	}
	
	
	public String getLoginTime() {
		return LoginTime;
	}
	public void setLoginTime(String loginTime) {
		LoginTime = loginTime;
	}
	public String getDeveloperCompanyName() {
		return DeveloperCompanyName;
	}
	public void setDeveloperCompanyName(String developerCompanyName) {
		DeveloperCompanyName = developerCompanyName;
	}
	public String getTele() {
		return Tele;
	}
	public void setTele(String tele) {
		Tele = tele;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
	public String getWeb() {
		return Web;
	}
	public void setWeb(String web) {
		Web = web;
	}
	public String getHelpLine() {
		return HelpLine;
	}
	public void setHelpLine(String helpLine) {
		HelpLine = helpLine;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}

	public String getSystemName() {
		return SystemName;
	}
	public void setSystemName(String systemName) {
		SystemName = systemName;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getUserIp() {
		return UserIp;
	}
	public void setUserIp(String userIp) {
		UserIp = userIp;
	}
	public String getPCName() {
		return PCName;
	}
	public void setPCName(String pCName) {
		PCName = pCName;
	}
	public String getClientCompanyName() {
		return ClientCompanyName;
	}
	public void setClientCompanyName(String clientCompanyName) {
		ClientCompanyName = clientCompanyName;
	}
	public String getClientAddress() {
		return ClientAddress;
	}
	public void setClientAddress(String clientAddress) {
		ClientAddress = clientAddress;
	}
	public String getClientPhone() {
		return ClientPhone;
	}
	public void setClientPhone(String clientPhone) {
		ClientPhone = clientPhone;
	}
	public String getVoterName() {
		return VoterName;
	}
	public void setVoterName(String voterName) {
		VoterName = voterName;
	}
	public String getVoterId() {
		return VoterId;
	}
	public void setVoterId(String voterId) {
		VoterId = voterId;
	}
	public String getVoterSlipNo() {
		return VoterSlipNo;
	}
	public void setVoterSlipNo(String voterSlipNo) {
		VoterSlipNo = voterSlipNo;
	}
	public String getVoterFatherName() {
		return VoterFatherName;
	}
	public void setVoterFatherName(String voterFatherName) {
		VoterFatherName = voterFatherName;
	}
	public String getVoterMotherName() {
		return VoterMotherName;
	}
	public void setVoterMotherName(String voterMotherName) {
		VoterMotherName = voterMotherName;
	}
	public String getVoterDistrict() {
		return VoterDistrict;
	}
	public void setVoterDistrict(String voterDistrict) {
		VoterDistrict = voterDistrict;
	}
	public String getVoterSeatNo() {
		return VoterSeatNo;
	}
	public void setVoterSeatNo(String voterSeatNo) {
		VoterSeatNo = voterSeatNo;
	}
	
	
	
}
