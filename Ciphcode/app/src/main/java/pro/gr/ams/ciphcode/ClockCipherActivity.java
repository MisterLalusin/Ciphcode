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

public class ClockCipherActivity extends AppCompatActivity {

    String codename = "Clock Cipher";
    String codesample = "02:11:14:02:10:00:02:08:15:07:04:17";

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

                        .length() !=0

                        ) {
                    Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                }
                /*else if (input.getText().toString().indexOf("  ")!=-1||input.getText().toString().indexOf("\n\n\n")!=-1) {
                    Toast.makeText(getApplicationContext(), "Too much spaces detected.", Toast.LENGTH_SHORT).show();
                }*/
                else {
                    Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();
                    clockcipherencrypt();
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
                    clockcipherdecrypt();
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

    public void clockcipherencrypt(){
        inputString = input.getText().toString();

        int i = 0;

        String removeextraseperator = null;

        for (String splitline : inputString.split("\n")) {
            String convert = inputString.toLowerCase()
                    .replace("a", "AM⁞")
                    .replace("b", "01⁞")
                    .replace("c", "02⁞")
                    .replace("d", "03⁞")
                    .replace("e", "04⁞")
                    .replace("f", "05⁞")
                    .replace("g", "06⁞")
                    .replace("h", "07⁞")
                    .replace("i", "08⁞")
                    .replace("j", "09⁞")
                    .replace("k", "10⁞")
                    .replace("l", "11⁞")
                    .replace("m", "12⁞")
                    .replace("n", "13⁞")
                    .replace("o", "14⁞")
                    .replace("p", "15⁞")
                    .replace("q", "16⁞")
                    .replace("r", "17⁞")
                    .replace("s", "18⁞")
                    .replace("t", "19⁞")
                    .replace("u", "20⁞")
                    .replace("v", "21⁞")
                    .replace("w", "22⁞")
                    .replace("x", "23⁞")
                    .replace("y", "24⁞")
                    .replace("z", "PM⁞")
                    .replace(" ", "00⁞")
                    ;

            removeextraseperator = convert +"⁞";
        }
        i++;

        outputString = removeextraseperator.replaceAll("⁞⁞", "").replaceAll("⁞", ":").replaceAll(":\n","\n").replaceAll("\n:","\n");

        output.setText(outputString);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void clockcipherdecrypt(){
        inputString = input.getText().toString().toUpperCase();

        int i = 0;

        String addextraseparator = null;

        String addsplit = null;

        CharSequence convertsequence = BuildConfig.FLAVOR;

        for (String splitline : inputString.split("\n")) {
            addextraseparator = ":" + splitline + ":";
            addsplit = addextraseparator.replaceAll(":",":⁞:");
            splitline = addsplit;
            convertsequence = (convertsequence + new StringBuilder(splitline).toString())+"\n";
        }
        i++;

        CharSequence codesequence = BuildConfig.FLAVOR;

        CharSequence chkcodesequence = BuildConfig.FLAVOR;

        for (String splitword : convertsequence.toString().split("⁞")) {
            chkcodesequence = (chkcodesequence + new StringBuilder(splitword).toString()
                    .replace(":AM:", ":")
                    .replace(":1:", ":")
                    .replace(":2:", ":")
                    .replace(":3:", ":")
                    .replace(":4:", ":")
                    .replace(":5:", ":")
                    .replace(":6:", ":")
                    .replace(":7:", ":")
                    .replace(":8:", ":")
                    .replace(":9:", ":")
                    .replace(":01:", ":")
                    .replace(":02:", ":")
                    .replace(":03:", ":")
                    .replace(":04:", ":")
                    .replace(":05:", ":")
                    .replace(":06:", ":")
                    .replace(":07:", ":")
                    .replace(":08:", ":")
                    .replace(":09:", ":")
                    .replace(":10:", ":")
                    .replace(":11:", ":")
                    .replace(":12:", ":")
                    .replace(":13:", ":")
                    .replace(":14:", ":")
                    .replace(":15:", ":")
                    .replace(":16:", ":")
                    .replace(":17:", ":")
                    .replace(":18:", ":")
                    .replace(":19:", ":")
                    .replace(":20:", ":")
                    .replace(":21:", ":")
                    .replace(":22:", ":")
                    .replace(":23:", ":")
                    .replace(":24:", ":")
                    .replace(":PM:", ":")
                    .replace(":00:", ":")
                    .replace(":", "")
                    .replace("\n", "")
            );
        }
        i++;


        if (addextraseparator.indexOf("::")!=-1) {
            Toast.makeText(getApplicationContext(), "Invalid format.", Toast.LENGTH_SHORT).show();
        }
        else if (chkcodesequence.length()!=0) {
            Toast.makeText(getApplicationContext(), "Not a valid code.", Toast.LENGTH_SHORT).show();
        }
        else {
            for (String splitword : convertsequence.toString().split("⁞")) {
                codesequence = (codesequence + new StringBuilder(splitword).toString()
                        .replace(":AM:", ":a:")
                        .replace(":1:", ":b:")
                        .replace(":2:", ":c:")
                        .replace(":3:", ":d:")
                        .replace(":4:", ":e:")
                        .replace(":5:", ":f:")
                        .replace(":6:", ":g:")
                        .replace(":7:", ":h:")
                        .replace(":8:", ":i:")
                        .replace(":9:", ":j:")
                        .replace(":01:", ":b:")
                        .replace(":02:", ":c:")
                        .replace(":03:", ":d:")
                        .replace(":04:", ":e:")
                        .replace(":05:", ":f:")
                        .replace(":06:", ":g:")
                        .replace(":07:", ":h:")
                        .replace(":08:", ":i:")
                        .replace(":09:", ":j:")
                        .replace(":10:", ":k:")
                        .replace(":11:", ":l:")
                        .replace(":12:", ":m:")
                        .replace(":13:", ":n:")
                        .replace(":14:", ":o:")
                        .replace(":15:", ":p:")
                        .replace(":16:", ":q:")
                        .replace(":17:", ":r:")
                        .replace(":18:", ":s:")
                        .replace(":19:", ":t:")
                        .replace(":20:", ":u:")
                        .replace(":21:", ":v:")
                        .replace(":22:", ":w:")
                        .replace(":23:", ":x:")
                        .replace(":24:", ":y:")
                        .replace(":PM:", ":z:")
                        .replace(":00:", ": :")
                        .replace(":", "")
                );
            }
            i++;
            outputString = codesequence.toString();

            output.setText(outputString);
            Toast.makeText(this, "Decrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
