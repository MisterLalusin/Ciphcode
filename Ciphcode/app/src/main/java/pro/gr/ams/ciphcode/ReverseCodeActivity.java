package pro.gr.ams.ciphcode;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ReverseCodeActivity extends AppCompatActivity {

    String codename = "Reverse Code";
    String codesample = "edoC esreveR";

    private TextView code_name;
    private Button encrypt;
    private Button decrypt;
    private EditText input;
    private TextView output;
    private String inputString;
    private String outputString;
    private Button info;
    private TextView code_sample;

    SQLiteDatabase infodb;
    SQLiteOpenHelper infoopenHelper;
    Cursor infocursor;

    private LinearLayout yes_no_option_layout;
    private TextView yes_no_option_question;
    private RadioButton yes_no_option_yes;
    private RadioButton yes_no_option_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        overridePendingTransition(R.anim.act2_enter,R.anim.act2_exit);

        code();

        info();

        yesnooption();

        hidekeyboardOutputFocus();

        disablePaste();
    }

    public void  disablePaste() {
        if (output.getCustomSelectionActionModeCallback() == null) {
            output.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    menu.removeItem(16908322);
                    menu.removeItem(16908320);
                    return true;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                }
            });
        }
        else{
            output.setCustomSelectionActionModeCallback(null);
        }
    }

    public void hidekeyboardOutputFocus() {
        output.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean hasfocus) {
                if (hasfocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                else {
                }
            }
        });
    }

    public void yesnooption() {
        yes_no_option_layout = (LinearLayout)findViewById(R.id.yes_no_option_layout);
        yes_no_option_layout.setVisibility(View.VISIBLE);
        yes_no_option_question = (TextView)findViewById(R.id.yes_no_option_question);
        yes_no_option_question.setText("Reverse words?");
        yes_no_option_yes = (RadioButton)findViewById(R.id.yes_no_option_yes);
        yes_no_option_no = (RadioButton)findViewById(R.id.yes_no_option_no);
    }

    public void info() {
        infoopenHelper = new InfoDatabaseHelper(this);
        infodb = infoopenHelper.getReadableDatabase();

        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =? AND FirstTime =?", new String[]{codename,"true"});
        if (infocursor != null) {
            if (infocursor.getCount() > 0) {
                Intent i = new Intent(getApplicationContext(),InfoActivity.class);
                i.putExtra("infoHTML",codename);
                startActivity(i);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.act1_enter, R.anim.act1_exit);
        finish();
    }

    public void code() {
        code_name = (TextView)findViewById(R.id.code_name);
        encrypt = (Button)findViewById(R.id.encrypt);
        decrypt = (Button)findViewById(R.id.decrypt);
        code_sample = (TextView)findViewById(R.id.code_sample);
        code_name.setText(codename);
        code_sample.setText(codesample);
        input = (EditText)findViewById(R.id.input);
        output = (TextView)findViewById(R.id.output);
        info = (Button) findViewById(R.id.info);
        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().replaceAll("\n","").replaceAll(" ","").length()==0) {
                    Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                }
                else if (input.getText().toString().indexOf("⁞")!=-1) {
                    Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                }
                else if (input.getText().toString().indexOf("ᴥ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                }
                /*else if (input.getText().toString().indexOf("  ")!=-1||input.getText().toString().indexOf("\n\n\n")!=-1) {
                    Toast.makeText(getApplicationContext(), "Too much spaces detected.", Toast.LENGTH_SHORT).show();
                }*/
                else if (!yes_no_option_yes.isChecked()&&!yes_no_option_no.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Do you want to reverse words?", Toast.LENGTH_SHORT).show();
                }
                else if (yes_no_option_yes.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();
                    reversecodeyes();
                }
                else if (yes_no_option_no.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();
                    reversecodeno();
                }
            }
        });
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().replaceAll("\n","").replaceAll(" ","").length()==0) {
                    Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                }
                else if (input.getText().toString().indexOf("⁞")!=-1) {
                    Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                }
                else if (input.getText().toString().indexOf("ᴥ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                }
                /*else if (input.getText().toString().indexOf("  ")!=-1||input.getText().toString().indexOf("\n\n\n")!=-1) {
                    Toast.makeText(getApplicationContext(), "Too much spaces detected.", Toast.LENGTH_SHORT).show();
                }*/
                else if (!yes_no_option_yes.isChecked()&&!yes_no_option_no.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Do you want to reverse words?", Toast.LENGTH_SHORT).show();
                }
                else if (yes_no_option_yes.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();
                    reversecodeyes();
                }
                else if (yes_no_option_no.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();
                    reversecodeno();
                }
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),InfoActivity.class);
                i.putExtra("infoHTML",codename);
                startActivity(i);
            }
        });
        input.requestFocus();
    }

    public void reversecodeyes(){
        inputString = input.getText().toString();
        StringBuffer buffer = new StringBuffer(inputString);
        buffer.reverse();
        outputString = buffer.toString();

        output.setText(outputString);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void reversecodeno(){
        inputString = input.getText().toString();
        int i = 0;

        CharSequence messagesequence = BuildConfig.FLAVOR;
        CharSequence reversesequence = BuildConfig.FLAVOR;

        for (String splitline : inputString.split("\n")) {
            messagesequence = (new StringBuilder(splitline).toString()) + " ⁞";
            for (String splitword : messagesequence.toString().split(" ")) {
                reversesequence = (reversesequence + new StringBuilder(splitword).reverse().toString()) + " ";
            }
            i++;
        }
        i++;

        outputString = reversesequence.toString().replaceAll(" ⁞ ","\n").replaceAll(" ⁞","\n");

        output.setText(outputString);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
