package ir.evoteam.evomap;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import za.co.riggaroo.materialhelptutorial.TutorialItem;
import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 1234;
    Button mLoginButton, mExitButton;
    EditText mUsernameEitText, mPasswordEditText;
    SharedPreferences sp;
    boolean isLogedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putBoolean("IsLogIN",false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginButton = (Button) findViewById(R.id.LogInButtonInLoginPage);
        mExitButton = (Button) findViewById(R.id.ExitButtonInLoginPage);
        mUsernameEitText = (EditText) findViewById(R.id.UserNameEditText);
        mPasswordEditText = (EditText) findViewById(R.id.PassWordEditText);
        mLoginButton.setOnClickListener(this);
        mExitButton.setOnClickListener(this);

        if (sp.getBoolean("IsLogIN",false))
        {
            loadTutorial();
            e.putBoolean("IsLogIN",true);

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LogInButtonInLoginPage: {
                AuthenticateAsync authenticateAsync = new AuthenticateAsync(LoginActivity.this);
                String temp1 = mUsernameEitText.getText().toString();
                String temp2 = mPasswordEditText.getText().toString();
                if (!temp1.equals("") && !temp2.equals("")) {
                    authenticateAsync.execute(temp1, temp2);

                }else {
                    Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
                    if (temp1.equals("")) {
                        mUsernameEitText.startAnimation(shake);
                    }
                    if (temp2.equals("")) {
                        mPasswordEditText.startAnimation(shake);
                    }
                }
            }
            break;
            case R.id.ExitButtonInLoginPage: {
                final AlertDialog.Builder exitDialogWarning = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogTheme);
                exitDialogWarning.setMessage(R.string.exit_Message);
                exitDialogWarning.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                exitDialogWarning.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                exitDialogWarning.show();

            }
            break;
        }
    }

    public void loadTutorial() {
        Intent mainAct = new Intent(this, MaterialTutorialActivity.class);
        mainAct.putParcelableArrayListExtra(MaterialTutorialActivity.MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS, getTutorialItems(this));
        startActivityForResult(mainAct, REQUEST_CODE);

    }

    private ArrayList<TutorialItem> getTutorialItems(Context context) {
//        TutorialItem tutorialItem1 = new TutorialItem(R.string.slide_1_Welcome, R.string.EvoMap,
//                R.color.slide_3, R.drawable.taxi2);
//
//        ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
//        tutorialItems.add(tutorialItem1);
//        TutorialItem tutorialItem1 = new TutorialItem(R.string.slide_1_Welcome, R.string.EvoMap,
//                R.color.slide_3, R.drawable.taxi2  ,  R.drawable.taxi2);

//        TutorialItem tutorialItem2 =new TutorialItem(R.string.slide_1_Welcome, R.string.EvoMap,
//                R.color.slide_3, R.drawable.taxi2  ,  R.drawable.taxi2);

        TutorialItem tutorialItem3 = new TutorialItem(context.getString(R.string.slide_1_Welcome), context.getString(
                R.string.EvoMap),
                R.color.slide_3, R.drawable.taxi2);

        TutorialItem tutorialItem4 = new TutorialItem(R.string.slide_1_Welcome, R.string.EvoMap,
                R.color.slide_2, R.color.transparent  ,  R.drawable.taxi1);

        ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
//        tutorialItems.add(tutorialItem1);
//        tutorialItems.add(tutorialItem2);
        tutorialItems.add(tutorialItem3);
        tutorialItems.add(tutorialItem4);
        return tutorialItems;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //    super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            Toast.makeText(this, "Tutorial finished", Toast.LENGTH_LONG).show();

        }
    }


}

