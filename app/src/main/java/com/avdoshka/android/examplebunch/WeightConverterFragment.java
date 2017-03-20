package com.avdoshka.android.examplebunch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

public class WeightConverterFragment extends Fragment{
    private String LOG_TAG = "WeightConverterFragment";

    // значение в тоннах, которое всегда обновляется при обновлении любого EditText
    // Оно потом пересчитывается для всех EditText, которые не в фокусе
    private double tonsValue;

    // номер EditText, который находится в фокусе - нужен для расчета tonsValue
    private int chosenIndex;

    // таблица перевода тонн в тонны, центнеры, килограммы, граммы
    private static final double [] convertRatiosFromTons = new double [] {1, 10, 1000, 1000000};

    private  EditText [] editTexts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_weight_converter, container, false);

        EditText tonEditText = (EditText) v.findViewById(R.id.ton_et);
        final EditText centnerEditText = (EditText) v.findViewById(R.id.centner_et);
        EditText kilogramEditText = (EditText) v.findViewById(R.id.kilogram_et);
        EditText gramEditText = (EditText) v.findViewById(R.id.gram_et);

        editTexts = new EditText[] {tonEditText, centnerEditText, kilogramEditText, gramEditText};

        for (EditText editText : editTexts) {

            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {

                    EditText chosenEditText = (EditText)view;

                    //определяем номер строки таблицы, в которой стоит chosenEditText
                    TableRow currentTableRow = (TableRow) chosenEditText.getParent();
                    TableLayout tableLayout = (TableLayout) currentTableRow.getParent();
                    int childCount = tableLayout.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        if (currentTableRow == tableLayout.getChildAt(i)) {
                            chosenIndex = i;
                            Log.d(LOG_TAG, "chosenIndex = " + chosenIndex );
                            break;
                        }
                    }

                    if (b) {
                        chosenEditText.addTextChangedListener(textWatcher);
                    }
                    else {
                        chosenEditText.removeTextChangedListener(textWatcher);
                    }
                }
            });
        }

        initToolbar();

        return v;
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            for (int i = 0; i < editTexts.length; i++) {

                // Если пока цифр не набрано, выходим из метода раньше
                if (TextUtils.isEmpty(s))
                    return;
                // записываем значение тонн
                double enteredValue = Double.valueOf(s.toString());
                tonsValue = enteredValue / convertRatiosFromTons[chosenIndex];
                // пересчитываем все поля, которые не в фокусу
                if (!editTexts[i].hasFocus()) {
                    editTexts[i].setText(String.valueOf(tonsValue * convertRatiosFromTons[i]));
                }
            }
        }
    };

    private void initToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar toolbar =  activity.getSupportActionBar();
        toolbar.setTitle(getString(R.string.launch_weight_converter_text));

    }
}
