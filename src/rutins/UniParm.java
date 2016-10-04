package rutins;

public class UniParm extends Object{
      int pInt=0;
      long pLong=0;
      boolean pBoolean=false;
      String pString;
      Object pObject=null;
      Array pArray=null;
      public UniParm() {this(0);}
      public UniParm(int aP) {pInt=aP;}
      public UniParm(long aP) {pLong=aP;}
      public UniParm(boolean aP) {pBoolean=aP;}
      public UniParm(String aP) {pString=aP;}
      public UniParm(Object aP) {pObject=aP;}

      public void set(int aP) {pInt=aP;}
      public void set(long aP) {pLong=aP;}
      public void set(boolean aP) {pBoolean=aP;}
      public void set(String aP) {pString=aP;}
      public void set(Object aP) {pObject=aP;};
      public Array Clr() {Array Ret=pArray;pArray=null;return Ret;}
      public Object add(Object aP) {
      	          if(pArray==null){pArray=new Array();}
      	          pArray.add(aP);
      	          return aP;
                   }//end add
      public int getInt(){return pInt;}
      public long getLong(){return pLong;}
      public boolean getBoolean(){return pBoolean;}
      public String getString(){return pString;}
      public Object getObject(){return pObject;}
      public Array getArray(){return pArray;}
      public String toString(){return "";}
}//end class
