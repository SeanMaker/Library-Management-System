import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;
public class Root extends JFrame
{
	
	DefaultMutableTreeNode[]  dmtn=
			{new DefaultMutableTreeNode(new NodeValue("Library Management System")),
			 new DefaultMutableTreeNode(new NodeValue("Student/User Management")),
			 new DefaultMutableTreeNode(new NodeValue("Books Management")),
			 new DefaultMutableTreeNode(new NodeValue("Search Book")),
			 new DefaultMutableTreeNode(new NodeValue("Borrow Book")),
			 new DefaultMutableTreeNode(new NodeValue("Return Book")),
			 new DefaultMutableTreeNode(new NodeValue("Pay Fine")),
			 new DefaultMutableTreeNode(new NodeValue("Administrator Management")),
			 new DefaultMutableTreeNode(new NodeValue("Exit"))};    
    DefaultTreeModel dtm=new DefaultTreeModel(dmtn[0]);//create tree model, assign root node as "Library Management System"    
    JTree jt=new JTree(dtm);//create JTree object 
    JScrollPane jsp=new JScrollPane(jt);//create ScrollPane for JTree    
    private JSplitPane jsplr=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true);//create SplitPane  
    private JPanel jp=new JPanel();//create JPanel object 
    Image image=new ImageIcon("images/bg.jpg").getImage();
    ImageIcon ii = new ImageIcon(image);    
	private JLabel jlRoot=new JLabel(ii);
    private Manager mg;//administrator for login
    String mgNo;//Administrator ID
	CardLayout cl=new CardLayout();//create cardlayout
    public Root(String mgNo)
    {
    	this.mgNo=mgNo;//get admin ID
    	mg=new Manager(mgNo);//create admin UI
   		this.setManager();//set admin authority
       	this.initJp();//initialize cardlayout panel
    	this.addTreeListener();//addTreeListener
    	for(int i=1;i<9;i++)
    	{//add child nodes into root node    		
    		dtm.insertNodeInto(dmtn[i],dmtn[0],i-1);	
    	}    	
		jt.setEditable(false);//set the nodes editable		
		this.add(jsplr);//add the jsplr into window		
		jsplr.setLeftComponent(jt);//add the tree into left of window		
		jp.setBounds(200,50,600,500);//set the size and location for jp 
		jsplr.setRightComponent(jp);// add jp into the right of window        
        jsplr.setDividerLocation(220);//set divider location        
        jsplr.setDividerSize(4);//set divider size
        jlRoot.setFont(new Font("Courier",Font.PLAIN,30));
		jlRoot.setHorizontalAlignment(JLabel.CENTER);
		jlRoot.setVerticalAlignment(JLabel.CENTER);
		//set window's colse, title, size, location, visible
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
 		Image image=new ImageIcon("images/icon3.png").getImage();  
 		this.setIconImage(image);
		this.setTitle("Library Management System");
		//window first show up: size, location
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=500;//window width
		int h=400;//height
		this.setBounds(centerX-w/2,centerY-h/2-100,w,h);//set window show up location:center		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);//window full screen
		this.setVisible(true);	
		jt.setShowsRootHandles(true);
    }
    public void setManager()
	{
//		String sql="select permitted from manager where mgNo='"+mgNo+"'";
//		DataBase db=new DataBase();
	}
	public void initJp()
	{
		jp.setLayout(cl);//set layout as cardlayout
		jp.add(jlRoot,"root");//add root node information
		jp.add(new Student(),"stu");//add student management UI
		jp.add(new BookManage(),"bm");//add book management UI
		jp.add(new SearchBook(),"sb");
		jp.add(new BorrowBook(),"bb");
		jp.add(new ReturnBook(),"rb");
		jp.add(this.mg,"Manager");
		jp.add(new ExceedTime(),"et");
	}
	public void addTreeListener()
	{
		jt.addTreeSelectionListener(new TreeSelectionListener()
			{
				public void valueChanged(TreeSelectionEvent e)
				{
					DefaultMutableTreeNode cdmtn=//get selected node object
								(DefaultMutableTreeNode)e.getPath().getLastPathComponent();
					NodeValue cnv=(NodeValue)cdmtn.getUserObject();
					if(cnv.value.equals("Library Management System"))
					{//show root node information
						cl.show(jp,"root");
					}
	                if(cnv.value.equals("Student/User Management"))
					{//Show student management UI								
						cl.show(jp,"stu");
					}
					else if(cnv.value.equals("Books Management"))
					{//show book management UI
					    cl.show(jp,"bm");	
					}	
					if(cnv.value.equals("Search Book"))
					{//show search book UI													
						cl.show(jp,"sb");
					}
					else if(cnv.value.equals("Borrow Book"))	
					{//show borrow book UI
						cl.show(jp,"bb");
					}
					else if(cnv.value.equals("Return Book"))	
					{//Show return book UI
						cl.show(jp,"rb");
					}
					else if(cnv.value.equals("Pay Fine"))	
					{//show pay fine UI
						cl.show(jp,"et");
					}
					else if(cnv.value.equals("Administrator Management"))	
					{//show admin management UI
						cl.show(jp,"Manager");
					}
					else if(cnv.value.equals("Exit"))
					{//show exit UI
						int i=JOptionPane.showConfirmDialog(Root.this,"Exit the system?",
																"Info",JOptionPane.YES_NO_OPTION);
						if(i==JOptionPane.YES_OPTION)
						{//exit system
							System.exit(0);
						}	
					}									
				}
			});
	}
    public static void main(String args[]){}
}