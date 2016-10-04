package rutins;

//****************************************************
public class ParmElem extends Object {
      String oStr=null; // original string
      String Name=null;
      String cDel=null;
      String ValueStr=null;
      Object Value=null;
      String Message=null;
      ParmArray pt=null;
      boolean isActive = false;
      
      public ParmElem(){}
      public ParmElem(String name,Object o)  {Name=name;Value=o;ValueStr=o.toString();isActive=true;}
      public ParmElem(Object s,ParmArray apt) {this(s.toString(),apt);}
      public ParmElem(String s,ParmArray apt) {this(s,apt,true);}
      public ParmElem(String s,ParmArray apt,boolean isExc) {oStr=s;cDel=Const.cEq;pt=apt;execStr(isExc);}
//****************************************************      
      public void execStr(boolean conv){
             int pos;
             String s=oStr;
             UniParm sc=new UniParm((Object)pt);
             pos = s.lastIndexOf(Const.cRem);
             if(pos>=0){
             	Message = s.substring(pos+Const.cRem.length());
             	s=s.substring(0,pos);
             	}//endif
             pos = s.indexOf(cDel);
             if(pos>=0){
                Name = s.substring(0,pos).trim().toUpperCase();
                s = s.substring(pos+cDel.length()).trim();
                ValueStr = s;
                if(conv) {
                Value=Convert.convertStr(s,sc);
                isActive=(Value!=null);}//end if
                }//endif
      	     }//end execStr
//****************************************************      
      public void exec(){
             UniParm sc=new UniParm((Object)pt);
             Value=Convert.convertStr(ValueStr,sc);
             isActive=(Value!=null);
      	     }//end exec
//****************************************************   
      public String getName(){
             return Name;     	
             }//end getName
//****************************************************   
      public Object getValue(){
             if(!getIsActive()){return getValueStr();} else      	
             {return Value;}     	
        }//end getName	
//****************************************************   
      public String getValueStr(){
             return ValueStr;     	
        }//end getName	
//****************************************************   
      public void putValue(Object aValue){
             Value = aValue;     	
        }//end getName	
//****************************************************           
      public boolean getIsActive(){
             return isActive;
        }//end getIsActive
//****************************************************           
      public String toString() { 
      	if(getIsActive()){return Value.getClass()==(new String()).getClass() ? Const.cStrBeg2+Value.toString()+Const.cStrBeg2 : Value.toString();}
      	else {return "??? "+getValueStr();}
      	}//end toString  
//****************************************************   
      }//end class
