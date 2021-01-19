package pro.gr.ams.ciphcode;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private LinearLayout binary_code;
    private LinearLayout hexadecimal_code;
    private LinearLayout reverse_code;
    private LinearLayout atbash;
    private LinearLayout ultimo_cifrado;
    private LinearLayout textmate;
    private LinearLayout morse_code;
    private LinearLayout hitman_cipher;
    private LinearLayout drenzen_cipher;
    private LinearLayout clock_cipher;
    private LinearLayout cross_code;
    private LinearLayout emoticode;
    private LinearLayout dot_dot_codes;
    private LinearLayout kenny_code;
    private LinearLayout tap_code;
    private LinearLayout caesar_cipher;
    private LinearLayout bacon_cipher;
    private LinearLayout dna_cipher;
    private LinearLayout phone_cipher;
    private LinearLayout kenny_cipher_abc;
    private LinearLayout qwerty_cipher;
    private LinearLayout ace_cipher;
    private LinearLayout alphanumeric;
    private LinearLayout brackets_code;
    private LinearLayout compound_code;
    private LinearLayout homophonic_code;
    private LinearLayout leetspeak_code;
    private LinearLayout mv_cipher;
    private LinearLayout pentomino_codes;
    private LinearLayout permulation_code;
    private LinearLayout tom_tom_codes;
    private LinearLayout decimal;
    private LinearLayout latin_code;
    private LinearLayout octal;
    private LinearLayout qr_code;
    private LinearLayout polybius_square;
    private LinearLayout pigpen;

    String resume = "false";

    SQLiteDatabase infodb;
    SQLiteOpenHelper infoopenHelper;
    Cursor infocursor;
    private String info_table = "Info";
    SQLiteDatabase regdb;
    SQLiteOpenHelper regopenHelper;
    Cursor regcursor;
    private String reg_table = "Registration";
    boolean TouchAgainToExit = false;
    private Thread onfirstpress;
    private Thread resetpress;
    private Thread resetResume;
    private int clickNum = 0;
    private ImageView logo_ciphcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if (resume.equals("false")) {
            overridePendingTransition(R.anim.alpha_enter, R.anim.alpha_exit);
        }
        else if (resume.equals("true")) {
            overridePendingTransition(R.anim.act1_enter, R.anim.act1_exit);
        }

        codeslinearlayout();

        Info();

        Reg();

        Gen();

    }

    public void Info() {
        infoopenHelper = new InfoDatabaseHelper(this);
        infodb = infoopenHelper.getReadableDatabase();
    }
    public void Reg() {
        regopenHelper = new InfoDatabaseHelper(this);
        regdb = regopenHelper.getReadableDatabase();
    }

    public void Gen() {
        logo_ciphcode = (ImageView)findViewById(R.id.logo_ciphcode);
        logo_ciphcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickNum < 69) {
                    clickNum = clickNum + 1;
                }
                if (clickNum == 69) {
                    Intent reg = new Intent(getApplicationContext(), RegistrationActivity.class);
                    startActivity(reg);
                }
            }
        });
    }

    public void codeslinearlayout() {
        atbash = (LinearLayout)findViewById(R.id.atbash);
        binary_code = (LinearLayout)findViewById(R.id.binary_code);
        hexadecimal_code = (LinearLayout)findViewById(R.id.hexadecimal_code);
        reverse_code = (LinearLayout)findViewById(R.id.reverse_code);
        ultimo_cifrado = (LinearLayout)findViewById(R.id.ultimo_cifrado);
        morse_code = (LinearLayout)findViewById(R.id.morse_code);
        textmate = (LinearLayout)findViewById(R.id.textmate);
        hitman_cipher = (LinearLayout)findViewById(R.id.hitman_cipher);
        drenzen_cipher = (LinearLayout)findViewById(R.id.drenzen_cipher);
        clock_cipher = (LinearLayout)findViewById(R.id.clock_cipher);
        cross_code = (LinearLayout)findViewById(R.id.cross_code);
        emoticode = (LinearLayout)findViewById(R.id.emoticode);
        dot_dot_codes = (LinearLayout)findViewById(R.id.dot_dot_codes);
        kenny_code = (LinearLayout)findViewById(R.id.kenny_code);
        tap_code = (LinearLayout)findViewById(R.id.tap_code);
        caesar_cipher = (LinearLayout)findViewById(R.id.caesar_cipher);
        bacon_cipher = (LinearLayout)findViewById(R.id.bacon_cipher);
        dna_cipher = (LinearLayout)findViewById(R.id.dna_cipher);
        phone_cipher = (LinearLayout)findViewById(R.id.phone_cipher);
        kenny_cipher_abc = (LinearLayout)findViewById(R.id.kenny_cipher_abc);
        qwerty_cipher = (LinearLayout)findViewById(R.id.qwerty_cipher);
        ace_cipher = (LinearLayout)findViewById(R.id.ace_cipher);
        alphanumeric = (LinearLayout)findViewById(R.id.alphanumeric);
        brackets_code = (LinearLayout)findViewById(R.id.brackets_code);
        compound_code = (LinearLayout)findViewById(R.id.compound_code);
        homophonic_code = (LinearLayout)findViewById(R.id.homophonic_code);
        leetspeak_code = (LinearLayout)findViewById(R.id.leetspeak_code);
        mv_cipher = (LinearLayout)findViewById(R.id.mv_cipher);
        pentomino_codes = (LinearLayout)findViewById(R.id.pentomino_codes);
        permulation_code = (LinearLayout)findViewById(R.id.permulation_code);
        tom_tom_codes = (LinearLayout)findViewById(R.id.tom_tom_codes);
        decimal = (LinearLayout)findViewById(R.id.decimal);
        latin_code = (LinearLayout)findViewById(R.id.latin_code);
        octal = (LinearLayout)findViewById(R.id.octal);
        qr_code = (LinearLayout)findViewById(R.id.qr_code);
        polybius_square = (LinearLayout)findViewById(R.id.polybius_square);
        pigpen = (LinearLayout)findViewById(R.id.pigpen);

        ace_cipher.setBackgroundColor(Color.rgb(245, 245, 240));
        alphanumeric.setBackgroundColor(Color.rgb(235, 235, 224));
        atbash.setBackgroundColor(Color.rgb(245, 245, 240));
        bacon_cipher.setBackgroundColor(Color.rgb(235, 235, 224));
        binary_code.setBackgroundColor(Color.rgb(245, 245, 240));
        brackets_code.setBackgroundColor(Color.rgb(235, 235, 224));
        caesar_cipher.setBackgroundColor(Color.rgb(245, 245, 240));
        clock_cipher.setBackgroundColor(Color.rgb(235, 235, 224));
        compound_code.setBackgroundColor(Color.rgb(245, 245, 240));
        cross_code.setBackgroundColor(Color.rgb(235, 235, 224));
        decimal.setBackgroundColor(Color.rgb(245, 245, 240));
        dna_cipher.setBackgroundColor(Color.rgb(235, 235, 224));
        dot_dot_codes.setBackgroundColor(Color.rgb(245, 245, 240));
        drenzen_cipher.setBackgroundColor(Color.rgb(235, 235, 224));
        emoticode.setBackgroundColor(Color.rgb(245, 245, 240));
        hexadecimal_code.setBackgroundColor(Color.rgb(235, 235, 224));
        hitman_cipher.setBackgroundColor(Color.rgb(245, 245, 240));
        homophonic_code.setBackgroundColor(Color.rgb(235, 235, 224));
        kenny_cipher_abc.setBackgroundColor(Color.rgb(245, 245, 240));
        kenny_code.setBackgroundColor(Color.rgb(235, 235, 224));
        latin_code.setBackgroundColor(Color.rgb(245, 245, 240));
        leetspeak_code.setBackgroundColor(Color.rgb(235, 235, 224));
        morse_code.setBackgroundColor(Color.rgb(245, 245, 240));
        mv_cipher.setBackgroundColor(Color.rgb(235, 235, 224));
        octal.setBackgroundColor(Color.rgb(245, 245, 240));
        pentomino_codes.setBackgroundColor(Color.rgb(235, 235, 224));
        permulation_code.setBackgroundColor(Color.rgb(245, 245, 240));
        phone_cipher.setBackgroundColor(Color.rgb(235, 235, 224));
        pigpen.setBackgroundColor(Color.rgb(245, 245, 240));
        polybius_square.setBackgroundColor(Color.rgb(235, 235, 224));
        qr_code.setBackgroundColor(Color.rgb(245, 245, 240));
        qwerty_cipher.setBackgroundColor(Color.rgb(235, 235, 224));
        reverse_code.setBackgroundColor(Color.rgb(245, 245, 240));
        tap_code.setBackgroundColor(Color.rgb(235, 235, 224));
        textmate.setBackgroundColor(Color.rgb(245, 245, 240));
        tom_tom_codes.setBackgroundColor(Color.rgb(235, 235, 224));
        ultimo_cifrado.setBackgroundColor(Color.rgb(245, 245, 240));


        atbash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),AtbashActivity.class);
                        startActivity(i);
                        String codename = "Atbash";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        reverse_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),ReverseCodeActivity.class);
                        startActivity(i);
                        String codename = "Reverse Code";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        ultimo_cifrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),UltimoCifradoActivity.class);
                        startActivity(i);
                        String codename = "Ultimo-Cifrado";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        textmate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),TextmateActivity.class);
                        startActivity(i);
                        String codename = "Textmate";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        morse_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),MorseCodeActivity.class);
                        startActivity(i);
                        String codename = "Morse Code";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        hitman_cipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),HitmanCipherActivity.class);
                        startActivity(i);
                        String codename = "Hitman Cipher";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        drenzen_cipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),DrenzenCipherActivity.class);
                        startActivity(i);
                        String codename = "Drenzen Cipher";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        binary_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),BinaryCodeActivity.class);
                        startActivity(i);
                        String codename = "Binary Code";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        hexadecimal_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),HexadecimalCodeActivity.class);
                        startActivity(i);
                        String codename = "Hexadecimal Code";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        clock_cipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),ClockCipherActivity.class);
                        startActivity(i);
                        String codename = "Clock Cipher";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        cross_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),CrossCodeActivity.class);
                        startActivity(i);
                        String codename = "Cross Code";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        emoticode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),EmoticodeActivity.class);
                        startActivity(i);
                        String codename = "Emoticode";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        dot_dot_codes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),DotDotCodesActivity.class);
                        startActivity(i);
                        String codename = "Dot Dot Codes";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        kenny_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),KennyCodeActivity.class);
                        startActivity(i);
                        String codename = "Kenny Code";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        tap_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),TapCodeActivity.class);
                        startActivity(i);
                        String codename = "Tap Code";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        caesar_cipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),CaesarCipherActivity.class);
                        startActivity(i);
                        String codename = "Caesar Cipher";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        bacon_cipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {Intent i = new Intent(getApplicationContext(),BaconCipherActivity.class);
                        startActivity(i);
                        String codename = "Bacon Cipher";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        dna_cipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),DNACipherActivity.class);
                        startActivity(i);
                        String codename = "DNA Cipher";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        phone_cipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {Intent i = new Intent(getApplicationContext(),PhoneCipherActivity.class);
                        startActivity(i);
                        String codename = "Phone Cipher";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        kenny_cipher_abc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),KennyCipherABCActivity.class);
                        startActivity(i);
                        String codename = "Kenny Cipher ABC";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        qwerty_cipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),QwertyCipherActivity.class);
                        startActivity(i);
                        String codename = "Qwerty Cipher";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        ace_cipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),AceCipherActivity.class);
                        startActivity(i);
                        String codename = "Ace Cipher";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        alphanumeric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),AlphanumericActivity.class);
                        startActivity(i);
                        String codename = "Alphanumeric";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        brackets_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),BracketsCodeActivity.class);
                        startActivity(i);
                        String codename = "Brackets Code";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        compound_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {Intent i = new Intent(getApplicationContext(),CompoundCodeActivity.class);
                        startActivity(i);
                        String codename = "Compound Code";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        homophonic_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),HomophonicCodeActivity.class);
                        startActivity(i);
                        String codename = "Homophonic Code";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        leetspeak_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),LeetspeakCodeActivity.class);
                        startActivity(i);
                        String codename = "Leetspeak Code";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        mv_cipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),MVCipherActivity.class);
                        startActivity(i);
                        String codename = "MV Cipher";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        pentomino_codes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),PentominoCodesActivity.class);
                        startActivity(i);
                        String codename = "Pentomino Codes";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        permulation_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),PermulationCodeActivity.class);
                        startActivity(i);
                        String codename = "Permulation Code";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        tom_tom_codes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),TomTomCodesActivity.class);
                        startActivity(i);
                        String codename = "Tom Tom Codes";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        qr_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),QRCodeActivity.class);
                        startActivity(i);
                        String codename = "QR Code";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        polybius_square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),PolybiusSquareActivity.class);
                        startActivity(i);
                        String codename = "Polybius Square";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        decimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),DecimalActivity.class);
                        startActivity(i);
                        String codename = "Decimal";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        octal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),OctalActivity.class);
                        startActivity(i);
                        String codename = "Octal";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        latin_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),LatinCodeActivity.class);
                        startActivity(i);
                        String codename = "Latin Code";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });

        pigpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regdb = regopenHelper.getReadableDatabase();
                regcursor = regdb.rawQuery("SELECT *FROM Registration WHERE ID =?", new String[]{"1"});
                if (regcursor != null) {
                    if (regcursor.getCount() > 0) {
                        Intent i = new Intent(getApplicationContext(),PigpenActivity.class);
                        startActivity(i);
                        String codename = "Pigpen";
                        infodb = infoopenHelper.getReadableDatabase();
                        infocursor = infodb.rawQuery("SELECT *FROM Info WHERE CiphCode =?", new String[]{codename});
                        if (infocursor != null) {
                            if (infocursor.getCount() > 0) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("FirstTime", "false");
                                long id = infodb.update(info_table, contentValues, "CiphCode =?", new String[]{codename});
                            }
                            else {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("CiphCode", codename);
                                contentValues.put("FirstTime", "true");
                                long id = infodb.insert(info_table, null, contentValues);
                            }
                        }
                    }
                    else {
                        Intent reg = new Intent(getApplicationContext(),RegistrationActivity.class);
                        startActivity(reg);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        if (TouchAgainToExit == false) {
            Toast.makeText(getApplicationContext(), "Press again to exit.", Toast.LENGTH_SHORT).show();
        }
    }

    public void finish() {
        if (resume.equals("false")) {
            onfirstpress=new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(1*50);
                        TouchAgainToExit = true;
                    }
                    catch (Exception ex)
                    {}
                }
            };
            resetpress=new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3*1000);
                        TouchAgainToExit = false;
                    }
                    catch (Exception ex)
                    {}
                }
            };
            if (TouchAgainToExit == false) {
                onfirstpress.start();
                resetpress.start();
            }
            else if (TouchAgainToExit == true) {
                super.finish();
            }
        }
        else if (resume.equals("true")) {
            onfirstpress=new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(1*610);
                        TouchAgainToExit = true;
                    }
                    catch (Exception ex)
                    {}
                }
            };
            resetpress=new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3*1000);
                        TouchAgainToExit = false;
                    }
                    catch (Exception ex)
                    {}
                }
            };
            if (TouchAgainToExit == false) {
                onfirstpress.start();
                resetpress.start();
            }
            else if (TouchAgainToExit == true) {
                super.finish();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        resume = "true";
        TouchAgainToExit = false;
        resetResume=new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1*610);
                    resume = "false";
                }
                catch (Exception ex)
                {}
            }
        };
        resetResume.start();
    }
}