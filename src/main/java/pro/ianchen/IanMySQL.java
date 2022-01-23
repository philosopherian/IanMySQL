package pro.ianchen;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ian的MySQL操作类
 * Ian's MySQL operater
 */
public class IanMySQL {
    /**
     * 日志
     * log delegate function
     */
    public Consumer<String> Log=null;

    /**
     * 日志普通信息
     * write normal log
     * @param s 日志内容 log content
     */
    public void LogInfo(String s){
        if(this.Log!=null){
            String log=IanConvert.FormatDate(IanStringTool.Now(),"yyyyMMddHHmmssSSS")+"\t普通日志\t"+IanStringTool.Deal(s,"");
            this.Log.accept(log);
        }
    }


    /**
     * 日志错误信息
     * write error log
     * @param s 日志信息 log content
     */
    void LogError(String s){
        if(this.Log!=null){
            String log=IanConvert.FormatDate(IanStringTool.Now(),"yyyyMMddHHmmssSSS")+"\t错误日志\t"+IanStringTool.Deal(s,"");
            this.Log.accept(log);
        }
    }

    /**
     * 实例全局号
     * the instance's guid
     * used to distinguish different log contents in a multithreaded environment
     */
    public String Guid= IanStringTool.Guid2();

    /**
     * 数据库连接源
     * database source
     */
    public javax.sql.DataSource DataSource=null;

    /**
     * 数据库连接
     * database connection
     */
    public java.sql.Connection Connection=null;

    /**
     * 是否自动提交
     * get whether the database operation is automatically committed
     * @return 是否 true or false
     */
    public boolean IsAutoCommit() throws SQLException {
        if(this.Connection==null) return true;
        return this.Connection.getAutoCommit();
    }

    /**
     * 数据连接超时时间秒数
     * the database connection timeout seconds
     */
    public int SQLTimeOut = 720;

    /**
     * 查询结果返回列
     * the columns information in the query result
     */
    public Map<Integer, String> QueryResultColumns=new HashMap<Integer, String>();

    /**
     * MySQL子处理对象列表
     * the IanMySQL instance can create the child instance
     * the parent and children instance use the same database connection to save resources and control transaction
     * all children instances are stored in this list
     */
    protected List<IanMySQL> _mySqlWheres=new ArrayList<IanMySQL>();

    /**
     * 增加MySQL子处理对象
     * add a IanMySQL instance to the MySQL instance list
     * @param m MySQL子处理对象 IanMySQL child instance
     */
    private void AddIanMySql(IanMySQL m){
        if(m!=null){
            this._mySqlWheres.add(m);
            this.LogInfo("IanMySql对象(" + this.Guid + ")加入子对象 " + m.Guid);
        }
    }

    /**
     * 构造函数
     * constructor
     * 从连接池分配连接
     * get the database connection from the datasource (the database connection pool)
     * @throws Exception
     */
    public IanMySQL(javax.sql.DataSource ds) throws Exception {
        if(ds==null) throw new Exception("数据库连接源不能为空");
        this.DataSource=ds;
        this.Connection=this.DataSource.getConnection();
        this.LogInfo(this.Guid+"创建IanMySql对象，并分配数据库连接");
    }

    /**
     * 构造函数
     * constructor
     * 如果指定连接为空，则从连接池分配连接，否则当前实例连接为指定连接
     * if the specified connection is null then get the connection from the database source (the database connection pool)
     * if the specified connection is not null then get the specified connection
     * @param conn 指定连接 the specified connection
     * @param ds 数据库连接源，指定连接的参数必须由此连接源产生 the datasource (the database connection pool)
     * @throws Exception
     */
    private IanMySQL(java.sql.Connection conn, javax.sql.DataSource ds) throws Exception {
        if(ds==null) throw new Exception("数据库连接源不能为空");
        this.DataSource=ds;
        if(conn!=null){
            this.Connection=conn;
        }else{
            this.Connection=ds.getConnection();
        }
    }

    /**
     * 创建当前IanMySQL的子实例，子实例与主实例共用一个数据库连接
     * create the IanMySQL child instance
     * the child instance and the parent instance use the same database connection
     * @return 返回创建的子实例 return the created IanMySQL child instance
     * @throws Exception
     */
    public IanMySQL CreateIanMySql() throws Exception {
        IanMySQL sql=new IanMySQL(this.Connection,this.DataSource);
        sql.Log=this.Log;
        this.AddIanMySql(sql);
        return sql;
    }

    /**
     * 关闭连接
     * close the IanMySQL instance (include the children instance)
     * and close the database connection if the database connection is not closed
     * @throws SQLException
     */
    public void Close() throws SQLException {
        for(IanMySQL m : this._mySqlWheres){
            m.Close();
        }
        try{
            if(this.Connection!=null&& !this.Connection.isClosed()){
                this.Connection.close();
            }
        }catch(Exception ex){

        }
        this.LogInfo("释放数据库连接"+this.Guid);
    }

