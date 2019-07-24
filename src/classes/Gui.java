package classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import net.proteanit.sql.DbUtils;

public class Gui extends JFrame implements ActionListener, KeyListener, MouseListener{
	
	JTextField date_received,
			   husband_name,
			   wife_name,
			   reg_no,
			   marriage_license,
			   release_date,
			   control_no,
			   or_no;
	
	JTextField search_husband, search_wife;
	
	JTable table;
	DefaultTableModel model;
	
	String driver = "org.gjt.mm.mysql.Driver";
	String url = "jdbc:mysql://localhost:3306/marriage_logs";
	String username = "root";
	String password = "";
	Connection connection;
	Statement stmt;
	ResultSet rs;
	PreparedStatement ps;
	
	String getID;
	
	JButton add, edit, clear, exit;
	
	Entity ent = new Entity();
			   
	
	public Gui() {
		super("MARRIAGE LOGS");
		setLayout(null);
		
		setTitle();
		setLabels();
		setFields();
		tableModel();
		setButtons();
		setPanelLines();
		
	}
	
	public void setTitle(){
		
		JLabel title = new JLabel("                                  " +
				"                              "
				+ "     MARRIAGE LOGS");
		title.setBounds(0,0,1000,25);
		title.setOpaque(true);
		title.setBackground(Color.blue);
		title.setFont(new Font("OLD ENGLISH MT",Font.BOLD,20));
		title.setForeground(Color.WHITE);
		add(title);
	}
	
	public void prepareLabels(String str, int xpos, int ypos, int width, int height,Color color,Color fontColor){
		
		JLabel labels = new JLabel(str);
		labels.setBounds(xpos,ypos,width,height);
		labels.setOpaque(true);
		labels.setBackground(color);
		labels.setForeground(fontColor);
		labels.setFont(new Font("Tahoma",Font.PLAIN,16));
		add(labels);
	}


	public void setLabels(){
		
		prepareLabels(" Date Received:",50,70,160,20,Color.CYAN,Color.BLACK);
		prepareLabels(" "
				+ "                      NAME OF APPLICANTS:",50,110,370,20,Color.BLUE,Color.WHITE);
		prepareLabels(" Husband Name:",50,150,160,20,Color.CYAN,Color.BLACK);
		prepareLabels(" Wife Name:",50,190,160,20,Color.CYAN,Color.BLACK);
		prepareLabels(" Reg No:",50,230,160,20,Color.CYAN,Color.BLACK);
		prepareLabels(" Marriage License No:",50,270,160,20,Color.CYAN,Color.BLACK);
		prepareLabels(" Date Of Release:",50,310,160,20,Color.CYAN,Color.BLACK);
		prepareLabels(" Control No:",50,350,160,20,Color.CYAN,Color.BLACK);
		prepareLabels(" OR No:",50,390,160,20,Color.CYAN,Color.BLACK);
		
		prepareLabels(" Search by Husband Name:",490,50,200,20,Color.CYAN,Color.BLACK);
		prepareLabels(" Search by Wife Name:",490,80,200,20,Color.CYAN,Color.BLACK);
	}
	
	
	public void setFields() {
		
		date_received = new JTextField();
		date_received.setBounds(220,70,200,20);
		add(date_received);
		
		husband_name = new JTextField();
		husband_name.setBounds(220,150,200,20);
		add(husband_name);
		
		wife_name = new JTextField();
		wife_name.setBounds(220,190,200,20);
		add(wife_name);
		
		reg_no = new JTextField();
		reg_no.setBounds(220,230,200,20);
		add(reg_no);
		
		marriage_license = new JTextField();
		marriage_license.setBounds(220,270,200,20);
		add(marriage_license);
		
		release_date = new JTextField();
		release_date.setBounds(220,310,200,20);
		add(release_date);
		
		control_no = new JTextField();
		control_no.setBounds(220,350,200,20);
		add(control_no);
		
		or_no = new JTextField();
		or_no.setBounds(220,390,200,20);
		add(or_no);
		
		search_husband = new JTextField();
		search_husband.setBounds(700,50,200,20);
		search_husband.addKeyListener(this);
		add(search_husband);
		
		search_wife = new JTextField();
		search_wife.setBounds(700,80,200,20);
		search_wife.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				wifeSearch();
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				wifeSearch();
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		add(search_wife);
	}

