package pro.ianchen;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MySQL operator prototype
 */
public class IanMySQL {
    /**
     * log delegate function
     */
    public Consumer<String> Log=null;

    /**
     * normal log
     * @param s log content
     */
    public void LogInfo(String s){
        if(this.Log!=null){
            String log=IanConvert.FormatDate(IanStringTool.Now(),"yyyyMMddHHmmssSSS")+"\tnormalLog\t"+IanStringTool.Deal(s,"");
            this.Log.accept(log);
        }
    }


    /**
     * error log
     * @param s log content
     */
    void LogError(String s){
        if(this.Log!=null){
            String log=IanConvert.FormatDate(IanStringTool.Now(),"yyyyMMddHHmmssSSS")+"\terrorLog\t"+IanStringTool.Deal(s,"");
            this.Log.accept(log);
        }
    }

    /**
     * mark guid
     * distinguish different log contents in multithreaded environment
     */
    public String Guid= IanStringTool.Guid2();

    /**
     * database source
     */
    public javax.sql.DataSource DataSource=null;

    /**
     * database connection
     */
    public java.sql.Connection Connection=null;

    /**
     * confirm if database operation is automatically committed
     * @return true or false
     */
    public boolean IsAutoCommit() throws SQLException {
        if(this.Connection==null) return true;
        return this.Connection.getAutoCommit();
    }

    /**
     * database connection timeout limit
     */
    public int SQLTimeOut = 720;

    /**
     * query result columns
     */
    public Map<Integer, String> QueryResultColumns=new HashMap<Integer, String>();

    /**
     * the IanMySQL instance can create child instance
     * parent and child instance share database connection to save resources and control transaction
     * all child instances are stored in this list
     */
    protected List<IanMySQL> _mySqlWheres=new ArrayList<IanMySQL>();

    /**
     * add IanMySQL instance to the MySQL instance list
     * @param m MySQL child instance, or IanMySQL child instance, in this case
     */
    private void AddIanMySql(IanMySQL m){
        if(m!=null){
            this._mySqlWheres.add(m);
            this.LogInfo("IanMySql instance(" + this.Guid + ")add child instance " + m.Guid);
        }
    }

    /**
     * constructor
     * get database connection from datasource (the database connection pool)
     * @throws Exception
     */
    public IanMySQL(javax.sql.DataSource ds) throws Exception {
        if(ds==null) throw new Exception("dataSource cannot be empty");
        this.DataSource=ds;
        this.Connection=this.DataSource.getConnection();
        this.LogInfo(this.Guid+"create IanMySql instance and assign database connection");
    }

    /**
     * constructor
     * if specified connection is null, get connection from database source (the database connection pool)
     * if specified connection is not null, get specified connection
     * @param conn the specified connection
     * @param ds the datasource (the database connection pool)
     * @throws Exception
     */
    private IanMySQL(java.sql.Connection conn, javax.sql.DataSource ds) throws Exception {
        if(ds==null) throw new Exception("dataSource cannot be empty");
        this.DataSource=ds;
        if(conn!=null){
            this.Connection=conn;
        }else{
            this.Connection=ds.getConnection();
        }
    }

    /**
     * create IanMySQL child instance
     * notice the child instance and the parent instance use the same database connection
     * @return return the created IanMySQL child instance
     * @throws Exception
     */
    public IanMySQL CreateIanMySql() throws Exception {
        IanMySQL sql=new IanMySQL(this.Connection,this.DataSource);
        sql.Log=this.Log;
        this.AddIanMySql(sql);
        return sql;
    }

    /**
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
        this.LogInfo("release database connection"+this.Guid);
    }

    /**
     * the main SQL statement
     */
    public String Sql="";

    /**
     * private where condition
     */
    protected List<IanKeyPair<IanLogicalRelationEnum, String>> _Wheres=new ArrayList<IanKeyPair<IanLogicalRelationEnum,String>>();

    /**
     * private Sql parameters list
     */
    protected Map<String, Object> _SqlParameters=new HashMap<String, Object>();

