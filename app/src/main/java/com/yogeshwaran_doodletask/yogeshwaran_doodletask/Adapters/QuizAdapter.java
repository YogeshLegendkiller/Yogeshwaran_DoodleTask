package com.yogeshwaran_doodletask.yogeshwaran_doodletask.Adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yogeshwaran_doodletask.yogeshwaran_doodletask.Model.QuizModel;
import com.yogeshwaran_doodletask.yogeshwaran_doodletask.R;

import java.util.List;

/**
 * Created by 4264 on 31/10/2017.
 */

public class QuizAdapter  extends RecyclerView.Adapter<QuizAdapter.FiterViewHolder> implements View.OnClickListener{
    private QuizAdapter.OnItemClickListener onItemClickListener;

    private List<QuizModel> listobj;


    public void setOnItemClickListener(final QuizAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public QuizAdapter(List<QuizModel>listobj){
        this.listobj=listobj;
    }
    @Override
    public void onClick(View v) {
        final RecyclerView recyclerView = (RecyclerView) v.getParent();
        int position = recyclerView.getChildAdapterPosition(v);
//        Log.d("post",String.valueOf(position));
        if (position != RecyclerView.NO_POSITION && onItemClickListener!=null ) {
            this.onItemClickListener.onItemClicked(position);
        }
        else {
            Log.d("post",String.valueOf(position));
        }


    }
    public interface OnItemClickListener {
        void onItemClicked(int  position);
    }
    @Override
    public FiterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quizadapter, parent, false);
        view.setOnClickListener(this);
        return new  QuizAdapter.FiterViewHolder(view);
    }
    @Override
    public void onBindViewHolder(FiterViewHolder holder, int position) {
        holder.Text.setText(listobj.get(position).getAnswers());
        if(listobj.get(position).getPosition()==position){
            holder.rl.setBackgroundColor(Color.parseColor("#e3e3e3"));
        }
        else {
            holder.rl.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }
    @Override
    public int getItemCount() {
        return listobj.size();
    }

    static class FiterViewHolder extends RecyclerView.ViewHolder {
        private TextView Text;
        private RelativeLayout rl;
        private FiterViewHolder(View itemView) {
            super(itemView);
            this.rl=itemView.findViewById(R.id.rel);
            this.Text= itemView.findViewById(R.id.text);
        }
    }
}


