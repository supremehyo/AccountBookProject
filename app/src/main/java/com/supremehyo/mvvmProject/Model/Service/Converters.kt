package com.supremehyo.mvvmProject.Model.Service

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimeStamp(value : Long) : Date{
        return value?.let {it -> Date(it) }
    }

    @TypeConverter
    fun dateToTimeStamp(date: Date){
         return date?.let {date.getTime()}
    }

    @TypeConverter
    fun fromStringList(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun toStringList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun toFloatList(value: String) = Gson().fromJson(value, Array<Float>::class.java).toList()


}