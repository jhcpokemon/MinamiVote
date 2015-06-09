package com.aria.jhcpokemon.minamivote.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aria.jhcpokemon.minamivote.R;
import com.aria.jhcpokemon.minamivote.model.Character;

public class DespActivity extends AppCompatActivity{
    private ImageView imageView;
    private TextView textView;
    private Button button;
    private Character character;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView = (ImageView) findViewById(R.id.character_pic);
        textView = (TextView) findViewById(R.id.description);
        button = (Button) findViewById(R.id.vote_button);
        character = getIntent().getParcelableExtra("main_activity");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(character.getName());
        }
        imageView.setImageResource(getImgId(this));
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        textView.setText(character.getDescription());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"我投了"+character.getName()+"一票!");
                startActivity(Intent.createChooser(intent,getResources().getText(R.string.intent_title)));
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
        switch (id) {
            case R.id.action_about:
                Toast.makeText(this, "Version:1.0", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public int getImgId(Context context) {
        try {
            return context.getResources().getIdentifier("pic_" + character.getId(), "drawable", context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
