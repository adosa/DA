package rutins;

public class P_Functions extends Object{
//****************************************************	
        ParmArray pt=null;
        public P_Functions(ParmArray aP){pt=aP;}
//****************************************************	        
        public UniDate BOY(String s){
        	return new UniDate();}//end BOY
//****************************************************	        
        public Integer YEAR(String s){
        	return new Integer(2002);}//end YEAR
//****************************************************	        
        public Integer Time(){
        	return new Integer(2002);}//end Time
//****************************************************	        
        public UniDate CTOD(String s){
        	return new UniDate();}//end CTOD

//****************************************************	        
        public UniDate Date(){
        	return new UniDate();}//end Date
//****************************************************	        
        public Boolean mezotoltes(String s,String s1,boolean b){
        	return new Boolean(false);}//end mezotoltes
//****************************************************	        	
        public Object P_PICTURE(String s,int Mit) {
        	int i;
        	String s1="";
        	String ss="";
        	for(i=0;i<s.length();i++){
                    ss=s.substring(i,i+1);
                    if(!ss.equals(Const.cArrayDel)) {s1=s1+ss;}
                }//end for
                
        	if(Mit==1){return new Integer (s1.length());}
        	if(Mit==2){
        	   int p=s.lastIndexOf(Const.cDecPoint);
        	   if((p>=0)&&(p<s.length())){return new Integer(s.length()-p-1);}else{return new Integer(0);}
        	}//end if
        	if(Mit==3){return s1;}
        	if(Mit==4){return s;}
        	if(Mit==5){return new Integer(s.trim().length());};
        	
        	return null;
                }//end V
//****************************************************	        
        public String V(String s1,String s2) {
        	return s1+"==="+s2;
        	}//end V
}//end class
