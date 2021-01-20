package com.example.sqlitedatabase1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. DBHelper는 context를 매개변수로 받기 때문에 this를 넣어준다
        val helper = DBHelper(this)

        // 2. 쿼리문 전달
        helper.writableDatabase.close()

        // 3. 버튼 클릭 시 데이터 세팅하게 하기
        button.setOnClickListener {
            val helper = DBHelper(this)

            // 4. 쿼리 테이블 세팅
            val sql = """
            insert into TestTable (textData, intData, floatData, dateData)  
            values(?, ?, ?, ?)
        """.trimIndent()

            // 5. 날짜 데이터를 널을거라 생성한다
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val now = sdf.format(Date())

            // 6. ?에 바인딩될 값 정의
            // 변수형 상관없이 다 문자열로 넣으면된다
            val arg1 = arrayOf("문자열1", "100", "11.11", now)
            val arg2 = arrayOf("문자열2", "200", "22.22", now)

            // 7. 저장
            helper.writableDatabase.execSQL(sql, arg1)
            helper.writableDatabase.execSQL(sql, arg2)
        }

        button2.setOnClickListener {
            val helper = DBHelper(this)

            val sql = "select * from TestTable"

            val c1 = helper.writableDatabase.rawQuery(sql, null)
            textView.text = ""

            // moveToNext: 다음 컬럼으로 이동
            // 이동 성공시 true를 반환받음, 즉 모든 데이터 조회하고 싶으면 while 사용
            while (c1.moveToNext()){
                // 가져올 컬럼의 인덱스 번호를 추출한다
                val idx1 = c1.getColumnIndex("idx")
                val idx2 = c1.getColumnIndex("textData")
                val idx3 = c1.getColumnIndex("intData")
                val idx4 = c1.getColumnIndex("floatData")
                val idx5 = c1.getColumnIndex("dateData")

                val idx = c1.getInt(idx1)
                val textData = c1.getString(idx2)
                val intData = c1.getInt(idx3)
                val floatData = c1.getDouble(idx4)
                val dateData = c1.getString(idx5)

                textView.append("idx: $idx\n")
                textView.append("textData: $textData\n")
                textView.append("intData: $intData\n")
                textView.append("floatData: $floatData\n")
                textView.append("dateData: $dateData\n")
            }
        }
        // 데이터 갱신하기
        button3.setOnClickListener {
            val helper = DBHelper(this)

            // 첫 번째 인덱스의 데이터를 해당 데이터로 업데이트함
            // 첫 번째인 이유는 idx = 1이기때문
            val sql = "update TestTable set textData = ? where idx = ?"
            val arg1 = arrayOf("문자열3", "1")

            helper.writableDatabase.execSQL(sql, arg1)
            helper.writableDatabase.close()

            textView.text = "수정완료료"
        }
        // 데이터 삭제하기
        button4.setOnClickListener {
            val helper = DBHelper(this)

            // 인덱스 1의 데이터 전부 삭제
            val sql = "delete from TestTable where idx = ?"
            val arg1 = arrayOf("1")

            helper.writableDatabase.execSQL(sql, arg1)
            helper.writableDatabase.close()

            textView.text = "삭제 완료료"
        }
    }
}