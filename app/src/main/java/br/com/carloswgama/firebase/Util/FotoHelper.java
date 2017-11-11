package br.com.carloswgama.firebase.Util;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class FotoHelper {


    public static String BitmapToString(Bitmap foto) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap StringToBitmap(String foto) {
        byte[] bytes = Base64.decode(foto, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
