package pro.ianchen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 长整型和名称对应项
 * the pair of id and name field
 * id is long value
 * and
 * name is string value
 */
public class IanLongName implements Serializable {
    /**
     * 长整型值
     * id value
     */
    public long id=0;

    /**
     * 取长整型值
     * get id value
     * @return 长整型值 long value
     */
    public long getId(){return this.id;}
    /**
     * 名称值
     * name value
     */
    public String name="";

    /**
     * 取名称值
     * get name value
     * @return 名称值 name value
     */
    public String getName(){return this.name;}

    /**
     * 构造函数
     * constructor
     */
    public IanLongName(){}

    /**
     * 构造函数
     * constructor
     * @param vid 编号 id value
     * @param vname 名称 name value
     */
    public IanLongName(long vid,String vname){
        this.id=vid;
        this.name=vname;
    }

    /**
     * 根据名称在列表中获取编号
     * get id by name in the IanLongName list
     * @param ds 列表 the IanLongName list
     * @param vName 名称 name value
     * @return 编号 id value
     * @throws Exception
     */
    public static long getIdByName(List<IanLongName> ds,String vName) throws Exception {
        if(ds==null) ds=new ArrayList<IanLongName>();
        if(ds.stream().anyMatch(a->a.name==null?(vName==null):(a.name.equals(vName)))){
            return ds.stream().filter(a->a.name==null?(vName==null):(a.name.equals(vName))).map(a->a.id).findFirst().get();
        }else{
            throw new Exception("未找到对比名称");
        }
    }

    /**
     * 根据名称在列表中获取编号
     * get id by name in the IanLongName list
     * if can not find the name then return default value
     * @param ds 列表 the IanLongName list
     * @param vName 名称 name
     * @param defValue 找不到时的默认值 default id value
     * @return 编号 id value
     * @throws Exception
     */
    public static long getIdByName(List<IanLongName> ds,String vName,long defValue) throws Exception {
        if(ds==null) ds=new ArrayList<IanLongName>();
        if(ds.stream().anyMatch(a->a.name==null?(vName==null):(a.name.equals(vName)))){
            return ds.stream().filter(a->a.name==null?(vName==null):(a.name.equals(vName))).map(a->a.id).findFirst().get();
        }else{
            return defValue;
        }
    }

    /**
     * 根据值在列表中获取名称
     * get name by id in the IanLongName list
     * @param ds 列表 the IanLongName list
     * @param vId 值 long value
     * @return 名称 name value
     * @throws Exception
     */
    public static String getNameById(List<IanLongName> ds,long vId) throws Exception {
        if(ds==null) ds=new ArrayList<IanLongName>();
        if(ds.stream().anyMatch(a->a.id==vId)){
            return ds.stream().filter(a->a.id==vId).map(a->a.name).findFirst().get();
        }else{
            throw new Exception("未找到对比值");
        }
    }

    /**
     * 根据名称在列表中获取编号
     * get name by id in the IanLongName list
     * if can not find the id then return default value
     * @param ds 列表 the IanLongName list
     * @param vId 值 id value
     * @param defValue 找不到时的默认值 default name value
     * @return 名称 name value
     * @throws Exception
     */
    public static String getNameById(List<IanLongName> ds,long vId,String defValue) throws Exception {
        if(ds==null) ds=new ArrayList<IanLongName>();
        if(ds.stream().anyMatch(a->a.id==vId)){
            return ds.stream().filter(a->a.id==vId).map(a->a.name).findFirst().get();
        }else{
            return defValue;
        }
    }

}
