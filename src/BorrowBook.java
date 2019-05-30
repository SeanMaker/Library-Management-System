import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
public class BorrowBook extends JPanel implements ActionListener{
	
      private JSplitPane jsp1=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	private JPanel jp2=new JPanel();
	
	int flag;
	String sql;
	
 	PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;

	private JButton jb2=new JButton("Ckeck");
	private JLabel jl3=new JLabel("Book ID");
	private JLabel jl4=new JLabel("Student ID");
	
	private JTextField jtxt3=new JTextField();
	private JTextField jtxt4=new JTextField();
	
	private JRadioButton[] jrbArray=
    {new JRadioButton("Borrow Book",true),new JRadioButton("Reserve Book")};
    private ButtonGroup bg=new ButtonGroup();
	Vector<String> head = new Vector<String>();	
	{
		head.add("Book ID");
		head.add("Name");
		head.add("Author");
		head.add("Publisher");
		head.add("Borrowed Y/N");
		head.add("Reserved Y/N");
	}	
	Vector<Vector> data=new Vector<Vector>();    
    DefaultTableModel dtm=new DefaultTableModel(data,head);   
	JTable jt=new JTable(dtm); 	
	JScrollPane jspn=new JScrollPane(jt);
    public BorrowBook()
    {
    	this.setLayout(new GridLayout(1,1));
    	
    	jsp1.setTopComponent(jp2);
    
    	jsp1.setBottomComponent(jspn);
    	
    	jsp1.setDividerLocation(100);
    	jsp1.setDividerSize(4);
    	jp2.setLayout(null);    	
		jb2.setBounds(380,20,100,20);
    	
		jp2.add(jb2);
		jb2.addActionListener(this);

    	jl3.setBounds(80,60,100,20);
    	jl4.setBounds(280,60,100,20);
    	
    	jp2.add(jl3);
    	jp2.add(jl4);	
    	jtxt3.setBounds(150,60,100,20);
    	jtxt4.setBounds(350,60,100,20);
    	jp2.add(jtxt3);
    	jp2.add(jtxt4);
    	for(int i=0;i<2;i++)
    	{
    		jrbArray[i].setBounds(70+i*150,20,150,20);
    		jp2.add(jrbArray[i]);
    		bg.add(jrbArray[i]);
    	}
    	this.add(jsp1);
    	
        this.setBounds(10,10,800,600);
        this.setVisible(true);  
    }	
    
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jb2){
			if(jtxt4.getText().equals("")){
				JOptionPane.showMessageDialog(this,"Student ID cannot be empty!",
				                      "Information",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			if(jtxt3.getText().equals("")){
				JOptionPane.showMessageDialog(this,"Book ID cannot be empty!",
				                      "Information",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
		
//           	sql="select * from STUDENT where StuNO="+Integer.parseInt(jtxt4.getText().trim());
           	
			Date d=new Date(); //get recent date for borrowing record
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");
			String dateNowStr=sdf.format(d);
			System.out.println(dateNowStr);
			
			int input_stuno;
			int input_bookno;
           	
           	try {
		    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				  ct=DriverManager.getConnection("jdbc:odbc:sql server");
				  ps=ct.prepareStatement("select * from STUDENT where StuNO="+Integer.parseInt(jtxt4.getText().trim()));
				  rs=ps.executeQuery();
				  
				  input_stuno=Integer.parseInt(jtxt4.getText().trim());
				  int db_stuno=0;
				  String db_permit=null;
				  while(rs.next()){
				  db_stuno=rs.getInt(1);
				  db_permit=rs.getString(8);
				  }
				  
				  if (input_stuno!=db_stuno){
					  
					  JOptionPane.showMessageDialog(this,"Wrong Student ID, please enter again!","Information",
                          JOptionPane.INFORMATION_MESSAGE);
					  return;
				  }
				  
				  if (input_stuno==db_stuno && db_permit.equals("No")){
					  JOptionPane.showMessageDialog(this,"Sorry, you do not allowed to borrow books, please pay the fine first!","Information",
	                          JOptionPane.INFORMATION_MESSAGE);
						  return;
				  }
				  else if (input_stuno==db_stuno && db_permit.equals("Yes")){
					  
					  try {
//				    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//						  ct=DriverManager.getConnection("jdbc:odbc:sql server");
						  ps=ct.prepareStatement("select * from book where BookNO="+Integer.parseInt(jtxt3.getText().trim()));
						  rs=ps.executeQuery();
						  
						  input_bookno=Integer.parseInt(jtxt3.getText().trim());
						  int db_bookno=0;
						  String borrowed=null;
						  String ordered=null;
						  
						  while(rs.next())
						  {
								db_bookno=rs.getInt(1);
								borrowed=rs.getString(6);
								ordered=rs.getString(7);
								
						  }	
								if(borrowed.equals("Yes")){
									JOptionPane.showMessageDialog(this,"Sorry, the book was borrowed!","Information",
						                      JOptionPane.INFORMATION_MESSAGE);
								}
								
								
								if(db_bookno==input_bookno && borrowed.equals("No")){
									
									JOptionPane.showMessageDialog(this,"The book is borrowed for you!","Information",
						                      JOptionPane.INFORMATION_MESSAGE);
									try {
									  ps=ct.prepareStatement("update book set Borrowed='Yes',Ordered='No' where BookNo="+Integer.parseInt(jtxt3.getText().trim()));
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
									
									//adding borrowing record into RECORD table
									try {
								    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
								    	  ct=DriverManager.getConnection("jdbc:odbc:sql server");

										  ps=ct.prepareStatement("insert into record values('"+input_bookno+"','"+input_stuno+"','"+dateNowStr+"','null','Yes','No')");
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

			
			
			
			
			
			
			
			
			
			
			
			