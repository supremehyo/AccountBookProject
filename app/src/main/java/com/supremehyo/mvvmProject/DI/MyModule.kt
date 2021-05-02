package com.supremehyo.mvvmProject.DI

import com.supremehyo.mvvmProject.Model.DataModel
import com.supremehyo.mvvmProject.Model.DataModelImpl
import com.supremehyo.mvvmProject.View.BookAddActivity
import com.supremehyo.mvvmProject.ViewModel.BookAddViewModel
import com.supremehyo.mvvmProject.ViewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

var retrofitPart = module {
    /*
    single<KakaoSearchService> {
        Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KakaoSearchService::class.java)
    } // single 은 factory와 다르게 한번만 만든다. 싱글톤이랑 비슷함.*/
}

var modelPart = module {
    //factory는 이 안에 들어있는 클래스를 공장처럼 찍어내는 역할을 한다.
    //inject 할때마다 새로운 인스턴스를 가져온다.
    single <DataModel> {
       DataModelImpl(context = BookAddActivity())// 이 클래스가 필요한 곳에서 get 을 하면 이 클래스가 거기로 들어간다.
    }
}
var adapterPart = module {
}
var viewModelPart = module {

    viewModel {
       MainViewModel(get())
        BookAddViewModel(get())
    }
}

var myDiModule = listOf(retrofitPart, adapterPart, modelPart, viewModelPart) // 이렇게 묶어주면 최상위 Application 클래스에서 의존성을 다 가져올수있다.
//사용하면 좋은점은 외부에서 만들어서 넣어주기 떄문에 깔끔하고 쉽게 주입가능
// 테스트에 용이해짐, 객체를 번거롭게 생성해주지 않아도 된다, 변수사용이 줄어든다
// 클래스간 결합도를 낮춘다.