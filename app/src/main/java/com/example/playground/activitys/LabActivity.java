package com.example.playground.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.playground.BlurBook.BlurView;
import com.example.playground.BlurBook.SupportRenderScriptBlur;
import com.example.playground.CircleImageView;
import com.example.playground.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.core.content.FileProvider.getUriForFile;

public class LabActivity extends AppCompatActivity {

    private static final int CHOICE_AVATAR_FROM_CAMERA_CROP = 777 ;
    private static final int CHOICE_AVATAR_FROM_CAMERA = 888;
    private static final int CHOICE_AVATAR_FROM_GALLERY = 999;
    public static String fileName;
    CircleImageView cir;
    ImageView backgroundimg;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    TextView titleView;
    BlurView blur;
    RecyclerView recyclerView;
    ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab);
        clearCache(this);
        pager = findViewById(R.id.Lab_pager);
        recyclerView = findViewById(R.id.Lab_Recycler);
        recyclerSetup();
         cir = findViewById(R.id.Lab_cirimg);
         backgroundimg = findViewById(R.id.Lab_img);
         toolbar = findViewById(R.id.Lab_toolbar);
         collapsingToolbarLayout = findViewById(R.id.Lab_Collapsing);
         appBarLayout = findViewById(R.id.Lab_appbar);
        for (int i = 0; i < toolbar.getChildCount(); ++i) {
            View child = toolbar.getChildAt(i);
            if(child instanceof TextView){
                titleView = (TextView) child;
            }
        }
        blur=findViewById(R.id.Lab_blur);
        // setSupportActionBar(toolbar);

        cir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogpicker();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        Log.e( "onRequestPermission",requestCode+"" );
        switch (requestCode) {
            case CHOICE_AVATAR_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, CHOICE_AVATAR_FROM_GALLERY);
                } else {
                    Log.d( "onRequestPermissions","ERROR here");
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
            case  CHOICE_AVATAR_FROM_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(galleryIntent, CHOICE_AVATAR_FROM_CAMERA);
                } else {
                    Log.d( "onRequestPermissions","ERROR here");
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }
    private void dialogpicker(){
        AlertDialog.Builder dia = new AlertDialog.Builder(this);
        dia.setTitle("Choose action")
                .setItems(R.array.chooses, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Me_Triggerd","click"+which);
                        switch (which){
                            case 0:{
                                choiceAvatarFromCamera();
                                break;
                            }
                            case 1:{
                                choiceAvatarFromGallery(""+which);
                                break;
                            }
                        }
                    }
                });
        dia.create().show();
    }

    public void choiceAvatarFromCamera() {
        Log.d("Me_Triggerd","camera");
        fileName = System.currentTimeMillis() + ".jpg";
        if (ActivityCompat.checkSelfPermission(LabActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LabActivity.this, new String[]{Manifest.permission.CAMERA}, CHOICE_AVATAR_FROM_CAMERA);
        }else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(fileName));
            startActivityForResult(intent, CHOICE_AVATAR_FROM_CAMERA);
        }
    }




    public void choiceAvatarFromGallery(String from) {
        Log.d("Me_Triggerd","gallery "+from);
        if (ActivityCompat.checkSelfPermission(LabActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LabActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CHOICE_AVATAR_FROM_GALLERY);
        }else{
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(intent, CHOICE_AVATAR_FROM_GALLERY);
        }
          //  return;

    }



    private void docrop(Uri imageuri) {


        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), queryName(getContentResolver(), imageuri)));
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(80);

        options.setToolbarColor(ContextCompat.getColor(this, android.R.color.white));
        options.setStatusBarColor(ContextCompat.getColor(this, android.R.color.white));
        options.setActiveWidgetColor(ContextCompat.getColor(this, android.R.color.white));

            options.withAspectRatio(1, 1);

            options.withMaxResultSize(1000, 1000);

        UCrop.of(imageuri, destinationUri)
                .withOptions(options)

                .start(this);

    }
    private static String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    private Uri getCacheImagePath(String fileName) {
        File path = new File(getExternalCacheDir(), "camera");
        if (!path.exists()) path.mkdirs();
        File image = new File(path, fileName);
        return getUriForFile(LabActivity.this, getPackageName() + ".provider", image);
    }
    public static void clearCache(Context context) {
        File path = new File(context.getExternalCacheDir(), "camera");
        if (path.exists() && path.isDirectory()) {
            for (File child : path.listFiles()) {
                child.delete();
            }
        }
    }
    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            Log.d("Me_Triggerd","result"+requestCode);
            switch (requestCode){
                case CHOICE_AVATAR_FROM_GALLERY:{
                    docrop(data.getData());
                    break;
                }
                case CHOICE_AVATAR_FROM_CAMERA:{
                    docrop(getCacheImagePath(fileName));
                    break;
                }
                case UCrop.REQUEST_CROP:{
                    Bitmap avatar = null;
                    try {
                        avatar = MediaStore.Images.Media.getBitmap(this.getContentResolver(),UCrop.getOutput(data));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cir.setImageBitmap(avatar);
                    backgroundimg.setImageBitmap(avatar);
                    int colorDominat = getDominantColor(avatar);

                    Log.d("ColotCheck ",isColorDark(colorDominat)+"");
                    if (isColorDark(colorDominat)) {
                        collapsingToolbarLayout.setTitle("Lab1");
                        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
                        collapsingToolbarLayout.setExpandedTitleTextColor(ColorStateList.valueOf(Color.WHITE));
                        View view = getWindow().getDecorView();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        }
                    }else{

                        collapsingToolbarLayout.setTitle("Lab2");
                        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
                        collapsingToolbarLayout.setExpandedTitleTextColor(ColorStateList.valueOf(Color.BLACK));
                        View view = getWindow().getDecorView();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        }
                    }
                    blur.setupWith(appBarLayout)
                            .setBlurEnabled(true)
                            .setFrameClearDrawable(new BitmapDrawable(getResources(),avatar))
                            .setBlurAlgorithm(new SupportRenderScriptBlur(this))
                            .setBlurRadius(6f)
                            .setOverlayColor(Color.TRANSPARENT)
                            .setHasFixedTransformationMatrix(true);

                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(manipulateColor(colorDominat,0.8f));
                //    backgroundimg.setColorFilter(colorDominat, PorterDuff.Mode.MULTIPLY);
                 //   toolbar.setBackgroundColor(colorDominat);
                    collapsingToolbarLayout.setContentScrimColor(colorDominat);
                    appBarLayout.setBackgroundColor(colorDominat);
                    break;
                }
            }


        }

        super.onActivityResult(requestCode, resultCode, data);

    }
    public static int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }
    public static boolean isColorDark(int color){
        double darkness = 1-(0.299*Color.red(color) + 0.587*Color.green(color) + 0.114*Color.blue(color))/255;
        return !(darkness < 0.5);
    }

    public static int getDominantColor(Bitmap bitmap) {

        if (bitmap == null)
            throw new NullPointerException();

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int size = width * height;
        int pixels[] = new int[size];

        Bitmap bitmap2 = bitmap.copy(Bitmap.Config.ARGB_4444, false);

        bitmap2.getPixels(pixels, 0, width, 0, 0, width, height);

        final List<HashMap<Integer, Integer>> colorMap = new ArrayList<HashMap<Integer, Integer>>();
        colorMap.add(new HashMap<Integer, Integer>());
        colorMap.add(new HashMap<Integer, Integer>());
        colorMap.add(new HashMap<Integer, Integer>());

        int color = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        Integer rC, gC, bC;
        for (int i = 0; i < pixels.length; i++) {
            color = pixels[i];

            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);

            rC = colorMap.get(0).get(r);
            if (rC == null)
                rC = 0;
            colorMap.get(0).put(r, ++rC);

            gC = colorMap.get(1).get(g);
            if (gC == null)
                gC = 0;
            colorMap.get(1).put(g, ++gC);

            bC = colorMap.get(2).get(b);
            if (bC == null)
                bC = 0;
            colorMap.get(2).put(b, ++bC);
        }

        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            int max = 0;
            int val = 0;
            for (Map.Entry<Integer, Integer> entry : colorMap.get(i).entrySet()) {
                if (entry.getValue() > max) {
                    max = entry.getValue();
                    val = entry.getKey();
                }
            }
            rgb[i] = val;
        }

        int dominantColor = Color.rgb(rgb[0], rgb[1], rgb[2]);

        return dominantColor;
    }