    /**
     * 主干SQL
     * the main SQL statement
     */
    public String Sql="";

    /**
     * 私有的where条件语句列表
     * the where condition statement list, it's private field
     */
    protected List<IanKeyPair<IanLogicalRelationEnum, String>> _Wheres=new ArrayList<IanKeyPair<IanLogicalRelationEnum,String>>();

    /**
     * 私有的SqlParamter参数信息列表
     * the Sql parameters list, it's private field
     */
    protected Map<String, Object> _SqlParameters=new HashMap<String, Object>();

    /**
     * 私有的Order By语句
     * the order statement, it's private field
     */
    protected String _OrderBy = "";

    /**
     * 私有的Group By语句
     * the group by statement, it's private field
     */
    protected String _GroupBy = "";
    /**
     * 私有的Limit语句
     * the limit statement, it's private field
     */
    protected String _Limit = "";

    /**
     * reset Sql、OrderBy、GroupBy、Limit statement field to blank
     * 重置Sql、OrderBy、GroupBy、Limit语句为空
     * 所有条件被清理 clear all condition
     * 所有的参数被清理 clear all parameters
     * 所有的子实例也被清理 clear all children instance
     */
    public void Reset() throws SQLException {
        this.Sql="";
        this._OrderBy="";
        this._GroupBy="";
        this._Limit="";
        this._Wheres.clear();
        this._SqlParameters.clear();
        for(IanMySQL m:this._mySqlWheres){
            m.Close();
            m=null;
        }
        this._mySqlWheres.clear();
    }

    /**
     * 加入where条件语句
     * add where condition statement
     * @param logicalRelation 逻辑关系and或者or logical relation
     * @param whereSql where条件语句 the sql statement representing where statement
     * @throws Exception
     */
    public void AddWhere(IanLogicalRelationEnum logicalRelation, String whereSql) throws Exception {
        whereSql= IanStringTool.Check(whereSql,"SQL语句加入的Where条件");
        Matcher m= Pattern.compile(".*(\\s+)or(\\s+).*").matcher(whereSql.toLowerCase());
        if(m.matches()){
            whereSql="("+whereSql+")";
        }
        this._Wheres.add(new IanKeyPair<IanLogicalRelationEnum,String>(logicalRelation,whereSql));
    }

    /**
     * 加入where条件语句
     * add where condition statement
     * 默认逻辑关系为And default logical relation is and
     * @param whereSql where条件语句 the sql statement representing where statement
     * @throws Exception
     */
    public void AddWhere(String whereSql) throws Exception {
        this.AddWhere(IanLogicalRelationEnum.And,whereSql);
    }

    /**
     * 设置SQL语句order by部分，不包含order by
     * set the order by statement in sql, not include "order by"
     * @param orderBy order by 内容 order by statement
     */
    public void SetOrderBy(String orderBy){
        orderBy= IanStringTool.Deal(orderBy,"");
        this._OrderBy=orderBy;
    }

    /**
     * 设置SQL语句的limit部分，不包括limit
     * set the limit statement in sql, not include "limit"
     * @param limit limit内容 limit statement
     */
    public void SetLimit(String limit){
        limit= IanStringTool.Deal(limit,"");
        this._Limit=limit;
    }

    /**
     * 设置SQL语句group by部分，不包含group by
     * set the group by statement in sql, not include "group by"
     * @param groupBy group by内容 group by statement
     */
    public void SetGroupBy(String groupBy){
        groupBy= IanStringTool.Deal(groupBy,"");
        this._GroupBy=groupBy;
    }

    /**
     * 加入MySqlParameter参数
     * add MySql Parameter name and value
     * the parameter name need start with character "@", the parameter name is correspond to variable in sql statement
     * @param name 参数名 parameter name
     * @param value 参数值 parameter value
     * @throws Exception
     */
    public void AddSqlParamter(String name, Object value) throws Exception {
        name= IanStringTool.Check(name,"参数名");
        if(!name.startsWith("@")) throw new Exception("参数请用@开头");
        if(value instanceof Date &&((Date)value).getTime()==Long.MAX_VALUE){
            this._SqlParameters.put(name,null);
        }else{
            this._SqlParameters.put(name,value);
        }
    }

    /**
     * 获取SQL语句
     * get the sql statement
     * 默认包含排序，不包含分组
     * default
     * include order by statement
     * exclude group by statement
     * @return SQL语句 sql statement
     */
    protected String GetSQL(){
        return this.GetSQL(true);
    }

