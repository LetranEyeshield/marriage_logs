package classes;

public class Entity {
	
	String date_received,
		   husband_name,
		   wife_name,
		   reg_no,
		   marriage_license,
		   release_date,
		   control_no,
		   or_no;
	
	
	public Entity() {
		
	}
	
	
	//setters
	
	public void setHusband_name(String husband_name) {
		this.husband_name = husband_name;
	}
	
	public void setWife_name(String wife_name) {
		this.wife_name = wife_name;
	}
	
	public void setControl_no(String control_no) {
		this.control_no = control_no;
	}
	
	public void setDate_received(String date_received) {
		this.date_received = date_received;
	}
	
	public void setMarriage_license(String marriage_license) {
		this.marriage_license = marriage_license;
	}
	
	public void setOr_no(String or_no) {
		this.or_no = or_no;
	}
	
	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}
	
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}
	
	
	//getters
	
	public String getWife_name() {
		return wife_name;
	}
	
	public String getHusband_name() {
		return husband_name;
	}
	
	public String getControl_no() {
		return control_no;
	}
	
	public String getDate_received() {
		return date_received;
	}
	
	public String getMarriage_license() {
		return marriage_license;
	}
	
	public String getOr_no() {
		return or_no;
	}
	
	public String getReg_no() {
		return reg_no;
	}
	
	public String getRelease_date() {
		return release_date;
	}

}
