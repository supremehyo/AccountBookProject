package com.supremehyo.mvvmProject.Model.Service

import android.content.Context
import androidx.room.*

//실질적인 room 데이터 베이스 구현
//RoomDatabase 를 상속받아서 만든다.
@Database(entities = [AccountBookContacts::class], version = 1, exportSchema = false)
abstract class AccountBookDatabase : RoomDatabase() {
    abstract fun contactsDao(): AccountBookDAO// DAO로 접근 할수 있도록
    //추상 함수로 선언해둠.

    //싱글톤으로 만들어서 메모리 절약
    companion object {
        private var instance: AccountBookDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AccountBookDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AccountBookDatabase::class.java,
                    "database-contacts"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}