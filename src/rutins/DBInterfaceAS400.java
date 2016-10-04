package rutins;
import java.io.*;
import java.sql.*;
import com.ibm.as400.access.*;

public class DBInterfaceAS400 extends DBInterface{
      final static String CDriver="com.ibm.as400.access.AS400JDBCDriver";
      final static String CDatabase="jdbc:as400://10.41.4.1/KNZURDB";
      final static String CUser = "budziad";
      final static String CPassword = "koskakos53";
      final static String CLib="BUDZIAD11";
//****************************************************      
      public DBInterfaceAS400(String aUser, String aPassword, String aDatabase, String aDriver,String aDBLib) {
        DBUser = aUser;
      	DBPassword = aPassword;
      	DBDriver = aDriver;
        DBDatabase = aDatabase;
        DBLib=aDBLib;
        DBILib=DBLib;
      	} // end DBInterface
//****************************************************
      public DBInterfaceAS400(String aUser, String aPassword, String aDatabase) {
        this(aUser,aPassword,aDatabase,CDriver,CLib);
        } // end DBInterface
//****************************************************
      public DBInterfaceAS400(String aUser, String aPassword) {
      	this(aUser,aPassword,CDatabase,CDriver,CLib);
        } // end DBInterface
//****************************************************
      public DBInterfaceAS400() {
      	this(CUser,CPassword,CDatabase,CDriver,CLib);
        } // end DBInterface
//****************************************************      
      public boolean Connect(boolean isNew) {
      	Connection Conn=null;
      	if ( !DBActive || isNew) {
      	   try {new UserMsg("Connecting AS400: "+DBDatabase);
      	        Class.forName(DBDriver);
                Conn = DriverManager.getConnection(DBDatabase, DBUser, DBPassword);
                Cons.add(new DBConnection(Conn));
                DBActive = true;  
                } //endtry
           catch(Exception e){new ErrorMsg(e);} //end catch
           
           } //end if
         return DBActive;
        } //end Connect

//****************************************************
      public String TableName(String name){
      	return DBLib+"."+Convert.del_(name);}
//****************************************************
      public String IndexName(String name){
      	return DBILib+"."+Convert.del_(name);}
//****************************************************
      public String CreateIndexDBFStru(String inds){
         String Ret="(";
         String cDel="";
         String s;
         String AD;
         int pos,pos1,pos2;
         
         while((pos=inds.indexOf(Const.cPlus))>=0){
             s=inds.substring(0,pos).trim();
             AD=" ASC";
             if(s.toUpperCase().indexOf("DESCEND")>=0){AD=" DESC";}
             pos2=s.indexOf(")");
             pos1=s.lastIndexOf("(");
             if(pos1>=0 && pos2>=0 && pos1<pos2){s=s.substring(pos1+1,pos2);}
             pos1=s.indexOf(",");
             if(pos1>=0){s=s.substring(0,pos1).trim();}
             Ret=Ret+cDel+Convert.del_(s)+ AD;
             cDel=",";
             inds=inds.substring(pos+1);
             }//end while
          s=inds.trim();
          AD=" ASC";
          if(s.toUpperCase().indexOf("DESCEND")>=0){AD=" DESC";}
          pos2=s.indexOf(")");
          pos1=s.lastIndexOf("(");
          if(pos1>=0 && pos2>=0 && pos1<pos2){s=s.substring(pos1+1,pos2);}
          pos1=s.indexOf(",");
          if(pos1>=0){s=s.substring(0,pos1).trim();}
             
         Ret=Ret+cDel+Convert.del_(s)+ AD +")";
         return Ret;
        }//end freeConn 
//****************************************************
	} //end Class
