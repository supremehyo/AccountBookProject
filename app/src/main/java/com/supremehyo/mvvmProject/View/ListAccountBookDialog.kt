package com.supremehyo.mvvmProject.View

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.ListView
import com.supremehyo.mvvmProject.Model.Service.AccountBookContacts
import com.supremehyo.mvvmProject.R

class ListAccountBookDialog(context: Context){
    private  val dialog = Dialog(context)

    //여기로 데이터 집어넣고 밑에서 listview로 넣어준다.
    fun MyDig(list : List<AccountBookContacts>){

        var listView : ListView = dialog.findViewById(R.id.last_orderList)

        dialog.setContentView(R.layout.custom_dialog)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)


    }

}