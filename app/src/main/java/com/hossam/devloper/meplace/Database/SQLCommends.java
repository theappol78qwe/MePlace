package com.hossam.devloper.meplace.Database;

public class SQLCommends {


    public final static String CREATE_ADD_FRIENDS_TABLE = "CREATE TABLE " + Contract.Friends_Table.Table_Name + " (" +

            Contract.Friends_Table.UniqueId + " TEXT NOT NULL," +

            Contract.Friends_Table.FriendId + " TEXT NOT NULL " + "); ";

    public final static String Drop_FRIEND_TABLE = "DROP TABLE IF EXISTS " + Contract.Friends_Table.Table_Name;

    public final static String SELECT_ALL_QUERY = "SELECT  * FROM " + Contract.Friends_Table.Table_Name;
}