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
                    binding.boardImage.setImageResource(R.drawable.image_board1);
                    binding.description.setText("Здравствуйте!" + "Спасибо,что скачали наше приложение!!");
                    break;
                case 1:
                    binding.boardImage.setImageResource(R.drawable.image_board2);
                    binding.description.setText("В этом приложении вы сможете записывать ваши дела,чтобы их не забыть");
                    break;
                case 2:
                    binding.boardImage.setImageResource(R.drawable.image_board3);
                    binding.description.setText("Желаем вам удачи!");
                    break;
            }
        }
    }
}