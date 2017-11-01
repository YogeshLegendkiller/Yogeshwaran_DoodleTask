package com.yogeshwaran_doodletask.yogeshwaran_doodletask.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yogeshwaran_doodletask.yogeshwaran_doodletask.Model.QuizModel;
import com.yogeshwaran_doodletask.yogeshwaran_doodletask.R;

import java.util.List;

/**
 * Created by 4264 on 31/10/2017.
 */

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.FiterViewHolder> implements View.OnClickListener{
private QuizAdapter.OnItemClickListener onItemClickListener;

private List<QuizModel> listobj;
  //  private Context context;


public void setOnItemClickListener(final QuizAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    //this.context=context;
        }

public IndexAdapter(List<QuizModel>listobj, Context context){
        this.listobj=listobj;
        }
@Override
public void onClick(View v) {
final RecyclerView recyclerView = (RecyclerView) v.getParent();
        int position = recyclerView.getChildAdapterPosition(v);
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
    public IndexAdapter.FiterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.indexadapter, parent, false);
        view.setOnClickListener(this);
        return new  IndexAdapter.FiterViewHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(IndexAdapter.FiterViewHolder holder, int position) {
        if(listobj.get(position).getPosition()>0) {
            holder.White.setVisibility(View.GONE);
            holder.Text.setVisibility(View.VISIBLE);
            holder.Text.setText(String.valueOf(listobj.get(position).getCount()));
      }
        else {
            holder.Text.setVisibility(View.GONE);
            holder.White.setVisibility(View.VISIBLE);
            holder.White.setText(String.valueOf(listobj.get(position).getCount()));
    }

    }
    @Override
    public int getItemCount() {
        return listobj.size();
    }

static class FiterViewHolder extends RecyclerView.ViewHolder {
    private TextView  Text,White;
    private FiterViewHolder(View itemView) {
        super(itemView);
        this.Text= itemView.findViewById(R.id.TextViewID);
        this.White=itemView.findViewById(R.id.white);
    }
}
}


