package com.supremehyo.mvvmProject.View

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.supremehyo.mvvmProject.Model.Service.AccountBookContacts
import com.supremehyo.mvvmProject.R
import com.supremehyo.mvvmProject.ViewModel.BookAddViewModel
import com.supremehyo.mvvmProject.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_edit.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditActivity : AppCompatActivity()   {

    val viewModel: BookAddViewModel by viewModel()
    lateinit var context : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        context = this.applicationContext

        var id = intent.getLongExtra("id" , 0L)
        var memo = intent.getStringExtra("memo")
        var where_pay = intent.getStringExtra("where_pay")
        var date = intent.getStringExtra("date")
        var money = intent.getStringExtra("money")

        edit_pay.setText(money)
        edit_where.setText(where_pay)
        edit_what.setText(memo)

        edit_bt.setOnClickListener {
            var accountbookContract = date?.let { it1 ->
                AccountBookContacts(id,
                    it1,edit_pay.text.toString() ,edit_where.text.toString(),edit_what.text.toString())
            }
            if (accountbookContract != null) {
                viewModel.editBookAccount(accountbookContract,context)
            }
        }




    }
    
}