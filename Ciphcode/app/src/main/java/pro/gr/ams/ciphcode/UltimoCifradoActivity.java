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

public class UltimoCifradoActivity extends AppCompatActivity {

    String codename = "Ultimo-Cifrado";
    String codesample = "OUUVWLMNTUAIXYMEIOHPNDFCUAIJKFHJRAEAGHDEIO";

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
                String invalid = "false";
                char[] charArray = input.getText().toString().replace(" ", "exemption").replace(".", "exemption").replace("?", "exemption").replace("!", "exemption").replace("-", "exemption").replace(",", "exemption").replace("\n", "exemption").toCharArray();
                for (char c:charArray) {
                    if(!Character.isLetterOrDigit(c)&&!Character.isSpaceChar(c)) {
                        invalid = "true";
                    }
                }
                /*if (input.getText().toString().replaceAll("\n","").replaceAll(" ","").length()==0) {
                    Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                }*/
                if (input.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                }
                else if (invalid.equals("true")) {
                    Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                }
                else if (invalid.equals("false")) {
                    if (input.getText().toString().indexOf("⁞")!=-1) {
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
                        ultimocifradoencrypt();
                    }
                }
            }
        });
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String chkthis = input.getText().toString().replaceAll("\n","");

                StringBuilder checkcode = new StringBuilder(chkthis);
                char separator = '-';

                for (int chk=0; chk < chkthis.length() / 3; chk++) {
                    checkcode.insert(((chk+1)*3)+chk, separator);
                }

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
                else if (
                        checkcode.toString()

                                .replace("AEA","")
                                .replace("IOE","")
                                .replace("UAI","")
                                .replace("EIO","")
                                .replace("OUU","")

                                .replace("BCB","")
                                .replace("DFC","")
                                .replace("GHD","")
                                .replace("JKF","")
                                .replace("LMG","")
                                .replace("NPH","")
                                .replace("QRJ","")
                                .replace("STK","")
                                .replace("VWL","")
                                .replace("XYM","")
                                .replace("ZBN","")
                                .replace("CDP","")
                                .replace("FGQ","")
                                .replace("HJR","")
                                .replace("KLS","")
                                .replace("MNT","")
                                .replace("PQV","")
                                .replace("RSW","")
                                .replace("TVX","")
                                .replace("WXY","")
                                .replace("YZZ","")

                                .replace("LNB","")

                                .replace("SAE","")

                                .replace("PRD","")
                                .replace("QNM","")
                                .replace("ENP","")

                                .replace("HPN","")
                                .replace("CMA","")

                                .replace("010","")
                                .replace("231","")
                                .replace("452","")
                                .replace("673","")
                                .replace("894","")
                                .replace("015","")
                                .replace("236","")
                                .replace("457","")
                                .replace("678","")
                                .replace("899","")

                                .replace("-","")

                                .length()!=0
                ) {
                    Toast.makeText(UltimoCifradoActivity.this, "Not a valid code.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Decrypted succesfully.", Toast.LENGTH_SHORT).show();
                    ultimocifradodecrypt();
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

    public void ultimocifradoencrypt(){
        String vowel001="AEA";
        String vowel002="IOE";
        String vowel003="UAI";
        String vowel004="EIO";
        String vowel005="OUU";

        String consonant001="BCB";
        String consonant002="DFC";
        String consonant003="GHD";
        String consonant004="JKF";
        String consonant005="LMG";
        String consonant006="NPH";
        String consonant007="QRJ";
        String consonant008="STK";
        String consonant009="VWL";
        String consonant010="XYM";
        String consonant011="ZBN";
        String consonant012="CDP";
        String consonant013="FGQ";
        String consonant014="HJR";
        String consonant015="KLS";
        String consonant016="MNT";
        String consonant017="PQV";
        String consonant018="RSW";
        String consonant019="TVX";
        String consonant020="WXY";
        String consonant021="YZZ";

        String linebreak001="LNB";

        String space001="SAE";

        String symbol001="PRD";
        String symbol002="QNM";
        String symbol003="ENP";

        String symbol004="HPN";
        String symbol005="CMA";

        String number001="010";
        String number002="231";
        String number003="452";
        String number004="673";
        String number005="894";
        String number006="015";
        String number007="236";
        String number008="457";
        String number009="678";
        String number010="899";

        inputString = input.getText().toString();
        String replace = inputString.toLowerCase()

                .replaceAll("a", vowel001)
                .replaceAll("e", vowel002)
                .replaceAll("i", vowel003)
                .replaceAll("o", vowel004)
                .replaceAll("u", vowel005)

                .replaceAll("b", consonant001)
                .replaceAll("c", consonant002)
                .replaceAll("d", consonant003)
                .replaceAll("f", consonant004)
                .replaceAll("g", consonant005)
                .replaceAll("h", consonant006)
                .replaceAll("j", consonant007)
                .replaceAll("k", consonant008)
                .replaceAll("l", consonant009)
                .replaceAll("m", consonant010)
                .replaceAll("n", consonant011)
                .replaceAll("p", consonant012)
                .replaceAll("q", consonant013)
                .replaceAll("r", consonant014)
                .replaceAll("s", consonant015)
                .replaceAll("t", consonant016)
                .replaceAll("v", consonant017)
                .replaceAll("w", consonant018)
                .replaceAll("x", consonant019)
                .replaceAll("y", consonant020)
                .replaceAll("z", consonant021)

                .replaceAll("\n", linebreak001)

                .replaceAll("\\s", space001)

                .replaceAll("\\.", symbol001)
                .replaceAll("\\?", symbol002)
                .replaceAll("\\!", symbol003)

                .replaceAll("\\-", symbol004)
                .replaceAll("\\,", symbol005)

                .replaceAll("0", "#zero")
                .replaceAll("1", "#one")
                .replaceAll("2", "#two")
                .replaceAll("3", "#three")
                .replaceAll("4", "#four")
                .replaceAll("5", "#five")
                .replaceAll("6", "#six")
                .replaceAll("7", "#seven")
                .replaceAll("8", "#eight")
                .replaceAll("9", "#nine")

                .replaceAll("#zero", number001)
                .replaceAll("#one", number002)
                .replaceAll("#two", number003)
                .replaceAll("#three", number004)
                .replaceAll("#four", number005)
                .replaceAll("#five", number006)
                .replaceAll("#six", number007)
                .replaceAll("#seven", number008)
                .replaceAll("#eight", number009)
                .replaceAll("#nine", number010);

        char[] allowed = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ \n".toCharArray();
        char[] charArray = replace.toCharArray();
        StringBuilder result = new StringBuilder();
        for (char c : charArray) {
            for (char a : allowed) {
                if (c==a)result.append(a);
            }
        }

        outputString = result.toString();

        output.setText(outputString.toUpperCase());

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    public void ultimocifradodecrypt(){
        String vowel001="A";
        String vowel002="E";
        String vowel003="I";
        String vowel004="O";
        String vowel005="U";

        String consonant001="B";
        String consonant002="C";
        String consonant003="D";
        String consonant004="F";
        String consonant005="G";
        String consonant006="H";
        String consonant007="J";
        String consonant008="K";
        String consonant009="L";
        String consonant010="M";
        String consonant011="N";
        String consonant012="P";
        String consonant013="Q";
        String consonant014="R";
        String consonant015="S";
        String consonant016="T";
        String consonant017="V";
        String consonant018="W";
        String consonant019="X";
        String consonant020="Y";
        String consonant021="Z";

        String linebreak001="\n";

        String space001=" ";

        String symbol001=".";
        String symbol002="?";
        String symbol003="!";

        String symbol004="-";
        String symbol005=",";

        String number001="0";
        String number002="1";
        String number003="2";
        String number004="3";
        String number005="4";
        String number006="5";
        String number007="6";
        String number008="7";
        String number009="8";
        String number010="9";

        String override001="SA";

        inputString = input.getText().toString().replaceAll("\n","LNB");
        outputString = inputString.toLowerCase()

                .replaceAll("lnb", linebreak001)

                .replaceAll("sae", space001)

                .replaceAll("aea",vowel001)
                .replaceAll("ioe",vowel002)
                .replaceAll("uai",vowel003)
                .replaceAll("eio",vowel004)
                .replaceAll("ouu",vowel005)

                .replaceAll("bcb",consonant001)
                .replaceAll("dfc",consonant002)
                .replaceAll("ghd",consonant003)
                .replaceAll("jkf",consonant004)
                .replaceAll("lmg",consonant005)
                .replaceAll("nph",consonant006)
                .replaceAll("qrj",consonant007)
                .replaceAll("stk",consonant008)
                .replaceAll("vwl",consonant009)
                .replaceAll("xym",consonant010)
                .replaceAll("zbn",consonant011)
                .replaceAll("cdp",consonant012)
                .replaceAll("fgq",consonant013)
                .replaceAll("hjr",consonant014)
                .replaceAll("kls",consonant015)
                .replaceAll("mnt",consonant016)
                .replaceAll("pqv",consonant017)
                .replaceAll("rsw",consonant018)
                .replaceAll("tvx",consonant019)
                .replaceAll("wxy",consonant020)
                .replaceAll("yzz",consonant021)

                .replaceAll("prd", symbol001)
                .replaceAll("qnm", symbol002)
                .replaceAll("enp", symbol003)

                .replaceAll("hpn", symbol004)
                .replaceAll("cma", symbol005)

                .replaceAll("010","#zero")
                .replaceAll("231","#one")
                .replaceAll("452","#two")
                .replaceAll("673","#three")
                .replaceAll("894","#four")
                .replaceAll("015","#five")
                .replaceAll("236","#six")
                .replaceAll("457","#seven")
                .replaceAll("678","#eight")
                .replaceAll("899","#nine")

                .replaceAll("#zero",number001)
                .replaceAll("#one",number002)
                .replaceAll("#two",number003)
                .replaceAll("#three",number004)
                .replaceAll("#four",number005)
                .replaceAll("#five",number006)
                .replaceAll("#six",number007)
                .replaceAll("#seven",number008)
                .replaceAll("#eight",number009)
                .replaceAll("#nine",number010)

                .replaceAll("kl a",override001);

        output.setText(outputString.toUpperCase());

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
