import java.sql.*;
import java.util.Vector;
public class DataBase 
{
    Vector attribute,record;
	PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;
    
	public DataBase()
	{
	}
    
	
	
	public static void main(String[] args)
	{

	    new DataBase();
	    
	}
}

