package org.mamasdelrio.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.mamasdelrio.android.R;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A view that provides a {@link android.widget.Spinner}. It allows a list of
 * labels--to be shown to the user--and a list of values--to be exported as an
 * identifier--to be paired together.
 */
public class SelectOneView extends LinearLayout {
  private String[] labels;
  private String[] values;
  @Bind(R.id.widget_selectone_spinner) Spinner spinner;
  private ArrayAdapter<String> adapter;

  public SelectOneView(Context context, int labelsResId, int valuesResid) {
    super(context);
    init(context);
    initializeView(labelsResId, valuesResid);
  }

  public SelectOneView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public SelectOneView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  public void addValuesToMap(Map<String, Object> map, String valueKey) {
    map.put(valueKey, getValueForSelected());
  }

  /**
   * Returns the {@link Spinner} backing the view. Most interactions should not
   * require this method.
   */
  public Spinner getSpinner() {
    return spinner;
  }

  /**
   * Initialize the view. This method only needs to be called if you have
   * inflated the view from XML and need to initialize it.
   * @param labelsResId reference to a string-array
   * @param valuesResId reference to a string-array
   */
  public void initializeView(int labelsResId, int valuesResId) {
    Context context = getContext();
    labels = context.getResources().getStringArray(labelsResId);
    values = context.getResources().getStringArray(valuesResId);
    adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,
        labels);
    adapter.setDropDownViewResource(
        android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter);
  }

  public String getValueForSelected() {
    if (!isInitialized()) {
      throw new IllegalStateException("view has not been initialized");
    }
    int selectedPosition = spinner.getSelectedItemPosition();
    return values[selectedPosition];
  }

  public String getLabelForSelected() {
    if (!isInitialized()) {
      throw new IllegalStateException("view has not been initialized");
    }
    int selectedPosition = spinner.getSelectedItemPosition();
    return labels[selectedPosition];
  }

  private boolean isInitialized() {
    return labels != null && values != null;
  }

  /**
   * Inflate the view and bind the views.
   */
  private void init(Context context) {
    LayoutInflater.from(context).inflate(R.layout.widget_selectone, this,
        true);
    ButterKnife.bind(this);
  }
}
