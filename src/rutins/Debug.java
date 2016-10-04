package rutins;


//****************************************************
public class Debug extends Object{
      public static final boolean isDebug=true;
      Object Msg=null;
      public Debug(Object e) {
      if(isDebug){
      if((e!=null) && (e.getClass()==((new Array()).getClass()))){
      	Array a=(Array)e;
      	for(int i=0;i<a.length();i++){System.out.println(a.elem(i));}
      	}
      else{
      System.out.println(e);}}
      }//end ErrorMsg
      }//end class
