package com.example.p0t4t0.simplefilemanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String CURRENT_DIR = "current_dir";

    private ListView fileListView;
    private FileAdapter fileAdapter;

    private String currentDir = Environment.getRootDirectory().getAbsolutePath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String dir = bundle.getString(CURRENT_DIR);
            if (dir != null && !dir.isEmpty())
                currentDir = dir;
        }

        fileAdapter = new FileAdapter(currentDir);
        fileListView = (ListView) findViewById(R.id.file_list);
        fileListView.setAdapter(fileAdapter);
        fileListView.setOnItemClickListener(new FileItemClickListener());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class FileItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String nextFolder = ((TextView) view.findViewById(R.id.file_path))
                    .getText().toString();

            boolean isDir = new File(nextFolder).isDirectory();

            if (isDir) {
                Intent initialIntent = new Intent(MainActivity.this, MainActivity.class);
                initialIntent.putExtra(CURRENT_DIR, nextFolder);

                startActivity(initialIntent);
            }
        }
    }
}
