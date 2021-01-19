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

public class HomophonicCodeActivity extends AppCompatActivity {

    String codename = "Homophonic Code";
    String codesample = "Cqgqlcqavs Sqfz";

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
                    homophoniccodeencrypt();
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
                    homophoniccodedecrypt();
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

    public void homophoniccodeencrypt(){

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
                    .replaceAll("⁞01", "d")
                    .replaceAll("⁞02", "x")
                    .replaceAll("⁞03", "s")
                    .replaceAll("⁞04", "f")
                    .replaceAll("⁞05", "z")
                    .replaceAll("⁞06", "e")
                    .replaceAll("⁞07", "h")
                    .replaceAll("⁞08", "c")
                    .replaceAll("⁞09", "v")
                    .replaceAll("⁞10", "i")
                    .replaceAll("⁞11", "t")
                    .replaceAll("⁞12", "p")
                    .replaceAll("⁞13", "g")
                    .replaceAll("⁞14", "a")
                    .replaceAll("⁞15", "q")
                    .replaceAll("⁞16", "l")
                    .replaceAll("⁞17", "k")
                    .replaceAll("⁞18", "j")
                    .replaceAll("⁞19", "r")
                    .replaceAll("⁞20", "u")
                    .replaceAll("⁞21", "o")
                    .replaceAll("⁞22", "w")
                    .replaceAll("⁞23", "m")
                    .replaceAll("⁞24", "y")
                    .replaceAll("⁞25", "b")
                    .replaceAll("⁞26", "n")
                    .replaceAll("⁞27", "D")
                    .replaceAll("⁞28", "X")
                    .replaceAll("⁞29", "S")
                    .replaceAll("⁞30", "F")
                    .replaceAll("⁞31", "Z")
                    .replaceAll("⁞32", "E")
                    .replaceAll("⁞33", "H")
                    .replaceAll("⁞34", "C")
                    .replaceAll("⁞35", "V")
                    .replaceAll("⁞36", "I")
                    .replaceAll("⁞37", "T")
                    .replaceAll("⁞38", "P")
                    .replaceAll("⁞39", "G")
                    .replaceAll("⁞40", "A")
                    .replaceAll("⁞41", "Q")
                    .replaceAll("⁞42", "L")
                    .replaceAll("⁞43", "K")
                    .replaceAll("⁞44", "J")
                    .replaceAll("⁞45", "R")
                    .replaceAll("⁞46", "U")
                    .replaceAll("⁞47", "O")
                    .replaceAll("⁞48", "W")
                    .replaceAll("⁞49", "M")
                    .replaceAll("⁞50", "Y")
                    .replaceAll("⁞51", "B")
                    .replaceAll("⁞52", "N")
            ;

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void homophoniccodedecrypt(){

        String chk4unsprtdchar = input.getText().toString().toLowerCase()
                .replaceAll("a", "")
                .replaceAll("5", "")
                .replaceAll("b", "")
                .replaceAll("c", "")
                .replaceAll("d", "")
                .replaceAll("9", "")
                .replaceAll("e", "")
                .replaceAll("f", "")
                .replaceAll("g", "")
                .replaceAll("h", "")
                .replaceAll("i", "")
                .replaceAll("j", "")
                .replaceAll("k", "")
                .replaceAll("l", "")
                .replaceAll("m", "")
                .replaceAll("n", "")
                .replaceAll("o", "")
                .replaceAll("p", "")
                .replaceAll("q", "")
                .replaceAll("0", "")
                .replaceAll("r", "")
                .replaceAll("4", "")
                .replaceAll("s", "")
                .replaceAll("t", "")
                .replaceAll("u", "")
                .replaceAll("6", "")
                .replaceAll("v", "")
                .replaceAll("3", "")
                .replaceAll("w", "")
                .replaceAll("x", "")
                .replaceAll("y", "")
                .replaceAll("z", "")
                .replaceAll("7", "")
                .replaceAll("2", "")
                .replaceAll("1", "")
                .replaceAll(" ","")
                .replaceAll("\n","")
                ;

        if(chk4unsprtdchar.length()!=0){
            Toast.makeText(this, "Not a valid code.", Toast.LENGTH_SHORT).show();
        }
        else{
            inputString = input.getText().toString()

                    .replaceAll("0", "⁞0⁞")
                    .replaceAll("1", "⁞00⁞")
                    .replaceAll("2", "⁞000⁞")
                    .replaceAll("3", "⁞0000⁞")
                    .replaceAll("4", "⁞00000⁞")
                    .replaceAll("5", "⁞000000⁞")
                    .replaceAll("6", "⁞0000000⁞")
                    .replaceAll("7", "⁞00000000⁞")
                    .replaceAll("9", "⁞000000000⁞")

                    .replaceAll("⁞0⁞", "⁞17")
                    .replaceAll("⁞00⁞", "⁞26")
                    .replaceAll("⁞000⁞", "⁞26")
                    .replaceAll("⁞0000⁞", "⁞22")
                    .replaceAll("⁞00000⁞", "⁞18")
                    .replaceAll("⁞000000⁞", "⁞01")
                    .replaceAll("⁞0000000⁞", "⁞21")
                    .replaceAll("⁞00000000⁞", "⁞26")
                    .replaceAll("⁞000000000⁞", "⁞04")

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
                    .replaceAll("⁞01", "n")
                    .replaceAll("⁞02", "y")
                    .replaceAll("⁞03", "h")
                    .replaceAll("⁞04", "a")
                    .replaceAll("⁞05", "f")
                    .replaceAll("⁞06", "d")
                    .replaceAll("⁞07", "m")
                    .replaceAll("⁞08", "g")
                    .replaceAll("⁞09", "j")
                    .replaceAll("⁞10", "r")
                    .replaceAll("⁞11", "q")
                    .replaceAll("⁞12", "p")
                    .replaceAll("⁞13", "w")
                    .replaceAll("⁞14", "z")
                    .replaceAll("⁞15", "u")
                    .replaceAll("⁞16", "l")
                    .replaceAll("⁞17", "o")
                    .replaceAll("⁞18", "s")
                    .replaceAll("⁞19", "c")
                    .replaceAll("⁞20", "k")
                    .replaceAll("⁞21", "t")
                    .replaceAll("⁞22", "i")
                    .replaceAll("⁞23", "v")
                    .replaceAll("⁞24", "b")
                    .replaceAll("⁞25", "x")
                    .replaceAll("⁞26", "e")
                    .replaceAll("⁞27", "N")
                    .replaceAll("⁞28", "Y")
                    .replaceAll("⁞29", "H")
                    .replaceAll("⁞30", "A")
                    .replaceAll("⁞31", "F")
                    .replaceAll("⁞32", "D")
                    .replaceAll("⁞33", "M")
                    .replaceAll("⁞34", "G")
                    .replaceAll("⁞35", "J")
                    .replaceAll("⁞36", "R")
                    .replaceAll("⁞37", "Q")
                    .replaceAll("⁞38", "P")
                    .replaceAll("⁞39", "W")
                    .replaceAll("⁞40", "Z")
                    .replaceAll("⁞41", "U")
                    .replaceAll("⁞42", "L")
                    .replaceAll("⁞43", "O")
                    .replaceAll("⁞44", "S")
                    .replaceAll("⁞45", "C")
                    .replaceAll("⁞46", "K")
                    .replaceAll("⁞47", "T")
                    .replaceAll("⁞48", "I")
                    .replaceAll("⁞49", "V")
                    .replaceAll("⁞50", "B")
                    .replaceAll("⁞51", "X")
                    .replaceAll("⁞52", "E")
            ;

            output.setText(outputString);


            Toast.makeText(getApplicationContext(), "Decrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
