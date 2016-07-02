package br.ufc.caps.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by messias on 7/1/16.
 *
 * @author Messias Lima
 */
public class FileUtil {
    private static final String FOLDER_NAME = "imagem";

    public static boolean saveBitmap(Bitmap bitmap, String id, Context context) {
        FileOutputStream fileOutputStream;
        try {
            File path = context.getDir(FOLDER_NAME, Context.MODE_PRIVATE);
            File imageFile = new File(path, id + ".png");
            fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            return true;
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return false;
    }

    public static Bitmap retrieveBitmap(String id, Context context) {
        try {
            File f = new File(context.getDir(FOLDER_NAME, Context.MODE_PRIVATE), id + ".png");
            return BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteImage(String id, Context context) {
        File f = new File(context.getDir(FOLDER_NAME, Context.MODE_PRIVATE), id + ".png");
        return f.delete();
    }
}
