package com.example.lifetrack.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lifetrack.R;
import com.example.lifetrack.adapter.TaskAdapter;
import com.example.lifetrack.databinding.FragmentHomeBinding;
import com.example.lifetrack.model.TaskModel;
import com.example.lifetrack.utils.App;
import com.example.lifetrack.utils.Constants;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements TaskAdapter.Listener {
    FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycler();
        binding.addFab.setOnClickListener(view1 -> {
            CreateTaskFragment createTaskFragment = new CreateTaskFragment();
            createTaskFragment.show(requireActivity().getSupportFragmentManager(), "ololo");
        });
    }

    private void initRecycler() {
        App.initDatabase(getContext()).taskDao().getAll().observe(getViewLifecycleOwner(), taskModels -> {
            TaskAdapter taskAdapter = new TaskAdapter((ArrayList<TaskModel>) taskModels, this);
            binding.taskRecycler.setAdapter(taskAdapter);
        });
    }

    @Override
    public void itemLongClick(TaskModel model) {
        new AlertDialog.Builder(requireContext()).setTitle("Предупреждение!").setMessage("Вы действительно хотите удалить эту запись?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        App.getInstance().getDatabase().taskDao().delete(model);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();

    }

    @Override
    public void itemClick(TaskModel model) {
        CreateTaskFragment createTaskFragment = new CreateTaskFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.UPDATE_MODEL, model);
        createTaskFragment.setArguments(bundle);
        createTaskFragment.show(requireActivity().getSupportFragmentManager(), Constants.UPDATE);
    }

}