package pro.ianchen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 整型和名称对应项
 */
public class IanLongName implements Serializable {
    /**
     * 整型值
     */
    public long id=0;

    /**
     * 取整型值
     * @return 整型值
     */
    public long getId(){return this.id;}
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
    public IanLongName(){}

    /**
     * 构造函数
     * @param vid 编号
     * @param vname 名称
     */
    public IanLongName(long vid,String vname){
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
     * @param ds 列表
     * @param vName 名称
     * @param defValue 找不到时的默认值
     * @return 编号
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
     * @param ds 列表
     * @param vId 值
     * @return 名称
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
     * @param ds 列表
     * @param vId 值
     * @param defValue 找不到时的默认值
     * @return 名称
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
