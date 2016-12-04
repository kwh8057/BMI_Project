package com.example.wook.bmi_project;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CalendarView;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.example.woooo.bmi_project.R;

        import java.util.Calendar;

public class AddBMIActivity extends AppCompatActivity {

    Button btn_cancle;
    Button btn_ok;

    EditText et_height;
    EditText et_weight;
    CalendarView calendarView;
    Calendar calendar;

    String str_height;
    String str_weight;
    String date;
    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bmi);

        btn_cancle = (Button)findViewById(R.id.btn_cancle);
        btn_ok = (Button)findViewById(R.id.btn_ok);

        et_height = (EditText)findViewById(R.id.et_height);
        et_weight = (EditText)findViewById(R.id.et_weight);
        calendarView = (CalendarView)findViewById(R.id.calendarView);

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_height = et_height.getText().toString();
                str_weight = et_weight.getText().toString();

                calendar = Calendar.getInstance();

                switch (calendar.get(Calendar.DAY_OF_WEEK)){
                    case 1:
                        day = " (일)";
                        break;
                    case 2:
                        day = " (월)";
                        break;
                    case 3:
                        day = " (화)";
                        break;
                    case 4:
                        day = " (수)";
                        break;
                    case 5:
                        day = " (목)";
                        break;
                    case 6:
                        day = " (금)";
                        break;
                    case 7:
                        day = " (토)";
                        break;
                }

                if(date == null){
                    date = calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.DAY_OF_MONTH);
                }

                if(str_height.equals("") && str_weight.equals("")){
                    Toast.makeText(MyApplication.getsContext(), "키와 몸무게를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent it = new Intent();

                    it.putExtra("str_height", str_height);
                    it.putExtra("str_weight", str_weight);
                    it.putExtra("date", date);
                    it.putExtra("day", day);

                    setResult(100, it);
                    finish();
                }

            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                month++;
                date = year + "." + month + "." + dayOfMonth;
            }
        });
    }
}