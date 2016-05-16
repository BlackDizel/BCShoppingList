package org.byters.bcshoppinglist.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.byters.bcshoppinglist.R;
import org.byters.bcshoppinglist.ui.fragment.FragmentPageList;

public class ActivityMain extends ActivityBase
        implements View.OnClickListener {

    private static final String TAG_PAGE = "tag_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    @Override
    public void onClick(View v) {
        //todo: implement.
        //todo: our app will have 2 screens: lists and shopping list.
        //todo: screens will be fragment, which loaded into main activity
        //todo: over fragments one activity fab will shown
        //todo: fab action depends on fragment shown
    }
}
