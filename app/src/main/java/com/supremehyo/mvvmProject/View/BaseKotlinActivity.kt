package com.supremehyo.mvvmProject.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.supremehyo.mvvmProject.ViewModel.BaseKotlinViewModel

abstract class BaseKotlinActivity<T : ViewDataBinding, R : BaseKotlinViewModel> : AppCompatActivity() {

    lateinit var viewDataBinding: T

    /**
     * setContentView로 호출할 Layout의 리소스 Id.
     * ex) R.layout.activity_sbs_main
     */
    //BaseKotlinActivity 상속받는 구현 액티비티마다 get 하는 방식으로 넣어주면 된다.
    //onCreate에서 레이아웃 리소스를 설정해줘야하는데, BaseKotlinActivity에서 onCreate를 override해버리니
    //BaseKotlinActivity를 구현한 액티비티는 레이아웃 리소스를 설정해 줄 곳이 없어서 이렇게 만들었음.
    //  override val layoutResourceId: Int
    //        get() = R.layout.activity_main 이런 느낌으로 사용하면 된다.
    abstract val layoutResourceId: Int

    /**
     * viewModel 로 쓰일 변수.
     */
    abstract val viewModel: R

    /**
     * 레이아웃을 띄운 직후 호출.
     * 뷰나 액티비티의 속성 등을 초기화.
     * ex) 리사이클러뷰, 툴바, 드로어뷰..
     */
    abstract fun initStartView()

    /**
     * 두번째로 호출.
     * 데이터 바인딩 및 rxjava 설정.
     * ex) rxjava observe, databinding observe..
     */
    abstract fun initDataBinding()

    /**
     * 바인딩 이후에 할 일을 여기에 구현.
     * 그 외에 설정할 것이 있으면 이곳에서 설정.
     * 클릭 리스너도 이곳에서 설정.
     */
    abstract fun initAfterBinding()

    private var isSetBackButtonValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //일반적인 setContentView 와는 다르지만 데이터바인딩에서는 이렇게하면 된다.

        viewDataBinding = DataBindingUtil.setContentView(this, layoutResourceId)

        initStartView()
        initDataBinding()
        initAfterBinding()
    }
}