package com.android.two_moons_project.Ui.Activities.main;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.android.two_moons_project.R;
import com.android.two_moons_project.Ui.Activities.Exports.ExportsActivity;
import com.android.two_moons_project.Ui.Activities.Imports.ImportActivity;
import com.android.two_moons_project.Ui.Activities.Reports.ReportsActivity;
import com.android.two_moons_project.common.HelperStuffs.PermissionHandlerFragment;
import com.android.two_moons_project.common.base.BaseActivity;
import com.android.two_moons_project.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity implements PostsAdapter.interAction,BaseActivity.onInit{

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 101;
    private PostViewModel postViewModel;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        onInit();
        setListeners(binding);

//
//        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
//
//        postViewModel.get_Posts();
//        recyclerView = findViewById(R.id.recycler);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        final PostsAdapter postsAdapter = new PostsAdapter(this,this);
//        recyclerView.setAdapter(postsAdapter);
//
//        postViewModel.postMutableLiveData.observe(this, new Observer<List<Posts>>() {
//            @Override
//            public void onChanged(List<Posts> posts) {
//                postsAdapter.setData(posts);
//            }
//        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void setListeners(Object obj) {
        ((ActivityMainBinding)obj).imports.setOnClickListener(btnImportsListener);
        ((ActivityMainBinding)obj).exports.setOnClickListener(btnExportsListener);
        ((ActivityMainBinding)obj).reports.setOnClickListener(btnReportsListener);
    }
    private View.OnClickListener btnReportsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), ReportsActivity.class));
        }
    };
    private View.OnClickListener btnImportsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), ImportActivity.class));
        }
    };

    private View.OnClickListener btnExportsListener  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), ExportsActivity.class));
        }
    };

    @Override
    public void onInit() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }
}
