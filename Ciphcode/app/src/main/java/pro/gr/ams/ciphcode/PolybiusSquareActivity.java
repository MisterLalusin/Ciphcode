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

public class PolybiusSquareActivity extends AppCompatActivity {

    String codename = "Polybius Square";
    String codesample = "35-34-31-54-12-24-45-43 43-41-45-11-42-15";

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
                    polybiussquareencrypt();
                }
            }
        });

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Polybius Square
                Matcher polyMat1 = Pattern.compile("[0-9]"+"[0-9]"+"[0-9]"+"[0-9]").matcher(input.getText().toString());
                //Polybius Square

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
                        .replaceAll("11", "")
                        .replaceAll("12", "")
                        .replaceAll("13", "")
                        .replaceAll("14", "")
                        .replaceAll("15", "")
                        .replaceAll("21", "")
                        .replaceAll("22", "")
                        .replaceAll("23", "")
                        .replaceAll("24", "")
                        .replaceAll("24", "")
                        .replaceAll("25", "")
                        .replaceAll("31", "")
                        .replaceAll("32", "")
                        .replaceAll("33", "")
                        .replaceAll("34", "")
                        .replaceAll("35", "")
                        .replaceAll("41", "")
                        .replaceAll("42", "")
                        .replaceAll("43", "")
                        .replaceAll("44", "")
                        .replaceAll("45", "")
                        .replaceAll("51", "")
                        .replaceAll("52", "")
                        .replaceAll("53", "")
                        .replaceAll("54", "")
                        .replaceAll("55", "")
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
                //Polybius Square
                else if (polyMat1.find()) {
                    Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                }
                //Polybius Square
                else {
                    Toast.makeText(getApplicationContext(), "Decrypted succesfully.", Toast.LENGTH_SHORT).show();
                    polybiussquaredecrypt();
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

    public void polybiussquareencrypt(){
        inputString = input.getText().toString().toLowerCase();

        StringBuilder sb1 = new StringBuilder(inputString);

        char separator = '⁞';

        for (int i=0; i < inputString.length() / 1; i++) {
            sb1.insert(((i+1)*1)+i, separator);
        }

        String convert = sb1.toString()
                .replaceAll("a⁞","11-⁞")
                .replaceAll("b⁞","12-⁞")
                .replaceAll("c⁞","13-⁞")
                .replaceAll("d⁞","14-⁞")
                .replaceAll("e⁞","15-⁞")
                .replaceAll("f⁞","21-⁞")
                .replaceAll("g⁞","22-⁞")
                .replaceAll("h⁞","23-⁞")
                .replaceAll("i⁞","24-⁞")
                .replaceAll("j⁞","24-⁞")
                .replaceAll("k⁞","25-⁞")
                .replaceAll("l⁞","31-⁞")
                .replaceAll("m⁞","32-⁞")
                .replaceAll("n⁞","33-⁞")
                .replaceAll("o⁞","34-⁞")
                .replaceAll("p⁞","35-⁞")
                .replaceAll("q⁞","41-⁞")
                .replaceAll("r⁞","42-⁞")
                .replaceAll("s⁞","43-⁞")
                .replaceAll("t⁞","44-⁞")
                .replaceAll("u⁞","45-⁞")
                .replaceAll("v⁞","51-⁞")
                .replaceAll("w⁞","52-⁞")
                .replaceAll("x⁞","53-⁞")
                .replaceAll("y⁞","54-⁞")
                .replaceAll("z⁞","55-⁞")
                ;

        String addforremoval = convert + " ";

        outputString = addforremoval.replaceAll("-⁞ "," ").replaceAll("- "," ").replaceAll("⁞","").replaceAll("-\n","\n");

        output.setText(outputString);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void polybiussquaredecrypt(){

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
                .replaceAll("11-","a")
                .replaceAll("12-","b")
                .replaceAll("13-","c")
                .replaceAll("14-","d")
                .replaceAll("15-","e")
                .replaceAll("21-","f")
                .replaceAll("22-","g")
                .replaceAll("23-","h")
                .replaceAll("24-","i")
                .replaceAll("24-","j")
                .replaceAll("25-","k")
                .replaceAll("31-","l")
                .replaceAll("32-","m")
                .replaceAll("33-","n")
                .replaceAll("34-","o")
                .replaceAll("35-","p")
                .replaceAll("41-","q")
                .replaceAll("42-","r")
                .replaceAll("43-","s")
                .replaceAll("44-","t")
                .replaceAll("45-","u")
                .replaceAll("51-","v")
                .replaceAll("52-","w")
                .replaceAll("53-","x")
                .replaceAll("54-","y")
                .replaceAll("55-","z")

                +"⁞"
                ;

        outputString = convert.replaceAll("-","").replaceAll("\n\n⁞","").replaceAll("\n⁞","");

        output.setText(outputString);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
