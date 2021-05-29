package com.supremehyo.mvvmProject.View.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.supremehyo.mvvmProject.MyApplication
import com.supremehyo.mvvmProject.R
import kotlinx.android.synthetic.main.fragment_account_book.*
import kotlinx.android.synthetic.main.fragment_account_book.view.*

class AccountBookFragment : Fragment() {
    private  var date : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            date = it.getString("date")
        }

        Log.v("프라그date" , date.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account_book , container , false)

        //여기 디폴트로 일별화면이 나와야함
        var dayFragment = DayFragment()
        val bundle = Bundle()
        bundle.putString("date", date)
        dayFragment.arguments = bundle
        childFragmentManager.beginTransaction()
            .replace(R.id.AccoutBook_fragment , dayFragment).commit();

        
        //일별을 눌렀을때
        view.day.setOnClickListener {
            var dayFragment = DayFragment()
            val bundle = Bundle()
            MyApplication.date.date = date.toString()
            dayFragment.arguments = bundle
            childFragmentManager.beginTransaction().replace(R.id.AccoutBook_fragment , dayFragment).commit();
        }
        //주별을 눌렀을때
        view.week.setOnClickListener {
            var weekFragment = WeekFragment()
            val bundle = Bundle()
            MyApplication.date.date = date.toString()
            weekFragment.arguments = bundle
            childFragmentManager.beginTransaction()
                .replace(R.id.AccoutBook_fragment , WeekFragment()).commit();
        }
        //월별을 눌렀을때
        view.month.setOnClickListener {
            childFragmentManager.beginTransaction()
                .replace(R.id.AccoutBook_fragment , MonthFragment()).commit();
        }

        return view
    }

    fun setDataAtFragment(fragment: AccountBookFragment , date : String){

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_view , fragment).commit()
    }

    override fun onResume() {
        super.onResume()
        var dayFragment = DayFragment()
        val bundle = Bundle()
        bundle.putString("date", date)
        dayFragment.arguments = bundle
        childFragmentManager.beginTransaction()
            .replace(R.id.AccoutBook_fragment , dayFragment).commit();
    }
}