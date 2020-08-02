package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStoreOwner;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class GeoActivity extends AppCompatActivity {

    private Button mButtonTrue;
    private Button mButtonFalse;
    private TextView mtextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // inflate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);

        // find views, because in infalating we mak objects but in layout we don't have any object,
        // so after inflating we have object but we should find them from Ids.
        findViews();

        // set listener
        setOnListener();
    }

    // This method set listener for each views (button true, button false, ...)
    private void setOnListener() {
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewsButtonTrue();
            }
        });
        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setViewsButtonFalse();
            }
        });
    }

    private void setViewsButtonTrue(){
        Toast toast = Toast.makeText(GeoActivity.this, R.string.toast_correct, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 225);


        View view_1 = toast.getView();
        TextView textView = view_1.findViewById(android.R.id.message);
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_check_24, 0);
        textView.setTextSize(20);

        mtextView.setTextSize(20);
        mtextView.setTextColor(Color.GREEN);

        toast.show();
    }


    private void setViewsButtonFalse(){
        Toast toast = Toast.makeText(GeoActivity.this, R.string.toast_incorrect, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 50);

        View view_1 = toast.getView();
        TextView textView = view_1.findViewById(android.R.id.message);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_clear_24, 0, 0, 0);
        textView.setTextSize(20);

        mtextView.setTextSize(20);
        mtextView.setTextColor(Color.RED);

        toast.show();
    }
    // This method find view of each views, if you don't, the initial value is null and id dose not work
    private void findViews() {
        mtextView = findViewById(R.id.textview_question_txt);
        mButtonTrue = findViewById(R.id.btn_true);
        mButtonFalse = findViewById(R.id.btn_false);
    }

}