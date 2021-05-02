package com.supremehyo.mvvmProject.View

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import com.supremehyo.mvvmProject.R
import com.supremehyo.mvvmProject.View.Fragment.AccountBookFragment
import com.supremehyo.mvvmProject.View.Fragment.StatisticsFragment
import com.supremehyo.mvvmProject.ViewModel.MainViewModel
import com.supremehyo.mvvmProject.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class HomeActivity : BaseKotlinActivity<ActivityMainBinding, MainViewModel>() {


    override val viewModel: MainViewModel by viewModel() // Koin 으로 의존성 주입
    override val layoutResourceId: Int
        get() = R.layout.activity_home


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btn1.setOnClickListener {
            setDataAtFragment(AccountBookFragment() , this_day.text.toString())//프라그먼트에 값 넘기기
        }

        btn2.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_view , StatisticsFragment()).commit()
        }

        val today = Calendar.getInstance()
        val year = today.get(Calendar.YEAR).toString()
        val month = (today.get(Calendar.MONTH)+1).toString()
        val date = today.get(Calendar.DAY_OF_MONTH).toString()

        //현재 날짜 출력
        this_day.text = year+"년"+month+"월"+date+"일"
        //현재 날짜 클릭
        this_day.setOnClickListener {
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)

            var listener = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                // i년 i2월 i3일
                this_day.text = "${i} / ${i2 + 1} / ${i3}"
                setDataAtFragment(AccountBookFragment() , this_day.text.toString())//프라그먼트에 값 넘기기
            }

            var picker = DatePickerDialog(this, listener, year, month, day)
            picker.show()
        }


    }

    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

    fun setDataAtFragment(fragment: AccountBookFragment , date : String){
        val bundle = Bundle()
        bundle.putString("date", date)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_view , fragment).commit()
    }
}