package Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anwar.magineproject.PlayerActivity;
import com.anwar.magineproject.R;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Fragments.ChannelListFragment;
//import Fragments.MoviePlayerFragment;
import Model.Movies;

/**
 * Created by Anwar on 16-Oct-16.
 * An Adapter for inflating each and specific row for movie
 */

public class MoviesAdapter extends ArrayAdapter {
    private final ArrayList<Movies> moviesList;
    private final LayoutInflater inflater;
    private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
    private final Context context;

    //adapter constructor
    public MoviesAdapter(Context context, int resource, ArrayList<Movies> moviesList) {
        super(context, resource, moviesList);
        this.context = context;
        this.moviesList = moviesList;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            //inflating the layout
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            //setting up the holder Class
            holder = new ViewHolder();

            holder.title = (TextView) convertView.findViewById(R.id.movie_title);
            holder.subTitle = (TextView) convertView.findViewById(R.id.movie_subtitle);
            holder.studio = (TextView) convertView.findViewById(R.id.movie_studio);
            holder.img = (ImageView) convertView.findViewById(R.id.movie_img);
            holder.thumb = (ImageView) convertView.findViewById(R.id.moview_thumb);
            holder.play = (ImageView) convertView.findViewById(R.id.play);
            holder.play.bringToFront();
            convertView.setTag(holder);

        } else {
            //instead of calling find new view we get the tag
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(moviesList.get(position).getTitle());
        holder.studio.setText(moviesList.get(position).getStudio());
        holder.subTitle.setText(moviesList.get(position).getSubtitle());
        holder.subTitle.setEllipsize(TextUtils.TruncateAt.END);
        holder.subTitle.setMaxLines(2);
        Picasso.with(context)
                .load(Uri.encode(moviesList.get(position).getThumb(), ALLOWED_URI_CHARS))
                .error(R.mipmap.ic_launcher)
                .into(holder.thumb);

        Picasso.with(context)
                .load(Uri.encode(moviesList.get(position).getImage(), ALLOWED_URI_CHARS))
                .error(R.mipmap.ic_launcher)

                .into(holder.img);
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("url", moviesList.get(position).getSourceUrl());
                intent.putExtra("movieName", moviesList.get(position).getTitle());
                ((Activity) context).startActivityForResult(intent, 1);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
    }

    //Using view holder pattern
    public class ViewHolder {
        TextView title;
        TextView subTitle;
        TextView studio;
        ImageView img;
        ImageView thumb;
        ImageView play;


    }
}
