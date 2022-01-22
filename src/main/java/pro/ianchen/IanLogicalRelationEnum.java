package pro.ianchen;

/**
 * 逻辑关系枚举
 * logical relation Enum
 */
public enum IanLogicalRelationEnum {
    /**
     * and relation
     */
    @IanEnumLabel(value=0,label="并且")
    And,
    /**
     * or relation
     */
    @IanEnumLabel(value = 1,label = "或者")
    Or
}
