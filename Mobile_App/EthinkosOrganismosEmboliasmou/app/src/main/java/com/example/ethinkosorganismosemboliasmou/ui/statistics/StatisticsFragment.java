package com.example.ethinkosorganismosemboliasmou.ui.statistics;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ethinkosorganismosemboliasmou.R;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatisticsFragment extends Fragment {

    private StatisticsModel statisticsModel;

    // variables for my date and time pickers
    private Button btnDatePickerFrom, btnDatePickerTo;
    private EditText txtDateFrom, txtDateTo;
    private int mYear, mMonth, mDay;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statisticsModel =
                new ViewModelProvider(this).get(StatisticsModel.class);
        View root = inflater.inflate(R.layout.fragment_statistics, container, false);

        // Remove the statistics results
        LinearLayout statisticsResults = (LinearLayout) root.findViewById(R.id.statistics_results);
        statisticsResults.setVisibility(View.GONE);

        btnDatePickerFrom=(Button)root.findViewById(R.id.date_from_button);
        txtDateFrom=(EditText)root.findViewById(R.id.date_from_field);

        btnDatePickerTo=(Button)root.findViewById(R.id.date_to_button);
        txtDateTo=(EditText)root.findViewById(R.id.date_to_field);

        // Listener to handle date picks by the user for the Date From
        btnDatePickerFrom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtDateFrom.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        // Listener to handle date picks by the user for the Date To
        btnDatePickerTo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtDateTo.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        // Listener to check the user input and get the final results
        Button getStatisticsButton = (Button) root.findViewById(R.id.get_statistics_button);
        getStatisticsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Remove the results from previous inputs
                LinearLayout linearLayout = (LinearLayout) root.findViewById(R.id.statistics_results);
                linearLayout.setVisibility(View.GONE);
                // Delete the content of the result tables but keep the headers
                TableLayout resultsTable = (TableLayout) root.findViewById(R.id.results_table);
                TableRow firstRow = (TableRow) root.findViewById(R.id.result_headers);
                resultsTable.removeAllViewsInLayout();
                resultsTable.addView(firstRow);


                // find out if the user has given inputs
                EditText from = (EditText) root.findViewById(R.id.date_from_field);
                EditText to = (EditText) root.findViewById(R.id.date_to_field);

                String fromText = from.getText().toString();
                String toText = to.getText().toString();

                if(fromText.equals("") || toText.equals("")){
                    // do not let the user go through without dates for input
                    return;
                }

                // make Date variables to make test for the way the user has given inputs
                Date realDateFrom = null;
                Date realDateTo = null;
                try {
                    realDateFrom = new SimpleDateFormat("dd-MM-yyyy").parse(fromText);
                    realDateTo = new SimpleDateFormat("dd-MM-yyyy").parse(toText);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(!realDateTo.after(realDateFrom) && !(realDateTo.compareTo(realDateFrom)==0)){
                    return;
                }

                Date today = new Date();
                if(today.before(realDateTo)){
                    return;
                }

                // The user has given good inputs and the below code will make the REST API call

                // Getting ready to make a retrofit call
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://data.gov.gr/api/v1/query/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                // I will use my own interface to get many records of the class Records to use them for my data
                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                fromText = formatter.format(realDateFrom);
                toText = formatter.format(realDateTo);

                // I am giving the query variables
                Call<List<Record>> call = jsonPlaceHolderApi.getRecords(fromText,toText);

                // I am making the call
                Date finalRealDateTo = realDateTo;
                Date finalRealDateFrom = realDateFrom;
                call.enqueue(new Callback<List<Record>>() {
                    // If the call has a response then ...
                    @Override
                    public void onResponse(Call<List<Record>> call, Response<List<Record>> response) {
                        // if it is not between status 200-300 then show a debug message
                        if(!response.isSuccessful()){
                            Log.d("NotSuccessfulCall",response.code()+"");
                            return;
                        }

                        // if the call was Successful then get the responce in a Records List
                        List<Record> records = response.body();

                        // Get the amount of days needed to be pursed
                        // For example from 2021-06-01 to 2021-06-02 there are 2 days to be pursed
                        long diff = finalRealDateTo.getTime() - finalRealDateFrom.getTime();
                        long diffDays = diff / (24 * 60 * 60 * 1000) + 1;

                        Date tempDate = finalRealDateFrom;
                        // run every day needed
                        for(int i=0; i<diffDays; i++){
                            // Get the results of the day
                            String[] dayResults = dayResults(records, tempDate);

                            // Make the new Row
                            TextView textView1 = new TextView(getActivity());
                            TextView textView2 = new TextView(getActivity());
                            TextView textView3 = new TextView(getActivity());
                            TextView textView4 = new TextView(getActivity());
                            TextView textView5 = new TextView(getActivity());

                            textView1.setLayoutParams(((TextView)root.findViewById(R.id.first_text_view)).getLayoutParams());
                            textView1.setGravity(Gravity.CENTER);
                            textView1.setPadding(10,10,10,10);
                            textView1.setTextColor(Color.parseColor("#000000"));
                            textView1.setTextSize(11.0f);
                            textView1.setBackgroundColor(Color.parseColor("#e6e6e6"));

                            textView2.setLayoutParams(((TextView)root.findViewById(R.id.first_text_view)).getLayoutParams());
                            textView2.setGravity(Gravity.CENTER);
                            textView2.setPadding(10,10,10,10);
                            textView2.setTextColor(Color.parseColor("#000000"));
                            textView2.setTextSize(11.0f);
                            textView2.setBackgroundColor(Color.parseColor("#f2f2f2"));

                            textView3.setLayoutParams(((TextView)root.findViewById(R.id.first_text_view)).getLayoutParams());
                            textView3.setGravity(Gravity.CENTER);
                            textView3.setPadding(10,10,10,10);
                            textView3.setTextColor(Color.parseColor("#000000"));
                            textView3.setTextSize(11.0f);
                            textView3.setBackgroundColor(Color.parseColor("#e6e6e6"));

                            textView4.setLayoutParams(((TextView)root.findViewById(R.id.first_text_view)).getLayoutParams());
                            textView4.setGravity(Gravity.CENTER);
                            textView4.setPadding(10,10,10,10);
                            textView4.setTextColor(Color.parseColor("#000000"));
                            textView4.setTextSize(11.0f);
                            textView4.setBackgroundColor(Color.parseColor("#f2f2f2"));

                            textView5.setLayoutParams(((TextView)root.findViewById(R.id.first_text_view)).getLayoutParams());
                            textView5.setGravity(Gravity.CENTER);
                            textView5.setPadding(10,10,10,10);
                            textView5.setTextColor(Color.parseColor("#000000"));
                            textView5.setTextSize(11.0f);
                            textView5.setBackgroundColor(Color.parseColor("#e6e6e6"));


                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                            String strTempDate = formatter.format(tempDate);

                            textView1.setText(strTempDate);
                            textView2.setText(dayResults[0]);
                            textView3.setText(dayResults[1]);
                            textView4.setText(dayResults[2]);
                            textView5.setText(dayResults[3]);

                            TableRow row= new TableRow(getActivity());

                            // Build the row
                            row.addView(textView1);
                            row.addView(textView2);
                            row.addView(textView3);
                            row.addView(textView4);
                            row.addView(textView5);

                            // Insert the row to the table
                            resultsTable.addView(row);

                            // Get the next day
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(tempDate);
                            cal.add(Calendar.DATE, 1);
                            tempDate = cal.getTime();
                        }

                        // Debug message
                        Log.d("MyOwnRecordsSize: ",records.size()+"");
                    }

                    // If the call fail then show a debug message
                    @Override
                    public void onFailure(Call<List<Record>> call, Throwable t) {
                        Log.d("FailedToMakeTheCall",t.getMessage());
                    }
                });

                // Show the results
                linearLayout.setVisibility(View.VISIBLE);
            }
        });

        /*
        final Button button = root.findViewById(R.id.button_statistics);
        statisticsModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                button.setText(s);
            }
        });
        */
        return root;
    }

    // This method will calculate the values needed for a date from the given List
    private String[] dayResults(List<Record> records, Date tempDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strGivenDate = formatter.format(tempDate);

        String[] answers = new String[4];

        int aDoseSum = 0;
        int bDoseSum = 0;
        int totalDoseSum = 0;
        int totalVaccinationsSum = 0;

        for(Record record : records){
            String recordDate = record.getReferencedate().substring(0,10);
            if(recordDate.equals(strGivenDate)){
                aDoseSum += record.getDailydose1();
                bDoseSum += record.getDailydose2();
                totalDoseSum += record.getDaytotal();
                totalVaccinationsSum += record.getTotalvaccinations();
            }
        }

        if(aDoseSum == 0 &&
                bDoseSum == 0 &&
                totalDoseSum == 0 &&
                totalVaccinationsSum == 0){
            answers[0] = "NO DATA";
            answers[1] = "NO DATA";
            answers[2] = "NO DATA";
            answers[3] = "NO DATA";
        }
        else{
            answers[0] = aDoseSum+"";
            answers[1] = bDoseSum+"";
            answers[2] = totalDoseSum+"";
            answers[3] = totalVaccinationsSum+"";
        }

        return answers;
    }


}