	public void setPanelLines() {
		
		JLabel panelLine1 = new JLabel();
		panelLine1.setBounds(20,40,430,500);
		panelLine1.setOpaque(true);
		panelLine1.setBorder(BorderFactory.createLineBorder(Color.RED));
		add(panelLine1);
		
		JLabel panelLine2 = new JLabel();
		panelLine2.setBounds(470,40,510,500);
		panelLine2.setOpaque(true);
		panelLine2.setBorder(BorderFactory.createLineBorder(Color.RED));
		add(panelLine2);
		
	}
	
	public void setFieldValues() {
		
		ent.setControl_no(control_no.getText().toString());
		ent.setDate_received(date_received.getText().toString());
		ent.setHusband_name(husband_name.getText().toString());
		ent.setWife_name(wife_name.getText().toString());
		ent.setMarriage_license(marriage_license.getText().toString());
		ent.setOr_no(or_no.getText().toString());
		ent.setReg_no(reg_no.getText().toString());
		ent.setRelease_date(release_date.getText().toString());
	}
	
	//METHOD PARA SA EXIT BUTTON
	public void CloseMe(){
		this.dispose();
	}
	
	public void tableModel(){
		
		String query;
	  
		 try  {
	   	
			 connection = (Connection) DriverManager.getConnection( url, username, password );
			 stmt = (Statement) connection.createStatement();

			 query = "SELECT * FROM marriage_logs";
			 rs = stmt.executeQuery(query);
			 table = new JTable(model);
			 JScrollPane scrollPane = new JScrollPane(table);
			 table.setPreferredScrollableViewportSize(new Dimension(500, 70));
			 table.setFillsViewportHeight(true);
			 table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			 table.setBackground(Color.LIGHT_GRAY);
			 scrollPane.setBounds(490,130,470,390);
			 scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			 scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			 add(scrollPane);
			 table.setModel(DbUtils.resultSetToTableModel(rs));
			 table.addMouseListener(this);
	      	
	       
	  
		 }catch(Exception moriel){
			 
			 JOptionPane.showMessageDialog(null,moriel.getMessage(),"MCR DATABASE SYSTEM",JOptionPane.ERROR_MESSAGE);
			 
		 }
	  
	}
	
