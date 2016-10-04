package rutins;


public class MsgExc extends Exception {
      /**
	 * 
	 */
	private static final long serialVersionUID = 1679328549711001677L;
	private String Msg;
      public MsgExc(String aMsg) { Msg = aMsg;}
      public String toString() { return Msg;}
      }//end class
