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

public class HitmanCipherActivity extends AppCompatActivity {

    String codename = "Hitman Cipher";
    String codesample = "Tshoan Ysltwj";

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
        info = (Button) findViewById(R.id.info);
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
                    hitmancipher();
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
                    hitmancipher();
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

    public void hitmancipher(){
        inputString = input.getText().toString();
        outputString = inputString
                .replaceAll("b", "⁞z")
                .replaceAll("c", "⁞y")
                .replaceAll("d", "⁞x")
                .replaceAll("e", "⁞w")
                .replaceAll("f", "⁞v")
                .replaceAll("g", "⁞u")
                .replaceAll("h", "⁞t")
                .replaceAll("i", "⁞s")
                .replaceAll("j", "⁞r")
                .replaceAll("k", "⁞q")
                .replaceAll("l", "⁞p")
                .replaceAll("m", "⁞o")

                .replaceAll("z", "b")
                .replaceAll("y", "c")
                .replaceAll("x", "d")
                .replaceAll("w", "e")
                .replaceAll("v", "f")
                .replaceAll("u", "g")
                .replaceAll("t", "h")
                .replaceAll("s", "i")
                .replaceAll("r", "j")
                .replaceAll("q", "k")
                .replaceAll("p", "l")
                .replaceAll("o", "m")

                .replaceAll("⁞b", "z")
                .replaceAll("⁞c", "y")
                .replaceAll("⁞d", "x")
                .replaceAll("⁞e", "w")
                .replaceAll("⁞f", "v")
                .replaceAll("⁞g", "u")
                .replaceAll("⁞h", "t")
                .replaceAll("⁞i", "s")
                .replaceAll("⁞j", "r")
                .replaceAll("⁞k", "q")
                .replaceAll("⁞l", "p")
                .replaceAll("⁞m", "o")

                .replaceAll("B", "⁞Z")
                .replaceAll("C", "⁞Y")
                .replaceAll("D", "⁞X")
                .replaceAll("E", "⁞W")
                .replaceAll("F", "⁞V")
                .replaceAll("G", "⁞U")
                .replaceAll("H", "⁞T")
                .replaceAll("I", "⁞S")
                .replaceAll("J", "⁞R")
                .replaceAll("K", "⁞Q")
                .replaceAll("L", "⁞P")
                .replaceAll("M", "⁞O")

                .replaceAll("Z", "B")
                .replaceAll("Y", "C")
                .replaceAll("X", "D")
                .replaceAll("W", "E")
                .replaceAll("V", "F")
                .replaceAll("U", "G")
                .replaceAll("T", "H")
                .replaceAll("S", "I")
                .replaceAll("R", "J")
                .replaceAll("Q", "K")
                .replaceAll("P", "L")
                .replaceAll("O", "M")

                .replaceAll("⁞B", "Z")
                .replaceAll("⁞C", "Y")
                .replaceAll("⁞D", "X")
                .replaceAll("⁞E", "W")
                .replaceAll("⁞F", "V")
                .replaceAll("⁞G", "U")
                .replaceAll("⁞H", "T")
                .replaceAll("⁞I", "S")
                .replaceAll("⁞J", "R")
                .replaceAll("⁞K", "Q")
                .replaceAll("⁞L", "P")
                .replaceAll("⁞M", "O")

                ;

        output.setText(outputString);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