private void recyclerSetup(){

        class data{
            String text,number;
            int img;

            public data(String text, String number, int img) {
                this.text = text;
                this.number = number;
                this.img = img;
            }

            public int getImg() {
                return img;
            }

            public void setImg(int img) {
                this.img = img;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }
        }
        final List<data> list = new ArrayList<>();
        final int[] imgs = new int[]{
                R.drawable.boom,R.drawable.halk,R.drawable.moneytrans,R.drawable.mountain
        };
        String names[] = new String[]{
                "Boom","Halk","Money","mountain"
        };
    for (int i = 0; i < imgs.length; i++)
        list.add(new data(names[i],""+i,imgs[i]));
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    class Viewport extends RecyclerView.ViewHolder {
        ImageView imageCard;
        TextView name;
        Viewport(@NonNull View itemView) {
            super(itemView);
            for(int index=0; index<((ViewGroup)itemView).getChildCount(); index++) {
                Log.d( "Viewport: index",index+"");
                View nextChild = ((ViewGroup)itemView).getChildAt(index);
                if(nextChild instanceof LinearLayout){
                    for(int indexin=0; indexin<((ViewGroup)nextChild).getChildCount()+1; indexin++) {
                        Log.d( "Viewport: indexIN",indexin+"/"+((ViewGroup)nextChild).getChildCount()+"/"+((ViewGroup)itemView).getChildCount());
                        View nextChildin = ((ViewGroup)nextChild).getChildAt(indexin);
                        if (nextChildin instanceof ImageView){
                            Log.d( "Viewport: index",indexin+"Found ImageView");
                            imageCard = (ImageView) nextChildin;}
                        if (nextChildin instanceof TextView){
                            Log.d( "Viewport: index",indexin+"Found TextView");
                            name = (TextView)nextChildin;}
//                        Log.d( "Viewport: index",indexin+":Class name:"+nextChildin.getClass().getName());
                    }
                }
            }
        }
    }
        recyclerView.setAdapter(new RecyclerView.Adapter<Viewport>() {


            @NonNull
            @Override
            public Viewport onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                CardView cardView = new CardView(parent.getContext());
                cardView.setCardElevation(9);
                cardView.setRadius(18);

                LinearLayout majorlayout = new LinearLayout(parent.getContext());
                ImageView img = new ImageView(parent.getContext());

                TextView name = new TextView(parent.getContext());
                majorlayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
                name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
                majorlayout.addView(img);
                majorlayout.addView(name);

                cardView.addView(majorlayout);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,800);
                params.setMargins(20,20,20,20);
                cardView.setLayoutParams(params);
                return new Viewport(cardView);
            }

            @Override
            public void onBindViewHolder(@NonNull Viewport holder, int position) {
                holder.imageCard.setBackground(getDrawable(list.get(position).img));
                holder.name.setTextColor(Color.BLACK);
                holder.name.setGravity(Gravity.CENTER);
                holder.name.setText(list.get(position).text);
            }


            @Override
            public int getItemCount() {
                return list.size();
            }
        });
}
    /**

     * Use for decoding camera response data.

     *

     * @param data

     * @return

     */

}
