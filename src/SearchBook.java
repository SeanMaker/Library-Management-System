import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
public class SearchBook extends JPanel implements ActionListener{
	int flag;
	String sql,sql_2;
	DataBase db;
	
	PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;
	
   
    private JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true);
	private JPanel jpt=new JPanel();
	private JPanel jpb=new JPanel();
	
	private String[] str={"Book ID","Book Name","Press","Author"};
	private JComboBox jcb=new JComboBox(str);
	private JButton jb=new JButton("Check");
	private JLabel[] jlArray=new JLabel[]{
		new JLabel("          Name"),
		new JLabel("       Author"),
		new JLabel("Publisher")
	};	
	private JTextField[] jtxtArray=new JTextField[]{
		new JTextField(),new JTextField(),
	    new JTextField(),new JTextField()
	};
	private JRadioButton[] jrbArray={
		new JRadioButton("Quick Search",true),
		new JRadioButton("Advanced")
	};	
	private ButtonGroup bg=new ButtonGroup();
	Vector<String> head = new Vector<String>();
	{//定义表头
		head.add("Book ID");head.add("Name");
		head.add("Author");head.add("Press");
		head.add("Published Date");head.add("Borrowed Y/N");
		head.add("Reserved Y/N");
	}
	Vector<Vector> data=new Vector<Vector>();
    DefaultTableModel dtm=new DefaultTableModel(data,head);
	JTable jt=new JTable(dtm); 
	JScrollPane jspn=new JScrollPane(jt);
	public SearchBook(){
		this.setLayout(new GridLayout(1,1));
		
		jpt.setLayout(null);
		jpb.setLayout(null);
		
		jpt.add(jcb);
		jcb.setBounds(160,20,150,20);	
	    jcb.addActionListener(this);
        
		jpt.add(jb);
		jb.setBounds(560,20,120,20);
		jb.addActionListener(this);
		for(int i=0;i<2;i++){
			jrbArray[i].setBounds(20,20+i*40,120,20);
			jpt.add(jrbArray[i]);
			jrbArray[i].addActionListener(this);
			bg.add(jrbArray[i]);
		}
		for(int i=0;i<3;i++){
			jlArray[i].setBounds(120+i*200,60,80,20);
			jtxtArray[i].setBounds(215+i*180,60,105,20);
			jpt.add(jtxtArray[i]);	
			jpt.add(jlArray[i]);
		}
		for(int i=0;i<3;i++){
			jtxtArray[i].setEditable(false);
		}
    	
		jtxtArray[3].setBounds(350,20,120,20);
		jpt.add(jtxtArray[3]);
        jsp.setTopComponent(jpt);
        jsp.setBottomComponent(jspn);
        jsp.setDividerSize(4);
       	this.add(jsp);
    	jsp.setDividerLocation(100);
		
        this.setBounds(3,10,600,400);
        this.setVisible(true);
	}
    
	public void actionPerformed(ActionEvent e){
        if(jrbArray[0].isSelected()){//"normal search"
        	jtxtArray[3].setEditable(true);
        	for(int i=0;i<jtxtArray.length-1;i++){
        		jtxtArray[i].setEditable(false);
        	}
        	if(jcb.getSelectedIndex()>=0&&jcb.getSelectedIndex()<4){
		    	jtxtArray[3].requestFocus();	    
			    if(e.getSource()==jb){//submit,ckeck button
					String str=jtxtArray[3].getText().trim();
					if(str.equals("")){
						JOptionPane.showMessageDialog(this,"Please input information!",
										"Information",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				    if(jcb.getSelectedIndex()==0){//search by bookno
						sql="select * from BOOK where BookNO='"+str+"'";
			        	jtxtArray[3].setText("");
					}
				    else if(jcb.getSelectedIndex()==1){//search by book name
						sql="select * from BOOK where BookName='"+str+"'";
			        	jtxtArray[3].setText("");
					}
					else if(jcb.getSelectedIndex()==2){//by press
						sql="select * from BOOK where Publishment='"+str+"'";
						jtxtArray[3].setText("");
					}
					else if(jcb.getSelectedIndex()==3){//by author
						sql="select * from BOOK where Author='"+str+"'";
						jtxtArray[3].setText("");
					}

				    
				    

				    
				    Vector<Vector> vtemp = new Vector<Vector>();
				    
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
								row.add(rs.getString(5));
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
        }
		if(jrbArray[1].isSelected()){//"advanced search"
			 jtxtArray[0].requestFocus(); //get focus
			 jtxtArray[3].setEditable(false);
           	 for(int i=0;i<jtxtArray.length-1;i++){//set the Texts is editable
        		jtxtArray[i].setEditable(true);
        	 }
			 if(e.getSource()==jb){//click "submit" button
			 	int bz=this.seniorSearch();
			 	if(bz!=0){return;}
 
				Vector<Vector> vtemp_2 = new Vector<Vector>();
				
				try {
			    	  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					  ct=DriverManager.getConnection("jdbc:odbc:sql server");
					  ps=ct.prepareStatement(sql_2);
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
							vtemp_2.add(row);
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
				
				dtm.setDataVector(vtemp_2,head);
				jt.updateUI();
				jt.repaint();							
			 } 	
		}    
	}
	public int seniorSearch(){
		int flag=0;
		String str0=jtxtArray[0].getText().trim();
		String str1=jtxtArray[1].getText().trim();
		String str2=jtxtArray[2].getText().trim();
		if(str0.equals("")&&str1.equals("")&&str2.equals("")){//text is empty
			JOptionPane.showMessageDialog(this,"Please input book information!",
								"Information",JOptionPane.INFORMATION_MESSAGE);
			flag++;
		}
		if(((!str0.equals(""))&&(str1.equals(""))&&(str2.equals("")))
		     ||((str0.equals(""))&&(!str1.equals(""))&&(str2.equals("")))
		     ||((str0.equals(""))&&(str1.equals(""))&&(!str2.equals("")))){
			JOptionPane.showMessageDialog(this,"please use simply search!",
								"Infomation",JOptionPane.INFORMATION_MESSAGE);
			flag++;
		}
        if((!str0.equals(""))&&(!str1.equals(""))&&(str2.equals(""))){//book name and author
			sql_2="select * from BOOK where BookName='"+str0+"' and Author='"+str1+"'";
			jtxtArray[0].setText("");jtxtArray[1].setText("");
		}
		if((!str0.equals(""))&&(str1.equals(""))&&(!str2.equals(""))){//bookname and publisher
			sql_2="select * from Book where BookName='"+str0+"' and Publishment='"+str2+"'";
			jtxtArray[0].setText("");jtxtArray[2].setText("");
		}
		if((str0.equals(""))&&(!str1.equals(""))&&(!str2.equals(""))){//author and publisher
			sql_2="select * from Book where Author='"+str1+"' and Publishment='"+str2+"'";
			jtxtArray[1].setText("");jtxtArray[2].setText("");
		}
		if((!str0.equals(""))&&(!str1.equals(""))&&(!str2.equals(""))){//bookname,author,publisher
			sql_2="select * from Book where BookName='"+str0
						+"' and Publishment='"+str2+"' and Author='"+str1+"'";
			jtxtArray[0].setText("");jtxtArray[1].setText("");jtxtArray[2].setText("");
		}
		return flag;
	}
}