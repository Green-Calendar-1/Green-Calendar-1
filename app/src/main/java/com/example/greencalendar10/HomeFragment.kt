package com.example.greencalendar10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greencalendar10.databinding.FragmentHomeBinding
import com.example.greencalendar10.databinding.FragmentMyDiaryBinding
import com.navercorp.nid.NaverIdLoginSDK.applicationContext
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class HomeFragment: Fragment(R.layout.fragment_home) {


    private lateinit var binding: FragmentHomeBinding

    //년,월 변수
    lateinit var selectedDate: LocalDate

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)


        //binding초기화
        //binding = DataBindingUtil.setContentView(this, R.layout.fragment_home) <?

        //현재날짜
        selectedDate = LocalDate.now()

        //화면설정
        setMonthView()

        //이전달 버튼 이벤트
        binding.prevBtn.setOnClickListener {
            //현재 월 -1 변수에 담기
            selectedDate = selectedDate.minusMonths(1)
            setMonthView()
        }
        //다음달 버튼 이벤트
        binding.nextBtn.setOnClickListener {
            selectedDate = selectedDate.plusMonths(1)
            setMonthView()
         }

        return binding.root
    }

    //**날짜 화면에 보여주기
    private fun setMonthView(){
        //년월 텍스트뷰 실행
        binding.monthYearText.text = monthYearFromDate(selectedDate)

        //날짜 생성해서 리스트에 담기
        val dayList = dayInMonthArray(selectedDate)


/*        //어뎁터 초기화
        val adapter = CalendarAdapter(dayList)

        //레이아웃 설정 ( 열 7개)
        var manager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext,7)

        //레이아웃 적용
        binding.recyclerView.layoutManager = manager


        //어뎁터 적용
        binding.recyclerView.adapter = adapter*/

    }

    //날짜 타입 설정
    private fun monthYearFromDate(date: LocalDate): String{

        var formatter = DateTimeFormatter.ofPattern("YYYY M월")
            return date.format(formatter)
    }
    //**날짜 생성
    private fun dayInMonthArray(date: LocalDate): ArrayList<String> {

        var dayList = ArrayList<String>()

        var yearMonth = YearMonth.from(date)

        //해당 월 마지막 날짜 가져오기(28,30,31..)
        var lastDay = yearMonth.lengthOfMonth()

        //해당 월 첫번째 날짜(9월 1일...)
        var firstDay = selectedDate.withDayOfMonth(1)

        //첫번째 날 요일 (월:1, 요일:7)
        var dayOfWeek = firstDay.dayOfWeek.value

        for (i in 1..41){
            if(i <= dayOfWeek || i > (lastDay + dayOfWeek)){
                dayList.add("")
            } else {
                dayList.add((i - dayOfWeek).toString())
            }
        }

        return dayList

    }


}