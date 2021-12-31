package pro.ianchen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 字符型和名称对应项
 */
public class IanStringName implements Serializable {
    /**
     * 字符型值
     */
    public String id="";

    /**
     * 取字符型值
     * @return 字符型值
     */
    public String getId(){return this.id;}
    /**
     * 名称值
     */
    public String name="";

    /**
     * 取名称值
     * @return 取名称值
     */
    public String getName(){return this.name;}

    /**
     * 构造函数
     */
    public IanStringName(){}

    /**
     * 构造函数
     * @param vid 编号
     * @param vname 名称
     */
    public IanStringName(String vid,String vname){
        this.id=vid;
        this.name=vname;
    }

    /**
     * 根据名称在列表中获取编号
     * @param ds 列表
     * @param vName 名称
     * @return 编号
     * @throws Exception
     */
    public static String getIdByName(List<IanStringName> ds,String vName) throws Exception {
        if(ds==null) ds=new ArrayList<IanStringName>();
        if(ds.stream().anyMatch(a->a.name==null?(vName==null):(a.name.equals(vName)))){
            return ds.stream().filter(a->a.name==null?(vName==null):(a.name.equals(vName))).map(a->a.id).findFirst().get();
        }else{
            throw new Exception("未找到对比名称");
        }
    }

    /**
     * 根据名称在列表中获取编号
     * @param ds 列表
     * @param vName 名称
     * @param defValue 找不到时的默认值
     * @return 编号
     * @throws Exception
     */
    public static String getIdByName(List<IanStringName> ds,String vName,String defValue) throws Exception {
        if(ds==null) ds=new ArrayList<IanStringName>();
        if(ds.stream().anyMatch(a->a.name==null?(vName==null):(a.name.equals(vName)))){
            return ds.stream().filter(a->a.name==null?(vName==null):(a.name.equals(vName))).map(a->a.id).findFirst().get();
        }else{
            return defValue;
        }
    }


    /**
     * 根据值在列表中获取名称
     * @param ds 列表
     * @param vId 值
     * @return 名称
     * @throws Exception
     */
    public static String getNameById(List<IanStringName> ds,String vId) throws Exception {
        if(ds==null) ds=new ArrayList<IanStringName>();
        if(ds.stream().anyMatch(a->a.id==null?(vId==null):(a.id.equals(vId)))){
            return ds.stream().filter(a->a.id==null?(vId==null):(a.id.equals(vId))).map(a->a.name).findFirst().get();
        }else{
            throw new Exception("未找到对比值");
        }
    }

    /**
     * 根据名称在列表中获取编号
     * @param ds 列表
     * @param vId 值
     * @param defValue 找不到时的默认值
     * @return 名称
     * @throws Exception
     */
    public static String getNameById(List<IanStringName> ds,String vId,String defValue) throws Exception {
        if(ds==null) ds=new ArrayList<IanStringName>();
        if(ds.stream().anyMatch(a->a.id==null?(vId==null):(a.id.equals(vId)))){
            return ds.stream().filter(a->a.id==null?(vId==null):(a.id.equals(vId))).map(a->a.name).findFirst().get();
        }else{
            return defValue;
        }
    }


}
