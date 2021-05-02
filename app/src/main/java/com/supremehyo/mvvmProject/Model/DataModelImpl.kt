package com.supremehyo.mvvmProject.Model

import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.supremehyo.mvvmProject.Model.Service.AccountBookContacts
import com.supremehyo.mvvmProject.Model.Service.AccountBookDTO
import com.supremehyo.mvvmProject.Model.Service.AccountBookDatabase
import com.supremehyo.mvvmProject.View.BookAddActivity
import io.reactivex.Single
import java.util.*
import kotlin.collections.ArrayList

class DataModelImpl(private val context : BookAddActivity) : DataModel{

    var db : AccountBookDatabase? = null
    override fun addAccountBookData(dto: AccountBookContacts , context: Context) {
       // db = AccountBookDatabase.getInstance(context)
        var dd = AccountBookDatabase.getInstance(context)!!.contactsDao().insertAccountBook(dto)
        Log.v("aaaa" ,"$dd")
        //db객체 내부에 추상함수로 선언해둔 contacts와 연결다리인 contactsDao 를통해
        //DAO와 연결
      //  db!!.contactsDao().insertAccountBook(dto)
    }

    override fun getAccountBookData(date: String, context: Context): List<AccountBookContacts> {

        var getContract2 = AccountBookDatabase.getInstance(context)!!.contactsDao().getAll(date)
          Log.v("awef", "$getContract2");
        return getContract2
    }


}