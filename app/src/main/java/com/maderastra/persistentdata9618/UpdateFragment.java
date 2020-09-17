package com.maderastra.persistentdata9618;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.maderastra.persistentdata9618.DatabaseClient;
import com.maderastra.persistentdata9618.User;

public class UpdateFragment extends Fragment {


    TextInputEditText editText;
    Button saveBtn, deleteBtn, cancelBtn;
    User user;

    public UpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        user = (User) getArguments().getSerializable("user");
        editText = view.findViewById(R.id.input_name);
        saveBtn = view.findViewById(R.id.btn_update);
        deleteBtn = view.findViewById(R.id.btn_delete);
        cancelBtn = view.findViewById(R.id.btn_cancel);

        try {
            if (user.getFullName() != null ){
                editText.setText(user.getFullName());
            }else {
                editText.setText("-");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setFullName(editText.getText().toString());
                update(user);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(user);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(UpdateFragment.this).commit();
            }
        });
    }

    private void update(final User user){
        class UpdateUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase()
                        .userDAO()
                        .update(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(), "User updated", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(UpdateFragment.this).commit();
            }
        }

        UpdateUser update = new UpdateUser();
        update.execute();
    }

    private void delete(final User user) {
        class DeleteUser extends AsyncTask<Void, Void, Void>{

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(), "User deleted", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(UpdateFragment.this).commit();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase()
                        .userDAO()
                        .delete(user);
                return null;
            }
        }

        DeleteUser delete = new DeleteUser();
        delete.execute();
    }

}