import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class OrderBook extends JPanel implements ActionListener{
	
    private JSplitPane jsp1=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	private JPanel jp2=new JPanel();
	
	int flag;
	String sql;
	
	
	PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;
	
	private JButton jb2=new JButton("Ckeck");
	private JLabel jl3=new JLabel("Please enter Book ID:");
	private JLabel jl4=new JLabel("Please enter Student ID:");
	
	private JTextField jtxt3=new JTextField();
	private JTextField jtxt4=new JTextField();
	Vector<String> head = new Vector<String>();	
	{
		head.add("Book ID");
		head.add("Book Name");
		head.add("Author");
		head.add("Press");
		head.add("Borrowed Y/N");
		head.add("Reserved Y/N");
	}
	
	Vector<Vector> data=new Vector<Vector>();
    
    DefaultTableModel dtm=new DefaultTableModel(data,head);
    
	JTable jt=new JTable(dtm);
	
	JScrollPane jspn=new JScrollPane(jt);
    public OrderBook()
    {
    	this.setLayout(new GridLayout(1,1));
    	
    	jsp1.setTopComponent(jp2);
    	
    	jsp1.setBottomComponent(jspn);
    	
    	jsp1.setDividerLocation(80);
    	
    	jsp1.setDividerSize(4);
    	
    	jp2.setLayout(null);
    	
		jb2.setBounds(540,30,100,20);
    	
		jp2.add(jb2);
		jb2.addActionListener(this);
		
    	jl3.setBounds(20,30,130,20);
    	jl4.setBounds(270,30,150,20);
    	
    	jp2.add(jl3);
    	jp2.add(jl4);	
    	jtxt3.setBounds(155,30,100,20);
    	jtxt4.setBounds(430,30,100,20);
    	jp2.add(jtxt3);
    	jp2.add(jtxt4);
    	this.add(jsp1);
    	
        this.setBounds(10,10,800,600);
        this.setVisible(true);  
    }	
  

	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jb2){
			if(jtxt4.getText().equals("")){
				JOptionPane.showMessageDialog(this,"Student ID cannot be empty, please enter again!",
				                      "Information",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			if(jtxt3.getText().equals("")){
				JOptionPane.showMessageDialog(this,"Book ID cannot be empty!",
				                      "Information",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			//search student ID if in the table
			
           	try {
		    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				  ct=DriverManager.getConnection("jdbc:odbc:sql server");
				  ps=ct.prepareStatement("select * from STUDENT where StuNO="+Integer.parseInt(jtxt4.getText().trim()));
				  rs=ps.executeQuery();
				  
				  int input_stuno=Integer.parseInt(jtxt4.getText().trim());
				  int db_stuno=0;
				  
				  while(rs.next()){
				  db_stuno=rs.getInt(1);
				  }
				  
				  if (input_stuno!=db_stuno){
					  
					  JOptionPane.showMessageDialog(this,"Wrong Student ID, please input again!","Information",
                            JOptionPane.INFORMATION_MESSAGE);
					  return;
				  }
				  
				  else if (input_stuno==db_stuno){
					  
					  try {
//				    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//						  ct=DriverManager.getConnection("jdbc:odbc:sql server");
						  ps=ct.prepareStatement("select * from book where BookNO="+Integer.parseInt(jtxt3.getText().trim()));
						  rs=ps.executeQuery();
						  
						  int input_bookno=Integer.parseInt(jtxt3.getText().trim());
						  int db_bookno=0;
						  String borrowed=null;
						  String ordered=null;
						  
						  while(rs.next())
						  {
								db_bookno=rs.getInt(1);
								borrowed=rs.getString(6);
								ordered=rs.getString(7);
								
						  }	
								if(borrowed.equals("Yes")|| ordered.equals("Yes")){
									JOptionPane.showMessageDialog(this,"Sorry, the book was ordered or borrowed!","Information",
						                      JOptionPane.INFORMATION_MESSAGE);
								}
								
								
								if(db_bookno==input_bookno && borrowed.equals("No")&& ordered.equals("No")){
									
									JOptionPane.showMessageDialog(this,"The book is reserved for you!","Information",
						                      JOptionPane.INFORMATION_MESSAGE);
									try {
//							    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//							    	  ct=DriverManager.getConnection("jdbc:odbc:sql server");
									  ps=ct.prepareStatement("update book set Ordered='Yes' where BookNo="+Integer.parseInt(jtxt3.getText().trim()));
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
		    	

           	
		Vector<Vector> vtemp = new Vector<Vector>();
		
		sql="select * from book where BookNO="+Integer.parseInt(jtxt3.getText().trim());
		try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement(sql);
			  rs=ps.executeQuery();
			  
		while(rs.next())
			  {
				  Vector row=new Vector();
					row.add(rs.getInt(1));
					row.add(rs.getString(2));
					row.add(rs.getString(3));
					row.add(rs.getString(4));
					row.add(rs.getString(6));
					row.add(rs.getString(7));
					vtemp.add(row);
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
	    
		
		dtm.setDataVector(vtemp,head);
		jt.updateUI();
		jt.repaint();	


		}
	}
