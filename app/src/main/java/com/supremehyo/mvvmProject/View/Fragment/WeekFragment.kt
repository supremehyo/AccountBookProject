package com.supremehyo.mvvmProject.View.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.supremehyo.mvvmProject.Adapter.CustomAdapter
import com.supremehyo.mvvmProject.Model.Service.AccountBookContacts
import com.supremehyo.mvvmProject.MyApplication
import com.supremehyo.mvvmProject.R
import com.supremehyo.mvvmProject.View.BookAddActivity
import com.supremehyo.mvvmProject.View.EditActivity
import com.supremehyo.mvvmProject.ViewModel.BookAddViewModel
import kotlinx.android.synthetic.main.fragment_week.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class WeekFragment : Fragment() {

    private  var date : String? = null
    val viewModel: BookAddViewModel by viewModel() // Koin 으로 의존성 주입
    private  var accountbookArrayList = ArrayList<AccountBookContacts>()
    var daylist : ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            date = it.getString("date")

        }
        date = MyApplication.date.date
        week(date!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_week, container, false)

        var recyclerView : RecyclerView = view.findViewById(R.id.week_account_book_rv)

        val context = container?.context
        if (context != null) {
                viewModel.getWeekBookAccount(context,daylist)
        }
        viewModel.week_bookAcountLiveData.observe(this , androidx.lifecycle.Observer {
            it.forEach{
                //여기에 for문 돌면서 data 를 list 로 바꿔주고 그걸 어뎁터로 넘기면 될거 같다.
                Log.v("로그dd", it.memo)
                accountbookArrayList.add(it);

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

            }

        })
        //리사이클러뷰 클릭기능 구현 https://ddolcat.tistory.com/591
        // -> 클릭해서 수정하는 화면 나오도록 구현


        // 플로팅 버튼을 눌렀을 때startActivity
        view.week_add_bt.setOnClickListener {
            val bookAddIntent = Intent(activity, BookAddActivity::class.java)
            startActivity(bookAddIntent)
        }


        return  view
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