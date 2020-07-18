package com.example.fruitturn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class ActivityViewPager extends AppCompatActivity {

    private static final int REQUEST = 115;
    private ImageView mainImage;
    private int count = 0;
    private Context mContext=ActivityViewPager.this;

    private int [] array = {
            R.drawable.pic_1, R.drawable.pic_2, R.drawable.pic_3, R.drawable.pic_4, R.drawable.pic_5, };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        ImageView btnLeft = findViewById(R.id.left);
        ImageView btnRight = findViewById(R.id.right);
        ImageView btnShare = findViewById(R.id.share);
        ImageView btnSetView = findViewById(R.id.setw);
        ImageView btnDownload = findViewById(R.id.download);
        mainImage = findViewById(R.id.main_image);

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count < 4) {
                    count++;
                    mainImage.setImageResource(array[count]);
                }
            }
        });

        btnSetView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WallpaperManager myWallpaperManager
                        = WallpaperManager.getInstance(getApplicationContext());
                try {
                    myWallpaperManager.setResource(array[count]);
                    Toast.makeText(getApplicationContext(), "Wallpaper set successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 23) {
                    String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (!hasPermissions(mContext, PERMISSIONS)) {
                        ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, REQUEST);
                    } else {
                        Bitmap bm = BitmapFactory.decodeResource(null, array[count]);
                        saveImageToExternalStorage();
                        Toast.makeText(getApplicationContext(), "Saved successfully, Check gallery", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Bitmap bm = BitmapFactory.decodeResource(null, array[count]);
                    saveImageToExternalStorage();
                    Toast.makeText(getApplicationContext(), "Saved successfully, Check gallery", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 0) {
                    count--;
                    mainImage.setImageResource(array[count]);
                }
            }
        });


        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareCompat.IntentBuilder.from(ActivityViewPager.this)
                        .setType("text/plain")
                        .setChooserTitle("Share URL")
                        .setText("http://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)
                        .startChooser();
            }
        });

    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Bitmap bitmap = BitmapFactory.decodeResource(null, array[count]);
                saveImageToExternalStorage();
                Toast.makeText(getApplicationContext(), "Saved successfully, Check gallery", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "The app was not allowed to write in your storage", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void saveImageToExternalStorage() {
        Drawable drawable = getResources().getDrawable(array[count]);

        Bitmap finalBitmap = ((BitmapDrawable) drawable).getBitmap();

        String ImagePath = MediaStore.Images.Media.insertImage(
                getContentResolver(), finalBitmap, "demo_image", "demo_image");

        Uri URI = Uri.parse(ImagePath);

    }
}