    /**
     * private order statement
     */
    protected String _OrderBy = "";

    /**
     * private group by statement
     */
    protected String _GroupBy = "";

    /**
     * private limit statement
     */
    protected String _Limit = "";

    /**
     * reset Sql、OrderBy、GroupBy、Limit statement field to empty
     * clear all conditions
     * clear all parameters
     * clear all child instances
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
     * add where condition statement
     * @param logicalRelation logical relation for sql
     * @param whereSql where statement for sql
     * @throws Exception
     */
    public void AddWhere(IanLogicalRelationEnum logicalRelation, String whereSql) throws Exception {
        whereSql= IanStringTool.Check(whereSql,"where condition in Sql");
        Matcher m= Pattern.compile(".*(\\s+)or(\\s+).*").matcher(whereSql.toLowerCase());
        if(m.matches()){
            whereSql="("+whereSql+")";
        }
        this._Wheres.add(new IanKeyPair<IanLogicalRelationEnum,String>(logicalRelation,whereSql));
    }

    /**
     * add where condition statement
     * default logical relation: and
     * @param whereSql where statement for sql
     * @throws Exception
     */
    public void AddWhere(String whereSql) throws Exception {
        this.AddWhere(IanLogicalRelationEnum.And,whereSql);
    }

    /**
     * set the order by statement in sql, not including "order by"
     * @param orderBy order by statement
     */
    public void SetOrderBy(String orderBy){
        orderBy= IanStringTool.Deal(orderBy,"");
        this._OrderBy=orderBy;
    }

    /**
     * set the limit statement in sql, not including "limit"
     * @param limit limit statement
     */
    public void SetLimit(String limit){
        limit= IanStringTool.Deal(limit,"");
        this._Limit=limit;
    }

    /**
     * set the group by statement in sql, not including "group by"
     * @param groupBy group by statement
     */
    public void SetGroupBy(String groupBy){
        groupBy= IanStringTool.Deal(groupBy,"");
        this._GroupBy=groupBy;
    }

    /**
     * add MySql Parameter name and value
     * the parameter name needs to start with character "@" in order to correspond to a variable in sql statement
     * @param name parameter name
     * @param value parameter value
     * @throws Exception
     */
    public void AddSqlParamter(String name, Object value) throws Exception {
        name= IanStringTool.Check(name,"parameter name");
        if(!name.startsWith("@")) throw new Exception("parameter name starts with @");
        if(value instanceof Date &&((Date)value).getTime()==Long.MAX_VALUE){
            this._SqlParameters.put(name,null);
        }else{
            this._SqlParameters.put(name,value);
        }
    }

    /**
     * get sql statement
     * include order by statement by default
     * exclude group by statement by default
     * @return sql statement
     */
    protected String GetSQL(){
        return this.GetSQL(true);
    }

    /**
     * get sql statement
     * exclude group by statement by default
     * @param isOrder whether include order by statement or not
     * @return sql statement
     */
    protected String GetSQL(boolean isOrder){
        return this.GetSQL(isOrder,false);
    }

