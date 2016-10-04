package rutins;
import java.util.*;

public class UniDate extends Date{
       /**
	 * 
	 */
	private static final long serialVersionUID = -2371139065446823203L;

	public String toString(){
              return new Integer(getYear()+1900)+"."+new Integer(getMonth())+"."+new Integer(getDay());}//end Date    
       }//end class
       