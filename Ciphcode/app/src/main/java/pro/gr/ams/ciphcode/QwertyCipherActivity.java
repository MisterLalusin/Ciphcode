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

public class QwertyCipherActivity extends AppCompatActivity {

    String codename = "Qwerty Cipher";
    String codesample = "Jvtkzn Eohitk";

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
                else if (input.getText().toString().toLowerCase().indexOf("a")==-1&& input.getText().toString().toLowerCase().indexOf("b")==-1&& input.getText().toString().toLowerCase().indexOf("c")==-1&& input.getText().toString().toLowerCase().indexOf("d")==-1&& input.getText().toString().toLowerCase().indexOf("e")==-1&& input.getText().toString().toLowerCase().indexOf("f")==-1&& input.getText().toString().toLowerCase().indexOf("g")==-1&& input.getText().toString().toLowerCase().indexOf("h")==-1&& input.getText().toString().toLowerCase().indexOf("i")==-1&& input.getText().toString().toLowerCase().indexOf("j")==-1&& input.getText().toString().toLowerCase().indexOf("k")==-1&& input.getText().toString().toLowerCase().indexOf("l")==-1&& input.getText().toString().toLowerCase().indexOf("m")==-1&& input.getText().toString().toLowerCase().indexOf("n")==-1&& input.getText().toString().toLowerCase().indexOf("o")==-1&& input.getText().toString().toLowerCase().indexOf("p")==-1&& input.getText().toString().toLowerCase().indexOf("q")==-1&& input.getText().toString().toLowerCase().indexOf("r")==-1&& input.getText().toString().toLowerCase().indexOf("s")==-1&& input.getText().toString().toLowerCase().indexOf("t")==-1&& input.getText().toString().toLowerCase().indexOf("u")==-1&& input.getText().toString().toLowerCase().indexOf("v")==-1&& input.getText().toString().toLowerCase().indexOf("w")==-1&& input.getText().toString().toLowerCase().indexOf("x")==-1&& input.getText().toString().toLowerCase().indexOf("y")==-1&& input.getText().toString().toLowerCase().indexOf("z")==-1) {
                    Toast.makeText(getApplicationContext(), "Nothing to encrypt.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();
                    qwertycipherencrypt();
                }
            }
        });
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().replaceAll("\n","").replaceAll(" ","").length()==0) {
                    Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                }
                else if (input.getText().toString().toLowerCase().indexOf("a")==-1&& input.getText().toString().toLowerCase().indexOf("b")==-1&& input.getText().toString().toLowerCase().indexOf("c")==-1&& input.getText().toString().toLowerCase().indexOf("d")==-1&& input.getText().toString().toLowerCase().indexOf("e")==-1&& input.getText().toString().toLowerCase().indexOf("f")==-1&& input.getText().toString().toLowerCase().indexOf("g")==-1&& input.getText().toString().toLowerCase().indexOf("h")==-1&& input.getText().toString().toLowerCase().indexOf("i")==-1&& input.getText().toString().toLowerCase().indexOf("j")==-1&& input.getText().toString().toLowerCase().indexOf("k")==-1&& input.getText().toString().toLowerCase().indexOf("l")==-1&& input.getText().toString().toLowerCase().indexOf("m")==-1&& input.getText().toString().toLowerCase().indexOf("n")==-1&& input.getText().toString().toLowerCase().indexOf("o")==-1&& input.getText().toString().toLowerCase().indexOf("p")==-1&& input.getText().toString().toLowerCase().indexOf("q")==-1&& input.getText().toString().toLowerCase().indexOf("r")==-1&& input.getText().toString().toLowerCase().indexOf("s")==-1&& input.getText().toString().toLowerCase().indexOf("t")==-1&& input.getText().toString().toLowerCase().indexOf("u")==-1&& input.getText().toString().toLowerCase().indexOf("v")==-1&& input.getText().toString().toLowerCase().indexOf("w")==-1&& input.getText().toString().toLowerCase().indexOf("x")==-1&& input.getText().toString().toLowerCase().indexOf("y")==-1&& input.getText().toString().toLowerCase().indexOf("z")==-1) {
                    Toast.makeText(getApplicationContext(), "Nothing to decrypt.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "Decrypted succesfully.", Toast.LENGTH_SHORT).show();
                    qwertycipherdecrypt();
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

    public void qwertycipherencrypt(){
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

                .replaceAll("A", "ᴥ01")
                .replaceAll("B", "ᴥ02")
                .replaceAll("C", "ᴥ03")
                .replaceAll("D", "ᴥ04")
                .replaceAll("E", "ᴥ05")
                .replaceAll("F", "ᴥ06")
                .replaceAll("G", "ᴥ07")
                .replaceAll("H", "ᴥ08")
                .replaceAll("I", "ᴥ09")
                .replaceAll("J", "ᴥ10")
                .replaceAll("K", "ᴥ11")
                .replaceAll("L", "ᴥ12")
                .replaceAll("M", "ᴥ13")
                .replaceAll("N", "ᴥ14")
                .replaceAll("O", "ᴥ15")
                .replaceAll("P", "ᴥ16")
                .replaceAll("Q", "ᴥ17")
                .replaceAll("R", "ᴥ18")
                .replaceAll("S", "ᴥ19")
                .replaceAll("T", "ᴥ20")
                .replaceAll("U", "ᴥ21")
                .replaceAll("V", "ᴥ22")
                .replaceAll("W", "ᴥ23")
                .replaceAll("X", "ᴥ24")
                .replaceAll("Y", "ᴥ25")
                .replaceAll("Z", "ᴥ26")
        ;

        outputString = inputString
                .replaceAll("⁞01", "q")
                .replaceAll("⁞02", "w")
                .replaceAll("⁞03", "e")
                .replaceAll("⁞04", "r")
                .replaceAll("⁞05", "t")
                .replaceAll("⁞06", "y")
                .replaceAll("⁞07", "u")
                .replaceAll("⁞08", "i")
                .replaceAll("⁞09", "o")
                .replaceAll("⁞10", "p")
                .replaceAll("⁞11", "a")
                .replaceAll("⁞12", "s")
                .replaceAll("⁞13", "d")
                .replaceAll("⁞14", "f")
                .replaceAll("⁞15", "g")
                .replaceAll("⁞16", "h")
                .replaceAll("⁞17", "j")
                .replaceAll("⁞18", "k")
                .replaceAll("⁞19", "l")
                .replaceAll("⁞20", "z")
                .replaceAll("⁞21", "x")
                .replaceAll("⁞22", "c")
                .replaceAll("⁞23", "v")
                .replaceAll("⁞24", "b")
                .replaceAll("⁞25", "n")
                .replaceAll("⁞26", "m")
                .replaceAll("ᴥ01", "Q")
                .replaceAll("ᴥ02", "W")
                .replaceAll("ᴥ03", "E")
                .replaceAll("ᴥ04", "R")
                .replaceAll("ᴥ05", "T")
                .replaceAll("ᴥ06", "Y")
                .replaceAll("ᴥ07", "U")
                .replaceAll("ᴥ08", "I")
                .replaceAll("ᴥ09", "O")
                .replaceAll("ᴥ10", "P")
                .replaceAll("ᴥ11", "A")
                .replaceAll("ᴥ12", "S")
                .replaceAll("ᴥ13", "D")
                .replaceAll("ᴥ14", "F")
                .replaceAll("ᴥ15", "G")
                .replaceAll("ᴥ16", "H")
                .replaceAll("ᴥ17", "J")
                .replaceAll("ᴥ18", "K")
                .replaceAll("ᴥ19", "L")
                .replaceAll("ᴥ20", "Z")
                .replaceAll("ᴥ21", "X")
                .replaceAll("ᴥ22", "C")
                .replaceAll("ᴥ23", "V")
                .replaceAll("ᴥ24", "B")
                .replaceAll("ᴥ25", "N")
                .replaceAll("ᴥ26", "M")
        ;

        output.setText(outputString);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void qwertycipherdecrypt(){
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

                .replaceAll("A", "ᴥ01")
                .replaceAll("B", "ᴥ02")
                .replaceAll("C", "ᴥ03")
                .replaceAll("D", "ᴥ04")
                .replaceAll("E", "ᴥ05")
                .replaceAll("F", "ᴥ06")
                .replaceAll("G", "ᴥ07")
                .replaceAll("H", "ᴥ08")
                .replaceAll("I", "ᴥ09")
                .replaceAll("J", "ᴥ10")
                .replaceAll("K", "ᴥ11")
                .replaceAll("L", "ᴥ12")
                .replaceAll("M", "ᴥ13")
                .replaceAll("N", "ᴥ14")
                .replaceAll("O", "ᴥ15")
                .replaceAll("P", "ᴥ16")
                .replaceAll("Q", "ᴥ17")
                .replaceAll("R", "ᴥ18")
                .replaceAll("S", "ᴥ19")
                .replaceAll("T", "ᴥ20")
                .replaceAll("U", "ᴥ21")
                .replaceAll("V", "ᴥ22")
                .replaceAll("W", "ᴥ23")
                .replaceAll("X", "ᴥ24")
                .replaceAll("Y", "ᴥ25")
                .replaceAll("Z", "ᴥ26")
        ;

        outputString = inputString
                .replaceAll("⁞01", "k")
                .replaceAll("⁞02", "x")
                .replaceAll("⁞03", "v")
                .replaceAll("⁞04", "m")
                .replaceAll("⁞05", "c")
                .replaceAll("⁞06", "n")
                .replaceAll("⁞07", "o")
                .replaceAll("⁞08", "p")
                .replaceAll("⁞09", "h")
                .replaceAll("⁞10", "q")
                .replaceAll("⁞11", "r")
                .replaceAll("⁞12", "s")
                .replaceAll("⁞13", "z")
                .replaceAll("⁞14", "y")
                .replaceAll("⁞15", "i")
                .replaceAll("⁞16", "j")
                .replaceAll("⁞17", "a")
                .replaceAll("⁞18", "d")
                .replaceAll("⁞19", "l")
                .replaceAll("⁞20", "e")
                .replaceAll("⁞21", "g")
                .replaceAll("⁞22", "w")
                .replaceAll("⁞23", "b")
                .replaceAll("⁞24", "u")
                .replaceAll("⁞25", "f")
                .replaceAll("⁞26", "t")
                .replaceAll("ᴥ01", "K")
                .replaceAll("ᴥ02", "X")
                .replaceAll("ᴥ03", "V")
                .replaceAll("ᴥ04", "M")
                .replaceAll("ᴥ05", "C")
                .replaceAll("ᴥ06", "N")
                .replaceAll("ᴥ07", "O")
                .replaceAll("ᴥ08", "P")
                .replaceAll("ᴥ09", "H")
                .replaceAll("ᴥ10", "Q")
                .replaceAll("ᴥ11", "R")
                .replaceAll("ᴥ12", "S")
                .replaceAll("ᴥ13", "Z")
                .replaceAll("ᴥ14", "Y")
                .replaceAll("ᴥ15", "I")
                .replaceAll("ᴥ16", "J")
                .replaceAll("ᴥ17", "A")
                .replaceAll("ᴥ18", "D")
                .replaceAll("ᴥ19", "L")
                .replaceAll("ᴥ20", "E")
                .replaceAll("ᴥ21", "G")
                .replaceAll("ᴥ22", "W")
                .replaceAll("ᴥ23", "B")
                .replaceAll("ᴥ24", "U")
                .replaceAll("ᴥ25", "F")
                .replaceAll("ᴥ26", "T")
        ;

        output.setText(outputString);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
