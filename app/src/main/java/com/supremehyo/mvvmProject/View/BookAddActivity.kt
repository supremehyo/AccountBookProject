package com.supremehyo.mvvmProject.View

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.supremehyo.mvvmProject.Model.Service.AccountBookContacts
import com.supremehyo.mvvmProject.R
import com.supremehyo.mvvmProject.ViewModel.BookAddViewModel
import com.supremehyo.mvvmProject.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_book_add.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.FileOutputStream

class BookAddActivity : BaseKotlinActivity<ActivityMainBinding, BookAddViewModel>() {

    lateinit var context : Context

    override val viewModel: BookAddViewModel by viewModel() // Koin 으로 의존성 주입
    override val layoutResourceId: Int
        get() = R.layout.activity_book_add

    var fname: String = ""
    var str: String = ""
    //참고링크 https://blog.naver.com/PostView.nhn?blogId=oper4062&logNo=221446757338&parentCategoryNo=&categoryNo=6&viewDate=&isShowPopularPosts=true&from=search
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_add)
        context = this.applicationContext
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
// 달력 날짜가 선택되면
            diaryTextView.visibility = View.VISIBLE // 해당 날짜가 뜨는 textView가 Visible
            save_Btn.visibility = View.VISIBLE // 저장 버튼이 Visible
            contextEditText.visibility = View.VISIBLE // EditText가 Visible
            whereEditText.visibility = View.VISIBLE
            howmuchEditText.visibility = View.VISIBLE
            textView2.visibility = View.INVISIBLE // 저장된 일기 textView가 Invisible
            cha_Btn.visibility = View.INVISIBLE // 수정 Button이 Invisible
            del_Btn.visibility = View.INVISIBLE // 삭제 Button이 Invisible

            diaryTextView.text = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
// 날짜를 보여주는 텍스트에 해당 날짜를 넣는다.
            contextEditText.setText("") // EditText에 공백값 넣기

            checkedDay(year, month, dayOfMonth) // checkedDay 메소드 호출

            save_Btn.setOnClickListener { // 저장 Button이 클릭되면
                saveDiary(fname) // saveDiary 메소드 호출
               // toast(fname + "데이터를 저장했습니다.") // 토스트 메세지
                str = contextEditText.getText().toString() // str 변수에 edittext내용을 toString
//형으로 저장
                textView2.text = "${str}" // textView에 str 출력



                save_Btn.visibility = View.INVISIBLE
                cha_Btn.visibility = View.VISIBLE
                del_Btn.visibility = View.VISIBLE
                contextEditText.visibility = View.INVISIBLE
                textView2.visibility = View.VISIBLE
            }
        }

    }


    fun saveDiary(readyDay: String) {

            var content: String = contextEditText.text.toString()
            var where : String = whereEditText.text.toString()
            var price : String = whereEditText.text.toString()
            var date : String = diaryTextView.text.toString()

            var dto = AccountBookContacts(0 ,date,price,where,content)

            viewModel.addBookAccount(dto, this)

    }

    fun checkedDay(cYear: Int, cMonth: Int, cDay: Int) {

            var select_day : String = ""
            select_day = "" + cYear + " / " + (cMonth + 1) + "" + " / " + cDay
            viewModel.getBookAccount(select_day ,this, "day")

            contextEditText.visibility = View.INVISIBLE
            textView2.visibility = View.VISIBLE
           // textView2.text = "${str}" // textView에 str 출력
            save_Btn.visibility = View.INVISIBLE
            cha_Btn.visibility = View.VISIBLE
            del_Btn.visibility = View.VISIBLE

            cha_Btn.setOnClickListener { // 수정 버튼을 누를 시
                contextEditText.visibility = View.VISIBLE
                textView2.visibility = View.INVISIBLE
                contextEditText.setText(str) // editText에 textView에 저장된
// 내용을 출력
                save_Btn.visibility = View.VISIBLE
                cha_Btn.visibility = View.INVISIBLE
                del_Btn.visibility = View.INVISIBLE
                textView2.text = "${contextEditText.getText()}"
            }

            del_Btn.setOnClickListener {
                textView2.visibility = View.INVISIBLE
                contextEditText.setText("")
                contextEditText.visibility = View.VISIBLE
                save_Btn.visibility = View.VISIBLE
                cha_Btn.visibility = View.INVISIBLE
                del_Btn.visibility = View.INVISIBLE
                removeDiary(fname)
            }

            if(textView2.getText() == ""){
                textView2.visibility = View.INVISIBLE
                diaryTextView.visibility = View.VISIBLE
                save_Btn.visibility = View.VISIBLE
                cha_Btn.visibility = View.INVISIBLE
                del_Btn.visibility = View.INVISIBLE
                contextEditText.visibility = View.VISIBLE
            }

        }

    @SuppressLint("WrongConstant")
    fun removeDiary(readyDay: String) {
        var fos: FileOutputStream? = null

        try {
            fos = openFileOutput(readyDay, MODE_NO_LOCALIZED_COLLATORS)
            var content: String = ""
            fos.write(content.toByteArray())
            fos.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

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
}