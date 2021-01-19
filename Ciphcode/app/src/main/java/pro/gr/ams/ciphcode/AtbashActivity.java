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

public class AtbashActivity extends AppCompatActivity {

    String codename = "Atbash";
    String codesample = "Zgyzhs";

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
                    atbash();
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
                    atbash();
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

    public void atbash(){
        inputString = input.getText().toString();
        outputString = inputString
                .replaceAll("a", "⁞a")
                .replaceAll("b", "⁞b")
                .replaceAll("c", "⁞c")
                .replaceAll("d", "⁞d")
                .replaceAll("e", "⁞e")
                .replaceAll("f", "⁞f")
                .replaceAll("g", "⁞g")
                .replaceAll("h", "⁞h")
                .replaceAll("i", "⁞i")
                .replaceAll("j", "⁞j")
                .replaceAll("k", "⁞k")
                .replaceAll("l", "⁞l")
                .replaceAll("m", "⁞n")
                .replaceAll("n", "m")
                .replaceAll("o", "l")
                .replaceAll("p", "k")
                .replaceAll("q", "j")
                .replaceAll("r", "i")
                .replaceAll("s", "h")
                .replaceAll("t", "g")
                .replaceAll("u", "f")
                .replaceAll("v", "e")
                .replaceAll("w", "d")
                .replaceAll("x", "c")
                .replaceAll("y", "b")
                .replaceAll("z", "a")

                .replaceAll("⁞a", "z")
                .replaceAll("⁞b", "y")
                .replaceAll("⁞c", "x")
                .replaceAll("⁞d", "w")
                .replaceAll("⁞e", "v")
                .replaceAll("⁞f", "u")
                .replaceAll("⁞g", "t")
                .replaceAll("⁞h", "s")
                .replaceAll("⁞i", "r")
                .replaceAll("⁞j", "q")
                .replaceAll("⁞k", "p")
                .replaceAll("⁞l", "o")
                .replaceAll("⁞m", "n")
                .replaceAll("A", "⁞A")

                .replaceAll("B", "⁞B")
                .replaceAll("C", "⁞C")
                .replaceAll("D", "⁞D")
                .replaceAll("E", "⁞E")
                .replaceAll("F", "⁞F")
                .replaceAll("G", "⁞G")
                .replaceAll("H", "⁞H")
                .replaceAll("I", "⁞I")
                .replaceAll("J", "⁞J")
                .replaceAll("K", "⁞K")
                .replaceAll("L", "⁞L")
                .replaceAll("M", "⁞N")
                .replaceAll("N", "M")
                .replaceAll("O", "L")
                .replaceAll("P", "K")
                .replaceAll("Q", "J")
                .replaceAll("R", "I")
                .replaceAll("S", "H")
                .replaceAll("T", "G")
                .replaceAll("U", "F")
                .replaceAll("V", "E")
                .replaceAll("W", "D")
                .replaceAll("X", "C")
                .replaceAll("Y", "B")
                .replaceAll("Z", "A")

                .replaceAll("⁞A", "Z")
                .replaceAll("⁞B", "Y")
                .replaceAll("⁞C", "X")
                .replaceAll("⁞D", "W")
                .replaceAll("⁞E", "V")
                .replaceAll("⁞F", "U")
                .replaceAll("⁞G", "T")
                .replaceAll("⁞H", "S")
                .replaceAll("⁞I", "R")
                .replaceAll("⁞J", "Q")
                .replaceAll("⁞K", "P")
                .replaceAll("⁞L", "O")
                .replaceAll("⁞M", "N");

        output.setText(outputString);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
