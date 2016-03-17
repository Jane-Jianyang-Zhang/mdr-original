package org.mamasdelrio.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import org.mamasdelrio.android.logic.IFormActivity;
import org.mamasdelrio.android.logic.IntegerArrayAdapter;
import org.mamasdelrio.android.logic.JsonHelper;
import org.mamasdelrio.android.logic.TimeStamper;
import org.mamasdelrio.android.util.Constants;
import org.mamasdelrio.android.util.JsonKeys;
import org.mamasdelrio.android.util.JsonValues;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class DoBirthActivity extends AppCompatActivity implements
    IFormActivity {
  @Bind(R.id.birth_enter_dni) EditText dni;
  @Bind(R.id.birth_send) Button send;
  @Bind(R.id.birth_date) DatePicker birthDate;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_do_birth);
    ButterKnife.bind(this);

    send.setEnabled(isReadyToBeSent());
  }

  @Override
  public boolean isReadyToBeSent() {
    return dni.getText().length() > 0;
  }

  @SuppressWarnings("unused")
  @OnTextChanged(R.id.birth_enter_dni)
  public void onTextChanged(CharSequence text) {
    send.setEnabled(isReadyToBeSent());
  }

  @Override
  public Map<String, Object> getMapContent(TimeStamper timeStamper) {
    Map<String, Object> result = new HashMap<>();
    JsonHelper jsonHelper = new JsonHelper(timeStamper);
    jsonHelper.addCommonEntries(result);

    result.put(JsonKeys.SharedKeys.FORM, JsonValues.Forms.BIRTHS);
    result.put(JsonKeys.Births.DNI, dni.getText().toString());
    result.put(JsonKeys.Births.BIRTH_YEAR, birthDate.getYear());
    // +1 because months are zero indexed, but we want to give MDR a user
    // friendly date
    result.put(JsonKeys.Births.BIRTH_MONTH, birthDate.getMonth() + 1);
    result.put(JsonKeys.Births.BIRTH_DAY, birthDate.getDayOfMonth());

    return result;
  }
}