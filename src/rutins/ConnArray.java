package rutins;
import java.sql.*;
//****************************************************
public class ConnArray extends Array{
      /**
	 * 
	 */
	private static final long serialVersionUID = 4020940242378083603L;
	public DBConnection connElem(int pos) {return (DBConnection)elem(pos);}
//****************************************************
      public DBConnection findFreeConn() {
      	     DBConnection Ret=null;
      	     for(int i=0;(i<length())&&(Ret==null);i++){
      	     	DBConnection c=connElem(i);
      	     	if(!c.getlock()&&c.lock()){Ret=c;}
      	     	}//end for
      	     return Ret;
      	     }//end findConn
//****************************************************
      public boolean freeConn(Connection ac) {
      	     boolean Ret=false;
      	     for(int i=0;(i<length())&&(!Ret);i++){
      	     	DBConnection c=connElem(i);
      	     	if(c.getConn()==ac){Ret=c.unlock();}
      	     	}//end for
             return Ret;
      	     }//end findConn
//****************************************************
      public void closeAll() {
      	     for(int i=0;i<length();i++){
      	     	try {connElem(i).getConn().close();} catch(Exception e){}
      	     	}//end for
      	     }//end closeAll

      }//end class
