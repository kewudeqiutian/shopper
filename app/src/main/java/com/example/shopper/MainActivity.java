package com.example.shopper;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.main_textview);
        ListView listView = findViewById(R.id.listview1);
        Button button = findViewById(R.id.main_button);

        List<String> listStr = new LinkedList<>();
        int count = 0;
        List<List<String>> lists = getDishes();
        for(int i = 0;i < lists.size();i++){
            List<String> list = lists.get(i);
            String s = "菜名:  " + list.get(0) + "  数量： " + list.get(1);
            listStr.add(s);
            count = count + Integer.parseInt(list.get(2));
        }

        String[] name = listStr.toArray(new String[]{});

        textView.setText("共计消费" + count + "元");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,name);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Toast.makeText(MainActivity.this,"已成功接单，骑手正在火速赶来",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<List<String>> getDishes(){

        FutureTask<List<List<String>>> futureTask = new FutureTask<>(new Callable<List<List<String>>>() {
            @Override
            public List<List<String>> call() throws Exception {
                List<List<String>> list = new ArrayList<>();

                String sql = "select dishname,number,price from shopper";
                ResultSet resultSet = JDBCUtils.query(sql);
                //int c = 0;
                while(resultSet.next()){
                    List<String> aList = new ArrayList<>();
                    String dishname = resultSet.getString("dishname");
                    String number = resultSet.getString("number");
                    String price = resultSet.getString("price");
                    aList.add(dishname);
                    aList.add(number);
                    aList.add(price);
                    list.add(aList);
                    //c++;
                }
                JDBCUtils.close();
                return list;
            }
        });
        new Thread(futureTask).start();
        try {
            return futureTask.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}