    /**
     * 获取SQL语句
     * get the sql statement
     * 默认不包含分组
     * default
     * exclude group by statement
     * @param isOrder 是否排序 whether include order by statement
     * @return SQL语句 sql statement
     */
    protected String GetSQL(boolean isOrder){
        return this.GetSQL(isOrder,false);
    }

    /**
     * 获取SQL语句
     * get the sql statement
     * @param order 是否包含排序 whether include order by statement
     * @param group 是否包含分组 whether include group by statement
     * @return SQL语句 sql statement
     */
    protected String GetSQL(boolean order, boolean group){
        StringBuilder sql = new StringBuilder();
        sql.append(this.Sql);
        if (this._Wheres.size() > 0)
        {
            sql.append(" where ");
        }
        boolean b = false;
        for (IanKeyPair<IanLogicalRelationEnum, String> s : this._Wheres)
        {
            if (b)
            {
                if (s.getKey() == IanLogicalRelationEnum.And)
                {
                    sql.append(" and ");
                }
                else
                {
                    sql.append(" or ");
                }
            }
            sql.append(" ").append(s.getValue());
            b = true;
        }
        if (group)
        {
            if (this._GroupBy.length() > 0)
            {
                sql.append(" group by ").append(this._GroupBy);
            }
        }

        if (order)
        {
            if (this._OrderBy.length() > 0)
            {
                sql.append(" order by ").append(this._OrderBy);
            }
        }
        if (this._Limit.length() > 0) {
            sql.append(" limit ").append(this._Limit);
        }
        return sql.toString().trim();
    }

    /**
     * 将SQL语句转换成“?”的参数表达式
     * convert the parameter (start with character "@") to "?" (parameter in standard mysql usage) expression
     * 如果没有参数则不转换
     * it will not do any conversion when no parameters in sql statement
     * @param sql 需要转换的SQL语句 the sql statement need to converted
     * @return
     * map.get(0)为转换后的可用SQL语句，如果有参数则为1至map.size() - 1,Key代表SQL语句中的"?"位置，Value为this._SqlParameters对应的Key值
     * map.get(0) is converted sql statement
     * map index 1 to map.size() -1 is parameters information
     * map Key is the "?" position in sql statement
     * map Value is the key of this._SqlParameters
     */
    private Map<Integer, String> parseSQL(String sql){
        String regex = "(@(\\w+))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sql);
        int idx=1;
        Map<Integer, String> ms=new HashMap<>();
        while (m.find()) {
            //参数名称可能有重复，使用序号来做Key
            ms.put(idx++, "@"+m.group(2));
        }
        String s = sql.replaceAll(regex, "?");
        ms.put(0,s);
        if(this.Log!=null){
            for(Integer i:ms.keySet()){
                if(i==0){
                    this.LogInfo("sql:"+ms.get(i));
                }else{
                    if(this._SqlParameters.containsKey(ms.get(i))){
                        this.LogInfo("param "+i+"("+ms.get(i)+"):"+String.valueOf(this._SqlParameters.get(ms.get(i))));
                    }else{
                        this.LogInfo("param "+i+"("+ms.get(i)+"):未发现名为"+ms.get(i)+"的参数设置");
                    }
                }
            }
        }
        return ms;
    }

    /**
     * 执行SQL语句
     * execute sql statement
     * @throws Exception
     */
    public void ExecuteCmd() throws Exception {
        this.ExecuteCmd(true);
    }

    /**
     * 执行SQL语句
     * execute sql statement
     * @param needExecute 真正执行 whether execute sql statement
     * @throws Exception
     */
    public void ExecuteCmd(boolean needExecute) throws Exception {
        String sql = this.GetSQL(false, false);
        Map<Integer, String> ms=this.parseSQL(sql);
        String sql1=ms.get(0);
        if(sql1.trim().toLowerCase().startsWith("drop table ")){
            Statement stm=null;
            try
            {
                this.LogInfo(this.Guid+" 执行SQL:"+sql1);
                stm=this.Connection.createStatement();
                if(needExecute){
                    stm.execute(sql1);
                }
            }
            catch (Exception e)
            {
                this.LogError(this.Guid+" 执行SQL:"+sql1+" 失败:"+e.getMessage()+"\r\n"+IanStringTool.GetExceptionStackInfo(e));
                throw e;
            }
            finally
            {
                if (stm != null) {
                    stm.close();
                    stm=null;
                }
            }
        }else{
            PreparedStatement stm=null;
            try
            {
                this.LogInfo(this.Guid+" 执行SQL:"+sql1);
                stm=this.Connection.prepareStatement(sql1);
                for(Integer id:ms.keySet()){
                    if(id==0) continue;
                    String key=ms.get(id);
                    if(!this._SqlParameters.containsKey(key)) throw new Exception("未发现名为"+key+"的参数设置");
                    Object val=this._SqlParameters.get(key);
                    stm.setObject(id,val);
                    this.LogInfo(this.Guid+" 执行SQL参数:@"+key+"="+IanConvert.ToString(val));
                }
                if(needExecute){
                    stm.executeUpdate();
                }
            }
            catch (Exception e)
            {
                this.LogError(this.Guid+" 执行SQL:"+sql1+" 失败:"+e.getMessage()+"\r\n"+IanStringTool.GetExceptionStackInfo(e));
                throw e;
            }
            finally
            {
                if (stm != null) {
                    stm.close();
                    stm=null;
                }
            }
        }
    }

