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

public class CompoundCodeActivity extends AppCompatActivity {

    String codename = "Compound Code";
    String codesample = "SCN Br AsO2 ZnO2 Br NO2 C2O4 HSO4  SCN Br HSO4 BO3";

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
                    compoundcodeencrypt();
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
                    compoundcodedecrypt();
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

    public void compoundcodeencrypt(){
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
                    //DNA Cipher
                    .replaceAll("⁞⁞", "⁞ ⁞")//this is the space separator
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
                    .replaceAll("01", "SnO2")
                    .replaceAll("02", "MnO4")
                    .replaceAll("03", "SCN")
                    .replaceAll("04", "HSO4")
                    .replaceAll("05", "BO3")
                    .replaceAll("06", "C2H3O2")
                    .replaceAll("07", "AsO3")
                    .replaceAll("08", "BrO3")
                    .replaceAll("09", "MoO4")
                    .replaceAll("10", "Fe(CN)6")
                    .replaceAll("11", "AS2O7")
                    .replaceAll("12", "HCOO")
                    .replaceAll("13", "AsO2")
                    .replaceAll("14", "C2O4")
                    .replaceAll("15", "Br")
                    .replaceAll("16", "ZnO2")
                    .replaceAll("17", "Ag(CN)2")
                    .replaceAll("18", "SiO4")
                    .replaceAll("19", "P2O7")
                    .replaceAll("20", "PbO2")
                    .replaceAll("21", "NO2")
                    .replaceAll("22", "NO3")
                    .replaceAll("23", "HCO3")
                    .replaceAll("24", "H2PO4")
                    .replaceAll("25", "S4O6")
                    .replaceAll("26", "Cr2O7")
                    ;

            outputString = convert;

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    public void compoundcodedecrypt(){
        //Brackets Code
        inputString = input.getText().toString().replaceAll("  "," / ")
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
        //Brackets Code

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
                    .replaceAll("⁞SnO2⁞", "⁞⁞")
                    .replaceAll("⁞MnO4⁞", "⁞⁞")
                    .replaceAll("⁞SCN⁞", "⁞⁞")
                    .replaceAll("⁞HSO4⁞", "⁞⁞")
                    .replaceAll("⁞BO3⁞", "⁞⁞")
                    .replaceAll("⁞C2H3O2⁞", "⁞⁞")
                    .replaceAll("⁞AsO3⁞", "⁞⁞")
                    .replaceAll("⁞BrO3⁞", "⁞⁞")
                    .replaceAll("⁞MoO4⁞", "⁞⁞")
                    .replaceAll("⁞Fe\\(CN\\)6⁞", "⁞⁞")
                    .replaceAll("⁞AS2O7⁞", "⁞⁞")
                    .replaceAll("⁞HCOO⁞", "⁞⁞")
                    .replaceAll("⁞AsO2⁞", "⁞⁞")
                    .replaceAll("⁞C2O4⁞", "⁞⁞")
                    .replaceAll("⁞Br⁞", "⁞⁞")
                    .replaceAll("⁞ZnO2⁞", "⁞⁞")
                    .replaceAll("⁞Ag\\(CN\\)2⁞", "⁞⁞")
                    .replaceAll("⁞SiO4⁞", "⁞⁞")
                    .replaceAll("⁞P2O7⁞", "⁞⁞")
                    .replaceAll("⁞PbO2⁞", "⁞⁞")
                    .replaceAll("⁞NO2⁞", "⁞⁞")
                    .replaceAll("⁞NO3⁞", "⁞⁞")
                    .replaceAll("⁞HCO3⁞", "⁞⁞")
                    .replaceAll("⁞H2PO4⁞", "⁞⁞")
                    .replaceAll("⁞S4O6⁞", "⁞⁞")
                    .replaceAll("⁞Cr2O7⁞", "⁞⁞")
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
                        .replaceAll("⁞SnO2⁞", "⁞a⁞")
                        .replaceAll("⁞MnO4⁞", "⁞b⁞")
                        .replaceAll("⁞SCN⁞", "⁞c⁞")
                        .replaceAll("⁞HSO4⁞", "⁞d⁞")
                        .replaceAll("⁞BO3⁞", "⁞e⁞")
                        .replaceAll("⁞C2H3O2⁞", "⁞f⁞")
                        .replaceAll("⁞AsO3⁞", "⁞g⁞")
                        .replaceAll("⁞BrO3⁞", "⁞h⁞")
                        .replaceAll("⁞MoO4⁞", "⁞i⁞")
                        .replaceAll("⁞Fe\\(CN\\)6⁞", "⁞j⁞")
                        .replaceAll("⁞AS2O7⁞", "⁞k⁞")
                        .replaceAll("⁞HCOO⁞", "⁞l⁞")
                        .replaceAll("⁞AsO2⁞", "⁞m⁞")
                        .replaceAll("⁞C2O4⁞", "⁞n⁞")
                        .replaceAll("⁞Br⁞", "⁞o⁞")
                        .replaceAll("⁞ZnO2⁞", "⁞p⁞")
                        .replaceAll("⁞Ag\\(CN\\)2⁞", "⁞q⁞")
                        .replaceAll("⁞SiO4⁞", "⁞r⁞")
                        .replaceAll("⁞P2O7⁞", "⁞s⁞")
                        .replaceAll("⁞PbO2⁞", "⁞t⁞")
                        .replaceAll("⁞NO2⁞", "⁞u⁞")
                        .replaceAll("⁞NO3⁞", "⁞v⁞")
                        .replaceAll("⁞HCO3⁞", "⁞w⁞")
                        .replaceAll("⁞H2PO4⁞", "⁞x⁞")
                        .replaceAll("⁞S4O6⁞", "⁞y⁞")
                        .replaceAll("⁞Cr2O7⁞", "⁞z⁞")
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
