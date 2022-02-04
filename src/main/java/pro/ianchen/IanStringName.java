package pro.ianchen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 字符型和名称对应项
 * the string value and name pairs
 */
public class IanStringName implements Serializable {
    /**
     * 字符型值
     * id value
     */
    public String id="";

    /**
     * 取字符型值
     * get the value of field id
     * @return 字符型值 string value
     */
    public String getId(){return this.id;}
    /**
     * 名称值
     * name value
     */
    public String name="";

    /**
     * 取名称值
     * get the value of field name
     * @return 取名称值 string name
     */
    public String getName(){return this.name;}

    /**
     * 构造函数
     * constructor
     */
    public IanStringName(){}

    /**
     * 构造函数
     * constructor
     * @param vid 编号 id
     * @param vname 名称 name
     */
    public IanStringName(String vid,String vname){
        this.id=vid;
        this.name=vname;
    }

    /**
     * 根据名称在列表中获取编号
     * get id value in the IanStringName list by name
     * @param ds 列表 IanStringName list
     * @param vName 名称 name
     * @return 编号 id
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
     * get id value in the IanStringName list by name
     * @param ds 列表 IanStringName list
     * @param vName 名称 name
     * @param defValue 找不到时的默认值 the default value will return when the name not found
     * @return 编号 id
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
     * get name value in the IanStringName list by id
     * @param ds 列表 IanStringName list
     * @param vId 值 id
     * @return 名称 name
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
     * get id value in the IanStringName list by name
     * @param ds 列表 IanStringName list
     * @param vId 值 id
     * @param defValue 找不到时的默认值 the default value return when name not found
     * @return 名称 name
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
