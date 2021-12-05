package com.example.lifetrack.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.example.lifetrack.R;
import com.example.lifetrack.adapter.TaskAdapter;
import com.example.lifetrack.databinding.FragmentCreateTaskBinding;
import com.example.lifetrack.model.TaskModel;
import com.example.lifetrack.utils.App;
import com.example.lifetrack.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;


public class CreateTaskFragment extends BottomSheetDialogFragment implements DatePickerDialog.OnDateSetListener {
    FragmentCreateTaskBinding binding;
    String userTask;
    String deadline;
    String repeatCount;
    private int startYear;
    private int startMonth;
    private int startDay;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        fillDialog();
    }

    private void fillDialog() {
        if (getTag().equals(Constants.UPDATE)) {
            TaskModel taskModel = (TaskModel) getArguments().getSerializable(Constants.UPDATE_MODEL);

            deadline = taskModel.getDeadline();
            userTask = taskModel.getTask();
            repeatCount = taskModel.getRepeatCount();

            binding.taskEd.setText(userTask);
            binding.dateTv.setText(deadline);
            binding.repeatTv.setText(repeatCount);
        }
    }

    private void initClickers() {
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getTag().equals(Constants.UPDATE)){
                    updateTask();
                }else {
                    insertTask();
                }
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

    private void updateTask() {
        TaskModel taskModel = (TaskModel) getArguments().getSerializable(Constants.UPDATE_MODEL);
        deadline = binding.dateTv.getText().toString();
        userTask = binding.taskEd.getText().toString();
        repeatCount = binding.repeatTv.getText().toString();
        TaskModel updateModel = new TaskModel(userTask, deadline, repeatCount);
        updateModel.setId(taskModel.getId());
        App.getInstance().getDatabase().taskDao().update(updateModel);
        dismiss();
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

//    private boolean checkDay() {
//        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("dayPreferences", Context.MODE_PRIVATE);
//        Calendar calendar = Calendar.getInstance();
//        String currentDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
//        String dayFromPreference = sharedPreferences.getString(Constants.CURRENT_DAY, "");
//        if (currentDay.equals(dayFromPreference)) {
//            sharedPreferences.edit().clear().apply();
//            sharedPreferences.edit().putString(Constants.CURRENT_DAY, currentDay).apply();
//            return true;
//        }
//        return false;
//    }

    private void insertTask() {
        userTask = binding.taskEd.getText().toString();
        TaskModel taskModel = new TaskModel(userTask, deadline, repeatCount);
        passToFireStore(taskModel);
        App.getInstance().getDatabase().taskDao().insert(taskModel);
        dismiss();
    }

    private void passToFireStore(TaskModel taskModel) {
        db.collection("models")
                .add(taskModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("lololo", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                });

        db.collection("models")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Log.d("ololo", document.getId() + " => " + document.getData());
                                Map<String,Object> map = document.getData();
                                String userTask = (String) map.get("userTask");
                                String deadline = (String) map.get("deadline");
                                String repeatCount = (String) map.get("repeatCount");
                            }
                        } else{
                            Log.w("ololo", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        deadline = day + "." + month + "." + year;
        binding.dateTv.setText(deadline);
    }
}