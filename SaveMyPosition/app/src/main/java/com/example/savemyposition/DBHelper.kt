package com.example.savemyposition

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context) : SQLiteOpenHelper(context,"Postion_Data",null,1){

    companion object{
        private val DB_Name = "Postion_Data"
        private val DB_VERSION = 1
        private val TABLE_NAME = "Time_Postion"
        private val Time = "time"
        private val Latitude = "latitude"
        private val Longitude = "longitude"

    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TABLE_NAME (" +
                    "Time TEXT," +
                    "Latitude Text," +
                    "Longitude Text)"
        db?.execSQL(createTable)
    }

    fun addPostion(time:String, latitude:String, longitude:String) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(Time,time)
        values.put(Latitude,latitude)
        values.put(Longitude,longitude)

        db.insert(TABLE_NAME,null,values)
        db.close()
    }
    fun getPostion() :MutableList<Postion>{
        val db = readableDatabase
        val selectAllQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectAllQuery,null)
        var time:String = ""
        var latitude:String = ""
        var longitude:String = ""
        val list = mutableListOf<Postion>()
        if (cursor != null){
            if(cursor.moveToFirst()){
                do{
                    time = cursor.getString(0)
                    latitude = cursor.getString(1)
                    longitude = cursor.getString(2)
                    val postion_db = Postion(time,latitude,longitude)

                    list.add(postion_db)

                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return list
    }

    fun deletePosition(){
        val db = writableDatabase
        val delete = "delete from $TABLE_NAME where Time like '2020.8.30%'"
        db.execSQL(delete)
        db.close()
}

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }


}



