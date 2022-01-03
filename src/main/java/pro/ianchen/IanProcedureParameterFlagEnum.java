package pro.ianchen;

/**
 * 存储过程参数调用类型
 */
public enum IanProcedureParameterFlagEnum {
    @IanEnumLabel(value=0,label="输入参数")
    IN,
    @IanEnumLabel(value=1,label="输出参数")
    OUT,
    @IanEnumLabel(value=2,label="输入输出参数")
    INOUT
}
