package gr.athtech.coolmovies.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class AbstractActivity extends AppCompatActivity {

    abstract int getLayout();

    abstract void initLayout();

    abstract void runOperations();

    abstract void stopOperations();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initLayout();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        runOperations();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopOperations();
    }
}
