import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class Manager extends JPanel implements ActionListener
{
	JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JPanel jpt=new JPanel();
	String[] str1=new String[3];
	public static String mgNo;

	
	PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;
    
	private JLabel[] jlArray=new JLabel[]
	{
		new JLabel("ID"),
		new JLabel("Authority Level"),
	    new JLabel("Password")   
	};
	private JTextField[] jtxtArray=new JTextField[]
	{
		new JTextField(),
		new JTextField(),
		new JTextField()
	};
	private JButton[] jbArray=new JButton[]
	{
		new JButton("Add Administrator"),
		new JButton("Delete Administrator"),
		new JButton("Modify Administrator Info"),
		new JButton("Search Administrator Info")
	};
	
	Vector<String> head=new Vector<String>();
	{
		head.add("Administrator ID");
		head.add("Authority Level");	
		head.add("Password");	
	}
	
	Vector<Vector> data=new Vector<Vector>();
    
    DefaultTableModel dtm=new DefaultTableModel(data,head);
    
	JTable jt=new JTable(dtm);
	
	JScrollPane jspn=new JScrollPane(jt);
	public Manager(String mgNo)
	{
		this.setLayout(new GridLayout(1,1));
	    this.mgNo=mgNo;
		
		jpt.setLayout(null);
		
		jsp.setDividerLocation(115);
		
		jsp.setDividerSize(4);
		jsp.setTopComponent(jpt);
		jsp.setBottomComponent(jspn);
		for(int i=0;i<3;i++)
		{
			jpt.add(jlArray[i]);
			jpt.add(jtxtArray[i]);	
		    jlArray[i].setBounds(25+i*250,20,100,25);
		    jtxtArray[i].setBounds(115+i*250,20,150,25);
		    jtxtArray[i].addActionListener(this);
		}
		this.add(jsp);
		
    	jsp.setBottomComponent(jspn);
    	
		for(int i=0;i<4;i++)
		{
			jpt.add(jbArray[i]);
			jbArray[i].setBounds(25+220*i,70,200,25);
			jbArray[i].addActionListener(this);
		}
	    
		this.setBounds(5,5,600,500);
		this.setVisible(true);
	}
	
	public void setFlag(boolean b)
	{
		jbArray[0].setEnabled(b);
		jbArray[1].setEnabled(b);		
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==jtxtArray[0]){jtxtArray[1].requestFocus();}		
		if(e.getSource()==jtxtArray[1]){jtxtArray[2].requestFocus();}
//		sql="select permitted from manager where mgNo='"+mgNo+"'";
		
		try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("select permitted from manager where mgNo='"+mgNo+"'");
			  rs=ps.executeQuery();
			  
			  String string="";
			  while(rs.next())
			  {
				  string=rs.getString(1);
			  }
		
		try{
			int p=0;
			if(string.equals("1")){   p++;
				String jtxt=jtxtArray[0].getText().trim();
				if(jtxt.equals("")){
					JOptionPane.showMessageDialog(this,	"Please enter ID number!",
						        "Information",JOptionPane.INFORMATION_MESSAGE);
				    return;
				}
				if(e.getSource()==jbArray[0]){//add
					this.insertManager();
				}
				if(e.getSource()==jbArray[1]){//delete
					this.deleteManager();
				}
				if(e.getSource()==jbArray[2]){//update
					this.updateManager();
				}
				if(e.getSource()==jbArray[3]){//search
					this.selectManager();
				}
			}
			if(p==0){ //normal admin, cannot change admin's information
				jtxtArray[0].requestDefaultFocus();
				String jtxt=jtxtArray[0].getText().trim();
				if(jtxt.equals("")){
					JOptionPane.showMessageDialog(this,	"Please enter ID number!",
						        "Information",JOptionPane.INFORMATION_MESSAGE);
				    return;
				}
				else if(jtxt.equals(mgNo)){//admin ID
					if(e.getSource()==jbArray[3]){//manager1 function
						this.manager1();
					}
					if(e.getSource()==jbArray[2]){//update
						String str[]=new String[3];
						int row = jt.getSelectedRow();
						if(row>=0){
							for(int i=0;i<3;i++){
								str[i]=jt.getValueAt(row,i).toString();
							}
//							sql="update manager set password='"
//							     +str[2]+"' where mgNo="+Integer.parseInt(str[0].trim());

							
							  ps=ct.prepareStatement("update manager set password='"
									     +str[2]+"' where mgNo="+Integer.parseInt(str[0].trim()));
							  rs=ps.executeQuery();
							
							int i=0;
							if(i==1){//
								JOptionPane.showMessageDialog(this,"Modify successfully!",
								                  "Info",JOptionPane.INFORMATION_MESSAGE);
								return;											
							}
							else{//tip
								JOptionPane.showMessageDialog(this,"Sorry, wrong operation, please try again!",
	                                   "Warning!",JOptionPane.INFORMATION_MESSAGE);
								return;											
							}
						}
						else{//tip
							JOptionPane.showMessageDialog(this,"Sorry, wrong operation, please try again!",
	                               "Warning!",JOptionPane.INFORMATION_MESSAGE);
							return;								
						}	
					}
				}
				else{//tip
					JOptionPane.showMessageDialog(this,	"Sorry, you cannot access other administrator's information!",
				        "Info",JOptionPane.INFORMATION_MESSAGE);
				    return;					
				}
			}
		}
		catch(Exception ex){ex.printStackTrace();}
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
	public void insertManager(){
		for(int i=0;i<3;i++){
		    str1[i]=jtxtArray[i].getText().trim();		
		}
		if(!str1[0].equals("")&&!str1[1].equals("")&&!str1[2].equals("")){
			if(!str1[0].matches("[0-9][0-9][0-9][0-9]")){
				JOptionPane.showMessageDialog(this,	"Administrator ID only can be number!",
				        "Info",JOptionPane.INFORMATION_MESSAGE);
				return;							
			}
			int temp = Integer.parseInt(str1[0]);			
//			sql="insert into manager(mgNo,Permitted,password) values("
//			          +temp+",'"+str1[1]+"','"+str1[2]+"')";

			
			try {
		    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				  ct=DriverManager.getConnection("jdbc:odbc:sql server");
				  ps=ct.prepareStatement("insert into manager(mgNo,Permitted,password) values("
				          +temp+",'"+str1[1]+"','"+str1[2]+"')");
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
			
			int j=0;
			if(j==0){
				JOptionPane.showMessageDialog(this,	"Adding successfully!",
				        "Info",JOptionPane.INFORMATION_MESSAGE);
				return;				
			}
			Vector<String> v=new Vector<String>();
		    for(int i=0;i<=2;i++){
				v.add(str1[i]);
				if(i<3){jtxtArray[i].setText("");}	
		    }
		    data.add(v);
			dtm.setDataVector(data,head);
			jt.updateUI();
			jt.repaint();
			return;
		}
		else{//tip
			JOptionPane.showMessageDialog(this,	"The information cannot be empty!",
						        "Info",JOptionPane.INFORMATION_MESSAGE);
						        return;	
		}
		
	}
	public void deleteManager(){
		String mgNo=jtxtArray[0].getText().trim();
		if(mgNo.equals("")){
			JOptionPane.showMessageDialog(this,	"ID cannot be empty",
						        "Info",JOptionPane.INFORMATION_MESSAGE);
			return;			
		}
//		sql="select permitted from manager where mgNo="+Integer.parseInt(mgNo);
		
		try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("select permitted from manager where mgNo="+Integer.parseInt(mgNo));
			  rs=ps.executeQuery();
			  
			  String str="";
		
		  while(rs.next())
		  {
			  str=rs.getString(1);
		  }
		
		try{
			if(str.equals("1")){//tip
	        	JOptionPane.showMessageDialog(this,	"Cannot delete super administrator's information!",
							        "Warnning",JOptionPane.INFORMATION_MESSAGE);
	        	return;	
			}
			else{//delete
//				sql="delete from manager where mgNo="+Integer.parseInt(mgNo);

		    	
		    	ps=ct.prepareStatement("delete from manager where mgNo="+Integer.parseInt(mgNo));
				rs=ps.executeQuery();
		    	

			}
		}
	    catch(Exception e){e.printStackTrace();}	
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
		
    	JOptionPane.showMessageDialog(this,	"Delete successfully!",
		        "Info",JOptionPane.INFORMATION_MESSAGE);
	}
	public void updateManager(){
		String str[]=new String[3];
		int row = jt.getSelectedRow();
		if(row>=0){
			for(int i=0;i<3;i++){//get select tow
				str[i]=jt.getValueAt(row,i).toString();
			}
//			sql="update manager set mgNo='"+str[0]+"',permitted='"+str[1]
//			   +"',password='"+str[2]+"' where mgNo="+Integer.parseInt(str[0].trim());

			
			try {
		    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				  ct=DriverManager.getConnection("jdbc:odbc:sql server");
				  ps=ct.prepareStatement("update manager set mgNo='"+str[0]+"',permitted='"+str[1]
						   +"',password='"+str[2]+"' where mgNo="+Integer.parseInt(str[0].trim()));
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
			
			JOptionPane.showMessageDialog(this,"Modify successfully",
			                      "Info",JOptionPane.INFORMATION_MESSAGE);
			return;								
		}
		if(row==-1){//tip
			JOptionPane.showMessageDialog(this,
			"Please click 'Search', then choose the row of record and click 'Modify'",
			                         "Warning!!",JOptionPane.INFORMATION_MESSAGE);
			return;
		}		
	}
	public void selectManager(){
		if(jtxtArray[0].getText().equals("")){//tip
			JOptionPane.showMessageDialog(this,"Cannot be empty, please try to enter again!",
			                              "Info",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int q=Integer.parseInt(jtxtArray[0].getText().trim());
		
//	   	sql="select * from manager where mgNo="+q;
		
		Vector v_search=new Vector();
		try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("select * from manager where mgNo="+q);
			  rs=ps.executeQuery();
			  
			  while(rs.next())
			  {
				  Vector row=new Vector();
					row.add(rs.getInt(1));
					row.add(rs.getString(2));
					row.add(rs.getString(3));

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
		//this.table();   	
	}
	
    public void manager1(){
//        sql="select * from manager where mgNo="+Integer.parseInt(jtxtArray[0].getText().trim());

    	
		try {
	    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			  ct=DriverManager.getConnection("jdbc:odbc:sql server");
			  ps=ct.prepareStatement("select * from manager where mgNo="+Integer.parseInt(jtxtArray[0].getText().trim()));
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
    	
    	
		this.table(); 	    	
    }
    public void table(){
    	try{
		     int k=0;
			 Vector<Vector> vtemp = new Vector<Vector>();
			 
			 if(k==0){//if cannot find the ID
			 	 JOptionPane.showMessageDialog(this,"Cannot find the information,please check the ID number again!",
				                 "Information",JOptionPane.INFORMATION_MESSAGE);
			 }
			dtm.setDataVector(vtemp,head);
			jt.updateUI();
			jt.repaint();				 	
		}
		catch(Exception e){e.printStackTrace();}	
    }
    public static void main(String[]args)
    {
    	new Manager(mgNo);
    }
}