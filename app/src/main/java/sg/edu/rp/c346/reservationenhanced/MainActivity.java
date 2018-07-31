package sg.edu.rp.c346.reservationenhanced;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView name;
    EditText namepl;
    TextView number;
    EditText numberpl;
    TextView size;
    EditText sizepl;
    CheckBox smokin;
    TextView day;
    EditText daypl;
    TextView time;
    EditText timepl;
    Button reserve;
    Button reset;

    @Override
    protected void onPause() {
        super.onPause();

        String strName = namepl.getText().toString();
        String strNum = numberpl.getText().toString();
        String strSize = sizepl.getText().toString();
        String strDay = daypl.getText().toString();
        String strTime = timepl.getText().toString();
        final CheckBox checkBox = findViewById(R.id.smoking);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor preferenceEdit = pref.edit();

        if(pref.contains("checked") && pref.getBoolean("checked",false) == true) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkBox.isChecked()) {
                    preferenceEdit.putBoolean("checked", true);
                    preferenceEdit.commit();
                }else{
                    preferenceEdit.putBoolean("unchecked", false);
                    preferenceEdit.commit();
                }
            }
        });

        preferenceEdit.putString("myName", strName);
        preferenceEdit.putString("myNum", strNum);
        preferenceEdit.putString("mySize", strSize);
        preferenceEdit.putString("myDay", strDay);
        preferenceEdit.putString("myTime", strTime);

        preferenceEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        String msg1 = pref.getString("myName", "");
        String msg2 = pref.getString("myNum", "");
        String msg3 = pref.getString("mySize", "");
        String msg4 = pref.getString("myDay", "");
        String msg5 = pref.getString("myTime", "");

        namepl.setText(msg1);
        numberpl.setText(msg2);
        sizepl.setText(msg3);
        daypl.setText(msg4);
        timepl.setText(msg5);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.textViewName);
        namepl=findViewById(R.id.editTextName);
        number=findViewById(R.id.textViewNum);
        numberpl=findViewById(R.id.editTextNum);
        size=findViewById(R.id.textViewSize);
        sizepl=findViewById(R.id.editTextSize);
        smokin=findViewById(R.id.smoking);
        day=findViewById(R.id.textViewDay);
        daypl=findViewById(R.id.editTextDay);
        time=findViewById(R.id.textViewTime);
        timepl=findViewById(R.id.editTextTime);
        reserve=findViewById(R.id.btnReserve);
        reset=findViewById(R.id.btnReset);

        daypl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        daypl.setText("Date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                    }
                };

                Calendar c= Calendar.getInstance();
                int mYear=c.get(Calendar.YEAR);
                int mMonth=c.get(Calendar.MONTH);
                int mDay=c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog myDateDialog=new DatePickerDialog(MainActivity.this,myDateListener,mYear,mMonth,mDay);
                myDateDialog.show();
            }
        });

        timepl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener myTimeListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        timepl.setText("Time: "+hourOfDay+":"+minute);

                    }
                };


                Calendar c= Calendar.getInstance();
                int mHour=c.get(Calendar.HOUR_OF_DAY);
                int mMinute=c.get(Calendar.MINUTE);

                TimePickerDialog myTimeDialog=new TimePickerDialog(MainActivity.this,myTimeListener,mHour,mMinute,true);
                myTimeDialog.show();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.details, null);

                final TextView Name = viewDialog.findViewById(R.id.tvName);
                final TextView Smoke = viewDialog.findViewById(R.id.tvSmoking);
                final TextView Size = viewDialog.findViewById(R.id.tvSize);
                final TextView Date = viewDialog.findViewById(R.id.tvDate);
                final TextView Time = viewDialog.findViewById(R.id.tvTime);

                String nameStr = namepl.getText().toString();
                String teleStr = numberpl.getText().toString();
                String sizeStr = sizepl.getText().toString();
                String dateStr = daypl.getText().toString();
                String timeStr = timepl.getText().toString();


                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setView(viewDialog);
                myBuilder.setTitle("Confrim Your Order");

                if (Smoke.isActivated()) {
                    myBuilder.setMessage("New Reservation \nName: " + nameStr +
                            "\nTelephone: " + teleStr +
                            "\nSmoking: Yes"  +
                            "\nSize: " + sizeStr +
                            "\n" + dateStr +
                            "\n" + timeStr);
                } else {
                    myBuilder.setMessage("New Reservation \nName: " + nameStr +
                            "\nTelephone: " + teleStr +
                            "\nSmoking: No" +
                            "\nSize: " + sizeStr +
                            "\n" + dateStr +
                            "\n" + timeStr);
                }
                myBuilder.setCancelable(true);
                myBuilder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                myBuilder.setNegativeButton("CANCEL",null);
                AlertDialog myDialog=myBuilder.create();
                myDialog.show();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                namepl.setText("");
                numberpl.setText("");
                sizepl.setText("");
                smokin.setChecked(false);
                daypl.setText("");
                timepl.setText("");
            }

        });

    }
}
