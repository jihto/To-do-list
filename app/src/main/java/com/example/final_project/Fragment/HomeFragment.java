package com.example.final_project.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.final_project.Adapter.ListsAdapter;
import com.example.final_project.DatabaseHelper;
import com.example.final_project.Domain.ListsDomain;
import com.example.final_project.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView.Adapter adapterNoteList;
    private RecyclerView recyclerViewNotes;
    private TextView userNametxt;
    private DatabaseHelper databaseHelper;
    private List<String> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        userNametxt = rootView.findViewById(R.id.userNametxt);
        recyclerViewNotes = rootView.findViewById(R.id.view1);

        SharedPreferences preferences = requireActivity().getSharedPreferences("user_data", requireActivity().MODE_PRIVATE);
        String username = preferences.getString("username", "");

        // Update TextViews with the retrieved data
        userNametxt.setText("Username: " + username);
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        ArrayList<ListsDomain> items = new ArrayList<>();
        items.add(new ListsDomain("One", "", true));
        items.add(new ListsDomain("Two", "", true));
        items.add(new ListsDomain("Three", "", true));

        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        adapterNoteList = new ListsAdapter(items);
        recyclerViewNotes.setAdapter(adapterNoteList);
    }

    private void getTasks() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks", null);
        if (cursor.moveToFirst()) {
            int titleIndex = cursor.getColumnIndex("title");
            int descriptionIndex = cursor.getColumnIndex("description");

            do {
                List<String> data = new ArrayList<>();
                if (titleIndex != -1) {
                    String title = cursor.getString(titleIndex);
                    data.add(title);
                }
                if (descriptionIndex != -1) {
                    String description = cursor.getString(descriptionIndex);
                    data.add(description);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }
}