    /**
     * 按对象列表批量导入
     * batch load the specified type list
     * @param c 对象类型 the specified type
     * @param ts 对象列表 the specified type list
     * @param <T> 对象类型 the specified type
     */
    public <T> void BatchAdd(Class<T> c,List<T> ts) throws Exception {
        Field[] fs= c.getFields();
        List<HashMap<String,Object>> ls=new ArrayList<HashMap<String, Object>>();
        for(T t:ts){
            HashMap<String,Object> ms=new HashMap<String,Object>();
            for(Field f:fs){
                if(f.getType().isEnum()){
                    String cname=f.getGenericType().getTypeName();
                    Class<Enum> ecls = (Class<Enum>) Class.forName(cname);
                    boolean islong=false;
                    boolean bfind=false;
                    long lval=0;
                    String sval="";
                    for(Enum v1:ecls.getEnumConstants()){
                        Field f1 =ecls.getDeclaredField(v1.name());
                        Enum a=(Enum)f.get(t);
                        if(a==v1){
                            IanEnumLabel el=f1.getAnnotation(IanEnumLabel.class);
                            if(el.svalue().length()>0){
                                sval=el.svalue();
                                islong=false;
                            }else{
                                lval=el.value();
                                islong=true;
                            }
                            bfind=true;
                            break;
                        }
                    }
                    if(bfind){
                        if(islong){
                            ms.put(f.getName(),lval);
                        }else{
                            ms.put(f.getName(),sval);
                        }
                    }else{
                        continue;
                    }
                }else if(!f.isSynthetic()){
                    f.setAccessible(true);
                    String ftype=f.getGenericType().getTypeName();
                    switch (ftype) {
                        case "java.lang.String":
                            ms.put(f.getName(),IanConvert.ToString(f.get(t)));
                            break;
                        case "java.lang.Integer":
                        case "int":
                            ms.put(f.getName(),f.getInt(t));
                            break;
                        case "java.lang.Character":
                        case "char":
                            ms.put(f.getName(),f.getChar(t));
                            break;
                        case "java.lang.Long":
                        case "long":
                            ms.put(f.getName(),f.getLong(t));
                            break;
                        case "java.lang.Byte":
                        case "byte":
                            ms.put(f.getName(),f.getByte(t));
                            break;
                        case "java.lang.Short":
                        case "short":
                            ms.put(f.getName(),f.getShort(t));
                            break;
                        case "java.lang.Float":
                        case "float":
                            ms.put(f.getName(),f.getFloat(t));
                            break;
                        case "java.lang.Double":
                        case "double":
                            ms.put(f.getName(),f.getDouble(t));
                            break;
                        case "java.lang.boolean":
                        case "boolean":
                            ms.put(f.getName(),f.getBoolean(t));
                            break;
                        case "java.util.Date":
                            ms.put(f.getName(),IanConvert.FormatMySQLDate(IanConvert.ToDate(f.get(t))));
                            break;
                        default:
                            break;
                    }
                    f.setAccessible(false);
                }else{
                    continue;
                }
            }
            ls.add(ms);
        }
        this.BatchAdd(ls);
    }

    /**
     * 批量添加数据
     * batch load the hashmap list
     * 如果原先未开启事务（BeginTransaction），则按照每1000条记录约定，进行自动提交，失败则之前提交的不能回滚，发生错误的这批数据会被回滚
     * if start no transaction (not BeginTransaction) then the process will auto commit very 1000 records and can not rollback the committed records,the no commit data will rollback.
     * 如果原先已经开启事务（BeginTransaction），则批处理不做自动提交，由外部统一管理事务提交与回滚
     * if start transaction (BeginTransaction) then the process will no auto commit, and the transaction will be managed by main IanMySQL instance
     * @param ps 批量参数数据 the hashmap data
     * @throws Exception
     */
    public void BatchAdd(List<HashMap<String, Object>> ps) throws Exception {
        this.BatchAdd(ps,1000);
    }

