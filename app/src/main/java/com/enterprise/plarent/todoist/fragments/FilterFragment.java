package com.enterprise.plarent.todoist.fragments;

/**
 * Created by Plarent on 8/22/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.enterprise.plarent.todoist.R;
import com.enterprise.plarent.todoist.activities.ListPrioritiesActivity;
import com.enterprise.plarent.todoist.adapters.FilterAdapter;
import com.enterprise.plarent.todoist.dao.TaskDAO;

public class FilterFragment extends Fragment {

    private ListView priorityListView;
    private FilterAdapter filterAdapter;

    private static final String[] priorities = {"Priority 1", "Priority 2", "Priority 3", "Priority 4"};
    private static final int figure = R.drawable.oil;

    public FilterFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_filter, container, false);
        filterAdapter = new FilterAdapter(getActivity(), priorities, figure);
        this.priorityListView = (ListView)view.findViewById(R.id.filteredList);
        priorityListView.setAdapter(filterAdapter);
        priorityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedPriority = priorities[position];
                Intent intent = new Intent(getContext(), ListPrioritiesActivity.class);
                intent.putExtra("PRIORITY", selectedPriority);
                startActivity(intent);
            }
        });
        return view;
    }
}
