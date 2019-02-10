package com.gavblaze.android.simplecanvas;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    // Initial offset for rectangle.
    private static final int OFFSET = 120;
    // Multiplier for randomizing color.
    private static final int MULTIPLIER = 100;

    // The Canvas object stores information on WHAT to draw
    // onto its associated bitmap.
    // For example, lines, circles, text, and custom paths.
    private Canvas mCanvas;

    // The Paint object stores HOW to draw.
    // For example, what color, style, line thickness, or text size.
    // The Paint class offers a rich set of coloring and drawing options.
    private Paint mPaint = new Paint();

    // Create a Paint object for underlined text.
    // The Paint class offers a full complement of typographical styling methods.
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);

    // The bitmap represents the pixels that will be displayed.
    private Bitmap mBitmap;

    // The view is the container for the bitmap.
    // Layout on the screen and all user interaction is through the view.
    private ImageView mImageView;

    private Rect mRect = new Rect();
    // Distance of rectangle from edge of canvas.
    private int mOffset = OFFSET;
    // Bounding box for text.
    private Rect mBounds = new Rect();

    private int mColorBackground;
    private int mColorRectangle;
    private int mColorAccent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mImageView  = findViewById(R.id.myimageview);

        mColorBackground = ResourcesCompat.getColor(getResources(), R.color.colorBackground, null);
        mColorRectangle = ResourcesCompat.getColor(getResources(), R.color.colorRectangle, null);
        mColorAccent = ResourcesCompat.getColor(getResources(), R.color.colorAccent, null);


        mPaintText.setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
        mPaintText.setTextSize(70);
    }

    /**
     * Click handler that responds to user taps by drawing an increasingly
     * smaller rectangle until it runs out of room. Then it draws a circle
     * with the text "Done!". Demonstrates basics of drawing on canvas.
     *   1. Create bitmap.
     *   2. Associate bitmap with view.
     *   3. Create canvas with bitmap.
     *   4. Draw on canvas.
     *   5. Invalidate the view to force redraw.
     *
     * @param view The view in which we are drawing.
     */
    public void drawSomething(View view) {

        int screenWidth = view.getWidth();
        int screenHeight = view.getHeight();

        if (mOffset == OFFSET) { // is this our first click? Create the canvas object only once
            mBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
            /*A canvas is a 2D drawing surface that provides methods for drawing onto a bitmap*/
            // Associate the bitmap to the ImageView.
            mImageView.setImageBitmap(mBitmap);

            mCanvas = new Canvas(mBitmap);  // We need to specify a bitmap for the Canvas to draw onto

            mCanvas.drawColor(mColorBackground);
            mCanvas.drawText(getResources().getString(R.string.keep_tapping), mOffset, mOffset, mPaintText);
            mOffset += OFFSET;
        } else if (mOffset < screenWidth / 2 && mOffset < screenHeight / 2) {
            // Change the color by subtracting an integer.
            mPaint.setColor(mColorRectangle - MULTIPLIER*mOffset);
            mRect.set(mOffset, mOffset, screenWidth - mOffset, screenHeight - mOffset);
       //     Rect rect = new Rect(mOffset, mOffset, screenWidth - mOffset, screenHeight - mOffset);
            mCanvas.drawRect(mRect, mPaint);
            mOffset += OFFSET;
        } else {

            mPaint.setColor(mColorAccent);
            mCanvas.drawCircle(screenWidth/2, screenHeight/2, 185, mPaint);

            String text = getResources().getString(R.string.done);
            // Get bounding box for text to calculate where to draw it
            mPaintText.getTextBounds(text, 0, text.length(), mBounds);
            // Calculate x and y for text so it's centered.
            int x = screenWidth/2 - mBounds.centerX();
            int y = screenHeight/2 - mBounds.centerY();

            mCanvas.drawText(text, x, y, mPaintText);

            mPaint.setColor(Color.BLUE);
            mCanvas.drawRect(mBounds, mPaint);
        }

        view.invalidate();
    }
}


