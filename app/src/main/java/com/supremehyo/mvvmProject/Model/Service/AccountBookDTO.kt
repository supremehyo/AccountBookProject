package com.supremehyo.mvvmProject.Model.Service

import java.util.*

data class AccountBookDTO(val id:Long,
                          var date : String,
                          var money : String ,
                          var where_pay : String ,
                          var memo: String)