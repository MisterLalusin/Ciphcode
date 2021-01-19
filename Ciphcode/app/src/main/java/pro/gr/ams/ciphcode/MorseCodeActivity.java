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

public class MorseCodeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    String codename = "Morse Code";
    String codesample = "-- --- .-. ... . / -.-. --- -.. . ";

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

    private LinearLayout choose_option_layout;
    private TextView choose_option_question;
    private Spinner choose_option_spinner;
    private String choose_option_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        choose_option_layout = (LinearLayout)findViewById(R.id.choose_option_layout);
        choose_option_layout.setVisibility(View.VISIBLE);
        choose_option_question = (TextView)findViewById(R.id.choose_option_question);
        choose_option_question.setText("Word Seperator ");
        choose_option_spinner = (Spinner)findViewById(R.id.choose_option_spinner);
        choose_option_spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("/");
        categories.add("//");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choose_option_spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        choose_option_selected = item;
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
        info = (Button) findViewById(R.id.info);

        encrypt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (choose_option_selected.equals("/")) {
                        String invalid = "false";
                        char[] charArray = input.getText().toString().replace("\n", "exemption").replace(".", "exemption").replace(".", "exemption").replace(",", "exemption").replace("?", "exemption").replace("'", "exemption").replace("!", "exemption").replace("/", "exemption").replace("(", "exemption").replace(")", "exemption").replace("&", "exemption").replace(":", "exemption").replace(";", "exemption").replace("=", "exemption").replace("+", "exemption").replace("-", "exemption").replace("_", "exemption").replace("\"", "exemption").replace("$", "exemption").replace("@", "exemption").toCharArray();
                        for (char c : charArray) {
                            if (!Character.isLetterOrDigit(c) && !Character.isSpaceChar(c)) {
                                invalid = "true";
                            }
                        }
                        if (invalid.equals("true")) {
                            Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                        }
                        else if (invalid.equals("false")) {
                            if (input.getText().toString().replaceAll("\n","").replaceAll(" ","").length()==0) {
                                Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                            }
                            else if (input.getText().toString().indexOf("⁞") != -1) {
                                Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                            }
                            else if (input.getText().toString().indexOf("ᴥ")!=-1) {
                                Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                            }
                            /*else if (input.getText().toString().indexOf("  ") != -1 || input.getText().toString().indexOf("\n\n\n") != -1) {
                                Toast.makeText(getApplicationContext(), "Too much spaces detected.", Toast.LENGTH_SHORT).show();
                            }*/
                            else if (input.getText().toString().indexOf("  ")!=-1) {
                                Toast.makeText(getApplicationContext(), "Multiple spaces not supported.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();
                                morsecodeencrypt();
                            }
                        }
                    }
                    else if (choose_option_selected.equals("//")) {
                        String invalid = "false";
                        char[] charArray = input.getText().toString().replace("\n", "exemption").replace(".", "exemption").replace(".", "exemption").replace(",", "exemption").replace("?", "exemption").replace("'", "exemption").replace("!", "exemption").replace("/", "exemption").replace("(", "exemption").replace(")", "exemption").replace("&", "exemption").replace(":", "exemption").replace(";", "exemption").replace("=", "exemption").replace("+", "exemption").replace("-", "exemption").replace("_", "exemption").replace("\"", "exemption").replace("$", "exemption").replace("@", "exemption").toCharArray();
                        for (char c : charArray) {
                            if (!Character.isLetterOrDigit(c) && !Character.isSpaceChar(c)) {
                                invalid = "true";
                            }
                        }
                        if (invalid.equals("true")) {
                            Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                        } else if (invalid.equals("false")) {
                            if (input.getText().toString().replaceAll("\n","").replaceAll(" ","").length()==0) {
                                Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                            }
                            else if (input.getText().toString().indexOf("⁞") != -1) {
                                Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                            }
                            else if (input.getText().toString().indexOf("ᴥ")!=-1) {
                                Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                            }
                            /*else if (input.getText().toString().indexOf("  ") != -1 || input.getText().toString().indexOf("\n\n\n") != -1) {
                                Toast.makeText(getApplicationContext(), "Too much spaces detected.", Toast.LENGTH_SHORT).show();
                            }*/
                            else if (input.getText().toString().indexOf("  ")!=-1) {
                                Toast.makeText(getApplicationContext(), "Multiple spaces not supported.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();
                                morsecodeencrypt();
                            }
                        }
                    }
                }

            });
            decrypt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (choose_option_selected.equals("/")) {
                        String chkinputString = input.getText().toString().toLowerCase().replaceAll("\n", "⁞]⁞");

                        int i = 0;

                        String[] removenextline = chkinputString.split("\n");
                        CharSequence codesequence = BuildConfig.FLAVOR;
                        String separatecode = null;

                        for (String input : removenextline) {
                            for (String splitword : input.split(" ")) {
                                codesequence = (codesequence + new StringBuilder(splitword).toString()) + " ";

                                codesequence = codesequence.toString().replaceAll(" ", "⁞ ⁞").replaceAll("⁞⁞", "⁞");
                            }
                            i++;
                            separatecode = "⁞" + codesequence.toString();
                        }

                        String[] convertarray = separatecode.split("\n");
                        CharSequence convertsequence = BuildConfig.FLAVOR;
                        String separateconvert = null;

                        for (String input : convertarray) {
                            for (String splitconvert : input.split(" ")) {
                                convertsequence = (convertsequence + new StringBuilder(splitconvert).toString()) + " ";

                                convertsequence = convertsequence.toString()

                                        .replace("\n", "⁞⁞")
                                        .replace("⁞/⁞", "⁞⁞")

                                        .replace("⁞.-⁞", "⁞⁞")
                                        .replace("⁞-...⁞", "⁞⁞")
                                        .replace("⁞-.-.⁞", "⁞⁞")
                                        .replace("⁞-..⁞", "⁞⁞")
                                        .replace("⁞.⁞", "⁞⁞")
                                        .replace("⁞..-.⁞", "⁞⁞")
                                        .replace("⁞--.⁞", "⁞⁞")
                                        .replace("⁞....⁞", "⁞⁞")
                                        .replace("⁞..⁞", "⁞⁞")
                                        .replace("⁞.---⁞", "⁞⁞")
                                        .replace("⁞-.-⁞", "⁞⁞")
                                        .replace("⁞.-..⁞", "⁞⁞")
                                        .replace("⁞--⁞", "⁞⁞")
                                        .replace("⁞-.⁞", "⁞⁞")
                                        .replace("⁞---⁞", "⁞⁞")
                                        .replace("⁞.--.⁞", "⁞⁞")
                                        .replace("⁞--.-⁞", "⁞⁞")
                                        .replace("⁞.-.⁞", "⁞⁞")
                                        .replace("⁞...⁞", "⁞⁞")
                                        .replace("⁞-⁞", "⁞⁞")
                                        .replace("⁞..-⁞", "⁞⁞")
                                        .replace("⁞...-⁞", "⁞⁞")
                                        .replace("⁞.--⁞", "⁞⁞")
                                        .replace("⁞-..-⁞", "⁞⁞")
                                        .replace("⁞-.--⁞", "⁞⁞")
                                        .replace("⁞--..⁞", "⁞⁞")

                                        .replace("⁞-----⁞", "⁞⁞")
                                        .replace("⁞.----⁞", "⁞⁞")
                                        .replace("⁞..---⁞", "⁞⁞")
                                        .replace("⁞...--⁞", "⁞⁞")
                                        .replace("⁞....-⁞", "⁞⁞")
                                        .replace("⁞.....⁞", "⁞⁞")
                                        .replace("⁞-....⁞", "⁞⁞")
                                        .replace("⁞--...⁞", "⁞⁞")
                                        .replace("⁞---..⁞", "⁞⁞")
                                        .replace("⁞----.⁞", "⁞⁞")

                                        .replace("⁞--..--⁞", "⁞⁞")
                                        .replace("⁞..--..⁞", "⁞⁞")
                                        .replace("⁞.----.⁞", "⁞⁞")
                                        .replace("⁞-.-.--⁞", "⁞⁞")
                                        .replace("⁞-.--.⁞", "⁞⁞")
                                        .replace("⁞-.--.-⁞", "⁞⁞")
                                        .replace("⁞.-...⁞", "⁞⁞")
                                        .replace("⁞---...⁞", "⁞⁞")
                                        .replace("⁞-.-.-.⁞", "⁞⁞")
                                        .replace("⁞-...-⁞", "⁞⁞")
                                        .replace("⁞.-.-.⁞", "⁞⁞")
                                        .replace("⁞..--.-⁞", "⁞⁞")
                                        .replace("⁞.-..-.⁞", "⁞⁞")
                                        .replace("⁞...-..-⁞", "⁞⁞")
                                        .replace("⁞.--.-.⁞", "⁞⁞")

                                        .replace("⁞-..-.⁞", "⁞⁞")
                                        .replace("⁞-....-⁞", "⁞⁞")
                                        .replace("⁞.-.-.-⁞", "⁞⁞")

                                        .replace("⁞⁞", "⁞")

                                ;
                            }
                            i++;
                            separateconvert = convertsequence.toString().replaceAll(" ", "");
                        }

                        String chkconvert = separateconvert
                                .replaceAll("⁞%⁞", "")
                                .replaceAll("⁞`⁞", "")
                                .replaceAll("⁞~⁞", "")
                                .replace("⁞ ⁞", "")
                                .replace("⁞", "")
                                .replaceAll("]", "")
                                .replace("{}", "");

                        if (input.getText().toString().replaceAll("\n","").replaceAll(" ","").length()==0) {
                            Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                        }
                        else if (input.getText().toString().indexOf("⁞") != -1) {
                            Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                        }
                        /*else if (input.getText().toString().indexOf("  ") != -1 || input.getText().toString().indexOf("\n\n\n") != -1) {
                            Toast.makeText(getApplicationContext(), "Too much spaces detected.", Toast.LENGTH_SHORT).show();
                        }*/
                        /*else if (input.getText().toString().indexOf("  ")!=-1) {
                            Toast.makeText(getApplicationContext(), "Multiple spaces not supported.", Toast.LENGTH_SHORT).show();
                        }*/
                        else if (chkconvert.length() != 0) {
                            Toast.makeText(getApplicationContext(), "Not a valid code.", Toast.LENGTH_SHORT).show();
                        }
                        else if (input.getText().toString().replaceAll(" ","").indexOf("//") != -1) {
                            Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Decrypted succesfully.", Toast.LENGTH_SHORT).show();
                            morsecodedecrypt();
                        }
                    }
                    else if (choose_option_selected.equals("//")) {
                        String chkinputString = input.getText().toString().toLowerCase().replaceAll("\n", "⁞]⁞");

                        int i = 0;

                        String[] removenextline = chkinputString.split("\n");
                        CharSequence codesequence = BuildConfig.FLAVOR;
                        String separatecode = null;

                        for (String input : removenextline) {
                            for (String splitword : input.split("/")) {
                                codesequence = (codesequence + new StringBuilder(splitword).toString()) + " ";

                                codesequence = codesequence.toString().replaceAll(" ", "⁞ ⁞").replaceAll("⁞⁞", "⁞");
                            }
                            i++;
                            separatecode = "⁞" + codesequence.toString();
                        }

                        String[] convertarray = separatecode.split("\n");
                        CharSequence convertsequence = BuildConfig.FLAVOR;
                        String separateconvert = null;

                        for (String input : convertarray) {
                            for (String splitconvert : input.split(" ")) {
                                convertsequence = (convertsequence + new StringBuilder(splitconvert).toString()) + " ";

                                convertsequence = convertsequence.toString()

                                        .replace("\n", "⁞⁞")
                                        .replace("⁞////⁞", "⁞⁞")
                                        .replace("⁞//⁞", "⁞⁞")

                                        .replace("⁞.-⁞", "⁞⁞")
                                        .replace("⁞-...⁞", "⁞⁞")
                                        .replace("⁞-.-.⁞", "⁞⁞")
                                        .replace("⁞-..⁞", "⁞⁞")
                                        .replace("⁞.⁞", "⁞⁞")
                                        .replace("⁞..-.⁞", "⁞⁞")
                                        .replace("⁞--.⁞", "⁞⁞")
                                        .replace("⁞....⁞", "⁞⁞")
                                        .replace("⁞..⁞", "⁞⁞")
                                        .replace("⁞.---⁞", "⁞⁞")
                                        .replace("⁞-.-⁞", "⁞⁞")
                                        .replace("⁞.-..⁞", "⁞⁞")
                                        .replace("⁞--⁞", "⁞⁞")
                                        .replace("⁞-.⁞", "⁞⁞")
                                        .replace("⁞---⁞", "⁞⁞")
                                        .replace("⁞.--.⁞", "⁞⁞")
                                        .replace("⁞--.-⁞", "⁞⁞")
                                        .replace("⁞.-.⁞", "⁞⁞")
                                        .replace("⁞...⁞", "⁞⁞")
                                        .replace("⁞-⁞", "⁞⁞")
                                        .replace("⁞..-⁞", "⁞⁞")
                                        .replace("⁞...-⁞", "⁞⁞")
                                        .replace("⁞.--⁞", "⁞⁞")
                                        .replace("⁞-..-⁞", "⁞⁞")
                                        .replace("⁞-.--⁞", "⁞⁞")
                                        .replace("⁞--..⁞", "⁞⁞")

                                        .replace("⁞-----⁞", "⁞⁞")
                                        .replace("⁞.----⁞", "⁞⁞")
                                        .replace("⁞..---⁞", "⁞⁞")
                                        .replace("⁞...--⁞", "⁞⁞")
                                        .replace("⁞....-⁞", "⁞⁞")
                                        .replace("⁞.....⁞", "⁞⁞")
                                        .replace("⁞-....⁞", "⁞⁞")
                                        .replace("⁞--...⁞", "⁞⁞")
                                        .replace("⁞---..⁞", "⁞⁞")
                                        .replace("⁞----.⁞", "⁞⁞")

                                        .replace("⁞--..--⁞", "⁞⁞")
                                        .replace("⁞..--..⁞", "⁞⁞")
                                        .replace("⁞.----.⁞", "⁞⁞")
                                        .replace("⁞-.-.--⁞", "⁞⁞")
                                        .replace("⁞-.--.⁞", "⁞⁞")
                                        .replace("⁞-.--.-⁞", "⁞⁞")
                                        .replace("⁞.-...⁞", "⁞⁞")
                                        .replace("⁞---...⁞", "⁞⁞")
                                        .replace("⁞-.-.-.⁞", "⁞⁞")
                                        .replace("⁞-...-⁞", "⁞⁞")
                                        .replace("⁞.-.-.⁞", "⁞⁞")
                                        .replace("⁞..--.-⁞", "⁞⁞")
                                        .replace("⁞.-..-.⁞", "⁞⁞")
                                        .replace("⁞...-..-⁞", "⁞⁞")
                                        .replace("⁞.--.-.⁞", "⁞⁞")

                                        .replace("⁞-..-.⁞", "⁞⁞")
                                        .replace("⁞-....-⁞", "⁞⁞")
                                        .replace("⁞.-.-.-⁞", "⁞⁞")

                                        .replace("⁞⁞", "⁞")

                                ;
                            }
                            i++;
                            separateconvert = convertsequence.toString().replaceAll(" ", "");
                        }

                        String chkconvert = separateconvert
                                .replaceAll("⁞%⁞", "")
                                .replaceAll("⁞`⁞", "")
                                .replaceAll("⁞~⁞", "")
                                .replace("⁞ ⁞", "")
                                .replace("⁞", "")
                                .replaceAll("]", "")
                                .replace("{}", "");


                        String inputStringforexemption = input.getText().toString().toLowerCase().replaceAll("\n", "⁞]⁞");

                        String[] removenextlineforexemption = inputStringforexemption.split("\n");
                        CharSequence codesequenceforexemption = BuildConfig.FLAVOR;
                        String separatecodeforexemption = null;

                        for (String inputforexemption : removenextlineforexemption) {
                            for (String splitwordforexemption : inputforexemption.split("⁞]⁞")) {
                                codesequenceforexemption = (codesequenceforexemption + new StringBuilder(splitwordforexemption).toString()) + " ";

                                codesequenceforexemption = "⁞"+codesequenceforexemption.toString() + "⁞";

                            }
                            i++;

                            separatecodeforexemption = codesequenceforexemption.toString().replaceAll("   ⁞","⁞⁞").replaceAll("  ⁞","⁞⁞").replaceAll("⁞  ","⁞⁞").replaceAll("⁞ ","⁞⁞").replaceAll("⁞","⁞⁞").replaceAll("  ⁞⁞","⁞").replaceAll("⁞⁞  ","").replaceAll("⁞⁞ ","").replaceAll(" ⁞⁞","").replaceAll("⁞","");
                        }


                        if (input.getText().toString().replaceAll("\n","").replaceAll(" ","").length()==0) {
                            Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                        }
                        else if (input.getText().toString().indexOf("⁞") != -1) {
                            Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                        }
                        /*else if (input.getText().toString().indexOf("  ") != -1 || input.getText().toString().indexOf("\n\n\n") != -1) {
                            Toast.makeText(getApplicationContext(), "Too much spaces detected.", Toast.LENGTH_SHORT).show();
                        }*/
                        /*else if (input.getText().toString().indexOf("  ")!=-1) {
                            Toast.makeText(getApplicationContext(), "Multiple spaces not supported.", Toast.LENGTH_SHORT).show();
                        }*/
                        else if (separatecodeforexemption.indexOf(" ") != -1) {
                            Toast.makeText(getApplicationContext(), "Not a valid code.", Toast.LENGTH_SHORT).show();
                        }
                        else if (chkconvert.length() != 0) {
                            Toast.makeText(getApplicationContext(), "Not a valid code.", Toast.LENGTH_SHORT).show();
                        }
                        else if (input.getText().toString().replaceAll(" ","").indexOf("/////") != -1) {
                            Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                        }
                        else if (input.getText().toString().replaceAll(" ","").indexOf(".///.") != -1) {
                            Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                        }
                        else if (input.getText().toString().replaceAll(" ","").indexOf(".///.") != -1) {
                            Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                        }
                        else if (input.getText().toString().replaceAll(" ","").indexOf(".///-") != -1) {
                            Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                        }
                        else if (input.getText().toString().replaceAll(" ","").indexOf("-///-") != -1) {
                            Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                        }
                        else if (input.getText().toString().replaceAll(" ","").indexOf("-///.") != -1) {
                            Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Decrypted succesfully.", Toast.LENGTH_SHORT).show();
                            morsecodedecrypt();
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

    public void morsecodeencrypt(){
       if (choose_option_selected.equals("/")) {
            inputString = input.getText().toString().toLowerCase();
            String convert = inputString

                    .replaceAll(" ", "[ ")

                    .replaceAll("/", "% ")
                    .replaceAll("-", "` ")
                    .replaceAll("\\.", "~ ")

                    .replaceAll(",", "--..-- ")
                    .replaceAll("\\?", "..--.. ")
                    .replaceAll("'", ".----. ")
                    .replaceAll("!", "-.-.-- ")
                    .replaceAll("\\(", "-.--. ")
                    .replaceAll("\\)", "-.--.- ")
                    .replaceAll("&", ".-... ")
                    .replaceAll(":", "---... ")
                    .replaceAll(";", "-.-.-. ")
                    .replaceAll("=", "-...- ")
                    .replaceAll("\\+", ".-.-. ")
                    .replaceAll("_", "..--.- ")
                    .replaceAll("\"", ".-..-. ")
                    .replaceAll("\\$", "...-..- ")
                    .replaceAll("@", ".--.-. ")

                    .replaceAll("a", ".- ")
                    .replaceAll("b", "-... ")
                    .replaceAll("c", "-.-. ")
                    .replaceAll("d", "-.. ")
                    .replaceAll("e", ". ")
                    .replaceAll("f", "..-. ")
                    .replaceAll("g", "--. ")
                    .replaceAll("h", ".... ")
                    .replaceAll("i", ".. ")
                    .replaceAll("j", ".--- ")
                    .replaceAll("k", "-.- ")
                    .replaceAll("l", ".-.. ")
                    .replaceAll("m", "-- ")
                    .replaceAll("n", "-. ")
                    .replaceAll("o", "--- ")
                    .replaceAll("p", ".--. ")
                    .replaceAll("q", "--.- ")
                    .replaceAll("r", ".-. ")
                    .replaceAll("s", "... ")
                    .replaceAll("t", "- ")
                    .replaceAll("u", "..- ")
                    .replaceAll("v", "...- ")
                    .replaceAll("w", ".-- ")
                    .replaceAll("x", "-..- ")
                    .replaceAll("y", "-.-- ")
                    .replaceAll("z", "--.. ")
                    .replaceAll("0", "----- ")
                    .replaceAll("1", ".---- ")
                    .replaceAll("2", "..--- ")
                    .replaceAll("3", "...-- ")
                    .replaceAll("4", "....- ")
                    .replaceAll("5", "..... ")
                    .replaceAll("6", "-.... ")
                    .replaceAll("7", "--... ")
                    .replaceAll("8", "---.. ")
                    .replaceAll("9", "----. ")

                    .replaceAll("% ", "-..-. ")
                    .replaceAll("` ", "-....- ")
                    .replaceAll("~ ", ".-.-.- ")

                    .replaceAll("\\[ ", "/ ");

            int i = 0;

            String[] removenextline = convert.split("\n");
            CharSequence addslashend = BuildConfig.FLAVOR;
            String removeslashend = null;

            for (String input : removenextline) {
                for (String splitline : input.split("\n")) {
                    addslashend = (addslashend + new StringBuilder(splitline).toString());
                    addslashend = addslashend + "⁞\n";
                }
                i++;
                removeslashend = addslashend.toString().replaceAll("/ ⁞", "");
            }

            String addtoend = removeslashend + "⁞";
            String addtoextraline = addtoend + "⁞⁞";
            String removeextraline = addtoextraline.replaceAll("\n⁞⁞", "");
            String removeendofline = removeextraline.replaceAll("⁞", "");

            outputString = removeendofline;

            output.setText(outputString);

           InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
           imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        else if (choose_option_selected.equals("//")) {
           inputString = input.getText().toString().toLowerCase();
           String convert = inputString

                   .replaceAll(" ", "⁞")

                   .replaceAll("/", "%/")
                   .replaceAll("-", "`/")
                   .replaceAll("\\.", "~/")

                   .replaceAll(",", "--..--/")
                   .replaceAll("\\?", "..--../")
                   .replaceAll("'", ".----./")
                   .replaceAll("!", "-.-.--/")
                   .replaceAll("\\(", "-.--./")
                   .replaceAll("\\)", "-.--.-/")
                   .replaceAll("&", ".-.../")
                   .replaceAll(":", "---.../")
                   .replaceAll(";", "-.-.-./")
                   .replaceAll("=", "-...-/")
                   .replaceAll("\\+", ".-.-./")
                   .replaceAll("_", "..--.-/")
                   .replaceAll("\"", ".-..-./")
                   .replaceAll("\\$", "...-..-/")
                   .replaceAll("@", ".--.-./")

                   .replaceAll("a", ".-/")
                   .replaceAll("b", "-.../")
                   .replaceAll("c", "-.-./")
                   .replaceAll("d", "-../")
                   .replaceAll("e", "./")
                   .replaceAll("f", "..-./")
                   .replaceAll("g", "--./")
                   .replaceAll("h", "..../")
                   .replaceAll("i", "../")
                   .replaceAll("j", ".---/")
                   .replaceAll("k", "-.-/")
                   .replaceAll("l", ".-../")
                   .replaceAll("m", "--/")
                   .replaceAll("n", "-./")
                   .replaceAll("o", "---/")
                   .replaceAll("p", ".--./")
                   .replaceAll("q", "--.-/")
                   .replaceAll("r", ".-./")
                   .replaceAll("s", ".../")
                   .replaceAll("t", "-/")
                   .replaceAll("u", "..-/")
                   .replaceAll("v", "...-/")
                   .replaceAll("w", ".--/")
                   .replaceAll("x", "-..-/")
                   .replaceAll("y", "-.--/")
                   .replaceAll("z", "--../")
                   .replaceAll("0", "-----/")
                   .replaceAll("1", ".----/")
                   .replaceAll("2", "..---/")
                   .replaceAll("3", "...--/")
                   .replaceAll("4", "....-/")
                   .replaceAll("5", "...../")
                   .replaceAll("6", "-..../")
                   .replaceAll("7", "--.../")
                   .replaceAll("8", "---../")
                   .replaceAll("9", "----./")

                   .replaceAll("%/", "-..-./")
                   .replaceAll("`/", "-....-/")
                   .replaceAll("~/", ".-.-.-/")

                   .replaceAll("\\[/", "//")

                   .replaceAll("//", "/")

                   .replaceAll("⁞", " ")
                   .replaceAll("/ ", "//");


           int i = 0;

           String[] removenextline = convert.split("\n");
           CharSequence addslashend = BuildConfig.FLAVOR;
           String removeslashend = null;

           for (String input : removenextline) {
               for (String splitline : input.split("\n")) {
                   addslashend = (addslashend + new StringBuilder(splitline).toString());
                   addslashend = addslashend + "⁞\n";
               }
               i++;
               removeslashend = addslashend.toString().replaceAll("/⁞", "////").replaceAll("/////","////");
           }

           String addtoend = removeslashend + "⁞";
           String addtoextraline = addtoend + "⁞⁞";
           String removeextraline = addtoextraline.replaceAll("\n⁞⁞", "");
           String removeendofline = removeextraline.replaceAll("⁞", "");

           outputString = removeendofline;

           output.setText(outputString);

           InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
           imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void morsecodedecrypt(){
        if (choose_option_selected.equals("/")) {
            inputString = input.getText().toString().toLowerCase().replaceAll("\n", "⁞]⁞");

            int i = 0;

            String[] removenextline = inputString.split("\n");
            CharSequence codesequence = BuildConfig.FLAVOR;
            String separatecode = null;

            for (String input : removenextline) {
                for (String splitword : input.split(" ")) {
                    codesequence = (codesequence + new StringBuilder(splitword).toString()) + " ";

                    codesequence = codesequence.toString().replaceAll(" ", "⁞ ⁞").replaceAll("⁞⁞", "⁞");
                }
                i++;
                separatecode = "⁞" + codesequence.toString();
            }

            String[] convertarray = separatecode.split("\n");
            CharSequence convertsequence = BuildConfig.FLAVOR;
            String separateconvert = null;

            for (String input : convertarray) {
                for (String splitconvert : input.split(" ")) {
                    convertsequence = (convertsequence + new StringBuilder(splitconvert).toString()) + " ";

                    convertsequence = convertsequence.toString()

                            .replace("\n", "⁞\n⁞")
                            .replace("⁞/⁞", "⁞{}⁞")

                            .replace("⁞.-⁞", "⁞a⁞")
                            .replace("⁞-...⁞", "⁞b⁞")
                            .replace("⁞-.-.⁞", "⁞c⁞")
                            .replace("⁞-..⁞", "⁞d⁞")
                            .replace("⁞.⁞", "⁞e⁞")
                            .replace("⁞..-.⁞", "⁞f⁞")
                            .replace("⁞--.⁞", "⁞g⁞")
                            .replace("⁞....⁞", "⁞h⁞")
                            .replace("⁞..⁞", "⁞i⁞")
                            .replace("⁞.---⁞", "⁞j⁞")
                            .replace("⁞-.-⁞", "⁞k⁞")
                            .replace("⁞.-..⁞", "⁞l⁞")
                            .replace("⁞--⁞", "⁞m⁞")
                            .replace("⁞-.⁞", "⁞n⁞")
                            .replace("⁞---⁞", "⁞o⁞")
                            .replace("⁞.--.⁞", "⁞p⁞")
                            .replace("⁞--.-⁞", "⁞q⁞")
                            .replace("⁞.-.⁞", "⁞r⁞")
                            .replace("⁞...⁞", "⁞s⁞")
                            .replace("⁞-⁞", "⁞t⁞")
                            .replace("⁞..-⁞", "⁞u⁞")
                            .replace("⁞...-⁞", "⁞v⁞")
                            .replace("⁞.--⁞", "⁞w⁞")
                            .replace("⁞-..-⁞", "⁞x⁞")
                            .replace("⁞-.--⁞", "⁞y⁞")
                            .replace("⁞--..⁞", "⁞z⁞")

                            .replace("⁞-----⁞", "⁞0⁞")
                            .replace("⁞.----⁞", "⁞1⁞")
                            .replace("⁞..---⁞", "⁞2⁞")
                            .replace("⁞...--⁞", "⁞3⁞")
                            .replace("⁞....-⁞", "⁞4⁞")
                            .replace("⁞.....⁞", "⁞5⁞")
                            .replace("⁞-....⁞", "⁞6⁞")
                            .replace("⁞--...⁞", "⁞7⁞")
                            .replace("⁞---..⁞", "⁞8⁞")
                            .replace("⁞----.⁞", "⁞9⁞")

                            .replace("⁞--..--⁞", "⁞,⁞")
                            .replace("⁞..--..⁞", "⁞?⁞")
                            .replace("⁞.----.⁞", "⁞'⁞")
                            .replace("⁞-.-.--⁞", "⁞!⁞")
                            .replace("⁞-.--.⁞", "⁞(⁞")
                            .replace("⁞-.--.-⁞", "⁞)⁞")
                            .replace("⁞.-...⁞", "⁞&⁞")
                            .replace("⁞---...⁞", "⁞:⁞")
                            .replace("⁞-.-.-.⁞", "⁞;⁞")
                            .replace("⁞-...-⁞", "⁞=⁞")
                            .replace("⁞.-.-.⁞", "⁞+⁞")
                            .replace("⁞..--.-⁞", "⁞_⁞")
                            .replace("⁞.-..-.⁞", "⁞\"⁞")
                            .replace("⁞...-..-⁞", "⁞$⁞")
                            .replace("⁞.--.-.⁞", "⁞@⁞")

                            .replace("⁞-..-.⁞", "⁞%⁞")
                            .replace("⁞-....-⁞", "⁞`⁞")
                            .replace("⁞.-.-.-⁞", "⁞~⁞")

                    ;
                }
                i++;
                separateconvert = convertsequence.toString().replaceAll(" ", "");
            }

            String convert = separateconvert
                    .replaceAll("⁞%⁞", "/")
                    .replaceAll("⁞`⁞", "-")
                    .replaceAll("⁞~⁞", ".")
                    .replace("⁞ ⁞", "")
                    .replace("⁞", "")
                    .replaceAll("]", "\n")
                    .replace("{}", " ");

            outputString = convert;

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        else if (choose_option_selected.equals("//")) {
            inputString = input.getText().toString().toLowerCase().replaceAll("\n", "⁞]⁞").replaceAll("////", "⁞]⁞").replace("//", "⁞{}⁞");

            int i = 0;

            String[] removenextline = inputString.split("\n");
            CharSequence codesequence = BuildConfig.FLAVOR;
            String separatecode = null;

            for (String input : removenextline) {
                for (String splitword : input.split("/")) {
                    codesequence = (codesequence + new StringBuilder(splitword).toString()) + " ";

                    codesequence = codesequence.toString().replaceAll(" ", "⁞ ⁞").replaceAll("⁞⁞", "⁞");
                }
                i++;
                separatecode = "⁞" + codesequence.toString();
            }

            String[] convertarray = separatecode.split("\n");
            CharSequence convertsequence = BuildConfig.FLAVOR;
            String separateconvert = null;

            for (String input : convertarray) {
                for (String splitconvert : input.split(" ")) {
                    convertsequence = (convertsequence + new StringBuilder(splitconvert).toString()) + " ";

                    convertsequence = convertsequence.toString()

                            .replace("⁞.-⁞", "⁞a⁞")
                            .replace("⁞-...⁞", "⁞b⁞")
                            .replace("⁞-.-.⁞", "⁞c⁞")
                            .replace("⁞-..⁞", "⁞d⁞")
                            .replace("⁞.⁞", "⁞e⁞")
                            .replace("⁞..-.⁞", "⁞f⁞")
                            .replace("⁞--.⁞", "⁞g⁞")
                            .replace("⁞....⁞", "⁞h⁞")
                            .replace("⁞..⁞", "⁞i⁞")
                            .replace("⁞.---⁞", "⁞j⁞")
                            .replace("⁞-.-⁞", "⁞k⁞")
                            .replace("⁞.-..⁞", "⁞l⁞")
                            .replace("⁞--⁞", "⁞m⁞")
                            .replace("⁞-.⁞", "⁞n⁞")
                            .replace("⁞---⁞", "⁞o⁞")
                            .replace("⁞.--.⁞", "⁞p⁞")
                            .replace("⁞--.-⁞", "⁞q⁞")
                            .replace("⁞.-.⁞", "⁞r⁞")
                            .replace("⁞...⁞", "⁞s⁞")
                            .replace("⁞-⁞", "⁞t⁞")
                            .replace("⁞..-⁞", "⁞u⁞")
                            .replace("⁞...-⁞", "⁞v⁞")
                            .replace("⁞.--⁞", "⁞w⁞")
                            .replace("⁞-..-⁞", "⁞x⁞")
                            .replace("⁞-.--⁞", "⁞y⁞")
                            .replace("⁞--..⁞", "⁞z⁞")

                            .replace("⁞-----⁞", "⁞0⁞")
                            .replace("⁞.----⁞", "⁞1⁞")
                            .replace("⁞..---⁞", "⁞2⁞")
                            .replace("⁞...--⁞", "⁞3⁞")
                            .replace("⁞....-⁞", "⁞4⁞")
                            .replace("⁞.....⁞", "⁞5⁞")
                            .replace("⁞-....⁞", "⁞6⁞")
                            .replace("⁞--...⁞", "⁞7⁞")
                            .replace("⁞---..⁞", "⁞8⁞")
                            .replace("⁞----.⁞", "⁞9⁞")

                            .replace("⁞--..--⁞", "⁞,⁞")
                            .replace("⁞..--..⁞", "⁞?⁞")
                            .replace("⁞.----.⁞", "⁞'⁞")
                            .replace("⁞-.-.--⁞", "⁞!⁞")
                            .replace("⁞-.--.⁞", "⁞(⁞")
                            .replace("⁞-.--.-⁞", "⁞)⁞")
                            .replace("⁞.-...⁞", "⁞&⁞")
                            .replace("⁞---...⁞", "⁞:⁞")
                            .replace("⁞-.-.-.⁞", "⁞;⁞")
                            .replace("⁞-...-⁞", "⁞=⁞")
                            .replace("⁞.-.-.⁞", "⁞+⁞")
                            .replace("⁞..--.-⁞", "⁞_⁞")
                            .replace("⁞.-..-.⁞", "⁞\"⁞")
                            .replace("⁞...-..-⁞", "⁞$⁞")
                            .replace("⁞.--.-.⁞", "⁞@⁞")

                            .replace("⁞-..-.⁞", "⁞%⁞")
                            .replace("⁞-....-⁞", "⁞`⁞")
                            .replace("⁞.-.-.-⁞", "⁞~⁞")

                    ;
                }
                i++;
                separateconvert = convertsequence.toString().replaceAll(" ", "");
            }

            String convert = separateconvert
                    .replaceAll("⁞%⁞", "/")
                    .replaceAll("⁞`⁞", "-")
                    .replaceAll("⁞~⁞", ".")
                    .replace("⁞ ⁞", "")
                    .replace("⁞", "")
                    .replaceAll("]", "\n")
                    .replace("{}", " ")
                    .replace("  ", "")
                    .replaceAll("\n\n", "\n");

            String extraspaceline = convert+"⁞";

            outputString = extraspaceline.replaceAll("\n⁞","").replaceAll("⁞","");

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
