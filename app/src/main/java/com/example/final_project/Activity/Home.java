package com.example.final_project.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.final_project.Fragment.CreateListFragment;
import com.example.final_project.Fragment.CurrentDayFragment;
import com.example.final_project.Fragment.HomeFragment;
import com.example.final_project.NotificationReceiver;
import com.example.final_project.NotificationService;
import com.example.final_project.R;
import com.example.final_project.databinding.ActivityHomeBinding;

public class Home extends AppCompatActivity {
    private ActivityHomeBinding binding;
    NotificationReceiver a = new NotificationReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.calender:
                    replaceFragment(new CurrentDayFragment());
                    break;
                case R.id.create:
                    replaceFragment(new CreateListFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

//        NotificationScheduler.scheduleNotification(this);
    }
}