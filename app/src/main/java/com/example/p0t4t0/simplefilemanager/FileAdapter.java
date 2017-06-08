package com.example.p0t4t0.simplefilemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class FileAdapter extends BaseAdapter {

    private ArrayList<File> files;

    public FileAdapter(String dir) {
        File currentDir = new File(dir);
        files = new ArrayList<>(Arrays.asList(currentDir.listFiles()));
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                boolean isFirstDir = o1.isDirectory();
                boolean isSecondDir = o2.isDirectory();

                if (isFirstDir && isSecondDir)
                    return o1.getName().compareToIgnoreCase(o2.getName());
                else
                    if (isFirstDir)
                        return -1;
                    else
                        return 11;
            }
        });
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public File getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;

        if (itemView == null) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);

            itemView.setTag(new ViewHolder(itemView));
        }

        bindView(itemView, position);
        return itemView;
    }

    private void bindView(View itemView, int position) {
        ViewHolder holder = (ViewHolder) itemView.getTag();
        File file = files.get(position);

        if (file.isFile())
            holder.typeLogo.setImageDrawable(itemView.getResources()
                    .getDrawable(R.drawable.ic_type_file));
        else
            holder.typeLogo.setImageDrawable(itemView.getResources()
                    .getDrawable(R.drawable.ic_type_folder));

        holder.fileName.setText(file.getName());
        holder.filePath.setText(file.getAbsolutePath());
    }

    private class ViewHolder {
        ImageView typeLogo;
        TextView fileName;
        TextView filePath;

        ViewHolder(View view) {
            typeLogo = (ImageView) view.findViewById(R.id.type_logo);
            fileName = (TextView) view.findViewById(R.id.file_name);
            filePath = (TextView) view.findViewById(R.id.file_path);
        }
    }
}
