package com.mact.simpleautomationapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.mact.simpleautomationapp.Activities.MainActivity;
import com.mact.simpleautomationapp.Adapters.AutosRecyclerViewAdapter;
import com.mact.simpleautomationapp.Room.Entity.Action;
import com.mact.simpleautomationapp.Room.Entity.AndroidAuto;
import com.mact.simpleautomationapp.Room.Entity.Trigger;
import com.mact.simpleautomationapp.R;
import com.mact.simpleautomationapp.Room.ViewModel.AutomatedTaskViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.mact.simpleautomationapp.Services.BroadcastReceiverService.TAG;

public class AndroidAutoFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private AutosRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AutomatedTaskViewModel viewModel;
    private AndroidAuto deletedAuto;
    public AndroidAutoFragment() {
        super(R.layout.android_auto_item);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(getActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AutomatedTaskViewModel.class);

        setLiveDataObserver();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new AutosRecyclerViewAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_android_auto, container, false);
        mRecyclerView = view.findViewById(R.id.android_auto_recycler_view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        androidx.appcompat.widget.SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void setLiveDataObserver() {
        viewModel.getAllAndroidTasks().observe(this, new Observer<List<AndroidAuto>>() {
            @Override
            public void onChanged(List<AndroidAuto> androidAutos) {
                mAdapter.submitList(androidAutos);
                mAdapter.setAllAndroidAutos(androidAutos);
                ((MainActivity) getActivity()).startBroadcastReceiverService((ArrayList) androidAutos);
            }
        });
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if(direction == ItemTouchHelper.RIGHT){
                int position = viewHolder.getAdapterPosition();
                deletedAuto = mAdapter.getCurrentList().get(position);
                mAdapter.deleteItemAt(position);
                mAdapter.submitList(mAdapter.getAllAndroidAutos());
                mAdapter.notifyDataSetChanged();
                Snackbar.make(mRecyclerView, "Deleting this task", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mAdapter.addItem(deletedAuto);
                                mAdapter.submitList(mAdapter.getAllAndroidAutos());
                                mAdapter.notifyDataSetChanged();
                            }
                        })
                        .addCallback(new Snackbar.Callback(){
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                if(event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT){
                                    viewModel.delete(deletedAuto);
                                }
                            }
                        })
                        .show();
            }
        }

    };

}