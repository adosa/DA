package rutins;


//****************************************************
public class CreFileReader extends ParFileReader implements P_Const{
      static final String cFilename = "FOZCRE.CFG";
//****************************************************
      public CreFileReader() {
      	this(cFilename);
      }// end ParFileReader
//****************************************************
      public CreFileReader(String aFileName) {
      	if(aFileName==null){aFileName=cFilename;}
      	Filename = aFileName;
      }// end ParFileReader
//****************************************************
      public Object executeBlock(Array St,ParmArray Pt) {
        CreArray Ret=new CreArray();
        if(St!=null){
        for(int i=1;i<St.length();i++){
            Ret.add(new CreElem(St.elem(i),Pt));   
            }//end for
        }//end if        
        return Ret;
        } //end executeBlock
//****************************************************
      public Object executeBlock(Array St,ParmArray Pt,String Type) {
        CreArray Ret=new CreArray();
        CreElem e;
        if(St!=null){
        for(int i=1;i<St.length();i++){
            e=new CreElem((String)St.elem(i),Pt,false);
            if(e.getName().equals(Type)){e.exec();}
            Ret.add(e);   
            }//end for
        }//end if        
        return Ret;
        } //end executeBlock

//****************************************************
      public String getLnkBlockName(String s){
      	String Ret=null;
      	if(s.startsWith(cCreLnkBlock)){Ret=s.substring(s.indexOf(cCreLnkBlock)+P_Const.cCreLnkBlock.length()).trim();}
      	return Ret;
      	}//end getlnkBlockName
//****************************************************
      public boolean getLnkBlock(String s,int i){
      	boolean Ret=(i>0)&&s.startsWith(cCreMezBlock);
      	return Ret;
      	}//end getlnkBlock
//****************************************************
      public Array getBlock(String Block) {
      	return getBlock(Block,true);
      	}//end getBlock

	} //end Class
