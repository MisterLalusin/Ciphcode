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

import java.nio.charset.StandardCharsets;

public class HexadecimalCodeActivity extends AppCompatActivity {

    String codename = "Hexadecimal Code";
    String codesample = "48657861646563696d616c20636f6465";

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
                /*if (input.getText().toString().replaceAll("\n","").replaceAll(" ","").length()==0) {
                    Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                }*/
                if (input.getText().toString().length()==0) {
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
                    hexadecimalcodeencrypt();
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
                /*else if (input.getText().toString().indexOf(" ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Not a valid code.", Toast.LENGTH_SHORT).show();
                }*/
                else {
                    try {
                        hexadecimalcodedecrypt();
                    }
                    catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "Not a valid code.", Toast.LENGTH_SHORT).show();
                    }
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

    public void hexadecimalcodeencrypt(){
        inputString = input.getText().toString();

        //ASCII
        String chkASCII = inputString
                .replaceAll("A","")
                .replaceAll("B","")
                .replaceAll("C","")
                .replaceAll("D","")
                .replaceAll("E","")
                .replaceAll("F","")
                .replaceAll("G","")
                .replaceAll("H","")
                .replaceAll("I","")
                .replaceAll("J","")
                .replaceAll("K","")
                .replaceAll("L","")
                .replaceAll("M","")
                .replaceAll("N","")
                .replaceAll("O","")
                .replaceAll("P","")
                .replaceAll("Q","")
                .replaceAll("R","")
                .replaceAll("S","")
                .replaceAll("T","")
                .replaceAll("U","")
                .replaceAll("V","")
                .replaceAll("W","")
                .replaceAll("X","")
                .replaceAll("Y","")
                .replaceAll("Z","")

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

                .replaceAll("!","")
                .replaceAll("\"","")
                .replaceAll("#","")
                .replaceAll("\\$","")
                .replaceAll("%","")
                .replaceAll("&","")
                .replaceAll("'","")
                .replaceAll("\\(","")
                .replaceAll("\\)","")
                .replaceAll("\\*","")
                .replaceAll("\\+","")
                .replaceAll(",","")
                .replaceAll("-","")
                .replaceAll("\\.","")
                .replaceAll("/","")
                .replaceAll(":","")
                .replaceAll(";","")
                .replaceAll("<","")
                .replaceAll("=","")
                .replaceAll(">","")
                .replaceAll("\\?","")
                .replaceAll("@","")
                .replaceAll("\\[","")
                .replaceAll("\\\\","")
                .replaceAll("\\]","")
                .replaceAll("\\^","")
                .replaceAll("_","")
                .replaceAll("`","")
                .replaceAll("\\{","")
                .replaceAll("\\|","")
                .replaceAll("\\}","")
                .replaceAll("~","")
                .replaceAll(" ","")
                .replaceAll("\n","")

                //additional printable characters
                .replaceAll("ñ","")
                ;
        //ASCII

        if (chkASCII.length() != 0) {
            Toast.makeText(this, "Use ASCII printable characters only.", Toast.LENGTH_SHORT).show();
        }

        else {

            StringBuilder buf = new StringBuilder(200);
            for (char ch : inputString.toCharArray()) {
                if (buf.length() > 0)
                    buf.append(' ');
                buf.append(String.format("%04x", (int) ch));
            }

            String convert = " " + buf.toString();

            outputString = convert.replaceAll(" 00", "");

            StringBuilder sb1 = new StringBuilder(outputString);

            char separator = '⁞';

            for (int i = 0; i < outputString.length() / 2; i++) {
                sb1.insert(((i + 1) * 2) + i, separator);
            }

            output.setText(outputString);
            Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void hexadecimalcodedecrypt() {
        inputString = input.getText().toString().replaceAll("\n","0a").replaceAll(" ","");

        StringBuilder sb1 = new StringBuilder(inputString);

        char separator = '⁞';

        for (int i = 0; i < inputString.length() / 2; i++) {
            sb1.insert(((i + 1) * 2) + i, separator);
        }

        String chkvalidity = sb1.toString().toUpperCase()
                .replaceAll("20", "")
                .replaceAll("21", "")
                .replaceAll("22", "")
                .replaceAll("23", "")
                .replaceAll("24", "")
                .replaceAll("25", "")
                .replaceAll("26", "")
                .replaceAll("27", "")
                .replaceAll("28", "")
                .replaceAll("29", "")
                .replaceAll("2A", "")
                .replaceAll("2B", "")
                .replaceAll("2C", "")
                .replaceAll("2D", "")
                .replaceAll("2E", "")
                .replaceAll("2F", "")
                .replaceAll("30", "")
                .replaceAll("31", "")
                .replaceAll("32", "")
                .replaceAll("33", "")
                .replaceAll("34", "")
                .replaceAll("35", "")
                .replaceAll("36", "")
                .replaceAll("37", "")
                .replaceAll("38", "")
                .replaceAll("39", "")
                .replaceAll("3A", "")
                .replaceAll("3B", "")
                .replaceAll("3C", "")
                .replaceAll("3D", "")
                .replaceAll("3E", "")
                .replaceAll("3F", "")
                .replaceAll("40", "")
                .replaceAll("41", "")
                .replaceAll("42", "")
                .replaceAll("43", "")
                .replaceAll("44", "")
                .replaceAll("45", "")
                .replaceAll("46", "")
                .replaceAll("47", "")
                .replaceAll("48", "")
                .replaceAll("49", "")
                .replaceAll("4A", "")
                .replaceAll("4B", "")
                .replaceAll("4C", "")
                .replaceAll("4D", "")
                .replaceAll("4E", "")
                .replaceAll("4F", "")
                .replaceAll("50", "")
                .replaceAll("51", "")
                .replaceAll("52", "")
                .replaceAll("53", "")
                .replaceAll("54", "")
                .replaceAll("55", "")
                .replaceAll("56", "")
                .replaceAll("57", "")
                .replaceAll("58", "")
                .replaceAll("59", "")
                .replaceAll("5A", "")
                .replaceAll("5B", "")
                .replaceAll("5C", "")
                .replaceAll("5D", "")
                .replaceAll("5E", "")
                .replaceAll("5F", "")
                .replaceAll("60", "")
                .replaceAll("61", "")
                .replaceAll("62", "")
                .replaceAll("63", "")
                .replaceAll("64", "")
                .replaceAll("65", "")
                .replaceAll("66", "")
                .replaceAll("67", "")
                .replaceAll("68", "")
                .replaceAll("69", "")
                .replaceAll("6A", "")
                .replaceAll("6B", "")
                .replaceAll("6C", "")
                .replaceAll("6D", "")
                .replaceAll("6E", "")
                .replaceAll("6F", "")
                .replaceAll("70", "")
                .replaceAll("71", "")
                .replaceAll("72", "")
                .replaceAll("73", "")
                .replaceAll("74", "")
                .replaceAll("75", "")
                .replaceAll("76", "")
                .replaceAll("77", "")
                .replaceAll("78", "")
                .replaceAll("79", "")
                .replaceAll("7A", "")
                .replaceAll("7B", "")
                .replaceAll("7C", "")
                .replaceAll("7D", "")
                .replaceAll("7E", "")
                .replaceAll("0A", "")

                .replaceAll("⁞", "")
                .replaceAll(" ", "")
        ;

        if (chkvalidity.length() != 0) {
            Toast.makeText(this, "Not a valid code.", Toast.LENGTH_SHORT).show();
        }
        else {

            String b = inputString;
            byte[] bytes = hexStringToByteArray(b);
            String st = new String(bytes, StandardCharsets.UTF_8);

            outputString = st;

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Decrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }
    }
    public static byte[] hexStringToByteArray(String hex) {
        int l = hex.length();
        byte[] data = new byte[l/2];
        for (int i = 0; i < l; i += 2) {
            data[i/2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }
}