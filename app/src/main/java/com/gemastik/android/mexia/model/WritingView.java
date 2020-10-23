package com.gemastik.android.mexia.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class WritingView extends View {
    private Paint paint;
    private Path path;
    private Canvas canvas;
    private String stringResult;
    private TextRecognizer textRecognizer;

    public WritingView(Context context) {
        super(context);
        init();
    }

    public WritingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(30);
        path = new Path();
        canvas = new Canvas();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        getParent().requestDisallowInterceptTouchEvent(true);
        canvas.drawPath(path, paint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                break;

        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Bitmap mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(mBitmap);
    }

    public void clear() {
        path.reset();
        invalidate();
    }

    @SuppressLint("WrongThread")
    public String textRecognition(Context context){
        this.setDrawingCacheEnabled(true);

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cacheImageMexia";
        File dir = new File(dirPath);
        if (!dir.exists()) dir.mkdir();
        File file = new File(dirPath, "imageCheck.png");
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            getDrawingCache().compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File fIn = new File(dirPath, "imageCheck.png");
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(fIn));
            textRecognizer = new TextRecognizer.Builder(context.getApplicationContext()).build();
            if (!textRecognizer.isOperational()){
                Toast.makeText(context, "Text recognizer tidak terdeteksi", Toast.LENGTH_SHORT).show();
            }else {
                Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                SparseArray<TextBlock> items = textRecognizer.detect(frame);
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < items.size(); i++) {
                    TextBlock myItem = items.valueAt(i);
                    stringBuilder.append(myItem.getValue());
                    stringBuilder.append("");
                }
                stringResult = stringBuilder.toString();
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        this.setDrawingCacheEnabled(false);
        return stringResult;
    }

}
