package com.supremehyo.mvvmProject.Model.Service

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single
import java.util.*
import kotlin.collections.ArrayList

//https://dalgonakit.tistory.com/141 room 사용법 참고
// 테이블인 Entity 쿼리로 접근하는 인터페이스
@Dao
interface AccountBookDAO {
    // 파라미터를 쿼리문안에 쓰고 싶으면 : 를 적어주고 사용
    @Query("SELECT * FROM tb_addAccountContacts where date =:date ")
    fun getAll(date: String): List<AccountBookContacts>

    @Insert
    fun insertAll(vararg contacts: AccountBookContacts)

    @Insert
    fun insertAccountBook(contacts: AccountBookContacts) : Long

    @Delete
    fun delete(contacts: AccountBookContacts)
}