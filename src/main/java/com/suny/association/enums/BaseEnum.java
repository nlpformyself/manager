package com.suny.association.enums;

/**
 * Comments:  基础错误代码枚举类
 * Author:   孙建荣
 * Create Date: 2017/03/08 18:10
 */
public enum BaseEnum {

    LOGIN_FAILURE("001", "登陆验证失败，请检查用户名密码是否正确"),
    ADD_FAILURE("002", "添加失败"),
    DELETE_FAILURE("003", "删除失败"),
    UPDATE_DELETE("004", "更新失败"),
    SELECT_FAILURE("005", "查询失败"),
    REPEAT_ADD("006", "重复添加"),
    ADD_SUCCESS("102", "添加成功"),
    DELETE_SUCCESS("103", "删除成功"),
    UPDATE_SUCCESS("104", "更新成功"),
    SELECT_SUCCESS("105", "查询成功"),
    REPEAT_EMAIL("106","邮箱重复"),
    REPEAT_PHONE("107","手机号码重复"),
    REPEAT_USERNAME("108","用户名重复"),
    NULL_OBJ("201", "对象为空"),
    FIELD_NULL("202","必要字段为空"),
    PRIMARY_NULL("203","主键字段为空"),
    HAVE_QUOTE("204","存在引用"),
    VALIDATE_CODE_ERROR("994","存在引用"),
    LOGIN_SYSTEM("995","登录成功"),
    PASS_ERROR("996","密码错误"),
    REPEAT_LOGIN("998","重复登录"),
    UNKNOWN_ERROR("999", "系统繁忙....");

    String value;
    String desc;

    BaseEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "系统代码:" + value + "," + desc;
    }


}
