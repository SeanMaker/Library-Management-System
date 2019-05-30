import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class GuaShi extends JPanel implements ActionListener
{
	DataBase db;
	String sql;
	String str;
	
	PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;
	
	
    private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
    
	private JPanel jpt=new JPanel();
	private JPanel jpb=new JPanel();
	
	private JButton[] jbArray=new JButton[]
	{
		new JButton	("Report Lost"),
		new JButton	("Check")
	};
	private JLabel jl=new JLabel("Please enter student ID:");

	private JTextField jtxt=new JTextField();

	
	Vector<String> head = new Vector<String>();
	{
		head.add("Book ID");
		head.add("Student ID");
		head.add("Borrow time");
		head.add("Return time");
		head.add("Borrowed Y/N");
		head.add("Reserved Y/N");
	}
	
	Vector<Vector> data=new Vector<Vector>();
	
    DefaultTableModel dtm=new DefaultTableModel(data,head);
    
	JTable jt=new JTable(dtm);
	
	JScrollPane jspn=new JScrollPane(jt);
	public GuaShi()
	{
		this.setLayout(new GridLayout(1,1));
		
		jpt.setLayout(null);
		jpb.setLayout(null);
		
           jl.setBounds(5,15,150,20);	
		
		 jpt.add(jl);
		
		jtxt.setBounds(155,15,300,20);
    	
	    jpt.add(jtxt);
        
	    jbArray[0].setBounds(5,50,100,20);
        jbArray[1].setBounds(150,50,100,20);
        
         for(int i=0;i<jbArray.length;i++)
		{
			 jpt.add(jbArray[i]);
			 jbArray[i].addActionListener(this);
		}
		
    	jsp.setTopComponent(jpt);
        jsp.setBottomComponent(jspn);
    	jsp.setDividerSize(4);
    	this.add(jsp);
    	
    	jsp.setDividerLocation(80);
		
        this.setBounds(10,10,800,600);
        this.setVisible(true); 
   	} 
   	public void actionPerformed(ActionEvent e)
   	{
   		if(e.getSource()==jbArray[1]){//check button
   			if(jtxt.getText().trim().equals("")){
   				JOptionPane.showMessageDialog(this,"Please enter Student ID!",
					            "Information",JOptionPane.INFORMATION_MESSAGE);
					return;
   			}
   			else{//search by sno
   			//sql="select * from RECORD where StuNO="+jtxt.getText().trim();

  			    Vector<Vector> vtemp = new Vector<Vector>();
   				
   			    try {
   			    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
   					  ct=DriverManager.getConnection("jdbc:odbc:sql server");
   					  ps=ct.prepareStatement("select * from RECORD where StuNO="+jtxt.getText().trim());
   					  rs=ps.executeQuery();
   					 		
 					  int input_stuno=Integer.parseInt(jtxt.getText().trim());
   		
   					  
   					  while(rs.next()){
							  Vector row=new Vector();
							  
							  int db_bookno=rs.getInt(1);
							  int db_stuno=rs.getInt(2);
							  String db_borrowtime=rs.getString(3);
							  String db_returntime=rs.getString(4);
							  String db_borrowed=rs.getString(5);
							  String db_ordered=rs.getString(6);
							  
								row.add(db_bookno);
								row.add(db_stuno);
								row.add(db_borrowtime);
								row.add(db_returntime);
								row.add(db_borrowed);
								row.add(db_ordered);
								vtemp.add(row);
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
   			    	
					dtm.setDataVector(vtemp,head);		
					jt.updateUI();
					jt.repaint();

			}

   		}
   		if(e.getSource()==jbArray[0]){//report lose
			int row=jt.getSelectedRow();
			if(row<0){
				JOptionPane.showMessageDialog(this,"Please select the book which lost!","Information",
					                              JOptionPane.INFORMATION_MESSAGE);
				return;
			}
   			loseBook(row);
   			updateTable();
   		}
   	}
   		
   	public void loseBook(int row){
		String bname="";
		int lbno=0;

		int bno=Integer.parseInt(jt.getValueAt(row,0).toString());
		int sno=Integer.parseInt(jt.getValueAt(row,1).toString());
		
//		sql="select BookName from BOOK where BookNO="+bno;
		try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("select BookName from BOOK where BookNO="+bno);
			  rs=ps.executeQuery();
			  
			  while(rs.next()){
				  bname=rs.getString(1);
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

//		sql="select MAX(LbNO) from LoseBook";
		
		try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("select MAX(LbNO) from LoseBook");
			  rs=ps.executeQuery();
			  
			  while(rs.next()){
				  lbno=rs.getInt(1)+1;
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
			  		
//		sql="insert into LOSEBOOK values("+lbno+","+sno+","+bno+",'"+bname+"')";//向丢书记录表中插入记录
		try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("insert into LOSEBOOK values(?,?,?,?)");
			  ps.setInt(1, lbno); ps.setInt(2, sno); ps.setInt(3, bno); ps.setString(4, bname);
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
			  
//		sql="select BookNo from ORDERREPORT where BookNO="+bno;
		
		try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("select BookNo from ORDERREPORT where BookNO="+bno);
			  rs=ps.executeQuery();
			  while(rs.next()){
				  int orderreport_no=rs.getInt(1);
				  if(orderreport_no==bno){
					  ps=ct.prepareStatement("delete from orderreport where BookNO="+bno);
					  rs=ps.executeQuery();
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
			  
//		sql="select BookNo from EXCEEDTIME where BookNO="+bno;	
		try {
			  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("select BookNo from EXCEEDTIME where BookNO="+bno);
			  rs=ps.executeQuery();
			  while(rs.next()){
				  int exceedtime_no=rs.getInt(1);
				  if(exceedtime_no==bno){
					  ps=ct.prepareStatement("delete from EXCEEDTIME where BookNO="+bno);
					  rs=ps.executeQuery();
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

		
//		sql="delete from RECORD where BookNO="+bno;
		try {
			  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("delete from RECORD where BookNO="+bno);
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
			  
//		sql="delete from BooK where BookNo="+bno;
		
		try {
			  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("delete from Book where BookNo="+bno);
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
		if(i>0){//report success
			JOptionPane.showMessageDialog(this,"Reporting lose book successfully!","Information",
				                           JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		else{//report failure
			JOptionPane.showMessageDialog(this,"Sorry, reporting lose book fail!",
					   	"Information",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
   	}	
	public void updateTable(){//update
   		
   		Vector<Vector> vtemp_1 = new Vector<Vector>();
   		
   		try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("select * from RECORD where StuNO="+jtxt.getText().trim());
			  rs=ps.executeQuery();
			  
			  
			  
			  while(rs.next()){
				  Vector row=new Vector();
				  
				  int db_bookno=rs.getInt(1);
				  int db_stuno=rs.getInt(2);
				  String db_borrowtime=rs.getString(3);
				  String db_returntime=rs.getString(4);
				  String db_borrowed=rs.getString(5);
				  String db_ordered=rs.getString(6);
				  
					row.add(db_bookno);
					row.add(db_stuno);
					row.add(db_borrowtime);
					row.add(db_returntime);
					row.add(db_borrowed);
					row.add(db_ordered);
					vtemp_1.add(row);
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
   		
		jt.clearSelection();			
		dtm.setDataVector(vtemp_1,head);	
		jt.updateUI();
		jt.repaint();   		
   	}	
   	public int checkTime(int sno,int bno)
   	{//-1:overtime& no pay;  1: successfully return;  -2:overtime&paid
   		int day=0;
   		int flag=0;
   		String bname="";
   		Date now=new Date();
   		String borrowtime="";
   		
   		try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("select BorrowTime from RECORD where StuNO="+sno+" and BookNO="+bno);
			  rs=ps.executeQuery();
			  
			  while(rs.next()){
				  borrowtime=rs.getString(1);
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
   		
   		//sql="select ReturnTime from RECORD where StuNO="+sno+" and BookNO="+bno;
   		
   		String[] strday=borrowtime.split("\\.");//date format
		int byear=Integer.parseInt(strday[0].trim());
		int bmonth=Integer.parseInt(strday[1].trim());
		int bday=Integer.parseInt(strday[2].trim());
		
		Calendar n= Calendar.getInstance();

		day=(n.get(Calendar.YEAR)-byear)*365+(n.get(Calendar.MONTH)+1-bmonth)*30+(n.get(Calendar.DAY_OF_MONTH)-bday);
		int overtime=day-30;
	
		if(day>30)
		{//overtime
			int i=JOptionPane.showConfirmDialog(this,"Return the book overtime, you should pay"
			             +(day-30)*0.1+"$. Will you pay the fine?","Information",JOptionPane.YES_NO_OPTION);
			if(i==JOptionPane.YES_OPTION){
				JOptionPane.showMessageDialog(this,"You paid"+(day-30)*0.1+"$.",
										"Information",JOptionPane.INFORMATION_MESSAGE);
				flag=-2;
			}
			else
			{//overtime and did't pay
				flag=-1;
				
				try {
			    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					  ct=DriverManager.getConnection("jdbc:odbc:sql server");
					  ps=ct.prepareStatement("select BookName from BOOK where BookNO="+bno);
					  rs=ps.executeQuery();
					  
					  while(rs.next()){
						  bname=rs.getString(1);
					  }
					  
					  ps=ct.prepareStatement("insert into EXCEEDTIME(StuNO,BookNO,BookName,DelayTime) values("+sno+","+bno+",'"+bname+"',"+overtime+")");
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
		else
		{//successfully return book 
			flag=1;
		}
		return flag;
   	}
}