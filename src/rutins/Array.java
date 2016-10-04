package rutins;
import java.util.*;
//****************************************************
public class Array extends ArrayList<Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8247609693940340936L;
	public Array bAdd(Object o){
      	     add(o);
      	     return this;
      	     }//end bAdd
      public Object elem(int pos) {
      	     if(pos>=length()){return null;}
      	     return get(pos);}
      public int length() {return size();};
      public String toString(){
      	     String Ret="[";
      	     String Del="";
      	     Class<? extends String> c=Ret.getClass();
      	     for(int i=0;i<length();i++){
      	     	if((elem(i)!=null) && (c==elem(i).getClass())) {
                   Ret=Ret+Del+Const.cStrBeg2+elem(i)+Const.cStrBeg2;}
                else {Ret=Ret+Del+elem(i);}
                Del=",";
      	        }//end for
      	     Ret=Ret+"]";
      	     return Ret;
      	     }//end toString;
      
      }//end class
