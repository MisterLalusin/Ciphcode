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


public class OctalActivity extends AppCompatActivity {

    String codename = "Octal";
    String codesample = "117 143 164 141 154";

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
                    octalencrypt();
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
                /*//Octal
                else if (input.getText().toString().indexOf("  ")!=-1||input.getText().toString().indexOf(" \n")!=-1) {
                    Toast.makeText(getApplicationContext(), "Too much spaces detected.", Toast.LENGTH_SHORT).show();
                }
                //Octal*/
                /*else if (input.getText().toString().indexOf("  ")!=-1||input.getText().toString().indexOf("\n\n\n")!=-1) {
                    Toast.makeText(getApplicationContext(), "Too much spaces detected.", Toast.LENGTH_SHORT).show();
                }
                /*else if (input.getText().toString().indexOf(" ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Not a valid code.", Toast.LENGTH_SHORT).show();
                }*/
                else {
                    try {
                        octaldecrypt();
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

    public void octalencrypt(){
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
                //.replaceAll("ñ","")
                ;
        //ASCII

        if (chkASCII.length() != 0) {
            Toast.makeText(this, "Use ASCII printable characters only.", Toast.LENGTH_SHORT).show();
        }

        else {

            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < inputString.getBytes().length; i++) {
                sb.append((Integer.toOctalString(inputString.charAt(i))));
                sb.append(" ");
            }

            outputString = sb+"";

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void octaldecrypt() {
        inputString = input.getText().toString().replace("\n"," 12 ");

        String xtrSpce = inputString+" ";
        String chkvalidity = xtrSpce

                .replaceAll("100 ", "")
                .replaceAll("101 ", "")
                .replaceAll("102 ", "")
                .replaceAll("103 ", "")
                .replaceAll("104 ", "")
                .replaceAll("105 ", "")
                .replaceAll("106 ", "")
                .replaceAll("107 ", "")
                .replaceAll("108 ", "")
                .replaceAll("109 ", "")
                .replaceAll("110 ", "")
                .replaceAll("111 ", "")
                .replaceAll("112 ", "")
                .replaceAll("113 ", "")
                .replaceAll("114 ", "")
                .replaceAll("115 ", "")
                .replaceAll("116 ", "")
                .replaceAll("117 ", "")
                .replaceAll("118 ", "")
                .replaceAll("119 ", "")
                .replaceAll("120 ", "")
                .replaceAll("121 ", "")
                .replaceAll("122 ", "")
                .replaceAll("123 ", "")
                .replaceAll("124 ", "")
                .replaceAll("125 ", "")
                .replaceAll("126 ", "")
                .replaceAll("127 ", "")
                .replaceAll("128 ", "")
                .replaceAll("129 ", "")
                .replaceAll("130 ", "")
                .replaceAll("131 ", "")
                .replaceAll("132 ", "")
                .replaceAll("133 ", "")
                .replaceAll("134 ", "")
                .replaceAll("135 ", "")
                .replaceAll("136 ", "")
                .replaceAll("137 ", "")
                .replaceAll("138 ", "")
                .replaceAll("139 ", "")
                .replaceAll("140 ", "")
                .replaceAll("141 ", "")
                .replaceAll("142 ", "")
                .replaceAll("143 ", "")
                .replaceAll("144 ", "")
                .replaceAll("145 ", "")
                .replaceAll("146 ", "")
                .replaceAll("147 ", "")
                .replaceAll("148 ", "")
                .replaceAll("149 ", "")
                .replaceAll("150 ", "")
                .replaceAll("151 ", "")
                .replaceAll("152 ", "")
                .replaceAll("153 ", "")
                .replaceAll("154 ", "")
                .replaceAll("155 ", "")
                .replaceAll("156 ", "")
                .replaceAll("157 ", "")
                .replaceAll("158 ", "")
                .replaceAll("159 ", "")
                .replaceAll("160 ", "")
                .replaceAll("161 ", "")
                .replaceAll("162 ", "")
                .replaceAll("163 ", "")
                .replaceAll("164 ", "")
                .replaceAll("165 ", "")
                .replaceAll("166 ", "")
                .replaceAll("167 ", "")
                .replaceAll("168 ", "")
                .replaceAll("169 ", "")
                .replaceAll("170 ", "")
                .replaceAll("171 ", "")
                .replaceAll("172 ", "")
                .replaceAll("173 ", "")
                .replaceAll("174 ", "")
                .replaceAll("175 ", "")
                .replaceAll("176 ", "")

                .replaceAll("12 ", "")

                .replaceAll("40 ", "")
                .replaceAll("41 ", "")
                .replaceAll("42 ", "")
                .replaceAll("43 ", "")
                .replaceAll("44 ", "")
                .replaceAll("45 ", "")
                .replaceAll("46 ", "")
                .replaceAll("47 ", "")
                .replaceAll("48 ", "")
                .replaceAll("49 ", "")
                .replaceAll("50 ", "")
                .replaceAll("51 ", "")
                .replaceAll("52 ", "")
                .replaceAll("53 ", "")
                .replaceAll("54 ", "")
                .replaceAll("55 ", "")
                .replaceAll("56 ", "")
                .replaceAll("57 ", "")
                .replaceAll("58 ", "")
                .replaceAll("59 ", "")
                .replaceAll("60 ", "")
                .replaceAll("61 ", "")
                .replaceAll("62 ", "")
                .replaceAll("63 ", "")
                .replaceAll("64 ", "")
                .replaceAll("65 ", "")
                .replaceAll("66 ", "")
                .replaceAll("67 ", "")
                .replaceAll("68 ", "")
                .replaceAll("69 ", "")
                .replaceAll("70 ", "")
                .replaceAll("71 ", "")
                .replaceAll("72 ", "")
                .replaceAll("73 ", "")
                .replaceAll("74 ", "")
                .replaceAll("75 ", "")
                .replaceAll("76 ", "")
                .replaceAll("77 ", "")
                .replaceAll("78 ", "")
                .replaceAll("79 ", "")
                .replaceAll("80 ", "")
                .replaceAll("81 ", "")
                .replaceAll("82 ", "")
                .replaceAll("83 ", "")
                .replaceAll("84 ", "")
                .replaceAll("85 ", "")
                .replaceAll("86 ", "")
                .replaceAll("87 ", "")
                .replaceAll("88 ", "")
                .replaceAll("89 ", "")
                .replaceAll("90 ", "")
                .replaceAll("91 ", "")
                .replaceAll("92 ", "")
                .replaceAll("93 ", "")
                .replaceAll("94 ", "")
                .replaceAll("95 ", "")
                .replaceAll("96 ", "")
                .replaceAll("97 ", "")
                .replaceAll("98 ", "")
                .replaceAll("99 ", "")

                .replaceAll("⁞", "")
                .replaceAll(" ", "")
                ;

        if (chkvalidity.length() != 0) {
            Toast.makeText(this, "Not a valid code.", Toast.LENGTH_SHORT).show();
        }
        else {
            String octCon = "";

            String conLF = input.getText().toString().replace("\n"," 12 ");
            String rmvSPC = conLF;

            do {
                rmvSPC = rmvSPC.replace("  "," ");
            }while (rmvSPC.indexOf("  ")>0);

            String[] arrayOct = rmvSPC.split(" ");

            for (String arrayNew : arrayOct) {
                String proOct = (char) Integer.parseInt(arrayNew, 8)+"";
                octCon = octCon + proOct;
            }

            outputString = octCon+"";

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Decrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }
    }
}