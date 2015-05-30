package com.aria.jhcpokemon.minamivote.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aria.jhcpokemon.minamivote.R;
import com.aria.jhcpokemon.minamivote.model.Character;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

/**
 * Created by jhcpokemon on 05/28/15.
 */
public class MyAdapter extends ArrayAdapter<Character> implements Runnable{
    int listItemLayout;
    View view;
    ViewHolder viewHolder;
    Character character;

    public MyAdapter(Context context, int listItemLayout, List<Character> characters) {
        super(context, listItemLayout, characters);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
        ImageLoader.getInstance().init(config);
        this.listItemLayout = listItemLayout;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        character = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(listItemLayout, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        run();
        return view;
    }

    public void run() {
        int imageResource = character.getImgId(getContext());
        RoundedBitmapDisplayer.RoundedDrawable image = new RoundedBitmapDisplayer.RoundedDrawable(BitmapFactory.decodeResource(getContext().getResources(), imageResource), 70, 0);
        viewHolder.characterAvatar.setImageDrawable(image);
        viewHolder.characterName.setText(character.getName());
        viewHolder.characterCV.setText("CV:" + character.getCv());
    }
}

class ViewHolder {
    ImageView characterAvatar;
    TextView characterName;
    TextView characterCV;

    public ViewHolder(View view) {
        characterAvatar = (ImageView) view.findViewById(R.id.avatar);
        characterName = (TextView) view.findViewById(R.id.character_name);
        characterCV = (TextView) view.findViewById(R.id.character_cv);
    }
}