package rutins;
import java.sql.*;
import java.lang.reflect.*;

//****************************************************
public class PENTALLDBF extends Object implements P_Const{
       CreFileReader f;
       ParmArray p;
       boolean isUseAlias=true;
       DBInterface DBI=null;
       Array Filek;
       Array FilekStru;
       Array Indek;
       Array IndekStru;
       String P_PENTALL;
       String P_PENTIND;
//**************************************************** 
       public PENTALLDBF(ParmArray aP){this((String)aP.get(P_Const.cCreFile),aP);}
//**************************************************** 
       public PENTALLDBF(String FileName,ParmArray aP){
       	      p=aP;
       	      f = new CreFileReader(FileName);
       	      //f.getAllBlock();
              initDBI();
              P_PENTALL=(String)p.get((Object)P_Const.cPENTALL);
              P_PENTIND=(String)p.get((Object)P_Const.cPENTIND);
              CreArray c=(CreArray)f.executeBlock(f.getBlock(P_PENTALL),p);
              Filek=c.get(P_Const.cCreRecBlock);
       	      FilekStru=c.get(P_Const.cCreMezBlock);
              c=(CreArray)f.executeBlock(f.getBlock(P_PENTIND),p);
              Indek=c.get(P_Const.cCreRecBlock);
       	      IndekStru=c.get(P_Const.cCreMezBlock);
       	      }//end PENTALLDBF      
//****************************************************      
      public String toString(){
             return f.Filename+Const.CR+Filek.toString();
             }//end toString
//****************************************************      
      public CreArray getStru(String name){
             return (CreArray)f.executeBlock(f.getBlock(name),p);
             }//end getStru      	
//****************************************************      
      public CreArray getStru(String name,String Type){
             return (CreArray)f.executeBlock(f.getBlock(name),p,Type);
             }//end getStru
//****************************************************      
      public static final Object getArrayField(Array a,int i1,int i2){
      	     return ((Array)((CreElem)a.elem(i1)).getValue()).elem(i2);
      	     }
//****************************************************      
      public static final Object getArrayFieldName(Array a,int i1){
      	     return ((Array)((CreElem)a.elem(i1)).getValue()).elem(0);
      	     }
//**************************************************** 
      public Object getFilekField(int i1,int i2){
      	     return getArrayField(Filek,i1,i2);
      	     }
//****************************************************      
      @SuppressWarnings("rawtypes")
	public Object GetDBI(String name){
      	     Object o=null; 
      	     try {
      	     Class<?> c=Class.forName("rutins.DBInterface"+name);
      	     Constructor[] co=c.getConstructors();
      	     Constructor ci=null;
      	     
      	     for(int i=0;(i<co.length)&&(ci==null);i++){
      	     	if(co[i].getParameterTypes().length==0){ci=co[i];}	
      	        }//end for
      	     if(ci!=null){o=ci.newInstance();} else {new ErrorMsg("DBI Counstructor not found!");}
      	     } catch (Exception e) {new ErrorMsg(e);}
      	     return o;
      	     }//GetDBI
//****************************************************      
      public void  initDBI(){
             String sDBI=(String)p.get((Object)P_Const.cDBI);
             if(DBI==null){DBI=(DBInterface) GetDBI(sDBI==null ? "":sDBI);}
             }//initDBI
//****************************************************      
      public void CreateDB(String name,boolean isMain,boolean isDrop,boolean isCreate,boolean isInsert){
             CreArray Stru;
             String fName,fAlias,iKod;
             Connection c=DBI.getConn();
             
             if(isMain){
             fAlias=fName=P_PENTALL;
             DBI.DropTableDBF(c,isUseAlias ? fAlias:fName);
             DBI.CreateTableDBF(c,isUseAlias ? fAlias:fName,FilekStru);
             DBI.InsertDBF(c,isUseAlias ? fAlias:fName,FilekStru,Filek);
             fAlias=fName=P_PENTIND;
             DBI.DropTableDBF(c,isUseAlias ? fAlias:fName);
             DBI.CreateTableDBF(c,isUseAlias ? fAlias:fName,IndekStru);
             DBI.InsertDBF(c,isUseAlias ? fAlias:fName,IndekStru,Indek);}//endif
             
             for(int i=0;i<Filek.length();i++){
             	fName=(String)getFilekField(i,cFAZON);
             	fAlias=(String)getFilekField(i,cALLNEV);
             	iKod=(String)getFilekField(i,cINDEXKOD);
             	if((name==null) || name.equals(fAlias)){
             	  Stru=getStru(fName);
             	  if(isDrop){DBI.DropIndexDBF(c,isUseAlias ? fAlias:fName,Indek,iKod);
             	  	     DBI.DropTableDBF(c,isUseAlias ? fAlias:fName);}
             	  if(isCreate){DBI.CreateTableDBF(c,isUseAlias ? fAlias:fName,Stru.get(cCreMezBlock));
             	  	       DBI.CreateIndexDBF(c,isUseAlias ? fAlias:fName,Indek,iKod);}
                  if(isInsert){DBI.InsertDBF(c,isUseAlias ? fAlias:fName,Stru.get(cCreMezBlock),Stru.get(cCreRecBlock));}                 
                }//end if 
             	}//end for     	
      	     }// end CreateDB
}//end Class
