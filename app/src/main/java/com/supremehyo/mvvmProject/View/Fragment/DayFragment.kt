package com.supremehyo.mvvmProject.View.Fragment

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.supremehyo.mvvmProject.Adapter.CustomAdapter
import com.supremehyo.mvvmProject.Model.Service.AccountBookContacts
import com.supremehyo.mvvmProject.R
import com.supremehyo.mvvmProject.View.BookAddActivity
import com.supremehyo.mvvmProject.View.EditActivity
import com.supremehyo.mvvmProject.View.HomeActivity
import com.supremehyo.mvvmProject.ViewModel.BookAddViewModel
import kotlinx.android.synthetic.main.fragment_account_book.*
import kotlinx.android.synthetic.main.fragment_day.*
import kotlinx.android.synthetic.main.fragment_day.view.*
import kotlinx.android.synthetic.main.fragment_day.view.account_book_rv
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList
import android.widget.Toast.makeText as makeText1

class DayFragment : Fragment() {

    private  var accountbookArrayList = ArrayList<AccountBookContacts>()
    private  var date : String? = null
    val viewModel: BookAddViewModel by viewModel() // Koin 으로 의존성 주입


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            date = it.getString("date")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): 
            View? {
                val view = inflater.inflate(R.layout.fragment_day, container, false)
                //어뎁터 생성
                var recyclerView : RecyclerView = view.findViewById(R.id.account_book_rv)
        
                val context = container?.context
                if (context != null) {
                    //원래 메인 스레드에서 동작하고 있었기 때문에 비동기로 만들어주는게 좋다.
                    //Rxjava 혹은 코루틴 사용 할 것
                    viewModel.getBookAccount(date.toString() ,context , "day")
                }
                viewModel.bookAcountLiveData.observe(this , androidx.lifecycle.Observer {
                    it.forEach{
                        //여기에 for문 돌면서 data 를 list 로 바꿔주고 그걸 어뎁터로 넘기면 될거 같다.
                        accountbookArrayList.add(it);
                    }
                    // list에 다 넣고나서 어뎁터로 넘겨주기
                    var mAdapter = context?.let {
                            it1 -> CustomAdapter(it1,accountbookArrayList) {
                                dataVo -> Toast.makeText(context, dataVo.memo.toString(), Toast.LENGTH_SHORT).show()
                                //클릭한 데이터를 intent 로 넘겨서 edittext에서 수정할 수 있도록 구현
                                val myIntent = Intent(context, EditActivity::class.java)
                                myIntent.putExtra("id",dataVo.id)
                                myIntent.putExtra("memo",dataVo.memo)
                                myIntent.putExtra("money",dataVo.money)
                                myIntent.putExtra("where_pay",dataVo.where_pay)
                                myIntent.putExtra("date",dataVo.date)
                                startActivity(myIntent)
                        }
                    }
                    recyclerView.adapter = mAdapter
                    val layout = LinearLayoutManager(context)
                    recyclerView.layoutManager = layout
                    recyclerView.setHasFixedSize(true)
                })
                //리사이클러뷰 클릭기능 구현 https://ddolcat.tistory.com/591
                // -> 클릭해서 수정하는 화면 나오도록 구현


                // 플로팅 버튼을 눌렀을 때startActivity
                view.add_bt.setOnClickListener {
                    val bookAddIntent = Intent(activity, BookAddActivity::class.java)
                    startActivity(bookAddIntent)
                }
                return view
    }

}