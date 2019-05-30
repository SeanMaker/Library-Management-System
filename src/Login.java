import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;

import java.security.interfaces.RSAKey;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class Login extends JFrame implements ActionListener{
    private JPanel jp=new JPanel();//create JPanel object
    private JLabel []jlArray={//create JLabel Array
    	   new JLabel("User IP"),new JLabel("Port"),new JLabel("User ID"),
    	   new JLabel("Password"),new JLabel("")
    };
    private JButton[] jbArray={//create JButton Array
    	new JButton("Student"),new JButton("Clear"),new JButton("Administrator")
    };
    private JTextField[] jtxtArray={ //create Text Array
    	   new JTextField("127.0.0.1"),new JTextField("3306"),new JTextField("")
    }; 
    private JPasswordField jpassword=new JPasswordField(""); //create PasswordField
    String sql;
    public Login(){
    	  jp.setLayout(null); //set JPanel Layout
    	  for(int i=0;i<4;i++){ // set Labels	
	          jlArray[i].setBounds(30,20+i*50,100,25);//set location and size for labels
	          jp.add(jlArray[i]);//add the labels into JPanel
          }
          for(int i=0;i<3;i++){//set location,size,ActionListenner for the buttons
	          jbArray[i].setBounds(10+i*130,230,120,25);
	          jp.add(jbArray[i]);
	          jbArray[i].addActionListener(this);      
          }
          for(int i=0;i<3;i++){//set location,size,ActionListenner for the Texts
	          jtxtArray[i].setBounds(100,20+50*i,180,25);
	          jp.add(jtxtArray[i]);
	          jtxtArray[i].addActionListener(this);
          }
          jpassword.setBounds(100,170,180,25);//set location,size for PasswordField
          jp.add(jpassword);//add PasswordField into JPanel
          jpassword.setEchoChar('*');//set EchoChar for PasswordField
          jpassword.addActionListener(this);//set ActionListenner for PasswordField
          jlArray[4].setBounds(10,280,300,25);
          jp.add(jlArray[4]); 
          this.add(jp);	
 	      Image image=new ImageIcon("images/icon3.png").getImage();//logo 
 	      this.setIconImage(image);
          //set the window location
          this.setTitle("Login");
          this.setResizable(false);
          this.setBounds(100,100,400,350);
          this.setVisible(true);
    }
    
 	PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;
    
    //ActionListener function
    public void actionPerformed(ActionEvent e)
    {//even source: Texts
    	String mgIP=jtxtArray[0].getText().trim();
	    String port=jtxtArray[1].getText().trim();
	    String mgno=jtxtArray[2].getText().trim();
        String message=mgIP+":"+port;
	    if(e.getSource()==jtxtArray[0]){
	         jtxtArray[1].requestFocus();//switch the focus to next Text
    	    }
    	    if(e.getSource()==jtxtArray[1]){
    		   jtxtArray[2].requestFocus();//switch the focus to next Text
    	    }
    	    if(e.getSource()==jtxtArray[2])	{	   
    		   jpassword.requestFocus();//switch the focus to password
    	    }
    	    else if(e.getSource()==jbArray[1]){//clear button
    	        
    		    jlArray[4].setText("");
    		    jtxtArray[2].setText("");
    		    jpassword.setText("");
    		    //set the focus
    		    jtxtArray[2].requestFocus();
    	    }
    	    else if(e.getSource()==jbArray[2]){//administrator button 
    	        //match the username & password
    	        if(!mgno.matches("[0-9][0-9][0-9][0-9]"))
    	        {//if username wrong
	    	    	JOptionPane.showMessageDialog(this,"Wrong User ID !","Information",
	    	    						JOptionPane.INFORMATION_MESSAGE);
	    	    	return;
	    	    }
	    	    if(jtxtArray[0].getText().trim().equals(""))
	    	    {//if ip is empty
	    	    	JOptionPane.showMessageDialog(this,"IP Cannot Be Empty!","Information",
	    	    	                                JOptionPane.INFORMATION_MESSAGE);
	    	    	return;
	    	    }
	    	    if(jtxtArray[1].getText().trim().equals(""))
	    	    {//if port number is empty
	    	     	    JOptionPane.showMessageDialog(this,"Port Number Cannot Be Empty!","Information",
	    	     	    						JOptionPane.INFORMATION_MESSAGE);
	    	    	    return;
		    	}
	    	    
	    	    
	    	    try {
  		    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
  				  ct=DriverManager.getConnection("jdbc:odbc:sql server");
  				  ps=ct.prepareStatement("select mgNo,password from manager where mgNo="+Integer.parseInt(mgno));
  				  rs=ps.executeQuery();
  				  
  				  int input_mgno=Integer.parseInt(mgno);
  				  String input_mgpassword=jpassword.getText().trim();
  				  
  				  while(rs.next()){
  				  int db_mgno=rs.getInt(1);
  				  String db_mgpassword=rs.getString(2);
  				  
  				  
  				  
  				  if (db_mgno==input_mgno && db_mgpassword.equalsIgnoreCase(input_mgpassword)){
  					jlArray[4].setText("Login Successfully!");
  						new Root(mgno);
			    		this.dispose();  	
  					  }
  				  if (db_mgno!=input_mgno || !db_mgpassword.equals(input_mgpassword)){
 					  
 					  JOptionPane.showMessageDialog(this,"Wrong User ID or Password!",
									"Information", JOptionPane.INFORMATION_MESSAGE);
 					  return;
 				  }
  				   				  
  				  
  				   }
  				  rs=ps.executeQuery();

  				  }
  		    

  		    catch(Exception e1){e1.printStackTrace();}
  			    finally
  			    {
  			    	try {
  			    		if(rs!=null)
  						{
  							rs.close();
  						}
  			    		if(ps!=null)
  						{
  							ps.close();
  						}
  						if(ct!=null)
  						{
  							ct.close();
  						}
  						
  					} catch(Exception e1){e1.printStackTrace();}
  			    }
  		    	
	    	    
		                    }
	    	else if(e.getSource()==jbArray[0]){//student button
	    	     if(!jtxtArray[2].getText().trim().matches("[0-9][0-9][0-9][0-9][0-9][0-9]")){
					//if student ID wrong
					JOptionPane.showMessageDialog(this,"Wrong User ID !",
									"Information", JOptionPane.INFORMATION_MESSAGE);
		    		return;
	    	     }
	    	     if(jtxtArray[0].getText().trim().equals("")){//IP is empty
			    	JOptionPane.showMessageDialog(this,"IP Cannot Be Empty!","Information",
			    						JOptionPane.INFORMATION_MESSAGE);
			    	return;
	    	     }
	    	     if(jtxtArray[1].getText().trim().equals("")){//Port is empty
		    	    	JOptionPane.showMessageDialog(this,"Port Number Cannot Be Empty!",
		    	    				"Information",JOptionPane.INFORMATION_MESSAGE);
		    	    	return;
	    	     }
	    	     //search ID from STUDENT table
	    	       
	    		    try {
	    		    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	    				  ct=DriverManager.getConnection("jdbc:odbc:sql server");
	    				  ps=ct.prepareStatement("select StuNO,Password from STUDENT where StuNO="+Integer.parseInt(jtxtArray[2].getText().trim()));
	    				  rs=ps.executeQuery();
	    				  
	    				  int input_no=Integer.parseInt(jtxtArray[2].getText().trim());
	    				  String input_password=jpassword.getText().trim();
	    				  
	    				  while(rs.next()){
	    				  int db_no=rs.getInt(1);
	    				  String db_password=rs.getString(2);
	    				  
	    				  if (input_no!=db_no || !input_password.equals(db_password)){
	      					  
	      					  JOptionPane.showMessageDialog(this,"Wrong User ID or Password!",
	    									"Information", JOptionPane.INFORMATION_MESSAGE);
	      					  return;
	      				  }
	    				  
	    				  else if (db_no==input_no && db_password.equals(input_password)){
	    					  		jlArray[4].setText("Login Successfully!");
	    					  		new StudentSystem();
	    					  		this.dispose();  	
	    					  }

	    				   				  
	    				  
	    				   }
	    				  rs=ps.executeQuery();

	    				  }
	    		    

	    		    catch(Exception e1){e1.printStackTrace();}
	    			    finally
	    			    {
	    			    	try {
	    			    		if(rs!=null)
	    						{
	    							rs.close();
	    						}
	    			    		if(ps!=null)
	    						{
	    							ps.close();
	    						}
	    						if(ct!=null)
	    						{
	    							ct.close();
	    						}
	    						
	    					} catch(Exception e1){e1.printStackTrace();}
	    			    }
	    		    	



	             
	      }
 }
 public static void main(String[]args)
 {
 	new Login();
 }
}