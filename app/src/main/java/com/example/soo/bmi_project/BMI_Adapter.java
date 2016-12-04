package com.example.woooo.bmi_project;

        import android.content.Context;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import java.util.ArrayList;


public class BMI_Adapter extends RecyclerView.Adapter<BMI_Adapter.ViewHolder> {
    ArrayList<BMI_Data> item;
    Context context;

    public BMI_Adapter(Context context, ArrayList<BMI_Data> item){
        this.item = item;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_height.setText(item.get(position).str_height);
        holder.tv_weight.setText(item.get(position).str_weight);
        holder.tv_date.setText(item.get(position).date);
        holder.tv_day.setText(item.get(position).day);
    }

    @Override
    public int getItemCount() {
        try{
            return item.size();
        }catch (NullPointerException e){
            e.printStackTrace();
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_height;
        private TextView tv_weight;
        private TextView tv_date;
        private TextView tv_day;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_height = (TextView)itemView.findViewById(R.id.tv_height);
            tv_weight = (TextView)itemView.findViewById(R.id.tv_weight);
            tv_date = (TextView)itemView.findViewById(R.id.tv_date);
            tv_day = (TextView)itemView.findViewById(R.id.tv_day);
        }
    }
}