package org.byters.bcshoppinglist.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import org.byters.bcshoppinglist.R;

public class DialogInput {
    @NonNull
    private AlertDialog dialog;
    private EditText editText;

    public DialogInput(Context context
            , @StringRes int titleId
            , @StringRes int messageId
            , @StringRes int hintId
            , @StringRes int buttonTextId, DialogInterface.OnClickListener listener) {
        dialog = new AlertDialog.Builder(context)
                .setTitle(titleId)
                .setMessage(messageId)
                .setPositiveButton(buttonTextId, listener)
                .create();

        LayoutInflater inflater = dialog.getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_input, null);

        dialog.setView(dialoglayout);
        editText = (EditText) dialoglayout.findViewById(R.id.et_dialog);
        editText.setHint(hintId);
    }

    public void show() {
        dialog.show();
    }

    @Nullable
    public String getTitle() {
        return editText.getText().toString();
    }

}
