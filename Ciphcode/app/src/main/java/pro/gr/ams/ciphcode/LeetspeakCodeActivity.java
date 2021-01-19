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

public class LeetspeakCodeActivity extends AppCompatActivity {

    String codename = "Leetspeak Code";
    String codesample = "|_3375|O34|< <0|)3";

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
                else if (input.getText().toString().toLowerCase().indexOf("a")==-1&& input.getText().toString().toLowerCase().indexOf("b")==-1&& input.getText().toString().toLowerCase().indexOf("c")==-1&& input.getText().toString().toLowerCase().indexOf("d")==-1&& input.getText().toString().toLowerCase().indexOf("e")==-1&& input.getText().toString().toLowerCase().indexOf("f")==-1&& input.getText().toString().toLowerCase().indexOf("g")==-1&& input.getText().toString().toLowerCase().indexOf("h")==-1&& input.getText().toString().toLowerCase().indexOf("i")==-1&& input.getText().toString().toLowerCase().indexOf("j")==-1&& input.getText().toString().toLowerCase().indexOf("k")==-1&& input.getText().toString().toLowerCase().indexOf("l")==-1&& input.getText().toString().toLowerCase().indexOf("m")==-1&& input.getText().toString().toLowerCase().indexOf("n")==-1&& input.getText().toString().toLowerCase().indexOf("o")==-1&& input.getText().toString().toLowerCase().indexOf("p")==-1&& input.getText().toString().toLowerCase().indexOf("q")==-1&& input.getText().toString().toLowerCase().indexOf("r")==-1&& input.getText().toString().toLowerCase().indexOf("s")==-1&& input.getText().toString().toLowerCase().indexOf("t")==-1&& input.getText().toString().toLowerCase().indexOf("u")==-1&& input.getText().toString().toLowerCase().indexOf("v")==-1&& input.getText().toString().toLowerCase().indexOf("w")==-1&& input.getText().toString().toLowerCase().indexOf("x")==-1&& input.getText().toString().toLowerCase().indexOf("y")==-1&& input.getText().toString().toLowerCase().indexOf("z")==-1&& input.getText().toString().toLowerCase().indexOf("0")==-1&& input.getText().toString().toLowerCase().indexOf("1")==-1&& input.getText().toString().toLowerCase().indexOf("2")==-1&& input.getText().toString().toLowerCase().indexOf("3")==-1&& input.getText().toString().toLowerCase().indexOf("4")==-1&& input.getText().toString().toLowerCase().indexOf("5")==-1&& input.getText().toString().toLowerCase().indexOf("6")==-1&& input.getText().toString().toLowerCase().indexOf("7")==-1&& input.getText().toString().toLowerCase().indexOf("8")==-1&& input.getText().toString().toLowerCase().indexOf("9")==-1) {
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
                    leetspeakcodeencrypt();
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
                    leetspeakcodedecrypt();
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

    public void leetspeakcodeencrypt(){

        inputString = input.getText().toString().toLowerCase();

        /*if (inputString
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
        }*/

        if(1==0) {}

        else{
            inputString = inputString
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

                    .replaceAll("⁞zeᴥ ", "⁞27")
                    .replaceAll("⁞onᴥ ", "⁞28")
                    .replaceAll("⁞twᴥ ", "⁞29")
                    .replaceAll("⁞thᴥ ", "⁞30")
                    .replaceAll("⁞foᴥ ", "⁞31")
                    .replaceAll("⁞fiᴥ ", "⁞32")
                    .replaceAll("⁞siᴥ ", "⁞33")
                    .replaceAll("⁞seᴥ ", "⁞34")
                    .replaceAll("⁞eiᴥ ", "⁞35")
                    .replaceAll("⁞niᴥ ", "⁞36")

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
            ;

            outputString = inputString
                    .replaceAll("⁞01", "4")
                    .replaceAll("⁞02", "8")
                    .replaceAll("⁞03", "<")
                    .replaceAll("⁞04", "|)")
                    .replaceAll("⁞05", "3")
                    .replaceAll("⁞06", "|=")
                    .replaceAll("⁞07", "6")
                    .replaceAll("⁞08", "|-|")
                    .replaceAll("⁞09", "1")
                    .replaceAll("⁞10", "_|")
                    .replaceAll("⁞11", "|<")
                    .replaceAll("⁞12", "|_")
                    .replaceAll("⁞13", "|\\\\/|")
                    .replaceAll("⁞14", "|\\\\|")
                    .replaceAll("⁞15", "0")
                    .replaceAll("⁞16", "|O")
                    .replaceAll("⁞17", "O_")
                    .replaceAll("⁞18", "|2")
                    .replaceAll("⁞19", "5")
                    .replaceAll("⁞20", "7")
                    .replaceAll("⁞21", "|_|")
                    .replaceAll("⁞22", "\\\\/")
                    .replaceAll("⁞23", "\\\\/\\\\/")
                    .replaceAll("⁞24", "><")
                    .replaceAll("⁞25", "`/")
                    .replaceAll("⁞26", "2")

                    .replaceAll("⁞27", "O")
                    .replaceAll("⁞28", "I")
                    .replaceAll("⁞29", "Z")
                    .replaceAll("⁞30", "E")
                    .replaceAll("⁞31", "A")
                    .replaceAll("⁞32", "S")
                    .replaceAll("⁞33", "G")
                    .replaceAll("⁞34", "T")
                    .replaceAll("⁞35", "B")
                    .replaceAll("⁞36", "P")
            ;

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void leetspeakcodedecrypt(){

        String leetspeakcode = input.getText().toString().toLowerCase()
                //---number value---

                .replaceAll("0", "⁞ze⁞")
                .replaceAll("1", "⁞on⁞")
                .replaceAll("2", "⁞tw⁞")
                .replaceAll("3", "⁞th⁞")
                .replaceAll("4", "⁞fo⁞")
                .replaceAll("5", "⁞fi⁞")
                .replaceAll("6", "⁞si⁞")
                .replaceAll("7", "⁞se⁞")
                .replaceAll("8", "⁞ei⁞")
                .replaceAll("9", "⁞ni⁞")

                //***number value***

                //---debug---
                .replaceAll("\\]\\[\\]\\[", "⁞12⁞12")
                .replaceAll("⁞on⁞⁞th⁞⁞th⁞⁞se⁞", "⁞12⁞05⁞05⁞20")
                .replaceAll("sp⁞th⁞⁞fo⁞k", "⁞19⁞16⁞05⁞01⁞11")
                .replaceAll("it's", "⁞09⁞20'⁞19")
                .replaceAll("tim⁞th⁞", "⁞20⁞09⁞13⁞05")
                .replaceAll("t⁞ze⁞", "⁞20⁞15")
                .replaceAll("l⁞th⁞⁞fo⁞rn", "⁞12⁞05⁞01⁞18⁞14")
                .replaceAll("h⁞ze⁞w", "⁞08⁞15⁞23")
                //.replaceAll("t⁞ze⁞", "⁞20⁞15")
                .replaceAll("r⁞th⁞⁞fo⁞d", "⁞18⁞05⁞01⁞04")
                .replaceAll("⁞fo⁞ga⁞on⁞n", "⁞01⁞07⁞01⁞09⁞14")
                //***debug***

                //---unique characers---

                //eight characters
                .replaceAll("\\]\\[\\\\\\\\//\\]\\[", "⁞13")

                //seven characters

                //six characters
                .replaceAll("]\\[\\\\\\\\\\]\\[", "⁞14")
                .replaceAll("\\[\\]\\\\/\\]\\[", "⁞13")

                //five characters
                .replaceAll("\\[\\]v\\[\\]", "⁞13")

                //four characters
                .replaceAll("\\\\/\\\\/", "⁞23")
                .replaceAll("\\(/\\\\\\)", "⁞23")
                .replaceAll("\\|/\\\\\\|", "⁞23")

                .replaceAll("\\|\\\\/\\|", "⁞13")
                .replaceAll("/\\\\/\\\\", "⁞13")

                //three characters
                .replaceAll("\\\\\\^/", "⁞23")
                .replaceAll("\\\\x/", "⁞23")

                .replaceAll("'//", "⁞23")

                .replaceAll("\\|_\\|", "⁞21")
                .replaceAll("\\\\_\\\\", "⁞21")
                .replaceAll("/_/", "⁞21")
                .replaceAll("\\\\_/", "⁞21")
                .replaceAll("\\(_\\)", "⁞21")
                .replaceAll("\\[_\\]", "⁞21")
                .replaceAll("\\{_\\}", "⁞21")

                .replaceAll("\\(,\\)", "⁞17")

                .replaceAll("\\|\\\\\\|", "⁞14")

                .replaceAll("/x\\\\", "⁞13")
                .replaceAll("\\(v\\)", "⁞13")
                .replaceAll("//\\.", "⁞13")
                .replaceAll("\\.\\\\\\\\", "⁞13")

                .replaceAll("\\|-\\|", "⁞08")
                .replaceAll("\\[-\\]", "⁞08")
                .replaceAll("\\{-\\}", "⁞08")
                .replaceAll("\\|=\\|", "⁞08")
                .replaceAll("\\[=\\]", "⁞08")
                .replaceAll("\\{=\\}", "⁞08")

                //two characters
                .replaceAll(">_", "⁞26")

                .replaceAll("`/", "⁞25")

                .replaceAll("><", "⁞24")

                .replaceAll("\\}\\{", "⁞24")
                .replaceAll("\\)\\(", "⁞24")

                .replaceAll("\\\\\\\\'", "⁞23")

                .replaceAll("vv", "⁞23")

                .replaceAll("\\|⁞tw⁞", "⁞18")
                .replaceAll("⁞on⁞⁞tw⁞", "⁞18")
                .replaceAll("\\.-", "⁞18")
                .replaceAll("\\|\\^", "⁞18")
                .replaceAll("l⁞tw⁞", "⁞18")

                .replaceAll("o_", "⁞17")

                .replaceAll("\\|o", "⁞16")
                .replaceAll("\\|>", "⁞16")
                .replaceAll("\\|\\*", "⁞16")
                .replaceAll("\\|°", "⁞16")
                .replaceAll("\\|d", "⁞16")
                .replaceAll("/o", "⁞16")

                .replaceAll("\\(\\)", "⁞15")
                .replaceAll("\\{\\}", "⁞15")
                .replaceAll("<>", "⁞15")

                .replaceAll("/v", "⁞14")

                .replaceAll("\\^\\^", "⁞13")
                .replaceAll("n\\\\", "⁞13")

                .replaceAll("\\|<", "⁞11")
                .replaceAll("⁞on⁞<", "⁞11")
                .replaceAll("l<", "⁞11")
                .replaceAll("\\|\\{", "⁞11")
                .replaceAll("l\\{", "⁞11")

                .replaceAll("_⁞se⁞", "⁞10")
                .replaceAll("_\\]", "⁞10")
                .replaceAll("_\\}", "⁞10")

                .replaceAll("\\[\\+", "⁞07")

                .replaceAll("ph", "⁞06")

                .replaceAll("\\|#", "⁞06")
                .replaceAll("\\|\"", "⁞06")

                .replaceAll("\\|\\)", "⁞04")
                //.replaceAll("\\|\\}", "⁞04")
                .replaceAll("\\|\\]", "⁞04")

                .replaceAll("\\|⁞th⁞", "⁞02")
                .replaceAll("⁞on⁞⁞th⁞", "⁞02")
                .replaceAll("\\|\\}", "⁞02")
                .replaceAll("\\|:", "⁞02")
                .replaceAll("\\|⁞ei⁞", "⁞02")
                .replaceAll("⁞on⁞⁞ei⁞", "⁞02")
                .replaceAll("\\|b", "⁞02")
                .replaceAll("\\|⁞ei⁞", "⁞02")
                .replaceAll("lo", "⁞02")
                .replaceAll("\\|o", "⁞02")

                .replaceAll("/-\\\\", "⁞01")
                .replaceAll("/_\\\\", "⁞01")

                //one character
                .replaceAll("⁞se⁞_", "⁞26")

                .replaceAll("¥", "⁞25")
                .replaceAll("%", "⁞24")

                .replaceAll("⁞se⁞`", "⁞20")
                .replaceAll("'\\|'", "⁞20")

                .replaceAll("⁞fi⁞", "⁞19")
                .replaceAll("\\$", "⁞19")
                .replaceAll("§", "⁞19")

                .replaceAll("я", "⁞18")

                .replaceAll("⁞ni⁞", "⁞17")

                //.replaceAll("⁞si⁞", "⁞02")
                .replaceAll("⁞ei⁞", "⁞02")

                //***unique characers***

                //---none unique characers---

                //three characters
                .replaceAll("/\\\\/", "⁞14")

                //two characters
                .replaceAll("\\\\/", "⁞22")

                .replaceAll("\\[\\]", "⁞15")

                //.replaceAll("⁞fo⁞⁞fo⁞", "⁞13")

                .replaceAll("\\|_", "⁞12")
                .replaceAll("]\\[", "⁞12")

                .replaceAll("_\\|", "⁞10")
                .replaceAll("_/", "⁞10")
                .replaceAll("_\\)", "⁞10")

                .replaceAll("\\|=", "⁞06")

                .replaceAll("/\\\\", "⁞01")

                //one character
                .replaceAll("⁞tw⁞", "⁞26")

                .replaceAll("\\*", "⁞24")

                .replaceAll("⁞se⁞", "⁞20")
                .replaceAll("\\+", "⁞20")

                //.replaceAll("0", "⁞17")

                .replaceAll("⁞ze⁞", "⁞15")

                //.replaceAll("\\|", "⁞12")
                //.replaceAll("1", "⁞12")

                .replaceAll("⁞on⁞", "⁞09")
                .replaceAll("\\|", "⁞09")
                .replaceAll("!", "⁞09")
                .replaceAll("⁞ni⁞", "⁞09")

                //.replaceAll("⁞fo⁞", "⁞08")

                .replaceAll("⁞si⁞", "⁞07")
                //.replaceAll("\\[", "⁞07")
                .replaceAll("-", "⁞07")

                .replaceAll("⁞th⁞", "⁞05")

                .replaceAll("<", "⁞03")
                .replaceAll("\\{", "⁞03")
                .replaceAll("\\[", "⁞03")
                .replaceAll("\\(", "⁞03")


                .replaceAll("⁞fo⁞", "⁞01")
                .replaceAll("@", "⁞01")

                //***none unique characers***

                //---characer value of numbers---

                .replaceAll("o", "⁞27")
                .replaceAll("d", "⁞27")
                .replaceAll("i", "⁞28")
                .replaceAll("l", "⁞28")
                .replaceAll("z", "⁞29")
                .replaceAll("e", "⁞30")
                .replaceAll("a", "⁞31")
                .replaceAll("h", "⁞31")
                .replaceAll("s", "⁞32")
                .replaceAll("g", "⁞33")
                //.replaceAll("b", "⁞33")
                .replaceAll("t", "⁞34")
                //.replaceAll("l", "⁞34")
                .replaceAll("j", "⁞34")
                .replaceAll("b", "⁞35")
                .replaceAll("p", "⁞36")

                //***characer value of numbers***

                //---debug---

                .replaceAll("⁞21_","⁞12⁞12")

                //***debug***
                ;

        String chk4unsprtdchar = leetspeakcode.toString()
                .replaceAll("⁞01", "")
                .replaceAll("⁞02", "")
                .replaceAll("⁞03", "")
                .replaceAll("⁞04", "")
                .replaceAll("⁞05", "")
                .replaceAll("⁞06", "")
                .replaceAll("⁞07", "")
                .replaceAll("⁞08", "")
                .replaceAll("⁞09", "")
                .replaceAll("⁞10", "")
                .replaceAll("⁞11", "")
                .replaceAll("⁞12", "")
                .replaceAll("⁞13", "")
                .replaceAll("⁞14", "")
                .replaceAll("⁞15", "")
                .replaceAll("⁞16", "")
                .replaceAll("⁞17", "")
                .replaceAll("⁞18", "")
                .replaceAll("⁞19", "")
                .replaceAll("⁞20", "")
                .replaceAll("⁞21", "")
                .replaceAll("⁞22", "")
                .replaceAll("⁞23", "")
                .replaceAll("⁞24", "")
                .replaceAll("⁞25", "")
                .replaceAll("⁞26", "")
                .replaceAll("⁞27", "")
                .replaceAll("⁞28", "")
                .replaceAll("⁞29", "")
                .replaceAll("⁞30", "")
                .replaceAll("⁞31", "")
                .replaceAll("⁞32", "")
                .replaceAll("⁞33", "")
                .replaceAll("⁞34", "")
                .replaceAll("⁞35", "")
                .replaceAll("⁞36", "")
                .replaceAll(" ","")
                .replaceAll("\n","")
                ;

        /*if(chk4unsprtdchar.indexOf("A")!=-1|| chk4unsprtdchar.indexOf("B")!=-1|| chk4unsprtdchar.indexOf("C")!=-1|| chk4unsprtdchar.indexOf("D")!=-1|| chk4unsprtdchar.indexOf("E")!=-1|| chk4unsprtdchar.indexOf("F")!=-1|| chk4unsprtdchar.indexOf("G")!=-1|| chk4unsprtdchar.indexOf("H")!=-1|| chk4unsprtdchar.indexOf("I")!=-1|| chk4unsprtdchar.indexOf("J")!=-1|| chk4unsprtdchar.indexOf("K")!=-1|| chk4unsprtdchar.indexOf("L")!=-1|| chk4unsprtdchar.indexOf("M")!=-1|| chk4unsprtdchar.indexOf("N")!=-1|| chk4unsprtdchar.indexOf("O")!=-1|| chk4unsprtdchar.indexOf("P")!=-1|| chk4unsprtdchar.indexOf("Q")!=-1|| chk4unsprtdchar.indexOf("R")!=-1|| chk4unsprtdchar.indexOf("S")!=-1|| chk4unsprtdchar.indexOf("T")!=-1|| chk4unsprtdchar.indexOf("U")!=-1|| chk4unsprtdchar.indexOf("V")!=-1|| chk4unsprtdchar.indexOf("W")!=-1|| chk4unsprtdchar.indexOf("X")!=-1|| chk4unsprtdchar.indexOf("Y")!=-1|| chk4unsprtdchar.indexOf("Z")!=-1) {
            Toast.makeText(this, "Not a valid code.", Toast.LENGTH_SHORT).show();
        }*/

        if (leetspeakcode.indexOf("⁞01")!=-1|| leetspeakcode.indexOf("⁞02")!=-1|| leetspeakcode.indexOf("⁞03")!=-1|| leetspeakcode.indexOf("⁞04")!=-1|| leetspeakcode.indexOf("⁞05")!=-1|| leetspeakcode.indexOf("⁞06")!=-1|| leetspeakcode.indexOf("⁞07")!=-1|| leetspeakcode.indexOf("⁞08")!=-1|| leetspeakcode.indexOf("⁞09")!=-1|| leetspeakcode.indexOf("⁞10")!=-1|| leetspeakcode.indexOf("⁞11")!=-1|| leetspeakcode.indexOf("⁞12")!=-1|| leetspeakcode.indexOf("⁞13")!=-1|| leetspeakcode.indexOf("⁞14")!=-1|| leetspeakcode.indexOf("⁞15")!=-1|| leetspeakcode.indexOf("⁞16")!=-1|| leetspeakcode.indexOf("⁞17")!=-1|| leetspeakcode.indexOf("⁞18")!=-1|| leetspeakcode.indexOf("⁞19")!=-1|| leetspeakcode.indexOf("⁞20")!=-1|| leetspeakcode.indexOf("⁞21")!=-1|| leetspeakcode.indexOf("⁞22")!=-1|| leetspeakcode.indexOf("⁞23")!=-1|| leetspeakcode.indexOf("⁞24")!=-1|| leetspeakcode.indexOf("⁞25")!=-1|| leetspeakcode.indexOf("⁞26")!=-1|| leetspeakcode.indexOf("⁞27")!=-1|| leetspeakcode.indexOf("⁞28")!=-1|| leetspeakcode.indexOf("⁞29")!=-1|| leetspeakcode.indexOf("⁞30")!=-1|| leetspeakcode.indexOf("⁞31")!=-1|| leetspeakcode.indexOf("⁞32")!=-1|| leetspeakcode.indexOf("⁞33")!=-1|| leetspeakcode.indexOf("⁞34")!=-1|| leetspeakcode.indexOf("⁞35")!=-1|| leetspeakcode.indexOf("⁞36")!=-1){

            if(chk4unsprtdchar.length()!=0){
                inputString = leetspeakcode;

                outputString = inputString
                        .replaceAll("⁞01", "a")
                        .replaceAll("⁞02", "b")
                        .replaceAll("⁞03", "c")
                        .replaceAll("⁞04", "d")
                        .replaceAll("⁞05", "e")
                        .replaceAll("⁞06", "f")
                        .replaceAll("⁞07", "g")
                        .replaceAll("⁞08", "h")
                        .replaceAll("⁞09", "i")
                        .replaceAll("⁞10", "j")
                        .replaceAll("⁞11", "k")
                        .replaceAll("⁞12", "l")
                        .replaceAll("⁞13", "m")
                        .replaceAll("⁞14", "n")
                        .replaceAll("⁞15", "o")
                        .replaceAll("⁞16", "p")
                        .replaceAll("⁞17", "q")
                        .replaceAll("⁞18", "r")
                        .replaceAll("⁞19", "s")
                        .replaceAll("⁞20", "t")
                        .replaceAll("⁞21", "u")
                        .replaceAll("⁞22", "v")
                        .replaceAll("⁞23", "w")
                        .replaceAll("⁞24", "x")
                        .replaceAll("⁞25", "y")
                        .replaceAll("⁞26", "z")
                        .replaceAll("⁞27", "0")
                        .replaceAll("⁞28", "1")
                        .replaceAll("⁞29", "2")
                        .replaceAll("⁞30", "3")
                        .replaceAll("⁞31", "4")
                        .replaceAll("⁞32", "5")
                        .replaceAll("⁞33", "6")
                        .replaceAll("⁞34", "7")
                        .replaceAll("⁞35", "8")
                        .replaceAll("⁞36", "9")
                ;

                output.setText(outputString);


                Toast.makeText(getApplicationContext(), "Some characters are not converted.", Toast.LENGTH_SHORT).show();

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            else{
                inputString = leetspeakcode;

                outputString = inputString
                        .replaceAll("⁞01", "a")
                        .replaceAll("⁞02", "b")
                        .replaceAll("⁞03", "c")
                        .replaceAll("⁞04", "d")
                        .replaceAll("⁞05", "e")
                        .replaceAll("⁞06", "f")
                        .replaceAll("⁞07", "g")
                        .replaceAll("⁞08", "h")
                        .replaceAll("⁞09", "i")
                        .replaceAll("⁞10", "j")
                        .replaceAll("⁞11", "k")
                        .replaceAll("⁞12", "l")
                        .replaceAll("⁞13", "m")
                        .replaceAll("⁞14", "n")
                        .replaceAll("⁞15", "o")
                        .replaceAll("⁞16", "p")
                        .replaceAll("⁞17", "q")
                        .replaceAll("⁞18", "r")
                        .replaceAll("⁞19", "s")
                        .replaceAll("⁞20", "t")
                        .replaceAll("⁞21", "u")
                        .replaceAll("⁞22", "v")
                        .replaceAll("⁞23", "w")
                        .replaceAll("⁞24", "x")
                        .replaceAll("⁞25", "y")
                        .replaceAll("⁞26", "z")
                        .replaceAll("⁞27", "0")
                        .replaceAll("⁞28", "1")
                        .replaceAll("⁞29", "2")
                        .replaceAll("⁞30", "3")
                        .replaceAll("⁞31", "4")
                        .replaceAll("⁞32", "5")
                        .replaceAll("⁞33", "6")
                        .replaceAll("⁞34", "7")
                        .replaceAll("⁞35", "8")
                        .replaceAll("⁞36", "9")
                ;

                output.setText(outputString);


                Toast.makeText(getApplicationContext(), "Decrypted succesfully.", Toast.LENGTH_SHORT).show();

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }

        else {
            Toast.makeText(this, "Not a valid code.", Toast.LENGTH_SHORT).show();
        }

    }
}
