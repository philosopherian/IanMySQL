package pro.ianchen;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * 存储过程调用参数
 * the parameter info of the procedure parameter
 */
public class IanProcedureParameter {
    /**
     * 参数名
     * parameter name
     */
    public String name="";
    /**
     * 参数类型
     * parameter type
     */
    public IanProcedureParameterFlagEnum flag= IanProcedureParameterFlagEnum.IN;

    /**
     * 数据类型
     * data type
     * 仅在flag为INOUT或OUT时使用
     * only used when the flag is INOUT or OUT
     */
    public int type= Types.INTEGER;
    /**
     * 参数值
     * parameter value
     */
    public Object value=null;

    /**
     * 获取描述信息
     * get the description of the parameter
     * @return 描述信息 the description
     */
    public String getLabel() throws Exception {
        String label="";
        String svalue="不能直接显示";
        switch (this.type) {
            case -7:
                label="BIT";
                svalue=IanConvert.ToString(this.value);
                break;
            case -6:
                label="TINYINT";
                svalue=IanConvert.ToString(this.value);
                break;
            case 5:
                label="SMALLINT";
                svalue=IanConvert.ToString(this.value);
                break;
            case 4:
                label="INTEGER";
                svalue=IanConvert.ToString(this.value);
                break;
            case -5:
                label="BIGINT";
                svalue=IanConvert.ToString(this.value);
                break;
            case 6:
                label="FLOAT";
                svalue=IanConvert.ToString(this.value);
                break;
            case 7:
                label="REAL";
                svalue=IanConvert.ToString(this.value);
                break;
            case 8:
                label="DOUBLE";
                svalue=IanConvert.ToString(this.value);
                break;
            case 2:
                label="NUMERIC";
                svalue=IanConvert.ToString(this.value);
                break;
            case 3:
                label="DECIMAL";
                svalue=IanConvert.ToString(this.value);
                break;
            case 1:
                label="CHAR";
                svalue=IanConvert.ToString(this.value);
                break;
            case 12:
                label="VARCHAR";
                svalue=IanConvert.ToString(this.value);
                break;
            case -1:
                label="LONGVARCHAR";
                break;
            case 91:
                label="DATE";
                svalue=IanConvert.ToString(this.value);
                break;
            case 92:
                label="TIME";
                svalue=IanConvert.ToString(this.value);
                break;
            case 93:
                label="TIMESTAMP";
                svalue=IanConvert.ToString(this.value);
                break;
            case -2:
                label="BINARY";
                break;
            case -3:
                label="VARBINARY";
                break;
            case 0:
                label="NULL";
                svalue="NULL";
                break;
            case 1111:
                label="OTHER";
                break;
            case 2000:
                label="JAVA_OBJECT";
                break;
            case 2001:
                label="DISTINCT";
                break;
            case 2002:
                label="STRUCT";
                break;
            case 2003:
                label = "Array";
                break;
            case 2004:
                label = "BLOB";
                break;
            case 2005:
                label = "CLOB";
                break;
            case 2006:
                label = "REF";
                break;
            case 70:
                label = "DATALINK";
                break;
            case 16:
                label = "BOOLEAN";
                break;
            case -8:
                label = "ROWID";
                break;
            case -15:
                label = "NCHAR";
                svalue=IanConvert.ToString(this.value);
                break;
            case -9:
                label = "NVARCHAR";
                svalue=IanConvert.ToString(this.value);
                break;
            case -16:
                label = "LONGNVARCHAR";
                break;
            case 2011:
                label = "NCLOB";
                break;
            case 2009:
                label = "SQLXML";
                break;
            case 2012:
                label = "REF_CURSOR";
                break;
            case 2013:
                label = "TIME_WITH_TIMEZONE";
                svalue=IanConvert.ToString(this.value);
                break;
            case 2014:
                label = "TIMESTAMP_WITH_TIMEZONE";
                svalue=IanConvert.ToString(this.value);
                break;
            default:
                throw new Exception("未知的MySQL数据类型"+this.type);
        }
        return "参数:"+this.name+" "+IanEnumLabelOpt.GetEnumLabel(IanProcedureParameterFlagEnum.class,this.flag)+" "+label+" value="+svalue;
    }

    /**
     * 获取一组参数说明
     * get a group parameters description
     * @param ps 参数列表
     */
    public static String getLabels(List<IanProcedureParameter> ps) throws Exception {
        if(ps==null) ps=new ArrayList<IanProcedureParameter>();
        StringBuilder sb=new StringBuilder();
        for(IanProcedureParameter p:ps){
            sb.append(p.getLabel()+"\r\n");
        }
        return sb.toString();
    }
}
