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
import android.widget.TextView;
import android.widget.Toast;

public class AceCipherActivity extends AppCompatActivity {

    String codename = "Ace Cipher";
    String codesample = "AAAAA AAAaa Aaaaa / AAAaa aaAAA aAAAa aaaAA Aaaaa AaaAA";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        overridePendingTransition(R.anim.act2_enter,R.anim.act2_exit);

        code();

        info();

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
        output = (TextView) findViewById(R.id.output);
        info = (Button)findViewById(R.id.info);
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
                else {
                    acecipherencrypt();
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
                /*else if (input.getText().toString().indexOf("  ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Multiple spaces not supported.", Toast.LENGTH_SHORT).show();
                }*/
                else {
                    acecipherdecrypt();
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

    public void acecipherencrypt(){
        inputString = input.getText().toString().toLowerCase();

        if (inputString
                .replaceAll("a","")
                .replaceAll("b","")
                .replaceAll("c","")
                .replaceAll("d","")
                .replaceAll("e","")
                .replaceAll("f","")
                .replaceAll("g","")
                .replaceAll("h","")
                .replaceAll("i","")
                .replaceAll("j","")
                .replaceAll("k","")
                .replaceAll("l","")
                .replaceAll("m","")
                .replaceAll("n","")
                .replaceAll("o","")
                .replaceAll("p","")
                .replaceAll("q","")
                .replaceAll("r","")
                .replaceAll("s","")
                .replaceAll("t","")
                .replaceAll("u","")
                .replaceAll("v","")
                .replaceAll("w","")
                .replaceAll("x","")
                .replaceAll("y","")
                .replaceAll("z","")
                .replaceAll(" ","")
                .replaceAll("\n","")

                .length()!=0
                ) {
            Toast.makeText(this, "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
        }

        else if (input.getText().toString().indexOf("  ")!=-1) {
            Toast.makeText(getApplicationContext(), "Multiple spaces not supported.", Toast.LENGTH_SHORT).show();
        }

        else {

            int i = 0;

            String addextraspace = null;

            String addsplit = null;

            CharSequence convertsequence = BuildConfig.FLAVOR;

            for (String splitline : inputString.split("\n")) {
                addextraspace = "⁞ " + splitline + " ⁞";//for separator
                addsplit = addextraspace.replaceAll(" ", "⁞⁞⁞ ");//for separator
                splitline = addsplit;
                convertsequence = (convertsequence + new StringBuilder(splitline).toString()) + "\n";
            }
            i++;

            String addextralinebreak = convertsequence+"⁞ ⁞⁞ ⁞⁞⁞";
            String removeextralinebreak = addextralinebreak.replaceAll("\n⁞ ⁞⁞ ⁞⁞⁞","");

            String convert = removeextralinebreak
                    .replaceAll("⁞ ⁞⁞⁞ ", "")//for separator
                    .replaceAll("⁞⁞⁞  ⁞", "")//for separator
                    .replaceAll("⁞ ", "")//for separator
                    .replaceAll(" ⁞", "")//for separator
                    .replaceAll("⁞⁞⁞", "")//for separator
                    .replaceAll("⁞⁞", "⁞/ ⁞")//this is the space separator
                    .replaceAll("a", "⁞AAAAA ")
                    .replaceAll("b", "⁞AAAAa ")
                    .replaceAll("c", "⁞AAAaa ")
                    .replaceAll("d", "⁞AAaaa ")
                    .replaceAll("e", "⁞Aaaaa ")
                    .replaceAll("f", "⁞aaaaa ")
                    .replaceAll("g", "⁞aaaaA ")
                    .replaceAll("h", "⁞aaaAA ")
                    .replaceAll("i", "⁞aaAAA ")
                    .replaceAll("j", "⁞aAAAA ")
                    .replaceAll("k", "⁞AAAaA ")
                    .replaceAll("l", "⁞AAaaA ")
                    .replaceAll("m", "⁞AaaaA ")
                    .replaceAll("n", "⁞aaaAa ")
                    .replaceAll("o", "⁞aaAAa ")
                    .replaceAll("p", "⁞aAAAa ")
                    .replaceAll("q", "⁞AaAAA ")
                    .replaceAll("r", "⁞AaaAA ")
                    .replaceAll("s", "⁞aAaaa ")
                    .replaceAll("t", "⁞aAAaa ")
                    .replaceAll("u", "⁞AaAaA ")
                    .replaceAll("v", "⁞aAaAa ")
                    .replaceAll("w", "⁞AAaAA ")
                    .replaceAll("x", "⁞aaAaa ")
                    .replaceAll("y", "⁞AaaAa ")
                    .replaceAll("z", "⁞aAAaA ")
                    .replaceAll("⁞", "")//this is to remove extra characters
                    ;

            outputString = convert;

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    public void acecipherdecrypt(){
        /*inputString = input.getText().toString().replaceAll("/", " / ").replaceAll("  /  ", " / ");*/
        inputString = input.getText().toString().replaceAll("  /  ", "/").replaceAll(" /  ", "/").replaceAll("  / ", "/").replaceAll(" / ", "/").replaceAll("/ ", "/").replaceAll(" /", "/").replaceAll("/", " / ");

        int i = 0;

        String addextraspace = null;

        String addsplit = null;

        String newaddextraspace = null;

        String newaddsplit = null;

        CharSequence convertsequence = BuildConfig.FLAVOR;

        CharSequence newconvertsequence = BuildConfig.FLAVOR;

        CharSequence codesequence = BuildConfig.FLAVOR;

        CharSequence chkcodesequence = BuildConfig.FLAVOR;

        for (String splitline : inputString.split("\n")) {
            addextraspace = " " + splitline + " ";//for separator
            addsplit = addextraspace;//for separator
            splitline = addsplit;
            convertsequence = (convertsequence + new StringBuilder(splitline).toString())+"\n";
        }
        i++;

        String addextralinebreak = convertsequence+"⁞ ⁞⁞ ⁞⁞⁞";
        String removeextralinebreak = addextralinebreak.replaceAll("\n⁞ ⁞⁞ ⁞⁞⁞","");
        String convertlinebreak = removeextralinebreak.replaceAll("\n","⁞ᴥ⁞");
        String addsequenceseparator = convertlinebreak.replaceAll("  ", "\n⁞").replaceAll(" ", "\n⁞");

        for (String newsplitline : addsequenceseparator.split("\n")) {
            newaddextraspace = "⁞" + newsplitline + "⁞";//for separator
            newaddsplit = newaddextraspace;//for separator
            newsplitline = newaddsplit;
            newconvertsequence = (newconvertsequence + new StringBuilder(newsplitline).toString())+"\n";
        }
        i++;

        for (String chksplitword : newconvertsequence.toString().split("\n")) {
            chkcodesequence = (chkcodesequence + new StringBuilder(chksplitword).toString()
                    .replaceAll("⁞AAAAA⁞", "⁞⁞")
                    .replaceAll("⁞AAAAa⁞", "⁞⁞")
                    .replaceAll("⁞AAAaa⁞", "⁞⁞")
                    .replaceAll("⁞AAaaa⁞", "⁞⁞")
                    .replaceAll("⁞Aaaaa⁞", "⁞⁞")
                    .replaceAll("⁞aaaaa⁞", "⁞⁞")
                    .replaceAll("⁞aaaaA⁞", "⁞⁞")
                    .replaceAll("⁞aaaAA⁞", "⁞⁞")
                    .replaceAll("⁞aaAAA⁞", "⁞⁞")
                    .replaceAll("⁞aAAAA⁞", "⁞⁞")
                    .replaceAll("⁞AAAaA⁞", "⁞⁞")
                    .replaceAll("⁞AAaaA⁞", "⁞⁞")
                    .replaceAll("⁞AaaaA⁞", "⁞⁞")
                    .replaceAll("⁞aaaAa⁞", "⁞⁞")
                    .replaceAll("⁞aaAAa⁞", "⁞⁞")
                    .replaceAll("⁞aAAAa⁞", "⁞⁞")
                    .replaceAll("⁞AaAAA⁞", "⁞⁞")
                    .replaceAll("⁞AaaAA⁞", "⁞⁞")
                    .replaceAll("⁞aAaaa⁞", "⁞⁞")
                    .replaceAll("⁞aAAaa⁞", "⁞⁞")
                    .replaceAll("⁞AaAaA⁞", "⁞⁞")
                    .replaceAll("⁞aAaAa⁞", "⁞⁞")
                    .replaceAll("⁞AAaAA⁞", "⁞⁞")
                    .replaceAll("⁞aaAaa⁞", "⁞⁞")
                    .replaceAll("⁞AaaAa⁞", "⁞⁞")
                    .replaceAll("⁞aAAaA⁞", "⁞⁞")
                    .replaceAll(" ", "")
                    .replaceAll("/", "⁞ ⁞")//separator
                    .replaceAll("⁞ᴥ⁞", "⁞\n⁞")//to add linebreak
                    .replaceAll("⁞", "")//this is to remove extra characters

                    .replaceAll(" ","")
                    .replaceAll("\n","")
            );
        }
        i++;

        String chk4unsprtdchar = chkcodesequence.toString();

        String chkmultipleseparator = "⁞"+input.getText().toString()+"⁞";

        if(chk4unsprtdchar.length()!=0){
            Toast.makeText(this, "Not a valid code.", Toast.LENGTH_SHORT).show();
        }
        else if(input.getText().toString().replaceAll(" ","").indexOf("//")!=-1||input.getText().toString().indexOf("/ /")!=-1){
            Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
        }
        //Cross Code
        else if(chkmultipleseparator.indexOf("/⁞")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
        else if(chkmultipleseparator.indexOf("/\n")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
        else if(chkmultipleseparator.indexOf("\n/")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
        else if(chkmultipleseparator.indexOf("⁞/")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
        //Cross Code
        else{
            for (String splitword : newconvertsequence.toString().split("\n")) {
                codesequence = (codesequence + new StringBuilder(splitword).toString()
                        .replaceAll("⁞AAAAA⁞", "⁞a⁞")
                        .replaceAll("⁞AAAAa⁞", "⁞b⁞")
                        .replaceAll("⁞AAAaa⁞", "⁞c⁞")
                        .replaceAll("⁞AAaaa⁞", "⁞d⁞")
                        .replaceAll("⁞Aaaaa⁞", "⁞e⁞")
                        .replaceAll("⁞aaaaa⁞", "⁞f⁞")
                        .replaceAll("⁞aaaaA⁞", "⁞g⁞")
                        .replaceAll("⁞aaaAA⁞", "⁞h⁞")
                        .replaceAll("⁞aaAAA⁞", "⁞i⁞")
                        .replaceAll("⁞aAAAA⁞", "⁞j⁞")
                        .replaceAll("⁞AAAaA⁞", "⁞k⁞")
                        .replaceAll("⁞AAaaA⁞", "⁞l⁞")
                        .replaceAll("⁞AaaaA⁞", "⁞m⁞")
                        .replaceAll("⁞aaaAa⁞", "⁞n⁞")
                        .replaceAll("⁞aaAAa⁞", "⁞o⁞")
                        .replaceAll("⁞aAAAa⁞", "⁞p⁞")
                        .replaceAll("⁞AaAAA⁞", "⁞q⁞")
                        .replaceAll("⁞AaaAA⁞", "⁞r⁞")
                        .replaceAll("⁞aAaaa⁞", "⁞s⁞")
                        .replaceAll("⁞aAAaa⁞", "⁞t⁞")
                        .replaceAll("⁞AaAaA⁞", "⁞u⁞")
                        .replaceAll("⁞aAaAa⁞", "⁞v⁞")
                        .replaceAll("⁞AAaAA⁞", "⁞w⁞")
                        .replaceAll("⁞aaAaa⁞", "⁞x⁞")
                        .replaceAll("⁞AaaAa⁞", "⁞y⁞")
                        .replaceAll("⁞aAAaA⁞", "⁞z⁞")
                        .replaceAll(" ", "")
                        .replaceAll("/", "⁞ ⁞")//separator
                        .replaceAll("⁞ᴥ⁞", "⁞\n⁞")//to add linebreak
                        .replaceAll("⁞", "")//this is to remove extra characters
                );
            }
            i++;

            outputString = codesequence.toString();

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Decrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
