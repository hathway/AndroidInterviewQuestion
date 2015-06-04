package com.hathway.androidinterviewquestion;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.getinch.retrogram.model.Media;
import com.getinch.retrogram.model.Popular;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by mikemaxwell on 5/24/15.
 */
public class InstagramFeedAdapter implements ListAdapter {

    private Popular data;
    private Context mContext;
    private LayoutInflater mInflater;
    DateFormat mFormat;

    public InstagramFeedAdapter(Context context, Popular popular)
    {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        data = popular;
        mFormat = DateFormat.getDateTimeInstance();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return data.getMediaList().size();
    }

    @Override
    public Object getItem(int i) {
        return data.getMediaList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(R.layout.row_photo, null);
        }

        ImageView photoView = (ImageView) view.findViewById(R.id.instaPhotoView);
        TextView textView = (TextView) view.findViewById(R.id.userTextView);
        TextView dateView = (TextView) view.findViewById(R.id.createdTextView);
        ImageView userProfileView = (ImageView) view.findViewById(R.id.userImageView);
        TextView likeView = (TextView) view.findViewById(R.id.likesTextView);

        Media media = data.getMediaList().get(i);
        textView.setText(media.getUser().getUsername() + " (" + media.getUser().getFullName() + ")");
        dateView.setText(mFormat.format(new Date(media.getCreatedTime())));
        likeView.setText("Likes: " + media.getLikes().getCount());

        Picasso.with(mContext).load(media.getImages().getStandardResolution().getUrl()).into(photoView);
        Picasso.with(mContext).load(media.getUser().getProfilePicture()).into(userProfileView);

        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return R.id.row_photo;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }
}
