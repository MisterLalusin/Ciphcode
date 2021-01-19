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

public class PentominoCodesActivity extends AppCompatActivity {

    String codename = "Pentomino Codes";
    String codesample = "N:2 P:1 I:2 V:2 L:2 F:2 W:1 I:2 L:2 / L:1 L:2 N:1 P:1 U:2";

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
                    pentominocodesencrypt();
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
                    pentominocodesdecrypt();
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

    public void pentominocodesencrypt(){
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
                    .replaceAll("a", "⁞F:1 ")
                    .replaceAll("b", "⁞I:1 ")
                    .replaceAll("c", "⁞L:1 ")
                    .replaceAll("d", "⁞N:1 ")
                    .replaceAll("e", "⁞P:1 ")
                    .replaceAll("f", "⁞T:1 ")
                    .replaceAll("g", "⁞U:1 ")
                    .replaceAll("h", "⁞V:1 ")
                    .replaceAll("i", "⁞W:1 ")
                    .replaceAll("j", "⁞X:1 ")
                    .replaceAll("k", "⁞Y:1 ")
                    .replaceAll("l", "⁞Z:1 ")
                    .replaceAll("m", "⁞F:2 ")
                    .replaceAll("n", "⁞I:2 ")
                    .replaceAll("o", "⁞L:2 ")
                    .replaceAll("p", "⁞N:2 ")
                    .replaceAll("q", "⁞P:2 ")
                    .replaceAll("r", "⁞T:2 ")
                    .replaceAll("s", "⁞U:2 ")
                    .replaceAll("t", "⁞V:2 ")
                    .replaceAll("u", "⁞W:2 ")
                    .replaceAll("v", "⁞X:2 ")
                    .replaceAll("w", "⁞Y:2 ")
                    .replaceAll("x", "⁞Z:2 ")
                    .replaceAll("y", "⁞F:3 ")
                    .replaceAll("z", "⁞I:3 ")
                    .replaceAll("⁞", "")//this is to remove extra characters
                    ;

            outputString = convert;

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    public void pentominocodesdecrypt(){
        inputString = input.getText().toString().toUpperCase().replaceAll("  /  ", "/").replaceAll(" /  ", "/").replaceAll("  / ", "/").replaceAll(" / ", "/").replaceAll("/ ", "/").replaceAll(" /", "/").replaceAll("/", " / ");

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
                    .replaceAll("⁞F:1⁞", "⁞⁞")
                    .replaceAll("⁞I:1⁞", "⁞⁞")
                    .replaceAll("⁞L:1⁞", "⁞⁞")
                    .replaceAll("⁞N:1⁞", "⁞⁞")
                    .replaceAll("⁞P:1⁞", "⁞⁞")
                    .replaceAll("⁞T:1⁞", "⁞⁞")
                    .replaceAll("⁞U:1⁞", "⁞⁞")
                    .replaceAll("⁞V:1⁞", "⁞⁞")
                    .replaceAll("⁞W:1⁞", "⁞⁞")
                    .replaceAll("⁞X:1⁞", "⁞⁞")
                    .replaceAll("⁞Y:1⁞", "⁞⁞")
                    .replaceAll("⁞Z:1⁞", "⁞⁞")
                    .replaceAll("⁞F:2⁞", "⁞⁞")
                    .replaceAll("⁞I:2⁞", "⁞⁞")
                    .replaceAll("⁞L:2⁞", "⁞⁞")
                    .replaceAll("⁞N:2⁞", "⁞⁞")
                    .replaceAll("⁞P:2⁞", "⁞⁞")
                    .replaceAll("⁞T:2⁞", "⁞⁞")
                    .replaceAll("⁞U:2⁞", "⁞⁞")
                    .replaceAll("⁞V:2⁞", "⁞⁞")
                    .replaceAll("⁞W:2⁞", "⁞⁞")
                    .replaceAll("⁞X:2⁞", "⁞⁞")
                    .replaceAll("⁞Y:2⁞", "⁞⁞")
                    .replaceAll("⁞Z:2⁞", "⁞⁞")
                    .replaceAll("⁞F:3⁞", "⁞⁞")
                    .replaceAll("⁞I:3⁞", "⁞⁞")
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
                        .replaceAll("⁞F:1⁞", "⁞a⁞")
                        .replaceAll("⁞I:1⁞", "⁞b⁞")
                        .replaceAll("⁞L:1⁞", "⁞c⁞")
                        .replaceAll("⁞N:1⁞", "⁞d⁞")
                        .replaceAll("⁞P:1⁞", "⁞e⁞")
                        .replaceAll("⁞T:1⁞", "⁞f⁞")
                        .replaceAll("⁞U:1⁞", "⁞g⁞")
                        .replaceAll("⁞V:1⁞", "⁞h⁞")
                        .replaceAll("⁞W:1⁞", "⁞i⁞")
                        .replaceAll("⁞X:1⁞", "⁞j⁞")
                        .replaceAll("⁞Y:1⁞", "⁞k⁞")
                        .replaceAll("⁞Z:1⁞", "⁞l⁞")
                        .replaceAll("⁞F:2⁞", "⁞m⁞")
                        .replaceAll("⁞I:2⁞", "⁞n⁞")
                        .replaceAll("⁞L:2⁞", "⁞o⁞")
                        .replaceAll("⁞N:2⁞", "⁞p⁞")
                        .replaceAll("⁞P:2⁞", "⁞q⁞")
                        .replaceAll("⁞T:2⁞", "⁞r⁞")
                        .replaceAll("⁞U:2⁞", "⁞s⁞")
                        .replaceAll("⁞V:2⁞", "⁞t⁞")
                        .replaceAll("⁞W:2⁞", "⁞u⁞")
                        .replaceAll("⁞X:2⁞", "⁞v⁞")
                        .replaceAll("⁞Y:2⁞", "⁞w⁞")
                        .replaceAll("⁞Z:2⁞", "⁞x⁞")
                        .replaceAll("⁞F:3⁞", "⁞y⁞")
                        .replaceAll("⁞I:3⁞", "⁞z⁞")
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
