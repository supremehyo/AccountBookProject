package com.supremehyo.mvvmProject.Model.Service

import androidx.room.*

//테이블 만드는거 밑에 적어준건 테이블 이름이랑 
//컬럼을 만드는거
@Entity(tableName = "tb_addAccountContacts")
data class AccountBookContacts(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var date: String,
    var money: String,
    var where_pay: String,
    var memo: String
)