	public void setButtons() {
		
		add = new JButton("ADD");
		add.setBounds(80,440,120,30);
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				checkRecordExist();
			}
			
		});
		add(add);
		
		edit = new JButton("EDIT");
		edit.setBounds(220,440,120,30);
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setFieldValues();
				EditSQL();
			}
			
		});
		add(edit);
		
		clear = new JButton("CLEAR FIELDS");
		clear.setBounds(80,490,120,30);
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				clearFields();
			}
			
		});
		add(clear);
		
		exit = new JButton("EXIT");
		exit.setBounds(220,490,120,30);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				CloseMe();
			}
			
		});
		add(exit);
	}
	
	public void clearFields() {
		
		date_received.setText("");
		husband_name.setText("");
		wife_name.setText("");
		reg_no.setText("");
		marriage_license.setText("");
		release_date.setText("");
		control_no.setText("");
		or_no.setText("");
	}
	
	public void checkRecordExist(){
		  
		try{

			//access natin ang java mysql driver library na ating inimport para magamit
			Class.forName(driver);

			//set natin ung user at pass ng sql appz, server, host or kung anong tawag jan natin, mine is xammp 

			String user = "root";
			String pass = "";

			//create na natin ung connection para sating database 
			connection = (Connection) DriverManager.getConnection(url,user,pass);

			//set the sql query

			String check = "SELECT reg_no, control_no FROM marriage_logs WHERE reg_no="+"\""+reg_no.getText().toString()+"\""
					+ "AND control_no="+"\""+control_no.getText().toString()+"\"";

			stmt = (Statement) connection.createStatement();

			rs = stmt.executeQuery(check);
	 
		
			if(rs.first()){

				JOptionPane.showMessageDialog(null,"Record Already Exist!"+"\n"+"Reg No, Control No the same","MCR MARRIAGE LOGS",JOptionPane.ERROR_MESSAGE);


			}else{

				setFieldValues();
				InsertSQL(); 

			}

			connection.close();

		}catch (Exception moriel){

			JOptionPane.showMessageDialog(null,"ERROR!!!\n"+"Error! Please Try Again! \n\n"+moriel.getMessage(),"MCR MARRIAGE LOGS",JOptionPane.ERROR_MESSAGE); 

		} 




	} 
	
	public void InsertSQL(){
		
		try{

			Class.forName(driver);

			//create na natin ung connection para sating database 
			connection = (Connection) DriverManager.getConnection(url,username,password);

			//set the sql query
			String query = "INSERT INTO marriage_logs ("
							+ "date_received,husband_name,wife_name,reg_no,marriage_license,date_of_release,control_no,or_no)"
							+"values(?,?,?,?,?,?,?,?)";


			stmt = (Statement) connection.createStatement();


			ps = (PreparedStatement) connection.prepareStatement(query);

			ps.setString(1,ent.getDate_received());
			ps.setString(2,ent.getHusband_name());
			ps.setString(3,ent.getWife_name());
			ps.setString(4,ent.getReg_no());
			ps.setString(5,ent.getMarriage_license());
			ps.setString(6,ent.getRelease_date());
			ps.setString(7,ent.getControl_no());
			ps.setString(8,ent.getOr_no());

			ps.execute();

			JOptionPane.showMessageDialog(null,"Record Saved!","MCR MARRIAGE LOGS",JOptionPane.INFORMATION_MESSAGE);


			connection.close();

		}catch (Exception moriel){

			JOptionPane.showMessageDialog(null,"ERROR!!!\n"+"PLEASE TRY AGAIN! \n\n"+moriel.getMessage(),"MCR MARRAIGE LOGS",JOptionPane.ERROR_MESSAGE); 
		}
		
	} 
	
	public void wifeSearch(){

		String query;

		try  {

			connection = (Connection) DriverManager.getConnection( url, username, password );
			stmt = (Statement) connection.createStatement();

			//query = "SELECT * FROM marriage_logs WHERE husband_name LIKE "+"\""+search_husband.getText().toString()+"%\" OR wife_name LIKE "+"\""+search_wife.getText().toString()+"%\"";
			
			query = "SELECT * FROM marriage_logs WHERE wife_name LIKE "+"\""+search_wife.getText().toString()+"%\"";
			
			
			rs = stmt.executeQuery(query);
			table.setModel(DbUtils.resultSetToTableModel(rs));

		}catch(Exception moriel){

			JOptionPane.showMessageDialog(null,moriel.getMessage(),"MCR MARRIAGE LOGS",JOptionPane.ERROR_MESSAGE);

		}

		
	}
	
	public void husbandSearch(){

		String query;

		try  {

			connection = (Connection) DriverManager.getConnection( url, username, password );
			stmt = (Statement) connection.createStatement();

			//query = "SELECT * FROM marriage_logs WHERE husband_name LIKE "+"\""+search_husband.getText().toString()+"%\" OR wife_name LIKE "+"\""+search_wife.getText().toString()+"%\"";
			
			query = "SELECT * FROM marriage_logs WHERE husband_name LIKE "+"\""+search_husband.getText().toString()+"%\"";
			
			
			rs = stmt.executeQuery(query);
			table.setModel(DbUtils.resultSetToTableModel(rs));

		}catch(Exception moriel){

			JOptionPane.showMessageDialog(null,moriel.getMessage(),"MCR MARRIAGE LOGS",JOptionPane.ERROR_MESSAGE);

		}

		
	}
	
	public void tableClickSearch(){
		  
		
		String query = "SELECT * FROM marriage_logs WHERE id="+"\""+table.getValueAt(table.getSelectedRow(), 0)+"\"";

		
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
				date_received.setText(rs.getString("date_received"));
				husband_name.setText(rs.getString("husband_name"));
				wife_name.setText(rs.getString("wife_name"));
				reg_no.setText(rs.getString("reg_no"));
				marriage_license.setText(rs.getString("marriage_license"));
				release_date.setText(rs.getString("date_of_release"));
				control_no.setText(rs.getString("control_no"));
				or_no.setText(rs.getString("or_no"));
				
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
				query = "UPDATE marriage_logs SET date_received=?,husband_name=?,wife_name=?,reg_no=?,"
						+ "marriage_license=?,date_of_release=?,control_no=?,or_no=?"
						+ "WHERE id = "+"\""+getID+"\"";

				
				ps = (PreparedStatement) connection.prepareStatement(query);
				
				ps.setString(1,ent.getDate_received());
				ps.setString(2,ent.getHusband_name());
				ps.setString(3,ent.getWife_name());
				ps.setString(4,ent.getReg_no());
				ps.setString(5,ent.getMarriage_license());
				ps.setString(6,ent.getRelease_date());
				ps.setString(7,ent.getControl_no());
				ps.setString(8,ent.getOr_no());
				
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


	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		husbandSearch();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		husbandSearch();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		tableClickSearch();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
