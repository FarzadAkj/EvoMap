package ir.evoteam.evomap;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.alhazmy13.catcho.library.Catcho;

import java.util.ArrayList;

import za.co.riggaroo.materialhelptutorial.TutorialItem;
import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialActivity;

import static ir.evoteam.evomap.MapsActivity.sharedPreferences;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 1234;
    Button mLoginButton, mExitButton;
    EditText mUsernameEitText, mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        //crash reporting
        Catcho.Builder(this)
            .recipients("evomapteam@gmail.com")
            .build();
        setContentView(R.layout.activity_login);
        mLoginButton = (Button) findViewById(R.id.LogInButtonInLoginPage);
        mExitButton = (Button) findViewById(R.id.ExitButtonInLoginPage);
        mUsernameEitText = (EditText) findViewById(R.id.UserNameEditText);
        mPasswordEditText = (EditText) findViewById(R.id.PassWordEditText);
        mLoginButton.setOnClickListener(this);
        mExitButton.setOnClickListener(this);

        sharedPreferences = getSharedPreferences
                (Constant.PREFERENCES_KEY, 0);


        loadTutorial();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LogInButtonInLoginPage:
            {
                AuthenticateAsync authenticateAsync = new AuthenticateAsync(LoginActivity.this);
                String temp1 = mUsernameEitText.getText().toString();
                String temp2 = MD5(mPasswordEditText.getText().toString());
                if (!temp1.equals("") && !temp2.equals("")) {

                    try{
                        authenticateAsync.execute(temp1, temp2);

                    }catch(Exception e){

                        Toast.makeText(this, "خطا!", Toast.LENGTH_SHORT).show();
                    }

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
        Intent mainAct = new Intent(LoginActivity.this
                , MaterialTutorialActivity.class);
        mainAct.putParcelableArrayListExtra(MaterialTutorialActivity.MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS, getTutorialItems(this));
        startActivityForResult(mainAct, REQUEST_CODE);

    }

    private ArrayList<TutorialItem> getTutorialItems(Context context) {

        TutorialItem tutorialItem3 = new TutorialItem(context.getString(R.string.slide_1_Welcome), context.getString(
                R.string.EvoMap),
                R.color.slide_3, R.drawable.taxi2);

        TutorialItem tutorialItem4 = new TutorialItem(R.string.slide_1_Welcome, R.string.EvoMap,
                R.color.slide_2, R.color.transparent  ,  R.drawable.taxi1);

        ArrayList<TutorialItem> tutorialItems = new ArrayList<>();

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

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}

