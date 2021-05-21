package com.mact.simpleautomationapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mact.simpleautomationapp.Activities.AutomateAndroid;
import com.mact.simpleautomationapp.Activities.MainActivity;
import com.mact.simpleautomationapp.R;
import com.mact.simpleautomationapp.Room.ViewModel.AutomatedTaskViewModel;
import com.mact.simpleautomationapp.Services.BroadcastManager;

public class ReviewAutomationFragment extends Fragment implements View.OnClickListener {
    private Button createAutoButton;
    private TextView titleTxtView;
    private AutomateAndroid automateAndroid;
    private AutomatedTaskViewModel viewModel;
    public ReviewAutomationFragment(){
        super(R.layout.fragment_review_automation);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        automateAndroid = (AutomateAndroid) getActivity();
        viewModel = new ViewModelProvider(getActivity(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(automateAndroid.getApplication())).get(AutomatedTaskViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AutomateAndroid) getActivity()).setToolBarTitle("Review");
        View view = inflater.inflate(R.layout.fragment_review_automation, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createAutoButton = view.findViewById(R.id.create_auto_btn);
        titleTxtView = view.findViewById(R.id.auto_title);
        createAutoButton.setOnClickListener(this::onClick);
        setTriggerDescription(view);
        setActionDescription(view);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.create_auto_btn) {
            String title = titleTxtView.getText().toString();
            if (title.isEmpty()) {
                Toast.makeText(getActivity(), R.string.no_title, Toast.LENGTH_LONG).show();
            } else {
                ((AutomateAndroid) getActivity()).getAuto().setTitle(title);
                viewModel.insert(automateAndroid.getAuto());
                getActivity().finish();
            }
        }
    }

    private void setTriggerDescription(View v){
        TextView triggerDesc = v.findViewById(R.id.trigger_description);
        triggerDesc.setText(((AutomateAndroid) getActivity()).getAuto().getTrigger().getTriggerDescription());
    }

    private void setActionDescription(View v){
        TextView actionDesc = v.findViewById(R.id.action_description);
        actionDesc.setText(((AutomateAndroid) getActivity()).getAuto().getAction().getActionDescription());
    }
}
