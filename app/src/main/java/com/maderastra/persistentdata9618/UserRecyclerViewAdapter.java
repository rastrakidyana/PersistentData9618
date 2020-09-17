package com.maderastra.persistentdata9618;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder> {

    private Context context;
    private List<User> userList;

    public UserRecyclerViewAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.textView.setText(user.getFullName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.full_name_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            User user = userList.get(getAdapterPosition());
            Bundle data = new Bundle();
            data.putSerializable("user", user);
            UpdateFragment updateFragment = new UpdateFragment();
            updateFragment.setArguments(data);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, updateFragment)
                    .commit();
        }
    }
}
