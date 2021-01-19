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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DotDotCodesActivity extends AppCompatActivity {

    String codename = "Dot Dot Codes";
    String codesample = "(•••°•)(°°°°••)(°•°°•)/(•••°•)(°°°°••)(°•°°•)/(••°••)(°°°°••)(•••°•)(••••°)(•°•°°)";

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

    private LinearLayout character_option_layout;
    private Button character_option_character_1;
    private Button character_option_character_2;
    private Button character_option_character_3;
    private Button character_option_character_4;
    private Button character_option_character_5;
    private Button character_option_character_6;
    private Button character_option_character_7;
    private Button character_option_character_8;
    private Button character_option_character_9;
    private Button character_option_character_10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        overridePendingTransition(R.anim.act2_enter,R.anim.act2_exit);

        code();

        info();

        characteroption();

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

    public void characteroption() {
        character_option_layout = (LinearLayout)findViewById(R.id.character_option_layout);
        character_option_layout.setVisibility(View.VISIBLE);
        character_option_character_1 = (Button)findViewById(R.id.character_option_character_1);
        character_option_character_2 = (Button)findViewById(R.id.character_option_character_2);
        character_option_character_3 = (Button)findViewById(R.id.character_option_character_3);
        character_option_character_4 = (Button)findViewById(R.id.character_option_character_4);
        character_option_character_5 = (Button)findViewById(R.id.character_option_character_5);
        character_option_character_6 = (Button)findViewById(R.id.character_option_character_6);
        character_option_character_7 = (Button)findViewById(R.id.character_option_character_7);
        character_option_character_8 = (Button)findViewById(R.id.character_option_character_8);
        character_option_character_9 = (Button)findViewById(R.id.character_option_character_9);
        character_option_character_10 = (Button)findViewById(R.id.character_option_character_10);
        character_option_character_1.setVisibility(View.VISIBLE);
        character_option_character_2.setVisibility(View.VISIBLE);
        character_option_character_3.setVisibility(View.VISIBLE);
        character_option_character_4.setVisibility(View.VISIBLE);
        character_option_character_5.setVisibility(View.VISIBLE);
        character_option_character_6.setVisibility(View.GONE);
        character_option_character_7.setVisibility(View.GONE);
        character_option_character_8.setVisibility(View.GONE);
        character_option_character_9.setVisibility(View.GONE);
        character_option_character_10.setVisibility(View.GONE);
        character_option_character_1.setText("(");
        character_option_character_2.setText("°");
        character_option_character_3.setText("•");
        character_option_character_4.setText(")");
        character_option_character_5.setText("/");
        character_option_character_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int start = input.getSelectionStart();
                String addChar = character_option_character_1.getText().toString();
                input.getText().insert(start, addChar);
            }
        });
        character_option_character_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int start = input.getSelectionStart();
                String addChar = character_option_character_2.getText().toString();
                input.getText().insert(start, addChar);
            }
        });
        character_option_character_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int start = input.getSelectionStart();
                String addChar = character_option_character_3.getText().toString();
                input.getText().insert(start, addChar);
            }
        });
        character_option_character_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int start = input.getSelectionStart();
                String addChar = character_option_character_4.getText().toString();
                input.getText().insert(start, addChar);
            }
        });
        character_option_character_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int start = input.getSelectionStart();
                String addChar = character_option_character_5.getText().toString();
                input.getText().insert(start, addChar);
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
                    dotdotcodesencrypt();
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
                /*else if (input.getText().toString().indexOf("  ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Multiple spaces not supported.", Toast.LENGTH_SHORT).show();
                }*/
                else {
                    dotdotcodesdecrypt();
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

    public void dotdotcodesencrypt(){
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

        else if (input.getText().toString().indexOf("  ")!=-1) {
            Toast.makeText(getApplicationContext(), "Multiple spaces not supported.", Toast.LENGTH_SHORT).show();
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

            String convert = removeextralinebreak
                    .replaceAll("⁞ ⁞⁞⁞ ", "")//for separator
                    .replaceAll("⁞⁞⁞  ⁞", "")//for separator
                    .replaceAll("⁞ ", "")//for separator
                    .replaceAll(" ⁞", "")//for separator
                    .replaceAll("⁞⁞⁞", "")//for separator
                    .replaceAll("⁞⁞", "⁞/⁞")//this is the space separator
                    .replaceAll("a", "⁞(°••••)")
                    .replaceAll("b", "⁞(•°•••)")
                    .replaceAll("c", "⁞(••°••)")
                    .replaceAll("d", "⁞(•••°•)")
                    .replaceAll("e", "⁞(••••°)")
                    .replaceAll("f", "⁞(•°°°°)")
                    .replaceAll("g", "⁞(°•°°°)")
                    .replaceAll("h", "⁞(°°•°°)")
                    .replaceAll("i", "⁞(°°°•°)")
                    .replaceAll("j", "⁞(°°°°•)")
                    .replaceAll("k", "⁞(°•°•°)")
                    .replaceAll("l", "⁞(•°•°•)")
                    .replaceAll("m", "⁞(••°°°°)")
                    .replaceAll("n", "⁞(°°••°°)")
                    .replaceAll("o", "⁞(°°°°••)")
                    .replaceAll("p", "⁞(°°••••)")
                    .replaceAll("q", "⁞(••°°••)")
                    .replaceAll("r", "⁞(••••°°)")
                    .replaceAll("s", "⁞(•°•°°)")
                    .replaceAll("t", "⁞(°•°°•)")
                    .replaceAll("u", "⁞(•°°•°)")
                    .replaceAll("v", "⁞(°°•°•)")
                    .replaceAll("w", "⁞(°•°••)")
                    .replaceAll("x", "⁞(•°••°)")
                    .replaceAll("y", "⁞(°••°•)")
                    .replaceAll("z", "⁞(••°•°)")
                    .replaceAll("⁞", "")//this is to remove extra characters
                    ;

            outputString = convert;

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    public void dotdotcodesdecrypt(){
        inputString = input.getText().toString().toLowerCase().replaceAll("/", " / ").replaceAll("  /  ", " / ").replaceAll("\\(","\t\\(").replaceAll("\\)","\\)\t").replaceAll("\t","⁞");

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
                    .replaceAll("⁞\\(°••••\\)⁞", "")
                    .replaceAll("⁞\\(•°•••\\)⁞", "")
                    .replaceAll("⁞\\(••°••\\)⁞", "")
                    .replaceAll("⁞\\(•••°•\\)⁞", "")
                    .replaceAll("⁞\\(••••°\\)⁞", "")
                    .replaceAll("⁞\\(•°°°°\\)⁞", "")
                    .replaceAll("⁞\\(°•°°°\\)⁞", "")
                    .replaceAll("⁞\\(°°•°°\\)⁞", "")
                    .replaceAll("⁞\\(°°°•°\\)⁞", "")
                    .replaceAll("⁞\\(°°°°•\\)⁞", "")
                    .replaceAll("⁞\\(°•°•°\\)⁞", "")
                    .replaceAll("⁞\\(•°•°•\\)⁞", "")
                    .replaceAll("⁞\\(••°°°°\\)⁞", "")
                    .replaceAll("⁞\\(°°••°°\\)⁞", "")
                    .replaceAll("⁞\\(°°°°••\\)⁞", "")
                    .replaceAll("⁞\\(°°••••\\)⁞", "")
                    .replaceAll("⁞\\(••°°••\\)⁞", "")
                    .replaceAll("⁞\\(••••°°\\)⁞", "")
                    .replaceAll("⁞\\(•°•°°\\)⁞", "")
                    .replaceAll("⁞\\(°•°°•\\)⁞", "")
                    .replaceAll("⁞\\(•°°•°\\)⁞", "")
                    .replaceAll("⁞\\(°°•°•\\)⁞", "")
                    .replaceAll("⁞\\(°•°••\\)⁞", "")
                    .replaceAll("⁞\\(•°••°\\)⁞", "")
                    .replaceAll("⁞\\(°••°•\\)⁞", "")
                    .replaceAll("⁞\\(••°•°\\)⁞", "")
                    .replaceAll(" ", "")
                    .replaceAll("/", "⁞ ⁞")//separator
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
        else if(input.getText().toString().indexOf("//")!=-1||input.getText().toString().indexOf("/ /")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
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
        else if(input.getText().toString().replaceAll(" ","").indexOf("//")!=-1||input.getText().toString().indexOf("/ /")!=-1){
            Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
        }
        else{
            for (String splitword : newconvertsequence.toString().split("\n")) {
                codesequence = (codesequence + new StringBuilder(splitword).toString()
                        .replaceAll("⁞\\(°••••\\)⁞", "a")
                        .replaceAll("⁞\\(•°•••\\)⁞", "b")
                        .replaceAll("⁞\\(••°••\\)⁞", "c")
                        .replaceAll("⁞\\(•••°•\\)⁞", "d")
                        .replaceAll("⁞\\(••••°\\)⁞", "e")
                        .replaceAll("⁞\\(•°°°°\\)⁞", "f")
                        .replaceAll("⁞\\(°•°°°\\)⁞", "g")
                        .replaceAll("⁞\\(°°•°°\\)⁞", "h")
                        .replaceAll("⁞\\(°°°•°\\)⁞", "i")
                        .replaceAll("⁞\\(°°°°•\\)⁞", "j")
                        .replaceAll("⁞\\(°•°•°\\)⁞", "k")
                        .replaceAll("⁞\\(•°•°•\\)⁞", "l")
                        .replaceAll("⁞\\(••°°°°\\)⁞", "m")
                        .replaceAll("⁞\\(°°••°°\\)⁞", "n")
                        .replaceAll("⁞\\(°°°°••\\)⁞", "o")
                        .replaceAll("⁞\\(°°••••\\)⁞", "p")
                        .replaceAll("⁞\\(••°°••\\)⁞", "q")
                        .replaceAll("⁞\\(••••°°\\)⁞", "r")
                        .replaceAll("⁞\\(•°•°°\\)⁞", "s")
                        .replaceAll("⁞\\(°•°°•\\)⁞", "t")
                        .replaceAll("⁞\\(•°°•°\\)⁞", "u")
                        .replaceAll("⁞\\(°°•°•\\)⁞", "v")
                        .replaceAll("⁞\\(°•°••\\)⁞", "w")
                        .replaceAll("⁞\\(•°••°\\)⁞", "x")
                        .replaceAll("⁞\\(°••°•\\)⁞", "y")
                        .replaceAll("⁞\\(••°•°\\)⁞", "z")
                        .replaceAll(" ", "")
                        .replaceAll("/", "⁞ ⁞")//separator
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