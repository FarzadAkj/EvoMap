package ir.evoteam.evomap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static ir.evoteam.evomap.MapsActivity.sharedPreferences;

public class StartingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("*****","start");
//        setContentView(R.layout.activity_starting);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        sharedPreferences = getSharedPreferences
                (Constant.PREFERENCES_KEY, 0);

        Boolean isLoggedIn;
        isLoggedIn = sharedPreferences.getBoolean(Constant.ISLOGEDIN_PREF_KEY ,false);

        Intent intent;

        if (isLoggedIn){
            intent = new Intent(this,MapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        else
            intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
        this.finish();
    }

}
