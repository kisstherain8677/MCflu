package com.new_afterwave.mc.dao;

/**
 * @author by
 * @description:
 * @date 2020/7/8 16:39
 */
public enum LoginSQLStatement
{
    //这句话就算如果不存在TABLE1的表，创建之
    //有以下列数
    //int列，存储数据
    //string列，存储字符串
    //主键为int
    CREATE_TABLE1(
        "CREATE TABLE IF NOT EXISTS `LoginInfo` (" +
        "`playerName` VARCHAR(30) NOT NULL," +
        "`password` VARCHAR(30) NOT NULL," +
        "PRIMARY KEY (`playerName`))"
    ),
    ADD_DATA(
        "INSERT INTO `LoginInfo` " +
        "(`playerName`, `password`)" +
        "VALUES (?, ?)"
    ),
    //添加一行数据，包含2个值
    DELETE_DATA(
        "DELETE FROM `LoginInfo` WHERE `playerName` = ?"
    ),
    //删除主键为[int]的一行数据
    SELECT_DATA(
        "SELECT * FROM `LoginInfo` WHERE `playerName` = ?"
    );
    //查找主键为[int]的一行数据

    /*
     * 这里可以添加更多的MySQL命令，格式如下
     * COMMAND_NAME(
     *    "YOUR_COMMAND_HERE" +
     *    "YOUR_COMMAND_HERE"
     * );
     */

    private String statement;

    LoginSQLStatement(String statement)
    {
        this.statement = statement;
    }

    public String getStatement()
    {
        return statement;
    }
}
