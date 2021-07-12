package sg.edu.rp.c346.id20024313.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
Button btnInsert, btnGetTask;
TextView tvResult;
ListView lv;
ArrayList<Task> al;
ArrayAdapter<Task> aa;
EditText etTask;
EditText etDate;

boolean asc= true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      btnInsert=findViewById(R.id.btnInsert);
      lv= findViewById(R.id.lv);
      al= new ArrayList<>();
      aa= new ArrayAdapter<Task>(MainActivity.this, android.R.layout.simple_list_item_1, al);
      lv.setAdapter(aa);
      etDate= findViewById(R.id.etDate);
      etTask=findViewById(R.id.etTask);

      btnInsert.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            DBHelper dbh= new DBHelper(MainActivity.this );
            dbh.insertTask(etTask.getText().toString(),etDate.getText().toString());
            al = dbh.getTasks(true);
            dbh.close();
            aa.notifyDataSetChanged();
            aa= new ArrayAdapter<Task>(MainActivity.this, android.R.layout.simple_list_item_1, al);
            lv.setAdapter(aa);

          }
      });
      tvResult = findViewById(R.id.tvResults);
      btnGetTask= findViewById(R.id.btnGetTasks);
      btnGetTask.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v) {
              DBHelper dbh = new DBHelper(MainActivity.this);
              al = dbh.getTasks(asc);
              dbh.close();
              asc= !asc;
              ArrayList<String> tasks = dbh.getTaskContent();

              String txt= "";
              for (int i = 0; i<tasks.size(); i++){
                  Log.d("Databse Content", i +". "+tasks.get(i));
                  txt += i+". "+ tasks.get(i)+ "\n";
              }
              tvResult.setText(txt);

              al.clear();
              al.addAll(dbh.getTasks(asc));
              aa.notifyDataSetChanged();

          }
      });
    }
}