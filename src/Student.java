import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class Student extends JPanel implements ActionListener
{   
	private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel jpt=new JPanel();
	String[]str1=new String[7];
		
	PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;
    
	private JLabel[] jlArray=new JLabel[]{//label array
		new JLabel("        Student ID"),
		new JLabel("                 Name"),
		new JLabel("              Gender"),
		new JLabel("               Class"),
		new JLabel("        Department"),
	    new JLabel("          Password"),
	    new JLabel("        Permission")   
	};
	private JTextField[] jtxtArray=new JTextField[]{//Text array
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField(),
		new JTextField(),new JTextField()
	};
	private String[] str={"Yes","No"};
	private JComboBox jcp=new JComboBox(str);//Jcombobox
	private JButton[] jbArray={//set text for JButton
	    new JButton("Add Student Info"),new JButton("Delete Student Info"),
	    new JButton("Modify Student Info"),new JButton("Search Student Info")
	};
	Vector<String> head = new Vector<String>();
	{//title
		head.add("Student ID");head.add("Name");
		head.add("Gender");head.add("Class");
		head.add("Department");head.add("Permission");
		head.add("Password");
	}
	Vector<Vector> data=new Vector<Vector>();
    DefaultTableModel dtm=new DefaultTableModel(data,head);
	JTable jt=new JTable(dtm);
	JScrollPane jspn=new JScrollPane(jt);
	public Student()
	{
		this.setLayout(new GridLayout(1,1));
		jpt.setLayout(null);
		jsp.setDividerLocation(130);
		jsp.setDividerSize(4);
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		for(int i=0;i<6;i++){
			jpt.add(jtxtArray[i]);
		}
		for(int i=0;i<7;i++){
			jpt.add(jlArray[i]);
			if(i<3)
			{
			    jlArray[i].setBounds(20+i*200,10,100,20);
			    jtxtArray[i].setBounds(120+i*200,10,120,20);
			    jtxtArray[i].addActionListener(this);
			}
			else if(i>2&&i<6)
			{
				jlArray[i].setBounds(20+(i-3)*200,50,100,20);
				jtxtArray[i].setBounds(120+(i-3)*200,50,120,20);
				jtxtArray[i].addActionListener(this);
			}
			else
			{
				jlArray[i].setBounds(620,10,100,20);
			}
		}
		this.add(jsp);
		jpt.add(jcp);
    	jsp.setBottomComponent(jspn);
		jcp.setBounds(720,10,100,20);
		for(int i=0;i<4;i++)
		{
			jpt.add(jbArray[i]);
			jbArray[i].setBounds(170+180*i,90,150,25);
			jbArray[i].addActionListener(this);	
		}		
		
		this.setBounds(5,5,600,500);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{//set focus for Text 
		if(e.getSource()==jtxtArray[0])
    	{
    		jtxtArray[1].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[1])
    	{
    		jtxtArray[2].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[2])
    	{
    		jtxtArray[3].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[3])
    	{
    		jtxtArray[4].requestFocus();
    	}
    	if(e.getSource()==jtxtArray[4])
    	{
    		jtxtArray[5].requestFocus();
    	}   
	    //click "add student information"
		if(e.getSource()==jbArray[0])
		{
			this.insertStudent();
		}
		//click "delete student"	
		if(e.getSource()==jbArray[1])
		{
			this.deleteStudent();
		}
		//click "update"
		if(e.getSource()==jbArray[2])
		{
			this.updateStudent();
		}
		//click "search"
		if(e.getSource()==jbArray[3])
		{
			this.searchStudent();
		}
	}
	public void insertStudent(){
        for(int i=0;i<6;i++){
			str1[i]=jtxtArray[i].getText().trim();		
		}
    	if(str1[0].equals("")&&str1[1].equals("")&&str1[2].equals("")
		   &&str1[3].equals("")&&str1[4].equals("")&&str1[5].equals(""))
		{
		  	JOptionPane.showMessageDialog(this,	"Student information cannot be empty!",
							        "Info",JOptionPane.INFORMATION_MESSAGE);
			return;	
		}
		if(!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")
		   &&!str1[3].equals("")&&!str1[4].equals("")&&!str1[5].equals(""))
		{
			str1[6]=jcp.getSelectedItem().toString();
			

		    
		    try {
		    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				  ct=DriverManager.getConnection("jdbc:odbc:sql server");
				  ps=ct.prepareStatement("insert into STUDENT(StuNO,StuName,StuSex,Class,Department,"
							+"Password,Permitted) values('"+str1[0]+"','"+str1[1]+"','"
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
		    
			JOptionPane.showMessageDialog(this,"Adding Successfully!",
	                "Info",JOptionPane.INFORMATION_MESSAGE);
			
			for(int i=0;i<6;i++){
	            jtxtArray[i].setText("");		
			}
			
			
			return;

			
		}
    }
	public void deleteStudent(){
		String stuno = jtxtArray[0].getText().trim();
		if(stuno.equals("")){
			JOptionPane.showMessageDialog(this,	"Student ID cannot be empty!",
						        "Info",JOptionPane.INFORMATION_MESSAGE);
			return;			
		}
		
		
//		sql="select * from STUDENT where StuNO="+Integer.parseInt(stuno);

		Vector v1=new Vector();
	    try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("select * from STUDENT where StuNO="+Integer.parseInt(stuno));
			  rs=ps.executeQuery();
			  
			  while(rs.next())
			  {
				  Vector row=new Vector();
					row.add(rs.getInt(1));
					row.add(rs.getString(2));
					row.add(rs.getString(4));
					row.add(rs.getString(5));
					row.add(rs.getString(6));
					row.add(rs.getString(8));
					row.add(rs.getString(9));
					v1.add(row);
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
			  
	    dtm.setDataVector(v1,head);	
		jt.updateUI();
		jt.repaint();	
		
//		sql="delete from STUDENT where StuNO="+Integer.parseInt(stuno);
		
	    try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("delete from STUDENT where StuNO="+Integer.parseInt(stuno));
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
	public void updateStudent(){
		String str[]=new String[7];
		int row = jt.getSelectedRow();
		if(row>=0){//select the row 
			for(int i=0;i<7;i++){str[i]=jt.getValueAt(row,i).toString();}
						
//			sql="update STUDENT set StuName='"+str[1]+"',StuSex='"+str[2]+"',Class='"
//			     +str[3]+"',Department='"+str[4]+"',Permitted='"+str[5]+"',Password='"+str[6]
//			     +"' where StuNO="+Integer.parseInt(str[0].trim());
			
		    try {
		    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				  ct=DriverManager.getConnection("jdbc:odbc:sql server");
				  ps=ct.prepareStatement("update STUDENT set StuName='"+str[1]+"',StuSex='"+str[2]+"',Class='"+str[3]+"',Department='"+str[4]+"',Password='"+str[6]+"',Permitted='"+str[5]+"' where StuNO="+Integer.parseInt(str[0].trim()));
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
				  
			
			JOptionPane.showMessageDialog(this,"Modify Successfully!",
			                                   "Info",JOptionPane.INFORMATION_MESSAGE);
					
			
			return;
		}
		if(row==-1){//tip
			JOptionPane.showMessageDialog(this,"Please click 'Search', then choose the row and click 'Modify'",
			                               "Warning!",JOptionPane.INFORMATION_MESSAGE);
			return;
		}		
	}
	public void searchStudent(){
		if(jtxtArray[0].getText().equals("")){//
			JOptionPane.showMessageDialog(this,"Cannot enter empty information, please enter again!",
			                              "Info",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
       	
		
		
//		sql="select * from STUDENT where StuNO="+Integer.parseInt(jtxtArray[0].getText().trim());
		
		Vector v2=new Vector();
	    try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("select * from STUDENT where StuNO="+Integer.parseInt(jtxtArray[0].getText().trim()));
			  rs=ps.executeQuery();
			  
			  while(rs.next())
			  {
				  Vector row=new Vector();
					row.add(rs.getInt(1));
					row.add(rs.getString(2));
					row.add(rs.getString(4));
					row.add(rs.getString(5));
					row.add(rs.getString(6));
					row.add(rs.getString(8));
					row.add(rs.getString(9));
					v2.add(row);
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
			  
	    dtm.setDataVector(v2,head);	
		jt.updateUI();
		jt.repaint();	
        
  	
	}
}