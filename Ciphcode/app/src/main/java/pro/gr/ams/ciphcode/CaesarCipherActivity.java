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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CaesarCipherActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String codename = "Caesar Cipher";
    String codesample = "Dbftbs Djqifs";

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

    private LinearLayout dialog_choose_layout;
    private TextView dialog_choose_question;
    private Spinner dialog_choose_spinner;
    private String dialog_choose_selected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        overridePendingTransition(R.anim.act2_enter,R.anim.act2_exit);

        code();

        info();

        chooseoption();

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

    public void chooseoption() {
        dialog_choose_layout = (LinearLayout)findViewById(R.id.dialog_choose_layout);
        dialog_choose_layout.setVisibility(View.VISIBLE);
        dialog_choose_question = (TextView)findViewById(R.id.dialog_choose_question);
        dialog_choose_question.setText("Shift of");
        dialog_choose_spinner = (Spinner)findViewById(R.id.dialog_choose_spinner);
        dialog_choose_spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("1");
        categories.add("2");
        categories.add("3");
        categories.add("4");
        categories.add("5");
        categories.add("6");
        categories.add("7");
        categories.add("8");
        categories.add("9");
        categories.add("10");
        categories.add("11");
        categories.add("12");
        categories.add("13");
        categories.add("14");
        categories.add("15");
        categories.add("16");
        categories.add("17");
        categories.add("18");
        categories.add("19");
        categories.add("20");
        categories.add("21");
        categories.add("22");
        categories.add("23");
        categories.add("24");
        categories.add("25");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialog_choose_spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        dialog_choose_selected = item;
    }

    public void onNothingSelected(AdapterView<?> arg0) {
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
                    caesarcipherEncrypt();
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
                    caesarcipherDecrypt();
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

    public void caesarcipherEncrypt(){
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

        if(dialog_choose_selected.equals("1")) {
            outputString = inputString
                    .replaceAll("⁞01", "b")
                    .replaceAll("⁞02", "c")
                    .replaceAll("⁞03", "d")
                    .replaceAll("⁞04", "e")
                    .replaceAll("⁞05", "f")
                    .replaceAll("⁞06", "g")
                    .replaceAll("⁞07", "h")
                    .replaceAll("⁞08", "i")
                    .replaceAll("⁞09", "j")
                    .replaceAll("⁞10", "k")
                    .replaceAll("⁞11", "l")
                    .replaceAll("⁞12", "m")
                    .replaceAll("⁞13", "n")
                    .replaceAll("⁞14", "o")
                    .replaceAll("⁞15", "p")
                    .replaceAll("⁞16", "q")
                    .replaceAll("⁞17", "r")
                    .replaceAll("⁞18", "s")
                    .replaceAll("⁞19", "t")
                    .replaceAll("⁞20", "u")
                    .replaceAll("⁞21", "v")
                    .replaceAll("⁞22", "w")
                    .replaceAll("⁞23", "x")
                    .replaceAll("⁞24", "y")
                    .replaceAll("⁞25", "z")
                    .replaceAll("⁞26", "a")
                    .replaceAll("ᴥ01", "B")
                    .replaceAll("ᴥ02", "C")
                    .replaceAll("ᴥ03", "D")
                    .replaceAll("ᴥ04", "E")
                    .replaceAll("ᴥ05", "F")
                    .replaceAll("ᴥ06", "G")
                    .replaceAll("ᴥ07", "H")
                    .replaceAll("ᴥ08", "I")
                    .replaceAll("ᴥ09", "J")
                    .replaceAll("ᴥ10", "K")
                    .replaceAll("ᴥ11", "L")
                    .replaceAll("ᴥ12", "M")
                    .replaceAll("ᴥ13", "N")
                    .replaceAll("ᴥ14", "O")
                    .replaceAll("ᴥ15", "P")
                    .replaceAll("ᴥ16", "Q")
                    .replaceAll("ᴥ17", "R")
                    .replaceAll("ᴥ18", "S")
                    .replaceAll("ᴥ19", "T")
                    .replaceAll("ᴥ20", "U")
                    .replaceAll("ᴥ21", "V")
                    .replaceAll("ᴥ22", "W")
                    .replaceAll("ᴥ23", "X")
                    .replaceAll("ᴥ24", "Y")
                    .replaceAll("ᴥ25", "Z")
                    .replaceAll("ᴥ26", "A")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("2")) {
            outputString = inputString
                    .replaceAll("⁞01", "c")
                    .replaceAll("⁞02", "d")
                    .replaceAll("⁞03", "e")
                    .replaceAll("⁞04", "f")
                    .replaceAll("⁞05", "g")
                    .replaceAll("⁞06", "h")
                    .replaceAll("⁞07", "i")
                    .replaceAll("⁞08", "j")
                    .replaceAll("⁞09", "k")
                    .replaceAll("⁞10", "l")
                    .replaceAll("⁞11", "m")
                    .replaceAll("⁞12", "n")
                    .replaceAll("⁞13", "o")
                    .replaceAll("⁞14", "p")
                    .replaceAll("⁞15", "q")
                    .replaceAll("⁞16", "r")
                    .replaceAll("⁞17", "s")
                    .replaceAll("⁞18", "t")
                    .replaceAll("⁞19", "u")
                    .replaceAll("⁞20", "v")
                    .replaceAll("⁞21", "w")
                    .replaceAll("⁞22", "x")
                    .replaceAll("⁞23", "y")
                    .replaceAll("⁞24", "z")
                    .replaceAll("⁞25", "a")
                    .replaceAll("⁞26", "b")
                    .replaceAll("ᴥ01", "C")
                    .replaceAll("ᴥ02", "D")
                    .replaceAll("ᴥ03", "E")
                    .replaceAll("ᴥ04", "F")
                    .replaceAll("ᴥ05", "G")
                    .replaceAll("ᴥ06", "H")
                    .replaceAll("ᴥ07", "I")
                    .replaceAll("ᴥ08", "J")
                    .replaceAll("ᴥ09", "K")
                    .replaceAll("ᴥ10", "L")
                    .replaceAll("ᴥ11", "M")
                    .replaceAll("ᴥ12", "N")
                    .replaceAll("ᴥ13", "O")
                    .replaceAll("ᴥ14", "P")
                    .replaceAll("ᴥ15", "Q")
                    .replaceAll("ᴥ16", "R")
                    .replaceAll("ᴥ17", "S")
                    .replaceAll("ᴥ18", "T")
                    .replaceAll("ᴥ19", "U")
                    .replaceAll("ᴥ20", "V")
                    .replaceAll("ᴥ21", "W")
                    .replaceAll("ᴥ22", "X")
                    .replaceAll("ᴥ23", "Y")
                    .replaceAll("ᴥ24", "Z")
                    .replaceAll("ᴥ25", "A")
                    .replaceAll("ᴥ26", "B")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("3")) {
            outputString = inputString
                    .replaceAll("⁞01", "d")
                    .replaceAll("⁞02", "e")
                    .replaceAll("⁞03", "f")
                    .replaceAll("⁞04", "g")
                    .replaceAll("⁞05", "h")
                    .replaceAll("⁞06", "i")
                    .replaceAll("⁞07", "j")
                    .replaceAll("⁞08", "k")
                    .replaceAll("⁞09", "l")
                    .replaceAll("⁞10", "m")
                    .replaceAll("⁞11", "n")
                    .replaceAll("⁞12", "o")
                    .replaceAll("⁞13", "p")
                    .replaceAll("⁞14", "q")
                    .replaceAll("⁞15", "r")
                    .replaceAll("⁞16", "s")
                    .replaceAll("⁞17", "t")
                    .replaceAll("⁞18", "u")
                    .replaceAll("⁞19", "v")
                    .replaceAll("⁞20", "w")
                    .replaceAll("⁞21", "x")
                    .replaceAll("⁞22", "y")
                    .replaceAll("⁞23", "z")
                    .replaceAll("⁞24", "a")
                    .replaceAll("⁞25", "b")
                    .replaceAll("⁞26", "c")
                    .replaceAll("ᴥ01", "D")
                    .replaceAll("ᴥ02", "E")
                    .replaceAll("ᴥ03", "F")
                    .replaceAll("ᴥ04", "G")
                    .replaceAll("ᴥ05", "H")
                    .replaceAll("ᴥ06", "I")
                    .replaceAll("ᴥ07", "J")
                    .replaceAll("ᴥ08", "K")
                    .replaceAll("ᴥ09", "L")
                    .replaceAll("ᴥ10", "M")
                    .replaceAll("ᴥ11", "N")
                    .replaceAll("ᴥ12", "O")
                    .replaceAll("ᴥ13", "P")
                    .replaceAll("ᴥ14", "Q")
                    .replaceAll("ᴥ15", "R")
                    .replaceAll("ᴥ16", "S")
                    .replaceAll("ᴥ17", "T")
                    .replaceAll("ᴥ18", "U")
                    .replaceAll("ᴥ19", "V")
                    .replaceAll("ᴥ20", "W")
                    .replaceAll("ᴥ21", "X")
                    .replaceAll("ᴥ22", "Y")
                    .replaceAll("ᴥ23", "Z")
                    .replaceAll("ᴥ24", "A")
                    .replaceAll("ᴥ25", "B")
                    .replaceAll("ᴥ26", "C")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("4")) {
            outputString = inputString
                    .replaceAll("⁞01", "e")
                    .replaceAll("⁞02", "f")
                    .replaceAll("⁞03", "g")
                    .replaceAll("⁞04", "h")
                    .replaceAll("⁞05", "i")
                    .replaceAll("⁞06", "j")
                    .replaceAll("⁞07", "k")
                    .replaceAll("⁞08", "l")
                    .replaceAll("⁞09", "m")
                    .replaceAll("⁞10", "n")
                    .replaceAll("⁞11", "o")
                    .replaceAll("⁞12", "p")
                    .replaceAll("⁞13", "q")
                    .replaceAll("⁞14", "r")
                    .replaceAll("⁞15", "s")
                    .replaceAll("⁞16", "t")
                    .replaceAll("⁞17", "u")
                    .replaceAll("⁞18", "v")
                    .replaceAll("⁞19", "w")
                    .replaceAll("⁞20", "x")
                    .replaceAll("⁞21", "y")
                    .replaceAll("⁞22", "z")
                    .replaceAll("⁞23", "a")
                    .replaceAll("⁞24", "b")
                    .replaceAll("⁞25", "c")
                    .replaceAll("⁞26", "d")
                    .replaceAll("ᴥ01", "E")
                    .replaceAll("ᴥ02", "F")
                    .replaceAll("ᴥ03", "G")
                    .replaceAll("ᴥ04", "H")
                    .replaceAll("ᴥ05", "I")
                    .replaceAll("ᴥ06", "J")
                    .replaceAll("ᴥ07", "K")
                    .replaceAll("ᴥ08", "L")
                    .replaceAll("ᴥ09", "M")
                    .replaceAll("ᴥ10", "N")
                    .replaceAll("ᴥ11", "O")
                    .replaceAll("ᴥ12", "P")
                    .replaceAll("ᴥ13", "Q")
                    .replaceAll("ᴥ14", "R")
                    .replaceAll("ᴥ15", "S")
                    .replaceAll("ᴥ16", "T")
                    .replaceAll("ᴥ17", "U")
                    .replaceAll("ᴥ18", "V")
                    .replaceAll("ᴥ19", "W")
                    .replaceAll("ᴥ20", "X")
                    .replaceAll("ᴥ21", "Y")
                    .replaceAll("ᴥ22", "Z")
                    .replaceAll("ᴥ23", "A")
                    .replaceAll("ᴥ24", "B")
                    .replaceAll("ᴥ25", "C")
                    .replaceAll("ᴥ26", "D")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("5")) {
            outputString = inputString
                    .replaceAll("⁞01", "f")
                    .replaceAll("⁞02", "g")
                    .replaceAll("⁞03", "h")
                    .replaceAll("⁞04", "i")
                    .replaceAll("⁞05", "j")
                    .replaceAll("⁞06", "k")
                    .replaceAll("⁞07", "l")
                    .replaceAll("⁞08", "m")
                    .replaceAll("⁞09", "n")
                    .replaceAll("⁞10", "o")
                    .replaceAll("⁞11", "p")
                    .replaceAll("⁞12", "q")
                    .replaceAll("⁞13", "r")
                    .replaceAll("⁞14", "s")
                    .replaceAll("⁞15", "t")
                    .replaceAll("⁞16", "u")
                    .replaceAll("⁞17", "v")
                    .replaceAll("⁞18", "w")
                    .replaceAll("⁞19", "x")
                    .replaceAll("⁞20", "y")
                    .replaceAll("⁞21", "z")
                    .replaceAll("⁞22", "a")
                    .replaceAll("⁞23", "b")
                    .replaceAll("⁞24", "c")
                    .replaceAll("⁞25", "d")
                    .replaceAll("⁞26", "e")
                    .replaceAll("ᴥ01", "F")
                    .replaceAll("ᴥ02", "G")
                    .replaceAll("ᴥ03", "H")
                    .replaceAll("ᴥ04", "I")
                    .replaceAll("ᴥ05", "J")
                    .replaceAll("ᴥ06", "K")
                    .replaceAll("ᴥ07", "L")
                    .replaceAll("ᴥ08", "M")
                    .replaceAll("ᴥ09", "N")
                    .replaceAll("ᴥ10", "O")
                    .replaceAll("ᴥ11", "P")
                    .replaceAll("ᴥ12", "Q")
                    .replaceAll("ᴥ13", "R")
                    .replaceAll("ᴥ14", "S")
                    .replaceAll("ᴥ15", "T")
                    .replaceAll("ᴥ16", "U")
                    .replaceAll("ᴥ17", "V")
                    .replaceAll("ᴥ18", "W")
                    .replaceAll("ᴥ19", "X")
                    .replaceAll("ᴥ20", "Y")
                    .replaceAll("ᴥ21", "Z")
                    .replaceAll("ᴥ22", "A")
                    .replaceAll("ᴥ23", "B")
                    .replaceAll("ᴥ24", "C")
                    .replaceAll("ᴥ25", "D")
                    .replaceAll("ᴥ26", "E")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("6")) {
            outputString = inputString
                    .replaceAll("⁞01", "g")
                    .replaceAll("⁞02", "h")
                    .replaceAll("⁞03", "i")
                    .replaceAll("⁞04", "j")
                    .replaceAll("⁞05", "k")
                    .replaceAll("⁞06", "l")
                    .replaceAll("⁞07", "m")
                    .replaceAll("⁞08", "n")
                    .replaceAll("⁞09", "o")
                    .replaceAll("⁞10", "p")
                    .replaceAll("⁞11", "q")
                    .replaceAll("⁞12", "r")
                    .replaceAll("⁞13", "s")
                    .replaceAll("⁞14", "t")
                    .replaceAll("⁞15", "u")
                    .replaceAll("⁞16", "v")
                    .replaceAll("⁞17", "w")
                    .replaceAll("⁞18", "x")
                    .replaceAll("⁞19", "y")
                    .replaceAll("⁞20", "z")
                    .replaceAll("⁞21", "a")
                    .replaceAll("⁞22", "b")
                    .replaceAll("⁞23", "c")
                    .replaceAll("⁞24", "d")
                    .replaceAll("⁞25", "e")
                    .replaceAll("⁞26", "f")
                    .replaceAll("ᴥ01", "G")
                    .replaceAll("ᴥ02", "H")
                    .replaceAll("ᴥ03", "I")
                    .replaceAll("ᴥ04", "J")
                    .replaceAll("ᴥ05", "K")
                    .replaceAll("ᴥ06", "L")
                    .replaceAll("ᴥ07", "M")
                    .replaceAll("ᴥ08", "N")
                    .replaceAll("ᴥ09", "O")
                    .replaceAll("ᴥ10", "P")
                    .replaceAll("ᴥ11", "Q")
                    .replaceAll("ᴥ12", "R")
                    .replaceAll("ᴥ13", "S")
                    .replaceAll("ᴥ14", "T")
                    .replaceAll("ᴥ15", "U")
                    .replaceAll("ᴥ16", "V")
                    .replaceAll("ᴥ17", "W")
                    .replaceAll("ᴥ18", "X")
                    .replaceAll("ᴥ19", "Y")
                    .replaceAll("ᴥ20", "Z")
                    .replaceAll("ᴥ21", "A")
                    .replaceAll("ᴥ22", "B")
                    .replaceAll("ᴥ23", "C")
                    .replaceAll("ᴥ24", "D")
                    .replaceAll("ᴥ25", "E")
                    .replaceAll("ᴥ26", "F")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("7")) {
            outputString = inputString
                    .replaceAll("⁞01", "h")
                    .replaceAll("⁞02", "i")
                    .replaceAll("⁞03", "j")
                    .replaceAll("⁞04", "k")
                    .replaceAll("⁞05", "l")
                    .replaceAll("⁞06", "m")
                    .replaceAll("⁞07", "n")
                    .replaceAll("⁞08", "o")
                    .replaceAll("⁞09", "p")
                    .replaceAll("⁞10", "q")
                    .replaceAll("⁞11", "r")
                    .replaceAll("⁞12", "s")
                    .replaceAll("⁞13", "t")
                    .replaceAll("⁞14", "u")
                    .replaceAll("⁞15", "v")
                    .replaceAll("⁞16", "w")
                    .replaceAll("⁞17", "x")
                    .replaceAll("⁞18", "y")
                    .replaceAll("⁞19", "z")
                    .replaceAll("⁞20", "a")
                    .replaceAll("⁞21", "b")
                    .replaceAll("⁞22", "c")
                    .replaceAll("⁞23", "d")
                    .replaceAll("⁞24", "e")
                    .replaceAll("⁞25", "f")
                    .replaceAll("⁞26", "g")
                    .replaceAll("ᴥ01", "H")
                    .replaceAll("ᴥ02", "I")
                    .replaceAll("ᴥ03", "J")
                    .replaceAll("ᴥ04", "K")
                    .replaceAll("ᴥ05", "L")
                    .replaceAll("ᴥ06", "M")
                    .replaceAll("ᴥ07", "N")
                    .replaceAll("ᴥ08", "O")
                    .replaceAll("ᴥ09", "P")
                    .replaceAll("ᴥ10", "Q")
                    .replaceAll("ᴥ11", "R")
                    .replaceAll("ᴥ12", "S")
                    .replaceAll("ᴥ13", "T")
                    .replaceAll("ᴥ14", "U")
                    .replaceAll("ᴥ15", "V")
                    .replaceAll("ᴥ16", "W")
                    .replaceAll("ᴥ17", "X")
                    .replaceAll("ᴥ18", "Y")
                    .replaceAll("ᴥ19", "Z")
                    .replaceAll("ᴥ20", "A")
                    .replaceAll("ᴥ21", "B")
                    .replaceAll("ᴥ22", "C")
                    .replaceAll("ᴥ23", "D")
                    .replaceAll("ᴥ24", "E")
                    .replaceAll("ᴥ25", "F")
                    .replaceAll("ᴥ26", "G")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("8")) {
            outputString = inputString
                    .replaceAll("⁞01", "i")
                    .replaceAll("⁞02", "j")
                    .replaceAll("⁞03", "k")
                    .replaceAll("⁞04", "l")
                    .replaceAll("⁞05", "m")
                    .replaceAll("⁞06", "n")
                    .replaceAll("⁞07", "o")
                    .replaceAll("⁞08", "p")
                    .replaceAll("⁞09", "q")
                    .replaceAll("⁞10", "r")
                    .replaceAll("⁞11", "s")
                    .replaceAll("⁞12", "t")
                    .replaceAll("⁞13", "u")
                    .replaceAll("⁞14", "v")
                    .replaceAll("⁞15", "w")
                    .replaceAll("⁞16", "x")
                    .replaceAll("⁞17", "y")
                    .replaceAll("⁞18", "z")
                    .replaceAll("⁞19", "a")
                    .replaceAll("⁞20", "b")
                    .replaceAll("⁞21", "c")
                    .replaceAll("⁞22", "d")
                    .replaceAll("⁞23", "e")
                    .replaceAll("⁞24", "f")
                    .replaceAll("⁞25", "g")
                    .replaceAll("⁞26", "h")
                    .replaceAll("ᴥ01", "I")
                    .replaceAll("ᴥ02", "J")
                    .replaceAll("ᴥ03", "K")
                    .replaceAll("ᴥ04", "L")
                    .replaceAll("ᴥ05", "M")
                    .replaceAll("ᴥ06", "N")
                    .replaceAll("ᴥ07", "O")
                    .replaceAll("ᴥ08", "P")
                    .replaceAll("ᴥ09", "Q")
                    .replaceAll("ᴥ10", "R")
                    .replaceAll("ᴥ11", "S")
                    .replaceAll("ᴥ12", "T")
                    .replaceAll("ᴥ13", "U")
                    .replaceAll("ᴥ14", "V")
                    .replaceAll("ᴥ15", "W")
                    .replaceAll("ᴥ16", "X")
                    .replaceAll("ᴥ17", "Y")
                    .replaceAll("ᴥ18", "Z")
                    .replaceAll("ᴥ19", "A")
                    .replaceAll("ᴥ20", "B")
                    .replaceAll("ᴥ21", "C")
                    .replaceAll("ᴥ22", "D")
                    .replaceAll("ᴥ23", "E")
                    .replaceAll("ᴥ24", "F")
                    .replaceAll("ᴥ25", "G")
                    .replaceAll("ᴥ26", "H")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("9")) {
            outputString = inputString
                    .replaceAll("⁞01", "j")
                    .replaceAll("⁞02", "k")
                    .replaceAll("⁞03", "l")
                    .replaceAll("⁞04", "m")
                    .replaceAll("⁞05", "n")
                    .replaceAll("⁞06", "o")
                    .replaceAll("⁞07", "p")
                    .replaceAll("⁞08", "q")
                    .replaceAll("⁞09", "r")
                    .replaceAll("⁞10", "s")
                    .replaceAll("⁞11", "t")
                    .replaceAll("⁞12", "u")
                    .replaceAll("⁞13", "v")
                    .replaceAll("⁞14", "w")
                    .replaceAll("⁞15", "x")
                    .replaceAll("⁞16", "y")
                    .replaceAll("⁞17", "z")
                    .replaceAll("⁞18", "a")
                    .replaceAll("⁞19", "b")
                    .replaceAll("⁞20", "c")
                    .replaceAll("⁞21", "d")
                    .replaceAll("⁞22", "e")
                    .replaceAll("⁞23", "f")
                    .replaceAll("⁞24", "g")
                    .replaceAll("⁞25", "h")
                    .replaceAll("⁞26", "i")
                    .replaceAll("ᴥ01", "J")
                    .replaceAll("ᴥ02", "K")
                    .replaceAll("ᴥ03", "L")
                    .replaceAll("ᴥ04", "M")
                    .replaceAll("ᴥ05", "N")
                    .replaceAll("ᴥ06", "O")
                    .replaceAll("ᴥ07", "P")
                    .replaceAll("ᴥ08", "Q")
                    .replaceAll("ᴥ09", "R")
                    .replaceAll("ᴥ10", "S")
                    .replaceAll("ᴥ11", "T")
                    .replaceAll("ᴥ12", "U")
                    .replaceAll("ᴥ13", "V")
                    .replaceAll("ᴥ14", "W")
                    .replaceAll("ᴥ15", "X")
                    .replaceAll("ᴥ16", "Y")
                    .replaceAll("ᴥ17", "Z")
                    .replaceAll("ᴥ18", "A")
                    .replaceAll("ᴥ19", "B")
                    .replaceAll("ᴥ20", "C")
                    .replaceAll("ᴥ21", "D")
                    .replaceAll("ᴥ22", "E")
                    .replaceAll("ᴥ23", "F")
                    .replaceAll("ᴥ24", "G")
                    .replaceAll("ᴥ25", "H")
                    .replaceAll("ᴥ26", "I")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("10")) {
            outputString = inputString
                    .replaceAll("⁞01", "k")
                    .replaceAll("⁞02", "l")
                    .replaceAll("⁞03", "m")
                    .replaceAll("⁞04", "n")
                    .replaceAll("⁞05", "o")
                    .replaceAll("⁞06", "p")
                    .replaceAll("⁞07", "q")
                    .replaceAll("⁞08", "r")
                    .replaceAll("⁞09", "s")
                    .replaceAll("⁞10", "t")
                    .replaceAll("⁞11", "u")
                    .replaceAll("⁞12", "v")
                    .replaceAll("⁞13", "w")
                    .replaceAll("⁞14", "x")
                    .replaceAll("⁞15", "y")
                    .replaceAll("⁞16", "z")
                    .replaceAll("⁞17", "a")
                    .replaceAll("⁞18", "b")
                    .replaceAll("⁞19", "c")
                    .replaceAll("⁞20", "d")
                    .replaceAll("⁞21", "e")
                    .replaceAll("⁞22", "f")
                    .replaceAll("⁞23", "g")
                    .replaceAll("⁞24", "h")
                    .replaceAll("⁞25", "i")
                    .replaceAll("⁞26", "j")
                    .replaceAll("ᴥ01", "K")
                    .replaceAll("ᴥ02", "L")
                    .replaceAll("ᴥ03", "M")
                    .replaceAll("ᴥ04", "N")
                    .replaceAll("ᴥ05", "O")
                    .replaceAll("ᴥ06", "P")
                    .replaceAll("ᴥ07", "Q")
                    .replaceAll("ᴥ08", "R")
                    .replaceAll("ᴥ09", "S")
                    .replaceAll("ᴥ10", "T")
                    .replaceAll("ᴥ11", "U")
                    .replaceAll("ᴥ12", "V")
                    .replaceAll("ᴥ13", "W")
                    .replaceAll("ᴥ14", "X")
                    .replaceAll("ᴥ15", "Y")
                    .replaceAll("ᴥ16", "Z")
                    .replaceAll("ᴥ17", "A")
                    .replaceAll("ᴥ18", "B")
                    .replaceAll("ᴥ19", "C")
                    .replaceAll("ᴥ20", "D")
                    .replaceAll("ᴥ21", "E")
                    .replaceAll("ᴥ22", "F")
                    .replaceAll("ᴥ23", "G")
                    .replaceAll("ᴥ24", "H")
                    .replaceAll("ᴥ25", "I")
                    .replaceAll("ᴥ26", "J")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("11")) {
            outputString = inputString
                    .replaceAll("⁞01", "l")
                    .replaceAll("⁞02", "m")
                    .replaceAll("⁞03", "n")
                    .replaceAll("⁞04", "o")
                    .replaceAll("⁞05", "p")
                    .replaceAll("⁞06", "q")
                    .replaceAll("⁞07", "r")
                    .replaceAll("⁞08", "s")
                    .replaceAll("⁞09", "t")
                    .replaceAll("⁞10", "u")
                    .replaceAll("⁞11", "v")
                    .replaceAll("⁞12", "w")
                    .replaceAll("⁞13", "x")
                    .replaceAll("⁞14", "y")
                    .replaceAll("⁞15", "z")
                    .replaceAll("⁞16", "a")
                    .replaceAll("⁞17", "b")
                    .replaceAll("⁞18", "c")
                    .replaceAll("⁞19", "d")
                    .replaceAll("⁞20", "e")
                    .replaceAll("⁞21", "f")
                    .replaceAll("⁞22", "g")
                    .replaceAll("⁞23", "h")
                    .replaceAll("⁞24", "i")
                    .replaceAll("⁞25", "j")
                    .replaceAll("⁞26", "k")
                    .replaceAll("ᴥ01", "L")
                    .replaceAll("ᴥ02", "M")
                    .replaceAll("ᴥ03", "N")
                    .replaceAll("ᴥ04", "O")
                    .replaceAll("ᴥ05", "P")
                    .replaceAll("ᴥ06", "Q")
                    .replaceAll("ᴥ07", "R")
                    .replaceAll("ᴥ08", "S")
                    .replaceAll("ᴥ09", "T")
                    .replaceAll("ᴥ10", "U")
                    .replaceAll("ᴥ11", "V")
                    .replaceAll("ᴥ12", "W")
                    .replaceAll("ᴥ13", "X")
                    .replaceAll("ᴥ14", "Y")
                    .replaceAll("ᴥ15", "Z")
                    .replaceAll("ᴥ16", "A")
                    .replaceAll("ᴥ17", "B")
                    .replaceAll("ᴥ18", "C")
                    .replaceAll("ᴥ19", "D")
                    .replaceAll("ᴥ20", "E")
                    .replaceAll("ᴥ21", "F")
                    .replaceAll("ᴥ22", "G")
                    .replaceAll("ᴥ23", "H")
                    .replaceAll("ᴥ24", "I")
                    .replaceAll("ᴥ25", "J")
                    .replaceAll("ᴥ26", "K")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("12")) {
            outputString = inputString
                    .replaceAll("⁞01", "m")
                    .replaceAll("⁞02", "n")
                    .replaceAll("⁞03", "o")
                    .replaceAll("⁞04", "p")
                    .replaceAll("⁞05", "q")
                    .replaceAll("⁞06", "r")
                    .replaceAll("⁞07", "s")
                    .replaceAll("⁞08", "t")
                    .replaceAll("⁞09", "u")
                    .replaceAll("⁞10", "v")
                    .replaceAll("⁞11", "w")
                    .replaceAll("⁞12", "x")
                    .replaceAll("⁞13", "y")
                    .replaceAll("⁞14", "z")
                    .replaceAll("⁞15", "a")
                    .replaceAll("⁞16", "b")
                    .replaceAll("⁞17", "c")
                    .replaceAll("⁞18", "d")
                    .replaceAll("⁞19", "e")
                    .replaceAll("⁞20", "f")
                    .replaceAll("⁞21", "g")
                    .replaceAll("⁞22", "h")
                    .replaceAll("⁞23", "i")
                    .replaceAll("⁞24", "j")
                    .replaceAll("⁞25", "k")
                    .replaceAll("⁞26", "l")
                    .replaceAll("ᴥ01", "M")
                    .replaceAll("ᴥ02", "N")
                    .replaceAll("ᴥ03", "O")
                    .replaceAll("ᴥ04", "P")
                    .replaceAll("ᴥ05", "Q")
                    .replaceAll("ᴥ06", "R")
                    .replaceAll("ᴥ07", "S")
                    .replaceAll("ᴥ08", "T")
                    .replaceAll("ᴥ09", "U")
                    .replaceAll("ᴥ10", "V")
                    .replaceAll("ᴥ11", "W")
                    .replaceAll("ᴥ12", "X")
                    .replaceAll("ᴥ13", "Y")
                    .replaceAll("ᴥ14", "Z")
                    .replaceAll("ᴥ15", "A")
                    .replaceAll("ᴥ16", "B")
                    .replaceAll("ᴥ17", "C")
                    .replaceAll("ᴥ18", "D")
                    .replaceAll("ᴥ19", "E")
                    .replaceAll("ᴥ20", "F")
                    .replaceAll("ᴥ21", "G")
                    .replaceAll("ᴥ22", "H")
                    .replaceAll("ᴥ23", "I")
                    .replaceAll("ᴥ24", "J")
                    .replaceAll("ᴥ25", "K")
                    .replaceAll("ᴥ26", "L")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("13")) {
            outputString = inputString
                    .replaceAll("⁞01", "n")
                    .replaceAll("⁞02", "o")
                    .replaceAll("⁞03", "p")
                    .replaceAll("⁞04", "q")
                    .replaceAll("⁞05", "r")
                    .replaceAll("⁞06", "s")
                    .replaceAll("⁞07", "t")
                    .replaceAll("⁞08", "u")
                    .replaceAll("⁞09", "v")
                    .replaceAll("⁞10", "w")
                    .replaceAll("⁞11", "x")
                    .replaceAll("⁞12", "y")
                    .replaceAll("⁞13", "z")
                    .replaceAll("⁞14", "a")
                    .replaceAll("⁞15", "b")
                    .replaceAll("⁞16", "c")
                    .replaceAll("⁞17", "d")
                    .replaceAll("⁞18", "e")
                    .replaceAll("⁞19", "f")
                    .replaceAll("⁞20", "g")
                    .replaceAll("⁞21", "h")
                    .replaceAll("⁞22", "i")
                    .replaceAll("⁞23", "j")
                    .replaceAll("⁞24", "k")
                    .replaceAll("⁞25", "l")
                    .replaceAll("⁞26", "m")
                    .replaceAll("ᴥ01", "N")
                    .replaceAll("ᴥ02", "O")
                    .replaceAll("ᴥ03", "P")
                    .replaceAll("ᴥ04", "Q")
                    .replaceAll("ᴥ05", "R")
                    .replaceAll("ᴥ06", "S")
                    .replaceAll("ᴥ07", "T")
                    .replaceAll("ᴥ08", "U")
                    .replaceAll("ᴥ09", "V")
                    .replaceAll("ᴥ10", "W")
                    .replaceAll("ᴥ11", "X")
                    .replaceAll("ᴥ12", "Y")
                    .replaceAll("ᴥ13", "Z")
                    .replaceAll("ᴥ14", "A")
                    .replaceAll("ᴥ15", "B")
                    .replaceAll("ᴥ16", "C")
                    .replaceAll("ᴥ17", "D")
                    .replaceAll("ᴥ18", "E")
                    .replaceAll("ᴥ19", "F")
                    .replaceAll("ᴥ20", "G")
                    .replaceAll("ᴥ21", "H")
                    .replaceAll("ᴥ22", "I")
                    .replaceAll("ᴥ23", "J")
                    .replaceAll("ᴥ24", "K")
                    .replaceAll("ᴥ25", "L")
                    .replaceAll("ᴥ26", "M")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("14")) {
            outputString = inputString
                    .replaceAll("⁞01", "o")
                    .replaceAll("⁞02", "p")
                    .replaceAll("⁞03", "q")
                    .replaceAll("⁞04", "r")
                    .replaceAll("⁞05", "s")
                    .replaceAll("⁞06", "t")
                    .replaceAll("⁞07", "u")
                    .replaceAll("⁞08", "v")
                    .replaceAll("⁞09", "w")
                    .replaceAll("⁞10", "x")
                    .replaceAll("⁞11", "y")
                    .replaceAll("⁞12", "z")
                    .replaceAll("⁞13", "a")
                    .replaceAll("⁞14", "b")
                    .replaceAll("⁞15", "c")
                    .replaceAll("⁞16", "d")
                    .replaceAll("⁞17", "e")
                    .replaceAll("⁞18", "f")
                    .replaceAll("⁞19", "g")
                    .replaceAll("⁞20", "h")
                    .replaceAll("⁞21", "i")
                    .replaceAll("⁞22", "j")
                    .replaceAll("⁞23", "k")
                    .replaceAll("⁞24", "l")
                    .replaceAll("⁞25", "m")
                    .replaceAll("⁞26", "n")
                    .replaceAll("ᴥ01", "O")
                    .replaceAll("ᴥ02", "P")
                    .replaceAll("ᴥ03", "Q")
                    .replaceAll("ᴥ04", "R")
                    .replaceAll("ᴥ05", "S")
                    .replaceAll("ᴥ06", "T")
                    .replaceAll("ᴥ07", "U")
                    .replaceAll("ᴥ08", "V")
                    .replaceAll("ᴥ09", "W")
                    .replaceAll("ᴥ10", "X")
                    .replaceAll("ᴥ11", "Y")
                    .replaceAll("ᴥ12", "Z")
                    .replaceAll("ᴥ13", "A")
                    .replaceAll("ᴥ14", "B")
                    .replaceAll("ᴥ15", "C")
                    .replaceAll("ᴥ16", "D")
                    .replaceAll("ᴥ17", "E")
                    .replaceAll("ᴥ18", "F")
                    .replaceAll("ᴥ19", "G")
                    .replaceAll("ᴥ20", "H")
                    .replaceAll("ᴥ21", "I")
                    .replaceAll("ᴥ22", "J")
                    .replaceAll("ᴥ23", "K")
                    .replaceAll("ᴥ24", "L")
                    .replaceAll("ᴥ25", "M")
                    .replaceAll("ᴥ26", "N")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("15")) {
            outputString = inputString
                    .replaceAll("⁞01", "p")
                    .replaceAll("⁞02", "q")
                    .replaceAll("⁞03", "r")
                    .replaceAll("⁞04", "s")
                    .replaceAll("⁞05", "t")
                    .replaceAll("⁞06", "u")
                    .replaceAll("⁞07", "v")
                    .replaceAll("⁞08", "w")
                    .replaceAll("⁞09", "x")
                    .replaceAll("⁞10", "y")
                    .replaceAll("⁞11", "z")
                    .replaceAll("⁞12", "a")
                    .replaceAll("⁞13", "b")
                    .replaceAll("⁞14", "c")
                    .replaceAll("⁞15", "d")
                    .replaceAll("⁞16", "e")
                    .replaceAll("⁞17", "f")
                    .replaceAll("⁞18", "g")
                    .replaceAll("⁞19", "h")
                    .replaceAll("⁞20", "i")
                    .replaceAll("⁞21", "j")
                    .replaceAll("⁞22", "k")
                    .replaceAll("⁞23", "l")
                    .replaceAll("⁞24", "m")
                    .replaceAll("⁞25", "n")
                    .replaceAll("⁞26", "o")
                    .replaceAll("ᴥ01", "P")
                    .replaceAll("ᴥ02", "Q")
                    .replaceAll("ᴥ03", "R")
                    .replaceAll("ᴥ04", "S")
                    .replaceAll("ᴥ05", "T")
                    .replaceAll("ᴥ06", "U")
                    .replaceAll("ᴥ07", "V")
                    .replaceAll("ᴥ08", "W")
                    .replaceAll("ᴥ09", "X")
                    .replaceAll("ᴥ10", "Y")
                    .replaceAll("ᴥ11", "Z")
                    .replaceAll("ᴥ12", "A")
                    .replaceAll("ᴥ13", "B")
                    .replaceAll("ᴥ14", "C")
                    .replaceAll("ᴥ15", "D")
                    .replaceAll("ᴥ16", "E")
                    .replaceAll("ᴥ17", "F")
                    .replaceAll("ᴥ18", "G")
                    .replaceAll("ᴥ19", "H")
                    .replaceAll("ᴥ20", "I")
                    .replaceAll("ᴥ21", "J")
                    .replaceAll("ᴥ22", "K")
                    .replaceAll("ᴥ23", "L")
                    .replaceAll("ᴥ24", "M")
                    .replaceAll("ᴥ25", "N")
                    .replaceAll("ᴥ26", "O")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("16")) {
            outputString = inputString
                    .replaceAll("⁞01", "q")
                    .replaceAll("⁞02", "r")
                    .replaceAll("⁞03", "s")
                    .replaceAll("⁞04", "t")
                    .replaceAll("⁞05", "u")
                    .replaceAll("⁞06", "v")
                    .replaceAll("⁞07", "w")
                    .replaceAll("⁞08", "x")
                    .replaceAll("⁞09", "y")
                    .replaceAll("⁞10", "z")
                    .replaceAll("⁞11", "a")
                    .replaceAll("⁞12", "b")
                    .replaceAll("⁞13", "c")
                    .replaceAll("⁞14", "d")
                    .replaceAll("⁞15", "e")
                    .replaceAll("⁞16", "f")
                    .replaceAll("⁞17", "g")
                    .replaceAll("⁞18", "h")
                    .replaceAll("⁞19", "i")
                    .replaceAll("⁞20", "j")
                    .replaceAll("⁞21", "k")
                    .replaceAll("⁞22", "l")
                    .replaceAll("⁞23", "m")
                    .replaceAll("⁞24", "n")
                    .replaceAll("⁞25", "o")
                    .replaceAll("⁞26", "p")
                    .replaceAll("ᴥ01", "Q")
                    .replaceAll("ᴥ02", "R")
                    .replaceAll("ᴥ03", "S")
                    .replaceAll("ᴥ04", "T")
                    .replaceAll("ᴥ05", "U")
                    .replaceAll("ᴥ06", "V")
                    .replaceAll("ᴥ07", "W")
                    .replaceAll("ᴥ08", "X")
                    .replaceAll("ᴥ09", "Y")
                    .replaceAll("ᴥ10", "Z")
                    .replaceAll("ᴥ11", "A")
                    .replaceAll("ᴥ12", "B")
                    .replaceAll("ᴥ13", "C")
                    .replaceAll("ᴥ14", "D")
                    .replaceAll("ᴥ15", "E")
                    .replaceAll("ᴥ16", "F")
                    .replaceAll("ᴥ17", "G")
                    .replaceAll("ᴥ18", "H")
                    .replaceAll("ᴥ19", "I")
                    .replaceAll("ᴥ20", "J")
                    .replaceAll("ᴥ21", "K")
                    .replaceAll("ᴥ22", "L")
                    .replaceAll("ᴥ23", "M")
                    .replaceAll("ᴥ24", "N")
                    .replaceAll("ᴥ25", "O")
                    .replaceAll("ᴥ26", "P")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("17")) {
            outputString = inputString
                    .replaceAll("⁞01", "r")
                    .replaceAll("⁞02", "s")
                    .replaceAll("⁞03", "t")
                    .replaceAll("⁞04", "u")
                    .replaceAll("⁞05", "v")
                    .replaceAll("⁞06", "w")
                    .replaceAll("⁞07", "x")
                    .replaceAll("⁞08", "y")
                    .replaceAll("⁞09", "z")
                    .replaceAll("⁞10", "a")
                    .replaceAll("⁞11", "b")
                    .replaceAll("⁞12", "c")
                    .replaceAll("⁞13", "d")
                    .replaceAll("⁞14", "e")
                    .replaceAll("⁞15", "f")
                    .replaceAll("⁞16", "g")
                    .replaceAll("⁞17", "h")
                    .replaceAll("⁞18", "i")
                    .replaceAll("⁞19", "j")
                    .replaceAll("⁞20", "k")
                    .replaceAll("⁞21", "l")
                    .replaceAll("⁞22", "m")
                    .replaceAll("⁞23", "n")
                    .replaceAll("⁞24", "o")
                    .replaceAll("⁞25", "p")
                    .replaceAll("⁞26", "q")
                    .replaceAll("ᴥ01", "R")
                    .replaceAll("ᴥ02", "S")
                    .replaceAll("ᴥ03", "T")
                    .replaceAll("ᴥ04", "U")
                    .replaceAll("ᴥ05", "V")
                    .replaceAll("ᴥ06", "W")
                    .replaceAll("ᴥ07", "X")
                    .replaceAll("ᴥ08", "Y")
                    .replaceAll("ᴥ09", "Z")
                    .replaceAll("ᴥ10", "A")
                    .replaceAll("ᴥ11", "B")
                    .replaceAll("ᴥ12", "C")
                    .replaceAll("ᴥ13", "D")
                    .replaceAll("ᴥ14", "E")
                    .replaceAll("ᴥ15", "F")
                    .replaceAll("ᴥ16", "G")
                    .replaceAll("ᴥ17", "H")
                    .replaceAll("ᴥ18", "I")
                    .replaceAll("ᴥ19", "J")
                    .replaceAll("ᴥ20", "K")
                    .replaceAll("ᴥ21", "L")
                    .replaceAll("ᴥ22", "M")
                    .replaceAll("ᴥ23", "N")
                    .replaceAll("ᴥ24", "O")
                    .replaceAll("ᴥ25", "P")
                    .replaceAll("ᴥ26", "Q")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("18")) {
            outputString = inputString
                    .replaceAll("⁞01", "s")
                    .replaceAll("⁞02", "t")
                    .replaceAll("⁞03", "u")
                    .replaceAll("⁞04", "v")
                    .replaceAll("⁞05", "w")
                    .replaceAll("⁞06", "x")
                    .replaceAll("⁞07", "y")
                    .replaceAll("⁞08", "z")
                    .replaceAll("⁞09", "a")
                    .replaceAll("⁞10", "b")
                    .replaceAll("⁞11", "c")
                    .replaceAll("⁞12", "d")
                    .replaceAll("⁞13", "e")
                    .replaceAll("⁞14", "f")
                    .replaceAll("⁞15", "g")
                    .replaceAll("⁞16", "h")
                    .replaceAll("⁞17", "i")
                    .replaceAll("⁞18", "j")
                    .replaceAll("⁞19", "k")
                    .replaceAll("⁞20", "l")
                    .replaceAll("⁞21", "m")
                    .replaceAll("⁞22", "n")
                    .replaceAll("⁞23", "o")
                    .replaceAll("⁞24", "p")
                    .replaceAll("⁞25", "q")
                    .replaceAll("⁞26", "r")
                    .replaceAll("ᴥ01", "S")
                    .replaceAll("ᴥ02", "T")
                    .replaceAll("ᴥ03", "U")
                    .replaceAll("ᴥ04", "V")
                    .replaceAll("ᴥ05", "W")
                    .replaceAll("ᴥ06", "X")
                    .replaceAll("ᴥ07", "Y")
                    .replaceAll("ᴥ08", "Z")
                    .replaceAll("ᴥ09", "A")
                    .replaceAll("ᴥ10", "B")
                    .replaceAll("ᴥ11", "C")
                    .replaceAll("ᴥ12", "D")
                    .replaceAll("ᴥ13", "E")
                    .replaceAll("ᴥ14", "F")
                    .replaceAll("ᴥ15", "G")
                    .replaceAll("ᴥ16", "H")
                    .replaceAll("ᴥ17", "I")
                    .replaceAll("ᴥ18", "J")
                    .replaceAll("ᴥ19", "K")
                    .replaceAll("ᴥ20", "L")
                    .replaceAll("ᴥ21", "M")
                    .replaceAll("ᴥ22", "N")
                    .replaceAll("ᴥ23", "O")
                    .replaceAll("ᴥ24", "P")
                    .replaceAll("ᴥ25", "Q")
                    .replaceAll("ᴥ26", "R")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("19")) {
            outputString = inputString
                    .replaceAll("⁞01", "t")
                    .replaceAll("⁞02", "u")
                    .replaceAll("⁞03", "v")
                    .replaceAll("⁞04", "w")
                    .replaceAll("⁞05", "x")
                    .replaceAll("⁞06", "y")
                    .replaceAll("⁞07", "z")
                    .replaceAll("⁞08", "a")
                    .replaceAll("⁞09", "b")
                    .replaceAll("⁞10", "c")
                    .replaceAll("⁞11", "d")
                    .replaceAll("⁞12", "e")
                    .replaceAll("⁞13", "f")
                    .replaceAll("⁞14", "g")
                    .replaceAll("⁞15", "h")
                    .replaceAll("⁞16", "i")
                    .replaceAll("⁞17", "j")
                    .replaceAll("⁞18", "k")
                    .replaceAll("⁞19", "l")
                    .replaceAll("⁞20", "m")
                    .replaceAll("⁞21", "n")
                    .replaceAll("⁞22", "o")
                    .replaceAll("⁞23", "p")
                    .replaceAll("⁞24", "q")
                    .replaceAll("⁞25", "r")
                    .replaceAll("⁞26", "s")
                    .replaceAll("ᴥ01", "T")
                    .replaceAll("ᴥ02", "U")
                    .replaceAll("ᴥ03", "V")
                    .replaceAll("ᴥ04", "W")
                    .replaceAll("ᴥ05", "X")
                    .replaceAll("ᴥ06", "Y")
                    .replaceAll("ᴥ07", "Z")
                    .replaceAll("ᴥ08", "A")
                    .replaceAll("ᴥ09", "B")
                    .replaceAll("ᴥ10", "C")
                    .replaceAll("ᴥ11", "D")
                    .replaceAll("ᴥ12", "E")
                    .replaceAll("ᴥ13", "F")
                    .replaceAll("ᴥ14", "G")
                    .replaceAll("ᴥ15", "H")
                    .replaceAll("ᴥ16", "I")
                    .replaceAll("ᴥ17", "J")
                    .replaceAll("ᴥ18", "K")
                    .replaceAll("ᴥ19", "L")
                    .replaceAll("ᴥ20", "M")
                    .replaceAll("ᴥ21", "N")
                    .replaceAll("ᴥ22", "O")
                    .replaceAll("ᴥ23", "P")
                    .replaceAll("ᴥ24", "Q")
                    .replaceAll("ᴥ25", "R")
                    .replaceAll("ᴥ26", "S")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("20")) {
            outputString = inputString
                    .replaceAll("⁞01", "u")
                    .replaceAll("⁞02", "v")
                    .replaceAll("⁞03", "w")
                    .replaceAll("⁞04", "x")
                    .replaceAll("⁞05", "y")
                    .replaceAll("⁞06", "z")
                    .replaceAll("⁞07", "a")
                    .replaceAll("⁞08", "b")
                    .replaceAll("⁞09", "c")
                    .replaceAll("⁞10", "d")
                    .replaceAll("⁞11", "e")
                    .replaceAll("⁞12", "f")
                    .replaceAll("⁞13", "g")
                    .replaceAll("⁞14", "h")
                    .replaceAll("⁞15", "i")
                    .replaceAll("⁞16", "j")
                    .replaceAll("⁞17", "k")
                    .replaceAll("⁞18", "l")
                    .replaceAll("⁞19", "m")
                    .replaceAll("⁞20", "n")
                    .replaceAll("⁞21", "o")
                    .replaceAll("⁞22", "p")
                    .replaceAll("⁞23", "q")
                    .replaceAll("⁞24", "r")
                    .replaceAll("⁞25", "s")
                    .replaceAll("⁞26", "t")
                    .replaceAll("ᴥ01", "U")
                    .replaceAll("ᴥ02", "V")
                    .replaceAll("ᴥ03", "W")
                    .replaceAll("ᴥ04", "X")
                    .replaceAll("ᴥ05", "Y")
                    .replaceAll("ᴥ06", "Z")
                    .replaceAll("ᴥ07", "A")
                    .replaceAll("ᴥ08", "B")
                    .replaceAll("ᴥ09", "C")
                    .replaceAll("ᴥ10", "D")
                    .replaceAll("ᴥ11", "E")
                    .replaceAll("ᴥ12", "F")
                    .replaceAll("ᴥ13", "G")
                    .replaceAll("ᴥ14", "H")
                    .replaceAll("ᴥ15", "I")
                    .replaceAll("ᴥ16", "J")
                    .replaceAll("ᴥ17", "K")
                    .replaceAll("ᴥ18", "L")
                    .replaceAll("ᴥ19", "M")
                    .replaceAll("ᴥ20", "N")
                    .replaceAll("ᴥ21", "O")
                    .replaceAll("ᴥ22", "P")
                    .replaceAll("ᴥ23", "Q")
                    .replaceAll("ᴥ24", "R")
                    .replaceAll("ᴥ25", "S")
                    .replaceAll("ᴥ26", "T")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("21")) {
            outputString = inputString
                    .replaceAll("⁞01", "v")
                    .replaceAll("⁞02", "w")
                    .replaceAll("⁞03", "x")
                    .replaceAll("⁞04", "y")
                    .replaceAll("⁞05", "z")
                    .replaceAll("⁞06", "a")
                    .replaceAll("⁞07", "b")
                    .replaceAll("⁞08", "c")
                    .replaceAll("⁞09", "d")
                    .replaceAll("⁞10", "e")
                    .replaceAll("⁞11", "f")
                    .replaceAll("⁞12", "g")
                    .replaceAll("⁞13", "h")
                    .replaceAll("⁞14", "i")
                    .replaceAll("⁞15", "j")
                    .replaceAll("⁞16", "k")
                    .replaceAll("⁞17", "l")
                    .replaceAll("⁞18", "m")
                    .replaceAll("⁞19", "n")
                    .replaceAll("⁞20", "o")
                    .replaceAll("⁞21", "p")
                    .replaceAll("⁞22", "q")
                    .replaceAll("⁞23", "r")
                    .replaceAll("⁞24", "s")
                    .replaceAll("⁞25", "t")
                    .replaceAll("⁞26", "u")
                    .replaceAll("ᴥ01", "V")
                    .replaceAll("ᴥ02", "W")
                    .replaceAll("ᴥ03", "X")
                    .replaceAll("ᴥ04", "Y")
                    .replaceAll("ᴥ05", "Z")
                    .replaceAll("ᴥ06", "A")
                    .replaceAll("ᴥ07", "B")
                    .replaceAll("ᴥ08", "C")
                    .replaceAll("ᴥ09", "D")
                    .replaceAll("ᴥ10", "E")
                    .replaceAll("ᴥ11", "F")
                    .replaceAll("ᴥ12", "G")
                    .replaceAll("ᴥ13", "H")
                    .replaceAll("ᴥ14", "I")
                    .replaceAll("ᴥ15", "J")
                    .replaceAll("ᴥ16", "K")
                    .replaceAll("ᴥ17", "L")
                    .replaceAll("ᴥ18", "M")
                    .replaceAll("ᴥ19", "N")
                    .replaceAll("ᴥ20", "O")
                    .replaceAll("ᴥ21", "P")
                    .replaceAll("ᴥ22", "Q")
                    .replaceAll("ᴥ23", "R")
                    .replaceAll("ᴥ24", "S")
                    .replaceAll("ᴥ25", "T")
                    .replaceAll("ᴥ26", "U")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("22")) {
            outputString = inputString
                    .replaceAll("⁞01", "w")
                    .replaceAll("⁞02", "x")
                    .replaceAll("⁞03", "y")
                    .replaceAll("⁞04", "z")
                    .replaceAll("⁞05", "a")
                    .replaceAll("⁞06", "b")
                    .replaceAll("⁞07", "c")
                    .replaceAll("⁞08", "d")
                    .replaceAll("⁞09", "e")
                    .replaceAll("⁞10", "f")
                    .replaceAll("⁞11", "g")
                    .replaceAll("⁞12", "h")
                    .replaceAll("⁞13", "i")
                    .replaceAll("⁞14", "j")
                    .replaceAll("⁞15", "k")
                    .replaceAll("⁞16", "l")
                    .replaceAll("⁞17", "m")
                    .replaceAll("⁞18", "n")
                    .replaceAll("⁞19", "o")
                    .replaceAll("⁞20", "p")
                    .replaceAll("⁞21", "q")
                    .replaceAll("⁞22", "r")
                    .replaceAll("⁞23", "s")
                    .replaceAll("⁞24", "t")
                    .replaceAll("⁞25", "u")
                    .replaceAll("⁞26", "v")
                    .replaceAll("ᴥ01", "W")
                    .replaceAll("ᴥ02", "X")
                    .replaceAll("ᴥ03", "Y")
                    .replaceAll("ᴥ04", "Z")
                    .replaceAll("ᴥ05", "A")
                    .replaceAll("ᴥ06", "B")
                    .replaceAll("ᴥ07", "C")
                    .replaceAll("ᴥ08", "D")
                    .replaceAll("ᴥ09", "E")
                    .replaceAll("ᴥ10", "F")
                    .replaceAll("ᴥ11", "G")
                    .replaceAll("ᴥ12", "H")
                    .replaceAll("ᴥ13", "I")
                    .replaceAll("ᴥ14", "J")
                    .replaceAll("ᴥ15", "K")
                    .replaceAll("ᴥ16", "L")
                    .replaceAll("ᴥ17", "M")
                    .replaceAll("ᴥ18", "N")
                    .replaceAll("ᴥ19", "O")
                    .replaceAll("ᴥ20", "P")
                    .replaceAll("ᴥ21", "Q")
                    .replaceAll("ᴥ22", "R")
                    .replaceAll("ᴥ23", "S")
                    .replaceAll("ᴥ24", "T")
                    .replaceAll("ᴥ25", "U")
                    .replaceAll("ᴥ26", "V")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("23")) {
            outputString = inputString
                    .replaceAll("⁞01", "x")
                    .replaceAll("⁞02", "y")
                    .replaceAll("⁞03", "z")
                    .replaceAll("⁞04", "a")
                    .replaceAll("⁞05", "b")
                    .replaceAll("⁞06", "c")
                    .replaceAll("⁞07", "d")
                    .replaceAll("⁞08", "e")
                    .replaceAll("⁞09", "f")
                    .replaceAll("⁞10", "g")
                    .replaceAll("⁞11", "h")
                    .replaceAll("⁞12", "i")
                    .replaceAll("⁞13", "j")
                    .replaceAll("⁞14", "k")
                    .replaceAll("⁞15", "l")
                    .replaceAll("⁞16", "m")
                    .replaceAll("⁞17", "n")
                    .replaceAll("⁞18", "o")
                    .replaceAll("⁞19", "p")
                    .replaceAll("⁞20", "q")
                    .replaceAll("⁞21", "r")
                    .replaceAll("⁞22", "s")
                    .replaceAll("⁞23", "t")
                    .replaceAll("⁞24", "u")
                    .replaceAll("⁞25", "v")
                    .replaceAll("⁞26", "w")
                    .replaceAll("ᴥ01", "X")
                    .replaceAll("ᴥ02", "Y")
                    .replaceAll("ᴥ03", "Z")
                    .replaceAll("ᴥ04", "A")
                    .replaceAll("ᴥ05", "B")
                    .replaceAll("ᴥ06", "C")
                    .replaceAll("ᴥ07", "D")
                    .replaceAll("ᴥ08", "E")
                    .replaceAll("ᴥ09", "F")
                    .replaceAll("ᴥ10", "G")
                    .replaceAll("ᴥ11", "H")
                    .replaceAll("ᴥ12", "I")
                    .replaceAll("ᴥ13", "J")
                    .replaceAll("ᴥ14", "K")
                    .replaceAll("ᴥ15", "L")
                    .replaceAll("ᴥ16", "M")
                    .replaceAll("ᴥ17", "N")
                    .replaceAll("ᴥ18", "O")
                    .replaceAll("ᴥ19", "P")
                    .replaceAll("ᴥ20", "Q")
                    .replaceAll("ᴥ21", "R")
                    .replaceAll("ᴥ22", "S")
                    .replaceAll("ᴥ23", "T")
                    .replaceAll("ᴥ24", "U")
                    .replaceAll("ᴥ25", "V")
                    .replaceAll("ᴥ26", "W")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("24")) {
            outputString = inputString
                    .replaceAll("⁞01", "y")
                    .replaceAll("⁞02", "z")
                    .replaceAll("⁞03", "a")
                    .replaceAll("⁞04", "b")
                    .replaceAll("⁞05", "c")
                    .replaceAll("⁞06", "d")
                    .replaceAll("⁞07", "e")
                    .replaceAll("⁞08", "f")
                    .replaceAll("⁞09", "g")
                    .replaceAll("⁞10", "h")
                    .replaceAll("⁞11", "i")
                    .replaceAll("⁞12", "j")
                    .replaceAll("⁞13", "k")
                    .replaceAll("⁞14", "l")
                    .replaceAll("⁞15", "m")
                    .replaceAll("⁞16", "n")
                    .replaceAll("⁞17", "o")
                    .replaceAll("⁞18", "p")
                    .replaceAll("⁞19", "q")
                    .replaceAll("⁞20", "r")
                    .replaceAll("⁞21", "s")
                    .replaceAll("⁞22", "t")
                    .replaceAll("⁞23", "u")
                    .replaceAll("⁞24", "v")
                    .replaceAll("⁞25", "w")
                    .replaceAll("⁞26", "x")
                    .replaceAll("ᴥ01", "Y")
                    .replaceAll("ᴥ02", "Z")
                    .replaceAll("ᴥ03", "A")
                    .replaceAll("ᴥ04", "B")
                    .replaceAll("ᴥ05", "C")
                    .replaceAll("ᴥ06", "D")
                    .replaceAll("ᴥ07", "E")
                    .replaceAll("ᴥ08", "F")
                    .replaceAll("ᴥ09", "G")
                    .replaceAll("ᴥ10", "H")
                    .replaceAll("ᴥ11", "I")
                    .replaceAll("ᴥ12", "J")
                    .replaceAll("ᴥ13", "K")
                    .replaceAll("ᴥ14", "L")
                    .replaceAll("ᴥ15", "M")
                    .replaceAll("ᴥ16", "N")
                    .replaceAll("ᴥ17", "O")
                    .replaceAll("ᴥ18", "P")
                    .replaceAll("ᴥ19", "Q")
                    .replaceAll("ᴥ20", "R")
                    .replaceAll("ᴥ21", "S")
                    .replaceAll("ᴥ22", "T")
                    .replaceAll("ᴥ23", "U")
                    .replaceAll("ᴥ24", "V")
                    .replaceAll("ᴥ25", "W")
                    .replaceAll("ᴥ26", "X")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("25")) {
            outputString = inputString
                    .replaceAll("⁞01", "z")
                    .replaceAll("⁞02", "a")
                    .replaceAll("⁞03", "b")
                    .replaceAll("⁞04", "c")
                    .replaceAll("⁞05", "d")
                    .replaceAll("⁞06", "e")
                    .replaceAll("⁞07", "f")
                    .replaceAll("⁞08", "g")
                    .replaceAll("⁞09", "h")
                    .replaceAll("⁞10", "i")
                    .replaceAll("⁞11", "j")
                    .replaceAll("⁞12", "k")
                    .replaceAll("⁞13", "l")
                    .replaceAll("⁞14", "m")
                    .replaceAll("⁞15", "n")
                    .replaceAll("⁞16", "o")
                    .replaceAll("⁞17", "p")
                    .replaceAll("⁞18", "q")
                    .replaceAll("⁞19", "r")
                    .replaceAll("⁞20", "s")
                    .replaceAll("⁞21", "t")
                    .replaceAll("⁞22", "u")
                    .replaceAll("⁞23", "v")
                    .replaceAll("⁞24", "w")
                    .replaceAll("⁞25", "x")
                    .replaceAll("⁞26", "y")
                    .replaceAll("ᴥ01", "Z")
                    .replaceAll("ᴥ02", "A")
                    .replaceAll("ᴥ03", "B")
                    .replaceAll("ᴥ04", "C")
                    .replaceAll("ᴥ05", "D")
                    .replaceAll("ᴥ06", "E")
                    .replaceAll("ᴥ07", "F")
                    .replaceAll("ᴥ08", "G")
                    .replaceAll("ᴥ09", "H")
                    .replaceAll("ᴥ10", "I")
                    .replaceAll("ᴥ11", "J")
                    .replaceAll("ᴥ12", "K")
                    .replaceAll("ᴥ13", "L")
                    .replaceAll("ᴥ14", "M")
                    .replaceAll("ᴥ15", "N")
                    .replaceAll("ᴥ16", "O")
                    .replaceAll("ᴥ17", "P")
                    .replaceAll("ᴥ18", "Q")
                    .replaceAll("ᴥ19", "R")
                    .replaceAll("ᴥ20", "S")
                    .replaceAll("ᴥ21", "T")
                    .replaceAll("ᴥ22", "U")
                    .replaceAll("ᴥ23", "V")
                    .replaceAll("ᴥ24", "W")
                    .replaceAll("ᴥ25", "X")
                    .replaceAll("ᴥ26", "Y")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void caesarcipherDecrypt() {
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

        if(dialog_choose_selected.equals("25")) {
            outputString = inputString
                    .replaceAll("⁞01", "b")
                    .replaceAll("⁞02", "c")
                    .replaceAll("⁞03", "d")
                    .replaceAll("⁞04", "e")
                    .replaceAll("⁞05", "f")
                    .replaceAll("⁞06", "g")
                    .replaceAll("⁞07", "h")
                    .replaceAll("⁞08", "i")
                    .replaceAll("⁞09", "j")
                    .replaceAll("⁞10", "k")
                    .replaceAll("⁞11", "l")
                    .replaceAll("⁞12", "m")
                    .replaceAll("⁞13", "n")
                    .replaceAll("⁞14", "o")
                    .replaceAll("⁞15", "p")
                    .replaceAll("⁞16", "q")
                    .replaceAll("⁞17", "r")
                    .replaceAll("⁞18", "s")
                    .replaceAll("⁞19", "t")
                    .replaceAll("⁞20", "u")
                    .replaceAll("⁞21", "v")
                    .replaceAll("⁞22", "w")
                    .replaceAll("⁞23", "x")
                    .replaceAll("⁞24", "y")
                    .replaceAll("⁞25", "z")
                    .replaceAll("⁞26", "a")
                    .replaceAll("ᴥ01", "B")
                    .replaceAll("ᴥ02", "C")
                    .replaceAll("ᴥ03", "D")
                    .replaceAll("ᴥ04", "E")
                    .replaceAll("ᴥ05", "F")
                    .replaceAll("ᴥ06", "G")
                    .replaceAll("ᴥ07", "H")
                    .replaceAll("ᴥ08", "I")
                    .replaceAll("ᴥ09", "J")
                    .replaceAll("ᴥ10", "K")
                    .replaceAll("ᴥ11", "L")
                    .replaceAll("ᴥ12", "M")
                    .replaceAll("ᴥ13", "N")
                    .replaceAll("ᴥ14", "O")
                    .replaceAll("ᴥ15", "P")
                    .replaceAll("ᴥ16", "Q")
                    .replaceAll("ᴥ17", "R")
                    .replaceAll("ᴥ18", "S")
                    .replaceAll("ᴥ19", "T")
                    .replaceAll("ᴥ20", "U")
                    .replaceAll("ᴥ21", "V")
                    .replaceAll("ᴥ22", "W")
                    .replaceAll("ᴥ23", "X")
                    .replaceAll("ᴥ24", "Y")
                    .replaceAll("ᴥ25", "Z")
                    .replaceAll("ᴥ26", "A")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("24")) {
            outputString = inputString
                    .replaceAll("⁞01", "c")
                    .replaceAll("⁞02", "d")
                    .replaceAll("⁞03", "e")
                    .replaceAll("⁞04", "f")
                    .replaceAll("⁞05", "g")
                    .replaceAll("⁞06", "h")
                    .replaceAll("⁞07", "i")
                    .replaceAll("⁞08", "j")
                    .replaceAll("⁞09", "k")
                    .replaceAll("⁞10", "l")
                    .replaceAll("⁞11", "m")
                    .replaceAll("⁞12", "n")
                    .replaceAll("⁞13", "o")
                    .replaceAll("⁞14", "p")
                    .replaceAll("⁞15", "q")
                    .replaceAll("⁞16", "r")
                    .replaceAll("⁞17", "s")
                    .replaceAll("⁞18", "t")
                    .replaceAll("⁞19", "u")
                    .replaceAll("⁞20", "v")
                    .replaceAll("⁞21", "w")
                    .replaceAll("⁞22", "x")
                    .replaceAll("⁞23", "y")
                    .replaceAll("⁞24", "z")
                    .replaceAll("⁞25", "a")
                    .replaceAll("⁞26", "b")
                    .replaceAll("ᴥ01", "C")
                    .replaceAll("ᴥ02", "D")
                    .replaceAll("ᴥ03", "E")
                    .replaceAll("ᴥ04", "F")
                    .replaceAll("ᴥ05", "G")
                    .replaceAll("ᴥ06", "H")
                    .replaceAll("ᴥ07", "I")
                    .replaceAll("ᴥ08", "J")
                    .replaceAll("ᴥ09", "K")
                    .replaceAll("ᴥ10", "L")
                    .replaceAll("ᴥ11", "M")
                    .replaceAll("ᴥ12", "N")
                    .replaceAll("ᴥ13", "O")
                    .replaceAll("ᴥ14", "P")
                    .replaceAll("ᴥ15", "Q")
                    .replaceAll("ᴥ16", "R")
                    .replaceAll("ᴥ17", "S")
                    .replaceAll("ᴥ18", "T")
                    .replaceAll("ᴥ19", "U")
                    .replaceAll("ᴥ20", "V")
                    .replaceAll("ᴥ21", "W")
                    .replaceAll("ᴥ22", "X")
                    .replaceAll("ᴥ23", "Y")
                    .replaceAll("ᴥ24", "Z")
                    .replaceAll("ᴥ25", "A")
                    .replaceAll("ᴥ26", "B")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("23")) {
            outputString = inputString
                    .replaceAll("⁞01", "d")
                    .replaceAll("⁞02", "e")
                    .replaceAll("⁞03", "f")
                    .replaceAll("⁞04", "g")
                    .replaceAll("⁞05", "h")
                    .replaceAll("⁞06", "i")
                    .replaceAll("⁞07", "j")
                    .replaceAll("⁞08", "k")
                    .replaceAll("⁞09", "l")
                    .replaceAll("⁞10", "m")
                    .replaceAll("⁞11", "n")
                    .replaceAll("⁞12", "o")
                    .replaceAll("⁞13", "p")
                    .replaceAll("⁞14", "q")
                    .replaceAll("⁞15", "r")
                    .replaceAll("⁞16", "s")
                    .replaceAll("⁞17", "t")
                    .replaceAll("⁞18", "u")
                    .replaceAll("⁞19", "v")
                    .replaceAll("⁞20", "w")
                    .replaceAll("⁞21", "x")
                    .replaceAll("⁞22", "y")
                    .replaceAll("⁞23", "z")
                    .replaceAll("⁞24", "a")
                    .replaceAll("⁞25", "b")
                    .replaceAll("⁞26", "c")
                    .replaceAll("ᴥ01", "D")
                    .replaceAll("ᴥ02", "E")
                    .replaceAll("ᴥ03", "F")
                    .replaceAll("ᴥ04", "G")
                    .replaceAll("ᴥ05", "H")
                    .replaceAll("ᴥ06", "I")
                    .replaceAll("ᴥ07", "J")
                    .replaceAll("ᴥ08", "K")
                    .replaceAll("ᴥ09", "L")
                    .replaceAll("ᴥ10", "M")
                    .replaceAll("ᴥ11", "N")
                    .replaceAll("ᴥ12", "O")
                    .replaceAll("ᴥ13", "P")
                    .replaceAll("ᴥ14", "Q")
                    .replaceAll("ᴥ15", "R")
                    .replaceAll("ᴥ16", "S")
                    .replaceAll("ᴥ17", "T")
                    .replaceAll("ᴥ18", "U")
                    .replaceAll("ᴥ19", "V")
                    .replaceAll("ᴥ20", "W")
                    .replaceAll("ᴥ21", "X")
                    .replaceAll("ᴥ22", "Y")
                    .replaceAll("ᴥ23", "Z")
                    .replaceAll("ᴥ24", "A")
                    .replaceAll("ᴥ25", "B")
                    .replaceAll("ᴥ26", "C")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("22")) {
            outputString = inputString
                    .replaceAll("⁞01", "e")
                    .replaceAll("⁞02", "f")
                    .replaceAll("⁞03", "g")
                    .replaceAll("⁞04", "h")
                    .replaceAll("⁞05", "i")
                    .replaceAll("⁞06", "j")
                    .replaceAll("⁞07", "k")
                    .replaceAll("⁞08", "l")
                    .replaceAll("⁞09", "m")
                    .replaceAll("⁞10", "n")
                    .replaceAll("⁞11", "o")
                    .replaceAll("⁞12", "p")
                    .replaceAll("⁞13", "q")
                    .replaceAll("⁞14", "r")
                    .replaceAll("⁞15", "s")
                    .replaceAll("⁞16", "t")
                    .replaceAll("⁞17", "u")
                    .replaceAll("⁞18", "v")
                    .replaceAll("⁞19", "w")
                    .replaceAll("⁞20", "x")
                    .replaceAll("⁞21", "y")
                    .replaceAll("⁞22", "z")
                    .replaceAll("⁞23", "a")
                    .replaceAll("⁞24", "b")
                    .replaceAll("⁞25", "c")
                    .replaceAll("⁞26", "d")
                    .replaceAll("ᴥ01", "E")
                    .replaceAll("ᴥ02", "F")
                    .replaceAll("ᴥ03", "G")
                    .replaceAll("ᴥ04", "H")
                    .replaceAll("ᴥ05", "I")
                    .replaceAll("ᴥ06", "J")
                    .replaceAll("ᴥ07", "K")
                    .replaceAll("ᴥ08", "L")
                    .replaceAll("ᴥ09", "M")
                    .replaceAll("ᴥ10", "N")
                    .replaceAll("ᴥ11", "O")
                    .replaceAll("ᴥ12", "P")
                    .replaceAll("ᴥ13", "Q")
                    .replaceAll("ᴥ14", "R")
                    .replaceAll("ᴥ15", "S")
                    .replaceAll("ᴥ16", "T")
                    .replaceAll("ᴥ17", "U")
                    .replaceAll("ᴥ18", "V")
                    .replaceAll("ᴥ19", "W")
                    .replaceAll("ᴥ20", "X")
                    .replaceAll("ᴥ21", "Y")
                    .replaceAll("ᴥ22", "Z")
                    .replaceAll("ᴥ23", "A")
                    .replaceAll("ᴥ24", "B")
                    .replaceAll("ᴥ25", "C")
                    .replaceAll("ᴥ26", "D")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("21")) {
            outputString = inputString
                    .replaceAll("⁞01", "f")
                    .replaceAll("⁞02", "g")
                    .replaceAll("⁞03", "h")
                    .replaceAll("⁞04", "i")
                    .replaceAll("⁞05", "j")
                    .replaceAll("⁞06", "k")
                    .replaceAll("⁞07", "l")
                    .replaceAll("⁞08", "m")
                    .replaceAll("⁞09", "n")
                    .replaceAll("⁞10", "o")
                    .replaceAll("⁞11", "p")
                    .replaceAll("⁞12", "q")
                    .replaceAll("⁞13", "r")
                    .replaceAll("⁞14", "s")
                    .replaceAll("⁞15", "t")
                    .replaceAll("⁞16", "u")
                    .replaceAll("⁞17", "v")
                    .replaceAll("⁞18", "w")
                    .replaceAll("⁞19", "x")
                    .replaceAll("⁞20", "y")
                    .replaceAll("⁞21", "z")
                    .replaceAll("⁞22", "a")
                    .replaceAll("⁞23", "b")
                    .replaceAll("⁞24", "c")
                    .replaceAll("⁞25", "d")
                    .replaceAll("⁞26", "e")
                    .replaceAll("ᴥ01", "F")
                    .replaceAll("ᴥ02", "G")
                    .replaceAll("ᴥ03", "H")
                    .replaceAll("ᴥ04", "I")
                    .replaceAll("ᴥ05", "J")
                    .replaceAll("ᴥ06", "K")
                    .replaceAll("ᴥ07", "L")
                    .replaceAll("ᴥ08", "M")
                    .replaceAll("ᴥ09", "N")
                    .replaceAll("ᴥ10", "O")
                    .replaceAll("ᴥ11", "P")
                    .replaceAll("ᴥ12", "Q")
                    .replaceAll("ᴥ13", "R")
                    .replaceAll("ᴥ14", "S")
                    .replaceAll("ᴥ15", "T")
                    .replaceAll("ᴥ16", "U")
                    .replaceAll("ᴥ17", "V")
                    .replaceAll("ᴥ18", "W")
                    .replaceAll("ᴥ19", "X")
                    .replaceAll("ᴥ20", "Y")
                    .replaceAll("ᴥ21", "Z")
                    .replaceAll("ᴥ22", "A")
                    .replaceAll("ᴥ23", "B")
                    .replaceAll("ᴥ24", "C")
                    .replaceAll("ᴥ25", "D")
                    .replaceAll("ᴥ26", "E")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("20")) {
            outputString = inputString
                    .replaceAll("⁞01", "g")
                    .replaceAll("⁞02", "h")
                    .replaceAll("⁞03", "i")
                    .replaceAll("⁞04", "j")
                    .replaceAll("⁞05", "k")
                    .replaceAll("⁞06", "l")
                    .replaceAll("⁞07", "m")
                    .replaceAll("⁞08", "n")
                    .replaceAll("⁞09", "o")
                    .replaceAll("⁞10", "p")
                    .replaceAll("⁞11", "q")
                    .replaceAll("⁞12", "r")
                    .replaceAll("⁞13", "s")
                    .replaceAll("⁞14", "t")
                    .replaceAll("⁞15", "u")
                    .replaceAll("⁞16", "v")
                    .replaceAll("⁞17", "w")
                    .replaceAll("⁞18", "x")
                    .replaceAll("⁞19", "y")
                    .replaceAll("⁞20", "z")
                    .replaceAll("⁞21", "a")
                    .replaceAll("⁞22", "b")
                    .replaceAll("⁞23", "c")
                    .replaceAll("⁞24", "d")
                    .replaceAll("⁞25", "e")
                    .replaceAll("⁞26", "f")
                    .replaceAll("ᴥ01", "G")
                    .replaceAll("ᴥ02", "H")
                    .replaceAll("ᴥ03", "I")
                    .replaceAll("ᴥ04", "J")
                    .replaceAll("ᴥ05", "K")
                    .replaceAll("ᴥ06", "L")
                    .replaceAll("ᴥ07", "M")
                    .replaceAll("ᴥ08", "N")
                    .replaceAll("ᴥ09", "O")
                    .replaceAll("ᴥ10", "P")
                    .replaceAll("ᴥ11", "Q")
                    .replaceAll("ᴥ12", "R")
                    .replaceAll("ᴥ13", "S")
                    .replaceAll("ᴥ14", "T")
                    .replaceAll("ᴥ15", "U")
                    .replaceAll("ᴥ16", "V")
                    .replaceAll("ᴥ17", "W")
                    .replaceAll("ᴥ18", "X")
                    .replaceAll("ᴥ19", "Y")
                    .replaceAll("ᴥ20", "Z")
                    .replaceAll("ᴥ21", "A")
                    .replaceAll("ᴥ22", "B")
                    .replaceAll("ᴥ23", "C")
                    .replaceAll("ᴥ24", "D")
                    .replaceAll("ᴥ25", "E")
                    .replaceAll("ᴥ26", "F")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("19")) {
            outputString = inputString
                    .replaceAll("⁞01", "h")
                    .replaceAll("⁞02", "i")
                    .replaceAll("⁞03", "j")
                    .replaceAll("⁞04", "k")
                    .replaceAll("⁞05", "l")
                    .replaceAll("⁞06", "m")
                    .replaceAll("⁞07", "n")
                    .replaceAll("⁞08", "o")
                    .replaceAll("⁞09", "p")
                    .replaceAll("⁞10", "q")
                    .replaceAll("⁞11", "r")
                    .replaceAll("⁞12", "s")
                    .replaceAll("⁞13", "t")
                    .replaceAll("⁞14", "u")
                    .replaceAll("⁞15", "v")
                    .replaceAll("⁞16", "w")
                    .replaceAll("⁞17", "x")
                    .replaceAll("⁞18", "y")
                    .replaceAll("⁞19", "z")
                    .replaceAll("⁞20", "a")
                    .replaceAll("⁞21", "b")
                    .replaceAll("⁞22", "c")
                    .replaceAll("⁞23", "d")
                    .replaceAll("⁞24", "e")
                    .replaceAll("⁞25", "f")
                    .replaceAll("⁞26", "g")
                    .replaceAll("ᴥ01", "H")
                    .replaceAll("ᴥ02", "I")
                    .replaceAll("ᴥ03", "J")
                    .replaceAll("ᴥ04", "K")
                    .replaceAll("ᴥ05", "L")
                    .replaceAll("ᴥ06", "M")
                    .replaceAll("ᴥ07", "N")
                    .replaceAll("ᴥ08", "O")
                    .replaceAll("ᴥ09", "P")
                    .replaceAll("ᴥ10", "Q")
                    .replaceAll("ᴥ11", "R")
                    .replaceAll("ᴥ12", "S")
                    .replaceAll("ᴥ13", "T")
                    .replaceAll("ᴥ14", "U")
                    .replaceAll("ᴥ15", "V")
                    .replaceAll("ᴥ16", "W")
                    .replaceAll("ᴥ17", "X")
                    .replaceAll("ᴥ18", "Y")
                    .replaceAll("ᴥ19", "Z")
                    .replaceAll("ᴥ20", "A")
                    .replaceAll("ᴥ21", "B")
                    .replaceAll("ᴥ22", "C")
                    .replaceAll("ᴥ23", "D")
                    .replaceAll("ᴥ24", "E")
                    .replaceAll("ᴥ25", "F")
                    .replaceAll("ᴥ26", "G")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("18")) {
            outputString = inputString
                    .replaceAll("⁞01", "i")
                    .replaceAll("⁞02", "j")
                    .replaceAll("⁞03", "k")
                    .replaceAll("⁞04", "l")
                    .replaceAll("⁞05", "m")
                    .replaceAll("⁞06", "n")
                    .replaceAll("⁞07", "o")
                    .replaceAll("⁞08", "p")
                    .replaceAll("⁞09", "q")
                    .replaceAll("⁞10", "r")
                    .replaceAll("⁞11", "s")
                    .replaceAll("⁞12", "t")
                    .replaceAll("⁞13", "u")
                    .replaceAll("⁞14", "v")
                    .replaceAll("⁞15", "w")
                    .replaceAll("⁞16", "x")
                    .replaceAll("⁞17", "y")
                    .replaceAll("⁞18", "z")
                    .replaceAll("⁞19", "a")
                    .replaceAll("⁞20", "b")
                    .replaceAll("⁞21", "c")
                    .replaceAll("⁞22", "d")
                    .replaceAll("⁞23", "e")
                    .replaceAll("⁞24", "f")
                    .replaceAll("⁞25", "g")
                    .replaceAll("⁞26", "h")
                    .replaceAll("ᴥ01", "I")
                    .replaceAll("ᴥ02", "J")
                    .replaceAll("ᴥ03", "K")
                    .replaceAll("ᴥ04", "L")
                    .replaceAll("ᴥ05", "M")
                    .replaceAll("ᴥ06", "N")
                    .replaceAll("ᴥ07", "O")
                    .replaceAll("ᴥ08", "P")
                    .replaceAll("ᴥ09", "Q")
                    .replaceAll("ᴥ10", "R")
                    .replaceAll("ᴥ11", "S")
                    .replaceAll("ᴥ12", "T")
                    .replaceAll("ᴥ13", "U")
                    .replaceAll("ᴥ14", "V")
                    .replaceAll("ᴥ15", "W")
                    .replaceAll("ᴥ16", "X")
                    .replaceAll("ᴥ17", "Y")
                    .replaceAll("ᴥ18", "Z")
                    .replaceAll("ᴥ19", "A")
                    .replaceAll("ᴥ20", "B")
                    .replaceAll("ᴥ21", "C")
                    .replaceAll("ᴥ22", "D")
                    .replaceAll("ᴥ23", "E")
                    .replaceAll("ᴥ24", "F")
                    .replaceAll("ᴥ25", "G")
                    .replaceAll("ᴥ26", "H")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("17")) {
            outputString = inputString
                    .replaceAll("⁞01", "j")
                    .replaceAll("⁞02", "k")
                    .replaceAll("⁞03", "l")
                    .replaceAll("⁞04", "m")
                    .replaceAll("⁞05", "n")
                    .replaceAll("⁞06", "o")
                    .replaceAll("⁞07", "p")
                    .replaceAll("⁞08", "q")
                    .replaceAll("⁞09", "r")
                    .replaceAll("⁞10", "s")
                    .replaceAll("⁞11", "t")
                    .replaceAll("⁞12", "u")
                    .replaceAll("⁞13", "v")
                    .replaceAll("⁞14", "w")
                    .replaceAll("⁞15", "x")
                    .replaceAll("⁞16", "y")
                    .replaceAll("⁞17", "z")
                    .replaceAll("⁞18", "a")
                    .replaceAll("⁞19", "b")
                    .replaceAll("⁞20", "c")
                    .replaceAll("⁞21", "d")
                    .replaceAll("⁞22", "e")
                    .replaceAll("⁞23", "f")
                    .replaceAll("⁞24", "g")
                    .replaceAll("⁞25", "h")
                    .replaceAll("⁞26", "i")
                    .replaceAll("ᴥ01", "J")
                    .replaceAll("ᴥ02", "K")
                    .replaceAll("ᴥ03", "L")
                    .replaceAll("ᴥ04", "M")
                    .replaceAll("ᴥ05", "N")
                    .replaceAll("ᴥ06", "O")
                    .replaceAll("ᴥ07", "P")
                    .replaceAll("ᴥ08", "Q")
                    .replaceAll("ᴥ09", "R")
                    .replaceAll("ᴥ10", "S")
                    .replaceAll("ᴥ11", "T")
                    .replaceAll("ᴥ12", "U")
                    .replaceAll("ᴥ13", "V")
                    .replaceAll("ᴥ14", "W")
                    .replaceAll("ᴥ15", "X")
                    .replaceAll("ᴥ16", "Y")
                    .replaceAll("ᴥ17", "Z")
                    .replaceAll("ᴥ18", "A")
                    .replaceAll("ᴥ19", "B")
                    .replaceAll("ᴥ20", "C")
                    .replaceAll("ᴥ21", "D")
                    .replaceAll("ᴥ22", "E")
                    .replaceAll("ᴥ23", "F")
                    .replaceAll("ᴥ24", "G")
                    .replaceAll("ᴥ25", "H")
                    .replaceAll("ᴥ26", "I")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("16")) {
            outputString = inputString
                    .replaceAll("⁞01", "k")
                    .replaceAll("⁞02", "l")
                    .replaceAll("⁞03", "m")
                    .replaceAll("⁞04", "n")
                    .replaceAll("⁞05", "o")
                    .replaceAll("⁞06", "p")
                    .replaceAll("⁞07", "q")
                    .replaceAll("⁞08", "r")
                    .replaceAll("⁞09", "s")
                    .replaceAll("⁞10", "t")
                    .replaceAll("⁞11", "u")
                    .replaceAll("⁞12", "v")
                    .replaceAll("⁞13", "w")
                    .replaceAll("⁞14", "x")
                    .replaceAll("⁞15", "y")
                    .replaceAll("⁞16", "z")
                    .replaceAll("⁞17", "a")
                    .replaceAll("⁞18", "b")
                    .replaceAll("⁞19", "c")
                    .replaceAll("⁞20", "d")
                    .replaceAll("⁞21", "e")
                    .replaceAll("⁞22", "f")
                    .replaceAll("⁞23", "g")
                    .replaceAll("⁞24", "h")
                    .replaceAll("⁞25", "i")
                    .replaceAll("⁞26", "j")
                    .replaceAll("ᴥ01", "K")
                    .replaceAll("ᴥ02", "L")
                    .replaceAll("ᴥ03", "M")
                    .replaceAll("ᴥ04", "N")
                    .replaceAll("ᴥ05", "O")
                    .replaceAll("ᴥ06", "P")
                    .replaceAll("ᴥ07", "Q")
                    .replaceAll("ᴥ08", "R")
                    .replaceAll("ᴥ09", "S")
                    .replaceAll("ᴥ10", "T")
                    .replaceAll("ᴥ11", "U")
                    .replaceAll("ᴥ12", "V")
                    .replaceAll("ᴥ13", "W")
                    .replaceAll("ᴥ14", "X")
                    .replaceAll("ᴥ15", "Y")
                    .replaceAll("ᴥ16", "Z")
                    .replaceAll("ᴥ17", "A")
                    .replaceAll("ᴥ18", "B")
                    .replaceAll("ᴥ19", "C")
                    .replaceAll("ᴥ20", "D")
                    .replaceAll("ᴥ21", "E")
                    .replaceAll("ᴥ22", "F")
                    .replaceAll("ᴥ23", "G")
                    .replaceAll("ᴥ24", "H")
                    .replaceAll("ᴥ25", "I")
                    .replaceAll("ᴥ26", "J")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("15")) {
            outputString = inputString
                    .replaceAll("⁞01", "l")
                    .replaceAll("⁞02", "m")
                    .replaceAll("⁞03", "n")
                    .replaceAll("⁞04", "o")
                    .replaceAll("⁞05", "p")
                    .replaceAll("⁞06", "q")
                    .replaceAll("⁞07", "r")
                    .replaceAll("⁞08", "s")
                    .replaceAll("⁞09", "t")
                    .replaceAll("⁞10", "u")
                    .replaceAll("⁞11", "v")
                    .replaceAll("⁞12", "w")
                    .replaceAll("⁞13", "x")
                    .replaceAll("⁞14", "y")
                    .replaceAll("⁞15", "z")
                    .replaceAll("⁞16", "a")
                    .replaceAll("⁞17", "b")
                    .replaceAll("⁞18", "c")
                    .replaceAll("⁞19", "d")
                    .replaceAll("⁞20", "e")
                    .replaceAll("⁞21", "f")
                    .replaceAll("⁞22", "g")
                    .replaceAll("⁞23", "h")
                    .replaceAll("⁞24", "i")
                    .replaceAll("⁞25", "j")
                    .replaceAll("⁞26", "k")
                    .replaceAll("ᴥ01", "L")
                    .replaceAll("ᴥ02", "M")
                    .replaceAll("ᴥ03", "N")
                    .replaceAll("ᴥ04", "O")
                    .replaceAll("ᴥ05", "P")
                    .replaceAll("ᴥ06", "Q")
                    .replaceAll("ᴥ07", "R")
                    .replaceAll("ᴥ08", "S")
                    .replaceAll("ᴥ09", "T")
                    .replaceAll("ᴥ10", "U")
                    .replaceAll("ᴥ11", "V")
                    .replaceAll("ᴥ12", "W")
                    .replaceAll("ᴥ13", "X")
                    .replaceAll("ᴥ14", "Y")
                    .replaceAll("ᴥ15", "Z")
                    .replaceAll("ᴥ16", "A")
                    .replaceAll("ᴥ17", "B")
                    .replaceAll("ᴥ18", "C")
                    .replaceAll("ᴥ19", "D")
                    .replaceAll("ᴥ20", "E")
                    .replaceAll("ᴥ21", "F")
                    .replaceAll("ᴥ22", "G")
                    .replaceAll("ᴥ23", "H")
                    .replaceAll("ᴥ24", "I")
                    .replaceAll("ᴥ25", "J")
                    .replaceAll("ᴥ26", "K")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("14")) {
            outputString = inputString
                    .replaceAll("⁞01", "m")
                    .replaceAll("⁞02", "n")
                    .replaceAll("⁞03", "o")
                    .replaceAll("⁞04", "p")
                    .replaceAll("⁞05", "q")
                    .replaceAll("⁞06", "r")
                    .replaceAll("⁞07", "s")
                    .replaceAll("⁞08", "t")
                    .replaceAll("⁞09", "u")
                    .replaceAll("⁞10", "v")
                    .replaceAll("⁞11", "w")
                    .replaceAll("⁞12", "x")
                    .replaceAll("⁞13", "y")
                    .replaceAll("⁞14", "z")
                    .replaceAll("⁞15", "a")
                    .replaceAll("⁞16", "b")
                    .replaceAll("⁞17", "c")
                    .replaceAll("⁞18", "d")
                    .replaceAll("⁞19", "e")
                    .replaceAll("⁞20", "f")
                    .replaceAll("⁞21", "g")
                    .replaceAll("⁞22", "h")
                    .replaceAll("⁞23", "i")
                    .replaceAll("⁞24", "j")
                    .replaceAll("⁞25", "k")
                    .replaceAll("⁞26", "l")
                    .replaceAll("ᴥ01", "M")
                    .replaceAll("ᴥ02", "N")
                    .replaceAll("ᴥ03", "O")
                    .replaceAll("ᴥ04", "P")
                    .replaceAll("ᴥ05", "Q")
                    .replaceAll("ᴥ06", "R")
                    .replaceAll("ᴥ07", "S")
                    .replaceAll("ᴥ08", "T")
                    .replaceAll("ᴥ09", "U")
                    .replaceAll("ᴥ10", "V")
                    .replaceAll("ᴥ11", "W")
                    .replaceAll("ᴥ12", "X")
                    .replaceAll("ᴥ13", "Y")
                    .replaceAll("ᴥ14", "Z")
                    .replaceAll("ᴥ15", "A")
                    .replaceAll("ᴥ16", "B")
                    .replaceAll("ᴥ17", "C")
                    .replaceAll("ᴥ18", "D")
                    .replaceAll("ᴥ19", "E")
                    .replaceAll("ᴥ20", "F")
                    .replaceAll("ᴥ21", "G")
                    .replaceAll("ᴥ22", "H")
                    .replaceAll("ᴥ23", "I")
                    .replaceAll("ᴥ24", "J")
                    .replaceAll("ᴥ25", "K")
                    .replaceAll("ᴥ26", "L")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("13")) {
            outputString = inputString
                    .replaceAll("⁞01", "n")
                    .replaceAll("⁞02", "o")
                    .replaceAll("⁞03", "p")
                    .replaceAll("⁞04", "q")
                    .replaceAll("⁞05", "r")
                    .replaceAll("⁞06", "s")
                    .replaceAll("⁞07", "t")
                    .replaceAll("⁞08", "u")
                    .replaceAll("⁞09", "v")
                    .replaceAll("⁞10", "w")
                    .replaceAll("⁞11", "x")
                    .replaceAll("⁞12", "y")
                    .replaceAll("⁞13", "z")
                    .replaceAll("⁞14", "a")
                    .replaceAll("⁞15", "b")
                    .replaceAll("⁞16", "c")
                    .replaceAll("⁞17", "d")
                    .replaceAll("⁞18", "e")
                    .replaceAll("⁞19", "f")
                    .replaceAll("⁞20", "g")
                    .replaceAll("⁞21", "h")
                    .replaceAll("⁞22", "i")
                    .replaceAll("⁞23", "j")
                    .replaceAll("⁞24", "k")
                    .replaceAll("⁞25", "l")
                    .replaceAll("⁞26", "m")
                    .replaceAll("ᴥ01", "N")
                    .replaceAll("ᴥ02", "O")
                    .replaceAll("ᴥ03", "P")
                    .replaceAll("ᴥ04", "Q")
                    .replaceAll("ᴥ05", "R")
                    .replaceAll("ᴥ06", "S")
                    .replaceAll("ᴥ07", "T")
                    .replaceAll("ᴥ08", "U")
                    .replaceAll("ᴥ09", "V")
                    .replaceAll("ᴥ10", "W")
                    .replaceAll("ᴥ11", "X")
                    .replaceAll("ᴥ12", "Y")
                    .replaceAll("ᴥ13", "Z")
                    .replaceAll("ᴥ14", "A")
                    .replaceAll("ᴥ15", "B")
                    .replaceAll("ᴥ16", "C")
                    .replaceAll("ᴥ17", "D")
                    .replaceAll("ᴥ18", "E")
                    .replaceAll("ᴥ19", "F")
                    .replaceAll("ᴥ20", "G")
                    .replaceAll("ᴥ21", "H")
                    .replaceAll("ᴥ22", "I")
                    .replaceAll("ᴥ23", "J")
                    .replaceAll("ᴥ24", "K")
                    .replaceAll("ᴥ25", "L")
                    .replaceAll("ᴥ26", "M")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("12")) {
            outputString = inputString
                    .replaceAll("⁞01", "o")
                    .replaceAll("⁞02", "p")
                    .replaceAll("⁞03", "q")
                    .replaceAll("⁞04", "r")
                    .replaceAll("⁞05", "s")
                    .replaceAll("⁞06", "t")
                    .replaceAll("⁞07", "u")
                    .replaceAll("⁞08", "v")
                    .replaceAll("⁞09", "w")
                    .replaceAll("⁞10", "x")
                    .replaceAll("⁞11", "y")
                    .replaceAll("⁞12", "z")
                    .replaceAll("⁞13", "a")
                    .replaceAll("⁞14", "b")
                    .replaceAll("⁞15", "c")
                    .replaceAll("⁞16", "d")
                    .replaceAll("⁞17", "e")
                    .replaceAll("⁞18", "f")
                    .replaceAll("⁞19", "g")
                    .replaceAll("⁞20", "h")
                    .replaceAll("⁞21", "i")
                    .replaceAll("⁞22", "j")
                    .replaceAll("⁞23", "k")
                    .replaceAll("⁞24", "l")
                    .replaceAll("⁞25", "m")
                    .replaceAll("⁞26", "n")
                    .replaceAll("ᴥ01", "O")
                    .replaceAll("ᴥ02", "P")
                    .replaceAll("ᴥ03", "Q")
                    .replaceAll("ᴥ04", "R")
                    .replaceAll("ᴥ05", "S")
                    .replaceAll("ᴥ06", "T")
                    .replaceAll("ᴥ07", "U")
                    .replaceAll("ᴥ08", "V")
                    .replaceAll("ᴥ09", "W")
                    .replaceAll("ᴥ10", "X")
                    .replaceAll("ᴥ11", "Y")
                    .replaceAll("ᴥ12", "Z")
                    .replaceAll("ᴥ13", "A")
                    .replaceAll("ᴥ14", "B")
                    .replaceAll("ᴥ15", "C")
                    .replaceAll("ᴥ16", "D")
                    .replaceAll("ᴥ17", "E")
                    .replaceAll("ᴥ18", "F")
                    .replaceAll("ᴥ19", "G")
                    .replaceAll("ᴥ20", "H")
                    .replaceAll("ᴥ21", "I")
                    .replaceAll("ᴥ22", "J")
                    .replaceAll("ᴥ23", "K")
                    .replaceAll("ᴥ24", "L")
                    .replaceAll("ᴥ25", "M")
                    .replaceAll("ᴥ26", "N")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("11")) {
            outputString = inputString
                    .replaceAll("⁞01", "p")
                    .replaceAll("⁞02", "q")
                    .replaceAll("⁞03", "r")
                    .replaceAll("⁞04", "s")
                    .replaceAll("⁞05", "t")
                    .replaceAll("⁞06", "u")
                    .replaceAll("⁞07", "v")
                    .replaceAll("⁞08", "w")
                    .replaceAll("⁞09", "x")
                    .replaceAll("⁞10", "y")
                    .replaceAll("⁞11", "z")
                    .replaceAll("⁞12", "a")
                    .replaceAll("⁞13", "b")
                    .replaceAll("⁞14", "c")
                    .replaceAll("⁞15", "d")
                    .replaceAll("⁞16", "e")
                    .replaceAll("⁞17", "f")
                    .replaceAll("⁞18", "g")
                    .replaceAll("⁞19", "h")
                    .replaceAll("⁞20", "i")
                    .replaceAll("⁞21", "j")
                    .replaceAll("⁞22", "k")
                    .replaceAll("⁞23", "l")
                    .replaceAll("⁞24", "m")
                    .replaceAll("⁞25", "n")
                    .replaceAll("⁞26", "o")
                    .replaceAll("ᴥ01", "P")
                    .replaceAll("ᴥ02", "Q")
                    .replaceAll("ᴥ03", "R")
                    .replaceAll("ᴥ04", "S")
                    .replaceAll("ᴥ05", "T")
                    .replaceAll("ᴥ06", "U")
                    .replaceAll("ᴥ07", "V")
                    .replaceAll("ᴥ08", "W")
                    .replaceAll("ᴥ09", "X")
                    .replaceAll("ᴥ10", "Y")
                    .replaceAll("ᴥ11", "Z")
                    .replaceAll("ᴥ12", "A")
                    .replaceAll("ᴥ13", "B")
                    .replaceAll("ᴥ14", "C")
                    .replaceAll("ᴥ15", "D")
                    .replaceAll("ᴥ16", "E")
                    .replaceAll("ᴥ17", "F")
                    .replaceAll("ᴥ18", "G")
                    .replaceAll("ᴥ19", "H")
                    .replaceAll("ᴥ20", "I")
                    .replaceAll("ᴥ21", "J")
                    .replaceAll("ᴥ22", "K")
                    .replaceAll("ᴥ23", "L")
                    .replaceAll("ᴥ24", "M")
                    .replaceAll("ᴥ25", "N")
                    .replaceAll("ᴥ26", "O")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("10")) {
            outputString = inputString
                    .replaceAll("⁞01", "q")
                    .replaceAll("⁞02", "r")
                    .replaceAll("⁞03", "s")
                    .replaceAll("⁞04", "t")
                    .replaceAll("⁞05", "u")
                    .replaceAll("⁞06", "v")
                    .replaceAll("⁞07", "w")
                    .replaceAll("⁞08", "x")
                    .replaceAll("⁞09", "y")
                    .replaceAll("⁞10", "z")
                    .replaceAll("⁞11", "a")
                    .replaceAll("⁞12", "b")
                    .replaceAll("⁞13", "c")
                    .replaceAll("⁞14", "d")
                    .replaceAll("⁞15", "e")
                    .replaceAll("⁞16", "f")
                    .replaceAll("⁞17", "g")
                    .replaceAll("⁞18", "h")
                    .replaceAll("⁞19", "i")
                    .replaceAll("⁞20", "j")
                    .replaceAll("⁞21", "k")
                    .replaceAll("⁞22", "l")
                    .replaceAll("⁞23", "m")
                    .replaceAll("⁞24", "n")
                    .replaceAll("⁞25", "o")
                    .replaceAll("⁞26", "p")
                    .replaceAll("ᴥ01", "Q")
                    .replaceAll("ᴥ02", "R")
                    .replaceAll("ᴥ03", "S")
                    .replaceAll("ᴥ04", "T")
                    .replaceAll("ᴥ05", "U")
                    .replaceAll("ᴥ06", "V")
                    .replaceAll("ᴥ07", "W")
                    .replaceAll("ᴥ08", "X")
                    .replaceAll("ᴥ09", "Y")
                    .replaceAll("ᴥ10", "Z")
                    .replaceAll("ᴥ11", "A")
                    .replaceAll("ᴥ12", "B")
                    .replaceAll("ᴥ13", "C")
                    .replaceAll("ᴥ14", "D")
                    .replaceAll("ᴥ15", "E")
                    .replaceAll("ᴥ16", "F")
                    .replaceAll("ᴥ17", "G")
                    .replaceAll("ᴥ18", "H")
                    .replaceAll("ᴥ19", "I")
                    .replaceAll("ᴥ20", "J")
                    .replaceAll("ᴥ21", "K")
                    .replaceAll("ᴥ22", "L")
                    .replaceAll("ᴥ23", "M")
                    .replaceAll("ᴥ24", "N")
                    .replaceAll("ᴥ25", "O")
                    .replaceAll("ᴥ26", "P")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("9")) {
            outputString = inputString
                    .replaceAll("⁞01", "r")
                    .replaceAll("⁞02", "s")
                    .replaceAll("⁞03", "t")
                    .replaceAll("⁞04", "u")
                    .replaceAll("⁞05", "v")
                    .replaceAll("⁞06", "w")
                    .replaceAll("⁞07", "x")
                    .replaceAll("⁞08", "y")
                    .replaceAll("⁞09", "z")
                    .replaceAll("⁞10", "a")
                    .replaceAll("⁞11", "b")
                    .replaceAll("⁞12", "c")
                    .replaceAll("⁞13", "d")
                    .replaceAll("⁞14", "e")
                    .replaceAll("⁞15", "f")
                    .replaceAll("⁞16", "g")
                    .replaceAll("⁞17", "h")
                    .replaceAll("⁞18", "i")
                    .replaceAll("⁞19", "j")
                    .replaceAll("⁞20", "k")
                    .replaceAll("⁞21", "l")
                    .replaceAll("⁞22", "m")
                    .replaceAll("⁞23", "n")
                    .replaceAll("⁞24", "o")
                    .replaceAll("⁞25", "p")
                    .replaceAll("⁞26", "q")
                    .replaceAll("ᴥ01", "R")
                    .replaceAll("ᴥ02", "S")
                    .replaceAll("ᴥ03", "T")
                    .replaceAll("ᴥ04", "U")
                    .replaceAll("ᴥ05", "V")
                    .replaceAll("ᴥ06", "W")
                    .replaceAll("ᴥ07", "X")
                    .replaceAll("ᴥ08", "Y")
                    .replaceAll("ᴥ09", "Z")
                    .replaceAll("ᴥ10", "A")
                    .replaceAll("ᴥ11", "B")
                    .replaceAll("ᴥ12", "C")
                    .replaceAll("ᴥ13", "D")
                    .replaceAll("ᴥ14", "E")
                    .replaceAll("ᴥ15", "F")
                    .replaceAll("ᴥ16", "G")
                    .replaceAll("ᴥ17", "H")
                    .replaceAll("ᴥ18", "I")
                    .replaceAll("ᴥ19", "J")
                    .replaceAll("ᴥ20", "K")
                    .replaceAll("ᴥ21", "L")
                    .replaceAll("ᴥ22", "M")
                    .replaceAll("ᴥ23", "N")
                    .replaceAll("ᴥ24", "O")
                    .replaceAll("ᴥ25", "P")
                    .replaceAll("ᴥ26", "Q")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("8")) {
            outputString = inputString
                    .replaceAll("⁞01", "s")
                    .replaceAll("⁞02", "t")
                    .replaceAll("⁞03", "u")
                    .replaceAll("⁞04", "v")
                    .replaceAll("⁞05", "w")
                    .replaceAll("⁞06", "x")
                    .replaceAll("⁞07", "y")
                    .replaceAll("⁞08", "z")
                    .replaceAll("⁞09", "a")
                    .replaceAll("⁞10", "b")
                    .replaceAll("⁞11", "c")
                    .replaceAll("⁞12", "d")
                    .replaceAll("⁞13", "e")
                    .replaceAll("⁞14", "f")
                    .replaceAll("⁞15", "g")
                    .replaceAll("⁞16", "h")
                    .replaceAll("⁞17", "i")
                    .replaceAll("⁞18", "j")
                    .replaceAll("⁞19", "k")
                    .replaceAll("⁞20", "l")
                    .replaceAll("⁞21", "m")
                    .replaceAll("⁞22", "n")
                    .replaceAll("⁞23", "o")
                    .replaceAll("⁞24", "p")
                    .replaceAll("⁞25", "q")
                    .replaceAll("⁞26", "r")
                    .replaceAll("ᴥ01", "S")
                    .replaceAll("ᴥ02", "T")
                    .replaceAll("ᴥ03", "U")
                    .replaceAll("ᴥ04", "V")
                    .replaceAll("ᴥ05", "W")
                    .replaceAll("ᴥ06", "X")
                    .replaceAll("ᴥ07", "Y")
                    .replaceAll("ᴥ08", "Z")
                    .replaceAll("ᴥ09", "A")
                    .replaceAll("ᴥ10", "B")
                    .replaceAll("ᴥ11", "C")
                    .replaceAll("ᴥ12", "D")
                    .replaceAll("ᴥ13", "E")
                    .replaceAll("ᴥ14", "F")
                    .replaceAll("ᴥ15", "G")
                    .replaceAll("ᴥ16", "H")
                    .replaceAll("ᴥ17", "I")
                    .replaceAll("ᴥ18", "J")
                    .replaceAll("ᴥ19", "K")
                    .replaceAll("ᴥ20", "L")
                    .replaceAll("ᴥ21", "M")
                    .replaceAll("ᴥ22", "N")
                    .replaceAll("ᴥ23", "O")
                    .replaceAll("ᴥ24", "P")
                    .replaceAll("ᴥ25", "Q")
                    .replaceAll("ᴥ26", "R")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("7")) {
            outputString = inputString
                    .replaceAll("⁞01", "t")
                    .replaceAll("⁞02", "u")
                    .replaceAll("⁞03", "v")
                    .replaceAll("⁞04", "w")
                    .replaceAll("⁞05", "x")
                    .replaceAll("⁞06", "y")
                    .replaceAll("⁞07", "z")
                    .replaceAll("⁞08", "a")
                    .replaceAll("⁞09", "b")
                    .replaceAll("⁞10", "c")
                    .replaceAll("⁞11", "d")
                    .replaceAll("⁞12", "e")
                    .replaceAll("⁞13", "f")
                    .replaceAll("⁞14", "g")
                    .replaceAll("⁞15", "h")
                    .replaceAll("⁞16", "i")
                    .replaceAll("⁞17", "j")
                    .replaceAll("⁞18", "k")
                    .replaceAll("⁞19", "l")
                    .replaceAll("⁞20", "m")
                    .replaceAll("⁞21", "n")
                    .replaceAll("⁞22", "o")
                    .replaceAll("⁞23", "p")
                    .replaceAll("⁞24", "q")
                    .replaceAll("⁞25", "r")
                    .replaceAll("⁞26", "s")
                    .replaceAll("ᴥ01", "T")
                    .replaceAll("ᴥ02", "U")
                    .replaceAll("ᴥ03", "V")
                    .replaceAll("ᴥ04", "W")
                    .replaceAll("ᴥ05", "X")
                    .replaceAll("ᴥ06", "Y")
                    .replaceAll("ᴥ07", "Z")
                    .replaceAll("ᴥ08", "A")
                    .replaceAll("ᴥ09", "B")
                    .replaceAll("ᴥ10", "C")
                    .replaceAll("ᴥ11", "D")
                    .replaceAll("ᴥ12", "E")
                    .replaceAll("ᴥ13", "F")
                    .replaceAll("ᴥ14", "G")
                    .replaceAll("ᴥ15", "H")
                    .replaceAll("ᴥ16", "I")
                    .replaceAll("ᴥ17", "J")
                    .replaceAll("ᴥ18", "K")
                    .replaceAll("ᴥ19", "L")
                    .replaceAll("ᴥ20", "M")
                    .replaceAll("ᴥ21", "N")
                    .replaceAll("ᴥ22", "O")
                    .replaceAll("ᴥ23", "P")
                    .replaceAll("ᴥ24", "Q")
                    .replaceAll("ᴥ25", "R")
                    .replaceAll("ᴥ26", "S")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("6")) {
            outputString = inputString
                    .replaceAll("⁞01", "u")
                    .replaceAll("⁞02", "v")
                    .replaceAll("⁞03", "w")
                    .replaceAll("⁞04", "x")
                    .replaceAll("⁞05", "y")
                    .replaceAll("⁞06", "z")
                    .replaceAll("⁞07", "a")
                    .replaceAll("⁞08", "b")
                    .replaceAll("⁞09", "c")
                    .replaceAll("⁞10", "d")
                    .replaceAll("⁞11", "e")
                    .replaceAll("⁞12", "f")
                    .replaceAll("⁞13", "g")
                    .replaceAll("⁞14", "h")
                    .replaceAll("⁞15", "i")
                    .replaceAll("⁞16", "j")
                    .replaceAll("⁞17", "k")
                    .replaceAll("⁞18", "l")
                    .replaceAll("⁞19", "m")
                    .replaceAll("⁞20", "n")
                    .replaceAll("⁞21", "o")
                    .replaceAll("⁞22", "p")
                    .replaceAll("⁞23", "q")
                    .replaceAll("⁞24", "r")
                    .replaceAll("⁞25", "s")
                    .replaceAll("⁞26", "t")
                    .replaceAll("ᴥ01", "U")
                    .replaceAll("ᴥ02", "V")
                    .replaceAll("ᴥ03", "W")
                    .replaceAll("ᴥ04", "X")
                    .replaceAll("ᴥ05", "Y")
                    .replaceAll("ᴥ06", "Z")
                    .replaceAll("ᴥ07", "A")
                    .replaceAll("ᴥ08", "B")
                    .replaceAll("ᴥ09", "C")
                    .replaceAll("ᴥ10", "D")
                    .replaceAll("ᴥ11", "E")
                    .replaceAll("ᴥ12", "F")
                    .replaceAll("ᴥ13", "G")
                    .replaceAll("ᴥ14", "H")
                    .replaceAll("ᴥ15", "I")
                    .replaceAll("ᴥ16", "J")
                    .replaceAll("ᴥ17", "K")
                    .replaceAll("ᴥ18", "L")
                    .replaceAll("ᴥ19", "M")
                    .replaceAll("ᴥ20", "N")
                    .replaceAll("ᴥ21", "O")
                    .replaceAll("ᴥ22", "P")
                    .replaceAll("ᴥ23", "Q")
                    .replaceAll("ᴥ24", "R")
                    .replaceAll("ᴥ25", "S")
                    .replaceAll("ᴥ26", "T")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("5")) {
            outputString = inputString
                    .replaceAll("⁞01", "v")
                    .replaceAll("⁞02", "w")
                    .replaceAll("⁞03", "x")
                    .replaceAll("⁞04", "y")
                    .replaceAll("⁞05", "z")
                    .replaceAll("⁞06", "a")
                    .replaceAll("⁞07", "b")
                    .replaceAll("⁞08", "c")
                    .replaceAll("⁞09", "d")
                    .replaceAll("⁞10", "e")
                    .replaceAll("⁞11", "f")
                    .replaceAll("⁞12", "g")
                    .replaceAll("⁞13", "h")
                    .replaceAll("⁞14", "i")
                    .replaceAll("⁞15", "j")
                    .replaceAll("⁞16", "k")
                    .replaceAll("⁞17", "l")
                    .replaceAll("⁞18", "m")
                    .replaceAll("⁞19", "n")
                    .replaceAll("⁞20", "o")
                    .replaceAll("⁞21", "p")
                    .replaceAll("⁞22", "q")
                    .replaceAll("⁞23", "r")
                    .replaceAll("⁞24", "s")
                    .replaceAll("⁞25", "t")
                    .replaceAll("⁞26", "u")
                    .replaceAll("ᴥ01", "V")
                    .replaceAll("ᴥ02", "W")
                    .replaceAll("ᴥ03", "X")
                    .replaceAll("ᴥ04", "Y")
                    .replaceAll("ᴥ05", "Z")
                    .replaceAll("ᴥ06", "A")
                    .replaceAll("ᴥ07", "B")
                    .replaceAll("ᴥ08", "C")
                    .replaceAll("ᴥ09", "D")
                    .replaceAll("ᴥ10", "E")
                    .replaceAll("ᴥ11", "F")
                    .replaceAll("ᴥ12", "G")
                    .replaceAll("ᴥ13", "H")
                    .replaceAll("ᴥ14", "I")
                    .replaceAll("ᴥ15", "J")
                    .replaceAll("ᴥ16", "K")
                    .replaceAll("ᴥ17", "L")
                    .replaceAll("ᴥ18", "M")
                    .replaceAll("ᴥ19", "N")
                    .replaceAll("ᴥ20", "O")
                    .replaceAll("ᴥ21", "P")
                    .replaceAll("ᴥ22", "Q")
                    .replaceAll("ᴥ23", "R")
                    .replaceAll("ᴥ24", "S")
                    .replaceAll("ᴥ25", "T")
                    .replaceAll("ᴥ26", "U")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("4")) {
            outputString = inputString
                    .replaceAll("⁞01", "w")
                    .replaceAll("⁞02", "x")
                    .replaceAll("⁞03", "y")
                    .replaceAll("⁞04", "z")
                    .replaceAll("⁞05", "a")
                    .replaceAll("⁞06", "b")
                    .replaceAll("⁞07", "c")
                    .replaceAll("⁞08", "d")
                    .replaceAll("⁞09", "e")
                    .replaceAll("⁞10", "f")
                    .replaceAll("⁞11", "g")
                    .replaceAll("⁞12", "h")
                    .replaceAll("⁞13", "i")
                    .replaceAll("⁞14", "j")
                    .replaceAll("⁞15", "k")
                    .replaceAll("⁞16", "l")
                    .replaceAll("⁞17", "m")
                    .replaceAll("⁞18", "n")
                    .replaceAll("⁞19", "o")
                    .replaceAll("⁞20", "p")
                    .replaceAll("⁞21", "q")
                    .replaceAll("⁞22", "r")
                    .replaceAll("⁞23", "s")
                    .replaceAll("⁞24", "t")
                    .replaceAll("⁞25", "u")
                    .replaceAll("⁞26", "v")
                    .replaceAll("ᴥ01", "W")
                    .replaceAll("ᴥ02", "X")
                    .replaceAll("ᴥ03", "Y")
                    .replaceAll("ᴥ04", "Z")
                    .replaceAll("ᴥ05", "A")
                    .replaceAll("ᴥ06", "B")
                    .replaceAll("ᴥ07", "C")
                    .replaceAll("ᴥ08", "D")
                    .replaceAll("ᴥ09", "E")
                    .replaceAll("ᴥ10", "F")
                    .replaceAll("ᴥ11", "G")
                    .replaceAll("ᴥ12", "H")
                    .replaceAll("ᴥ13", "I")
                    .replaceAll("ᴥ14", "J")
                    .replaceAll("ᴥ15", "K")
                    .replaceAll("ᴥ16", "L")
                    .replaceAll("ᴥ17", "M")
                    .replaceAll("ᴥ18", "N")
                    .replaceAll("ᴥ19", "O")
                    .replaceAll("ᴥ20", "P")
                    .replaceAll("ᴥ21", "Q")
                    .replaceAll("ᴥ22", "R")
                    .replaceAll("ᴥ23", "S")
                    .replaceAll("ᴥ24", "T")
                    .replaceAll("ᴥ25", "U")
                    .replaceAll("ᴥ26", "V")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("3")) {
            outputString = inputString
                    .replaceAll("⁞01", "x")
                    .replaceAll("⁞02", "y")
                    .replaceAll("⁞03", "z")
                    .replaceAll("⁞04", "a")
                    .replaceAll("⁞05", "b")
                    .replaceAll("⁞06", "c")
                    .replaceAll("⁞07", "d")
                    .replaceAll("⁞08", "e")
                    .replaceAll("⁞09", "f")
                    .replaceAll("⁞10", "g")
                    .replaceAll("⁞11", "h")
                    .replaceAll("⁞12", "i")
                    .replaceAll("⁞13", "j")
                    .replaceAll("⁞14", "k")
                    .replaceAll("⁞15", "l")
                    .replaceAll("⁞16", "m")
                    .replaceAll("⁞17", "n")
                    .replaceAll("⁞18", "o")
                    .replaceAll("⁞19", "p")
                    .replaceAll("⁞20", "q")
                    .replaceAll("⁞21", "r")
                    .replaceAll("⁞22", "s")
                    .replaceAll("⁞23", "t")
                    .replaceAll("⁞24", "u")
                    .replaceAll("⁞25", "v")
                    .replaceAll("⁞26", "w")
                    .replaceAll("ᴥ01", "X")
                    .replaceAll("ᴥ02", "Y")
                    .replaceAll("ᴥ03", "Z")
                    .replaceAll("ᴥ04", "A")
                    .replaceAll("ᴥ05", "B")
                    .replaceAll("ᴥ06", "C")
                    .replaceAll("ᴥ07", "D")
                    .replaceAll("ᴥ08", "E")
                    .replaceAll("ᴥ09", "F")
                    .replaceAll("ᴥ10", "G")
                    .replaceAll("ᴥ11", "H")
                    .replaceAll("ᴥ12", "I")
                    .replaceAll("ᴥ13", "J")
                    .replaceAll("ᴥ14", "K")
                    .replaceAll("ᴥ15", "L")
                    .replaceAll("ᴥ16", "M")
                    .replaceAll("ᴥ17", "N")
                    .replaceAll("ᴥ18", "O")
                    .replaceAll("ᴥ19", "P")
                    .replaceAll("ᴥ20", "Q")
                    .replaceAll("ᴥ21", "R")
                    .replaceAll("ᴥ22", "S")
                    .replaceAll("ᴥ23", "T")
                    .replaceAll("ᴥ24", "U")
                    .replaceAll("ᴥ25", "V")
                    .replaceAll("ᴥ26", "W")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("2")) {
            outputString = inputString
                    .replaceAll("⁞01", "y")
                    .replaceAll("⁞02", "z")
                    .replaceAll("⁞03", "a")
                    .replaceAll("⁞04", "b")
                    .replaceAll("⁞05", "c")
                    .replaceAll("⁞06", "d")
                    .replaceAll("⁞07", "e")
                    .replaceAll("⁞08", "f")
                    .replaceAll("⁞09", "g")
                    .replaceAll("⁞10", "h")
                    .replaceAll("⁞11", "i")
                    .replaceAll("⁞12", "j")
                    .replaceAll("⁞13", "k")
                    .replaceAll("⁞14", "l")
                    .replaceAll("⁞15", "m")
                    .replaceAll("⁞16", "n")
                    .replaceAll("⁞17", "o")
                    .replaceAll("⁞18", "p")
                    .replaceAll("⁞19", "q")
                    .replaceAll("⁞20", "r")
                    .replaceAll("⁞21", "s")
                    .replaceAll("⁞22", "t")
                    .replaceAll("⁞23", "u")
                    .replaceAll("⁞24", "v")
                    .replaceAll("⁞25", "w")
                    .replaceAll("⁞26", "x")
                    .replaceAll("ᴥ01", "Y")
                    .replaceAll("ᴥ02", "Z")
                    .replaceAll("ᴥ03", "A")
                    .replaceAll("ᴥ04", "B")
                    .replaceAll("ᴥ05", "C")
                    .replaceAll("ᴥ06", "D")
                    .replaceAll("ᴥ07", "E")
                    .replaceAll("ᴥ08", "F")
                    .replaceAll("ᴥ09", "G")
                    .replaceAll("ᴥ10", "H")
                    .replaceAll("ᴥ11", "I")
                    .replaceAll("ᴥ12", "J")
                    .replaceAll("ᴥ13", "K")
                    .replaceAll("ᴥ14", "L")
                    .replaceAll("ᴥ15", "M")
                    .replaceAll("ᴥ16", "N")
                    .replaceAll("ᴥ17", "O")
                    .replaceAll("ᴥ18", "P")
                    .replaceAll("ᴥ19", "Q")
                    .replaceAll("ᴥ20", "R")
                    .replaceAll("ᴥ21", "S")
                    .replaceAll("ᴥ22", "T")
                    .replaceAll("ᴥ23", "U")
                    .replaceAll("ᴥ24", "V")
                    .replaceAll("ᴥ25", "W")
                    .replaceAll("ᴥ26", "X")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        else if(dialog_choose_selected.equals("1")) {
            outputString = inputString
                    .replaceAll("⁞01", "z")
                    .replaceAll("⁞02", "a")
                    .replaceAll("⁞03", "b")
                    .replaceAll("⁞04", "c")
                    .replaceAll("⁞05", "d")
                    .replaceAll("⁞06", "e")
                    .replaceAll("⁞07", "f")
                    .replaceAll("⁞08", "g")
                    .replaceAll("⁞09", "h")
                    .replaceAll("⁞10", "i")
                    .replaceAll("⁞11", "j")
                    .replaceAll("⁞12", "k")
                    .replaceAll("⁞13", "l")
                    .replaceAll("⁞14", "m")
                    .replaceAll("⁞15", "n")
                    .replaceAll("⁞16", "o")
                    .replaceAll("⁞17", "p")
                    .replaceAll("⁞18", "q")
                    .replaceAll("⁞19", "r")
                    .replaceAll("⁞20", "s")
                    .replaceAll("⁞21", "t")
                    .replaceAll("⁞22", "u")
                    .replaceAll("⁞23", "v")
                    .replaceAll("⁞24", "w")
                    .replaceAll("⁞25", "x")
                    .replaceAll("⁞26", "y")
                    .replaceAll("ᴥ01", "Z")
                    .replaceAll("ᴥ02", "A")
                    .replaceAll("ᴥ03", "B")
                    .replaceAll("ᴥ04", "C")
                    .replaceAll("ᴥ05", "D")
                    .replaceAll("ᴥ06", "E")
                    .replaceAll("ᴥ07", "F")
                    .replaceAll("ᴥ08", "G")
                    .replaceAll("ᴥ09", "H")
                    .replaceAll("ᴥ10", "I")
                    .replaceAll("ᴥ11", "J")
                    .replaceAll("ᴥ12", "K")
                    .replaceAll("ᴥ13", "L")
                    .replaceAll("ᴥ14", "M")
                    .replaceAll("ᴥ15", "N")
                    .replaceAll("ᴥ16", "O")
                    .replaceAll("ᴥ17", "P")
                    .replaceAll("ᴥ18", "Q")
                    .replaceAll("ᴥ19", "R")
                    .replaceAll("ᴥ20", "S")
                    .replaceAll("ᴥ21", "T")
                    .replaceAll("ᴥ22", "U")
                    .replaceAll("ᴥ23", "V")
                    .replaceAll("ᴥ24", "W")
                    .replaceAll("ᴥ25", "X")
                    .replaceAll("ᴥ26", "Y")
            ;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