    /**
     * get the sql statement
     * @param order whether include order by statement or not
     * @param group whether include group by statement or not
     * @return sql statement
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
     * convert parameter (start with character "@") to "?" (parameter in standard mysql usage) expression
     * doesn't convert empty parameters in sql statement
     * @param sql sql statement needs to be converted
     * @return
     * map.get(0) is the converted sql statement
     * map index 1 to map.size()-1 specifies parameter information
     * map Key specifies the position of "?" in sql statement
     * map Value represents the value of Key for this._SqlParameters
     */
    private Map<Integer, String> parseSQL(String sql){
        String regex = "(@(\\w+))";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(sql);
        int idx=1;
        Map<Integer, String> ms=new HashMap<>();
        while (m.find()) {
            //number the Key in case there's repetition in parameters
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
                        this.LogInfo("param "+i+"("+ms.get(i)+"):parameter "+ms.get(i)+" not found");
                    }
                }
            }
        }
        return ms;
    }

    /**
     * execute sql statement
     * @throws Exception
     */
    public void ExecuteCmd() throws Exception {
        this.ExecuteCmd(true);
    }

    /**
     * execute sql statement
     * @param needExecute confirm sql statement execution
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
                this.LogInfo(this.Guid+" execute SQL:"+sql1);
                stm=this.Connection.createStatement();
                if(needExecute){
                    stm.execute(sql1);
                }
            }
            catch (Exception e)
            {
                this.LogError(this.Guid+" execute SQL:"+sql1+" failed:"+e.getMessage()+"\r\n"+IanStringTool.GetExceptionStackInfo(e));
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
                this.LogInfo(this.Guid+" execute SQL:"+sql1);
                stm=this.Connection.prepareStatement(sql1);
                for(Integer id:ms.keySet()){
                    if(id==0) continue;
                    String key=ms.get(id);
                    if(!this._SqlParameters.containsKey(key)) throw new Exception("parameter information "+key+" not found");
                    Object val=this._SqlParameters.get(key);
                    stm.setObject(id,val);
                    this.LogInfo(this.Guid+" execute SQL parameter:@"+key+"="+IanConvert.ToString(val));
                }
                if(needExecute){
                    stm.executeUpdate();
                }
            }
            catch (Exception e)
            {
                this.LogError(this.Guid+" execute SQL:"+sql1+" failed:"+e.getMessage()+"\r\n"+IanStringTool.GetExceptionStackInfo(e));
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
     * batch loading specified type list
     * @param c the specified type
     * @param ts the specified type list
     * @param <T> the specified type
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
     * batch loading the hashmap list
     * if already started no transaction (BeginTransaction), the process will auto commit every 1000 records. Committed records cannot be rolled back; only not committed data can roll back.
     * if already started the transaction (BeginTransaction), the process will not auto commit records, and the transaction will be managed by main IanMySQL instance
     * @param ps the hashmap data
     * @throws Exception
     */
    public void BatchAdd(List<HashMap<String, Object>> ps) throws Exception {
        this.BatchAdd(ps,1000);
    }

    /**
     * batch loading the hashmap list with commit record's count
     * if already started no transaction (BeginTransaction), the process will auto commit commitRows records and can not roll back the committed records; only not committed data can roll back.
     * if already started transaction (BeginTransaction), the process will not auto commit, and the transaction will be managed by main IanMySQL instance
     * @param ps the hashmap data
     * @param commitRows transactions already en route will not be committed automatically
     * @throws Exception
     */
    public void BatchAdd(List<HashMap<String, Object>> ps, int commitRows) throws Exception {
        if(ps==null) ps=new ArrayList<HashMap<String, Object>>();
        if(ps.size()==0) throw new Exception("empty batch loading parameter");
        if(commitRows<=0) commitRows=1000;
        String sql = this.GetSQL(false, false);
        Map<Integer, String> ms=this.parseSQL(sql);
        this.LogInfo("batch adding data ("+ps.size()+")，submit every "+commitRows+" records");
        String sql1=ms.get(0);
        PreparedStatement stm=null;
        boolean b=this.Connection.getAutoCommit();
        boolean remain=false;
        try
        {
            if(b) this.Connection.setAutoCommit(false);
            stm=this.Connection.prepareStatement(sql1);
            this.LogInfo(this.Guid+" execute SQL:"+sql1);
            int idx=0;
            for(Map<String, Object> p:ps){
                idx++;
                StringBuilder sb=new StringBuilder();
                sb.append(this.Guid+" at line number "+idx+", SQL execution:\r\n");
                for(Integer id:ms.keySet()){
                    if(id==0) continue;
                    String key=ms.get(id);
                    if(!p.containsKey(key)) throw new Exception("at record number "+idx+", parameter information "+key+" not found");
                    Object val=p.get(key);
                    stm.setObject(id,val);

                    sb.append(key+"="+IanConvert.ToString(val)+"\r\n");
                }
                this.LogInfo(this.Guid+" confirm: record number "+idx+" has been added. ("+sb.toString()+")");
                stm.addBatch();
                remain=true;
                if(idx%commitRows==0){
                    if(b){
                        stm.executeBatch();
                        if(b) {
                            this.Connection.commit();
                            this.LogInfo(this.Guid+" submit after "+idx+" record(s) added");
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
                    this.LogInfo(this.Guid+" add after record number "+idx+" and submit");
                }
                stm.clearBatch();
                remain=false;
            }
        }
        catch (Exception e)
        {
            this.LogError(this.Guid+" execute SQL:"+sql1+" failed:"+e.getMessage()+"\r\n"+IanStringTool.GetExceptionStackInfo(e));
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
     * execute the sql script and convert the result to the T list
     * T must have a constructor without parameter
     * @param t T's type
     * @param <T> generic type T
     * @return the T list
     * @throws Exception
     */
    public <T> List<T> GetList(Class<T> t) throws Exception {
        return this.GetList(t,null);
    }

    /**
     * execute the sql script and convert the result to the T list
     * T must have a constructor without parameters
     * @param replaceCols the dictionary to map the column relation
     *                    dictionary key is class field
     *                    dictionary value is the field in the query result from database
     * @param t generic type T
     *          if field is Enum type, the value (database table field) will be dealt only in the case that the class field used EnumLabel
     * @param <T> generic type T
     * @return the T list
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
        this.LogInfo(this.Guid+" search SQL by type:"+sql);
        String sql1=ms.get(0);
        PreparedStatement stm=null;
        List<T> ls=new ArrayList<T>();
        try
        {
            stm=this.Connection.prepareStatement(sql1);
            for(Integer id:ms.keySet()){
                if(id==0) continue;
                String key=ms.get(id);
                if(!this._SqlParameters.containsKey(key)) throw new Exception("parameter information "+key+" not found");
                Object val=this._SqlParameters.get(key);
                stm.setObject(id,val);
                this.LogInfo(this.Guid+" search SQL parameter by type:@"+key+"="+IanConvert.ToString(val));
            }
            ResultSet rs=stm.executeQuery();
            ResultSetMetaData md=rs.getMetaData();
            int colCnt=md.getColumnCount();
            Map<Integer, String> cs=new HashMap<Integer, String>();
            for(int i=1;i<=colCnt;i++){
                cs.put(i,md.getColumnLabel(i));
            }
            if(cs.size()==0) throw new Exception("result not found");
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
            this.LogError(this.Guid+" search SQL by type:"+sql1+" failed:"+e.getMessage()+"\r\n"+IanStringTool.GetExceptionStackInfo(e));
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
     * execute sql script and convert the result to the hashmap list
     * @return the query result list; every row is a hashmap, and the query field name is hashmap key; the corresponding query field value is hashmap value
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
            this.LogInfo(this.Guid+" no SQL selection:"+sql1);
            for(Integer id:ms.keySet()){
                if(id==0) continue;
                String key=ms.get(id);
                if(!this._SqlParameters.containsKey(key)) throw new Exception("parameter information "+key+" not found");
                Object val=this._SqlParameters.get(key);
                stm.setObject(id,val);
                this.LogInfo(this.Guid+" no SQL selection parameter:@"+key+"="+IanConvert.ToString(val));
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
            this.LogError(this.Guid+" no SQL selection:"+sql1+" failed:"+e.getMessage()+"\r\n"+IanStringTool.GetExceptionStackInfo(e));
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
     * query single int value result
     * @return int value
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
            this.LogInfo(this.Guid+" single Int SQL selection:"+sql1);
            for(Integer id:ms.keySet()){
                if(id==0) continue;
                String key=ms.get(id);
                if(!this._SqlParameters.containsKey(key)) throw new Exception("parameter information "+key+" not found");
                Object val=this._SqlParameters.get(key);
                stm.setObject(id,val);
                this.LogInfo(this.Guid+" single Int SQL selection parameter:@"+key+"="+IanConvert.ToString(val));
            }
            ResultSet rs=stm.executeQuery();
            boolean b=false;
            while(rs.next()){
                r=rs.getInt(1);
                b=true;
            }
            if(!b) throw new Exception("record not found");
            this.LogInfo(this.Guid+" single Int SQL selection result:"+r);
        }
        catch (Exception e)
        {
            this.LogError(this.Guid+" single Int SQL selection:"+sql1+" failed:"+e.getMessage()+"\r\n"+IanStringTool.GetExceptionStackInfo(e));
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
     * query single string value result
     * @return string value
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
            this.LogInfo(this.Guid+" single String SQL selection:"+sql1);
            for(Integer id:ms.keySet()){
                if(id==0) continue;
                String key=ms.get(id);
                if(!this._SqlParameters.containsKey(key)) throw new Exception("parameter information "+key+" not found");
                Object val=this._SqlParameters.get(key);
                stm.setObject(id,val);
                this.LogInfo(this.Guid+" single String SQL selection parameter:@"+key+"="+IanConvert.ToString(val));
            }
            ResultSet rs=stm.executeQuery();
            boolean b=false;
            while(rs.next()){
                r=rs.getString(1);
                b=true;
            }
            if(!b) throw new Exception("record not found");
            this.LogInfo(this.Guid+" single String SQL selection result:"+r);
        }
        catch (Exception e)
        {
            this.LogError(this.Guid+" single String SQL selection:"+sql1+" failed:"+e.getMessage()+"\r\n"+IanStringTool.GetExceptionStackInfo(e));
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
     * call database procedure
     * @param strProcedureName procedure name
     * @param parameterList procedure parameters list
     * @return result of the procedure return
     */
    public HashMap<String, Object> ExecProcedure(String strProcedureName, List<IanProcedureParameter> parameterList) throws Exception {
        if(parameterList==null) parameterList=new ArrayList<IanProcedureParameter>();
        strProcedureName= IanStringTool.Check(strProcedureName,"procedure name");
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
                sps.append("parameter"+idx+":");
                if(p.flag== IanProcedureParameterFlagEnum.IN){
                    stm.setObject(idx,p.value);
                    sps.append("input,"+IanConvert.ToString(p.value));
                }else if(p.flag== IanProcedureParameterFlagEnum.OUT){
                    stm.setObject(idx,p.value);
                    stm.registerOutParameter(idx,p.type);
                    rs1.put(idx,p.name);
                    sps.append("output,"+IanConvert.ToString(p.name));
                }else{
                    stm.registerOutParameter(idx,p.type);
                    rs1.put(idx,p.name);
                    sps.append("input and output,"+IanConvert.ToString(p.name));
                }
                sps.append("\r\n");
                idx++;
            }
            this.LogInfo("execute procedure:"+sql1);
            this.LogInfo(sps.toString());
            stm.execute();
            for(int id:rs1.keySet()){
                String pname=rs1.get(id);
                rs.put(pname,stm.getObject(id));
                this.LogInfo("get result:"+pname+"="+rs.get(pname));
            }
        }catch (Exception e){
            this.LogError(this.Guid+" get procedure:"+sql1+" parameter"+IanProcedureParameter.getLabels(parameterList)+" failed:"+e.getMessage()+"\r\n"+IanStringTool.GetExceptionStackInfo(e));
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
     * start transaction
     */
    public void BeginTransaction() throws SQLException {
        this.Connection.setAutoCommit(false);
        this.LogInfo(this.Guid+"start transaction");
    }

    /**
     * commit transaction
     * @throws SQLException
     */
    public void Commit() throws SQLException {
        this.Connection.commit();;
        this.Connection.setAutoCommit(true);
        this.LogInfo(this.Guid+"commit transaction");
    }

    /**
     * rollback transaction
     * @throws SQLException
     */
    public void Rollback() throws SQLException {
        this.Connection.rollback();;
        this.Connection.setAutoCommit(true);
        this.LogInfo(this.Guid+"roll back transaction");
    }
}
