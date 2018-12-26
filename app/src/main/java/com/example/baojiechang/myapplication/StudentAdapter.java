package com.example.baojiechang.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class StudentAdapter extends BaseAdapter {
    private Context mcontext;
    private List<StudentInfo> mlist;
    Holder holder = new Holder();
    public StudentAdapter(Context mcontext, List<StudentInfo> mlist) {
        this.mcontext = mcontext;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mcontext, R.layout.item_student, null);

        StudentInfo studentInfo = (StudentInfo) getItem(position);
        holder.studentName=convertView.findViewById(R.id.studentName);
        holder.studentName.setText(studentInfo.name);
        holder.studentStatus=convertView.findViewById(R.id.studentStatus);
        holder.studentStatus.setText(studentInfo.map.get(studentInfo.status));
        convertView.setTag(holder);
        return convertView;
    }
/*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String select_content1 = holder.spinner.getSelectedItem().toString();
        String select_content2 = holder.spinner.getItemAtPosition(position).toString();
        Log.e("1",select_content1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/


    private class Holder {
        TextView studentName;
        TextView studentStatus;
    }
}
