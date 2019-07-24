package classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class Login extends JFrame implements ActionListener{
	
	JLabel label;
	
	JTextField user;
	JPasswordField pass;
	
	JButton login, editAccount, exit;
	
	String driver = "org.gjt.mm.mysql.Driver";
	String url = "jdbc:mysql://localhost:3306/marriage_logs";
	String username = "root";
	String password = "";
	Connection connection;
	Statement stmt;
	ResultSet rs;
	PreparedStatement ps;
	
	public Login() {
		super("MCR MARRIAGE LOGS");
		setLayout(null);
		
		setLabels();
		setStuffs();
		setBackground();
		
	}
	
	
	public void prepareLabels(String str,int xpos,int ypos,int width,int height,Font font,Color backColor,Color fontColor) {
		
		label = new JLabel(str);
		label.setBounds(xpos,ypos,width,height);
		label.setFont(font);
		label.setOpaque(true);
		label.setBackground(backColor);
		label.setForeground(fontColor);
		add(label);
	}
	
	public void setLabels() {
		
		prepareLabels("                                                            "
				+ "USER LOGIN",0,0,600,40,new Font("Times New Roman",Font.BOLD,16),Color.BLUE,Color.WHITE);
		prepareLabels("  Username: ",50,100,100,20,new Font("Times New Roman",Font.PLAIN,18),Color.CYAN,Color.BLACK);
		prepareLabels("  Password: ",50,150,100,20,new Font("Times New Roman",Font.PLAIN,18),Color.CYAN,Color.BLACK);
	}
	
	public void setBackground(){

		JLabel background = new JLabel(new ImageIcon("logo.png"));
		 background.setBounds(90,0,400,400);
		// background.setBackground(new Color(0,0,255));
		// background.setBackground(Color.LIGHT_GRAY);
		 background.setOpaque(true);
		 add(background);
	}
	
	public void setStuffs() {
		
		user = new JTextField();
		user.setBounds(160,100,170,20);
		add(user);
		
		pass = new JPasswordField();
		pass.setBounds(160,150,170,20);
		pass.addActionListener(this);
		add(pass);
		
		login = new JButton("LOG IN");
		login.setFont(new Font("Tahoma",Font.BOLD,14));
		login.setBounds(220,200,100,30);
		login.setBackground(Color.blue);
		login.setForeground(Color.WHITE);
		login.addActionListener(this);
		add(login);
		
		editAccount = new JButton("EDIT USER ACCOUNT");
		editAccount.setFont(new Font("Tahoma",Font.BOLD,13));
		editAccount.setBounds(50,280,160,30);
		editAccount.setBackground(Color.CYAN);
		editAccount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				UserGui userGui = new UserGui();
				userGui.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				userGui.setSize(600,400);
				userGui.setVisible(true);
				userGui.setLocation(100,50);
				userGui.setResizable(false);
			}
			
		});
		add(editAccount);
		
		exit = new JButton("EXIT");
		exit.setFont(new Font("Tahoma",Font.BOLD,13));
		exit.setBounds(250,280,80,30);
		exit.setBackground(Color.CYAN);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				CloseMe();
			}
			
		});
		add(exit);
	}
	
	public void CloseMe() {
		this.dispose();
	}
	
	public void checkInputs() {
		
		if(user.getText().toString().equals("") && pass.getText().toString().equals("")) {
			
			JOptionPane.showMessageDialog(null, "Fill All Fields!","MCR MARRIAGE LOGS",JOptionPane.ERROR_MESSAGE);
			
		}else if(user.getText().toString().equals("")) {
			
			JOptionPane.showMessageDialog(null, "Please Enter Username!","MCR MARRIAGE LOGS",JOptionPane.ERROR_MESSAGE);
			
		}else if(pass.getText().toString().equals("")) {
			
			JOptionPane.showMessageDialog(null, "Please Enter Password!","MCR MARRIAGE LOGS",JOptionPane.ERROR_MESSAGE);
			
		}else {
			
			Validate();
		}
	}
	
	public void Validate(){
		  
		try{

			//access natin ang java mysql driver library na ating inimport para magamit
			Class.forName(driver);

			//create na natin ung connection para sating database 
			connection = (Connection) DriverManager.getConnection(url,username,password);

			//set the sql query

			String check = "SELECT * FROM user WHERE username="+"\""+user.getText().toString()+"\""
					+ "AND password="+"\""+pass.getText().toString()+"\"";

			stmt = (Statement) connection.createStatement();

			rs = stmt.executeQuery(check);
	 
		
			if(rs.first()){
				
				JOptionPane.showMessageDialog(null, "Access Granted!","MCR MARRIAGE LOGS",JOptionPane.INFORMATION_MESSAGE);

				Gui gui = new Gui();
				gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				gui.setSize(1000,600);
				gui.setVisible(true);
				gui.setLocation(100,50);
				gui.setResizable(false);
				
				CloseMe();

			}else{ 

				JOptionPane.showMessageDialog(null, "Invalid Username/Password Combination","MCR MARRIAGE LOGS",JOptionPane.ERROR_MESSAGE);
			}

			connection.close();

		}catch (Exception moriel){

			JOptionPane.showMessageDialog(null,"ERROR!!!\n"+"Error! Please Try Again! \n\n"+moriel.getMessage(),"MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE); 

		} 




	} 

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		checkInputs();
	}

}
