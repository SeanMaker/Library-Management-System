import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class BookManage extends JPanel implements ActionListener
{
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	private JPanel jpt=new JPanel();
	String []str1=new String [7];
	
	PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;
	
	private JLabel[] jlArray=new JLabel[]
	{
		new JLabel("         Book ID"),
		new JLabel("            Name"),
		new JLabel("          Author"),
		new JLabel("       Publisher"),
		new JLabel("       Publish Date"),
		new JLabel("         Reserved"),
		new JLabel("         Borrowed")
	};
	private JTextField[] jtxtArray=new JTextField[]
	{
		new JTextField(),
		new JTextField(),
		new JTextField(),
		new JTextField(),
		new JTextField()
	};
	//set text for JButton
	private JButton[] jbArray=
	{
	    new JButton("Add Book"),
	    new JButton("Delete Book"),
	    new JButton("Modify Book Record"),
	    new JButton("Search Book")
	};
	//create title
	Vector<String> head = new Vector<String>();
	{
		head.add("Book ID");
		head.add("Name");
		head.add("Author");
		head.add("Publisher");
		head.add("Publish Date");
		head.add("Borrowed Y/N");
		head.add("Reserved Y/N");
	}
	//set table for window(right-bottom)
	Vector<Vector> data=new Vector<Vector>();
    //create table model
    DefaultTableModel dtm=new DefaultTableModel(data,head);
    //create Jtable object
	JTable jt=new JTable(dtm);
	JScrollPane jspn=new JScrollPane(jt);
	private String[] str={"No","Yes"};
	//create Jcombobox, add text
	private JComboBox jcp1=new JComboBox(str);
	private JComboBox jcp2=new JComboBox(str);
	public BookManage()
	{
		this.setLayout(new GridLayout(1,1));
		//set layout management(right-top)
		jpt.setLayout(null);
		jsp.setDividerLocation(140);
		jsp.setDividerSize(4);
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		for(int i=0;i<5;i++)
		{
			jpt.add(jtxtArray[i]);
		}
		for(int i=0;i<7;i++)
		{
			jpt.add(jlArray[i]);
			if(i<3)
			{
			    jlArray[i].setBounds(15,10+30*i,100,20);
			    jtxtArray[i].setBounds(115,10+30*i,150,20);
	
			}
			else if(i>2&&i<5)
			{
				jlArray[i].setBounds(265,10+30*(i-3),100,20);
				jtxtArray[i].setBounds(375,10+30*(i-3),120,20);
			}
			else
			{
				jlArray[i].setBounds(495,10+30*(i-5),100,20);	
			}	
		}
		for(int i=0;i<5;i++)
		{
			jtxtArray[i].addActionListener(this);
		}
		this.add(jsp);
		jpt.add(jcp1);
		jpt.add(jcp2);
	
    	jsp.setBottomComponent(jspn);
		jcp1.setBounds(595,10,100,20);
		jcp2.setBounds(595,40,100,20);
		//add JButton into jpt
		for(int i=0;i<4;i++)
		{
			jpt.add(jbArray[i]);
			jbArray[i].setBounds(150+180*i,100,150,25);
		}
		//set actionlistener for button
		for(int i=0;i<4;i++)
		{
			jbArray[i].addActionListener(this);
		}		
		//set the window size and location
		this.setBounds(5,5,600,500);
		this.setVisible(true);
	}
    public void actionPerformed(ActionEvent e){
    	//set cursor focus
		if(e.getSource()==jtxtArray[0]){
			jtxtArray[1].requestFocus();
		}
    	if(e.getSource()==jtxtArray[1]){
    		jtxtArray[2].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[2]){
    		jtxtArray[3].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[3]){
    		jtxtArray[4].requestFocus();
    	}
		if(e.getSource()==jbArray[0]){//add book
			this.insertBook();
		} 
	    if(e.getSource()==jbArray[1]){//delete book
	    	this.deleteBook();
	    	}	
	    if(e.getSource()==jbArray[2]){//update book
	    	this.updateBook();
	    }	
	    if(e.getSource()==jbArray[3]){//search book
	    	this.searchBook();
	    }
	}
    public void insertBook(){
		for(int i=0;i<5;i++){
            str1[i]=jtxtArray[i].getText().trim();		
		}
		if(str1[0].equals("")&&str1[1].equals("")&&str1[2].equals("")
			   &&str1[3].equals("")&&str1[4].equals("")){
			JOptionPane.showMessageDialog(this,	"Book information cannot be empty!",
						        "Information",JOptionPane.INFORMATION_MESSAGE);
	            return;	
		}
	    if(!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")
		   &&!str1[3].equals("")&&!str1[4].equals("")){//insert book info into book table
			str1[5]=jcp1.getSelectedItem().toString();
			str1[6]=jcp2.getSelectedItem().toString();
			
			Vector<String> v = new Vector<String>();
			
		    try {
		    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				  ct=DriverManager.getConnection("jdbc:odbc:sql server");
				  ps=ct.prepareStatement("insert into BOOK values('"+str1[0]+"','"+str1[1]+"','"
							 + str1[2] + "',' "+str1[3]+"','"+
					            str1[4]+"','"+str1[5]+"','"+str1[6]+"')");
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
				  
		    for(int i=1;i<=7;i++){//temporary array
				v.add(str1[i-1]);	
		    }
		    data.add(v);
			dtm.setDataVector(data,head);//update table	
			jt.updateUI();
			jt.repaint();
			
			for(int i=0;i<5;i++){
	            jtxtArray[i].setText("");		
			}
			
			return;
		}
    }		
    
    
	public void deleteBook(){
		String bookno = jtxtArray[0].getText().trim();
		if(bookno.equals("")){
			JOptionPane.showMessageDialog(this,	"Book ID cannot be empty! Please try again.",
						        "Information",JOptionPane.INFORMATION_MESSAGE);
			return;			
		}
//		sql="select * from RECORD where BookNO="+Integer.parseInt(bookno);
//		sql="delete from book where BookNO="+Integer.parseInt(bookno);
	    
		Vector v_delete=new Vector();
	    try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("select * from BOOK where BookNO="+Integer.parseInt(bookno));
			  rs=ps.executeQuery();
			  
			  while(rs.next())
			  {
				  Vector row=new Vector();
					row.add(rs.getInt(1));
					row.add(rs.getString(2));
					row.add(rs.getString(3));
					row.add(rs.getString(4));
					row.add(rs.getString(5));
					row.add(rs.getString(6));
					row.add(rs.getString(7));
					v_delete.add(row);
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
	    dtm.setDataVector(v_delete,head);	//update table	
		jt.updateUI();
		jt.repaint();
	    
	    
		try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("delete from book where BookNO="+Integer.parseInt(bookno));
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
	    
		JOptionPane.showMessageDialog(this,"Delete Successfully!",
                "Info",JOptionPane.INFORMATION_MESSAGE);
		return;

		
	}
	
	
	public void updateBook(){
		String bookno = jtxtArray[0].getText().trim();
		if(bookno.equals("")){
			JOptionPane.showMessageDialog(this,	"Please enter book ID!",
						               "Information",JOptionPane.INFORMATION_MESSAGE);
			return;			
		}
		else{
		    for(int i=0;i<5;i++){
                str1[i]=jtxtArray[i].getText().trim();		
		    }

			int i=0;
			int flag=1;
			int b=Integer.parseInt(bookno);
			if(!str1[1].equals("")){i=i+1;}
			if(!str1[2].equals("")){i=i+2;}
			if(!str1[3].equals("")){i=i+4;}
			if(!str1[4].equals("")){i=i+8;}
			switch(i){
				case 0:
					JOptionPane.showMessageDialog(this,"The information cannot be empty!",
					                     "Information",JOptionPane.INFORMATION_MESSAGE);
				break;
				case 1:
//				sql="update BOOK set BookName='"+str1[1]+"' where BookNO="+b;
					
					try {
				    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
						  ct=DriverManager.getConnection("jdbc:odbc:sql server");
						  ps=ct.prepareStatement("update BOOK set BookName='"+str1[1]+"' where BookNO="+b);
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
		
		        if(flag>0){
					JOptionPane.showMessageDialog(this,"Successfully modified!",
					                       "Info",JOptionPane.INFORMATION_MESSAGE);		        	
		        }break;
		        
		        case 2:
//		        sql="update BOOK set Author='"+str1[2]+"' where BookNO="+b;
		        	
					try {
				    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
						  ct=DriverManager.getConnection("jdbc:odbc:sql server");
						  ps=ct.prepareStatement("update BOOK set Author='"+str1[2]+"' where BookNO="+b);
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
		        	
		        if(flag>0){
					JOptionPane.showMessageDialog(this,"Successfully modified!",
					                     "Info",JOptionPane.INFORMATION_MESSAGE);		        	
		        }break;
		        
		        case 3:
//		        sql="update BOOK set BookName='"+str1[1]+"',"+"Author='"
//		                      +str1[2]+"' where BookNO="+b;
		        
				try {
			    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					  ct=DriverManager.getConnection("jdbc:odbc:sql server");
					  ps=ct.prepareStatement("update BOOK set BookName='"+str1[1]+"',"+"Author='"
		                      +str1[2]+"' where BookNO="+b);
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
		        
		        if(flag>0){
					JOptionPane.showMessageDialog(this,"Successfully modified!",
					                        "Info",JOptionPane.INFORMATION_MESSAGE);		        	
		        }break;
		        
		        case 4:
//		        sql="update BOOK set Publishment='"+str1[3]+"' where BookNO="+b;
		        
				try {
			    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					  ct=DriverManager.getConnection("jdbc:odbc:sql server");
					  ps=ct.prepareStatement("update BOOK set Publishment='"+str1[3]+"' where BookNO="+b);
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
		        
		        if(flag>0){
					JOptionPane.showMessageDialog(this,"Successfully modified!",
					                      "Info",JOptionPane.INFORMATION_MESSAGE);		        	
		        }break;
		        
		        case 5:
//		        sql="update BOOK set BookName='"+str1[1]+"',"+"Publishment='"
//		                        +str1[3]+"' where BookNO="+b;
		        
				try {
			    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					  ct=DriverManager.getConnection("jdbc:odbc:sql server");
					  ps=ct.prepareStatement("update BOOK set BookName='"+str1[1]+"',"+"Publishment='"
		                        +str1[3]+"' where BookNO="+b);
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
				
		        if(flag>0){
					JOptionPane.showMessageDialog(this,"Successfully modified!",
					                     "Info",JOptionPane.INFORMATION_MESSAGE);		        	
		        }break;
		        
		        case 6:
//		        sql="update BOOK set Author='"+str1[2]+"',"+"Publishment='"
//		                      +str1[3]+"' where BookNO="+b;
		        
				try {
			    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					  ct=DriverManager.getConnection("jdbc:odbc:sql server");
					  ps=ct.prepareStatement("update BOOK set Author='"+str1[2]+"',"+"Publishment='"
		                      +str1[3]+"' where BookNO="+b);
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
		        
		        if(flag>0){
					JOptionPane.showMessageDialog(this,"Successfully modified!",
					                       "Info",JOptionPane.INFORMATION_MESSAGE);		        	
		        }break;
		        
		        case 7:
//		        sql="update BOOK set BookName='"+str1[1]+"',"+"Author='"+str1[2]
//		                       +"',"+"Publishment='"+str1[3]+"' where BookNO="+b;
		        
				try {
			    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					  ct=DriverManager.getConnection("jdbc:odbc:sql server");
					  ps=ct.prepareStatement("update BOOK set BookName='"+str1[1]+"',"+"Author='"+str1[2]
		                       +"',"+"Publishment='"+str1[3]+"' where BookNO="+b);
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
		        
		        if(flag>0){
					JOptionPane.showMessageDialog(this,"Successfully modified!",
					                      "Info",JOptionPane.INFORMATION_MESSAGE);		        	
		        }break;
		        
		        case 8:
//		        sql="update BOOK set PublishTime='"+str1[4]+"' where BookNO="+b;
		        
				try {
			    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					  ct=DriverManager.getConnection("jdbc:odbc:sql server");
					  ps=ct.prepareStatement("update BOOK set PublishTime='"+str1[4]+"' where BookNO="+b);
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
		        
		        if(flag>0){
					JOptionPane.showMessageDialog(this,"Successfully modified!",
					                        "Info",JOptionPane.INFORMATION_MESSAGE);		        	
		        }break;
		        
		        case 9:
//		        sql="update BOOK set BookName='"+str1[1]+"',"+"PublishTime='"
//		                +str1[4]+"' where BookNO="+b;
		        
				try {
			    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					  ct=DriverManager.getConnection("jdbc:odbc:sql server");
					  ps=ct.prepareStatement("update BOOK set BookName='"+str1[1]+"',"+"PublishTime='"
				                +str1[4]+"' where BookNO="+b);
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
		        
		        if(flag>0){
					JOptionPane.showMessageDialog(this,"Successfully modified!",
					                     "Info",JOptionPane.INFORMATION_MESSAGE);		        	
		        }break;
		        
		        case 10:
//		        sql="update BOOK set Author='"+str1[2]+"',"+"PublishTime='"
//		                  +str1[4]+"' where BookNO="+b;
		        
				try {
			    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					  ct=DriverManager.getConnection("jdbc:odbc:sql server");
					  ps=ct.prepareStatement("update BOOK set Author='"+str1[2]+"',"+"PublishTime='"
			                  +str1[4]+"' where BookNO="+b);
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
		        
		        if(flag>0){
					JOptionPane.showMessageDialog(this,"Successfully modified!",
					                      "info",JOptionPane.INFORMATION_MESSAGE);		        	
		        }break;
		        
		        case 11:
//		        sql="update BOOK set BookName='"+str1[1]+"',"+"Author='"+str1[2]
//		             +"',"+"PublishTime='"+str1[4]+"' where BookNO="+b;
		        
				try {
			    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					  ct=DriverManager.getConnection("jdbc:odbc:sql server");
					  ps=ct.prepareStatement("update BOOK set BookName='"+str1[1]+"',"+"Author='"+str1[2]
					             +"',"+"PublishTime='"+str1[4]+"' where BookNO="+b);
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
		        
		        if(flag>0){
					JOptionPane.showMessageDialog(this,"Successfully modified!",
					                        "Info",JOptionPane.INFORMATION_MESSAGE);		        	
		        }break;
		        
		        case 12:
//		        sql="update BOOK set Publishment='"+str1[3]+"',"+"PublishTime='"
//		                        +str1[4]+"' where BookNO="+b;
		        
				try {
			    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					  ct=DriverManager.getConnection("jdbc:odbc:sql server");
					  ps=ct.prepareStatement("update BOOK set Publishment='"+str1[3]+"',"+"PublishTime='"
		                        +str1[4]+"' where BookNO="+b);
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
		        
		        if(flag>0){
					JOptionPane.showMessageDialog(this,"Successfully modified!",
					                       "Info",JOptionPane.INFORMATION_MESSAGE);		        	
		        }break;
		        
		        case 13:
//		        sql="update BOOK set BookName='"+str1[1]+"',"+"Publishment='"+str1[3]
//		                       +"',"+"PublishTime='"+str1[4]+"' where BookNO="+b;
		        
				try {
			    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					  ct=DriverManager.getConnection("jdbc:odbc:sql server");
					  ps=ct.prepareStatement("update BOOK set BookName='"+str1[1]+"',"+"Publishment='"+str1[3]
		                       +"',"+"PublishTime='"+str1[4]+"' where BookNO="+b);
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
		        
		       if(flag>0){
					JOptionPane.showMessageDialog(this,"Successfully modified!",
					                      "Info",JOptionPane.INFORMATION_MESSAGE);		        	
		        }break;
		        
		        case 14:
//		        sql="update BOOK set Author='"+str1[1]+"',"+"Publishment='"+str1[2]
//		                        +"',"+"PublishTime='"+str1[4]+"' where BookNO="+b;
		        
				try {
			    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					  ct=DriverManager.getConnection("jdbc:odbc:sql server");
					  ps=ct.prepareStatement("update BOOK set Author='"+str1[2]+"',"+"Publishment='"+str1[3]
		                        +"',"+"PublishTime='"+str1[4]+"' where BookNO="+b);
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
		        
		        if(flag>0){
					JOptionPane.showMessageDialog(this,"Successfully modified!",
					                       "Info",JOptionPane.INFORMATION_MESSAGE);		        	
		        }break;
		        
		        case 15:
//		        sql="update BOOK set BookName='"+str1[1]+"',"+"Author='"+str1[2]+"',"+
//		            "Publishment='"+str1[3]+"',"+"PublishTime='"+str1[4]+"' where BookNO="+b;
		        
				try {
			    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					  ct=DriverManager.getConnection("jdbc:odbc:sql server");
					  ps=ct.prepareStatement("update BOOK set BookName='"+str1[1]+"',"+"Author='"+str1[2]+"',"+
					            "Publishment='"+str1[3]+"',"+"PublishTime='"+str1[4]+"' where BookNO="+b);
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
		        
		        if(flag>0){
					JOptionPane.showMessageDialog(this,"Successfully modified!",
					                              "Info",JOptionPane.INFORMATION_MESSAGE);		        	
		        }break;
			}
			
			Vector v_update=new Vector();
		    try {
		    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				  ct=DriverManager.getConnection("jdbc:odbc:sql server");
				  ps=ct.prepareStatement("select * from BOOK where BookNO="+Integer.parseInt(jtxtArray[0].getText().trim()));
				  rs=ps.executeQuery();
				  
				  while(rs.next())
				  {
					  Vector row=new Vector();
						row.add(rs.getInt(1));
						row.add(rs.getString(2));
						row.add(rs.getString(3));
						row.add(rs.getString(4));
						row.add(rs.getString(5));
						row.add(rs.getString(6));
						row.add(rs.getString(7));
						v_update.add(row);
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
				  
		    dtm.setDataVector(v_update,head);	
			jt.updateUI();
			jt.repaint();	
		}
	}
	
	
	public void searchBook(){

		if(jtxtArray[0].getText().equals("")){//
			JOptionPane.showMessageDialog(this,"please enter book ID!",
			                              "Info",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		Vector v_search=new Vector();
	    try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("select * from BOOK where BookNO="+Integer.parseInt(jtxtArray[0].getText().trim()));
			  rs=ps.executeQuery();
			  
			  while(rs.next())
			  {
				  Vector row=new Vector();
					row.add(rs.getInt(1));
					row.add(rs.getString(2));
					row.add(rs.getString(3));
					row.add(rs.getString(4));
					row.add(rs.getString(5));
					row.add(rs.getString(6));
					row.add(rs.getString(7));
					v_search.add(row);
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
			  
	    dtm.setDataVector(v_search,head);
		jt.updateUI();
		jt.repaint();	
		
	}	
}