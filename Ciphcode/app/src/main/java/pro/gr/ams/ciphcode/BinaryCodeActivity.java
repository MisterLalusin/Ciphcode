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

public class BinaryCodeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String codename = "Binary Code";
    String codesample = "01000010 01101001 01101110 01100001 01110010 01111001 00100000 01000011 01101111 01100100 01100101 ";

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
        choose_option_question.setText("Binary Digits ");
        choose_option_spinner = (Spinner)findViewById(R.id.choose_option_spinner);
        choose_option_spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("8");
        categories.add("7");
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
                if (choose_option_selected.equals("8")) {
                    /*if (input.getText().toString().replaceAll("\n","").replaceAll(" ","").length()==0) {
                        Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                    }*/
                    if (input.getText().toString().length()==0) {
                        Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                    }
                    else if (input.getText().toString().indexOf("⁞") != -1) {
                        Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                    }
                    else if (input.getText().toString().indexOf("ᴥ")!=-1) {
                        Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                    }
                   /* else if (input.getText().toString().indexOf("  ") != -1 || input.getText().toString().indexOf("\n\n\n") != -1) {
                        Toast.makeText(getApplicationContext(), "Too much spaces detected.", Toast.LENGTH_SHORT).show();
                    }*/
                    else {
                        Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();
                        binarycodeencrypt();
                    }
                }
                else if (choose_option_selected.equals("7")) {
                    /*if (input.getText().toString().replaceAll("\n","").replaceAll(" ","").length()==0) {
                        Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                    }*/
                    if (input.getText().toString().length()==0) {
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
                    else {
                        Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();
                        binarycodeencrypt();
                    }
                }
            }
        });

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choose_option_selected.equals("8")) {
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
                    else if (input.getText().toString().indexOf("⁞") != -1) {
                        Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                    }
                    /*
                    else if (input.getText().toString().indexOf("  ") != -1 || input.getText().toString().indexOf("\n\n\n") != -1) {
                        Toast.makeText(getApplicationContext(), "Too much spaces detected.", Toast.LENGTH_SHORT).show();
                    }*/
                    else if (input.getText().toString().replaceAll("0", "⁞").replaceAll("1", "⁞").indexOf(" ⁞⁞⁞⁞⁞⁞⁞ ") != -1) {
                        Toast.makeText(getApplicationContext(), "Not a valid code.", Toast.LENGTH_SHORT).show();
                    }
                    else if (input.getText().toString().replaceAll("0", "⁞").replaceAll("1", "⁞").replaceAll("\n","").replaceAll(" ","").length() % 8 != 0) {
                        Toast.makeText(getApplicationContext(), "Not a valid code.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        try {
                            binarycodedecrypt();
                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), "Not a valid code.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else if (choose_option_selected.equals("7")) {
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
                    else if (input.getText().toString().indexOf("⁞") != -1) {
                        Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                    }
                    /*else if (input.getText().toString().indexOf("  ") != -1 || input.getText().toString().indexOf("\n\n\n") != -1) {
                        Toast.makeText(getApplicationContext(), "Too much spaces detected.", Toast.LENGTH_SHORT).show();
                    }*/
                    else if (input.getText().toString().replaceAll("0", "⁞").replaceAll("1", "⁞").indexOf(" ⁞⁞⁞⁞⁞⁞⁞⁞ ") != -1) {
                        Toast.makeText(getApplicationContext(), "Not a valid code.", Toast.LENGTH_SHORT).show();
                    }
                    else if (input.getText().toString().replaceAll("0", "⁞").replaceAll("1", "⁞").replaceAll("\n","").replaceAll(" ","").length() % 7 != 0) {
                        Toast.makeText(getApplicationContext(), "Not a valid code.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        try {
                            binarycodedecrypt();
                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), "Not a valid code.", Toast.LENGTH_SHORT).show();
                        }
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

    public void binarycodeencrypt(){
        if (choose_option_selected.equals("8")) {
            inputString = input.getText().toString();

            String s = inputString;
            byte[] bytes = s.getBytes();
            StringBuilder binary = new StringBuilder();
            for (byte b : bytes) {
                int val = b;
                for (int i = 0; i < 8; i++) {
                    binary.append((val & 128) == 0 ? 0 : 1);
                    val <<= 1;
                }
                binary.append(' ');
            }

            outputString = binary.toString();

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        else if (choose_option_selected.equals("7")) {
            inputString = input.getText().toString();

            String s = inputString;
            byte[] bytes = s.getBytes();
            StringBuilder binary = new StringBuilder();
            for (int j = 0; j < bytes.length; j++) {
                int val = bytes[j];
                for (int i = 0; i < 7; i++) {
                    val <<= 1;
                    binary.append((val & 128) == 0 ? 0 : 1);
                }
                binary.append(' ');
            }

            outputString = binary.toString();

            output.setText(outputString);

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void binarycodedecrypt() {
        if (choose_option_selected.equals("8")) {
            inputString = input.getText().toString().replaceAll("\n","00001010");

            StringBuilder sb = new StringBuilder();
            char[] chars = inputString.replaceAll("\\s", "").toCharArray();
            int[] mapping = {1, 2, 4, 8, 16, 32, 64, 128};

            for (int j = 0; j < chars.length; j += 8) {
                int idx = 0;
                int sum = 0;
                for (int i = 7; i >= 0; i--) {
                    if (chars[i + j] == '1') {
                        sum += mapping[idx];
                    }
                    idx++;
                }
                sb.append(Character.toChars(sum));
            }
            outputString = sb.toString();

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Decrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        else if (choose_option_selected.equals("7")) {

            inputString = input.getText().toString().replaceAll("\n","0001010");

            StringBuilder sb = new StringBuilder();
            char[] chars = inputString.replaceAll("\\s", "").toCharArray();
            int[] mapping = {1, 2, 4, 8, 16, 32, 64, 128};

            for (int j = 0; j < chars.length; j += 7) {
                int idx = 0;
                int sum = 0;
                for (int i = 6; i >= 0; i--) {
                    if (chars[i + j] == '1') {
                        sum += mapping[idx];
                    }
                    idx++;
                }
                sb.append(Character.toChars(sum));
            }
            outputString = sb.toString();

            output.setText(outputString);

            Toast.makeText(getApplicationContext(), "Decrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
