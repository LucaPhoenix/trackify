package de.info6.trackify;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public interface VerkehrsmittelSpinner {

    String[] dropdownVerkehrsmittel = new String[]{"Bitte auswählen", "Straßenbahn", "Bus", "Rufaxi", "Nahverkehrszug"};

    static void FillSpinner(Context context, Spinner dropdown_verkehrsmittel){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, dropdownVerkehrsmittel);
        dropdown_verkehrsmittel.setAdapter(adapter);

        dropdown_verkehrsmittel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String verkehrsmittel = dropdownVerkehrsmittel[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });
    }
}
