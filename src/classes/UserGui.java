package classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import net.proteanit.sql.DbUtils;

public class UserGui extends JFrame implements ActionListener{
	
	JTextField user;
	JPasswordField pass;
	
	String getPass,confirmPass;
	
	String driver = "org.gjt.mm.mysql.Driver";
	String url = "jdbc:mysql://localhost:3306/marriage_logs";
	String username = "root";
	String password = "";
	Connection connection;
	Statement stmt;
	ResultSet rs;
	PreparedStatement ps;
	
	String getID;
	
	JButton edit, exit;

	public UserGui() {
		super("MCR MARRIAGE LOGS");
		setLayout(null);
		
		setLabels();
		setStuffs();
		Search();
	}
	
	public void prepareLabels(String str,int xpos,int ypos,int width,int height,Font font,Color color,Color fontColor) {
		
		JLabel label = new JLabel(str);
		label.setBounds(xpos,ypos,width,height);
		label.setOpaque(true);
		label.setBackground(color);
		label.setFont(font);
		label.setForeground(fontColor);
		add(label);
		
	}
	
	public void setLabels() {
		
		prepareLabels("                              "
				+ "                        EDIT USER ACCOUNT",0,0,600,30,new Font("Times New Roman",Font.BOLD,15),Color.BLUE,Color.WHITE);
		prepareLabels(" Username: ",40,80,90,20,new Font("Times New Roman",Font.PLAIN,18),Color.CYAN,Color.BLACK);
		prepareLabels(" Password: ",40,120,90,20,new Font("Times New Roman",Font.PLAIN,18),Color.CYAN,Color.BLACK);
	}
	
	public void setStuffs() {
		
		user = new JTextField();
		user.setBounds(140,80,170,20);
		add(user);
		
		pass = new JPasswordField();
		pass.setBounds(140,120,170,20);
		add(pass);
		
		edit = new JButton("EDIT");
		edit.setBounds(60,200,100,35);
		edit.addActionListener(this);
		add(edit);
		
		exit = new JButton("EXIT");
		exit.setBounds(190,200,100,35);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				closeMe();
			}
			
		});
		add(exit);
	}
	
public void Search(){
		  
		
		String query = "SELECT * FROM user";

		
		try{

			//access natin ang java mysql driver library na ating inimport para magamit
			Class.forName(driver);

			//create na natin ung connection para sating database 
			connection = (Connection) DriverManager.getConnection(url,username,password);

			stmt = (Statement) connection.createStatement();

			rs = stmt.executeQuery(query);

			int counter = 0;

			while(rs.next()){

				getID = rs.getString("id");
				user.setText(rs.getString("username"));
				pass.setText(rs.getString("password"));
				
				counter++;
			}
				 

			if(counter<1){

				JOptionPane.showMessageDialog(null,"RECORD NOT FOUND!","MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE);

			}

			connection.close();

		}catch (Exception moriel){

			JOptionPane.showMessageDialog(null,"ERROR!!!\n"+"PLEASE TRY AGAIN! \n\n"+moriel.getMessage(),"MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE);


		} 

	} 

public void confirmPassword(){
	  
	
	String query = "SELECT * FROM user WHERE id = 1";

	
	try{

		//access natin ang java mysql driver library na ating inimport para magamit
		Class.forName(driver);

		//create na natin ung connection para sating database 
		connection = (Connection) DriverManager.getConnection(url,username,password);

		stmt = (Statement) connection.createStatement();

		rs = stmt.executeQuery(query);

		int counter = 0;

		while(rs.next()){

			getID = rs.getString("id");
			getPass=rs.getString("password");
			
			counter++;
		}
			 

		if(counter<1){

			JOptionPane.showMessageDialog(null,"RECORD NOT FOUND!","MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE);

		}

		connection.close();

	}catch (Exception moriel){

		JOptionPane.showMessageDialog(null,"ERROR!!!\n"+"PLEASE TRY AGAIN! \n\n"+moriel.getMessage(),"MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE);


	} 

} 
	
	public void EditSQL(){

		String query;

		int ans = JOptionPane.showConfirmDialog(null,"Save Changes?");
		
		
		if(ans == JOptionPane.YES_OPTION){
			
			try{

				//access natin ang java mysql driver library na ating inimport para magamit
				Class.forName(driver);

				//create na natin ung connection para sating database 
				connection = (Connection) DriverManager.getConnection(url,username,password);

				//set the sql query
				query = "UPDATE user SET username=?,password=?"
						+ "WHERE id = "+"\""+getID+"\"";

				
				ps = (PreparedStatement) connection.prepareStatement(query);
				
				ps.setString(1,user.getText().toString());
				ps.setString(2,pass.getText().toString());
				
				ps.execute();

				JOptionPane.showMessageDialog(null,"Record Saved!","MCR MARRIAGE LOGS",JOptionPane.INFORMATION_MESSAGE); 

				connection.close();

			}catch (Exception moriel){

				JOptionPane.showMessageDialog(null,"ERROR!!!\n"+"PLEASE TRY AGAIN! \n\n"+moriel.getMessage(),"MCR MARRIAGE LOGS",JOptionPane.ERROR_MESSAGE); 

			} 
			
			
		}else if(ans==JOptionPane.NO_OPTION){
			
			
		}else if(ans ==JOptionPane.CANCEL_OPTION){
			
		}else if(ans ==JOptionPane.CLOSED_OPTION){
			
		}
		  
		
	} 

	
	public void closeMe() {
		this.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		confirmPass = JOptionPane.showInputDialog(null, "Enter Password To Make Changes","MCR MARRIAGE LOGS",JOptionPane.QUESTION_MESSAGE);
		
		confirmPassword();
		
		if(confirmPass.equals(getPass)) {
			EditSQL();
		}else {
			JOptionPane.showMessageDialog(null, "Password Incorrect!","MCR MARRIAGE LOGS",JOptionPane.ERROR_MESSAGE);
		}
	}

}
