package rutins;
import java.util.*;

//****************************************************
public class ParmArrayFile extends Object {
       ParmArray p=null;
       public ParmArrayFile(String FileName,String Block){
       	      ParFileReader f = new ParFileReader(FileName);
       	      f.getAllBlock();
              p = new ParmArray();
              f.executeBlock(f.getBlock(Block),p);
       	      }//end ParmArrayFile      
//****************************************************
      public Object get(Object k) {return p.get(k);}//end get
//****************************************************
      public Object getParmArray() {return p;}//end getParmArray      
//****************************************************      
      public boolean is(Object k) {return p.is(k);}//end is
//****************************************************      
      public String toString(){
             String s=new String();      
             Iterator<String> it = p.keySet().iterator();
             while (it.hasNext()){
             Object o=it.next();
             s=s+o+p.getDel(o)+p.get(o)+"\n";}
             return s;
             }//end toString
}//end Class
