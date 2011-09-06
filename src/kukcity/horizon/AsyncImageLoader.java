package kukcity.horizon;


import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class AsyncImageLoader {
   
    private HashMap<String, SoftReference<Drawable>> imageCache;
    public AsyncImageLoader() {
        imageCache = new HashMap<String, SoftReference<Drawable>>();
    }
    public Drawable loadDrawable(final String imageUrl,final ImageView imageView, final ImageCallback imageCallback){
        if (imageCache.containsKey(imageUrl)) {
       
            SoftReference<Drawable> softReference = imageCache.get(imageUrl);
            Drawable drawable = softReference.get();
            if (drawable != null) {
                return drawable;
            }
        }
        final Handler handler = new Handler() {
            public void handleMessage(Message message) {
                imageCallback.imageLoaded((Drawable) message.obj, imageView,imageUrl);
            }
        };
    
        new Thread() {
            @Override
            public void run() {
                Drawable drawable = null;
				try {
					drawable = ImageUtil.geRoundDrawableFromUrl(imageUrl, 10);
				} catch (Exception e) {
					e.printStackTrace();
				}
                imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
                Message message = handler.obtainMessage(0, drawable);
                handler.sendMessage(message);
            }
        }.start();
        return null;
    }

    public interface ImageCallback {
        public void imageLoaded(Drawable imageDrawable,ImageView imageView, String imageUrl);
    }
}