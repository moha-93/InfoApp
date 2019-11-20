package com.mycompany.testtask.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mycompany.testtask.R;
import com.mycompany.testtask.pojo.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    private Context context;
    private OnItemClickListener listener;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_layout, parent, false);
        return new UserViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.imageView.setAnimation(AnimationUtils.loadAnimation
                (context, R.anim.fade_transition_anim));
        holder.linearLayout.setAnimation(AnimationUtils.loadAnimation
                (context, R.anim.fade_scale_animation));
        String userName = user.getName();
        String userEmail = user.getEmail();
        String userCompany = user.getCompany().getCatchPhrase();
        int userId = user.getId();
        holder.txtName.setText(userName);
        holder.txtEmail.setText(userEmail);
        holder.txtInfo.setText(userCompany);
        Glide.with(context).load(String.format("https://avatars.io/twitter/%d", userId)).placeholder(R.drawable.default_128).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return (userList == null) ? 0 : userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageView;
        private TextView txtName, txtEmail, txtInfo;
        private LinearLayout linearLayout;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_user);
            txtName = itemView.findViewById(R.id.txt_user_name);
            txtEmail = itemView.findViewById(R.id.txt_user_email);
            txtInfo = itemView.findViewById(R.id.txt_user_info);
            linearLayout = itemView.findViewById(R.id.anim_container);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}


