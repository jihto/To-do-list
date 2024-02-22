package com.example.final_project.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.final_project.Adapter.ListsAdapter;
import com.example.final_project.Domain.ListsDomain;
import com.example.final_project.R;

import java.util.ArrayList;


public class CurrentDayFragment extends Fragment {
    private RecyclerView.Adapter adapterNoteList;
    private RecyclerView recyclerViewNotes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_current_day, container, false);

        recyclerViewNotes = rootView.findViewById(R.id.listWorks);

        initRecyclerView();

        return rootView;
    }
    private void initRecyclerView() {
        ArrayList<ListsDomain> items = new ArrayList<>();
        items.add(new ListsDomain("One", "", true));
        items.add(new ListsDomain("Two", "", true));
        items.add(new ListsDomain("Three", "", true));
        items.add(new ListsDomain("Four", "", true));

        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        adapterNoteList = new ListsAdapter(items);
        recyclerViewNotes.setAdapter(adapterNoteList);
    }
}