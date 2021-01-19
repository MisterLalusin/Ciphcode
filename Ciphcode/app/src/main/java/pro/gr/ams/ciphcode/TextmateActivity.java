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

public class TextmateActivity extends AppCompatActivity {

    String codename = "Textmate";
    String codesample = "09201824820 09130201696";

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
                char[] charArray = input.getText().toString().replace(".","exemption").replace("?","exemption").replace("!","exemption").replace("\n","exemption").toCharArray();
                for (char c:charArray) {
                    if(!Character.isLetterOrDigit(c)&&!Character.isSpaceChar(c)) {
                        invalid = "true";
                    }
                }
                if (invalid.equals("true")) {
                    Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                }
                else if (invalid.equals("false")) {
                    if (input.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                    }
                    else if (input.getText().toString().equals(" ")) {
                        Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                    }
                    else if (input.getText().toString().equals("  ")) {
                        Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                    }
                    else if (input.getText().toString().equals("\n\n")||input.getText().toString().equals("\n")) {
                        Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                    }
                    else if (input.getText().toString().equals(".")||input.getText().toString().equals("?")||input.getText().toString().equals("!")) {
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
                    else if (input.getText().toString().indexOf("  ")!=-1) {
                        Toast.makeText(getApplicationContext(), "Multiple spaces not supported.", Toast.LENGTH_SHORT).show();
                    }
                    else if (input.getText().toString().indexOf("\n\n")!=-1) {
                        Toast.makeText(getApplicationContext(), "Paragraphs are not supported.", Toast.LENGTH_SHORT).show();
                    }
                    else if (

                            input.getText().toString().indexOf("  ")!=-1 ||

                                    input.getText().toString().indexOf(" .")!=-1 ||
                                    input.getText().toString().indexOf(" ?")!=-1 ||
                                    input.getText().toString().indexOf(" !")!=-1 ||
                                    input.getText().toString().indexOf(" \n")!=-1 ||

                                    input.getText().toString().indexOf("..")!=-1 ||
                                    input.getText().toString().indexOf(".?")!=-1 ||
                                    input.getText().toString().indexOf(".!")!=-1 ||
                                    input.getText().toString().indexOf(".\n")!=-1 ||

                                    input.getText().toString().indexOf("?.")!=-1 ||
                                    input.getText().toString().indexOf("??")!=-1 ||
                                    input.getText().toString().indexOf("?!")!=-1 ||
                                    input.getText().toString().indexOf("?\n")!=-1 ||

                                    input.getText().toString().indexOf("!.")!=-1 ||
                                    input.getText().toString().indexOf("!?")!=-1 ||
                                    input.getText().toString().indexOf("!!")!=-1 ||
                                    input.getText().toString().indexOf("!\n")!=-1 ||

                                    input.getText().toString().indexOf("\n.")!=-1 ||
                                    input.getText().toString().indexOf("\n?")!=-1 ||
                                    input.getText().toString().indexOf("\n!")!=-1 ||
                                    input.getText().toString().indexOf("\n\n\n")!=-1

                            ) {
                        Toast.makeText(TextmateActivity.this, "Invalid format", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();
                        textmateencrypt();
                    }
                }
            }
        });
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String chkthis = input.getText().toString().replaceAll("\n09"," 09666696696 09").replaceFirst("09","").replaceAll(" 09","").replaceAll("\n","");

                int i = 0;

                CharSequence removedoublespacing = BuildConfig.FLAVOR;

                for (String splitline : chkthis.split("\n")) {
                    splitline = chkthis.replaceAll(" ","");
                    removedoublespacing = (removedoublespacing + new StringBuilder(splitline).toString());
                }
                i++;

                String chkthisrmv = removedoublespacing.toString();

                StringBuilder checkcode = new StringBuilder(chkthisrmv);
                char separator = '-';

                for (int chk=0; chk < chkthisrmv.length() / 3; chk++) {
                    checkcode.insert(((chk+1)*3)+chk, separator);
                }

                if (input.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                }
                else if (input.getText().toString().equals(" ")) {
                    Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                }
                else if (input.getText().toString().equals("  ")) {
                    Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                }
                else if (input.getText().toString().equals("\n\n")||input.getText().toString().equals("\n")) {
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
                else if (input.getText().toString().replaceAll(" ","").replaceAll("\n","").length()%11!=0) {
                    Toast.makeText(getApplicationContext(), "Not a valid code.", Toast.LENGTH_SHORT).show();
                }
                else if (checkcode.toString()

                        .replaceAll("701","")
                        .replaceAll("802","")
                        .replaceAll("803","")
                        .replaceAll("804","")
                        .replaceAll("705","")
                        .replaceAll("806","")
                        .replaceAll("807","")
                        .replaceAll("808","")
                        .replaceAll("709","")
                        .replaceAll("810","")
                        .replaceAll("811","")
                        .replaceAll("812","")
                        .replaceAll("813","")
                        .replaceAll("814","")
                        .replaceAll("715","")
                        .replaceAll("816","")
                        .replaceAll("817","")
                        .replaceAll("818","")
                        .replaceAll("819","")
                        .replaceAll("820","")
                        .replaceAll("721","")
                        .replaceAll("822","")
                        .replaceAll("823","")
                        .replaceAll("824","")
                        .replaceAll("825","")
                        .replaceAll("826","")

                        .replaceAll("010","")
                        .replaceAll("011","")
                        .replaceAll("012","")
                        .replaceAll("013","")
                        .replaceAll("014","")

                        .replaceAll("020","")
                        .replaceAll("021","")
                        .replaceAll("022","")
                        .replaceAll("023","")
                        .replaceAll("024","")

                        .replaceAll("030","")
                        .replaceAll("031","")
                        .replaceAll("032","")
                        .replaceAll("033","")
                        .replaceAll("034","")

                        .replaceAll("040","")
                        .replaceAll("041","")
                        .replaceAll("042","")
                        .replaceAll("043","")
                        .replaceAll("044","")

                        .replaceAll("050","")
                        .replaceAll("051","")
                        .replaceAll("052","")
                        .replaceAll("053","")
                        .replaceAll("054","")

                        .replaceAll("060","")
                        .replaceAll("061","")
                        .replaceAll("062","")
                        .replaceAll("063","")
                        .replaceAll("064","")

                        .replaceAll("070","")
                        .replaceAll("071","")
                        .replaceAll("072","")
                        .replaceAll("073","")
                        .replaceAll("074","")

                        .replaceAll("080","")
                        .replaceAll("081","")
                        .replaceAll("082","")
                        .replaceAll("083","")
                        .replaceAll("084","")

                        .replaceAll("090","")
                        .replaceAll("091","")
                        .replaceAll("092","")
                        .replaceAll("093","")
                        .replaceAll("094","")

                        .replaceAll("100","")
                        .replaceAll("101","")
                        .replaceAll("102","")
                        .replaceAll("103","")
                        .replaceAll("104","")

                        .replaceAll("110","")
                        .replaceAll("111","")
                        .replaceAll("112","")
                        .replaceAll("113","")
                        .replaceAll("114","")

                        .replaceAll("120","")
                        .replaceAll("121","")
                        .replaceAll("122","")
                        .replaceAll("123","")
                        .replaceAll("124","")

                        .replaceAll("130","")
                        .replaceAll("131","")
                        .replaceAll("132","")
                        .replaceAll("133","")
                        .replaceAll("134","")

                        .replaceAll("140","")
                        .replaceAll("141","")
                        .replaceAll("142","")
                        .replaceAll("143","")
                        .replaceAll("144","")

                        .replaceAll("150","")
                        .replaceAll("151","")
                        .replaceAll("152","")
                        .replaceAll("153","")
                        .replaceAll("154","")

                        .replaceAll("160","")
                        .replaceAll("161","")
                        .replaceAll("162","")
                        .replaceAll("163","")
                        .replaceAll("164","")

                        .replaceAll("170","")
                        .replaceAll("171","")
                        .replaceAll("172","")
                        .replaceAll("173","")
                        .replaceAll("174","")

                        .replaceAll("180","")
                        .replaceAll("181","")
                        .replaceAll("182","")
                        .replaceAll("183","")
                        .replaceAll("184","")

                        .replaceAll("190","")
                        .replaceAll("191","")
                        .replaceAll("192","")
                        .replaceAll("193","")
                        .replaceAll("194","")

                        .replaceAll("200","")
                        .replaceAll("201","")
                        .replaceAll("202","")
                        .replaceAll("203","")
                        .replaceAll("204","")

                        .replaceAll("210","")
                        .replaceAll("211","")
                        .replaceAll("212","")
                        .replaceAll("213","")
                        .replaceAll("214","")

                        .replaceAll("220","")
                        .replaceAll("221","")
                        .replaceAll("222","")
                        .replaceAll("223","")
                        .replaceAll("224","")

                        .replaceAll("230","")
                        .replaceAll("231","")
                        .replaceAll("232","")
                        .replaceAll("233","")
                        .replaceAll("234","")

                        .replaceAll("240","")
                        .replaceAll("241","")
                        .replaceAll("242","")
                        .replaceAll("243","")
                        .replaceAll("244","")

                        .replaceAll("250","")
                        .replaceAll("251","")
                        .replaceAll("252","")
                        .replaceAll("253","")
                        .replaceAll("254","")

                        .replaceAll("260","")
                        .replaceAll("261","")
                        .replaceAll("262","")
                        .replaceAll("263","")
                        .replaceAll("264","")

                        .replaceAll("015","")
                        .replaceAll("016","")
                        .replaceAll("017","")
                        .replaceAll("018","")
                        .replaceAll("019","")

                        .replaceAll("025","")
                        .replaceAll("026","")
                        .replaceAll("027","")
                        .replaceAll("028","")
                        .replaceAll("029","")

                        .replaceAll("035","")
                        .replaceAll("036","")
                        .replaceAll("037","")
                        .replaceAll("038","")
                        .replaceAll("039","")

                        .replaceAll("045","")
                        .replaceAll("046","")
                        .replaceAll("047","")
                        .replaceAll("048","")
                        .replaceAll("049","")

                        .replaceAll("055","")
                        .replaceAll("056","")
                        .replaceAll("057","")
                        .replaceAll("058","")
                        .replaceAll("059","")

                        .replaceAll("065","")
                        .replaceAll("066","")
                        .replaceAll("067","")
                        .replaceAll("068","")
                        .replaceAll("069","")

                        .replaceAll("075","")
                        .replaceAll("076","")
                        .replaceAll("077","")
                        .replaceAll("078","")
                        .replaceAll("079","")

                        .replaceAll("085","")
                        .replaceAll("086","")
                        .replaceAll("087","")
                        .replaceAll("088","")
                        .replaceAll("089","")

                        .replaceAll("095","")
                        .replaceAll("096","")
                        .replaceAll("097","")
                        .replaceAll("098","")
                        .replaceAll("099","")

                        .replaceAll("105","")
                        .replaceAll("106","")
                        .replaceAll("107","")
                        .replaceAll("108","")
                        .replaceAll("109","")

                        .replaceAll("115","")
                        .replaceAll("116","")
                        .replaceAll("117","")
                        .replaceAll("118","")
                        .replaceAll("119","")

                        .replaceAll("125","")
                        .replaceAll("126","")
                        .replaceAll("127","")
                        .replaceAll("128","")
                        .replaceAll("129","")

                        .replaceAll("135","")
                        .replaceAll("136","")
                        .replaceAll("137","")
                        .replaceAll("138","")
                        .replaceAll("139","")

                        .replaceAll("145","")
                        .replaceAll("146","")
                        .replaceAll("147","")
                        .replaceAll("148","")
                        .replaceAll("149","")

                        .replaceAll("155","")
                        .replaceAll("156","")
                        .replaceAll("157","")
                        .replaceAll("158","")
                        .replaceAll("159","")

                        .replaceAll("165","")
                        .replaceAll("166","")
                        .replaceAll("167","")
                        .replaceAll("168","")
                        .replaceAll("169","")

                        .replaceAll("175","")
                        .replaceAll("176","")
                        .replaceAll("177","")
                        .replaceAll("178","")
                        .replaceAll("179","")

                        .replaceAll("185","")
                        .replaceAll("186","")
                        .replaceAll("187","")
                        .replaceAll("188","")
                        .replaceAll("189","")

                        .replaceAll("195","")
                        .replaceAll("196","")
                        .replaceAll("197","")
                        .replaceAll("198","")
                        .replaceAll("199","")

                        .replaceAll("205","")
                        .replaceAll("206","")
                        .replaceAll("207","")
                        .replaceAll("208","")
                        .replaceAll("209","")

                        .replaceAll("215","")
                        .replaceAll("216","")
                        .replaceAll("217","")
                        .replaceAll("218","")
                        .replaceAll("219","")

                        .replaceAll("225","")
                        .replaceAll("226","")
                        .replaceAll("227","")
                        .replaceAll("228","")
                        .replaceAll("229","")

                        .replaceAll("235","")
                        .replaceAll("236","")
                        .replaceAll("237","")
                        .replaceAll("238","")
                        .replaceAll("239","")

                        .replaceAll("245","")
                        .replaceAll("246","")
                        .replaceAll("247","")
                        .replaceAll("248","")
                        .replaceAll("249","")

                        .replaceAll("255","")
                        .replaceAll("256","")
                        .replaceAll("257","")
                        .replaceAll("258","")
                        .replaceAll("259","")

                        .replaceAll("265","")
                        .replaceAll("266","")
                        .replaceAll("267","")
                        .replaceAll("268","")
                        .replaceAll("269","")

                        .replaceAll("500","")
                        .replaceAll("501","")
                        .replaceAll("502","")
                        .replaceAll("503","")
                        .replaceAll("504","")
                        .replaceAll("505","")
                        .replaceAll("506","")
                        .replaceAll("507","")
                        .replaceAll("508","")
                        .replaceAll("509","")

                        .replaceAll("510","")
                        .replaceAll("511","")
                        .replaceAll("512","")
                        .replaceAll("513","")
                        .replaceAll("514","")
                        .replaceAll("515","")
                        .replaceAll("516","")
                        .replaceAll("517","")
                        .replaceAll("518","")
                        .replaceAll("519","")

                        .replaceAll("520","")
                        .replaceAll("521","")
                        .replaceAll("522","")
                        .replaceAll("523","")
                        .replaceAll("524","")
                        .replaceAll("525","")
                        .replaceAll("526","")
                        .replaceAll("527","")
                        .replaceAll("528","")
                        .replaceAll("529","")

                        .replaceAll("530","")
                        .replaceAll("531","")
                        .replaceAll("532","")
                        .replaceAll("533","")
                        .replaceAll("534","")
                        .replaceAll("535","")
                        .replaceAll("536","")
                        .replaceAll("537","")
                        .replaceAll("538","")
                        .replaceAll("539","")

                        .replaceAll("540","")
                        .replaceAll("541","")
                        .replaceAll("542","")
                        .replaceAll("543","")
                        .replaceAll("544","")
                        .replaceAll("545","")
                        .replaceAll("546","")
                        .replaceAll("547","")
                        .replaceAll("548","")
                        .replaceAll("549","")

                        .replaceAll("550","")
                        .replaceAll("551","")
                        .replaceAll("552","")
                        .replaceAll("553","")
                        .replaceAll("554","")
                        .replaceAll("555","")
                        .replaceAll("556","")
                        .replaceAll("557","")
                        .replaceAll("558","")
                        .replaceAll("559","")

                        .replaceAll("560","")
                        .replaceAll("561","")
                        .replaceAll("562","")
                        .replaceAll("563","")
                        .replaceAll("564","")
                        .replaceAll("565","")
                        .replaceAll("566","")
                        .replaceAll("567","")
                        .replaceAll("568","")
                        .replaceAll("569","")

                        .replaceAll("570","")
                        .replaceAll("571","")
                        .replaceAll("572","")
                        .replaceAll("573","")
                        .replaceAll("574","")
                        .replaceAll("575","")
                        .replaceAll("576","")
                        .replaceAll("577","")
                        .replaceAll("578","")
                        .replaceAll("579","")

                        .replaceAll("580","")
                        .replaceAll("581","")
                        .replaceAll("582","")
                        .replaceAll("583","")
                        .replaceAll("584","")
                        .replaceAll("585","")
                        .replaceAll("586","")
                        .replaceAll("587","")
                        .replaceAll("588","")
                        .replaceAll("589","")

                        .replaceAll("590","")
                        .replaceAll("591","")
                        .replaceAll("592","")
                        .replaceAll("593","")
                        .replaceAll("594","")
                        .replaceAll("595","")
                        .replaceAll("596","")
                        .replaceAll("597","")
                        .replaceAll("598","")
                        .replaceAll("599","")

                        .replaceAll("696","")
                        .replaceAll("666","")

                        .replaceAll("-","")
                        .replaceAll(" ","")

                        .length()!=0
                        )
                {
                    Toast.makeText(getApplicationContext(), "Not a valid code.", Toast.LENGTH_SHORT).show();
                }
                /*else if (input.getText().toString().indexOf("  ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                }*/
                else if (input.getText().toString().indexOf("666666")!=-1||input.getText().toString().indexOf("696666")!=-1) {
                    Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Decrypted succesfully.", Toast.LENGTH_SHORT).show();
                    textmatedecrypt();
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

    public void textmateencrypt(){
        inputString = input.getText().toString();

        char[] allowed = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.?\n ".toCharArray();
        char[] charArray = inputString.toCharArray();
        StringBuilder result = new StringBuilder();
        for (char c : charArray) {
            for (char a : allowed) {
                if (c==a)result.append(a);
            }
        }

        StringBuilder sb1 = new StringBuilder(result);

        char separator = '-';

        String separatenumbers = sb1.toString();
        Pattern digitafterdigit = Pattern.compile("[0-9]"+"[0-9]");
        Matcher digitmatcher = digitafterdigit.matcher(separatenumbers);
        while (digitmatcher.find()) {
            separatenumbers = separatenumbers.toString().replace(digitmatcher.group(),digitmatcher.group()+"|");
        }


        String number = separatenumbers.toString()

                .replace("90","⁞nineZE")
                .replace("91","⁞nineON")
                .replace("92","⁞nineTW")
                .replace("93","⁞nineTH")
                .replace("94","⁞nineFO")
                .replace("95","⁞nineFI")
                .replace("96","⁞nineSI")
                .replace("97","⁞nineSE")
                .replace("98","⁞nineEI")
                .replace("99","⁞nineNI")

                .replace("80","⁞eightZE")
                .replace("81","⁞eightON")
                .replace("82","⁞eightTW")
                .replace("83","⁞eightTH")
                .replace("84","⁞eightFO")
                .replace("85","⁞eightFI")
                .replace("86","⁞eightSI")
                .replace("87","⁞eightSE")
                .replace("88","⁞eightEI")
                .replace("89","⁞eightNI")

                .replace("70","⁞sevenZE")
                .replace("71","⁞sevenON")
                .replace("72","⁞sevenTW")
                .replace("73","⁞sevenTH")
                .replace("74","⁞sevenFO")
                .replace("75","⁞sevenFI")
                .replace("76","⁞sevenSI")
                .replace("77","⁞sevenSE")
                .replace("78","⁞sevenEI")
                .replace("79","⁞sevenNI")

                .replace("60","⁞sixZE")
                .replace("61","⁞sixON")
                .replace("62","⁞sixTW")
                .replace("63","⁞sixTH")
                .replace("64","⁞sixFO")
                .replace("65","⁞sixFI")
                .replace("66","⁞sixSI")
                .replace("67","⁞sixSE")
                .replace("68","⁞sixEI")
                .replace("69","⁞sixNI")

                .replace("50","⁞fiveZE")
                .replace("51","⁞fiveON")
                .replace("52","⁞fiveTW")
                .replace("53","⁞fiveTH")
                .replace("54","⁞fiveFO")
                .replace("55","⁞fiveFI")
                .replace("56","⁞fiveSI")
                .replace("57","⁞fiveSE")
                .replace("58","⁞fiveEI")
                .replace("59","⁞fiveNI")

                .replace("40","⁞fourZE")
                .replace("41","⁞fourON")
                .replace("42","⁞fourTW")
                .replace("43","⁞fourTH")
                .replace("44","⁞fourFO")
                .replace("45","⁞fourFI")
                .replace("46","⁞fourSI")
                .replace("47","⁞fourSE")
                .replace("48","⁞fourEI")
                .replace("49","⁞fourNI")

                .replace("30","⁞threeZE")
                .replace("31","⁞threeON")
                .replace("32","⁞threeTW")
                .replace("33","⁞threeTH")
                .replace("34","⁞threeFO")
                .replace("35","⁞threeFI")
                .replace("36","⁞threeSI")
                .replace("37","⁞threeSE")
                .replace("38","⁞threeEI")
                .replace("39","⁞threeNI")

                .replace("20","⁞twoZE")
                .replace("21","⁞twoON")
                .replace("22","⁞twoTW")
                .replace("23","⁞twoTH")
                .replace("24","⁞twoFO")
                .replace("25","⁞twoFI")
                .replace("26","⁞twoSI")
                .replace("27","⁞twoSE")
                .replace("28","⁞twoEI")
                .replace("29","⁞twoNI")

                .replace("10","⁞oneZE")
                .replace("11","⁞oneON")
                .replace("12","⁞oneTW")
                .replace("13","⁞oneTH")
                .replace("14","⁞oneFO")
                .replace("15","⁞oneFI")
                .replace("16","⁞oneSI")
                .replace("17","⁞oneSE")
                .replace("18","⁞oneEI")
                .replace("19","⁞oneNI")

                .replace("00","⁞zeroZE")
                .replace("01","⁞zeroON")
                .replace("02","⁞zeroTW")
                .replace("03","⁞zeroTH")
                .replace("04","⁞zeroFO")
                .replace("05","⁞zeroFI")
                .replace("06","⁞zeroSI")
                .replace("07","⁞zeroSE")
                .replace("08","⁞zeroEI")
                .replace("09","⁞zeroNI")

                .replace("0","⁞blankZE")
                .replace("1","⁞blankON")
                .replace("2","⁞blankTW")
                .replace("3","⁞blankTH")
                .replace("4","⁞blankFO")
                .replace("5","⁞blankFI")
                .replace("6","⁞blankSI")
                .replace("7","⁞blankSE")
                .replace("8","⁞blankEI")
                .replace("9","⁞blankNI")

                .replace("⁞nineZE","5"+"90")
                .replace("⁞nineON","5"+"91")
                .replace("⁞nineTW","5"+"92")
                .replace("⁞nineTH","5"+"93")
                .replace("⁞nineFO","5"+"94")
                .replace("⁞nineFI","5"+"95")
                .replace("⁞nineSI","5"+"96")
                .replace("⁞nineSE","5"+"97")
                .replace("⁞nineEI","5"+"98")
                .replace("⁞nineNI","5"+"99")

                .replace("⁞eightZE","5"+"80")
                .replace("⁞eightON","5"+"81")
                .replace("⁞eightTW","5"+"82")
                .replace("⁞eightTH","5"+"83")
                .replace("⁞eightFO","5"+"84")
                .replace("⁞eightFI","5"+"85")
                .replace("⁞eightSI","5"+"86")
                .replace("⁞eightSE","5"+"87")
                .replace("⁞eightEI","5"+"88")
                .replace("⁞eightNI","5"+"89")

                .replace("⁞sevenZE","5"+"70")
                .replace("⁞sevenON","5"+"71")
                .replace("⁞sevenTW","5"+"72")
                .replace("⁞sevenTH","5"+"73")
                .replace("⁞sevenFO","5"+"74")
                .replace("⁞sevenFI","5"+"75")
                .replace("⁞sevenSI","5"+"76")
                .replace("⁞sevenSE","5"+"77")
                .replace("⁞sevenEI","5"+"78")
                .replace("⁞sevenNI","5"+"79")

                .replace("⁞sixZE","5"+"60")
                .replace("⁞sixON","5"+"61")
                .replace("⁞sixTW","5"+"62")
                .replace("⁞sixTH","5"+"63")
                .replace("⁞sixFO","5"+"64")
                .replace("⁞sixFI","5"+"65")
                .replace("⁞sixSI","5"+"66")
                .replace("⁞sixSE","5"+"67")
                .replace("⁞sixEI","5"+"68")
                .replace("⁞sixNI","5"+"69")

                .replace("⁞fiveZE","5"+"50")
                .replace("⁞fiveON","5"+"51")
                .replace("⁞fiveTW","5"+"52")
                .replace("⁞fiveTH","5"+"53")
                .replace("⁞fiveFO","5"+"54")
                .replace("⁞fiveFI","5"+"55")
                .replace("⁞fiveSI","5"+"56")
                .replace("⁞fiveSE","5"+"57")
                .replace("⁞fiveEI","5"+"58")
                .replace("⁞fiveNI","5"+"59")

                .replace("⁞fourZE","5"+"40")
                .replace("⁞fourON","5"+"41")
                .replace("⁞fourTW","5"+"42")
                .replace("⁞fourTH","5"+"43")
                .replace("⁞fourFO","5"+"44")
                .replace("⁞fourFI","5"+"45")
                .replace("⁞fourSI","5"+"46")
                .replace("⁞fourSE","5"+"47")
                .replace("⁞fourEI","5"+"48")
                .replace("⁞fourNI","5"+"49")

                .replace("⁞threeZE","5"+"30")
                .replace("⁞threeON","5"+"31")
                .replace("⁞threeTW","5"+"32")
                .replace("⁞threeTH","5"+"33")
                .replace("⁞threeFO","5"+"34")
                .replace("⁞threeFI","5"+"35")
                .replace("⁞threeSI","5"+"36")
                .replace("⁞threeSE","5"+"37")
                .replace("⁞threeEI","5"+"38")
                .replace("⁞threeNI","5"+"39")

                .replace("⁞twoZE","5"+"20")
                .replace("⁞twoON","5"+"21")
                .replace("⁞twoTW","5"+"22")
                .replace("⁞twoTH","5"+"23")
                .replace("⁞twoFO","5"+"24")
                .replace("⁞twoFI","5"+"25")
                .replace("⁞twoSI","5"+"26")
                .replace("⁞twoSE","5"+"27")
                .replace("⁞twoEI","5"+"28")
                .replace("⁞twoNI","5"+"29")

                .replace("⁞oneZE","5"+"10")
                .replace("⁞oneON","5"+"11")
                .replace("⁞oneTW","5"+"12")
                .replace("⁞oneTH","5"+"13")
                .replace("⁞oneFO","5"+"14")
                .replace("⁞oneFI","5"+"15")
                .replace("⁞oneSI","5"+"16")
                .replace("⁞oneSE","5"+"17")
                .replace("⁞oneEI","5"+"18")
                .replace("⁞oneNI","5"+"19")

                .replace("⁞zeroZE","5"+"00"+"5"+"00")
                .replace("⁞zeroON","5"+"00"+"5"+"01")
                .replace("⁞zeroTW","5"+"00"+"5"+"02")
                .replace("⁞zeroTH","5"+"00"+"5"+"03")
                .replace("⁞zeroFO","5"+"00"+"5"+"04")
                .replace("⁞zeroFI","5"+"00"+"5"+"05")
                .replace("⁞zeroSI","5"+"00"+"5"+"06")
                .replace("⁞zeroSE","5"+"00"+"5"+"07")
                .replace("⁞zeroEI","5"+"00"+"5"+"08")
                .replace("⁞zeroNI","5"+"00"+"5"+"09")

                .replace("⁞blankZE","5"+"00")
                .replace("⁞blankON","5"+"01")
                .replace("⁞blankTW","5"+"02")
                .replace("⁞blankTH","5"+"03")
                .replace("⁞blankFO","5"+"04")
                .replace("⁞blankFI","5"+"05")
                .replace("⁞blankSI","5"+"06")
                .replace("⁞blankSE","5"+"07")
                .replace("⁞blankEI","5"+"08")
                .replace("⁞blankNI","5"+"09")

                ;

        String a = "01";
        String b = "02";
        String c = "03";
        String d = "04";
        String e = "05";
        String f = "06";
        String g = "07";
        String h = "08";
        String i = "09";
        String j = "10";
        String k = "11";
        String l = "12";
        String m = "13";
        String n = "14";
        String o = "15";
        String p = "16";
        String q = "17";
        String r = "18";
        String s = "19";
        String t = "20";
        String u = "21";
        String v = "22";
        String w = "23";
        String x = "24";
        String y = "25";
        String z = "26";

        String A = "a";
        String E = "e";
        String I = "i";
        String O = "o";
        String U = "u";

        String sentence = number.replace(".","666").replace("?","666").replace("!","666").replace("\n","666");

        String space = sentence.replace(" ","696");

        String separatedoublevowels = space.toString().toLowerCase();;
        Pattern doublevowelsA = Pattern.compile("[a-z]"+"aa");
        Pattern doublevowelsE = Pattern.compile("[a-z]"+"ee");
        Pattern doublevowelsI = Pattern.compile("[a-z]"+"ii");
        Pattern doublevowelsO = Pattern.compile("[a-z]"+"oo");
        Pattern doublevowelsU = Pattern.compile("[a-z]"+"uu");
        Matcher doublevowelsAmatcher = doublevowelsA.matcher(separatedoublevowels);
        Matcher doublevowelsEmatcher = doublevowelsE.matcher(separatedoublevowels);
        Matcher doublevowelsImatcher = doublevowelsI.matcher(separatedoublevowels);
        Matcher doublevowelsOmatcher = doublevowelsO.matcher(separatedoublevowels);
        Matcher doublevowelsUmatcher = doublevowelsU.matcher(separatedoublevowels);

        while (doublevowelsAmatcher.find()||doublevowelsEmatcher.find()||doublevowelsImatcher.find()||doublevowelsOmatcher.find()||doublevowelsUmatcher.find()) {
            if (doublevowelsAmatcher.find()) {
                separatedoublevowels = separatedoublevowels.toString().replace(doublevowelsAmatcher.group(), "|" + doublevowelsAmatcher.group() + "|");
            }
            if (doublevowelsEmatcher.find()) {
                separatedoublevowels = separatedoublevowels.toString().replace(doublevowelsEmatcher.group(), "|" + doublevowelsEmatcher.group() + "|");
            }
            if (doublevowelsImatcher.find()) {
                separatedoublevowels = separatedoublevowels.toString().replace(doublevowelsImatcher.group(), "|" + doublevowelsImatcher.group() + "|");
            }
            if (doublevowelsOmatcher.find()) {
                separatedoublevowels = separatedoublevowels.toString().replace(doublevowelsOmatcher.group(), "|" + doublevowelsOmatcher.group() + "|");
            }
            if (doublevowelsUmatcher.find()) {
                separatedoublevowels = separatedoublevowels.toString().replace(doublevowelsUmatcher.group(), "|" + doublevowelsUmatcher.group() + "|");
            }
        }

        String convertdoublevowels = separatedoublevowels

                .replace("a"+A+A, a+"5")
                .replace("a"+E+E, a+"6")
                .replace("a"+I+I, a+"7")
                .replace("a"+O+O, a+"8")
                .replace("a"+U+U, a+"9")

                .replace("b"+A+A, b+"5")
                .replace("b"+E+E, b+"6")
                .replace("b"+I+I, b+"7")
                .replace("b"+O+O, b+"8")
                .replace("b"+U+U, b+"9")

                .replace("c"+A+A, c+"5")
                .replace("c"+E+E, c+"6")
                .replace("c"+I+I, c+"7")
                .replace("c"+O+O, c+"8")
                .replace("c"+U+U, c+"9")

                .replace("d"+A+A, d+"5")
                .replace("d"+E+E, d+"6")
                .replace("d"+I+I, d+"7")
                .replace("d"+O+O, d+"8")
                .replace("d"+U+U, d+"9")

                .replace("e"+A+A, e+"5")
                .replace("e"+E+E, e+"6")
                .replace("e"+I+I, e+"7")
                .replace("e"+O+O, e+"8")
                .replace("e"+U+U, e+"9")

                .replace("f"+A+A, f+"5")
                .replace("f"+E+E, f+"6")
                .replace("f"+I+I, f+"7")
                .replace("f"+O+O, f+"8")
                .replace("f"+U+U, f+"9")

                .replace("g"+A+A, g+"5")
                .replace("g"+E+E, g+"6")
                .replace("g"+I+I, g+"7")
                .replace("g"+O+O, g+"8")
                .replace("g"+U+U, g+"9")

                .replace("h"+A+A, h+"5")
                .replace("h"+E+E, h+"6")
                .replace("h"+I+I, h+"7")
                .replace("h"+O+O, h+"8")
                .replace("h"+U+U, h+"9")

                .replace("i"+A+A, i+"5")
                .replace("i"+E+E, i+"6")
                .replace("i"+I+I, i+"7")
                .replace("i"+O+O, i+"8")
                .replace("i"+U+U, i+"9")

                .replace("j"+A+A, j+"5")
                .replace("j"+E+E, j+"6")
                .replace("j"+I+I, j+"7")
                .replace("j"+O+O, j+"8")
                .replace("j"+U+U, j+"9")

                .replace("k"+A+A, k+"5")
                .replace("k"+E+E, k+"6")
                .replace("k"+I+I, k+"7")
                .replace("k"+O+O, k+"8")
                .replace("k"+U+U, k+"9")

                .replace("l"+A+A, l+"5")
                .replace("l"+E+E, l+"6")
                .replace("l"+I+I, l+"7")
                .replace("l"+O+O, l+"8")
                .replace("l"+U+U, l+"9")

                .replace("m"+A+A, m+"5")
                .replace("m"+E+E, m+"6")
                .replace("m"+I+I, m+"7")
                .replace("m"+O+O, m+"8")
                .replace("m"+U+U, m+"9")

                .replace("n"+A+A, n+"5")
                .replace("n"+E+E, n+"6")
                .replace("n"+I+I, n+"7")
                .replace("n"+O+O, n+"8")
                .replace("n"+U+U, n+"9")

                .replace("o"+A+A, o+"5")
                .replace("o"+E+E, o+"6")
                .replace("o"+I+I, o+"7")
                .replace("o"+O+O, o+"8")
                .replace("o"+U+U, o+"9")

                .replace("p"+A+A, p+"5")
                .replace("p"+E+E, p+"6")
                .replace("p"+I+I, p+"7")
                .replace("p"+O+O, p+"8")
                .replace("p"+U+U, p+"9")

                .replace("q"+A+A, q+"5")
                .replace("q"+E+E, q+"6")
                .replace("q"+I+I, q+"7")
                .replace("q"+O+O, q+"8")
                .replace("q"+U+U, q+"9")

                .replace("r"+A+A, r+"5")
                .replace("r"+E+E, r+"6")
                .replace("r"+I+I, r+"7")
                .replace("r"+O+O, r+"8")
                .replace("r"+U+U, r+"9")

                .replace("s"+A+A, s+"5")
                .replace("s"+E+E, s+"6")
                .replace("s"+I+I, s+"7")
                .replace("s"+O+O, s+"8")
                .replace("s"+U+U, s+"9")

                .replace("t"+A+A, t+"5")
                .replace("t"+E+E, t+"6")
                .replace("t"+I+I, t+"7")
                .replace("t"+O+O, t+"8")
                .replace("t"+U+U, t+"9")

                .replace("u"+A+A, u+"5")
                .replace("u"+E+E, u+"6")
                .replace("u"+I+I, u+"7")
                .replace("u"+O+O, u+"8")
                .replace("u"+U+U, u+"9")

                .replace("v"+A+A, v+"5")
                .replace("v"+E+E, v+"6")
                .replace("v"+I+I, v+"7")
                .replace("v"+O+O, v+"8")
                .replace("v"+U+U, v+"9")

                .replace("w"+A+A, w+"5")
                .replace("w"+E+E, w+"6")
                .replace("w"+I+I, w+"7")
                .replace("w"+O+O, w+"8")
                .replace("w"+U+U, w+"9")

                .replace("x"+A+A, x+"5")
                .replace("x"+E+E, x+"6")
                .replace("x"+I+I, x+"7")
                .replace("x"+O+O, x+"8")
                .replace("x"+U+U, x+"9")

                .replace("y"+A+A, y+"5")
                .replace("y"+E+E, y+"6")
                .replace("y"+I+I, y+"7")
                .replace("y"+O+O, y+"8")
                .replace("y"+U+U, y+"9")

                .replace("z"+A+A, z+"5")
                .replace("z"+E+E, z+"6")
                .replace("z"+I+I, z+"7")
                .replace("z"+O+O, z+"8")
                .replace("z"+U+U, z+"9")

                ;

        String singlevowel = convertdoublevowels

                .replace("a"+A, a+"0")
                .replace("a"+E, a+"1")
                .replace("a"+I, a+"2")
                .replace("a"+I+O, a+"3")
                .replace("a"+U, a+"4")

                .replace("b"+A, b+"0")
                .replace("b"+E, b+"1")
                .replace("b"+I, b+"2")
                .replace("b"+O, b+"3")
                .replace("b"+U, b+"4")

                .replace("c"+A, c+"0")
                .replace("c"+E, c+"1")
                .replace("c"+I, c+"2")
                .replace("c"+O, c+"3")
                .replace("c"+U, c+"4")

                .replace("d"+A, d+"0")
                .replace("d"+E, d+"1")
                .replace("d"+I, d+"2")
                .replace("d"+O, d+"3")
                .replace("d"+U, d+"4")

                .replace("e"+A, e+"0")
                .replace("e"+E, e+"1")
                .replace("e"+I, e+"2")
                .replace("e"+O, e+"3")
                .replace("e"+U, e+"4")

                .replace("f"+A, f+"0")
                .replace("f"+E, f+"1")
                .replace("f"+I, f+"2")
                .replace("f"+O, f+"3")
                .replace("f"+U, f+"4")

                .replace("g"+A, g+"0")
                .replace("g"+E, g+"1")
                .replace("g"+I, g+"2")
                .replace("g"+O, g+"3")
                .replace("g"+U, g+"4")

                .replace("h"+A, h+"0")
                .replace("h"+E, h+"1")
                .replace("h"+I, h+"2")
                .replace("h"+O, h+"3")
                .replace("h"+U, h+"4")

                .replace("i"+A, i+"0")
                .replace("i"+E, i+"1")
                .replace("i"+I, i+"2")
                .replace("i"+O, i+"3")
                .replace("i"+U, i+"4")

                .replace("j"+A, j+"0")
                .replace("j"+E, j+"1")
                .replace("j"+I, j+"2")
                .replace("j"+O, j+"3")
                .replace("j"+U, j+"4")

                .replace("k"+A, k+"0")
                .replace("k"+E, k+"1")
                .replace("k"+I, k+"2")
                .replace("k"+O, k+"3")
                .replace("k"+U, k+"4")

                .replace("l"+A, l+"0")
                .replace("l"+E, l+"1")
                .replace("l"+I, l+"2")
                .replace("l"+O, l+"3")
                .replace("l"+U, l+"4")

                .replace("m"+A, m+"0")
                .replace("m"+E, m+"1")
                .replace("m"+I, m+"2")
                .replace("m"+O, m+"3")
                .replace("m"+U, m+"4")

                .replace("n"+A, n+"0")
                .replace("n"+E, n+"1")
                .replace("n"+I, n+"2")
                .replace("n"+O, n+"3")
                .replace("n"+U, n+"4")

                .replace("o"+A, o+"0")
                .replace("o"+E, o+"1")
                .replace("o"+I, o+"2")
                .replace("o"+O, o+"3")
                .replace("o"+U, o+"4")

                .replace("p"+A, p+"0")
                .replace("p"+E, p+"1")
                .replace("p"+I, p+"2")
                .replace("p"+O, p+"3")
                .replace("p"+U, p+"4")

                .replace("q"+A, q+"0")
                .replace("q"+E, q+"1")
                .replace("q"+I, q+"2")
                .replace("q"+O, q+"3")
                .replace("q"+U, q+"4")

                .replace("r"+A, r+"0")
                .replace("r"+E, r+"1")
                .replace("r"+I, r+"2")
                .replace("r"+O, r+"3")
                .replace("r"+U, r+"4")

                .replace("s"+A, s+"0")
                .replace("s"+E, s+"1")
                .replace("s"+I, s+"2")
                .replace("s"+O, s+"3")
                .replace("s"+U, s+"4")

                .replace("t"+A, t+"0")
                .replace("t"+E, t+"1")
                .replace("t"+I, t+"2")
                .replace("t"+O, t+"3")
                .replace("t"+U, t+"4")

                .replace("u"+A, u+"0")
                .replace("u"+E, u+"1")
                .replace("u"+I, u+"2")
                .replace("u"+O, u+"3")
                .replace("u"+U, u+"4")

                .replace("v"+A, v+"0")
                .replace("v"+E, v+"1")
                .replace("v"+I, v+"2")
                .replace("v"+O, v+"3")
                .replace("v"+U, v+"4")

                .replace("w"+A, w+"0")
                .replace("w"+E, w+"1")
                .replace("w"+I, w+"2")
                .replace("w"+O, w+"3")
                .replace("w"+U, w+"4")

                .replace("x"+A, x+"0")
                .replace("x"+E, x+"1")
                .replace("x"+I, x+"2")
                .replace("x"+O, x+"3")
                .replace("x"+U, x+"4")

                .replace("y"+A, y+"0")
                .replace("y"+E, y+"1")
                .replace("y"+I, y+"2")
                .replace("y"+O, y+"3")
                .replace("y"+U, y+"4")

                .replace("z"+A, z+"0")
                .replace("z"+E, z+"1")
                .replace("z"+I, z+"2")
                .replace("z"+O, z+"3")
                .replace("z"+U, z+"4")

                ;

        String singlecharactervowel = singlevowel

                .replace("|","")

                .replace("a", "7"+a)
                .replace("e", "7"+e)
                .replace("i", "7"+i)
                .replace("o", "7"+o)
                .replace("u", "7"+u)

                ;

        String singleconsonant = singlecharactervowel

                .replace("b", "8"+b)
                .replace("c", "8"+c)
                .replace("d", "8"+d)
                .replace("f", "8"+f)
                .replace("g", "8"+g)
                .replace("h", "8"+h)
                .replace("j", "8"+j)
                .replace("k", "8"+k)
                .replace("l", "8"+l)
                .replace("m", "8"+m)
                .replace("n", "8"+n)
                .replace("p", "8"+p)
                .replace("q", "8"+q)
                .replace("r", "8"+r)
                .replace("s", "8"+s)
                .replace("t", "8"+t)
                .replace("v", "8"+v)
                .replace("w", "8"+w)
                .replace("x", "8"+x)
                .replace("y", "8"+y)
                .replace("z", "8"+z)

                ;

        String addone = singleconsonant+"696";
        String addtwo = singleconsonant+"696696";
        String dividenine = singleconsonant;

        if (singleconsonant.length() % 9 == 0) {

            dividenine = singleconsonant;

        }
        else if (addone.length() % 9 != 0) {

            dividenine = singleconsonant.replace("-","")+"696696";

        }
        else if (addtwo.length() % 9 != 0) {

            dividenine = singleconsonant.replace("-","")+"696";

        }

        StringBuilder converttophonenumber = new StringBuilder(dividenine);

        for (int m1=0; m1 < dividenine.length() / 9; m1++) {
            converttophonenumber.insert(((m1+1)*9)+m1, "⁞");
        }

        String symbol = converttophonenumber.toString();
        String add09start = "09"+symbol+"rmv";
        String remove = add09start.replace("09rmv", "").replace("⁞rmv", "").replace("⁞"," 09").replace("09696696696","");
        String addtoend = remove+"⁞spaces";
        String endspaces = addtoend.replaceAll(" ⁞spaces","").replaceAll("⁞spaces","");
        String outputString = endspaces;

        output.setText(outputString);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }

    public void textmatedecrypt(){
        inputString = input.getText().toString().replaceAll("\n09","09666696696 09").replaceAll("\n","09666696696 ");
        String removespace = inputString.replaceAll(" ", "");;

        StringBuilder sb1 = new StringBuilder(removespace);

        char separator = '-';

        for (int i=0; i < removespace.length() / 11; i++) {
            sb1.insert(((i+1)*11)+i, separator);
        }

        String addhypentostart1 = "-"+sb1;

        String remove09 = addhypentostart1.replaceAll("-09","").replaceAll("-","");

        StringBuilder sb2 = new StringBuilder(remove09);

        for (int i=0; i < remove09.length() / 3; i++) {
            sb2.insert(((i+1)*3)+i, separator);
        }

        String addhypentostart2 = "-"+sb2;

        String a = "01";
        String b = "02";
        String c = "03";
        String d = "04";
        String e = "05";
        String f = "06";
        String g = "07";
        String h = "08";
        String i = "09";
        String j = "10";
        String k = "11";
        String l = "12";
        String m = "13";
        String n = "14";
        String o = "15";
        String p = "16";
        String q = "17";
        String r = "18";
        String s = "19";
        String t = "20";
        String u = "21";
        String v = "22";
        String w = "23";
        String x = "24";
        String y = "25";
        String z = "26";

        String A = "a";
        String E = "e";
        String I = "i";
        String O = "o";
        String U = "u";

        String convert = addhypentostart2

                .replaceAll("-500", "-"+"0")
                .replaceAll("-501", "-"+"1")
                .replaceAll("-502", "-"+"2")
                .replaceAll("-503", "-"+"3")
                .replaceAll("-504", "-"+"4")
                .replaceAll("-505", "-"+"5")
                .replaceAll("-506", "-"+"6")
                .replaceAll("-507", "-"+"7")
                .replaceAll("-508", "-"+"8")
                .replaceAll("-509", "-"+"9")

                .replaceAll("-510", "-"+"10")
                .replaceAll("-511", "-"+"11")
                .replaceAll("-512", "-"+"12")
                .replaceAll("-513", "-"+"13")
                .replaceAll("-514", "-"+"14")
                .replaceAll("-515", "-"+"15")
                .replaceAll("-516", "-"+"16")
                .replaceAll("-517", "-"+"17")
                .replaceAll("-518", "-"+"18")
                .replaceAll("-519", "-"+"19")

                .replaceAll("-520", "-"+"20")
                .replaceAll("-521", "-"+"21")
                .replaceAll("-522", "-"+"22")
                .replaceAll("-523", "-"+"23")
                .replaceAll("-524", "-"+"24")
                .replaceAll("-525", "-"+"25")
                .replaceAll("-526", "-"+"26")
                .replaceAll("-527", "-"+"27")
                .replaceAll("-528", "-"+"28")
                .replaceAll("-529", "-"+"29")

                .replaceAll("-530", "-"+"30")
                .replaceAll("-531", "-"+"31")
                .replaceAll("-532", "-"+"32")
                .replaceAll("-533", "-"+"33")
                .replaceAll("-534", "-"+"34")
                .replaceAll("-535", "-"+"35")
                .replaceAll("-536", "-"+"36")
                .replaceAll("-537", "-"+"37")
                .replaceAll("-538", "-"+"38")
                .replaceAll("-539", "-"+"39")

                .replaceAll("-540", "-"+"40")
                .replaceAll("-541", "-"+"41")
                .replaceAll("-542", "-"+"42")
                .replaceAll("-543", "-"+"43")
                .replaceAll("-544", "-"+"44")
                .replaceAll("-545", "-"+"45")
                .replaceAll("-546", "-"+"46")
                .replaceAll("-547", "-"+"47")
                .replaceAll("-548", "-"+"48")
                .replaceAll("-549", "-"+"49")

                .replaceAll("-550", "-"+"50")
                .replaceAll("-551", "-"+"51")
                .replaceAll("-552", "-"+"52")
                .replaceAll("-553", "-"+"53")
                .replaceAll("-554", "-"+"54")
                .replaceAll("-555", "-"+"55")
                .replaceAll("-556", "-"+"56")
                .replaceAll("-557", "-"+"57")
                .replaceAll("-558", "-"+"58")
                .replaceAll("-559", "-"+"59")

                .replaceAll("-560", "-"+"60")
                .replaceAll("-561", "-"+"61")
                .replaceAll("-562", "-"+"62")
                .replaceAll("-563", "-"+"63")
                .replaceAll("-564", "-"+"64")
                .replaceAll("-565", "-"+"65")
                .replaceAll("-566", "-"+"66")
                .replaceAll("-567", "-"+"67")
                .replaceAll("-568", "-"+"68")
                .replaceAll("-569", "-"+"69")

                .replaceAll("-570", "-"+"70")
                .replaceAll("-571", "-"+"71")
                .replaceAll("-572", "-"+"72")
                .replaceAll("-573", "-"+"73")
                .replaceAll("-574", "-"+"74")
                .replaceAll("-575", "-"+"75")
                .replaceAll("-576", "-"+"76")
                .replaceAll("-577", "-"+"77")
                .replaceAll("-578", "-"+"78")
                .replaceAll("-579", "-"+"79")

                .replaceAll("-580", "-"+"80")
                .replaceAll("-581", "-"+"81")
                .replaceAll("-582", "-"+"82")
                .replaceAll("-583", "-"+"83")
                .replaceAll("-584", "-"+"84")
                .replaceAll("-585", "-"+"85")
                .replaceAll("-586", "-"+"86")
                .replaceAll("-587", "-"+"87")
                .replaceAll("-588", "-"+"88")
                .replaceAll("-589", "-"+"89")

                .replaceAll("-590", "-"+"90")
                .replaceAll("-591", "-"+"91")
                .replaceAll("-592", "-"+"92")
                .replaceAll("-593", "-"+"93")
                .replaceAll("-594", "-"+"94")
                .replaceAll("-595", "-"+"95")
                .replaceAll("-596", "-"+"96")
                .replaceAll("-597", "-"+"97")
                .replaceAll("-598", "-"+"98")
                .replaceAll("-599", "-"+"99")

                .replaceAll("-666-696", "-"+"\n")
                .replaceAll("-696-666", "-"+"\n")
                .replaceAll("-696", "-"+" ")
                .replaceAll("-666", "-"+"\n")

                .replaceAll("-7"+a, "-"+"a")
                .replaceAll("-7"+e, "-"+"e")
                .replaceAll("-7"+i, "-"+"i")
                .replaceAll("-7"+o, "-"+"o")
                .replaceAll("-7"+u, "-"+"u")

                .replaceAll("-8"+b, "-"+"b")
                .replaceAll("-8"+c, "-"+"c")
                .replaceAll("-8"+d, "-"+"d")
                .replaceAll("-8"+f, "-"+"f")
                .replaceAll("-8"+g, "-"+"g")
                .replaceAll("-8"+h, "-"+"h")
                .replaceAll("-8"+j, "-"+"j")
                .replaceAll("-8"+k, "-"+"k")
                .replaceAll("-8"+l, "-"+"l")
                .replaceAll("-8"+m, "-"+"m")
                .replaceAll("-8"+n, "-"+"n")
                .replaceAll("-8"+p, "-"+"p")
                .replaceAll("-8"+q, "-"+"q")
                .replaceAll("-8"+r, "-"+"r")
                .replaceAll("-8"+s, "-"+"s")
                .replaceAll("-8"+t, "-"+"t")
                .replaceAll("-8"+v, "-"+"v")
                .replaceAll("-8"+w, "-"+"w")
                .replaceAll("-8"+x, "-"+"x")
                .replaceAll("-8"+y, "-"+"y")
                .replaceAll("-8"+z, "-"+"z")

                .replaceAll(a+"0", "-"+"a"+A)
                .replaceAll(a+"1", "-"+"a"+E)
                .replaceAll(a+"2", "-"+"a"+I)
                .replaceAll(a+"3", "-"+"a"+O)
                .replaceAll(a+"4", "-"+"a"+U)
                .replaceAll(a+"5", "-"+"a"+A+A)
                .replaceAll(a+"6", "-"+"a"+E+E)
                .replaceAll(a+"7", "-"+"a"+I+I)
                .replaceAll(a+"8", "-"+"a"+O+O)
                .replaceAll(a+"9", "-"+"a"+U+U)

                .replaceAll(b+"0", "-"+"b"+A)
                .replaceAll(b+"1", "-"+"b"+E)
                .replaceAll(b+"2", "-"+"b"+I)
                .replaceAll(b+"3", "-"+"b"+O)
                .replaceAll(b+"4", "-"+"b"+U)
                .replaceAll(b+"5", "-"+"b"+A+A)
                .replaceAll(b+"6", "-"+"b"+E+E)
                .replaceAll(b+"7", "-"+"b"+I+I)
                .replaceAll(b+"8", "-"+"b"+O+O)
                .replaceAll(b+"9", "-"+"b"+U+U)

                .replaceAll(c+"0", "-"+"c"+A)
                .replaceAll(c+"1", "-"+"c"+E)
                .replaceAll(c+"2", "-"+"c"+I)
                .replaceAll(c+"3", "-"+"c"+O)
                .replaceAll(c+"4", "-"+"c"+U)
                .replaceAll(c+"5", "-"+"c"+A+A)
                .replaceAll(c+"6", "-"+"c"+E+E)
                .replaceAll(c+"7", "-"+"c"+I+I)
                .replaceAll(c+"8", "-"+"c"+O+O)
                .replaceAll(c+"9", "-"+"c"+U+U)

                .replaceAll(d+"0", "-"+"d"+A)
                .replaceAll(d+"1", "-"+"d"+E)
                .replaceAll(d+"2", "-"+"d"+I)
                .replaceAll(d+"3", "-"+"d"+O)
                .replaceAll(d+"4", "-"+"d"+U)
                .replaceAll(d+"5", "-"+"d"+A+A)
                .replaceAll(d+"6", "-"+"d"+E+E)
                .replaceAll(d+"7", "-"+"d"+I+I)
                .replaceAll(d+"8", "-"+"d"+O+O)
                .replaceAll(d+"9", "-"+"d"+U+U)

                .replaceAll(e+"0", "-"+"e"+A)
                .replaceAll(e+"1", "-"+"e"+E)
                .replaceAll(e+"2", "-"+"e"+I)
                .replaceAll(e+"3", "-"+"e"+O)
                .replaceAll(e+"4", "-"+"e"+U)
                .replaceAll(e+"5", "-"+"e"+A+A)
                .replaceAll(e+"6", "-"+"e"+E+E)
                .replaceAll(e+"7", "-"+"e"+I+I)
                .replaceAll(e+"8", "-"+"e"+O+O)
                .replaceAll(e+"9", "-"+"e"+U+U)

                .replaceAll(f+"0", "-"+"f"+A)
                .replaceAll(f+"1", "-"+"f"+E)
                .replaceAll(f+"2", "-"+"f"+I)
                .replaceAll(f+"3", "-"+"f"+O)
                .replaceAll(f+"4", "-"+"f"+U)
                .replaceAll(f+"5", "-"+"f"+A+A)
                .replaceAll(f+"6", "-"+"f"+E+E)
                .replaceAll(f+"7", "-"+"f"+I+I)
                .replaceAll(f+"8", "-"+"f"+O+O)
                .replaceAll(f+"9", "-"+"f"+U+U)

                .replaceAll(g+"0", "-"+"g"+A)
                .replaceAll(g+"1", "-"+"g"+E)
                .replaceAll(g+"2", "-"+"g"+I)
                .replaceAll(g+"3", "-"+"g"+O)
                .replaceAll(g+"4", "-"+"g"+U)
                .replaceAll(g+"5", "-"+"g"+A+A)
                .replaceAll(g+"6", "-"+"g"+E+E)
                .replaceAll(g+"7", "-"+"g"+I+I)
                .replaceAll(g+"8", "-"+"g"+O+O)
                .replaceAll(g+"9", "-"+"g"+U+U)

                .replaceAll(h+"0", "-"+"h"+A)
                .replaceAll(h+"1", "-"+"h"+E)
                .replaceAll(h+"2", "-"+"h"+I)
                .replaceAll(h+"3", "-"+"h"+O)
                .replaceAll(h+"4", "-"+"h"+U)
                .replaceAll(h+"5", "-"+"h"+A+A)
                .replaceAll(h+"6", "-"+"h"+E+E)
                .replaceAll(h+"7", "-"+"h"+I+I)
                .replaceAll(h+"8", "-"+"h"+O+O)
                .replaceAll(h+"9", "-"+"h"+U+U)

                .replaceAll(i+"0", "-"+"i"+A)
                .replaceAll(i+"1", "-"+"i"+E)
                .replaceAll(i+"2", "-"+"i"+I)
                .replaceAll(i+"3", "-"+"i"+O)
                .replaceAll(i+"4", "-"+"i"+U)
                .replaceAll(i+"5", "-"+"i"+A+A)
                .replaceAll(i+"6", "-"+"i"+E+E)
                .replaceAll(i+"7", "-"+"i"+I+I)
                .replaceAll(i+"8", "-"+"i"+O+O)
                .replaceAll(i+"9", "-"+"i"+U+U)

                .replaceAll(j+"0", "-"+"j"+A)
                .replaceAll(j+"1", "-"+"j"+E)
                .replaceAll(j+"2", "-"+"j"+I)
                .replaceAll(j+"3", "-"+"j"+O)
                .replaceAll(j+"4", "-"+"j"+U)
                .replaceAll(j+"5", "-"+"j"+A+A)
                .replaceAll(j+"6", "-"+"j"+E+E)
                .replaceAll(j+"7", "-"+"j"+I+I)
                .replaceAll(j+"8", "-"+"j"+O+O)
                .replaceAll(j+"9", "-"+"j"+U+U)

                .replaceAll(k+"0", "-"+"k"+A)
                .replaceAll(k+"1", "-"+"k"+E)
                .replaceAll(k+"2", "-"+"k"+I)
                .replaceAll(k+"3", "-"+"k"+O)
                .replaceAll(k+"4", "-"+"k"+U)
                .replaceAll(k+"5", "-"+"k"+A+A)
                .replaceAll(k+"6", "-"+"k"+E+E)
                .replaceAll(k+"7", "-"+"k"+I+I)
                .replaceAll(k+"8", "-"+"k"+O+O)
                .replaceAll(k+"9", "-"+"k"+U+U)

                .replaceAll(l+"0", "-"+"l"+A)
                .replaceAll(l+"1", "-"+"l"+E)
                .replaceAll(l+"2", "-"+"l"+I)
                .replaceAll(l+"3", "-"+"l"+O)
                .replaceAll(l+"4", "-"+"l"+U)
                .replaceAll(l+"5", "-"+"l"+A+A)
                .replaceAll(l+"6", "-"+"l"+E+E)
                .replaceAll(l+"7", "-"+"l"+I+I)
                .replaceAll(l+"8", "-"+"l"+O+O)
                .replaceAll(l+"9", "-"+"l"+U+U)

                .replaceAll(m+"0", "-"+"m"+A)
                .replaceAll(m+"1", "-"+"m"+E)
                .replaceAll(m+"2", "-"+"m"+I)
                .replaceAll(m+"3", "-"+"m"+O)
                .replaceAll(m+"4", "-"+"m"+U)
                .replaceAll(m+"5", "-"+"m"+A+A)
                .replaceAll(m+"6", "-"+"m"+E+E)
                .replaceAll(m+"7", "-"+"m"+I+I)
                .replaceAll(m+"8", "-"+"m"+O+O)
                .replaceAll(m+"9", "-"+"m"+U+U)

                .replaceAll(n+"0", "-"+"n"+A)
                .replaceAll(n+"1", "-"+"n"+E)
                .replaceAll(n+"2", "-"+"n"+I)
                .replaceAll(n+"3", "-"+"n"+O)
                .replaceAll(n+"4", "-"+"n"+U)
                .replaceAll(n+"5", "-"+"n"+A+A)
                .replaceAll(n+"6", "-"+"n"+E+E)
                .replaceAll(n+"7", "-"+"n"+I+I)
                .replaceAll(n+"8", "-"+"n"+O+O)
                .replaceAll(n+"9", "-"+"n"+U+U)

                .replaceAll(o+"0", "-"+"o"+A)
                .replaceAll(o+"1", "-"+"o"+E)
                .replaceAll(o+"2", "-"+"o"+I)
                .replaceAll(o+"3", "-"+"o"+O)
                .replaceAll(o+"4", "-"+"o"+U)
                .replaceAll(o+"5", "-"+"o"+A+A)
                .replaceAll(o+"6", "-"+"o"+E+E)
                .replaceAll(o+"7", "-"+"o"+I+I)
                .replaceAll(o+"8", "-"+"o"+O+O)
                .replaceAll(o+"9", "-"+"o"+U+U)

                .replaceAll(p+"0", "-"+"p"+A)
                .replaceAll(p+"1", "-"+"p"+E)
                .replaceAll(p+"2", "-"+"p"+I)
                .replaceAll(p+"3", "-"+"p"+O)
                .replaceAll(p+"4", "-"+"p"+U)
                .replaceAll(p+"5", "-"+"p"+A+A)
                .replaceAll(p+"6", "-"+"p"+E+E)
                .replaceAll(p+"7", "-"+"p"+I+I)
                .replaceAll(p+"8", "-"+"p"+O+O)
                .replaceAll(p+"9", "-"+"p"+U+U)

                .replaceAll(q+"0", "-"+"q"+A)
                .replaceAll(q+"1", "-"+"q"+E)
                .replaceAll(q+"2", "-"+"q"+I)
                .replaceAll(q+"3", "-"+"q"+O)
                .replaceAll(q+"4", "-"+"q"+U)
                .replaceAll(q+"5", "-"+"q"+A+A)
                .replaceAll(q+"6", "-"+"q"+E+E)
                .replaceAll(q+"7", "-"+"q"+I+I)
                .replaceAll(q+"8", "-"+"q"+O+O)
                .replaceAll(q+"9", "-"+"q"+U+U)

                .replaceAll(r+"0", "-"+"r"+A)
                .replaceAll(r+"1", "-"+"r"+E)
                .replaceAll(r+"2", "-"+"r"+I)
                .replaceAll(r+"3", "-"+"r"+O)
                .replaceAll(r+"4", "-"+"r"+U)
                .replaceAll(r+"5", "-"+"r"+A+A)
                .replaceAll(r+"6", "-"+"r"+E+E)
                .replaceAll(r+"7", "-"+"r"+I+I)
                .replaceAll(r+"8", "-"+"r"+O+O)
                .replaceAll(r+"9", "-"+"r"+U+U)

                .replaceAll(s+"0", "-"+"s"+A)
                .replaceAll(s+"1", "-"+"s"+E)
                .replaceAll(s+"2", "-"+"s"+I)
                .replaceAll(s+"3", "-"+"s"+O)
                .replaceAll(s+"4", "-"+"s"+U)
                .replaceAll(s+"5", "-"+"s"+A+A)
                .replaceAll(s+"6", "-"+"s"+E+E)
                .replaceAll(s+"7", "-"+"s"+I+I)
                .replaceAll(s+"8", "-"+"s"+O+O)
                .replaceAll(s+"9", "-"+"s"+U+U)

                .replaceAll(t+"0", "-"+"t"+A)
                .replaceAll(t+"1", "-"+"t"+E)
                .replaceAll(t+"2", "-"+"t"+I)
                .replaceAll(t+"3", "-"+"t"+O)
                .replaceAll(t+"4", "-"+"t"+U)
                .replaceAll(t+"5", "-"+"t"+A+A)
                .replaceAll(t+"6", "-"+"t"+E+E)
                .replaceAll(t+"7", "-"+"t"+I+I)
                .replaceAll(t+"8", "-"+"t"+O+O)
                .replaceAll(t+"9", "-"+"t"+U+U)

                .replaceAll(u+"0", "-"+"u"+A)
                .replaceAll(u+"1", "-"+"u"+E)
                .replaceAll(u+"2", "-"+"u"+I)
                .replaceAll(u+"3", "-"+"u"+O)
                .replaceAll(u+"4", "-"+"u"+U)
                .replaceAll(u+"5", "-"+"u"+A+A)
                .replaceAll(u+"6", "-"+"u"+E+E)
                .replaceAll(u+"7", "-"+"u"+I+I)
                .replaceAll(u+"8", "-"+"u"+O+O)
                .replaceAll(u+"9", "-"+"u"+U+U)

                .replaceAll(v+"0", "-"+"v"+A)
                .replaceAll(v+"1", "-"+"v"+E)
                .replaceAll(v+"2", "-"+"v"+I)
                .replaceAll(v+"3", "-"+"v"+O)
                .replaceAll(v+"4", "-"+"v"+U)
                .replaceAll(v+"5", "-"+"v"+A+A)
                .replaceAll(v+"6", "-"+"v"+E+E)
                .replaceAll(v+"7", "-"+"v"+I+I)
                .replaceAll(v+"8", "-"+"v"+O+O)
                .replaceAll(v+"9", "-"+"v"+U+U)

                .replaceAll(w+"0", "-"+"w"+A)
                .replaceAll(w+"1", "-"+"w"+E)
                .replaceAll(w+"2", "-"+"w"+I)
                .replaceAll(w+"3", "-"+"w"+O)
                .replaceAll(w+"4", "-"+"w"+U)
                .replaceAll(w+"5", "-"+"w"+A+A)
                .replaceAll(w+"6", "-"+"w"+E+E)
                .replaceAll(w+"7", "-"+"w"+I+I)
                .replaceAll(w+"8", "-"+"w"+O+O)
                .replaceAll(w+"9", "-"+"w"+U+U)

                .replaceAll(x+"0", "-"+"x"+A)
                .replaceAll(x+"1", "-"+"x"+E)
                .replaceAll(x+"2", "-"+"x"+I)
                .replaceAll(x+"3", "-"+"x"+O)
                .replaceAll(x+"4", "-"+"x"+U)
                .replaceAll(x+"5", "-"+"x"+A+A)
                .replaceAll(x+"6", "-"+"x"+E+E)
                .replaceAll(x+"7", "-"+"x"+I+I)
                .replaceAll(x+"8", "-"+"x"+O+O)
                .replaceAll(x+"9", "-"+"x"+U+U)

                .replaceAll(y+"0", "-"+"y"+A)
                .replaceAll(y+"1", "-"+"y"+E)
                .replaceAll(y+"2", "-"+"y"+I)
                .replaceAll(y+"3", "-"+"y"+O)
                .replaceAll(y+"4", "-"+"y"+U)
                .replaceAll(y+"5", "-"+"y"+A+A)
                .replaceAll(y+"6", "-"+"y"+E+E)
                .replaceAll(y+"7", "-"+"y"+I+I)
                .replaceAll(y+"8", "-"+"y"+O+O)
                .replaceAll(y+"9", "-"+"y"+U+U)

                .replaceAll(z+"0", "-"+"z"+A)
                .replaceAll(z+"1", "-"+"z"+E)
                .replaceAll(z+"2", "-"+"z"+I)
                .replaceAll(z+"3", "-"+"z"+O)
                .replaceAll(z+"4", "-"+"z"+U)
                .replaceAll(z+"5", "-"+"z"+A+A)
                .replaceAll(z+"6", "-"+"z"+E+E)
                .replaceAll(z+"7", "-"+"z"+I+I)
                .replaceAll(z+"8", "-"+"z"+O+O)
                .replaceAll(z+"9", "-"+"z"+U+U)

                .replaceAll("-", "")

                ;

        outputString = convert.replaceAll("\n ","\n");

        output.setText(outputString);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }

}