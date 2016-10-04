package rutins;
import java.io.*;

//****************************************************
public class ParFileReader {
      static final String cFilename = "FOZ.CFG";
      String Filename;
      File   F;
      BlockArray AllBlock = null;
      boolean isAllBlock = false;
      boolean isActive = false;
//****************************************************
      public ParFileReader() {
      	this(cFilename);
      }// end ParFileReader
//****************************************************
      public ParFileReader(String aFileName) {
      	if(aFileName==null){aFileName=cFilename;}
      	Filename = aFileName;
      }// end ParFileReader
//****************************************************
      public boolean open() throws Exception {
      	F = new File(Filename);
      	if(!F.exists()){throw new MsgExc("A " + F.getName()+ " file nem létezik !");}
        isActive = true;
        return true;
        } //end open
//****************************************************
      public Object executeBlock(Array St,ParmArray Pt) {
        if(St!=null){
        for(int i=1;i<St.length();i++){
            ParmElem p=new ParmElem(St.elem(i),Pt);
            Pt.add(p.getName(),p);   
            }//end for
        }//endif  
        return Pt;
        } //end executeBlock
      
//****************************************************
      public String getLnkBlockName(String s){
      	String Ret=null;
      	int pos=s.indexOf(Const.cLnkBlock);
      	if(pos>=0){Ret=s.substring(pos+Const.cLnkBlock.length()).trim();}
      	return Ret;
      	}//end getlnkBlockName
//****************************************************
      public boolean getLnkBlock(String s,int i){
      	boolean Ret=(i>0);
      	return Ret;
      	}//end getlnkBlock

//****************************************************
      public Array getBlockOpen(Array s, boolean open){
      	if(!open) {return s;}
      	Array v = new Array();
      	Array Ret=null;
      	for(int i=0;i<s.length();i++){
      	    String ss=(String)s.elem(i);
      	    int pos = ss.lastIndexOf(Const.cRem);
            if(pos>=0){
               ss=ss.substring(0,pos);
               }//endif
        
      	    ss=getLnkBlockName(ss);
      	    if(ss!=null) {
      	       Array s1=getBlock(ss,open);
               if(s1!=null){
      	       for(int ii=0;ii<s1.length();ii++){if(getLnkBlock((String)s1.elem(ii),ii)){v.add(s1.elem(ii));}}}
      	       }//endif
      	    else {v.add(s.elem(i));}
      	}//end for	
      Ret = v;
      return Ret;
      }//end getBlockOpen
//****************************************************
      public Array getBlock(String Block) {
      	return getBlock(Block,true);
      	}//end getBlock
//****************************************************
      public Array getBlock(String Block, boolean open) {
      	if(isAllBlock){return getBlockOpen((Array)AllBlock.get(Block),open);}
      	else {return getBlockOpen(getBlockF(Block),open);}	
      	}//end getBlock
//****************************************************
      public Array getBlockF(String Block) {
      	String s=null;
      	Array Ret=null;

      	try {
      	if(!isActive){open();}	
      	BufferedReader br = new BufferedReader(new FileReader(F));
      	Array v = new Array();
      	boolean found = false;
      	String ActBlock=null;
      	int Pos;
      	 
      	while ((!found) && ((s = br.readLine()) !=null)) {
              
              if(ActBlock == null) {
                 Pos = s.indexOf(",");
                 if(Pos > 0) {ActBlock = s.substring(0,Pos);}  
                 
                 if(ActBlock.equals(Block)){found = true;}
              }
              else {
              if (s.startsWith("END"+ActBlock)) {ActBlock=null;}	
              }           
         }//end while
         
        if (found) {       	
        boolean fend = false;	
        do {  
              if (s.startsWith("END"+Block)) { fend = true; }
              else {if(s.length()>0) {v.add(s);}}
         }while ((!fend) && ((s = br.readLine()) !=null));//end while

        Ret = v;
        }//endif
        br.close();
        
         }//end try
        catch (MsgExc e) {new ErrorMsg(e);}
        catch (Exception e) {new ErrorMsg(e);}
        return Ret;
 }//end getBlockF
//****************************************************
      public BlockArray getAllBlock() {
      	String s;
      	if(isAllBlock) {return AllBlock;}
      	BlockArray Ret=new BlockArray();
        
      	try {
      	if(!isActive){open();}	
      	BufferedReader br = new BufferedReader(new FileReader(F));
      	Array v = null;
      	//boolean found = false;
      	String ActBlock=null;
      	int Pos;
      	
      	while ((s = br.readLine()) !=null) {
              
              if(ActBlock == null) {
                 Pos = s.indexOf(",");
                 if(Pos > 0) {
                    ActBlock = s.substring(0,Pos);
                    v = new Array();
                    v.add(s);}  
              }//end if
              else {
              if (s.startsWith("END"+ActBlock)) {
              	 if(v!=null) {
              	  Ret.add(ActBlock,v);                 	 
              	  ActBlock=null;
              	  v=null;}}
              else {if(s.length()>0){v.add(s);}}	  	
              }//end else           
         }//end while
      	br.close();
        }//end try
        catch (MsgExc e) {new ErrorMsg(e);}
        catch (Exception e) {new ErrorMsg(e);}
        AllBlock = Ret;
        isAllBlock = true;
        return Ret;
 }//end getAllBlock

	} //end Class