    /**
     * 批量添加数据
     * batch load the hashmap list with commit record's count
     * 如果原先未开启事务（BeginTransaction），则按照commitRows约定，进行自动提交，失败则之前提交的不能回滚，发生错误的这批数据会被回滚
     * if start no transaction (not BeginTransaction) then the process will auto commit very commitRows records and can not rollback the committed records,the no commit data will rollback.
     * 如果原先已经开启事务（BeginTransaction），则批处理不做自动提交，由外部统一管理事务提交与回滚
     * if start transaction (BeginTransaction) then the process will no auto commit, and the transaction will be managed by main IanMySQL instance
     * @param ps 批量参数数据 the hashmap data
     * @param commitRows 每多少条提交一次，对于已开启事务的处理，没有作用 if 
     * @throws Exception
     */
    public void BatchAdd(List<HashMap<String, Object>> ps, int commitRows) throws Exception {
        if(ps==null) ps=new ArrayList<HashMap<String, Object>>();
        if(ps.size()==0) throw new Exception("批处理参数数据为空");
        if(commitRows<=0) commitRows=1000;
        String sql = this.GetSQL(false, false);
        Map<Integer, String> ms=this.parseSQL(sql);
        this.LogInfo("批处理增加数据("+ps.size()+")，每"+commitRows+"条提交一次");
        String sql1=ms.get(0);
        PreparedStatement stm=null;
        boolean b=this.Connection.getAutoCommit();
        boolean remain=false;
        try
        {
            if(b) this.Connection.setAutoCommit(false);
            stm=this.Connection.prepareStatement(sql1);
            this.LogInfo(this.Guid+" 执行SQL:"+sql1);
            int idx=0;
            for(Map<String, Object> p:ps){
                idx++;
                StringBuilder sb=new StringBuilder();
                sb.append(this.Guid+" 第"+idx+"行，执行SQL参数:\r\n");
                for(Integer id:ms.keySet()){
                    if(id==0) continue;
                    String key=ms.get(id);
                    if(!p.containsKey(key)) throw new Exception("第"+idx+"条参数记录，未发现名为"+key+"的参数设置");
                    Object val=p.get(key);
                    stm.setObject(id,val);

                    sb.append(key+"="+IanConvert.ToString(val)+"\r\n");
                }
                this.LogInfo(this.Guid+" 完成第"+idx+"条记录数据加入("+sb.toString()+")");
                stm.addBatch();
                remain=true;
                if(idx%commitRows==0){
                    if(b){
                        stm.executeBatch();
                        if(b) {
                            this.Connection.commit();
                            this.LogInfo(this.Guid+" 在第"+idx+"条记录数据加入后提交");
                        }
                        stm.clearBatch();
                        remain=false;
                    }
                }
            }
            if(remain){
                stm.executeBatch();
                if(b) {
                    this.Connection.commit();
                    this.LogInfo(this.Guid+" 在第"+idx+"条记录数据加入，最后提交");
                }
                stm.clearBatch();
                remain=false;
            }
        }
        catch (Exception e)
        {
            this.LogError(this.Guid+" 执行SQL:"+sql1+" 失败:"+e.getMessage()+"\r\n"+IanStringTool.GetExceptionStackInfo(e));
            throw e;
        }
        finally
        {
            if (stm != null) {
                stm.close();
                stm=null;
            }
            if(b) this.Connection.setAutoCommit(true);
        }
    }

    /**
     * 按SQL语句获取T的列表
     * T必须有无参数的构造函数
     * @param t T的类型
     * @param <T> T的泛型
     * @return T的列表
     * @throws Exception
     */
    public <T> List<T> GetList(Class<T> t) throws Exception {
        return this.GetList(t,null);
    }

