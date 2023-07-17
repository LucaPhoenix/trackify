package de.info6.trackify;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TextView;
import android.widget.TimePicker;


import java.util.Calendar;

/**
 * Interface for the TimePickerDIalog, so it doesn't have to be implemented 20 times
 */
public interface OpenTimePicker {
    /**
     * Method to open the TimePickerDialog, TextView has to be put as param to get a result because TimePicker can only do .setText() apparently
     * @param context
     * @param random
     */
    static void openTimePickerDialog(Context context, TextView random){
        final String[] times = new String[1];
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String hours, minutes;
                if(hour<10){
                    hours = "0" + String.valueOf(hour);
                }
                else {
                    hours = String.valueOf(hour);
                }
                if(minute<10){
                    minutes = "0" + String.valueOf(minute);
                }
                else {
                    minutes = String.valueOf(minute);
                }
                times[0] = hours + ":" + minutes;
                random.setText(times[0]);
            }
        }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);
        timePickerDialog.show();
        timePickerDialog.updateTime(Calendar.HOUR_OF_DAY, Calendar.MINUTE);
    }
}
