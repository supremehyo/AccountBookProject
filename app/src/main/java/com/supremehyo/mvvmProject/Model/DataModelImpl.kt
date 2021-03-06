package com.supremehyo.mvvmProject.Model

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.supremehyo.mvvmProject.Model.Service.AccountBookContacts
import com.supremehyo.mvvmProject.Model.Service.AccountBookDatabase
import com.supremehyo.mvvmProject.View.BookAddActivity
import io.reactivex.Single


class DataModelImpl(private val context : BookAddActivity) : DataModel{

    override fun addAccountBookData(dto: AccountBookContacts , context: Context) {
       // db = AccountBookDatabase.getInstance(context)
        var dd = AccountBookDatabase.getInstance(context)!!.contactsDao().insertAccountBook(dto)
        Log.v("aaaa" ,"$dd")
        //db객체 내부에 추상함수로 선언해둔 contacts와 연결다리인 contactsDao 를통해
        //DAO와 연결
      //  db!!.contactsDao().insertAccountBook(dto)
    }

    override fun getAccountBookData(date: String, context: Context):List<AccountBookContacts> {
        var getContract2 = AccountBookDatabase.getInstance(context)!!.contactsDao().getAll(date)
        return getContract2
    }

    override fun editAccountBookDate(dto: AccountBookContacts , context: Context) {
        var dfa =AccountBookDatabase.getInstance(context)!!.contactsDao().update(dto)
    }

    override fun deleteBookAccount(long: Long, context: Context) {
        var dfa = AccountBookDatabase.getInstance(context)!!.contactsDao().delete(long)
        Toast.makeText(context, "삭제했습니다.", Toast.LENGTH_SHORT).show()
    }


}