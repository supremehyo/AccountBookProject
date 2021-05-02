package com.supremehyo.mvvmProject.Model


import android.content.Context
import com.supremehyo.mvvmProject.Model.Service.AccountBookContacts
import com.supremehyo.mvvmProject.Model.Service.AccountBookDTO
import io.reactivex.Single
import java.util.*
import kotlin.collections.ArrayList

interface DataModel {
    fun addAccountBookData(dto : AccountBookContacts , context : Context)
    fun getAccountBookData (date : String , context :Context) : List<AccountBookContacts>// 날짜로 데이터 가져오는 작업
}