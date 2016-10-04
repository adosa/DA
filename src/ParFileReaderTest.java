import rutins.PENTALLDBF;
import rutins.ParmArray;
import rutins.ParmArrayFile;

public class ParFileReaderTest {
//****************************************************
      public static void main( String[] args )
      {
      	ParmArray pt = (ParmArray)(new ParmArrayFile("FOZ.CFG","FO")).getParmArray();
        pt.addParmElem("P_USRNAME","");
        PENTALLDBF PALL=new PENTALLDBF(pt);
        //PALL.CreateDB(null,true,true,true,true);        
        System.out.println(pt);
        //System.out.println(((ParmElem)pt.get(sss)).getValue().getClass());}
      	
      	//Array q=(Array)Convert.convertStr("{P_UZEM_NEV,{},.T.,{'alt1'+'alrt'+P_UZEM_NEV,'alt2',{111,{111,{111},2,{3},{}}},-11.25+4.00*6.00,23},'abcd','s,s',.F.,{1}}", new UniParm((Object) pt));
      	//System.out.println(q);
      	//try{
      	//System.out.println((Convert.convertStr("P_PICTURE('999999',2)+V('DDDD','eee')", new UniParm((Object) pt))));
        //System.out.println((Array)(Convert.convertStr("{'AKT        ' ,'N',  P_PICTURE(P_LEN_NYAR,1)*P_PICTURE(P_LEN_NYAR,1)+1,  P_PICTURE(P_LEN_NYAR,2), .T. .OR. .F.}", new UniParm((Object) pt))));
        //}catch (Exception e) {System.out.println(e);e.printStackTrace();}
      	//BlockArray s1 = F.getAllBlock();
        //Iterator it = s1.keySet().iterator();
        //while (it.hasNext()){
        //s = (Array)s1.get(it.next());
      	//if(s != null) {
      	//for(int i=0;i<s.length();i++) {System.out.println(s.elem(i));}}}
       } // end main
	}