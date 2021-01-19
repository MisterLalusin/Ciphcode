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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DrenzenCipherActivity extends AppCompatActivity {

    String codename = "Drenzen Cipher";
    String codesample = "13-C3-14-B4-FF-14-B4 12-A4-C1-A3-14-C3";

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
                /*else if (input.getText().toString().toLowerCase().indexOf("a")==-1&& input.getText().toString().toLowerCase().indexOf("b")==-1&& input.getText().toString().toLowerCase().indexOf("c")==-1&& input.getText().toString().toLowerCase().indexOf("d")==-1&& input.getText().toString().toLowerCase().indexOf("e")==-1&& input.getText().toString().toLowerCase().indexOf("f")==-1&& input.getText().toString().toLowerCase().indexOf("g")==-1&& input.getText().toString().toLowerCase().indexOf("h")==-1&& input.getText().toString().toLowerCase().indexOf("i")==-1&& input.getText().toString().toLowerCase().indexOf("j")==-1&& input.getText().toString().toLowerCase().indexOf("k")==-1&& input.getText().toString().toLowerCase().indexOf("l")==-1&& input.getText().toString().toLowerCase().indexOf("m")==-1&& input.getText().toString().toLowerCase().indexOf("n")==-1&& input.getText().toString().toLowerCase().indexOf("o")==-1&& input.getText().toString().toLowerCase().indexOf("p")==-1&& input.getText().toString().toLowerCase().indexOf("q")==-1&& input.getText().toString().toLowerCase().indexOf("r")==-1&& input.getText().toString().toLowerCase().indexOf("s")==-1&& input.getText().toString().toLowerCase().indexOf("t")==-1&& input.getText().toString().toLowerCase().indexOf("u")==-1&& input.getText().toString().toLowerCase().indexOf("v")==-1&& input.getText().toString().toLowerCase().indexOf("w")==-1&& input.getText().toString().toLowerCase().indexOf("x")==-1&& input.getText().toString().toLowerCase().indexOf("y")==-1&& input.getText().toString().toLowerCase().indexOf("z")==-1) {
                    Toast.makeText(getApplicationContext(), "Nothing to encrypt.", Toast.LENGTH_SHORT).show();
                }*/
                else if (input.getText().toString().indexOf("⁞")!=-1) {
                    Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                }
                else if (input.getText().toString().indexOf("ᴥ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                }
                else if (input.getText().toString().toLowerCase()
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

                        .length() != 0) {
                    Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                }
                /*else if (input.getText().toString().indexOf("  ")!=-1||input.getText().toString().indexOf("\n\n\n")!=-1) {
                    Toast.makeText(getApplicationContext(), "Too much spaces detected.", Toast.LENGTH_SHORT).show();
                }*/
                else if (input.getText().toString().indexOf("  ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Multiple spaces not supported.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();
                    drenzencipherencrypt();
                }
            }
        });

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Drenzen Cipher
                Matcher drenMat1 = Pattern.compile("[0-9]"+"[0-9]"+"[0-9]"+"[0-9]").matcher(input.getText().toString());
                Matcher drenMat2 = Pattern.compile("[a-z]"+"[a-z]"+"[a-z]"+"[a-z]").matcher(input.getText().toString());
                Matcher drenMat3 = Pattern.compile("[a-z]"+"[0-9]"+"[0-9]"+"[0-9]").matcher(input.getText().toString());
                Matcher drenMat4 = Pattern.compile("[0-9]"+"[0-9]"+"[a-z]"+"[0-9]").matcher(input.getText().toString());
                //Drenzen Cipher
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
                else if (input.getText().toString().toUpperCase()
                        .replaceAll("10", "")
                        .replaceAll("11", "")
                        .replaceAll("12", "")
                        .replaceAll("13", "")
                        .replaceAll("14", "")
                        .replaceAll("A1", "")
                        .replaceAll("A2", "")
                        .replaceAll("A3", "")
                        .replaceAll("A4", "")
                        .replaceAll("A5", "")
                        .replaceAll("B1", "")
                        .replaceAll("B2", "")
                        .replaceAll("B3", "")
                        .replaceAll("B4", "")
                        .replaceAll("B5", "")
                        .replaceAll("C1", "")
                        .replaceAll("C2", "")
                        .replaceAll("C3", "")
                        .replaceAll("C4", "")
                        .replaceAll("C5", "")
                        .replaceAll("AA", "")
                        .replaceAll("BB", "")
                        .replaceAll("CC", "")
                        .replaceAll("DD", "")
                        .replaceAll("EE", "")
                        .replaceAll("FF", "")
                        .replaceAll(" ","")
                        .replaceAll("\n","")
                        .replaceAll("-","")
                        .length() != 0) {
                    Toast.makeText(getApplicationContext(), "Not a valid code.", Toast.LENGTH_SHORT).show();
                }
                else if(input.getText().toString().replaceAll(" ","").indexOf("--")!=-1||input.getText().toString().indexOf("/ /")!=-1){
                    Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                }
                else if (input.getText().toString().indexOf(" -")!=-1||input.getText().toString().indexOf("- ")!=-1||input.getText().toString().indexOf("-\n")!=-1||input.getText().toString().indexOf("\n-")!=-1) {
                    Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                }
                else if (input.getText().toString().indexOf("-\n")!=-1||input.getText().toString().indexOf("\n-")!=-1) {
                    Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                }
                //Drenzen Cipher
                else if (drenMat1.find()||drenMat2.find()||drenMat3.find()||drenMat4.find()) {
                    Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                }
                //Drenzen Cipher
                else {
                    Toast.makeText(getApplicationContext(), "Decrypted succesfully.", Toast.LENGTH_SHORT).show();
                    drenzencipherdecrypt();
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

    public void drenzencipherencrypt(){
        inputString = input.getText().toString().toLowerCase();

        StringBuilder sb1 = new StringBuilder(inputString);

        char separator = '⁞';

        for (int i=0; i < inputString.length() / 1; i++) {
            sb1.insert(((i+1)*1)+i, separator);
        }

        String convert = sb1.toString()
                .replaceAll("a⁞","10-⁞")
                .replaceAll("b⁞","11-⁞")
                .replaceAll("c⁞","12-⁞")
                .replaceAll("d⁞","13-⁞")
                .replaceAll("e⁞","14-⁞")
                .replaceAll("f⁞","A1-⁞")
                .replaceAll("g⁞","A2-⁞")
                .replaceAll("h⁞","A3-⁞")
                .replaceAll("i⁞","A4-⁞")
                .replaceAll("j⁞","A5-⁞")
                .replaceAll("k⁞","B1-⁞")
                .replaceAll("l⁞","B2-⁞")
                .replaceAll("m⁞","B3-⁞")
                .replaceAll("n⁞","B4-⁞")
                .replaceAll("o⁞","B5-⁞")
                .replaceAll("p⁞","C1-⁞")
                .replaceAll("q⁞","C2-⁞")
                .replaceAll("r⁞","C3-⁞")
                .replaceAll("s⁞","C4-⁞")
                .replaceAll("t⁞","C5-⁞")
                .replaceAll("u⁞","AA-⁞")
                .replaceAll("v⁞","BB-⁞")
                .replaceAll("w⁞","CC-⁞")
                .replaceAll("x⁞","DD-⁞")
                .replaceAll("y⁞","EE-⁞")
                .replaceAll("z⁞","FF-⁞")
        ;

        String addforremoval = convert + " ";

        outputString = addforremoval.replaceAll("-⁞ "," ").replaceAll("- "," ").replaceAll("⁞","").replaceAll("-\n","\n");

        output.setText(outputString);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void drenzencipherdecrypt(){

        inputString = input.getText().toString().toUpperCase();

        int i = 0;

        CharSequence codesequence = BuildConfig.FLAVOR;
        String separatecode = null;

        for (String splitword : inputString.split("\n")) {
            codesequence = (codesequence + new StringBuilder(splitword).toString()) + "-\n";
            codesequence = codesequence.toString().replaceAll(" -", "-");
        }
        i++;
        separatecode = codesequence.toString().replaceAll(" ","- ");


        String convert = separatecode
                .replaceAll("10-","a")
                .replaceAll("11-","b")
                .replaceAll("12-","c")
                .replaceAll("13-","d")
                .replaceAll("14-","e")
                .replaceAll("A1-","f")
                .replaceAll("A2-","g")
                .replaceAll("A3-","h")
                .replaceAll("A4-","i")
                .replaceAll("A5-","j")
                .replaceAll("B1-","k")
                .replaceAll("B2-","l")
                .replaceAll("B3-","m")
                .replaceAll("B4-","n")
                .replaceAll("B5-","o")
                .replaceAll("C1-","p")
                .replaceAll("C2-","q")
                .replaceAll("C3-","r")
                .replaceAll("C4-","s")
                .replaceAll("C5-","t")
                .replaceAll("AA-","u")
                .replaceAll("BB-","v")
                .replaceAll("CC-","w")
                .replaceAll("DD-","x")
                .replaceAll("EE-","y")
                .replaceAll("FF-","z")

                +"⁞"
                ;

        outputString = convert.replaceAll("-","").replaceAll("\n\n⁞","").replaceAll("\n⁞","");

        output.setText(outputString);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
