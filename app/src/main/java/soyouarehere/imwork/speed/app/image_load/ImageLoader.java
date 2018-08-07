package soyouarehere.imwork.speed.app.image_load;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageLoader {

    ImageCache cache = new MemoryCache();

    public void displayImage(String url, ImageView imageView){
        Bitmap bitmap = cache.get(url);
        if (bitmap==null){

        }else {
            imageView.setImageBitmap(bitmap);
        }
    }
    public void setImageCache(ImageCache cache){
        this.cache = cache;
    }
}
