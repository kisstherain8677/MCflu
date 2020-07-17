package com.new_afterwave.mc.dao;

public enum  SQLCommand {
    CREATE_HEALTHTABLE(
            "CREATE TABLE IF NOT EXISTS 'HealthState' (" +
                    "'UUID' VARCHAR(100)  primary key not null," +
                    "'playerName' VARCHAR(20)  not null," +
                    "'nearDis' varchar(20),"+
                    "'dangerTime' varchar(20),"+
                    "'deadTime' varchar(20),"+
                    "'isInfected' boolean,"+
                    "'isMasked' boolean,"+
                    "'isDisinfected' boolean,"+
                    "'isPotionTacked' boolean,"+
                    "'isVaccine' boolean,"+
                    "'maskTime' varchar(20),"+
                    "'disinfectorTime' varchar(20)"+
                    ")"
    ),
    DELETE_DATA(
            "DELETE FROM 'HealthState' WHERE UUID = ?"
    ),
    SELECT_DATA(
            "SELECT * FROM 'HealthState' WHERE UUID =?"
    ),
    ADD_DATA(
            "INSERT INTO 'HealthState' " +
                    "('UUID','playerName','nearDis','dangerTime','deadTime','isInfected','isMasked','isDisinfected'," +
                    "'isPotionTacked','isVaccine','maskTime','disinfectorTime')" +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"
    ),
    CREATE_VENT_TABLE(
            "CREATE TABLE IF NOT EXISTS 'Ventilator' (" +
                    "'ID' VARCHAR(10) primary key not null," +
                    "'PositionX' VARCHAR(20)  not null," +
                    "'PositionY' VARCHAR(20)  not null," +
                    "'PositionZ' VARCHAR(20)  not null" +
                    ")"
    ),
    INSERT_VENT_TABLE(
            "INSERT INTO 'Ventilator'" +
                    "('ID','PositionX','PositionY','PositionZ')" +
                    "VALUES(?,?,?,?)"
    ),
    DELETE_VENT_TABLE(
            "DELETE FROM 'Ventilator' WHERE ID = ?"
    ),
    SELECT_VENT_TABLE(
            "SELECT * FROM 'Ventilator'"
    ),
    SELECT_VENT_TABLE_BY_POSITION(
            "SELECT * FROM 'Ventilator' WHERE PositionX = ? " +
                    "AND PositionY = ? " +
                    "AND PositionZ = ? "
    );
//    UPDATE_DATA(
//            "UPDATE 'HealthState' " +
//                    "('PlayerName','info')" +
//                    "VALUES (?,?)"
//    );
//    ADD_DATA(
//            "INSERT INTO `TABLE1` " +
//                    "(`int`, `string`)" +
//                    "VALUES (?, ?)"
//    );



    private String command;

    SQLCommand(String command)
    {
        this.command = command;
    }
    public String commandToString()
    {
        return command;
    }
}
