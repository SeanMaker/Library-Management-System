import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;
import java.net.*;
public class StudentSystem extends JFrame implements ActionListener{
	DefaultMutableTreeNode[] dmtn={//create nodes array
		new DefaultMutableTreeNode(new NodeValue("Student Management System")),
	    new DefaultMutableTreeNode(new NodeValue("Search books")),
	    new DefaultMutableTreeNode(new NodeValue("Reserve books")),
	    new DefaultMutableTreeNode(new NodeValue("Report book loss")),
	    new DefaultMutableTreeNode(new NodeValue("Exit"))
	};
    DefaultTreeModel dtm=new DefaultTreeModel(dmtn[0]);
    JTree jt=new JTree(dtm);    
    JScrollPane jsp=new JScrollPane(jt);      
    private JSplitPane jsplr=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true); 
    Image image=new ImageIcon("images/bg.jpg").getImage();
    ImageIcon ii = new ImageIcon(image);    
    private JLabel jlRoot=new JLabel(ii);
    private JPanel jp=new JPanel(); 
	CardLayout cl=new CardLayout();	
	public StudentSystem(){
       	this.initJp();
    	jt.addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent e){
				DefaultMutableTreeNode cdmtn=
					(DefaultMutableTreeNode)e.getPath().getLastPathComponent();
				NodeValue cnv=(NodeValue)cdmtn.getUserObject();	
				if(cnv.value.equals("Student Management System")){
					cl.show(jp,"root");//show main UI
				}
				if(cnv.value.equals("Search books")){													
					cl.show(jp,"ts");//show search book UI
				}
				else if(cnv.value.equals("Reserve books")){
					cl.show(jp,"OrderBook");//show reserve book UI
				}
				else if(cnv.value.equals("Report book loss")){
					cl.show(jp,"GuaShi");//show report loss UI
				}
				else if(cnv.value.equals("Exit")){//exit
					int i=JOptionPane.showConfirmDialog(StudentSystem.this,
									"Exit the system?","Info",JOptionPane.YES_NO_OPTION);
					if(i==JOptionPane.YES_OPTION){System.exit(0);}						
				}									
			}
		});
    	for(int i=1;i<dmtn.length;i++){
    		dtm.insertNodeInto(dmtn[i],dmtn[0],i-1);	
    	}
		jt.setEditable(false);
		this.add(jsplr);
		jsplr.setLeftComponent(jt);
		
		jp.setBounds(200,50,300,400);
		jsplr.setRightComponent(jp);
        jsplr.setDividerLocation(250);
        jsplr.setDividerSize(4); 
        jlRoot.setFont(new Font("Courier",Font.PLAIN,30));
		jlRoot.setHorizontalAlignment(JLabel.CENTER);
		jlRoot.setVerticalAlignment(JLabel.CENTER);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
 		Image image=new ImageIcon("images/icon3.png").getImage();
 		this.setIconImage(image);
		this.setTitle("Student Management System");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=500;
		int h=400;
		this.setBounds(centerX-w/2,centerY-h/2-100,w,h);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		jt.setShowsRootHandles(true);
    }
    public void initJp(){
    	jp.setLayout(cl);
    	jp.add(jlRoot,"root");
    	jp.add(new SearchBook(),"ts");
    	jp.add(new OrderBook(),"OrderBook");
    	jp.add(new GuaShi(),"GuaShi");
    }
    public void actionPerformed(ActionEvent e){}
	public static void main(String[]args){new StudentSystem();}
}
class NodeValue{
	String value;
	public NodeValue(String value){
		this.value=value;
	}
	public String getValue(){
		return this.value;
	}
	public String toString(){
		return value;
	}
}