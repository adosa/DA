package rutins;

import java.util.*;

//****************************************************
public class ParmArray extends TreeMap<String,Object> {
/**
	 * 
	 */
	private static final long serialVersionUID = -6266351765527314018L;
	//****************************************************
      public Object addParmElem(String name, Object o) {
      put(name,new ParmElem(name,o));
      return o;
      }//end add
//****************************************************
      public Object add(String name, Object o) {
      put(name,o);
      return o;
      }//end add
//****************************************************
      public Object get(Object k) {
      	Object Ret=null;
      	if(containsKey(k)){
      	  Ret=((ParmElem)super.get(k)).getValue();	
          }//end if
      return Ret;
      }//end ParmArray
//****************************************************
      public Object getPar(Object k) {
      	Object Ret=null;
      	if(containsKey(k)){
      	  Ret=((ParmElem)super.get(k)).getValue();	
          }//end if
        else {
          System.out.println("A paraméter "+k+" nem létezik !");}
      return Ret;
      }//end ParmArray

//****************************************************
      public String getDel(Object k) {
      	String Ret=null;
      	if(containsKey(k)){
      	  Ret=((ParmElem)super.get(k)).cDel;	
          }//end if
      return Ret;
      }//end ParmArray
//****************************************************      
      public boolean is(Object k) {
      	boolean Ret=false;
      	if(((Boolean)get(k)).booleanValue()) {Ret=true;}
      	return Ret;
      	}//end is
      }//end class