    /**
     * 按SQL语句获取T的列表
     * T必须有无参数的构造函数
     * @param replaceCols 用于字段替换的对应字典表 Key是类的字段名，Value是数据库查询结果里的列名
     * @param t T的类型 如果字段为枚举类型，仅对使用EnumLabel定义value值得字段进行赋值处理
     * @param <T> T的泛型
     * @return T的列表
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> GetList(Class<T> t, HashMap<String, String> replaceCols) throws Exception {
        if(replaceCols==null) replaceCols=new HashMap<String, String>();
        boolean bIsBaseType=false;
        try{
            bIsBaseType=((Class)(t.getField("TYPE").get(null))).isPrimitive();
        }catch (Exception e1){

        }
        if(t.getTypeName().equals("java.lang.String")) bIsBaseType=true;
        if(t.getTypeName().equals("java.util.Date")||t.getTypeName().equals("java.sql.Date")||t.getTypeName().equals("java.sql.Timestamp")) bIsBaseType=true;
        HashMap<String, String> rcs=new HashMap<String, String>();
        for(String key:replaceCols.keySet()){
            rcs.put(key.toLowerCase(),replaceCols.get(key).toLowerCase());
        }
        String sql = this.GetSQL(true, true);
        Map<Integer, String> ms=this.parseSQL(sql);
        this.LogInfo(this.Guid+" 按类型查询SQL:"+sql);
        String sql1=ms.get(0);
        PreparedStatement stm=null;
        List<T> ls=new ArrayList<T>();
        try
        {
            stm=this.Connection.prepareStatement(sql1);
            for(Integer id:ms.keySet()){
                if(id==0) continue;
                String key=ms.get(id);
                if(!this._SqlParameters.containsKey(key)) throw new Exception("未发现名为"+key+"的参数设置");
                Object val=this._SqlParameters.get(key);
                stm.setObject(id,val);
                this.LogInfo(this.Guid+" 按类型查询SQL参数:@"+key+"="+IanConvert.ToString(val));
            }
            ResultSet rs=stm.executeQuery();
            ResultSetMetaData md=rs.getMetaData();
            int colCnt=md.getColumnCount();
            Map<Integer, String> cs=new HashMap<Integer, String>();
            for(int i=1;i<=colCnt;i++){
                cs.put(i,md.getColumnLabel(i));
            }
            if(cs.size()==0) throw new Exception("没有检索到字段");
            Map<Integer, Field> mfs=new HashMap<Integer, Field>();
            this.QueryResultColumns.clear();
            if(bIsBaseType){
                this.QueryResultColumns.put(1,cs.get(1));
            }else{
                Field[] fs=t.getFields();
                for(Field f:fs){
                    String fname=f.getName().toLowerCase();
                    if(rcs.containsValue(fname)){
                        for(String rckey:rcs.keySet()){
                            if(rcs.get(rckey).equals(fname)){
                                fname=rckey;
                                break;
                            }
                        }
                    }
                    for(Integer id:cs.keySet()){
                        if(cs.get(id).toLowerCase().equals(fname)){
                            mfs.put(id,f);
                            this.QueryResultColumns.put(id,cs.get(id));
                            break;
                        }
                    }
                }
            }
            while(rs.next()){
                if(bIsBaseType){
                    T v;
                    if(t.getTypeName().equals("java.util.Date")||t.getTypeName().equals("java.sql.Date")||t.getTypeName().equals("java.sql.Timestamp")){
                        v=(T)IanConvert.ToDate(rs.getTimestamp(1),true);
                    }else{
                        v=(T)rs.getObject(1);
                    }
                    ls.add(v);
                }else{
                    T v=t.newInstance();
                    for(Integer id:mfs.keySet()){
                        Field f=mfs.get(id);
                        if(f.getType().isEnum()){
                            String cname=f.getGenericType().getTypeName();
                            Class<Enum> ecls = (Class<Enum>) Class.forName(cname);
                            boolean islong=false;
                            boolean bfind=false;
                            long lval=0;
                            String sval="";
                            for(Enum v1:ecls.getEnumConstants()){
                                Field f1 =ecls.getDeclaredField(v1.name());
                                Enum a=(Enum)f.get(v);
                                if(v1==a){
                                    IanEnumLabel el=f1.getAnnotation(IanEnumLabel.class);
                                    if(el.svalue().length()>0){
                                        sval=rs.getString(id);
                                        islong=false;
                                    }else{
                                        lval=rs.getLong(id);
                                        islong=true;
                                    }
                                    bfind=true;
                                    break;
                                }
                            }
                            if(bfind){
                                if(islong){
                                    f.set(v, IanEnumLabelOpt.GetEnumByValue(ecls,lval));
                                }else{
                                    f.set(v, IanEnumLabelOpt.GetEnumByValue(ecls,sval));
                                }
                            }else{
                                continue;
                            }
                        }else if(!f.isSynthetic()){
                            f.setAccessible(true);
                            String ftype=f.getGenericType().getTypeName();
                            switch (ftype) {
                                case "java.lang.String":
                                    f.set(v,rs.getString(id));
                                    break;
                                case "java.lang.Integer":
                                case "java.lang.Character":
                                case "int":
                                case "char":
                                    f.set(v,rs.getInt(id));
                                    break;
                                case "java.lang.Long":
                                case "long":
                                    f.set(v,rs.getLong(id));
                                    break;
                                case "java.lang.Byte":
                                case "byte":
                                    f.set(v,rs.getByte(id));
                                    break;
                                case "java.lang.Short":
                                case "short":
                                    f.set(v,rs.getShort(id));
                                    break;
                                case "java.lang.Float":
                                case "float":
                                    f.set(v,rs.getFloat(id));
                                    break;
                                case "java.lang.Double":
                                case "double":
                                    f.set(v,rs.getDouble(id));
                                    break;
                                case "java.lang.boolean":
                                case "boolean":
                                    f.set(v,rs.getBoolean(id));
                                    break;
                                case "java.util.Date":
                                case "java.sql.Date":
                                case "java.sql.Timestamp":
                                    f.set(v,IanConvert.ToDate(rs.getTimestamp(id),true));
                                    break;
                                default:
                                    break;
                            }
                            f.setAccessible(false);
                        }else{
                            continue;
                        }
                    }
                    ls.add(v);
                }
            }
        }
        catch (Exception e)
        {
            this.LogError(this.Guid+" 按类型查询SQL:"+sql1+" 失败:"+e.getMessage()+"\r\n"+IanStringTool.GetExceptionStackInfo(e));
            throw e;
        }
        finally
        {
            if (stm != null) {
                stm.close();
                stm=null;
            }
        }
        return ls;
    }

    /**
     * 按SQL语句获取查询结果列表
     * @return 查询结果列表，每一行为一个HashMap，以查询字段名为Key，值为Value
     * @throws Exception
     */
    public List<HashMap<String, Object>> GetList() throws Exception {
        String sql = this.GetSQL(true, true);
        Map<Integer, String> ms=this.parseSQL(sql);
        String sql1=ms.get(0);
        PreparedStatement stm=null;
        List<HashMap<String, Object>> ls=new ArrayList<HashMap<String, Object>>();
        try
        {
            stm=this.Connection.prepareStatement(sql1);
            this.LogInfo(this.Guid+" 无类型查询SQL:"+sql1);
            for(Integer id:ms.keySet()){
                if(id==0) continue;
                String key=ms.get(id);
                if(!this._SqlParameters.containsKey(key)) throw new Exception("未发现名为"+key+"的参数设置");
                Object val=this._SqlParameters.get(key);
                stm.setObject(id,val);
                this.LogInfo(this.Guid+" 无类型查询SQL参数:@"+key+"="+IanConvert.ToString(val));
            }
            ResultSet rs=stm.executeQuery();
            ResultSetMetaData md=rs.getMetaData();
            int colCnt=md.getColumnCount();
            this.QueryResultColumns.clear();
            {
                for(int i=1;i<=colCnt;i++){
                    this.QueryResultColumns.put(i,md.getColumnLabel(i));
                }
            }
            while(rs.next()){
                HashMap<String, Object> v=new HashMap<String, Object>();
                for(int i=1;i<=colCnt;i++){
                    if(rs.getObject(i) instanceof java.util.Date ||rs.getObject(i) instanceof java.sql.Date || rs.getObject(i) instanceof Timestamp){
                        v.put(md.getColumnLabel(i),IanConvert.ToDate(rs.getTimestamp(i),true));
                    }else{
                        v.put(md.getColumnLabel(i),rs.getObject(i));
                    }
                }
                ls.add(v);
            }
        }
        catch (Exception e)
        {
            this.LogError(this.Guid+" 无类型查询SQL:"+sql1+" 失败:"+e.getMessage()+"\r\n"+IanStringTool.GetExceptionStackInfo(e));
            throw e;
        }
        finally
        {
            if (stm != null) {
                stm.close();
                stm=null;
            }
        }
        return ls;
    }

