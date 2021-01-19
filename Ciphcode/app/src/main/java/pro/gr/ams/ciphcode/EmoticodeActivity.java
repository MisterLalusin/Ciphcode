package pro.gr.ams.ciphcode;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EmoticodeActivity extends AppCompatActivity {

    String codename = "Emoticode";
    String codesample = "";

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
    private ImageView code_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        overridePendingTransition(R.anim.act2_enter,R.anim.act2_exit);

        code();

        info();

        overrideSample();

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

    public void overrideSample() {
        code_sample.setVisibility(View.GONE);
        code_image = (ImageView)findViewById(R.id.code_image);
        code_image.setVisibility(View.VISIBLE);

        String uriString = "android.resource://pro.gr.ams.ciphcode/" + R.raw.emoticode_sample;
        Uri uri = Uri.parse(uriString);

        code_image.setImageURI(uri);
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
                else if (input.getText().toString().indexOf("\n\n\n")!=-1) {
                    Toast.makeText(getApplicationContext(), "Multiple line breaks not supported.", Toast.LENGTH_SHORT).show();
                }
                else {
                    emoticodeencrypt();
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
                    emoticodedecrypt();
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

    public void emoticodeencrypt(){
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
                    .replaceAll("⁞⁞", "⁞\uD83D\uDE0D\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D⁞")//this is the space separator
                    .replaceAll("a", "⁞\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE22\uD83D\uDE0D")
                    .replaceAll("b", "⁞\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("c", "⁞\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("d", "⁞\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("e", "⁞\uD83D\uDE07\uD83D\uDE07\uD83D\uDE22\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("f", "⁞\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("g", "⁞\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("h", "⁞\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE0D")
                    .replaceAll("i", "⁞\uD83D\uDE22\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE22\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("j", "⁞\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("k", "⁞\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("l", "⁞\uD83D\uDE02\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("m", "⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("n", "⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE0D")
                    .replaceAll("o", "⁞\uD83D\uDE22\uD83D\uDE22\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE22\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("p", "⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("q", "⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("r", "⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("s", "⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE0D")
                    .replaceAll("t", "⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("u", "⁞\uD83D\uDE22\uD83D\uDE22\uD83D\uDE22\uD83D\uDE07\uD83D\uDE22\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("v", "⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("w", "⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE0D")
                    .replaceAll("x", "⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("y", "⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("z", "⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE02\uD83D\uDE0D")
                    .replaceAll(" ", "⁞\uD83D\uDE0D\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D")
                    .replaceAll("⁞", "")//this is to remove extra characters

                    .replaceAll("\n", "\uD83D\uDE1C")
                    ;


            String addforextraseparator = convert+"⁞";
            String removeextraseparator = addforextraseparator.replaceAll("\uD83D\uDE0D⁞","");
            String convertSpace = removeextraseparator.replaceAll("\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D","\uD83D\uDE0D\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D");
            String convertLineBreakOne = convertSpace.replaceAll("\uD83D\uDE0D\uD83D\uDE1C","\uD83D\uDE1C");
            String convertLineBreakTwo = convertLineBreakOne.replaceAll("\uD83D\uDE0D\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D\uD83D\uDE1C","\uD83D\uDE1C");
            String endofparagraph = convertLineBreakTwo.replaceAll("\uD83D\uDE1C\uD83D\uDE1C","\uD83D\uDE36");

            outputString = endofparagraph;

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    public void emoticodedecrypt(){
        inputString = input.getText().toString().toLowerCase().replaceAll("\uD83D\uDE0D"," \uD83D\uDE0D ").replaceAll("\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07"," \uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07 ").replaceAll("\uD83D\uDE1C"," \uD83D\uDE1C ").replaceAll("\uD83D\uDE36"," \uD83D\uDE36 ").replaceAll("\n", " \uD83D\uDE36 ");

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
                    .replaceAll("⁞\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE22⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE07\uD83D\uDE07\uD83D\uDE22\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE22\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE22\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE02\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE22\uD83D\uDE22\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE22\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE22\uD83D\uDE22\uD83D\uDE22\uD83D\uDE07\uD83D\uDE22\uD83D\uDE07\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07⁞", "⁞⁞")
                    .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE02⁞", "⁞⁞")
                    .replaceAll("\uD83D\uDE0D", "")
                    .replaceAll("⁞\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞⁞")//separator
                    .replaceAll("\uD83D\uDE36", "⁞⁞")//to add linebreak
                    .replaceAll("⁞\uD83D\uDE1C⁞", "⁞⁞")//to add linebreak
                    .replaceAll("⁞", "")//this is to remove extra characters
            );
        }
        i++;

        String chk4unsprtdchar = chkcodesequence.toString();

        String chkmultipleseparator = input.getText().toString()+"⁞";

        if (chk4unsprtdchar.length()!=0){
            Toast.makeText(this, "Not a valid code.", Toast.LENGTH_SHORT).show();
        }
        else if(input.getText().toString().indexOf(" ")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
        else if(chkmultipleseparator.indexOf("\uD83D\uDE0D⁞")!=-1){
            Toast.makeText(this, "Invalid format1", Toast.LENGTH_SHORT).show();
        }
        /*else if(chkmultipleseparator.indexOf("\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }*/
        else if(chkmultipleseparator.indexOf("\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE0D\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
        else if(chkmultipleseparator.indexOf("\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE1C\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
        else if(chkmultipleseparator.indexOf("\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE36\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
        else if(chkmultipleseparator.indexOf("\uD83D\uDE0D\uD83D\uDE0D")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
        else if(chkmultipleseparator.indexOf("\uD83D\uDE0D\uD83D\uDE1C")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
        else if(chkmultipleseparator.indexOf("\uD83D\uDE0D\uD83D\uDE36")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
        else if(chkmultipleseparator.indexOf("\uD83D\uDE1C\uD83D\uDE0D")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
        else if(chkmultipleseparator.indexOf("\uD83D\uDE1C\uD83D\uDE1C")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
        else if(chkmultipleseparator.indexOf("\uD83D\uDE1C\uD83D\uDE36")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
        else if(chkmultipleseparator.indexOf("\uD83D\uDE36\uD83D\uDE0D")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
        else if(chkmultipleseparator.indexOf("\uD83D\uDE36\uD83D\uDE1C")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
        else if(chkmultipleseparator.indexOf("\uD83D\uDE36\uD83D\uDE36")!=-1){
            Toast.makeText(this, "Invalid format", Toast.LENGTH_SHORT).show();
        }
        else{
            for (String splitword : newconvertsequence.toString().split("\n")) {
                codesequence = (codesequence + new StringBuilder(splitword).toString()
                        .replaceAll("⁞\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE22⁞", "⁞a⁞")
                        .replaceAll("⁞\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07⁞", "⁞b⁞")
                        .replaceAll("⁞\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07⁞", "⁞c⁞")
                        .replaceAll("⁞\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞d⁞")
                        .replaceAll("⁞\uD83D\uDE07\uD83D\uDE07\uD83D\uDE22\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞e⁞")
                        .replaceAll("⁞\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞f⁞")
                        .replaceAll("⁞\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞g⁞")
                        .replaceAll("⁞\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02⁞", "⁞h⁞")
                        .replaceAll("⁞\uD83D\uDE22\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE22\uD83D\uDE07⁞", "⁞i⁞")
                        .replaceAll("⁞\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07⁞", "⁞j⁞")
                        .replaceAll("⁞\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞k⁞")
                        .replaceAll("⁞\uD83D\uDE02\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞l⁞")
                        .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞m⁞")
                        .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02⁞", "⁞n⁞")
                        .replaceAll("⁞\uD83D\uDE22\uD83D\uDE22\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE22\uD83D\uDE07⁞", "⁞o⁞")
                        .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07⁞", "⁞p⁞")
                        .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞q⁞")
                        .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞r⁞")
                        .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02⁞", "⁞s⁞")
                        .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07⁞", "⁞t⁞")
                        .replaceAll("⁞\uD83D\uDE22\uD83D\uDE22\uD83D\uDE22\uD83D\uDE07\uD83D\uDE22\uD83D\uDE07\uD83D\uDE07⁞", "⁞u⁞")
                        .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞v⁞")
                        .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07\uD83D\uDE02⁞", "⁞w⁞")
                        .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE02\uD83D\uDE07⁞", "⁞x⁞")
                        .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE07⁞", "⁞y⁞")
                        .replaceAll("⁞\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE07\uD83D\uDE02⁞", "⁞z⁞")
                        .replaceAll("\uD83D\uDE0D", "")
                        .replaceAll("⁞\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07\uD83D\uDE07⁞", "⁞ ⁞")//separator
                        .replaceAll("\uD83D\uDE36", "⁞\n\n⁞")//to add linebreak
                        .replaceAll("⁞\uD83D\uDE1C⁞", "⁞\n⁞")//to add linebreak
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
