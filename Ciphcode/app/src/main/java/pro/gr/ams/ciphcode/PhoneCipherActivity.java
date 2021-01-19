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

public class PhoneCipherActivity extends AppCompatActivity {

    String codename = "Phone Cipher";
    String codesample = "71 42 63 62 32 / 23 43 71 42 32 73";

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
                    phonecipherencrypt();
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
                else if(input.getText().toString().replaceAll(" ","").indexOf("//")!=-1||input.getText().toString().indexOf("/ /")!=-1){
                    Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                }
                else {
                    phonecipherdecrypt();
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

    public void phonecipherencrypt(){
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
                    .replaceAll("a", "⁞21 ")
                    .replaceAll("b", "⁞22 ")
                    .replaceAll("c", "⁞23 ")
                    .replaceAll("d", "⁞31 ")
                    .replaceAll("e", "⁞32 ")
                    .replaceAll("f", "⁞33 ")
                    .replaceAll("g", "⁞41 ")
                    .replaceAll("h", "⁞42 ")
                    .replaceAll("i", "⁞43 ")
                    .replaceAll("j", "⁞51 ")
                    .replaceAll("k", "⁞52 ")
                    .replaceAll("l", "⁞53 ")
                    .replaceAll("m", "⁞61 ")
                    .replaceAll("n", "⁞62 ")
                    .replaceAll("o", "⁞63 ")
                    .replaceAll("p", "⁞71 ")
                    .replaceAll("q", "⁞72 ")
                    .replaceAll("r", "⁞73 ")
                    .replaceAll("s", "⁞74 ")
                    .replaceAll("t", "⁞81 ")
                    .replaceAll("u", "⁞82 ")
                    .replaceAll("v", "⁞83 ")
                    .replaceAll("w", "⁞91 ")
                    .replaceAll("x", "⁞92 ")
                    .replaceAll("y", "⁞93 ")
                    .replaceAll("z", "⁞94 ")
                    .replaceAll("⁞", "")//this is to remove extra characters
                    ;

            outputString = convert;

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    public void phonecipherdecrypt(){
        inputString = input.getText().toString().toLowerCase().replaceAll("  /  ", "/").replaceAll(" /  ", "/").replaceAll("  / ", "/").replaceAll(" / ", "/").replaceAll("/ ", "/").replaceAll(" /", "/").replaceAll("/", " / ");

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
                    .replaceAll("⁞21⁞", "⁞⁞")
                    .replaceAll("⁞22⁞", "⁞⁞")
                    .replaceAll("⁞23⁞", "⁞⁞")
                    .replaceAll("⁞31⁞", "⁞⁞")
                    .replaceAll("⁞32⁞", "⁞⁞")
                    .replaceAll("⁞33⁞", "⁞⁞")
                    .replaceAll("⁞41⁞", "⁞⁞")
                    .replaceAll("⁞42⁞", "⁞⁞")
                    .replaceAll("⁞43⁞", "⁞⁞")
                    .replaceAll("⁞51⁞", "⁞⁞")
                    .replaceAll("⁞52⁞", "⁞⁞")
                    .replaceAll("⁞53⁞", "⁞⁞")
                    .replaceAll("⁞61⁞", "⁞⁞")
                    .replaceAll("⁞62⁞", "⁞⁞")
                    .replaceAll("⁞63⁞", "⁞⁞")
                    .replaceAll("⁞71⁞", "⁞⁞")
                    .replaceAll("⁞72⁞", "⁞⁞")
                    .replaceAll("⁞73⁞", "⁞⁞")
                    .replaceAll("⁞74⁞", "⁞⁞")
                    .replaceAll("⁞81⁞", "⁞⁞")
                    .replaceAll("⁞82⁞", "⁞⁞")
                    .replaceAll("⁞83⁞", "⁞⁞")
                    .replaceAll("⁞91⁞", "⁞⁞")
                    .replaceAll("⁞92⁞", "⁞⁞")
                    .replaceAll("⁞93⁞", "⁞⁞")
                    .replaceAll("⁞94⁞", "⁞⁞")
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
        else if(input.getText().toString().indexOf("//")!=-1||input.getText().toString().indexOf("/ /")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
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
                        .replaceAll("⁞21⁞", "⁞a⁞")
                        .replaceAll("⁞22⁞", "⁞b⁞")
                        .replaceAll("⁞23⁞", "⁞c⁞")
                        .replaceAll("⁞31⁞", "⁞d⁞")
                        .replaceAll("⁞32⁞", "⁞e⁞")
                        .replaceAll("⁞33⁞", "⁞f⁞")
                        .replaceAll("⁞41⁞", "⁞g⁞")
                        .replaceAll("⁞42⁞", "⁞h⁞")
                        .replaceAll("⁞43⁞", "⁞i⁞")
                        .replaceAll("⁞51⁞", "⁞j⁞")
                        .replaceAll("⁞52⁞", "⁞k⁞")
                        .replaceAll("⁞53⁞", "⁞l⁞")
                        .replaceAll("⁞61⁞", "⁞m⁞")
                        .replaceAll("⁞62⁞", "⁞n⁞")
                        .replaceAll("⁞63⁞", "⁞o⁞")
                        .replaceAll("⁞71⁞", "⁞p⁞")
                        .replaceAll("⁞72⁞", "⁞q⁞")
                        .replaceAll("⁞73⁞", "⁞r⁞")
                        .replaceAll("⁞74⁞", "⁞s⁞")
                        .replaceAll("⁞81⁞", "⁞t⁞")
                        .replaceAll("⁞82⁞", "⁞u⁞")
                        .replaceAll("⁞83⁞", "⁞v⁞")
                        .replaceAll("⁞91⁞", "⁞w⁞")
                        .replaceAll("⁞92⁞", "⁞x⁞")
                        .replaceAll("⁞93⁞", "⁞y⁞")
                        .replaceAll("⁞94⁞", "⁞z⁞")
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
