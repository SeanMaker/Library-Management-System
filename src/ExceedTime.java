import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class ExceedTime extends JPanel implements ActionListener
{
	
	private JLabel jl=new JLabel("Please enter Student ID:");
	private JTextField jtf=new JTextField();
	private JLabel jl1=new JLabel("Please enter amount of money:");
	private JTextField jtf1=new JTextField();
	//创建按钮
	private JButton jb=new JButton("Pay");
	private JButton jb1=new JButton("Search Fine");
	public ExceedTime()
	{
		this.setLayout(null);
		this.add(jl);
		this.add(jtf);
		this.add(jl1);
		this.add(jtf1);
		
		this.add(jb);
		this.add(jb1);
		
		jl.setBounds(50,30,200,20);
		jtf.setBounds(250,30,120,20);
		jl1.setBounds(50,70,200,20);
		jtf1.setBounds(250,70,120,20);
		jb.setBounds(190,110,110,25);
		
		jb.addActionListener(this);
		jb1.addActionListener(this);
		jb1.setBounds(50,110,110,25);
		
        this.setBounds(300,300,600,650);
        this.setVisible(true);
	}
	
   	public void actionPerformed(ActionEvent e)
	{
   		PreparedStatement ps=null;
   	    Connection ct=null;
   	    ResultSet rs=null;
   		
		int day=0;

		String sno=(String)jtf.getText().trim();
		if(sno.equals("")){
			JOptionPane.showMessageDialog(this,"Student ID cannot be empty!",
			                "Information",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if(!sno.matches("[0-9][0-9][0-9][0-9][0-9][0-9]")){
			JOptionPane.showMessageDialog(this,"Wrong Student ID, please enter again!",
			                   "Information",JOptionPane.INFORMATION_MESSAGE);
			return;			
		}
		
		
//		String sql="select DelayTime from EXCEEDTIME where StuNO="
//		           +Integer.parseInt(sno);
		try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("select DelayTime from EXCEEDTIME where StuNO="
			           +Integer.parseInt(sno));
			  rs=ps.executeQuery();
			  while(rs.next()){
				  int delaytime=rs.getInt(1);
				  if(delaytime<=0){
					  JOptionPane.showMessageDialog(this,"The book you borrowed is not overdue, and doesn't need to be paid.",
	                             "Information",JOptionPane.INFORMATION_MESSAGE);
					  	return;
				  }
				  
			  }
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
		

		
		if(e.getSource()==jb1){//search fine
			
			try {
		    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				  ct=DriverManager.getConnection("jdbc:odbc:sql server");
				  ps=ct.prepareStatement("select DelayTime from EXCEEDTIME where StuNO="
				           +Integer.parseInt(sno));
				  rs=ps.executeQuery();
				  while(rs.next()){
					  day=rs.getInt(1);
				  }
				
    		if(day>0){
    			JOptionPane.showMessageDialog(this,"You need to pay "+day*0.1+"$.",
		                                 "Information",JOptionPane.INFORMATION_MESSAGE);
		      return;
    		}
    		else{
    			JOptionPane.showMessageDialog(this,"The book you borrowed was not overdue, and you don't need to pay any fine.",
			                               "Information",JOptionPane.INFORMATION_MESSAGE);
			return;
    		}
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
			
		else if(e.getSource()==jb){//pay fine
			
			if(jtf1.getText().trim().equals("")){
				JOptionPane.showMessageDialog(this,"Please enter the amount of money you want to pay.",
										"Information",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			int k=JOptionPane.showConfirmDialog(this,"Pay it now?",
							"Info",JOptionPane.YES_NO_OPTION);
			
			if(k==JOptionPane.NO_OPTION){//select "NO"
				JOptionPane.showMessageDialog(this,"Sorry, make payment failed!",
									"Info",JOptionPane.INFORMATION_MESSAGE);
			      return;					
			}
			
			if(k==JOptionPane.YES_OPTION){//select "Yes"
				
				double amount=Double.parseDouble(jtf1.getText().trim());	
				
				try {
			    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					  ct=DriverManager.getConnection("jdbc:odbc:sql server");
					  ps=ct.prepareStatement("select DelayTime from EXCEEDTIME where StuNO="
					           +Integer.parseInt(sno));
					  rs=ps.executeQuery();
					  while(rs.next()){
						  day=rs.getInt(1);
					  }
				
				
				if(amount<(day*0.1)){
		   		
					try {
						  ps=ct.prepareStatement("update exceedtime set delaytime=delaytime-"+amount*10+" where stuNO="+Integer.parseInt(sno));
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
					int i=1;
					double price=(day*0.1-amount);
					if(i==1){//still need to pay
						JOptionPane.showMessageDialog(this,"You already paid "+amount+"$,you still need to pay "+price+"$.",
											"Info",JOptionPane.INFORMATION_MESSAGE);
						
						
					      return;
							}
					
				}
				
			    else {//pay successfully
			      	JOptionPane.showMessageDialog(this,"You succeed paid "+day*0.1+"$.",
										"Info",JOptionPane.INFORMATION_MESSAGE);
					jtf.setText("");
					jtf.setText("");
					
					  
					  try {
				    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
						  ct=DriverManager.getConnection("jdbc:odbc:sql server");
						  ps=ct.prepareStatement("delete from EXCEEDTIME where StuNO="+Integer.parseInt(sno));
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
					  
					  
					  try {
				    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
						  ct=DriverManager.getConnection("jdbc:odbc:sql server");
						  ps=ct.prepareStatement("update STUDENT set Permitted='Yes' where StuNO="+Integer.parseInt(sno));
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
			}			 
		}
