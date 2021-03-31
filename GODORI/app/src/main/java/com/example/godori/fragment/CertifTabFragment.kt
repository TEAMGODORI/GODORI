package com.example.godori.fragment


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.godori.R
import com.example.godori.activity.CertifTabDetailActivity
import com.example.godori.activity.CertifTabUpload1Activity
import com.example.godori.activity.TabBarActivity
import com.prolificinteractive.materialcalendarview.*
import kotlinx.android.synthetic.main.activity_certif_tab_upload1.*
import kotlinx.android.synthetic.main.activity_certif_tab_upload4.*
import kotlinx.android.synthetic.main.fragment_certif_tab.*
import kotlinx.android.synthetic.main.fragment_certif_tab.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CertifTabFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CertifTabFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)

    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_certif_tab, container, false)
        val materialCalendarView: MaterialCalendarView = view.findViewById(R.id.cal)

        materialCalendarView.state().edit()
            .setFirstDayOfWeek(Calendar.MONDAY)
            .setMinimumDate(CalendarDay.from(2020, 1, 1))
            .setMaximumDate(CalendarDay.today())
            .setCalendarDisplayMode(CalendarMode.WEEKS)
            .commit()

        materialCalendarView.addDecorators(
            SundayDecorator(),
            SaturdayDecorator(),
            OneDayDecorator(materialCalendarView)
        )

        // calendarText에 클릭하면 날짜 표시
        materialCalendarView.setOnDateChangedListener(OnDateSelectedListener { widget, date, selected ->
            val weekdayFormat = SimpleDateFormat("EE", Locale.getDefault())
            val weekDay = weekdayFormat.format(date.getDate())
            calendarText.text = "${date.month + 1}월 ${date.day}일 ${weekDay}요일"
        })

        return view
    }

    class SundayDecorator : DayViewDecorator { //일요일 데코
        private val calendar = Calendar.getInstance()
        override fun shouldDecorate(day: CalendarDay): Boolean {
            day.copyTo(calendar)
            val weekDay = calendar[Calendar.DAY_OF_WEEK]
            return weekDay == Calendar.SUNDAY
        }

        override fun decorate(view: DayViewFacade) {
//            view.addSpan(ForegroundColorSpan(Color.RED))
        }
    }

    class SaturdayDecorator : DayViewDecorator { //토요일 데코
        private val calendar = Calendar.getInstance()
        override fun shouldDecorate(day: CalendarDay): Boolean {
            day.copyTo(calendar)
            val weekDay = calendar[Calendar.DAY_OF_WEEK]
            return weekDay == Calendar.SATURDAY
        }

        override fun decorate(view: DayViewFacade) { //토요일일 경우, 파란색
//            view.addSpan(ForegroundColorSpan(Color.BLUE))
        }
    }

    class OneDayDecorator(context: MaterialCalendarView) : DayViewDecorator { //오늘 날짜에 표시
        private var date: CalendarDay?
        private val calendar = Calendar.getInstance()
        val drawable: Drawable = context.resources.getDrawable(R.drawable.button_circle)


        override fun shouldDecorate(day: CalendarDay): Boolean { //date가 day오늘 날짜랑 같은지 비교
            return date != null && day == date
        }

        override fun decorate(view: DayViewFacade) {
//            view.addSpan(StyleSpan(Typeface.BOLD))
//            view.addSpan(RelativeSizeSpan(1.4f))
            view.addSpan(ForegroundColorSpan(Color.BLUE))
//            view.setBackgroundDrawable(drawable)
        }
        /**
         * We're changing the internals, so make sure to call [MaterialCalendarView.invalidateDecorators]
         */
        fun setDate(date: Date?) {
            this.date = CalendarDay.from(date)
        }

        init {
            date = CalendarDay.today()
        }
    }

    // 리사이클러뷰
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // item List 준비
        val personList = ArrayList<PersonForList>()
        for (i in 0 until 7) { // 이후 명수 가져오기
            personList.add(PersonForList("" + i + " 사람"))
        }

        // adapter 생성
        val adapter = certifRecyclerViewAdapter(personList, LayoutInflater.from(this.context))
        certifRecycler.adapter = adapter
        certifRecycler.layoutManager = GridLayoutManager(this.context, 2)

    }

    // 사람별 인증 리스트
    class PersonForList(val name: String) {

    }

    //인증탭 리사이클러뷰 어댑터
    class certifRecyclerViewAdapter(
        val itemList: ArrayList<PersonForList>,
        val inflater: LayoutInflater
    ) : RecyclerView.Adapter<certifRecyclerViewAdapter.ViewHolder>() {

        @SuppressLint("RestrictedApi")
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //
            var personName: TextView
            var certifImg: ImageView

            init {
                personName = itemView.findViewById(R.id.personName)
                certifImg = itemView.findViewById(R.id.certifImg)

                // Person 아이템을 눌렀을 때
                itemView.setOnClickListener { view ->
//                    val posintion: Int = adapterPosition
//                    val personName = itemList.get(posintion).name

                    //CertifTabDetailActivity로 이동
                    val intent = Intent(view.context, CertifTabDetailActivity::class.java)
                    view.getContext().startActivity(intent)
                }
            }
        }

        // 아이템 하나가 들어갈 뷰를 만들고 뷰 홀더(*****)에 넣어줌
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = inflater.inflate(R.layout.item_certif_tab, parent, false)
            return ViewHolder(view)
        }

        //리스트의 전체 개수
        override fun getItemCount(): Int {
            return itemList.size
        }

        //뷰를 그리는 부분
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.certifImg.setImageResource(R.drawable.certif_un)
            holder.personName.setText(itemList.get(position).name)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var date: CalendarDay
        val calendar = Calendar.getInstance()

        uploadBtn1.setOnClickListener {
            val intent = Intent(getActivity(), CertifTabUpload1Activity::class.java)
            startActivity(intent)
        }
        val currentTime = Calendar.getInstance().time
        val weekdayFormat = SimpleDateFormat("EE", Locale.getDefault())
        val dayFormat = SimpleDateFormat("d", Locale.getDefault())
        val monthFormat = SimpleDateFormat("M", Locale.getDefault())

        val month = monthFormat.format(currentTime)
        val day = dayFormat.format(currentTime)
        val weekDay = weekdayFormat.format(currentTime)

        //오늘 날짜 가져오기
        calendarText.text = month + "월 " + day + "일 " + weekDay + "요일"


    }

}