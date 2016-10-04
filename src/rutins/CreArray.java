package rutins;


//****************************************************
public class CreArray extends Object{
      ParmArray p=new ParmArray();
//****************************************************      
      public CreElem add(CreElem e) {
      Array a=null;
      if(e!=null){
      	a=(Array)get(e.getName());
      	if(a==null){p.put(e.getName(),new ParmElem(e.getName(),(Object)(a=new Array())));}
      	a.add(e);
      	}//end if	
      return e;
      }//end ParmArray

//****************************************************
      public Array get(String type) {
      return (Array)p.get(type);
      }//end getMez
      }//end class
