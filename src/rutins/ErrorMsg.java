package rutins;


//****************************************************
public class ErrorMsg extends Object{
      Object Msg=null;
      public ErrorMsg(Object e) {
      if((e!=null) && (e.getClass()==((new Array()).getClass()))){
      	Array a=(Array)e;
      	for(int i=0;i<a.length();i++){System.out.println(a.elem(i));}
      	}
      else{
      System.out.println(e);}
      }//end ErrorMsg
      }//end class
