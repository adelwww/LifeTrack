package com.example.lifetrack.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.RadioButton;

import com.example.lifetrack.R;
import com.example.lifetrack.databinding.FragmentCreateTaskBinding;
import com.example.lifetrack.model.TaskModel;
import com.example.lifetrack.utils.App;
import com.example.lifetrack.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;


public class CreateTaskFragment extends BottomSheetDialogFragment implements DatePickerDialog.OnDateSetListener {
    FragmentCreateTaskBinding binding;
    String userTask;
    String deadline;
    String repeatCount;
    private int startYear;
    private int startMonth;
    private int startDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateTaskBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initClickers();
    }

    private void initClickers() {
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertTask();
            }
        });
        binding.dateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        binding.repeatTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRepeatDialog();
            }
        });
    }

    private void showRepeatDialog() {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_repeat, null);
        Dialog alertDialog = new Dialog(requireContext());
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(view);
        alertDialog.show();
        RadioButton neverBtn = alertDialog.findViewById(R.id.never);
        neverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatCount = neverBtn.getText().toString();
                binding.repeatTv.setText(neverBtn.getText().toString());
                alertDialog.dismiss();

            }
        });
        RadioButton everyDay = alertDialog.findViewById(R.id.every_day);
        everyDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatCount = everyDay.getText().toString();
                binding.repeatTv.setText(everyDay.getText().toString());
                alertDialog.dismiss();
            }
        });
        RadioButton everyWeek = alertDialog.findViewById(R.id.every_week);
        everyWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatCount = everyWeek.getText().toString();
                binding.repeatTv.setText(everyWeek.getText().toString());
                alertDialog.dismiss();
            }
        });
        RadioButton everyMonth = alertDialog.findViewById(R.id.every_month);
        everyMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatCount = everyMonth.getText().toString();
                binding.repeatTv.setText(everyMonth.getText().toString());
                alertDialog.dismiss();
            }
        });
        RadioButton everyYear = alertDialog.findViewById(R.id.every_year);
        everyYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatCount = everyYear.getText().toString();
                binding.repeatTv.setText(everyYear.getText().toString());
                alertDialog.dismiss();
            }
        });
    }

    private void showDialog() {
        Calendar calendar = Calendar.getInstance();
        startYear = calendar.get(Calendar.YEAR);
        startMonth = calendar.get(Calendar.MONTH);
        startDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireActivity(), this, startYear, startMonth, startDay);
        datePickerDialog.show();

    }

    private void insertTask() {
        userTask = binding.taskEd.getText().toString();
        TaskModel taskModel = new TaskModel(userTask, deadline, repeatCount);
        App.initDatabase(getContext()).taskDao().insert(taskModel);
        dismiss();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        deadline = day + "." + month + "." + year;
        binding.dateTv.setText(deadline);
    }
}