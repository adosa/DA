package rutins;
import java.lang.reflect.*;
public class Convert {
//****************************************************	
        public static final String del_(String s){
        s=s.trim();
        while(s.startsWith("_")){s=s.substring(1).trim();}
        return s;
        }//end del_
//****************************************************	
        public static final boolean ValType(Object aO,String t) {
	     boolean exit=false;
	     boolean Ret=false;
	     int i;
	     String s=aO.getClass().toString();
	     for(i=0;(i<Const.cClassNames.length) && (!exit);i++){
	         if(s.indexOf(Const.cClassNames[i])>=0){exit=true;}}//end for
	
	     if(exit && (Const.cClassTypes[i]).equals(t)) {Ret=true;}
	     return Ret;
	     }//end ValType 
//****************************************************
        public static final boolean isArray(Object aO) {
	     return aO.getClass().isArray();
	     }//end ValType 
//****************************************************
	public static final Array convertArray(String aS, UniParm sPos,boolean AorP,boolean isExc) {
             Array v=new Array();
             boolean Ret=false;
             Object aO=null;
             String s=aS;
             String s1;
             String cBeg=AorP ? Const.cArrayBeg : Const.cParmBeg;
             String cEnd=AorP ? Const.cArrayEnd : Const.cParmEnd;
             int block1=1;
             boolean isBlock=false;
             boolean h=false;
             String BlockEnd=null;

             for(int i=1;(i<s.length())&& !Ret;i++){
             	s1=s.substring(i,i+1);
             	h=false;
             	if(!h && isBlock){
                   if(s1.equals(BlockEnd)){isBlock=false;BlockEnd=null;h=true;}
                   else {h=true;}
             	   }//end if
             	
             	if(!h && (s1.equals(Const.cStrBeg1) || s1.equals(Const.cStrBeg2)) && (!isBlock)){
             	   isBlock=true;
             	   BlockEnd=s1;
             	   h=true;
             	   }//end if

                if(!h &&  s1.equals(Const.cParmBeg)){                	
             	   convertArray(s.substring(i),sPos,false,false);
             	   i=i+sPos.getInt()-1;
             	   h=true;	
            	   }//end if
             	
             	if(!h && s1.equals(cBeg)){
             	   aO=convertArray(s.substring(i),sPos,AorP,isExc);
             	   i=i+sPos.getInt()-1;
             	   h=true;	
             	   }//end if

             	if(!h && s1.equals(cEnd)){
                   if(aO==null) {
                      if((!((s.substring(block1,i).trim().length()==0)&&(block1==1)))&&isExc){
                      aO=convertStr(s.substring(block1,i),sPos);
                      v.add(aO);}}
                   else{v.add(aO);}
                   aO=null;
                   h=true;
             	   sPos.set((int)(i+1));             
                   Ret=true;
              	   }//end if
             	if(!h && s1.equals(Const.cArrayDel) && (!isBlock)){
             	   if(isExc){
             	   if(aO==null) {aO=convertStr(s.substring(block1,i),sPos);}
                   v.add(aO);}
                   aO=null;
                   block1=i+1;
                   h=true;
                   }//end if
             	}//end for
             return v;	
             }//end convertArray
//****************************************************
public static final Object P_Run(String name,Array p,UniParm rS) {
             	Object Ret=null;
             try {
             	boolean fEnd=false;
             	P_Functions pF=new P_Functions((ParmArray)rS.getObject());
             	Class<? extends P_Functions> c=pF.getClass();
             	Method[] m=c.getMethods();
             	Object ot=null;
             	if(p.length()!=0){ 
             	   ot=new Object[p.length()];
             	   p.toArray((Object[])ot);
             	   }//end if
             	for(int i=0;(i<m.length)&&(!fEnd);i++){
             	    if(m[i].getName().toUpperCase().equals(name)){
             	       Ret=c.getDeclaredMethod(m[i].getName(),m[i].getParameterTypes()).invoke(pF,(Object[])ot);fEnd=true;}}
             	if(!fEnd){new ErrorMsg("Hiba:"+name+p+" nincs ilyen függvény !");}       
             	} catch (Exception e) {new ErrorMsg("Hiba:"+name+p);}
             return Ret;
             }//end P_Run	
//****************************************************
         public static final boolean convertVar(String aS,UniParm rS) {
             boolean Ret=false;
             String s=aS;
             String s1=null;
             String name=new String("");
             Array p;
             Object v=null;
             UniParm sc=new UniParm(rS.getObject());
             int i;
             rS.set((int)0);
             for(i=0;(i<s.length())&& !Ret;i++){
                s1=s.substring(i,i+1);
                if(Const.cAlfaNum.indexOf(s1)<0){Ret = true;}
                else {name=name+s1;}
             }//end for       
             name=name.toUpperCase();
             if(s1.equals(Const.cParmBeg)){
             	s=s.substring(i-1);
             	p = convertArray(s,sc,false,true);
             	v=P_Run(name,p,rS);
             	if(v!=null){rS.set(rS.getString()+v.toString());rS.add(v);}
             	rS.set((int)(i+sc.getInt()-1));
             	}//endif
             else {
             if(rS.getObject()!=null){
             	ParmArray pt=(ParmArray)rS.getObject();
              	rS.set(rS.getString()+rS.add(pt.getPar((Object)name)).toString());
             	rS.set(i);
             	Ret=true;
             	}//end if
             	}//end if
             return Ret;	
             }//end convertVar
//****************************************************
         public static final boolean convertNum(String aS,UniParm rS) {
             boolean Ret=false;
             String s=aS;
             String s1=null;
             String num=new String("");
             Object v=null;
             //UniParm sc=new UniParm(rS.getObject());
             int i;
             rS.set((int)0);
             for(i=0;(i<s.length())&& !Ret;i++){
                s1=s.substring(i,i+1);
                if(Const.cNum.indexOf(s1)<0){Ret = true;i=i-1;}
                else {num=num+s1;}
                }//end for          
             Ret=false;
             if(!Ret) { // check if Integer
                try {Ret=true;v = new Integer(num); } catch(NumberFormatException e) {v=null;Ret=false;}   
                }//end if
             if(!Ret) { // check if Double
                try {Ret=true;v = new Double(num); } catch(NumberFormatException e) {v=null;Ret=false;}   
                }//end if 
             if( Ret) {rS.set(rS.getString()+v);rS.add(v);rS.set((int)(i));}
             return Ret;	
             }//end convertNum

//****************************************************
         public static final boolean convertStrFg(String aS,UniParm rS) {
             int pos1,pos2;
             boolean Ret=false;
             boolean h=false;
             String s = aS.trim();
             String sc=null;	
             
             if(s.length()==0) {return Ret;}
             if(s.startsWith(Const.cStrBeg1)) {sc=Const.cStrBeg1;}
             if(s.startsWith(Const.cStrBeg2)) {sc=Const.cStrBeg2;}
             if(sc!=null){	
             	pos1=s.indexOf(sc);
             	if((pos1>=0)&&(pos1<sc.length())){pos2=s.indexOf(sc,pos1+sc.length());} else {pos2=pos1;}
             	
             	if(pos1<pos2){
             	      rS.set(rS.getString()+s.substring(pos1+sc.length(),pos2));
                      s=s.substring(pos2+1).trim();
                      convertStrFg(s,rS);}//end if
                Ret=true;
                h=true;}//end if   

              if(!h && (Const.cMuv.indexOf(s.substring(0,1))>=0)){
                      	   rS.add(new UniMuv(s.substring(0,1)));
                      	   s=s.substring(1);
                  	   convertStrFg(s,rS);
                           Ret=(!rS.getString().equals(""));
                           h=true;}//endif 

              if(!h && s.startsWith(Const.cAnd)){
                      	   rS.add(new UniMuv(Const.cAnd));
                      	   s=s.substring(Const.cAnd.length());
                  	   convertStrFg(s,rS);
                           Ret=(!rS.getString().equals(""));
                           h=true;}//endif 
              if(!h && s.startsWith(Const.cOr)){
                      	   rS.add(new UniMuv(Const.cOr));
                      	   s=s.substring(Const.cOr.length());
                  	   convertStrFg(s,rS);
                           Ret=(!rS.getString().equals(""));
                           h=true;}//endif 
              if(!h && s.startsWith(Const.cNot)){
                      	   rS.add(new UniMuv(Const.cNot));
                      	   s=s.substring(Const.cNot.length());
                  	   convertStrFg(s,rS);
                           Ret=(!rS.getString().equals(""));
                           h=true;}//endif 
              if(!h && s.startsWith(Const.cTrue)){
                      	   rS.add(new Boolean(true));
                      	   s=s.substring(Const.cTrue.length());
                  	   rS.set(rS.getString()+(new Boolean(true)));
                  	   convertStrFg(s,rS);
                           Ret=(!rS.getString().equals(""));
                           h=true;}//endif 
              if(!h && s.startsWith(Const.cFalse)){
                      	   rS.add(new Boolean(false));
                      	   s=s.substring(Const.cFalse.length());
                  	   rS.set(rS.getString()+(new Boolean(false)));
                  	   convertStrFg(s,rS);
                           Ret=(!rS.getString().equals(""));
                           h=true;}//endif 

              if(!h && (Const.cAlfa.indexOf(s.substring(0,1))>=0)){
                  if(convertVar(s,rS)){
                  	s=s.substring(rS.getInt()).trim();
                  	convertStrFg(s,rS);}//endif
                  Ret=(!rS.getString().equals(""));
                  h=true;
                }//end if
                
              if(!h && (Const.cNum.indexOf(s.substring(0,1))>=0)){
                  if(convertNum(s,rS)){
                  	s=s.substring(rS.getInt()).trim();
                  	convertStrFg(s,rS);}//endif
                  Ret=(!rS.getString().equals(""));
                  h=true;
                }//end if  
             return Ret;
             }//end convertStrFg
//****************************************************
         public static final Object Eval(String s,Array a) {             
             Object Ret=s;
             boolean fEnd=false;
             //boolean h=false;
             int i=0;
             int vI=0;
             double vD=0;
             boolean vB=true;
             Object v=null;
             String Muv=null;
             if(a==null) {return Ret;}
             Class<? extends UniMuv> cC=(new UniMuv("")).getClass();
             Class<? extends Object> aC=null,fC=null;
             Class<? extends Integer> IC=(new Integer(0)).getClass();
             Class<? extends Double> DC=(new Double(0)).getClass();
             Class<? extends String> SC=(new String()).getClass();
             Class<? extends Boolean> BC=(new Boolean(false)).getClass();
                          
             while(!fEnd&&(i<a.length())){
             	   v=a.elem(i);
             	   aC=null;
             	   if(v!=null) {aC=v.getClass();}
             	   if(aC==cC) { // muvelet
             	     Muv=((UniMuv)v).toString();
             	     }//endif
             	   else { // nem muvelet
             	   if(fC==null){fC=aC;}
             	   if(fC==SC){fEnd=true;}
             	   if(fC!=aC){fEnd=true;}
             	   if(!fEnd && aC==DC){ //double
             	      if(Muv==null)                  {vD=vD+((Double)v).doubleValue();} else {
             	      if(Muv.equals(Const.cPlus))    {vD=vD+((Double)v).doubleValue();}
             	      if(Muv.equals(Const.cMinus))   {vD=vD-((Double)v).doubleValue();}
             	      if(Muv.equals(Const.cMultiple)){vD=vD*((Double)v).doubleValue();}
             	      if(Muv.equals(Const.cDivide))  {vD=vD/((Double)v).doubleValue();}}
             	      }//end if
             	   if(!fEnd && aC==BC){ //boolean
             	      if(Muv==null)                  {vB=vB&&((Boolean)v).booleanValue();} else {
             	      if(Muv.equals(Const.cAnd))     {vB=vB&&((Boolean)v).booleanValue();}
             	      if(Muv.equals(Const.cOr))      {vB=vB||((Boolean)v).booleanValue();}
             	      if(Muv.equals(Const.cNot))     {vB=!((Boolean)v).booleanValue();}}
             	      }//end if

             	   if(!fEnd && aC==IC){ //integer
             	      if(Muv==null)                  {vI=vI+((Integer)v).intValue();} else {
             	      if(Muv.equals(Const.cPlus))    {vI=vI+((Integer)v).intValue();}
             	      if(Muv.equals(Const.cMinus))   {vI=vI-((Integer)v).intValue();}
             	      if(Muv.equals(Const.cMultiple)){vI=vI*((Integer)v).intValue();}
             	      if(Muv.equals(Const.cDivide))  {vI=vI/((Integer)v).intValue();}}
             	      }//end if
             	
             	   }//end else
             	   i=i+1;
                   }//end while
             if(aC!=null && !fEnd){
             	if(aC==DC){Ret=new Double(vD);}
             	if(aC==IC){Ret=new Integer(vI);}
             	if(aC==BC){Ret=new Boolean(vB);}
             	}//end if
             return Ret;
             }//end convertStrFg
//****************************************************
         public static final Object convertStr(String aS,UniParm sc) {
             //int pos1,pos2;
             String s = aS.trim();
             sc.set((String)"");
             sc.set((int)0);
             sc.Clr();
             Object Value = null;
             boolean isActive=false;
             if(!isActive) { // check if string
             if(convertStrFg(s,sc)){Value=Eval(sc.getString(),sc.getArray());isActive=true;}      	
             } // endif
             
             if(!isActive) { // check if Array
                if(s.startsWith(Const.cArrayBeg)){Value = convertArray(s,sc,true,true);}
                isActive=true;	            
                }//end if

             return Value;   
      	     }//end convertStr
      }//end class      	     
	