package rutins;

import java.sql.*;
//import com.ibm.as400.access.*;
//****************************************************
public class DBConnection{
      Connection Conn=null;
      boolean locked=false;
      public DBConnection(Connection c){Conn=c;};
//****************************************************
      public synchronized boolean lock(){
      	while(locked) {try{wait();}catch(InterruptedException e){}}
      	locked=true;
      	notifyAll();
      	return true;
      }//end lock
//****************************************************      
      public synchronized boolean unlock(){
      	while(locked) {try{wait();}catch(InterruptedException e){}}
      	locked=false;
      	notifyAll();
      	return true;
      }//end unlock
//****************************************************      
      public boolean getlock(){return locked;}
//****************************************************      
      public Connection getConn(){return Conn;}
      }//end class
