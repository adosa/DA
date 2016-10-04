package rutins;

//****************************************************
public class CreElem extends ParmElem {
      public CreElem(Object s,ParmArray apt) {this(s.toString(),apt);}
      public CreElem(String s,ParmArray apt) {this(s,apt,true);}
      public CreElem(String s,ParmArray apt,boolean isExc) {oStr=s;cDel=Const.cDDot;pt=apt;execStr(isExc);}
      }//end class
