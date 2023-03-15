package com.example.SmartBox_2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private List<String> mImageUrls;
    private Context mContext;

    public ImageAdapter(Context context, List<String> imageUrls) {
        mContext = context;
        mImageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    @Override
    public String getItem(int position) {
        return mImageUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Use position as ID
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            // If convertView is null, inflate a new view
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.grid_item_layout, parent, false);
        } else {
            // If convertView is not null, reuse the view
            view = convertView;
        }

        // Load the image into the ImageView using Picasso or Glide
        String imageUrl = getItem(position);
        ImageView imageView = view.findViewById(R.id.imageView);
        Glide.with(mContext).load(imageUrl).into(imageView);

        // Set the image name in the TextView
        String imageName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);

        // Finding the index of the "?" character
        int questionMarkIndex = imageName.indexOf("?");

        // Extracting the substring up to (but not including) the "?" character
        String url = imageName.substring(0, questionMarkIndex);

        // Finding the index of the last occurrence of "%2F"
        int lastIndex = url.lastIndexOf("%2F");

        // Extracting the last segment of the URL
        String lastSegment = url.substring(lastIndex + 3);

        // Replacing colons with %3A
        String extractedString = lastSegment.replace("%3A", ":");
        String extractedString2 = extractedString.replace("%20", " ");

        TextView textView = view.findViewById(R.id.textView);
        Log.d("TAG", "Image Name: " + extractedString2);
        textView.setText(extractedString2);

        return view;
    }

    // Define a ViewHolder class to hold references to the ImageView and TextView
    private static class ViewHolder {
        ImageView imageView;
        TextView textView;

        ViewHolder(ImageView imageView, TextView textView) {
            this.imageView = imageView;
            this.textView = textView;
        }
    }
}