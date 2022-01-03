package pro.ianchen;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * MySql数据库连接数据源
 */
public class IanMySQLDataSource {

    /**
     * 以Hikari方式获取数据库连接池数据源
     * @param vJdbcUrl jdbc连接
     * @param vUserName 账号
     * @param vPassword 密码
     * @return Hikari数据库连接池数据源
     * @throws Exception
     */
    public HikariDataSource GetHikariDataSource(String vJdbcUrl,String vUserName,String vPassword) throws Exception{
        return this.GetHikariDataSource(vJdbcUrl,vUserName,vPassword,"utf8");
    }

    /**
     * 以Hikari方式获取数据库连接池数据源
     * @param vJdbcUrl jdbc连接
     * @param vUserName 账号
     * @param vPassword 密码
     * @param vCharset 字符集
     * @return Hikari数据库连接池数据源
     * @throws Exception
     */
    public HikariDataSource GetHikariDataSource(String vJdbcUrl,String vUserName,String vPassword,String vCharset) throws Exception {
        String jdbcUrl=IanStringTool.Check(vJdbcUrl,"连接URL");
        String userName=IanStringTool.Check(vUserName,"账号");
        String password=IanStringTool.Check(vPassword,"密码");
        String charset=IanStringTool.Deal(vCharset,"");
        int cacheSize=200;//正式建议250
        int sqlLimit=2048;

        HikariConfig hconfig=new HikariConfig();
        //hconfig.setDriverClassName("com.mysql.jdbc.Driver");
        hconfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hconfig.setJdbcUrl(jdbcUrl);//数据源
        hconfig.setUsername(userName);//用户名
        hconfig.setPassword(password);//密码
        hconfig.addDataSourceProperty("cachePrepStmts", "true"); //是否自定义配置，为true时下面两个参数才生效
        hconfig.addDataSourceProperty("prepStmtCacheSize", String.valueOf(cacheSize)); //连接池大小默认25，官方推荐250-500
        hconfig.addDataSourceProperty("prepStmtCacheSqlLimit", String.valueOf(sqlLimit)); //单条语句最大长度默认256，官方推荐2048
        hconfig.addDataSourceProperty("useServerPrepStmts", "true"); //新版本MySQL支持服务器端准备，开启能够得到显著性能提升
        hconfig.addDataSourceProperty("useLocalSessionState", "true");
        hconfig.addDataSourceProperty("useLocalTransactionState", "true");
        hconfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hconfig.addDataSourceProperty("cacheResultSetMetadata", "true");
        hconfig.addDataSourceProperty("cacheServerConfiguration", "true");
        hconfig.addDataSourceProperty("elideSetAutoCommits", "true");
        hconfig.addDataSourceProperty("maintainTimeStats", "false");
        if(charset.length()>0){
            hconfig.addDataSourceProperty("useUnicode", "true");
            hconfig.addDataSourceProperty("characterEncoding", charset);
        }
        hconfig.setConnectionTestQuery("select 1");
        hconfig.setPoolName("NBTLHikariDatabasePool");
        hconfig.setConnectionTimeout(30000);
        hconfig.setMaxLifetime(28800000);
        hconfig.setIdleTimeout(28800000);
        hconfig.setMaximumPoolSize(500);
        hconfig.setAutoCommit(true);
        hconfig.setMinimumIdle(5);
        return new HikariDataSource(hconfig);
    }

    /**
     * 关闭Hikari数据库连接池数据源
     * @param ds Hikari数据库连接池数据源
     */
    public void CloseDataSource(HikariDataSource ds){
        if(ds!=null){
            if(!ds.isClosed()){
                ds.close();
            }
            ds=null;
        }
    }
}

