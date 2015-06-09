package com.aria.jhcpokemon.minamivote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.aria.jhcpokemon.minamivote.R;
import com.aria.jhcpokemon.minamivote.adapter.MyAdapter;
import com.aria.jhcpokemon.minamivote.model.Character;
import com.aria.jhcpokemon.minamivote.util.Util;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView characterList = (ListView) findViewById(R.id.characters_list);
        final List<Character> characters = Util.getCharacterList(this);
        MyAdapter adapter = new MyAdapter(getApplicationContext(), R.layout.list_item, characters);
        characterList.setAdapter(adapter);
        characterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Character character = characters.get(i);
                Intent intent = new Intent(MainActivity.this,DespActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("main_activity",character);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Toast.makeText(this,"Version:1.0",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
