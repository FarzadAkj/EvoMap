package ir.evoteam.evomap;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button mLoginButton, mExitButton;
    EditText mUsernameEitText, mPasswordEditText;
    boolean isLogedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginButton = (Button) findViewById(R.id.LogInButtonInLoginPage);
        mExitButton = (Button) findViewById(R.id.ExitButtonInLoginPage);
        mUsernameEitText = (EditText) findViewById(R.id.UserNameEditText);
        mPasswordEditText = (EditText) findViewById(R.id.PassWordEditText);
        mLoginButton.setOnClickListener(this);
        mExitButton.setOnClickListener(this);

        if (isLogedIn) {
//            Intent startMapActivityIntent = new Intent(this, MainActivity.class);
//            startActivity(startMapActivityIntent);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LogInButtonInLoginPage: {
                AuthenticateAsync authenticateAsync = new AuthenticateAsync(LoginActivity.this);
                String temp1 = mUsernameEitText.getText().toString();
                String temp2 = mPasswordEditText.getText().toString();
                if (!temp1.equals("") && !temp2.equals(""))
                    authenticateAsync.execute(temp1, temp2);
                else {
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

    private String LatID = "Latitude";
    private String LngID = "Longtitude";
    private String DateID = "DateTime";
    private String DriverID = "Driver_ID";
    private String MarkLngID = "Mark_Longtitude";
    private String MarkLatID = "Mark_Latitude";
    private String MarkTitleID = "Mark_Title";




}

