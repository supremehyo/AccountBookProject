package com.supremehyo.mvvmProject.View

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.supremehyo.mvvmProject.Model.Service.AccountBookContacts
import com.supremehyo.mvvmProject.R
import com.supremehyo.mvvmProject.ViewModel.BookAddViewModel
import com.supremehyo.mvvmProject.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_book_add.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookAddActivity : BaseKotlinActivity<ActivityMainBinding, BookAddViewModel>() {

    lateinit var context : Context

    override val viewModel: BookAddViewModel by viewModel() // Koin 으로 의존성 주입
    override val layoutResourceId: Int
        get() = R.layout.activity_book_add

    var fname: String = ""
    var str: String = ""
    var group_str : String = ""

    //참고링크 https://blog.naver.com/PostView.nhn?blogId=oper4062&logNo=221446757338&parentCategoryNo=&categoryNo=6&viewDate=&isShowPopularPosts=true&from=search
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_add)
        context = this.applicationContext
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->

            diaryTextView.text = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
// 날짜를 보여주는 텍스트에 해당 날짜를 넣는다.
            contextEditText.setText("") // EditText에 공백값 넣기

            checkedDay(year, month, dayOfMonth) // checkedDay 메소드 호출

            save_Btn.setOnClickListener { // 저장 Button이 클릭되면
                saveDiary(fname) // saveDiary 메소드 호출
               // toast(fname + "데이터를 저장했습니다.") // 토스트 메세지
                str = contextEditText.getText().toString() // str 변수에 edittext내용을 toString
//형으로 저장
            }
        }

        groupSelect() //카테고리 클릭 리스터 초기화
    }


    fun saveDiary(readyDay: String) {

            var content: String = contextEditText.text.toString()
            var where : String = whereEditText.text.toString()
            var price : String = howmuchEditText.text.toString()
            var date : String = diaryTextView.text.toString()

            if(content.isNotEmpty() && where.isNotEmpty() && price.isNotEmpty() && date.isNotEmpty()){
                var dto = AccountBookContacts(0 ,date,price,where,content)
                viewModel.addBookAccount(dto, this)
                finish()
            }else{
                Toast.makeText(context, "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show()
                finish()
            }
    }

    fun checkedDay(cYear: Int, cMonth: Int, cDay: Int) {
            var select_day : String = ""
            select_day = "" + cYear + " / " + (cMonth + 1) + "" + " / " + cDay
            viewModel.getBookAccount(select_day ,this, "day")
        }

    override fun initStartView() {
    }

    override fun initDataBinding() {
            viewModel.bookAcountLiveData.observe(this, Observer {
                it.forEach {
                    Log.v("로그dddd", it.memo.toString())
                }
            })
    }

    override fun initAfterBinding() {
    }

    fun groupSelect(){
        group1.setOnClickListener {
            group_str = "식비"
            whereEditText.setText(group_str)
        }
        group2.setOnClickListener {
            group_str = "교통비"
            whereEditText.setText(group_str)
        }
        group3.setOnClickListener {
            group_str = "여가생활"
            whereEditText.setText(group_str)
        }
        group4.setOnClickListener {
            group_str = "의류"
            whereEditText.setText(group_str)
        }
        group5.setOnClickListener {
            group_str = "생활비"
            whereEditText.setText(group_str)
        }
        group6.setOnClickListener {
            group_str = "기타"
            whereEditText.setText(group_str)
        }
    }
}