package pro.ianchen;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.*;

public class IanMySQLTest {

    HikariDataSource _dataSource=null;

    @Before
    public void setUp() {
        try{
            IanMySQLDataSource ds=new IanMySQLDataSource();
            this._dataSource=ds.GetHikariDataSource("jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf-8&serverTimezone=Asia/Shanghai","root","cs");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        try{
            if(this._dataSource!=null){
                this._dataSource.close();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void ShowLog(String s){
        System.out.println(s);
    }

    @Test
    public void doNormalSqlTest() throws SQLException {
        IanMySQL sql=null;
        try{
            sql=new IanMySQL(this._dataSource);
            sql.Log= this::ShowLog;
            boolean b=false;
            {
                IanMySQL sql2=sql.CreateIanMySql();
                sql2.Sql="select count(*) cnt from information_schema.tables where table_name=@tname";
                sql2.AddSqlParamter("@tname","temp_1");
                b=sql2.SelectInt()>0;
            }
            if(b){
                IanMySQL sql2=sql.CreateIanMySql();
                sql2.Sql="drop table temp_1";
                sql2.ExecuteCmd();
            }
            {
                IanMySQL sql2=sql.CreateIanMySql();
                sql2.Sql="create table temp_1 (id bigint not null auto_increment primary key,name varchar(100) not null default '-',level bigint not null default 0, ctime datetime not null default current_timestamp,etime datetime not null default current_timestamp)";
                sql2.ExecuteCmd();
            }
            for(int i=0;i<20;i++)
            {
                IanMySQL sql2=sql.CreateIanMySql();
                sql2.Sql="insert into temp_1 (name,level) values (@name,@level)";
                sql2.AddSqlParamter("@name","Name"+(i+1));
                sql2.AddSqlParamter("@level",i%3);
                sql2.ExecuteCmd();
            }
            {
                IanMySQL sql2=sql.CreateIanMySql();
                sql2.Sql="update temp_1 set level=@level,etime=@etime where id=@id";
                sql2.AddSqlParamter("@level",0);
                sql2.AddSqlParamter("@etime",IanStringTool.MySQLNow());
                sql2.AddSqlParamter("@id",18);
                sql2.ExecuteCmd();
            }
            {
                IanMySQL sql2=sql.CreateIanMySql();
                sql2.Sql="delete from temp_1 where id=@id";
                sql2.AddSqlParamter("@id",19);
                sql2.ExecuteCmd();
            }
            {
                IanMySQL sql2=sql.CreateIanMySql();
                sql2.Sql="select * from temp_1";
                sql2.AddWhere("id>=@minid and id<@maxid");
                sql2.AddWhere(IanLogicalRelationEnum.Or,"level=@level");
                sql2.AddSqlParamter("@minid",2);
                sql2.AddSqlParamter("@maxid",8);
                sql2.AddSqlParamter("@level",3);
                sql2.SetOrderBy("id desc");
                sql2.SetLimit("3");
                List<LinkedHashMap<String, Object>> rows=sql2.GetList();
                for(LinkedHashMap<String,Object> row:rows){
                    String s="id="+IanConvert.ToLong(row.get("id"))+"\t";
                    s+="name="+IanConvert.ToString(row.get("name"))+"\t";
                    s+="level="+IanConvert.ToLong(row.get("level"))+"\t";
                    s+="ctime="+IanConvert.FormatMySQLDate(IanConvert.ToDate(row.get("ctime")))+"\t";
                    s+="etime="+IanConvert.FormatMySQLDate(IanConvert.ToDate(row.get("etime")));
                    System.out.println(s);
                }
            }
            System.out.println("=======================================");
            {
                IanMySQL sql2=sql.CreateIanMySql();
                sql2.Sql="select * from temp_1";
                sql2.SetOrderBy("id desc");
                sql2.SetLimit("2,10");
                List<TTemp_1> rs=sql2.GetList(TTemp_1.class);
                for(TTemp_1 r:rs){
                    String s="id="+r.id+"\t";
                    s+="name="+r.name+"\t";
                    s+="level="+r.level+"\t";
                    s+="ctime="+IanConvert.FormatDate(r.ctime,"yyyy-MM-dd HH:mm:ss")+"\t";
                    s+="etime="+IanConvert.FormatDate(r.etime,"yyyy-MM-dd HH:mm:ss");
                    System.out.println(s);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(sql!=null){
                sql.Close();
                sql=null;
            }
        }
    }

    @Test
    public void doTransactionTest() throws SQLException {
        IanMySQL sql=null;
        try{
            sql=new IanMySQL(this._dataSource);
            sql.BeginTransaction();
            long mid=0;
            {
                IanMySQL sql2=sql.CreateIanMySql();
                sql2.Sql="select ifnull(max(id),0) from temp_1";
                mid=sql2.SelectInt();
                mid++;
            }
            {
                IanMySQL sql2=sql.CreateIanMySql();
                sql2.Sql="insert into temp_1 (id,name,level) values (@id,@name,@level)";
                sql2.AddSqlParamter("@id",mid);
                sql2.AddSqlParamter("@name","Name"+mid);
                sql2.AddSqlParamter("@level",mid%3);
                sql2.ExecuteCmd();
            }
            {
                IanMySQL sql2=sql.CreateIanMySql();
                sql2.Sql="select * from temp_1 where id=@id";
                sql2.AddSqlParamter("@id",mid);
                List<LinkedHashMap<String, Object>> rs=sql2.GetList();
                System.out.println("before commit");
                for(int i=0;i<rs.size();i++){
                    String s="";
                    for(String colname:rs.get(i).keySet()){
                        s+="col "+colname+" = "+IanConvert.ToString(rs.get(i).get(colname));
                    }
                    System.out.println(s);
                }
            }
            if(mid%2==0) throw new Exception("planted error, test rollback mechanism");
            sql.Commit();
            System.out.println("submit data");
            {
                IanMySQL sql2=sql.CreateIanMySql();
                sql2.Sql="select * from temp_1 where id=@id";
                sql2.AddSqlParamter("@id",mid);
                List<LinkedHashMap<String, Object>> rs=sql2.GetList();
                System.out.println("after commit");
                for(int i=0;i<rs.size();i++){
                    String s="";
                    for(String colname:rs.get(i).keySet()){
                        s+="col "+colname+" = "+IanConvert.ToString(rs.get(i).get(colname));
                    }
                    System.out.println(s);
                }
            }
       }catch (Exception ex){
           ex.printStackTrace();
           sql.Rollback();
       }finally {
           if(sql!=null){
               sql.Close();
               sql=null;
           }
       }
    }

    @Test
    public void doProcedureTest() throws SQLException {
        IanMySQL sql=null;
        try{
             sql=new IanMySQL(this._dataSource);
            {
                IanMySQL sql2=sql.CreateIanMySql();
                sql2.Sql="drop procedure if exists p_test";
                sql2.ExecuteCmd();
            }
            {
                IanMySQL sql2=sql.CreateIanMySql();
                String s="CREATE PROCEDURE p_test (in v_value int,out v_result int)" +
                        "BEGIN " +
                        " set v_result = v_value * 2; " +
                        "END";
                sql2.Sql=s;
                sql2.ExecuteCmd();
            }
            {
                List<IanProcedureParameter> ps=new ArrayList<IanProcedureParameter>();
                {
                    IanProcedureParameter p=new IanProcedureParameter();
                    p.name="v_value";
                    p.flag= IanProcedureParameterFlagEnum.IN;
                    p.type= Types.INTEGER;
                    p.value=10;
                    ps.add(p);
                }
                {
                    IanProcedureParameter p=new IanProcedureParameter();
                    p.name="v_result";
                    p.flag= IanProcedureParameterFlagEnum.OUT;
                    p.type= Types.INTEGER;
                    p.value=0;
                    ps.add(p);
                }
                {
                    IanMySQL sql2=sql.CreateIanMySql();
                    HashMap<String, Object> rs=sql2.ExecProcedure("p_test",ps);
                    int v_result= Integer.parseInt(String.valueOf(rs.get("v_result")));
                    System.out.println("call procedure p_test return "+v_result);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(sql!=null){
                sql.Close();
                sql=null;
            }
        }
    }
}