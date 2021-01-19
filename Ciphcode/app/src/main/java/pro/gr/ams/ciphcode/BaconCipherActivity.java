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

public class BaconCipherActivity extends AppCompatActivity {

    String codename = "Bacon Cipher";
    String codesample = "aaaab aaaaa aaaba abbba abbab / aaaba abaaa abbbb aabbb aabaa baaab";

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
                    baconcipherencrypt();
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
                    baconcipherdecrypt();
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

    public void baconcipherencrypt(){
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

            String converttonumbers = removeextralinebreak
                    .replaceAll("⁞ ⁞⁞⁞ ", "")//for separator
                    .replaceAll("⁞⁞⁞  ⁞", "")//for separator
                    .replaceAll("⁞ ", "")//for separator
                    .replaceAll(" ⁞", "")//for separator
                    .replaceAll("⁞⁞⁞", "")//for separator
                    .replaceAll("⁞⁞", "⁞/ ⁞")//this is the space separator
                    .replaceAll("a", "⁞01 ")
                    .replaceAll("b", "⁞02 ")
                    .replaceAll("c", "⁞03 ")
                    .replaceAll("d", "⁞04 ")
                    .replaceAll("e", "⁞05 ")
                    .replaceAll("f", "⁞06 ")
                    .replaceAll("g", "⁞07 ")
                    .replaceAll("h", "⁞08 ")
                    .replaceAll("i", "⁞09 ")
                    .replaceAll("j", "⁞10 ")
                    .replaceAll("k", "⁞11 ")
                    .replaceAll("l", "⁞12 ")
                    .replaceAll("m", "⁞13 ")
                    .replaceAll("n", "⁞14 ")
                    .replaceAll("o", "⁞15 ")
                    .replaceAll("p", "⁞16 ")
                    .replaceAll("q", "⁞17 ")
                    .replaceAll("r", "⁞18 ")
                    .replaceAll("s", "⁞19 ")
                    .replaceAll("t", "⁞20 ")
                    .replaceAll("u", "⁞21 ")
                    .replaceAll("v", "⁞22 ")
                    .replaceAll("w", "⁞23 ")
                    .replaceAll("x", "⁞24 ")
                    .replaceAll("y", "⁞25 ")
                    .replaceAll("z", "⁞26 ")
                    .replaceAll("⁞", "")//this is to remove extra characters
                    ;

            String convert = converttonumbers
                    .replaceAll("01", "aaaaa")
                    .replaceAll("02", "aaaab")
                    .replaceAll("03", "aaaba")
                    .replaceAll("04", "aaabb")
                    .replaceAll("05", "aabaa")
                    .replaceAll("06", "aabab")
                    .replaceAll("07", "aabba")
                    .replaceAll("08", "aabbb")
                    .replaceAll("09", "abaaa")
                    .replaceAll("10", "abaab")
                    .replaceAll("11", "ababa")
                    .replaceAll("12", "ababb")
                    .replaceAll("13", "abbaa")
                    .replaceAll("14", "abbab")
                    .replaceAll("15", "abbba")
                    .replaceAll("16", "abbbb")
                    .replaceAll("17", "baaaa")
                    .replaceAll("18", "baaab")
                    .replaceAll("19", "baaba")
                    .replaceAll("20", "baabb")
                    .replaceAll("21", "babaa")
                    .replaceAll("22", "babab")
                    .replaceAll("23", "babba")
                    .replaceAll("24", "babbb")
                    .replaceAll("25", "bbaaa")
                    .replaceAll("26", "bbaab")
                    ;

            outputString = convert;

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    public void baconcipherdecrypt(){
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
                    .replaceAll("⁞aaaaa⁞", "⁞⁞")
                    .replaceAll("⁞aaaab⁞", "⁞⁞")
                    .replaceAll("⁞aaaba⁞", "⁞⁞")
                    .replaceAll("⁞aaabb⁞", "⁞⁞")
                    .replaceAll("⁞aabaa⁞", "⁞⁞")
                    .replaceAll("⁞aabab⁞", "⁞⁞")
                    .replaceAll("⁞aabba⁞", "⁞⁞")
                    .replaceAll("⁞aabbb⁞", "⁞⁞")
                    .replaceAll("⁞abaaa⁞", "⁞⁞")
                    .replaceAll("⁞abaab⁞", "⁞⁞")
                    .replaceAll("⁞ababa⁞", "⁞⁞")
                    .replaceAll("⁞ababb⁞", "⁞⁞")
                    .replaceAll("⁞abbaa⁞", "⁞⁞")
                    .replaceAll("⁞abbab⁞", "⁞⁞")
                    .replaceAll("⁞abbba⁞", "⁞⁞")
                    .replaceAll("⁞abbbb⁞", "⁞⁞")
                    .replaceAll("⁞baaaa⁞", "⁞⁞")
                    .replaceAll("⁞baaab⁞", "⁞⁞")
                    .replaceAll("⁞baaba⁞", "⁞⁞")
                    .replaceAll("⁞baabb⁞", "⁞⁞")
                    .replaceAll("⁞babaa⁞", "⁞⁞")
                    .replaceAll("⁞babab⁞", "⁞⁞")
                    .replaceAll("⁞babba⁞", "⁞⁞")
                    .replaceAll("⁞babbb⁞", "⁞⁞")
                    .replaceAll("⁞bbaaa⁞", "⁞⁞")
                    .replaceAll("⁞bbaab⁞", "⁞⁞")
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
        /*//Cross Code
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
        //Cross Code*/
        else{
            for (String splitword : newconvertsequence.toString().split("\n")) {
                codesequence = (codesequence + new StringBuilder(splitword).toString()
                        .replaceAll("⁞aaaaa⁞", "⁞a⁞")
                        .replaceAll("⁞aaaab⁞", "⁞b⁞")
                        .replaceAll("⁞aaaba⁞", "⁞c⁞")
                        .replaceAll("⁞aaabb⁞", "⁞d⁞")
                        .replaceAll("⁞aabaa⁞", "⁞e⁞")
                        .replaceAll("⁞aabab⁞", "⁞f⁞")
                        .replaceAll("⁞aabba⁞", "⁞g⁞")
                        .replaceAll("⁞aabbb⁞", "⁞h⁞")
                        .replaceAll("⁞abaaa⁞", "⁞i⁞")
                        .replaceAll("⁞abaab⁞", "⁞j⁞")
                        .replaceAll("⁞ababa⁞", "⁞k⁞")
                        .replaceAll("⁞ababb⁞", "⁞l⁞")
                        .replaceAll("⁞abbaa⁞", "⁞m⁞")
                        .replaceAll("⁞abbab⁞", "⁞n⁞")
                        .replaceAll("⁞abbba⁞", "⁞o⁞")
                        .replaceAll("⁞abbbb⁞", "⁞p⁞")
                        .replaceAll("⁞baaaa⁞", "⁞q⁞")
                        .replaceAll("⁞baaab⁞", "⁞r⁞")
                        .replaceAll("⁞baaba⁞", "⁞s⁞")
                        .replaceAll("⁞baabb⁞", "⁞t⁞")
                        .replaceAll("⁞babaa⁞", "⁞u⁞")
                        .replaceAll("⁞babab⁞", "⁞v⁞")
                        .replaceAll("⁞babba⁞", "⁞w⁞")
                        .replaceAll("⁞babbb⁞", "⁞x⁞")
                        .replaceAll("⁞bbaaa⁞", "⁞y⁞")
                        .replaceAll("⁞bbaab⁞", "⁞z⁞")
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
