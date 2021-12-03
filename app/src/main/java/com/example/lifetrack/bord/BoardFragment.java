package com.example.lifetrack.bord;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.lifetrack.R;
import com.example.lifetrack.databinding.FragmentBoardBinding;
import com.example.lifetrack.utils.Constants;



public class BoardFragment extends Fragment {
    FragmentBoardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPositionFromAdepter();

    }

    private void getPositionFromAdepter() {
        if (getArguments() != null){
            int position = getArguments().getInt(Constants.FRAGMENT_POSITION);
            switch (position){
                case 0:
                    binding.description.setText("Не теряйте время зря, вспоминая о том,что хотели сделать");
                    binding.boardImage.setAnimation("time.json");
                    break;
                case 1:
                    binding.description.setText("Это приложение поможет вам!Записывайте ваши дела,планы,идеи чтобы их не забыть");
                    binding.boardImage.setAnimation("task.json");
                    break;
                case 2:
                    binding.description.setText("Надеюсь мы вам поможем!Желаем удачи");
                    binding.boardImage.setAnimation("ideas.json");
                    break;
            }
        }
    }
}