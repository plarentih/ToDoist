package com.enterprise.plarent.todoist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.enterprise.plarent.todoist.adapters.CodeColorAdapter;
import com.enterprise.plarent.todoist.model.Project;
import com.enterprise.plarent.todoist.fragments.ProjectFragment;
import com.enterprise.plarent.todoist.R;

public class AddProjectActivity extends AppCompatActivity {

    private EditText txtProjectName;
    private ImageView colorView;
    private TextView colorTextName;
    private Project.Color color;
    private AlertDialog colorDialogObject;
    private CodeColorAdapter codeColorAdapter;
    private ListView listView;

    public String[] colors = new String[] {"Red", "Green", "Blue", "Orange", "Silver"};
    public int[] colorCodess = new int[]{R.drawable.red, R.drawable.green, R.drawable.blue,
            R.drawable.orange, R.drawable.silver};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        colorView = (ImageView)findViewById(R.id.colorChoice);
        color = Project.Color.RED;
        this.colorTextName = (TextView)findViewById(R.id.text_color);
        this.txtProjectName = (EditText)findViewById(R.id.txt_company_name);

        colorTextName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildColorCodeDialog();
            }
        });
        codeColorAdapter = new CodeColorAdapter(this, colors, colorCodess);
    }

    private void buildColorCodeDialog(){
        AlertDialog.Builder colorDialogBuilder = new AlertDialog.Builder(AddProjectActivity.this);
        LayoutInflater inflater = AddProjectActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.color_code_dialog_layout, null);
        colorDialogBuilder.setView(dialogView);
        listView = (ListView)dialogView.findViewById(R.id.dialog_list);
        listView.setAdapter(codeColorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        color = Project.Color.RED;
                        colorView.setImageResource(R.drawable.red);
                        colorTextName.setText("Red");
                        colorDialogObject.dismiss();
                        break;
                    case 1:
                        color = Project.Color.GREEN;
                        colorView.setImageResource(R.drawable.green);
                        colorTextName.setText("Green");
                        colorDialogObject.dismiss();
                        break;
                    case 2:
                        color = Project.Color.BLUE;
                        colorView.setImageResource(R.drawable.blue);
                        colorTextName.setText("Blue");
                        colorDialogObject.dismiss();
                        break;
                    case 3:
                        color = Project.Color.ORANGE;
                        colorView.setImageResource(R.drawable.orange);
                        colorTextName.setText("Orange");
                        colorDialogObject.dismiss();
                        break;
                    case 4:
                        color = Project.Color.SILVER;
                        colorView.setImageResource(R.drawable.silver);
                        colorTextName.setText("Silver");
                        colorDialogObject.dismiss();
                        break;
                }
            }
        });
        colorDialogObject = colorDialogBuilder.create();
        colorDialogObject = colorDialogBuilder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_add_project){
            Editable titlePro = txtProjectName.getText();
            if(!TextUtils.isEmpty(titlePro)){
                Project createdProject = new Project();
                createdProject.projectName = titlePro.toString();
                createdProject.color = color;
                createdProject.save();
                Log.d("PLARENT", "CREATED PROJECT " + color.name());
                Intent intent = new Intent();
                intent.putExtra(ProjectFragment.EXTRA_ADDED_PROJECT, createdProject);
                setResult(RESULT_OK, intent);
                finish();
            }
            else {
                Toast.makeText(this, "The name project can't be empty.", Toast.LENGTH_LONG).show();
            }
        }
        else if(id == R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

