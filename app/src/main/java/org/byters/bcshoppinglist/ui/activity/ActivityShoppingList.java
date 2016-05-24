package org.byters.bcshoppinglist.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.ui.dialog.DialogInput;
import org.byters.bcshoppinglist.ui.fragment.FragmentPageList;
import org.byters.bcshoppinglist.ui.fragment.FragmentPageShoppingList;

public class ActivityShoppingList extends ActivityBase
        implements View.OnClickListener
        , DialogInterface.OnClickListener {

    private static final String TAG_PAGE = "tag_fragment";
    private DialogInput dialog;

    public static void display(Context context) {
        context.startActivity(new Intent(context, ActivityShoppingList.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        initToolbar();
        initFloatingAction();
        initPage();
    }

    private void initPage() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_PAGE);
        if (fragment == null) {
            fragment = new FragmentPageList();
            fragment.setRetainInstance(true);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentView, fragment, TAG_PAGE)
                .commit();
    }

    private void initFloatingAction() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_PAGE);
        if (fragment == null) return;
        if (fragment instanceof FragmentPageList) {

            dialog = new DialogInput(this
                    , R.string.dialog_add_list_title
                    , R.string.dialog_add_list_message
                    , R.string.dialog_add_list_hint
                    , R.string.dialog_add_list_button_text
                    , this);
            dialog.show();
            return;
        } else if (fragment instanceof FragmentPageShoppingList) {

            //todo implement other text
            dialog = new DialogInput(this
                    , R.string.dialog_add_item_title
                    , R.string.dialog_add_item_message
                    , R.string.dialog_add_item_hint
                    , R.string.dialog_add_item_button_text
                    , this);
            dialog.show();
            return;
        }
        dialog = null;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_PAGE);
        if (fragment == null) return;
        if (fragment instanceof FragmentPageList) {
            if (this.dialog == null) return;
            ((FragmentPageList) fragment).addItem(this, this.dialog.getTitle());
        } else if (fragment instanceof FragmentPageShoppingList) {
            if (this.dialog == null) return;
            ((FragmentPageShoppingList) fragment).addItem(this, this.dialog.getTitle());
        }

        this.dialog = null;
    }

    public void navigateShoppingList() {
        //todo: add animation
        //transaction.setCustomAnimations(animIn, animOut);

        Fragment fragment = new FragmentPageShoppingList();
        if (fragment == null) return;
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.contentView, fragment, TAG_PAGE)
                .commit();
    }
}
