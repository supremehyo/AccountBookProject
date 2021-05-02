package com.supremehyo.mvvmProject.ViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.supremehyo.mvvmProject.Model.DataModel
import com.supremehyo.mvvmProject.Model.DataModelImpl
import com.supremehyo.mvvmProject.Model.Service.AccountBookContacts
import com.supremehyo.mvvmProject.Model.Service.AccountBookDTO
import io.reactivex.Single
import java.util.*
import kotlin.collections.ArrayList

class BookAddViewModel(private val model: DataModel) : BaseKotlinViewModel() {

    private val TAG = "MainViewModel"
    private  val _bookAcountLiveData = MutableLiveData<List<AccountBookContacts>>()
    val bookAcountLiveData: LiveData<List<AccountBookContacts>>
        get() = _bookAcountLiveData

    // 가계부 정보 추가
    fun addBookAccount(dto : AccountBookContacts ,context: Context){
        model.addAccountBookData(dto ,context)

    }

    //가계부 정보 가져오기
    fun getBookAccount(date: String, context: Context){

        var datalist : List<AccountBookContacts>? =model.getAccountBookData(date,context)
        if (datalist != null && datalist.size !=0) {
            for( index in datalist.indices){ //
                Log.v("로그" , datalist.get(index).memo) // room 연동 성공 값 넘어오는거 확인했음
            }
        }
        _bookAcountLiveData.postValue(datalist)
    }

    //가계부 정보 지우기
    fun deleteBookAccount(date : String){

    }



}

