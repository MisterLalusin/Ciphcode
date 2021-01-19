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

public class MVCipherActivity extends AppCompatActivity {

    String codename = "MV Cipher";
    String codesample = ",B Vo[jrt";

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
                    mvcipherencrypt();
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
                else {
                    mvcipherdecrypt();
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

    public void mvcipherencrypt(){

        inputString = input.getText().toString();

        if (inputString.toLowerCase()
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

        else{
            inputString = inputString
                    .replaceAll("a", "⁞01")
                    .replaceAll("b", "⁞02")
                    .replaceAll("c", "⁞03")
                    .replaceAll("d", "⁞04")
                    .replaceAll("e", "⁞05")
                    .replaceAll("f", "⁞06")
                    .replaceAll("g", "⁞07")
                    .replaceAll("h", "⁞08")
                    .replaceAll("i", "⁞09")
                    .replaceAll("j", "⁞10")
                    .replaceAll("k", "⁞11")
                    .replaceAll("l", "⁞12")
                    .replaceAll("m", "⁞13")
                    .replaceAll("n", "⁞14")
                    .replaceAll("o", "⁞15")
                    .replaceAll("p", "⁞16")
                    .replaceAll("q", "⁞17")
                    .replaceAll("r", "⁞18")
                    .replaceAll("s", "⁞19")
                    .replaceAll("t", "⁞20")
                    .replaceAll("u", "⁞21")
                    .replaceAll("v", "⁞22")
                    .replaceAll("w", "⁞23")
                    .replaceAll("x", "⁞24")
                    .replaceAll("y", "⁞25")
                    .replaceAll("z", "⁞26")
                    .replaceAll("A", "⁞27")
                    .replaceAll("B", "⁞28")
                    .replaceAll("C", "⁞29")
                    .replaceAll("D", "⁞30")
                    .replaceAll("E", "⁞31")
                    .replaceAll("F", "⁞32")
                    .replaceAll("G", "⁞33")
                    .replaceAll("H", "⁞34")
                    .replaceAll("I", "⁞35")
                    .replaceAll("J", "⁞36")
                    .replaceAll("K", "⁞37")
                    .replaceAll("L", "⁞38")
                    .replaceAll("M", "⁞39")
                    .replaceAll("N", "⁞40")
                    .replaceAll("O", "⁞41")
                    .replaceAll("P", "⁞42")
                    .replaceAll("Q", "⁞43")
                    .replaceAll("R", "⁞44")
                    .replaceAll("S", "⁞45")
                    .replaceAll("T", "⁞46")
                    .replaceAll("U", "⁞47")
                    .replaceAll("V", "⁞48")
                    .replaceAll("W", "⁞49")
                    .replaceAll("X", "⁞50")
                    .replaceAll("Y", "⁞51")
                    .replaceAll("Z", "⁞52")
            ;

            outputString = inputString
                    .replaceAll("⁞01", "s")
                    .replaceAll("⁞02", "n")
                    .replaceAll("⁞03", "v")
                    .replaceAll("⁞04", "f")
                    .replaceAll("⁞05", "r")
                    .replaceAll("⁞06", "g")
                    .replaceAll("⁞07", "h")
                    .replaceAll("⁞08", "j")
                    .replaceAll("⁞09", "o")
                    .replaceAll("⁞10", "k")
                    .replaceAll("⁞11", "k")
                    .replaceAll("⁞12", ";")
                    .replaceAll("⁞13", ",")
                    .replaceAll("⁞14", "m")
                    .replaceAll("⁞15", "p")
                    .replaceAll("⁞16", "[")
                    .replaceAll("⁞17", "w")
                    .replaceAll("⁞18", "t")
                    .replaceAll("⁞19", "d")
                    .replaceAll("⁞20", "y")
                    .replaceAll("⁞21", "i")
                    .replaceAll("⁞22", "b")
                    .replaceAll("⁞23", "e")
                    .replaceAll("⁞24", "c")
                    .replaceAll("⁞25", "u")
                    .replaceAll("⁞26", "x")
                    .replaceAll("⁞27", "S")
                    .replaceAll("⁞28", "N")
                    .replaceAll("⁞29", "V")
                    .replaceAll("⁞30", "F")
                    .replaceAll("⁞31", "R")
                    .replaceAll("⁞32", "G")
                    .replaceAll("⁞33", "H")
                    .replaceAll("⁞34", "J")
                    .replaceAll("⁞35", "O")
                    .replaceAll("⁞36", "K")
                    .replaceAll("⁞37", "K")
                    .replaceAll("⁞38", ";")
                    .replaceAll("⁞39", ",")
                    .replaceAll("⁞40", "M")
                    .replaceAll("⁞41", "P")
                    .replaceAll("⁞42", "[")
                    .replaceAll("⁞43", "W")
                    .replaceAll("⁞44", "T")
                    .replaceAll("⁞45", "D")
                    .replaceAll("⁞46", "Y")
                    .replaceAll("⁞47", "I")
                    .replaceAll("⁞48", "B")
                    .replaceAll("⁞49", "E")
                    .replaceAll("⁞50", "C")
                    .replaceAll("⁞51", "U")
                    .replaceAll("⁞52", "X")
            ;

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void mvcipherdecrypt(){

        String chk4unsprtdchar = input.getText().toString().toLowerCase()
                .replaceAll("s","")
                .replaceAll("n","")
                .replaceAll("v","")
                .replaceAll("f","")
                .replaceAll("r","")
                .replaceAll("g","")
                .replaceAll("h","")
                .replaceAll("j","")
                .replaceAll("o","")
                .replaceAll("k","")
                .replaceAll("k","")
                .replaceAll(";","")
                .replaceAll(",","")
                .replaceAll("m","")
                .replaceAll("p","")
                .replaceAll("\\[","")
                .replaceAll("w","")
                .replaceAll("t","")
                .replaceAll("d","")
                .replaceAll("y","")
                .replaceAll("i","")
                .replaceAll("b","")
                .replaceAll("e","")
                .replaceAll("c","")
                .replaceAll("u","")
                .replaceAll("z","")
                .replaceAll("x","")
                .replaceAll("\n","")
                .replaceAll(" ","")
                .replaceAll("\n","")
                ;

        if(chk4unsprtdchar.length()!=0){
            Toast.makeText(this, "Not a valid code.", Toast.LENGTH_SHORT).show();
        }
        else{
            inputString = input.getText().toString()
                    .replaceAll("a", "⁞01")
                    .replaceAll("b", "⁞02")
                    .replaceAll("c", "⁞03")
                    .replaceAll("d", "⁞04")
                    .replaceAll("e", "⁞05")
                    .replaceAll("f", "⁞06")
                    .replaceAll("g", "⁞07")
                    .replaceAll("h", "⁞08")
                    .replaceAll("i", "⁞09")
                    .replaceAll("j", "⁞10")
                    .replaceAll("k", "⁞11")
                    .replaceAll("l", "⁞12")
                    .replaceAll("m", "⁞13")
                    .replaceAll("n", "⁞14")
                    .replaceAll("o", "⁞15")
                    .replaceAll("p", "⁞16")
                    .replaceAll("q", "⁞17")
                    .replaceAll("r", "⁞18")
                    .replaceAll("s", "⁞19")
                    .replaceAll("t", "⁞20")
                    .replaceAll("u", "⁞21")
                    .replaceAll("v", "⁞22")
                    .replaceAll("w", "⁞23")
                    .replaceAll("x", "⁞24")
                    .replaceAll("y", "⁞25")
                    .replaceAll("z", "⁞26")
                    .replaceAll("A", "⁞27")
                    .replaceAll("B", "⁞28")
                    .replaceAll("C", "⁞29")
                    .replaceAll("D", "⁞30")
                    .replaceAll("E", "⁞31")
                    .replaceAll("F", "⁞32")
                    .replaceAll("G", "⁞33")
                    .replaceAll("H", "⁞34")
                    .replaceAll("I", "⁞35")
                    .replaceAll("J", "⁞36")
                    .replaceAll("K", "⁞37")
                    .replaceAll("L", "⁞38")
                    .replaceAll("M", "⁞39")
                    .replaceAll("N", "⁞40")
                    .replaceAll("O", "⁞41")
                    .replaceAll("P", "⁞42")
                    .replaceAll("Q", "⁞43")
                    .replaceAll("R", "⁞44")
                    .replaceAll("S", "⁞45")
                    .replaceAll("T", "⁞46")
                    .replaceAll("U", "⁞47")
                    .replaceAll("V", "⁞48")
                    .replaceAll("W", "⁞49")
                    .replaceAll("X", "⁞50")
                    .replaceAll("Y", "⁞51")
                    .replaceAll("Z", "⁞52")
                    .replaceAll(";", "⁞53")
                    .replaceAll(",", "⁞54")
                    .replaceAll("\\[", "⁞55")
            ;

            outputString = inputString
                    .replaceAll("⁞01", "")
                    .replaceAll("⁞02", "v")
                    .replaceAll("⁞03", "x")
                    .replaceAll("⁞04", "s")
                    .replaceAll("⁞05", "w")
                    .replaceAll("⁞06", "d")
                    .replaceAll("⁞07", "f")
                    .replaceAll("⁞08", "g")
                    .replaceAll("⁞09", "u")
                    .replaceAll("⁞10", "h")
                    .replaceAll("⁞11", "k")
                    .replaceAll("⁞12", "")
                    .replaceAll("⁞13", "n")
                    .replaceAll("⁞14", "b")
                    .replaceAll("⁞15", "i")
                    .replaceAll("⁞16", "o")
                    .replaceAll("⁞17", "")
                    .replaceAll("⁞18", "e")
                    .replaceAll("⁞19", "a")
                    .replaceAll("⁞20", "r")
                    .replaceAll("⁞21", "y")
                    .replaceAll("⁞22", "c")
                    .replaceAll("⁞23", "q")
                    .replaceAll("⁞24", "z")
                    .replaceAll("⁞25", "t")
                    .replaceAll("⁞26", "")
                    .replaceAll("⁞27", "")
                    .replaceAll("⁞28", "V")
                    .replaceAll("⁞29", "X")
                    .replaceAll("⁞30", "S")
                    .replaceAll("⁞31", "W")
                    .replaceAll("⁞32", "D")
                    .replaceAll("⁞33", "F")
                    .replaceAll("⁞34", "G")
                    .replaceAll("⁞35", "U")
                    .replaceAll("⁞36", "H")
                    .replaceAll("⁞37", "K")
                    .replaceAll("⁞38", "")
                    .replaceAll("⁞39", "N")
                    .replaceAll("⁞40", "B")
                    .replaceAll("⁞41", "I")
                    .replaceAll("⁞42", "O")
                    .replaceAll("⁞43", "")
                    .replaceAll("⁞44", "E")
                    .replaceAll("⁞45", "A")
                    .replaceAll("⁞46", "R")
                    .replaceAll("⁞47", "Y")
                    .replaceAll("⁞48", "C")
                    .replaceAll("⁞49", "Q")
                    .replaceAll("⁞50", "Z")
                    .replaceAll("⁞51", "T")
                    .replaceAll("⁞52", "")
                    .replaceAll("⁞53", "l")
                    .replaceAll("⁞54", "m")
                    .replaceAll("⁞55", "p")
            ;

            output.setText(outputString);


            Toast.makeText(getApplicationContext(), "Decrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