    /**
     * 查询一条一个整型结果
     * @return 整型结果
     * @throws Exception
     */
    public int SelectInt() throws Exception {
        String sql = this.GetSQL(true, true);
        Map<Integer, String> ms=this.parseSQL(sql);
        String sql1=ms.get(0);
        PreparedStatement stm=null;
        int r=0;
        try
        {
            stm=this.Connection.prepareStatement(sql1);
            this.LogInfo(this.Guid+" 单条Int查询SQL:"+sql1);
            for(Integer id:ms.keySet()){
                if(id==0) continue;
                String key=ms.get(id);
                if(!this._SqlParameters.containsKey(key)) throw new Exception("未发现名为"+key+"的参数设置");
                Object val=this._SqlParameters.get(key);
                stm.setObject(id,val);
                this.LogInfo(this.Guid+" 单条Int查询SQL参数:@"+key+"="+IanConvert.ToString(val));
            }
            ResultSet rs=stm.executeQuery();
            boolean b=false;
            while(rs.next()){
                r=rs.getInt(1);
                b=true;
            }
            if(!b) throw new Exception("未查询到记录");
            this.LogInfo(this.Guid+" 单条Int查询SQL返回结果:"+r);
        }
        catch (Exception e)
        {
            this.LogError(this.Guid+" 单条Int查询SQL:"+sql1+" 失败:"+e.getMessage()+"\r\n"+IanStringTool.GetExceptionStackInfo(e));
            throw e;
        }
        finally
        {
            if (stm != null) {
                stm.close();
                stm=null;
            }
        }
        return r;
    }

