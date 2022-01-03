package pro.ianchen;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class IanMySQLTest {

    HikariDataSource _dataSource=null;

    @Before
    public void setUp() {
        try{
            IanMySQLDataSource ds=new IanMySQLDataSource();
            this._dataSource=ds.GetHikariDataSource("jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf-8&serverTimezone=Asia/Shanghai","test","test");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        try{
            this._dataSource.close();
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
                sql2.Sql="create table temp_1 (id bigint not null auto_increament,name varchar(100) not null default '-',level bigint not null default 0, ctime datetime not null default current_timestamp,etime datetime not null default current_timestamp)";
                sql2.ExecuteCmd();
            }
            for(int i=0;i<10;i++)
            {
                IanMySQL sql2=sql.CreateIanMySql();
                sql2.Sql="insert into temp_1 (name,level) values (@name,@level)";
                sql2.AddSqlParamter("@name","Name"+(i+1));
                sql2.AddSqlParamter("@level",i%3);
                sql2.ExecuteCmd();
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