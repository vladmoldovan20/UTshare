package moldovan.vlad.utshare.Utils;

/**
 * Created by vladu on 12/17/2017.
 */

public class StringManipulation {

    public static String expandUsernane(String username){
        return username.replace("."," ");
    }

    public static String condenseUsername(String username){
        return username.replace(" ",".");
    }
}