    /**
     * 查询一条一个字符串结果
     * @return 字符串结果
     * @throws Exception
     */
    public String SelectString() throws Exception {
        String sql = this.GetSQL(true, true);
        Map<Integer, String> ms=this.parseSQL(sql);
        String sql1=ms.get(0);
        PreparedStatement stm=null;
        String r="";
        try
        {
            stm=this.Connection.prepareStatement(sql1);
            this.LogInfo(this.Guid+" 单条String查询SQL:"+sql1);
            for(Integer id:ms.keySet()){
                if(id==0) continue;
                String key=ms.get(id);
                if(!this._SqlParameters.containsKey(key)) throw new Exception("未发现名为"+key+"的参数设置");
                Object val=this._SqlParameters.get(key);
                stm.setObject(id,val);
                this.LogInfo(this.Guid+" 单条String查询SQL参数:@"+key+"="+IanConvert.ToString(val));
            }
            ResultSet rs=stm.executeQuery();
            boolean b=false;
            while(rs.next()){
                r=rs.getString(1);
                b=true;
            }
            if(!b) throw new Exception("未查询到记录");
            this.LogInfo(this.Guid+" 单条String查询SQL返回结果:"+r);
        }
        catch (Exception e)
        {
            this.LogError(this.Guid+" 单条String查询SQL:"+sql1+" 失败:"+e.getMessage()+"\r\n"+IanStringTool.GetExceptionStackInfo(e));
            throw e;
        }
        finally
        {
            if (stm != null) {
                stm.close();
                stm=null;
            }
        }
        return r;
    }

    /**
     * 调用存储过程
     * @param strProcedureName 存储过程名
     * @param parameterList 参数类型列表
     * @return 存储过程返回
     */
    public HashMap<String, Object> ExecProcedure(String strProcedureName, List<IanProcedureParameter> parameterList) throws Exception {
        if(parameterList==null) parameterList=new ArrayList<IanProcedureParameter>();
        strProcedureName= IanStringTool.Check(strProcedureName,"存储过程名称");
        CallableStatement stm=null;
        String sql1="call "+strProcedureName;
        HashMap<String, Object> rs=new HashMap<String, Object>();
        try{
            String s="(";
            for(IanProcedureParameter p : parameterList){
                s+="?,";
            }
            if(s.indexOf(",")>0){
                s=s.substring(0,s.length() - 1);
            }
            s+=")";
            sql1+=s;
            stm=this.Connection.prepareCall(sql1);
            int idx=1;
            HashMap<Integer, String> rs1=new HashMap<Integer, String>();
            StringBuilder sps=new StringBuilder();
            for(IanProcedureParameter p:parameterList){
                sps.append("参数"+idx+":");
                if(p.flag== IanProcedureParameterFlagEnum.IN){
                    stm.setObject(idx,p.value);
                    sps.append("输入,"+IanConvert.ToString(p.value));
                }else if(p.flag== IanProcedureParameterFlagEnum.OUT){
                    stm.setObject(idx,p.value);
                    stm.registerOutParameter(idx,p.type);
                    rs1.put(idx,p.name);
                    sps.append("输出,"+IanConvert.ToString(p.name));
                }else{
                    stm.registerOutParameter(idx,p.type);
                    rs1.put(idx,p.name);
                    sps.append("输入输出,"+IanConvert.ToString(p.name));
                }
                sps.append("\r\n");
                idx++;
            }
            this.LogInfo("执行存储过程:"+sql1);
            this.LogInfo(sps.toString());
            stm.execute();
            for(int id:rs1.keySet()){
                String pname=rs1.get(id);
                rs.put(pname,stm.getObject(id));
                this.LogInfo("获取结果:"+pname+"="+rs.get(pname));
            }
        }catch (Exception e){
            this.LogError(this.Guid+" 调用存储过程:"+sql1+" 参数"+IanProcedureParameter.getLabels(parameterList)+" 失败:"+e.getMessage()+"\r\n"+IanStringTool.GetExceptionStackInfo(e));
            throw e;
        }finally {
            if(stm!=null){
                stm.close();
                stm=null;
            }
        }
        return rs;
    }

    /**
     * 开始事务
     */
    public void BeginTransaction() throws SQLException {
        this.Connection.setAutoCommit(false);
        this.LogInfo(this.Guid+"开始事务");
    }

    /**
     * 提交事务
     * @throws SQLException
     */
    public void Commit() throws SQLException {
        this.Connection.commit();;
        this.Connection.setAutoCommit(true);
        this.LogInfo(this.Guid+"提交事务");
    }

    /**
     * 回滚事务
     * @throws SQLException
     */
    public void Rollback() throws SQLException {
        this.Connection.rollback();;
        this.Connection.setAutoCommit(true);
        this.LogInfo(this.Guid+"回滚事务");
    }
}
