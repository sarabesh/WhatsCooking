package com.wolf.silver.whatscooking;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

/**
 * Created by sarabesh on 12/23/2017.
 */

public class RecipeAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Recipe> RItems;

    public RecipeAdapter(Activity activity, List<Recipe> Items) {
        this.activity = activity;
        this.RItems = Items;
    }

    @Override
    public int getCount() {
        return RItems.size();
    }

    @Override
    public Object getItem(int i) {
        return RItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.listie, null);

        TextView name=view.findViewById(R.id.nam);

        ImageView imgv=view.findViewById(R.id.img);
        //Button dia=view.findViewById(R.id.button4);
        final Recipe r=RItems.get(i);

        name.setText(r.getDish());

       // imgv.setImageURI(Uri.parse("http://192.168.43.207/wcapi/uploads/"+r.getImage()));
       DownloadImageWithURLTask downloadTask = new DownloadImageWithURLTask(imgv);
        downloadTask.execute("http://192.168.43.207/wcapi/uploads/"+r.getImage());
        return view;
    }

}
class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    public DownloadImageWithURLTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String pathToFile = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(pathToFile).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }
    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}

