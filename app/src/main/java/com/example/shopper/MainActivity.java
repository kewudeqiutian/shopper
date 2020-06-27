package com.example.shopper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Shopper instance = Shopper.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("*****************",instance.getShopName());

        TextView textView = findViewById(R.id.main_textview);
        ListView listView = findViewById(R.id.listview1);
        Button button = findViewById(R.id.main_button);

        List<String> listStr = new LinkedList<>();
        int count = 0;
        List<List<String>> lists = getDishes();
        for(int i = 0;i < lists.size();i++){
            List<String> list = lists.get(i);
            Map<String,String> map = splitStr(list.get(2));
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            for (Map.Entry entry:entrySet){
                String s = list.get(0) + "在" + list.get(1) +  "点了" + entry.getValue() +"份" + entry.getKey();
                listStr.add(s);
            }

//            count = count + Integer.parseInt(list.get(2));
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
                List<List<String>> list = new LinkedList<>();


                String sql = "select username,time,ds,price from dishes where shopname = '"
                        + "鸡公煲" + "'";
                ResultSet resultSet = JDBCUtils.query(sql);
                //int c = 0;
                while(resultSet.next()){
                    List<String> aList = new ArrayList<>();
                    String username = resultSet.getString("username");
                    String time = resultSet.getString("time");
                    String dishes = resultSet.getString("ds");
                    String price = resultSet.getString("price");
                    aList.add(username);
                    aList.add(time);
                    aList.add(dishes);
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
        return new ArrayList<>();
    }

    public static Map<String,String> splitStr(String str){
        String regex1 = "[0-9]";
        String regex2 = "[^0-9]";
        String[] charStr = str.split(regex1);
        Pattern p = Pattern.compile(regex2);
        Matcher m = p.matcher(str);
        char[] chars = m.replaceAll("").trim().toCharArray();
        Map<String,String> map = new HashMap<>();
        for (int i = 0;i < charStr.length;i++){
            Log.i("*****************",map.toString());
            map.put(charStr[i], String.valueOf(chars[i]));
        }
        return map;
    }
}