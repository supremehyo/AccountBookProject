package com.supremehyo.mvvmProject.View.Fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS
import com.supremehyo.mvvmProject.Adapter.CustomAdapter
import com.supremehyo.mvvmProject.Model.Service.AccountBookContacts
import com.supremehyo.mvvmProject.MyApplication
import com.supremehyo.mvvmProject.R
import com.supremehyo.mvvmProject.View.EditActivity
import com.supremehyo.mvvmProject.ViewModel.BookAddViewModel
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlinx.android.synthetic.main.fragment_statistics.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StatisticsFragment : Fragment() {

    lateinit var PieChart : PieChart
    private  var date : String? = null
    private  var accountbookArrayList = ArrayList<AccountBookContacts>()
    private  var week_accountbookArrayList = ArrayList<AccountBookContacts>()
    var daylist : ArrayList<String> = ArrayList()

    val viewModel: BookAddViewModel by viewModel() // Koin 으로 의존성 주입

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            date = it.getString("date")
        }
        date = MyApplication.date.date
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)

        view.chart.setUsePercentValues(true)
        val context = container?.context
        
        //현재 선택된 날짜로 일주일치 날짜 구하기
        week(date.toString())

        viewModel.bookAcountLiveData.observe(this , androidx.lifecycle.Observer {
            it.forEach {
                accountbookArrayList.add(it);
            }
            makeChart(view,accountbookArrayList)
        })

        viewModel.week_bookAcountLiveData.observe(this , androidx.lifecycle.Observer {
            it.forEach {
                week_accountbookArrayList.add(it)
            }
            makeChart(view,week_accountbookArrayList)
        })

        //시간별 클릭 했을때 바뀌는 프래그먼트
        view.dayy.setOnClickListener {
            Log.v("test2", "눌렸음")
            if (context != null) {
                Log.v("test3", "눌렸음")
                accountbookArrayList.clear()
                viewModel.getBookAccount(date.toString() ,context , "day")
            }
        }
        view.week.setOnClickListener {
            if (context != null) {
                week_accountbookArrayList.clear()
                viewModel.getWeekBookAccount(context,daylist)
            }
        }

        view.month.setOnClickListener {
            if (context != null) {
                accountbookArrayList.clear()
                viewModel.getBookAccount(date.toString() ,context , "day")
            }
        }
        return view
    }

    fun makeChart(view : View , list : ArrayList<AccountBookContacts>){

        var category1 : Int = 0 // 식비
        var category2 : Int = 0// 교통비
        var category3 : Int = 0// 여가생활
        var category4 : Int = 0 // 의류
        var category5 : Int = 0 // 생활비
        var category6 : Int = 0 // 기타

        var category1_flag : Int = 0 // 식비
        var category2_flag : Int = 0// 교통비
        var category3_flag : Int = 0// 여가생활
        var category4_flag : Int = 0 // 의류
        var category5_flag : Int = 0 // 생활비
        var category6_flag : Int = 0 // 기타

        val entries = ArrayList<PieEntry>()
        for(accountBook in list){
            if(accountBook.where_pay.equals("식비")){
                category1 += accountBook.money.toInt()
                category1_flag = 1
            }else if(accountBook.where_pay.equals("교통비")){
                category2 += accountBook.money.toInt()
                category2_flag = 1
            }else if(accountBook.where_pay.equals("여가생활")){
                category3 += accountBook.money.toInt()
                category3_flag = 1
            }else if(accountBook.where_pay.equals("의류")){
                category4 += accountBook.money.toInt()
                category4_flag = 1
            }else if(accountBook.where_pay.equals("생활비")){
                category5 += accountBook.money.toInt()
                category5_flag = 1
            }else if(accountBook.where_pay.equals("기타")){
                category6 += accountBook.money.toInt()
                category6_flag = 1
            }
        }

        if(category1_flag == 1){
            entries.add(PieEntry(category1.toFloat(), "식비"))
        }else if(category2_flag == 1){
            entries.add(PieEntry(category2.toFloat(), "교통비"))
        }else if(category3_flag == 1){
            entries.add(PieEntry(category3.toFloat(), "여가생활"))
        }else if(category4_flag == 1){
            entries.add(PieEntry(category4.toFloat(), "의류"))
        }else if(category5_flag == 1){
            entries.add(PieEntry(category5.toFloat(), "생활비"))
        }else if(category6_flag == 1){
            entries.add(PieEntry(category6.toFloat(), "기타"))
        }

// add a lot of colors
        val colorsItems = ArrayList<Int>()
        for (c in ColorTemplate.VORDIPLOM_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.JOYFUL_COLORS) colorsItems.add(c)
        for (c in COLORFUL_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.LIBERTY_COLORS) colorsItems.add(c)
        for (c in ColorTemplate.PASTEL_COLORS) colorsItems.add(c)
        colorsItems.add(ColorTemplate.getHoloBlue())

        val pieDataSet = PieDataSet(entries, "")
        pieDataSet.apply {
            colors = colorsItems
            valueTextColor = Color.BLACK
            valueTextSize = 16f
        }

        val pieData = PieData(pieDataSet)
        view.chart.apply {
            data = pieData
            description.isEnabled = false
            isRotationEnabled = false
            centerText = "This is Center"
            setEntryLabelColor(Color.BLACK)
            animateY(1400, Easing.EaseInOutQuad)
            animate()
        }
    }

    fun week(eventDate: String) {
        val dateArray = eventDate.split(" / ").toTypedArray()
        val cal = Calendar.getInstance()
        cal[dateArray[0].toInt(), dateArray[1].toInt() - 1] = dateArray[2].toInt()
        // 일주일의 첫날을 일요일로 지정한다
        cal.firstDayOfWeek = Calendar.SUNDAY // 시작일과 특정날짜의 차이를 구한다
        val dayOfWeek = cal[Calendar.DAY_OF_WEEK] - cal.firstDayOfWeek // 해당 주차의 첫째날을 지정한다
        cal.add(Calendar.DAY_OF_MONTH, -dayOfWeek)
        val sf = SimpleDateFormat("yyyy / MM / dd") // 해당 주차의 첫째 날짜
        val startDt = sf.format(cal.time) // 해당 주차의 마지막 날짜 지정
        daylist.add(startDt)
        for (i in 1..6) {
            val year = cal[Calendar.YEAR]
            val month = cal[Calendar.MONTH] + 1
            val day = cal[Calendar.DAY_OF_MONTH]
            cal.add(Calendar.DAY_OF_MONTH, 1)
            daylist.add("${year} / ${month} / ${day}") // 해당 주차의 마지막 날짜
        }
    }

}
