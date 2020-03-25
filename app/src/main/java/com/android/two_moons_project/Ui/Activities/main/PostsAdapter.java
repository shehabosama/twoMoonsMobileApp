package com.android.two_moons_project.Ui.Activities.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.two_moons_project.R;
import com.android.two_moons_project.common.model.Posts;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {
    private interAction interAction;
    private Context context;
    private List<Posts> list = new ArrayList<>();

    public PostsAdapter(Context context, interAction interAction) {
        this.context = context;

        this.interAction = interAction;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_post_item, parent, false);

        return new PostsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        Posts posts= list.get(position);

        holder.text_title.setText(posts.getTitle());
        holder.text_body.setText(posts.getBody());
    }
    public void setData(List<Posts> posts){
        this.list = posts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public interface interAction {

    }

    public class PostsViewHolder extends RecyclerView.ViewHolder {


        private TextView text_title,text_body;
        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);

            text_body = itemView.findViewById(R.id.body);
            text_title = itemView.findViewById(R.id.title);
        }

        public void setListener() {

        }
    }
}
