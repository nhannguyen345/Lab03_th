package com.example.lab03_th;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder>{

    private Activity context;
    private List<Student> studentlist;

    private DbAdapter db;
    private OnItemClickListener mListener;

    public StudentAdapter(Activity context, List<Student> studentlist, DbAdapter db){
        this.context = context;
        this.studentlist = studentlist;
        this.db = db;
    }

    public void addItem(Student st){
        for (int i = 0; i < studentlist.size(); i++){
            if(st.getId() == studentlist.get(i).getId()){
                studentlist.set(i, st);
                notifyDataSetChanged();
            }
        }
//        int index = st.getId() - 1;
//        if (index >= 0 && index < studentlist.size()) {
//            studentlist.set(index, st);
//            notifyDataSetChanged();
//        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_stu_recycler, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewHolder holder, int position) {
        Student student = studentlist.get(position);
        holder.tvFullInfor.setText(student.getId()+", "+student.getName()+", "+student.getMajor()+", "+student.getAverage());
        holder.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isLongClick) {
                if (isLongClick == false){
                    Intent intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("id", studentlist.get(position).getId());
                    intent.putExtra("name", studentlist.get(position).getName());
                    intent.putExtra("major", studentlist.get(position).getMajor());
                    intent.putExtra("average", studentlist.get(position).getAverage());
                    context.startActivityForResult(intent, 1);
                }
                else{
                    Student st = new Student();
                    st.setId( studentlist.get(position).getId());
                    st.setName(studentlist.get(position).getName());
                    st.setMajor(studentlist.get(position).getMajor());
                    st.setAverage(studentlist.get(position).getAverage());
                    db.deleteStudent(st);
                    studentlist.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentlist.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private TextView tvFullInfor;
        private ImageView imageView;
        private LinearLayout llParent;

        private OnItemClickListener itemClickListener;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullInfor = itemView.findViewById(R.id.item_student_tv_fullinfo);
            imageView = itemView.findViewById(R.id.item_student_iv);
            llParent = itemView.findViewById(R.id.item_student_ll_parent);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener (OnItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition(), true);
            return true;
        }
    }
}
