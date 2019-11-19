package com.mycompany.testtask.adapter;

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

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        if (user != null) {
            holder.imageView.setAnimation(AnimationUtils.loadAnimation
                    (context, R.anim.fade_transition_anim));
            holder.linearLayout.setAnimation(AnimationUtils.loadAnimation
                    (context, R.anim.fade_scale_animation));
            holder.txtName.setText(user.getName());
            holder.txtEmail.setText(user.getEmail());
            holder.txtInfo.setText(user.getCompany().getCatchPhrase());

            switch (user.getId()) {
                case 1:
                    Glide.with(context).load("https://avatars.io/twitter/1").placeholder(R.drawable.default_128)
                            .into(holder.imageView);
                    break;
                case 2:
                    Glide.with(context).load("https://avatars.io/twitter/2").placeholder(R.drawable.default_128)
                            .into(holder.imageView);
                    break;
                case 3:
                    Glide.with(context).load("https://avatars.io/twitter/3").placeholder(R.drawable.default_128)
                            .into(holder.imageView);
                    break;
                case 4:
                    Glide.with(context).load("https://avatars.io/twitter/4").placeholder(R.drawable.default_128)
                            .into(holder.imageView);
                    break;
                case 5:
                    Glide.with(context).load("https://avatars.io/twitter/5").placeholder(R.drawable.default_128)
                            .into(holder.imageView);
                    break;
                case 6:
                    Glide.with(context).load("https://avatars.io/twitter/6").placeholder(R.drawable.default_128)
                            .into(holder.imageView);
                    break;
                case 7:
                    Glide.with(context).load("https://avatars.io/twitter/7").placeholder(R.drawable.default_128)
                            .into(holder.imageView);
                    break;
                case 8:
                    Glide.with(context).load("https://avatars.io/twitter/8").placeholder(R.drawable.default_128)
                            .into(holder.imageView);
                    break;
                case 9:
                    Glide.with(context).load("https://avatars.io/twitter/9").placeholder(R.drawable.default_128)
                            .into(holder.imageView);
                    break;
                case 10:
                    Glide.with(context).load("https://avatars.io/twitter/10").placeholder(R.drawable.default_128)
                            .into(holder.imageView);
                    break;
                default:
                    Glide.with(context).load(R.drawable.default_128)
                            .into(holder.imageView);
                    break;
            }

        }

    }

    @Override
    public int getItemCount() {
        int a;
        if (userList != null && !userList.isEmpty()) {
            a = userList.size();
        } else {
            a = 0;
        }
        return a;
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


