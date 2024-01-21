package com.example.drawoverotherapps;

import static android.content.Context.WINDOW_SERVICE;


import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class Window {


    // declaring required variables

    private Context context;

    private View mView;

    private WindowManager.LayoutParams mParams;

    private WindowManager mWindowManager;

    private LayoutInflater layoutInflater;


    private float dX, dY;

    public Window(Context context){

        this.context=context;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // set the layout parameters of the window

            mParams = new WindowManager.LayoutParams(

                    // Shrink the window to wrap the content rather

                    // than filling the screen

                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,

                    // Display it on top of other application windows

                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,

                    // Don't let it grab the input focus

                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,

                    // Make the underlying application window visible

                    // through any transparent parts

                    PixelFormat.TRANSLUCENT);



        }

        // getting a LayoutInflater

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // inflating the view with the custom layout we created

        mView = layoutInflater.inflate(R.layout.popup_window, null);


        // the view from the window
        EditText editText = mView.findViewById(R.id.txtWord);
        editText.requestFocus();

        // Esto supuestamente muestra el telclado.. sin embargo no funciona.
        InputMethodManager im = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.toggleSoftInputFromWindow(mView.getApplicationWindowToken(),InputMethodManager.SHOW_FORCED, 0);

        // set onClickListener on the remove button, which removes
        mView.findViewById(R.id.window_close).setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                close();

            }

        });

        // Define the position of the

        // window within the screen

        mParams.gravity = Gravity.CENTER;

        mParams.y=500;

        mParams.flags = mParams.flags | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;


        mWindowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);


        // Agregar OnTouchListener para la vista
        /*
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Guardar las coordenadas iniciales al presionar
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        // Actualizar las coordenadas mientras se mueve
                        v.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        return true;

                    default:
                        return false;
                }
            }
        });
         */


    }



    public void open() {

        try {

            // check if the view is already

            // inflated or present in the window

            if(mView.getWindowToken()==null) {

                if(mView.getParent()==null) {

                    mWindowManager.addView(mView, mParams);

                }

            }

        } catch (Exception e) {

            Log.d("Error1",e.toString());

        }
    }


    public void close() {



        try {

            // remove the view from the window

            ((WindowManager)context.getSystemService(WINDOW_SERVICE)).removeView(mView);

            // invalidate the view

            mView.invalidate();

            // remove all views

            ((ViewGroup)mView.getParent()).removeAllViews();



            // the above steps are necessary when you are adding and removing

            // the view simultaneously, it might give some exceptions

        } catch (Exception e) {

            Log.d("Error2",e.toString());

        }

    }

}
