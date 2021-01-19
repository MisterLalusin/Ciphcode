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

public class KennyCodeActivity extends AppCompatActivity {

    String codename = "Kenny Code";
    String codesample = "pmp mpp ppp ppp ffm mmf ppf mpm mpp";

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
                /*else if (input.getText().toString().indexOf("  ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Multiple spaces not supported.", Toast.LENGTH_SHORT).show();
                }*/
                else {
                    kennycodeencrypt();
                }
            }
        });
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().replaceAll("\n","").replaceAll(" ","").length()==0) {
                    Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                }
                else if (input.getText().toString().indexOf("ᴥ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                }
                /*else if (input.getText().toString().indexOf("  ")!=-1||input.getText().toString().indexOf("\n\n\n")!=-1) {
                    Toast.makeText(getApplicationContext(), "Too much spaces detected.", Toast.LENGTH_SHORT).show();
                }*/
                else {
                    kennycodedecrypt();
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

    public void kennycodeencrypt(){
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
                    //.replaceAll("⁞⁞", "⁞/ ⁞")//this is the space separator
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
                    .replaceAll("01", "mmm")
                    .replaceAll("02", "mmp")
                    .replaceAll("03", "mmf")
                    .replaceAll("04", "mpm")
                    .replaceAll("05", "mpp")
                    .replaceAll("06", "mpf")
                    .replaceAll("07", "mfm")
                    .replaceAll("08", "mfp")
                    .replaceAll("09", "mff")
                    .replaceAll("10", "pmm")
                    .replaceAll("11", "pmp")
                    .replaceAll("12", "pmf")
                    .replaceAll("13", "ppm")
                    .replaceAll("14", "ppp")
                    .replaceAll("15", "ppf")
                    .replaceAll("16", "pfm")
                    .replaceAll("17", "pfp")
                    .replaceAll("18", "pff")
                    .replaceAll("19", "fmm")
                    .replaceAll("20", "fmp")
                    .replaceAll("21", "fmf")
                    .replaceAll("22", "fpm")
                    .replaceAll("23", "fpp")
                    .replaceAll("24", "fpf")
                    .replaceAll("25", "ffm")
                    .replaceAll("26", "ffp")
                    ;

            outputString = convert;

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    public void kennycodedecrypt(){
        inputString = input.getText().toString().toLowerCase();

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
                    .replaceAll("⁞mmm⁞", "⁞⁞")
                    .replaceAll("⁞mmp⁞", "⁞⁞")
                    .replaceAll("⁞mmf⁞", "⁞⁞")
                    .replaceAll("⁞mpm⁞", "⁞⁞")
                    .replaceAll("⁞mpp⁞", "⁞⁞")
                    .replaceAll("⁞mpf⁞", "⁞⁞")
                    .replaceAll("⁞mfm⁞", "⁞⁞")
                    .replaceAll("⁞mfp⁞", "⁞⁞")
                    .replaceAll("⁞mff⁞", "⁞⁞")
                    .replaceAll("⁞pmm⁞", "⁞⁞")
                    .replaceAll("⁞pmp⁞", "⁞⁞")
                    .replaceAll("⁞pmf⁞", "⁞⁞")
                    .replaceAll("⁞ppm⁞", "⁞⁞")
                    .replaceAll("⁞ppp⁞", "⁞⁞")
                    .replaceAll("⁞ppf⁞", "⁞⁞")
                    .replaceAll("⁞pfm⁞", "⁞⁞")
                    .replaceAll("⁞pfp⁞", "⁞⁞")
                    .replaceAll("⁞pff⁞", "⁞⁞")
                    .replaceAll("⁞fmm⁞", "⁞⁞")
                    .replaceAll("⁞fmp⁞", "⁞⁞")
                    .replaceAll("⁞fmf⁞", "⁞⁞")
                    .replaceAll("⁞fpm⁞", "⁞⁞")
                    .replaceAll("⁞fpp⁞", "⁞⁞")
                    .replaceAll("⁞fpf⁞", "⁞⁞")
                    .replaceAll("⁞ffm⁞", "⁞⁞")
                    .replaceAll("⁞ffp⁞", "⁞⁞")
                    .replaceAll(" ", "")
                    //.replaceAll("/", "⁞ ⁞")//separator
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
                        .replaceAll("⁞mmm⁞", "⁞a⁞")
                        .replaceAll("⁞mmp⁞", "⁞b⁞")
                        .replaceAll("⁞mmf⁞", "⁞c⁞")
                        .replaceAll("⁞mpm⁞", "⁞d⁞")
                        .replaceAll("⁞mpp⁞", "⁞e⁞")
                        .replaceAll("⁞mpf⁞", "⁞f⁞")
                        .replaceAll("⁞mfm⁞", "⁞g⁞")
                        .replaceAll("⁞mfp⁞", "⁞h⁞")
                        .replaceAll("⁞mff⁞", "⁞i⁞")
                        .replaceAll("⁞pmm⁞", "⁞j⁞")
                        .replaceAll("⁞pmp⁞", "⁞k⁞")
                        .replaceAll("⁞pmf⁞", "⁞l⁞")
                        .replaceAll("⁞ppm⁞", "⁞m⁞")
                        .replaceAll("⁞ppp⁞", "⁞n⁞")
                        .replaceAll("⁞ppf⁞", "⁞o⁞")
                        .replaceAll("⁞pfm⁞", "⁞p⁞")
                        .replaceAll("⁞pfp⁞", "⁞q⁞")
                        .replaceAll("⁞pff⁞", "⁞r⁞")
                        .replaceAll("⁞fmm⁞", "⁞s⁞")
                        .replaceAll("⁞fmp⁞", "⁞t⁞")
                        .replaceAll("⁞fmf⁞", "⁞u⁞")
                        .replaceAll("⁞fpm⁞", "⁞v⁞")
                        .replaceAll("⁞fpp⁞", "⁞w⁞")
                        .replaceAll("⁞fpf⁞", "⁞x⁞")
                        .replaceAll("⁞ffm⁞", "⁞y⁞")
                        .replaceAll("⁞ffp⁞", "⁞z⁞")
                        .replaceAll(" ", "")
                        //.replaceAll("/", "⁞ ⁞")//separator
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
