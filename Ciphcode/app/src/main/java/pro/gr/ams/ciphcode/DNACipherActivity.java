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

public class DNACipherActivity extends AppCompatActivity {

    String codename = "DNA Cipher";
    String codesample = "ATC AAG TAC  CTA CAG TAG GCA ACT ATG";

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
                    dnacipherencrypt();
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
                //DNA Cipher
                /*else if (input.getText().toString().indexOf("   ")!=-1||input.getText().toString().indexOf("\n\n\n")!=-1) {
                    Toast.makeText(getApplicationContext(), "Too much spaces detected.", Toast.LENGTH_SHORT).show();
                }*/
                //DNA Cipher
                /*else if (input.getText().toString().indexOf("  ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Multiple spaces not supported.", Toast.LENGTH_SHORT).show();
                }*/
                else {
                    dnacipherdecrypt();
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

    public void dnacipherencrypt(){
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

                .replaceAll("0","")
                .replaceAll("1","")
                .replaceAll("2","")
                .replaceAll("3","")
                .replaceAll("4","")
                .replaceAll("5","")
                .replaceAll("6","")
                .replaceAll("7","")
                .replaceAll("8","")
                .replaceAll("9","")

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
                    //DNA Cipher
                    .replaceAll("⁞⁞", "⁞ ⁞")//this is the space separator
                    //DNA Cipher

                    //DNA Cipher
                    .replaceAll("0", "⁞zeᴥ ")
                    .replaceAll("1", "⁞onᴥ ")
                    .replaceAll("2", "⁞twᴥ ")
                    .replaceAll("3", "⁞thᴥ ")
                    .replaceAll("4", "⁞foᴥ ")
                    .replaceAll("5", "⁞fiᴥ ")
                    .replaceAll("6", "⁞siᴥ ")
                    .replaceAll("7", "⁞seᴥ ")
                    .replaceAll("8", "⁞eiᴥ ")
                    .replaceAll("9", "⁞niᴥ ")

                    .replaceAll("⁞zeᴥ ", "⁞27 ")
                    .replaceAll("⁞onᴥ ", "⁞28 ")
                    .replaceAll("⁞twᴥ ", "⁞29 ")
                    .replaceAll("⁞thᴥ ", "⁞30 ")
                    .replaceAll("⁞foᴥ ", "⁞31 ")
                    .replaceAll("⁞fiᴥ ", "⁞32 ")
                    .replaceAll("⁞siᴥ ", "⁞33 ")
                    .replaceAll("⁞seᴥ ", "⁞34 ")
                    .replaceAll("⁞eiᴥ ", "⁞35 ")
                    .replaceAll("⁞niᴥ ", "⁞36 ")
                    //DNA Cipher

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
                    .replaceAll("01", "TAC")
                    .replaceAll("02", "TCA")
                    .replaceAll("03", "CTA")
                    .replaceAll("04", "ATC")
                    .replaceAll("05", "ACT")
                    .replaceAll("06", "CAT")
                    .replaceAll("07", "GAC")
                    .replaceAll("08", "GCA")
                    .replaceAll("09", "CAG")
                    .replaceAll("10", "ACG")
                    .replaceAll("11", "AGC")
                    .replaceAll("12", "CGA")
                    .replaceAll("13", "AGA")
                    .replaceAll("14", "AAG")
                    .replaceAll("15", "GAA")
                    .replaceAll("16", "TAG")
                    .replaceAll("17", "TGA")
                    .replaceAll("18", "ATG")
                    .replaceAll("19", "GAT")
                    .replaceAll("20", "GTA")
                    .replaceAll("21", "AGT")
                    .replaceAll("22", "AGU")
                    .replaceAll("23", "GAU")
                    .replaceAll("24", "CGU")
                    .replaceAll("25", "UGA")
                    .replaceAll("26", "CAU")
                    .replaceAll("27", "GUC")
                    .replaceAll("28", "AUG")
                    .replaceAll("29", "ATD")
                    .replaceAll("30", "CUG")
                    .replaceAll("31", "TAN")
                    .replaceAll("32", "UCG")
                    .replaceAll("33", "CGT")
                    .replaceAll("34", "AUC")
                    .replaceAll("35", "GCO")
                    .replaceAll("36", "UGC")
                    ;

            outputString = convert;

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    public void dnacipherdecrypt(){
        //DNA Cipher
        inputString = input.getText().toString().toUpperCase().replaceAll("  "," / ")
                .replaceAll("/  /  /  /  /  /  /  /  /","/")
                .replaceAll("/  /  /  /  /  /  /  /","/")
                .replaceAll("/  /  /  /  /  /  /","/")
                .replaceAll("/  /  /  /  /  /","/")
                .replaceAll("/  /  /  /  /","/")
                .replaceAll("/  /  /  /","/")
                .replaceAll("/  /  /","/")
                .replaceAll("/  /","/")
                .replaceAll("/  /","/")
                .replaceAll("/  /  /","/")
                .replaceAll("/  /  /  /","/")
                .replaceAll("/  /  /  /  /","/")
                .replaceAll("/  /  /  /  /  /","/")
                .replaceAll("/  /  /  /  /  /  /","/")
                .replaceAll("/  /  /  /  /  /  /  /","/")
                .replaceAll("/  /  /  /  /  /  /  /  /","/")
        ;
        //DNA Cipher

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
                    .replaceAll("⁞TAC⁞", "⁞⁞")
                    .replaceAll("⁞TCA⁞", "⁞⁞")
                    .replaceAll("⁞CTA⁞", "⁞⁞")
                    .replaceAll("⁞ATC⁞", "⁞⁞")
                    .replaceAll("⁞ACT⁞", "⁞⁞")
                    .replaceAll("⁞CAT⁞", "⁞⁞")
                    .replaceAll("⁞GAC⁞", "⁞⁞")
                    .replaceAll("⁞GCA⁞", "⁞⁞")
                    .replaceAll("⁞CAG⁞", "⁞⁞")
                    .replaceAll("⁞ACG⁞", "⁞⁞")
                    .replaceAll("⁞AGC⁞", "⁞⁞")
                    .replaceAll("⁞CGA⁞", "⁞⁞")
                    .replaceAll("⁞AGA⁞", "⁞⁞")
                    .replaceAll("⁞AAG⁞", "⁞⁞")
                    .replaceAll("⁞GAA⁞", "⁞⁞")
                    .replaceAll("⁞TAG⁞", "⁞⁞")
                    .replaceAll("⁞TGA⁞", "⁞⁞")
                    .replaceAll("⁞ATG⁞", "⁞⁞")
                    .replaceAll("⁞GAT⁞", "⁞⁞")
                    .replaceAll("⁞GTA⁞", "⁞⁞")
                    .replaceAll("⁞AGT⁞", "⁞⁞")
                    .replaceAll("⁞AGU⁞", "⁞⁞")
                    .replaceAll("⁞GAU⁞", "⁞⁞")
                    .replaceAll("⁞CGU⁞", "⁞⁞")
                    .replaceAll("⁞UGA⁞", "⁞⁞")
                    .replaceAll("⁞CAU⁞", "⁞⁞")
                    .replaceAll("⁞GUC⁞", "⁞⁞")
                    .replaceAll("⁞AUG⁞", "⁞⁞")
                    .replaceAll("⁞ATD⁞", "⁞⁞")
                    .replaceAll("⁞CUG⁞", "⁞⁞")
                    .replaceAll("⁞TAN⁞", "⁞⁞")
                    .replaceAll("⁞UCG⁞", "⁞⁞")
                    .replaceAll("⁞CGT⁞", "⁞⁞")
                    .replaceAll("⁞AUC⁞", "⁞⁞")
                    .replaceAll("⁞GCO⁞", "⁞⁞")
                    .replaceAll("⁞UGC⁞", "⁞⁞")
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
                        .replaceAll("⁞TAC⁞", "⁞a⁞")
                        .replaceAll("⁞TCA⁞", "⁞b⁞")
                        .replaceAll("⁞CTA⁞", "⁞c⁞")
                        .replaceAll("⁞ATC⁞", "⁞d⁞")
                        .replaceAll("⁞ACT⁞", "⁞e⁞")
                        .replaceAll("⁞CAT⁞", "⁞f⁞")
                        .replaceAll("⁞GAC⁞", "⁞g⁞")
                        .replaceAll("⁞GCA⁞", "⁞h⁞")
                        .replaceAll("⁞CAG⁞", "⁞i⁞")
                        .replaceAll("⁞ACG⁞", "⁞j⁞")
                        .replaceAll("⁞AGC⁞", "⁞k⁞")
                        .replaceAll("⁞CGA⁞", "⁞l⁞")
                        .replaceAll("⁞AGA⁞", "⁞m⁞")
                        .replaceAll("⁞AAG⁞", "⁞n⁞")
                        .replaceAll("⁞GAA⁞", "⁞o⁞")
                        .replaceAll("⁞TAG⁞", "⁞p⁞")
                        .replaceAll("⁞TGA⁞", "⁞q⁞")
                        .replaceAll("⁞ATG⁞", "⁞r⁞")
                        .replaceAll("⁞GAT⁞", "⁞s⁞")
                        .replaceAll("⁞GTA⁞", "⁞t⁞")
                        .replaceAll("⁞AGT⁞", "⁞u⁞")
                        .replaceAll("⁞AGU⁞", "⁞v⁞")
                        .replaceAll("⁞GAU⁞", "⁞w⁞")
                        .replaceAll("⁞CGU⁞", "⁞x⁞")
                        .replaceAll("⁞UGA⁞", "⁞y⁞")
                        .replaceAll("⁞CAU⁞", "⁞z⁞")
                        .replaceAll("⁞GUC⁞", "⁞0⁞")
                        .replaceAll("⁞AUG⁞", "⁞1⁞")
                        .replaceAll("⁞ATD⁞", "⁞2⁞")
                        .replaceAll("⁞CUG⁞", "⁞3⁞")
                        .replaceAll("⁞TAN⁞", "⁞4⁞")
                        .replaceAll("⁞UCG⁞", "⁞5⁞")
                        .replaceAll("⁞CGT⁞", "⁞6⁞")
                        .replaceAll("⁞AUC⁞", "⁞7⁞")
                        .replaceAll("⁞GCO⁞", "⁞8⁞")
                        .replaceAll("⁞UGC⁞", "⁞9⁞")
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
