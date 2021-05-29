package com.supremehyo.mvvmProject.ViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.supremehyo.mvvmProject.Model.DataModel
import com.supremehyo.mvvmProject.Model.Service.AccountBookContacts

class BookAddViewModel(private val model: DataModel) : BaseKotlinViewModel() {

    private val TAG = "MainViewModel"
    var bookaccountlist : ArrayList<AccountBookContacts> = ArrayList<AccountBookContacts>()

    private  val _bookAcountLiveData = MutableLiveData<List<AccountBookContacts>>()
    val bookAcountLiveData: LiveData<List<AccountBookContacts>>
        get() = _bookAcountLiveData

    private  val _week_bookAcountLiveData = MutableLiveData<List<AccountBookContacts>>()
    val week_bookAcountLiveData: LiveData<List<AccountBookContacts>>
        get() = _week_bookAcountLiveData

    // 가계부 정보 추가
    fun addBookAccount(dto : AccountBookContacts ,context: Context){
        model.addAccountBookData(dto ,context)

    }

    // 가계부 정보 업데이트
    fun editBookAccount(dto : AccountBookContacts ,context: Context){
        model.editAccountBookDate(dto , context)
    }

    //가계부 정보 가져오기
    fun getBookAccount(date: String, context: Context, flag : String){
        if(flag.equals("day")){
            var datalist : List<AccountBookContacts>? = model.getAccountBookData(date,context)
            if (datalist != null && datalist.size !=0) {
                for( index in datalist.indices){ //
                    Log.v("로그" , datalist.get(index).memo)
                }
                _bookAcountLiveData.postValue(datalist)
            }
            /*
            addDisposable(model.getAccountBookData(date,context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.run {
                        if (it.size > 0) {
                            _bookAcountLiveData.postValue(it)
                        }
                    }
                }, {
                    Log.d(TAG, "response error, message : ${it.message}")
                }))*/
        }
        /*
*/
    }

    fun getWeekBookAccount( context: Context , datlist : ArrayList<String>){
        for (index in datlist.indices) { //
            var temp_bookaccountlist : List<AccountBookContacts> =  model.getAccountBookData(datlist.get(index),context)
            for (index in temp_bookaccountlist.indices) { //
                bookaccountlist.add(temp_bookaccountlist.get(index))
            }
             //이 plus 가 잘 안되서 안되는중 이거만 되면 잘될듯
        }
        _week_bookAcountLiveData.postValue(bookaccountlist)
    }



    //가계부 정보 지우기
    fun deleteBookAccount(context: Context , long : Long){
        model.deleteBookAccount(long ,context)
    }



}

