package pro.ianchen;

import java.util.function.Consumer;

/**
 * Ian的MySQL操作类
 */
public class IanMySQL {
    public Consumer<String> Log=null;

    public void WriteLog(String log){
        if(this.Log!=null){
            log=IanStringTool.Deal(log,"");

            this.Log.accept(log);
        }
    }
}
