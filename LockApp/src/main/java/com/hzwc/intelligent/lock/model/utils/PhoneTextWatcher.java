package com.hzwc.intelligent.lock.model.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

//import static com.yanzhenjie.nohttp.tools.ResCompat.getColor;


/**
 * Created by Anna
 *
 */
public class PhoneTextWatcher implements TextWatcher {

    private EditText _text;

    public PhoneTextWatcher(EditText _text) {
        this._text = _text;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (s == null || s.length() == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                continue;
            } else {
                sb.append(s.charAt(i));
                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                    sb.insert(sb.length() - 1, ' ');
                }
            }
        }
        if (!sb.toString().equals(s.toString())) {
            int index = start + 1;
            if (sb.charAt(start) == ' ') {
                if (before == 0) {
                    index++;
                } else {
                    index--;
                }
            } else {
                if (before == 1) {
                    index--;
                }
            }
            _text.setText(sb.toString());
            _text.setSelection(index);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {
//        _text.setTextColor(getColor(R.color.text_color_black));
    }
}
