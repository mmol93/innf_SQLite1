package com.example.sqlitedatabase1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper : SQLiteOpenHelper{
    // 1. "Test.db"라는 이름으로 새로운 데이터 파일 만듬 / 연다
    constructor(context: Context) : super(context, "Test.db",null, 1)

    // 2. 해당 이름의 데이터 파일이 없을 경우에만 호출하는 메서드
    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("test", "데이터베이스 생성")

        // 테이블 구조 생성
        // 즉, idx를 key 컬럼으로 선정하고
        // textData, intData, floatData, dateDate 컬럼을 만든다
        val sql = """
            create table TestTable
                (idx integer primary key autoincrement,
                textData text not null,
                intData integer not null,
                floatData real not null,
                dateData date null)
        """.trimIndent()

        // 데이터 베이스 구성 실행
        db?.execSQL(sql)
    }

    // 3. version을 업그레이드 할 경우 호출되는 메서드
    // 최신 형태의 테이블을 갱신하는 쿼리문을 작성
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("test", "업그레이드됨 $oldVersion -> $newVersion")
        if (oldVersion == 1){
            // 1에서 2 버전으로 업글하는 코딩입력
            // 기본적으로 onCreate()에서 정의한 내용을 업그레이드 하면된다
        }
    }
}