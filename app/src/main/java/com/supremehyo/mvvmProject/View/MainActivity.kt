package com.supremehyo.mvvmProject.View


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.supremehyo.mvvmProject.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.supremehyo.mvvmProject.ViewModel.MainViewModel
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.supremehyo.mvvmProject.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseKotlinActivity<ActivityMainBinding, MainViewModel>(){

    override val viewModel: MainViewModel by viewModel() // Koin 으로 의존성 주입
    override val layoutResourceId: Int
        get() = R.layout.activity_main


    companion object {
        const val RESULT_CODE = 10
    }


    private val googleSignInIntent by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        GoogleSignIn.getClient(this, gso).signInIntent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        sign_in_button.setOnClickListener {
            startActivityForResult(googleSignInIntent, RESULT_CODE)
        }

        goMain_bt.setOnClickListener {
            val nextIntent = Intent(this, HomeActivity::class.java)
            startActivity(nextIntent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == RESULT_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            result?.let {
                if (it.isSuccess) {
                    it.signInAccount?.displayName //이름
                    it.signInAccount?.email //이메일
                    Log.e("Value", it.signInAccount?.email!!)
                    // 기타 등등
                } else  {
                    Log.e("Value", "error")
                    // 에러 처리
                }
            }
        }

    }

    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }
}