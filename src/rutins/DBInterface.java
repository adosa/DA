package rutins;

import java.sql.*;


public class DBInterface {
      final static String CDriver="org.postgresql.Driver";
      final static String CDatabase="jdbc:postgresql://192.168.0.1/BUDZIAD11";
      //final static String CDatabase="jdbc:postgresql://10.47.48.1/BUDZIAD11";
      final static String CUser = "adosa";
      final static String CPassword = "koskakos";
      final static String CLib="BUDZIAD11";
      ConnArray Cons=new ConnArray();
      String DBDriver;
      String DBDatabase;
      String DBUser;
      String DBPassword;
      String DBLib;
      String DBILib;
      boolean DBActive=false;
//****************************************************      
      public DBInterface(String aUser, String aPassword, String aDatabase, String aDriver,String aDBLib) {
        DBUser = aUser;
      	DBPassword = aPassword;
      	DBDriver = aDriver;
        DBDatabase = aDatabase;
        DBLib=aDBLib;
        DBILib=DBLib;
      	} // end DBInterface
//****************************************************
      public DBInterface(String aUser, String aPassword, String aDatabase) {
        this(aUser,aPassword,aDatabase,CDriver,CLib);
        } // end DBInterface
//****************************************************
      public DBInterface(String aUser, String aPassword) {
      	this(aUser,aPassword,CDatabase,CDriver,CLib);
        } // end DBInterface
//****************************************************
      public DBInterface() {
      	this(CUser,CPassword,CDatabase,CDriver,CLib);
        } // end DBInterface
//****************************************************      
      public boolean Connect() {return Connect(false);}
//****************************************************      
      public boolean Connect(boolean isNew) {
      	Connection Conn=null;
      	if ( !DBActive || isNew) {
      	   try {new UserMsg("Connecting "+DBDatabase);
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
      public Connection getConn(){return getConn(true);}
//****************************************************
      public Connection getConn(boolean isNew){
         Connection Ret=null;
         DBConnection Conn=null;
         if (!DBActive ) {Connect();}
         Conn=Cons.findFreeConn();
         if(Conn!=null) {Ret=Conn.getConn();}
         return Ret;    
        }//end GetConn 
//****************************************************
      public void freeConn(Connection c){
         Cons.freeConn(c);
        }//end freeConn 
//****************************************************
      public String CreateDBFStru(Array a){
         String Ret="(";
         String cDel="";
         String s,t;
         Integer l,d;
         boolean h=false;
         for(int i=0;i<a.length();i++){
             t=(String)PENTALLDBF.getArrayField(a,i,1);
             l=(Integer)PENTALLDBF.getArrayField(a,i,2);
             d=(Integer)PENTALLDBF.getArrayField(a,i,3);
             h=false;s="";
             for(int j=0;(j<Const.cDBFTypes.length)&&!h;j++){
             if(t.equals(Const.cDBFTypes[j])){
             	s=s+Const.cAS400Types[j];
             	if(Const.cAS400Types1[j].equals("")){s=s+"("+l+")";}
             	if(Const.cAS400Types1[j].equals(",")){s=s+"("+l+","+d+")";}             	
             	else {s=s+Const.cAS400Types1[j];};
             	h=true;
             	}//end if
             }//end for
             Ret=Ret+cDel+Convert.del_((String)PENTALLDBF.getArrayFieldName(a,i))+" "+s+" " ;
             cDel=",";
             }//end for
         Ret=Ret+")";
         return Ret;
        }//end freeConn 

//****************************************************
      public String TableName(String name){
      	return Convert.del_(name);}
//****************************************************
      public String IndexName(String name){
      	return Convert.del_(name);}
//****************************************************
      public boolean DropIndexDBF(Connection c,String name,Array ind,String iKod){
          boolean Ret=true;
          String s=null;
          int counter=0;
          try{
          Statement st = c.createStatement();
          for(int i=0;i<ind.length();i++){
          
          if(((String)PENTALLDBF.getArrayField(ind,i,0)).equals(iKod)){
          s="DROP INDEX "+IndexName(name.trim()+'I'+(new Integer(counter)).toString().trim());
          new UserMsg("SQL: "+s);
          st.executeUpdate(s);
          counter++;
          }//end if
          }//end for
          } catch(Exception e) {new ErrorMsg((new Array()).bAdd(s).bAdd(e));Ret=false;}
        return Ret;
        }//end freeConn 

//****************************************************
      public boolean DropTableDBF(Connection c,String name){
          boolean Ret=true;
          String s=null;
          try{
          Statement st = c.createStatement();
          s="DROP TABLE "+TableName(name);
          new UserMsg("SQL: "+s);
          st.executeUpdate(s);
          } catch(Exception e) {new ErrorMsg((new Array()).bAdd(s).bAdd(e));Ret=false;}
        return Ret;
        }//end freeConn 

//****************************************************
      public boolean CreateTableDBF(Connection c,String name,Array a){
          boolean Ret=true;
          String s=null;
          try{
          Statement st = c.createStatement();
          s="CREATE TABLE "+TableName(name)+CreateDBFStru(a);
          new UserMsg("SQL: "+s);
          st.executeUpdate(s);
          } catch(Exception e) {new ErrorMsg((new Array()).bAdd(s).bAdd(e));Ret=false;}
        return Ret;
        }//end CreateTableDBF 
//****************************************************
      public String CreateIndexDBFStru(String inds){
         String Ret="(";
         String cDel="";
         String s;
         @SuppressWarnings("unused")
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
             Ret=Ret+cDel+Convert.del_(s);//+ AD;
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
             
         Ret=Ret+cDel+Convert.del_(s)/*+ AD*/+")";
         return Ret;
        }//end freeConn 

//****************************************************
      public boolean CreateIndexDBF(Connection c,String name,Array ind,String iKod){
          boolean Ret=true;
          String s=null;
          int counter=0;
          try{
          Statement st = c.createStatement();
          for(int i=0;i<ind.length();i++){
          
          if(((String)PENTALLDBF.getArrayField(ind,i,0)).equals(iKod)){
          s="CREATE INDEX "+IndexName(name.trim()+'I'+(new Integer(counter)).toString().trim())+" ON "+TableName(name)+" "+CreateIndexDBFStru((String)PENTALLDBF.getArrayField(ind,i,1));
          new UserMsg("SQL: "+s);
          st.executeUpdate(s);
          counter++;
          }//end if
          }//end for
          } catch(Exception e) {new ErrorMsg((new Array()).bAdd(s).bAdd(e));Ret=false;}
        return Ret;
        }//end CreateTableDBF 
//****************************************************
      public String InsertDBFStru(Array a){
         String Ret="(";
         String cDel="";
         //boolean h=false;
         for(int i=0;i<a.length();i++){
             Ret=Ret+cDel+"?" ;
             cDel=",";
             }//end for
         Ret=Ret+")";
         return Ret;
        }//end freeConn 
//****************************************************
      public Object ConvertValues(Object o,String t){
      	boolean h=false;
      	if(o==null){
              for(int j=0;(j<Const.cDBFTypes.length)&&!h;j++){
                 if(t.equals(Const.cDBFTypes[j])){
             	   o=Const.cAS400NullValues[j];
             	   h=true;
             	 }//end if
              }//end for
        }//end if
        if(o.getClass()==(new Boolean(true)).getClass()){o=((Boolean)o).booleanValue() ? "1" : "0";}
        return o;
      	}//end ConvertValues
//****************************************************
      public boolean InsertDBFCrtValues(PreparedStatement st,Array a,Array v,Array r){
         boolean Ret=true;
         String t;
         for(int i=0;i<a.length();i++){
      	    Object o=v.elem(i);
      	    t=(String)PENTALLDBF.getArrayField(a,i,1);
      	    o=ConvertValues(o,t);  
            r.add(o);
            try{st.setObject(i+1,o);}catch(Exception e) {new ErrorMsg((new Array()).bAdd(o).bAdd(e));Ret=Ret && false;}
      	 }//end for	
      return Ret;
      }//end InsertDBFCrtValues
//****************************************************
      public boolean InsertDBF(Connection c,String name,Array a,Array v){
          boolean Ret=true;
          String s=null;
          Array r=new Array();
          if(v==null) {return Ret;}
          try{
          s="INSERT INTO "+TableName(name)+" VALUES"+InsertDBFStru(a);
          new UserMsg("SQL: "+s);
          PreparedStatement st = c.prepareStatement(s);
          for(int i=0;i<v.length();i++){
              InsertDBFCrtValues(st,a,(Array)((CreElem)v.elem(i)).getValue(),r);
          st.executeUpdate();
          }//end for
          } catch(Exception e) {new ErrorMsg((new Array()).bAdd(s).bAdd(e).bAdd(r));Ret=false;}
        return Ret;
        }//end CreateTableDBF 

//****************************************************
	} //end Class

