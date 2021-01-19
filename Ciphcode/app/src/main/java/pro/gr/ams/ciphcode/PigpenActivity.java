package pro.gr.ams.ciphcode;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class PigpenActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    String codename = "Pigpen";
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

    //Pigpen
    private Button crtIMG;
    private Button decIMG;
    private EditText imgInp;
    private GridLayout gridENC;
    private TextView outputIMG;
    private ScrollView scrollENC;
    private Switch list_char_switch;
    private LinearLayout image_option_layout;
    private LinearLayout charLIN;
    private ImageView charLIN_character_1;
    private ImageView charLIN_character_2;
    private ImageView charLIN_character_3;
    private ImageView charLIN_character_4;
    private ImageView charLIN_character_5;
    private ImageView charLIN_character_6;
    private ImageView charLIN_character_7;
    private ImageView charLIN_character_8;
    private ImageView charLIN_character_9;
    private ImageView charLIN_character_10;
    private ImageView charLIN_character_11;
    private ImageView charLIN_character_12;
    private ImageView charLIN_character_13;
    private ImageView charLIN_character_14;
    private ImageView charLIN_character_15;
    private ImageView charLIN_character_16;
    private ImageView charLIN_character_17;
    private ImageView charLIN_character_18;
    private ImageView charLIN_character_19;
    private ImageView charLIN_character_20;
    private ImageView charLIN_character_21;
    private ImageView charLIN_character_22;
    private ImageView charLIN_character_23;
    private ImageView charLIN_character_24;
    private ImageView charLIN_character_25;
    private ImageView charLIN_character_26;
    private ImageView charLIN_space;
    private ImageView charLIN_linebreak;
    private ImageView charLIN_backspace;
    private String convertIMGVAL="";
    private GridLayout gridDEC;
    private ScrollView scrollDEC;
    private TextView charOnTV;
    private String mode = "en";
    private ImageView sep_char_1;
    private ImageView sep_char_2;
    private ImageView sep_char_3;
    private ImageView sep_char_4;
    private ImageView sep_char_5;
    private ImageView sep_char_6;
    private ImageView sep_char_7;
    private ImageView sep_char_8;
    private ImageView sep_char_9;
    private ImageView sep_char_10;
    private ImageView sep_char_11;
    private ImageView sep_char_12;
    private ImageView sep_char_13;
    private ImageView sep_char_14;
    private ImageView sep_char_15;
    private ImageView sep_char_16;
    private ImageView sep_char_17;
    private ImageView sep_char_18;
    private ImageView sep_char_19;
    private ImageView sep_char_20;
    private ImageView sep_char_21;
    private ImageView sep_char_22;
    private ImageView sep_char_23;
    private ImageView sep_char_24;
    private ImageView sep_char_25;
    private ImageView sep_space;
    private ImageView sep_linebreak;
    private ImageView sep_backspace;
    private TextView image_option_question;
    private static final String IMAGE_DIRECTORY = "/Pictures/Ciphcode/Pigpen";
    private boolean longPressDisp = false;
    private Bitmap bitmap;
    private boolean ppGenerated = false;
    private boolean permissionGranted = false;
    //Pigpen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        overridePendingTransition(R.anim.act2_enter,R.anim.act2_exit);

        code();

        info();

        imageoption();

        overrideSample();

        hidekeyboardOutputFocus();

        disablePaste();

    }

    public void  disablePaste() {
        if (outputIMG.getCustomSelectionActionModeCallback() == null) {
            outputIMG.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
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
            outputIMG.setCustomSelectionActionModeCallback(null);
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

    public  void  imageoption(){
        list_char_switch=(Switch)findViewById(R.id.list_char_switch);
        image_option_question = (TextView)findViewById(R.id.image_option_question);
        list_char_switch.setOnCheckedChangeListener(this);
        //list_char_switch.setTextOff("Alpha");
        //list_char_switch.setTextOn("Char");

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            list_char_switch.setTextOff("   ");
            list_char_switch.setTextOn("   ");
            list_char_switch.setTrackResource(R.drawable.switch_track);
            list_char_switch.setThumbResource(R.drawable.switch_thumb);
        }

        image_option_question.setText(codename);

        image_option_layout = (LinearLayout)findViewById(R.id.image_option_layout);
        image_option_layout.setVisibility(View.VISIBLE);

        charLIN = (LinearLayout)findViewById(R.id.charLIN);

        //Pigpen
        charLIN_character_1 = (ImageView)findViewById(R.id.charLIN_character_1);
        charLIN_character_2 = (ImageView)findViewById(R.id.charLIN_character_2);
        charLIN_character_3 = (ImageView)findViewById(R.id.charLIN_character_3);
        charLIN_character_4= (ImageView)findViewById(R.id.charLIN_character_4);
        charLIN_character_5 = (ImageView)findViewById(R.id.charLIN_character_5);
        charLIN_character_6 = (ImageView)findViewById(R.id.charLIN_character_6);
        charLIN_character_7 = (ImageView)findViewById(R.id.charLIN_character_7);
        charLIN_character_8 = (ImageView)findViewById(R.id.charLIN_character_8);
        charLIN_character_9 = (ImageView)findViewById(R.id.charLIN_character_9);
        charLIN_character_10 = (ImageView)findViewById(R.id.charLIN_character_10);
        charLIN_character_11 = (ImageView)findViewById(R.id.charLIN_character_11);
        charLIN_character_12 = (ImageView)findViewById(R.id.charLIN_character_12);
        charLIN_character_13 = (ImageView)findViewById(R.id.charLIN_character_13);
        charLIN_character_14 = (ImageView)findViewById(R.id.charLIN_character_14);
        charLIN_character_15 = (ImageView)findViewById(R.id.charLIN_character_15);
        charLIN_character_16 = (ImageView)findViewById(R.id.charLIN_character_16);
        charLIN_character_17 = (ImageView)findViewById(R.id.charLIN_character_17);
        charLIN_character_18 = (ImageView)findViewById(R.id.charLIN_character_18);
        charLIN_character_19 = (ImageView)findViewById(R.id.charLIN_character_19);
        charLIN_character_20 = (ImageView)findViewById(R.id.charLIN_character_20);
        charLIN_character_21 = (ImageView)findViewById(R.id.charLIN_character_21);
        charLIN_character_22 = (ImageView)findViewById(R.id.charLIN_character_22);
        charLIN_character_23 = (ImageView)findViewById(R.id.charLIN_character_23);
        charLIN_character_24 = (ImageView)findViewById(R.id.charLIN_character_24);
        charLIN_character_25 = (ImageView)findViewById(R.id.charLIN_character_25);
        charLIN_character_26 = (ImageView)findViewById(R.id.charLIN_character_26);
        charLIN_space = (ImageView)findViewById(R.id.charLIN_space);
        charLIN_linebreak = (ImageView)findViewById(R.id.charLIN_linebreak);
        charLIN_backspace = (ImageView)findViewById(R.id.charLIN_backspace);

        charLIN_character_1.setVisibility(View.VISIBLE);
        charLIN_character_2.setVisibility(View.VISIBLE);
        charLIN_character_3.setVisibility(View.VISIBLE);
        charLIN_character_4.setVisibility(View.VISIBLE);
        charLIN_character_5.setVisibility(View.VISIBLE);
        charLIN_character_6.setVisibility(View.VISIBLE);
        charLIN_character_7.setVisibility(View.VISIBLE);
        charLIN_character_8.setVisibility(View.VISIBLE);
        charLIN_character_9.setVisibility(View.VISIBLE);
        charLIN_character_10.setVisibility(View.VISIBLE);
        charLIN_character_11.setVisibility(View.VISIBLE);
        charLIN_character_12.setVisibility(View.VISIBLE);
        charLIN_character_13.setVisibility(View.VISIBLE);
        charLIN_character_14.setVisibility(View.VISIBLE);
        charLIN_character_15.setVisibility(View.VISIBLE);
        charLIN_character_16.setVisibility(View.VISIBLE);
        charLIN_character_17.setVisibility(View.VISIBLE);
        charLIN_character_18.setVisibility(View.VISIBLE);
        charLIN_character_19.setVisibility(View.VISIBLE);
        charLIN_character_20.setVisibility(View.VISIBLE);
        charLIN_character_21.setVisibility(View.VISIBLE);
        charLIN_character_22.setVisibility(View.VISIBLE);
        charLIN_character_23.setVisibility(View.VISIBLE);
        charLIN_character_24.setVisibility(View.VISIBLE);
        charLIN_character_25.setVisibility(View.VISIBLE);
        charLIN_character_26.setVisibility(View.VISIBLE);
        charLIN_space.setVisibility(View.VISIBLE);
        charLIN_linebreak.setVisibility(View.VISIBLE);
        charLIN_backspace.setVisibility(View.VISIBLE);

        charLIN_character_1.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_a));
        charLIN_character_2.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_b));
        charLIN_character_3.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_c));
        charLIN_character_4.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_d));
        charLIN_character_5.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_e));
        charLIN_character_6.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_f));
        charLIN_character_7.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_g));
        charLIN_character_8.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_h));
        charLIN_character_9.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_i));
        charLIN_character_10.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_j));
        charLIN_character_11.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_k));
        charLIN_character_12.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_l));
        charLIN_character_13.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_m));
        charLIN_character_14.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_n));
        charLIN_character_15.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_o));
        charLIN_character_16.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_p));
        charLIN_character_17.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_q));
        charLIN_character_18.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_r));
        charLIN_character_19.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_s));
        charLIN_character_20.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_t));
        charLIN_character_21.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_u));
        charLIN_character_22.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_v));
        charLIN_character_23.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_w));
        charLIN_character_24.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_x));
        charLIN_character_25.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_y));
        charLIN_character_26.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_z));
        charLIN_space.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.char_space));
        charLIN_linebreak.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.char_line_break));
        charLIN_backspace.setImageURI(Uri.parse("android.resource://pro.gr.ams.ciphcode/" + R.raw.char_backspace));

        sep_char_1 = (ImageView)findViewById(R.id.sep_char_1);
        sep_char_2 = (ImageView)findViewById(R.id.sep_char_2);
        sep_char_3 = (ImageView)findViewById(R.id.sep_char_3);
        sep_char_4 = (ImageView)findViewById(R.id.sep_char_4);
        sep_char_5 = (ImageView)findViewById(R.id.sep_char_5);
        sep_char_6 = (ImageView)findViewById(R.id.sep_char_6);
        sep_char_7 = (ImageView)findViewById(R.id.sep_char_7);
        sep_char_8 = (ImageView)findViewById(R.id.sep_char_8);
        sep_char_9 = (ImageView)findViewById(R.id.sep_char_9);
        sep_char_10 = (ImageView)findViewById(R.id.sep_char_10);
        sep_char_11 = (ImageView)findViewById(R.id.sep_char_11);
        sep_char_12 = (ImageView)findViewById(R.id.sep_char_12);
        sep_char_13 = (ImageView)findViewById(R.id.sep_char_13);
        sep_char_14 = (ImageView)findViewById(R.id.sep_char_14);
        sep_char_15 = (ImageView)findViewById(R.id.sep_char_15);
        sep_char_16 = (ImageView)findViewById(R.id.sep_char_16);
        sep_char_17 = (ImageView)findViewById(R.id.sep_char_17);
        sep_char_18 = (ImageView)findViewById(R.id.sep_char_18);
        sep_char_19 = (ImageView)findViewById(R.id.sep_char_19);
        sep_char_20 = (ImageView)findViewById(R.id.sep_char_20);
        sep_char_21 = (ImageView)findViewById(R.id.sep_char_21);
        sep_char_22 = (ImageView)findViewById(R.id.sep_char_22);
        sep_char_23 = (ImageView)findViewById(R.id.sep_char_23);
        sep_char_24 = (ImageView)findViewById(R.id.sep_char_24);
        sep_char_25 = (ImageView)findViewById(R.id.sep_char_25);
        sep_space = (ImageView)findViewById(R.id.sep_space);
        sep_linebreak = (ImageView)findViewById(R.id.sep_linebreak);
        sep_backspace = (ImageView)findViewById(R.id.sep_backspace);

        sep_char_1.setVisibility(View.VISIBLE);
        sep_char_2.setVisibility(View.VISIBLE);
        sep_char_3.setVisibility(View.VISIBLE);
        sep_char_4.setVisibility(View.VISIBLE);
        sep_char_5.setVisibility(View.VISIBLE);
        sep_char_6.setVisibility(View.VISIBLE);
        sep_char_7.setVisibility(View.VISIBLE);
        sep_char_8.setVisibility(View.VISIBLE);
        sep_char_9.setVisibility(View.VISIBLE);
        sep_char_10.setVisibility(View.VISIBLE);
        sep_char_11.setVisibility(View.VISIBLE);
        sep_char_12.setVisibility(View.VISIBLE);
        sep_char_13.setVisibility(View.VISIBLE);
        sep_char_14.setVisibility(View.VISIBLE);
        sep_char_15.setVisibility(View.VISIBLE);
        sep_char_16.setVisibility(View.VISIBLE);
        sep_char_17.setVisibility(View.VISIBLE);
        sep_char_18.setVisibility(View.VISIBLE);
        sep_char_19.setVisibility(View.VISIBLE);
        sep_char_20.setVisibility(View.VISIBLE);
        sep_char_21.setVisibility(View.VISIBLE);
        sep_char_22.setVisibility(View.VISIBLE);
        sep_char_23.setVisibility(View.VISIBLE);
        sep_char_24.setVisibility(View.VISIBLE);
        sep_char_25.setVisibility(View.VISIBLE);
        sep_space.setVisibility(View.VISIBLE);
        sep_linebreak.setVisibility(View.VISIBLE);
        sep_backspace.setVisibility(View.VISIBLE);

        charLIN_character_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"a";
                pigpendecryptClick();
            }
        });
        charLIN_character_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"b";
                pigpendecryptClick();
            }
        });
        charLIN_character_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"c";
                pigpendecryptClick();
            }
        });
        charLIN_character_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"d";
                pigpendecryptClick();
            }
        });
        charLIN_character_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"e";
                pigpendecryptClick();
            }
        });
        charLIN_character_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"f";
                pigpendecryptClick();
            }
        });
        charLIN_character_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"g";
                pigpendecryptClick();
            }
        });
        charLIN_character_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"h";
                pigpendecryptClick();
            }
        });
        charLIN_character_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"i";
                pigpendecryptClick();
            }
        });
        charLIN_character_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"j";
                pigpendecryptClick();
            }
        });
        charLIN_character_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"k";
                pigpendecryptClick();
            }
        });
        charLIN_character_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"l";
                pigpendecryptClick();
            }
        });
        charLIN_character_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"m";
                pigpendecryptClick();
            }
        });
        charLIN_character_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"n";
                pigpendecryptClick();
            }
        });
        charLIN_character_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"o";
                pigpendecryptClick();
            }
        });
        charLIN_character_16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"p";
                pigpendecryptClick();
            }
        });
        charLIN_character_17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"q";
                pigpendecryptClick();
            }
        });

        charLIN_character_18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"r";
                pigpendecryptClick();
            }
        });
        charLIN_character_19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"s";
                pigpendecryptClick();
            }
        });
        charLIN_character_20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"t";
                pigpendecryptClick();
            }
        });
        charLIN_character_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"u";
                pigpendecryptClick();
            }
        });
        charLIN_character_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"v";
                pigpendecryptClick();
            }
        });
        charLIN_character_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"w";
                pigpendecryptClick();
            }
        });
        charLIN_character_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"x";
                pigpendecryptClick();
            }
        });
        charLIN_character_25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"y";
                pigpendecryptClick();
            }
        });
        charLIN_character_26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertIMGVAL = convertIMGVAL+"z";
                pigpendecryptClick();
            }
        });
        charLIN_space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((convertIMGVAL+"\n").indexOf("\n\n\n")!=-1||(convertIMGVAL+" ").indexOf("  ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Don't use so much spaces.", Toast.LENGTH_SHORT).show();
                }
                else if ((convertIMGVAL+"\n").indexOf("\n ")!=-1||("\n"+imgInp.getText().toString()).indexOf("\n ")!=-1||convertIMGVAL.length()==0) {
                    Toast.makeText(getApplicationContext(), "Don't start on spaces.", Toast.LENGTH_SHORT).show();
                }
                else if(convertIMGVAL.length()>1&&convertIMGVAL.replaceAll(" ","").length()!=0) {
                    convertIMGVAL = convertIMGVAL+" ";
                    pigpendecryptClick();
                }
                else {
                    convertIMGVAL = convertIMGVAL+" ";
                    charOnTV.setVisibility(View.GONE);
                    scrollDEC.setVisibility(View.VISIBLE);
                }

            }
        });
        charLIN_linebreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((convertIMGVAL+"\n").indexOf("\n\n\n")!=-1||(convertIMGVAL+" ").indexOf("  ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Don't use so much spaces.", Toast.LENGTH_SHORT).show();
                }
                else if ((convertIMGVAL+"\n").indexOf("\n ")!=-1||("\n"+imgInp.getText().toString()).indexOf("\n ")!=-1||convertIMGVAL.length()==0) {
                    Toast.makeText(getApplicationContext(), "Don't start on spaces.", Toast.LENGTH_SHORT).show();
                }
                else if(convertIMGVAL.length()>1&&convertIMGVAL.replaceAll(" ","").replaceAll("\n","").length()!=0) {
                    convertIMGVAL = convertIMGVAL+"\n";
                    pigpendecryptClick();
                }
                else {
                    convertIMGVAL = convertIMGVAL+"\n";
                    charOnTV.setVisibility(View.GONE);
                    scrollDEC.setVisibility(View.VISIBLE);
                }
            }
        });
        charLIN_backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(convertIMGVAL.length()>1) {
                    convertIMGVAL = convertIMGVAL.substring(0, convertIMGVAL.length() - 1);
                    pigpendecryptClick();
                }
                else {
                    convertIMGVAL = "";
                    gridDEC.removeAllViews();
                    scrollDEC.setVisibility(View.INVISIBLE);
                    charOnTV.setVisibility(View.VISIBLE);
                }
            }
        });
        charLIN_backspace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                if (convertIMGVAL.length() > 1) {
                    do {
                        if (convertIMGVAL.length() > 2) {
                            convertIMGVAL = convertIMGVAL.substring(0, convertIMGVAL.length() - 2);
                            pigpendecryptClick();
                        } else {
                            convertIMGVAL = "";
                            gridDEC.removeAllViews();
                            scrollDEC.setVisibility(View.INVISIBLE);
                            charOnTV.setVisibility(View.VISIBLE);
                        }
                    } while (onLongClick(view) == true && convertIMGVAL.length() > 2);
                    TimeInterval();
                }
                else {
                    convertIMGVAL = "";
                    gridDEC.removeAllViews();
                    scrollDEC.setVisibility(View.INVISIBLE);
                    charOnTV.setVisibility(View.VISIBLE);
                }

                return false;
            }
        });
        //Pigpen

    }

    private void TimeInterval() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            Toast.makeText(this, codename+" characters are showed.", Toast.LENGTH_SHORT).show();
            imgInp.setVisibility(View.GONE);
            charLIN.setVisibility(View.VISIBLE);
            gridDEC.removeAllViews();
            scrollDEC.setVisibility(View.GONE);
            charOnTV.setVisibility(View.VISIBLE);
            imgInp.setText("");
            convertIMGVAL = "";
            charOnTV.setText("");
            mode = "dec";

            try {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
            catch (Exception ex) {
            }

        }
        else {
            Toast.makeText(this, codename+" characters are hidden.", Toast.LENGTH_SHORT).show();
            charLIN.setVisibility(View.GONE);
            imgInp.setVisibility(View.VISIBLE);
            convertIMGVAL = "";
            imgInp.setText("");
            charOnTV.setText("");
            mode = "en";
        }
    }

    public void overrideSample() {
        code_sample.setVisibility(View.GONE);
        code_image = (ImageView)findViewById(R.id.code_image);
        code_image.setVisibility(View.VISIBLE);

        String uriString = "android.resource://pro.gr.ams.ciphcode/" + R.raw.pigpen_sample;
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

        crtIMG = (Button)findViewById(R.id.crtIMG);
        decIMG = (Button)findViewById(R.id.decIMG);
        imgInp = (EditText)findViewById(R.id.imgInp);
        outputIMG = (TextView)findViewById(R.id.outputIMG);
        gridENC = (GridLayout)findViewById(R.id.gridENC);
        scrollENC = (ScrollView)findViewById(R.id.scrollENC);
        gridDEC = (GridLayout)findViewById(R.id.gridDEC);
        scrollDEC = (ScrollView)findViewById(R.id.scrollDEC);
        charOnTV = (TextView) findViewById(R.id.charOnTV);

        LinearLayout processViewDefault = (LinearLayout)findViewById(R.id.processViewDefault);
        final LinearLayout processViewImages = (LinearLayout)findViewById(R.id.processViewImages);
        processViewDefault.setVisibility(View.GONE);
        processViewImages.setVisibility(View.VISIBLE);

        crtIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pigpen
                if (mode.equals("dec")){
                    if (charOnTV.getText().toString().length()!=0){
                        imgInp.setText(charOnTV.getText().toString());
                        imgInp.setVisibility(View.VISIBLE);

                        charLIN.setVisibility(View.GONE);
                        pigpenencrypt();
                    }
                    else if (convertIMGVAL.replaceAll("\n","").replaceAll(" ","").length()==0) {
                        Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                    }
                }
                //Pigpen
                else if (imgInp.getText().toString().replaceAll("\n","").replaceAll(" ","").length()==0) {
                    Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                }
                else if (imgInp.getText().toString().indexOf("⁞")!=-1) {
                    Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                }
                else if (imgInp.getText().toString().indexOf("ᴥ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                }
                /*else if (imgInp.getText().toString().indexOf("  ")!=-1||imgInp.getText().toString().indexOf("\n\n\n")!=-1) {
                    Toast.makeText(getApplicationContext(), "Too much spaces detected.", Toast.LENGTH_SHORT).show();
                }
                else if (imgInp.getText().toString().indexOf("\n\n\n")!=-1) {
                    Toast.makeText(getApplicationContext(), "Multiple line breaks not supported.", Toast.LENGTH_SHORT).show();
                }*/
                //Pigpen
                else if ((imgInp.getText().toString()+"\n").indexOf("\n\n\n")!=-1||(imgInp.getText().toString()+" ").indexOf("  ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Don't use so much spaces.", Toast.LENGTH_SHORT).show();
                }
                else if ((imgInp.getText().toString()+"\n").indexOf("\n ")!=-1||("\n"+imgInp.getText().toString()).indexOf("\n ")!=-1||imgInp.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(), "Don't start on spaces.", Toast.LENGTH_SHORT).show();
                }
                //Pigpen
                else {
                    pigpenencrypt();
                }
            }
        });
        decIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pigpen
                if (mode.equals("en")){
                    if (imgInp.getText().toString().replaceAll("\n","").replaceAll(" ","").length()==0) {
                        Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Not a valid code.", Toast.LENGTH_SHORT).show();
                    }
                }
                //Pigpen
                else if (charOnTV.getText().toString().length()!=0){
                    Toast.makeText(PigpenActivity.this, "Not a valid code.", Toast.LENGTH_SHORT).show();
                }
                else if (convertIMGVAL.replaceAll("\n","").replaceAll(" ","").length()==0) {
                    Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                }
                else if (convertIMGVAL.indexOf("⁞")!=-1) {
                    Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                }
                else if (convertIMGVAL.toString().indexOf("ᴥ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Unsupported characters detected.", Toast.LENGTH_SHORT).show();
                }
                /*else if (convertIMGVAL.toString().indexOf("  ")!=-1||imgInp.getText().toString().indexOf("\n\n\n")!=-1) {
                    Toast.makeText(getApplicationContext(), "Too much spaces detected.", Toast.LENGTH_SHORT).show();
                }
                else if (convertIMGVAL.indexOf("  ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Multiple spaces not supported.", Toast.LENGTH_SHORT).show();
                }*/
                /*//Pigpen
                else if (("\n"+convertIMGVAL).indexOf("\n ")!=-1) {
                    Toast.makeText(getApplicationContext(), "Don't start on spaces.", Toast.LENGTH_SHORT).show();
                }
                else if (convertIMGVAL.indexOf("\n ")!=-1||convertIMGVAL.charAt(0) == ' ') {
                    Toast.makeText(getApplicationContext(), "Don't start on spaces.", Toast.LENGTH_SHORT).show();
                }
                //Pigpen*/
                else {
                    pigpendecrypt();
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
        imgInp.requestFocus();

        gridENC.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int hiSCRL = scrollENC.getMeasuredHeight();
                int grSZ = gridENC.getMeasuredHeight()+20;
                int cdSZ = 25;

                if (grSZ < hiSCRL){
                    int saveAS = hiSCRL-grSZ;
                    ImageView pigpen_space = new ImageView(getApplicationContext());
                    pigpen_space.setImageResource(R.raw.pigpen_space);
                    pigpen_space.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, saveAS));
                    gridENC.addView(pigpen_space);
                }

                if (ppGenerated == true) {
                    askSave();
                }
                return false;
            }
        });
    }

    public void pigpenencrypt(){
        inputString = imgInp.getText().toString().toLowerCase();

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

        else{

            outputIMG.setVisibility(View.GONE);
            scrollENC.setVisibility(View.VISIBLE);

            gridENC.removeAllViews();

            int wdSCRL = imgInp.getMeasuredWidth();
            int cdSZ = 25;
            int clSZ = (wdSCRL / cdSZ)-1;
            int lnbrkRPT = 0;
            int capCOM;
            int splitlineLNT;
            int splitlineLNTRES;

            int i = 0;

            String inputStringTMP = "";

            for (String splitLIMIT : inputString.split("\n")) {

                String string = splitLIMIT;
                int charWrap = clSZ;
                int lastBreak = 0;
                int nextBreak = charWrap;
                String setString = "";
                if (string.length() > charWrap) {
                    do {
                        while (string.charAt(nextBreak) != ' ' && nextBreak > lastBreak) {
                            nextBreak--;
                        }
                        if (nextBreak == lastBreak) {
                            nextBreak = lastBreak + charWrap;
                        }
                        setString += string.substring(lastBreak, nextBreak).trim() + "\n";
                        lastBreak = nextBreak;
                        nextBreak += (charWrap+1);

                    } while (nextBreak < string.length());
                    setString += string.substring(lastBreak).trim();
                    inputStringTMP = inputStringTMP + "\n" + setString;
                }
                else {
                    setString += string.substring(lastBreak).trim();
                    inputStringTMP = inputStringTMP + "\n" + setString;
                }
            }

            inputString = inputStringTMP.replaceFirst("\n","");

            do {
                inputString = inputString.replaceAll("\n\n","\nᴥ\n");
            }while (inputString.indexOf("\n\n")!=-1);

            String strtLBRK = inputString.charAt(0)+"";

            if(strtLBRK.equals("\n")) {
                inputString = inputString.replaceFirst("\n","ᴥ\n");
            }

            for (String splitline : inputString.split("\n")) {
                lnbrkRPT = clSZ-splitline.length();
                capCOM = splitline.length();
                splitlineLNT = splitline.length();
                splitlineLNTRES = splitlineLNT;
                gridENC.setColumnCount(clSZ);
                do {
                    String conV = splitline.charAt(0) + "";
                    if (conV.equals("a")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_a = new ImageView(this);
                        pigpen_a.setImageResource(R.raw.pigpen_a);
                        pigpen_a.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_a);
                    } else if (conV.equals("b")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_b = new ImageView(this);
                        pigpen_b.setImageResource(R.raw.pigpen_b);
                        pigpen_b.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_b);
                    } else if (conV.equals("c")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_c = new ImageView(this);
                        pigpen_c.setImageResource(R.raw.pigpen_c);
                        pigpen_c.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_c);
                    } else if (conV.equals("d")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_d = new ImageView(this);
                        pigpen_d.setImageResource(R.raw.pigpen_d);
                        pigpen_d.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_d);
                    } else if (conV.equals("e")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_e = new ImageView(this);
                        pigpen_e.setImageResource(R.raw.pigpen_e);
                        pigpen_e.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_e);
                    } else if (conV.equals("f")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_f = new ImageView(this);
                        pigpen_f.setImageResource(R.raw.pigpen_f);
                        pigpen_f.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_f);
                    } else if (conV.equals("g")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_g = new ImageView(this);
                        pigpen_g.setImageResource(R.raw.pigpen_g);
                        pigpen_g.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_g);
                    } else if (conV.equals("h")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_h = new ImageView(this);
                        pigpen_h.setImageResource(R.raw.pigpen_h);
                        pigpen_h.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_h);
                    } else if (conV.equals("i")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_i = new ImageView(this);
                        pigpen_i.setImageResource(R.raw.pigpen_i);
                        pigpen_i.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_i);
                    } else if (conV.equals("j")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_j = new ImageView(this);
                        pigpen_j.setImageResource(R.raw.pigpen_j);
                        pigpen_j.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_j);
                    } else if (conV.equals("k")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_k = new ImageView(this);
                        pigpen_k.setImageResource(R.raw.pigpen_k);
                        pigpen_k.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_k);
                    } else if (conV.equals("l")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_l = new ImageView(this);
                        pigpen_l.setImageResource(R.raw.pigpen_l);
                        pigpen_l.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_l);
                    } else if (conV.equals("m")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_m = new ImageView(this);
                        pigpen_m.setImageResource(R.raw.pigpen_m);
                        pigpen_m.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_m);
                    } else if (conV.equals("n")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_n = new ImageView(this);
                        pigpen_n.setImageResource(R.raw.pigpen_n);
                        pigpen_n.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_n);
                    } else if (conV.equals("o")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_o = new ImageView(this);
                        pigpen_o.setImageResource(R.raw.pigpen_o);
                        pigpen_o.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_o);
                    } else if (conV.equals("p")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_p = new ImageView(this);
                        pigpen_p.setImageResource(R.raw.pigpen_p);
                        pigpen_p.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_p);
                    } else if (conV.equals("q")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_q = new ImageView(this);
                        pigpen_q.setImageResource(R.raw.pigpen_q);
                        pigpen_q.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_q);
                    } else if (conV.equals("r")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_r = new ImageView(this);
                        pigpen_r.setImageResource(R.raw.pigpen_r);
                        pigpen_r.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_r);
                    } else if (conV.equals("s")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_s = new ImageView(this);
                        pigpen_s.setImageResource(R.raw.pigpen_s);
                        pigpen_s.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_s);
                    } else if (conV.equals("t")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_t = new ImageView(this);
                        pigpen_t.setImageResource(R.raw.pigpen_t);
                        pigpen_t.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_t);
                    } else if (conV.equals("u")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_u = new ImageView(this);
                        pigpen_u.setImageResource(R.raw.pigpen_u);
                        pigpen_u.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_u);
                    } else if (conV.equals("v")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_v = new ImageView(this);
                        pigpen_v.setImageResource(R.raw.pigpen_v);
                        pigpen_v.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_v);
                    } else if (conV.equals("w")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_w = new ImageView(this);
                        pigpen_w.setImageResource(R.raw.pigpen_w);
                        pigpen_w.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_w);
                    } else if (conV.equals("x")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_x = new ImageView(this);
                        pigpen_x.setImageResource(R.raw.pigpen_x);
                        pigpen_x.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_x);
                    } else if (conV.equals("y")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_y = new ImageView(this);
                        pigpen_y.setImageResource(R.raw.pigpen_y);
                        pigpen_y.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_y);
                    } else if (conV.equals("z")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_z = new ImageView(this);
                        pigpen_z.setImageResource(R.raw.pigpen_z);
                        pigpen_z.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_z);
                    } else if (conV.equals(" ")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_space = new ImageView(this);
                        pigpen_space.setImageResource(R.raw.pigpen_space);
                        pigpen_space.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_space);
                    } else if (conV.equals("ᴥ")) {
                        splitline = splitline.replaceFirst(conV, "");

                        ImageView pigpen_line_break = new ImageView(this);
                        pigpen_line_break.setImageResource(R.raw.pigpen_line_break);
                        pigpen_line_break.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_line_break);

                    }

                } while (splitline.length() != 0);

                if(capCOM < clSZ) {
                    do {
                        ImageView pigpen_line_break = new ImageView(this);
                        pigpen_line_break.setImageResource(R.raw.pigpen_line_break);
                        pigpen_line_break.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridENC.addView(pigpen_line_break);
                        lnbrkRPT = lnbrkRPT - 1;
                    } while (lnbrkRPT != 0);
                }
                else if(capCOM >= clSZ){
                    lnbrkRPT = 0 - (clSZ+lnbrkRPT);

                    boolean repeatMLBRK = false;

                    do {
                        lnbrkRPT = 0 - ((clSZ) - lnbrkRPT);
                    }while (lnbrkRPT >= 0);

                    boolean repeatSLBRK = true;

                    int comADD = 0;

                    int forty8 = clSZ;
                    if ((lnbrkRPT+clSZ)==0) {
                        if ((lnbrkRPT)>(0-(clSZ*2))) {
                            int ylnbrkRPT=(0+(forty8/2));


                            do {
                                splitlineLNT-=clSZ;
                            }while (splitlineLNT>clSZ);
                            int tst = splitlineLNT;

                            int one = splitlineLNT;
                            lnbrkRPT = 0-((ylnbrkRPT*2)-one);
                        }
                        else {
                            repeatSLBRK = true;
                        }
                    }
                    else if ((lnbrkRPT + clSZ) < 0) {

                        if ((lnbrkRPT)>(0-(clSZ*2))) {
                            int ylnbrkRPT=(0+(forty8/2));


                            do {
                                splitlineLNT-=clSZ;
                            }while (splitlineLNT>clSZ);
                            int tst = splitlineLNT;

                            int one = splitlineLNT;
                            lnbrkRPT = 0-((ylnbrkRPT*2)-one);

                        }
                        else {
                            lnbrkRPT = lnbrkRPT + clSZ;
                            repeatSLBRK = false;
                            comADD = lnbrkRPT + clSZ;
                        }
                    }

                    if (lnbrkRPT >= 0) {
                        repeatSLBRK = false;
                    }

                    if (repeatSLBRK  == true) {
                        do {
                            ImageView pigpen_line_break = new ImageView(this);
                            pigpen_line_break.setImageResource(R.raw.pigpen_line_break);
                            pigpen_line_break.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                            gridENC.addView(pigpen_line_break);
                            lnbrkRPT = lnbrkRPT + 1;
                        } while (lnbrkRPT != 0);
                    }
                }
            }
            i++;

            Toast.makeText(getApplicationContext(), "Encrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            ppGenerated = true;

            //askSave();
        }

    }

    public void pigpendecrypt(){
        scrollENC.setVisibility(View.GONE);
        outputIMG.setVisibility(View.VISIBLE);
        inputString = convertIMGVAL;
        outputIMG.setText(convertIMGVAL);
        input.requestFocus();
        Toast.makeText(getApplicationContext(), "Decrypted succesfully.", Toast.LENGTH_SHORT).show();
    }

    public void pigpendecryptClick(){
        inputString = convertIMGVAL;

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

        else{

            charOnTV.setVisibility(View.GONE);
            scrollDEC.setVisibility(View.VISIBLE);

            gridDEC.removeAllViews();

            int wdSCRL = charOnTV.getMeasuredWidth();
            int cdSZ = 25;
            int clSZ = (wdSCRL / cdSZ)-1;
            int lnbrkRPT = 0;
            int capCOM;
            int splitlineLNT;
            int splitlineLNTRES;

            int i = 0;

            String inputStringTMP = "";

            for (String splitLIMIT : inputString.split("\n")) {

                String string = splitLIMIT;
                int charWrap = clSZ;
                int lastBreak = 0;
                int nextBreak = charWrap;
                String setString = "";
                if (string.length() > charWrap) {
                    do {
                        while (string.charAt(nextBreak) != ' ' && nextBreak > lastBreak) {
                            nextBreak--;
                        }
                        if (nextBreak == lastBreak) {
                            nextBreak = lastBreak + charWrap;
                        }
                        setString += string.substring(lastBreak, nextBreak).trim() + "\n";
                        lastBreak = nextBreak;
                        nextBreak += (charWrap+1);

                    } while (nextBreak < string.length());
                    setString += string.substring(lastBreak).trim();
                    inputStringTMP = inputStringTMP + "\n" + setString;
                }
                else {
                    setString += string.substring(lastBreak).trim();
                    inputStringTMP = inputStringTMP + "\n" + setString;
                }
            }

            inputString = inputStringTMP.replaceFirst("\n","");

            do {
                inputString = inputString.replaceAll("\n\n","\nᴥ\n");
            }while (inputString.indexOf("\n\n")!=-1);

            String strtLBRK = inputString.charAt(0)+"";

            if(strtLBRK.equals("\n")) {
                inputString = inputString.replaceFirst("\n","ᴥ\n");
            }

            for (String splitline : inputString.split("\n")) {
                lnbrkRPT = clSZ-splitline.length();
                capCOM = splitline.length();
                splitlineLNT = splitline.length();
                splitlineLNTRES = splitlineLNT;
                gridDEC.setColumnCount(clSZ);
                do {
                    String conV = splitline.charAt(0) + "";
                    if (conV.equals("a")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_a = new ImageView(this);
                        pigpen_a.setImageResource(R.raw.pigpen_a);
                        pigpen_a.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_a);
                    } else if (conV.equals("b")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_b = new ImageView(this);
                        pigpen_b.setImageResource(R.raw.pigpen_b);
                        pigpen_b.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_b);
                    } else if (conV.equals("c")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_c = new ImageView(this);
                        pigpen_c.setImageResource(R.raw.pigpen_c);
                        pigpen_c.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_c);
                    } else if (conV.equals("d")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_d = new ImageView(this);
                        pigpen_d.setImageResource(R.raw.pigpen_d);
                        pigpen_d.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_d);
                    } else if (conV.equals("e")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_e = new ImageView(this);
                        pigpen_e.setImageResource(R.raw.pigpen_e);
                        pigpen_e.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_e);
                    } else if (conV.equals("f")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_f = new ImageView(this);
                        pigpen_f.setImageResource(R.raw.pigpen_f);
                        pigpen_f.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_f);
                    } else if (conV.equals("g")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_g = new ImageView(this);
                        pigpen_g.setImageResource(R.raw.pigpen_g);
                        pigpen_g.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_g);
                    } else if (conV.equals("h")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_h = new ImageView(this);
                        pigpen_h.setImageResource(R.raw.pigpen_h);
                        pigpen_h.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_h);
                    } else if (conV.equals("i")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_i = new ImageView(this);
                        pigpen_i.setImageResource(R.raw.pigpen_i);
                        pigpen_i.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_i);
                    } else if (conV.equals("j")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_j = new ImageView(this);
                        pigpen_j.setImageResource(R.raw.pigpen_j);
                        pigpen_j.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_j);
                    } else if (conV.equals("k")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_k = new ImageView(this);
                        pigpen_k.setImageResource(R.raw.pigpen_k);
                        pigpen_k.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_k);
                    } else if (conV.equals("l")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_l = new ImageView(this);
                        pigpen_l.setImageResource(R.raw.pigpen_l);
                        pigpen_l.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_l);
                    } else if (conV.equals("m")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_m = new ImageView(this);
                        pigpen_m.setImageResource(R.raw.pigpen_m);
                        pigpen_m.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_m);
                    } else if (conV.equals("n")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_n = new ImageView(this);
                        pigpen_n.setImageResource(R.raw.pigpen_n);
                        pigpen_n.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_n);
                    } else if (conV.equals("o")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_o = new ImageView(this);
                        pigpen_o.setImageResource(R.raw.pigpen_o);
                        pigpen_o.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_o);
                    } else if (conV.equals("p")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_p = new ImageView(this);
                        pigpen_p.setImageResource(R.raw.pigpen_p);
                        pigpen_p.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_p);
                    } else if (conV.equals("q")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_q = new ImageView(this);
                        pigpen_q.setImageResource(R.raw.pigpen_q);
                        pigpen_q.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_q);
                    } else if (conV.equals("r")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_r = new ImageView(this);
                        pigpen_r.setImageResource(R.raw.pigpen_r);
                        pigpen_r.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_r);
                    } else if (conV.equals("s")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_s = new ImageView(this);
                        pigpen_s.setImageResource(R.raw.pigpen_s);
                        pigpen_s.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_s);
                    } else if (conV.equals("t")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_t = new ImageView(this);
                        pigpen_t.setImageResource(R.raw.pigpen_t);
                        pigpen_t.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_t);
                    } else if (conV.equals("u")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_u = new ImageView(this);
                        pigpen_u.setImageResource(R.raw.pigpen_u);
                        pigpen_u.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_u);
                    } else if (conV.equals("v")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_v = new ImageView(this);
                        pigpen_v.setImageResource(R.raw.pigpen_v);
                        pigpen_v.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_v);
                    } else if (conV.equals("w")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_w = new ImageView(this);
                        pigpen_w.setImageResource(R.raw.pigpen_w);
                        pigpen_w.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_w);
                    } else if (conV.equals("x")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_x = new ImageView(this);
                        pigpen_x.setImageResource(R.raw.pigpen_x);
                        pigpen_x.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_x);
                    } else if (conV.equals("y")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_y = new ImageView(this);
                        pigpen_y.setImageResource(R.raw.pigpen_y);
                        pigpen_y.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_y);
                    } else if (conV.equals("z")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_z = new ImageView(this);
                        pigpen_z.setImageResource(R.raw.pigpen_z);
                        pigpen_z.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_z);
                    } else if (conV.equals(" ")) {
                        splitline = splitline.replaceFirst(conV, "");
                        ImageView pigpen_space = new ImageView(this);
                        pigpen_space.setImageResource(R.raw.pigpen_space);
                        pigpen_space.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_space);
                    } else if (conV.equals("ᴥ")) {
                        splitline = splitline.replaceFirst(conV, "");

                        ImageView pigpen_line_break = new ImageView(this);
                        pigpen_line_break.setImageResource(R.raw.pigpen_line_break);
                        pigpen_line_break.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_line_break);

                    }

                } while (splitline.length() != 0);

                if(capCOM < clSZ) {
                    do {
                        ImageView pigpen_line_break = new ImageView(this);
                        pigpen_line_break.setImageResource(R.raw.pigpen_line_break);
                        pigpen_line_break.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                        gridDEC.addView(pigpen_line_break);
                        lnbrkRPT = lnbrkRPT - 1;
                    } while (lnbrkRPT != 0);
                }
                else if(capCOM >= clSZ){
                    lnbrkRPT = 0 - (clSZ+lnbrkRPT);

                    boolean repeatMLBRK = false;

                    do {
                        lnbrkRPT = 0 - ((clSZ) - lnbrkRPT);
                    }while (lnbrkRPT >= 0);

                    boolean repeatSLBRK = true;

                    int comADD = 0;

                    int forty8 = clSZ;
                    if ((lnbrkRPT+clSZ)==0) {
                        if ((lnbrkRPT)>(0-(clSZ*2))) {
                            int ylnbrkRPT=(0+(forty8/2));


                            do {
                                splitlineLNT-=clSZ;
                            }while (splitlineLNT>clSZ);
                            int tst = splitlineLNT;

                            int one = splitlineLNT;
                            lnbrkRPT = 0-((ylnbrkRPT*2)-one);
                        }
                        else {
                            repeatSLBRK = true;
                        }
                    }
                    else if ((lnbrkRPT + clSZ) < 0) {

                        if ((lnbrkRPT)>(0-(clSZ*2))) {
                            int ylnbrkRPT=(0+(forty8/2));


                            do {
                                splitlineLNT-=clSZ;
                            }while (splitlineLNT>clSZ);
                            int tst = splitlineLNT;

                            int one = splitlineLNT;
                            lnbrkRPT = 0-((ylnbrkRPT*2)-one);

                        }
                        else {
                            lnbrkRPT = lnbrkRPT + clSZ;
                            repeatSLBRK = false;
                            comADD = lnbrkRPT + clSZ;
                        }
                    }

                    if (lnbrkRPT >= 0) {
                        repeatSLBRK = false;
                    }

                    if (repeatSLBRK  == true) {
                        do {
                            ImageView pigpen_line_break = new ImageView(this);
                            pigpen_line_break.setImageResource(R.raw.pigpen_line_break);
                            pigpen_line_break.setLayoutParams(new LinearLayout.LayoutParams(cdSZ, cdSZ));
                            gridDEC.addView(pigpen_line_break);
                            lnbrkRPT = lnbrkRPT + 1;
                        } while (lnbrkRPT != 0);
                    }
                }
            }
            i++;

            /*Toast.makeText(getApplicationContext(), "Decrypted succesfully.", Toast.LENGTH_SHORT).show();

            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);*/

            scrollDEC.fullScroll(View.FOCUS_DOWN);
        }
    }

    public void askSave() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(codename);
        builder.setMessage("Save as image?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(final DialogInterface dialog, int which) {

                requestPermission();

                (new Handler()).postDelayed(new Runnable() {
                    public void run() {

                        if (Build.VERSION.SDK_INT >= 23) {
                            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                permissionGranted = true;
                            }
                        }
                        else {
                            permissionGranted = true;
                        }

                        if (permissionGranted == true) {

                            // Do nothing but close the dialog
                            dialog.dismiss();

                            convertScrollView();

                            String path = saveImage(bitmap);  //give read write permission
                            Toast.makeText(getApplicationContext(), "File was saved at " + path, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(PigpenActivity.this, "Permission denied.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, (long)3000);
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();

                if (longPressDisp == false) {
                    Toast.makeText(getApplicationContext(), "Long press and you can still save the image.", Toast.LENGTH_SHORT).show();
                }

                longPressDisp = true;
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    //Pigpen
    private void convertScrollView() {

        ScrollView iv = (ScrollView) findViewById(R.id.scrollENC);
        bitmap = Bitmap.createBitmap(
                iv.getChildAt(0).getWidth()*2,
                iv.getChildAt(0).getHeight()*2,
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        c.scale(2.0f, 2.0f);
        c.drawColor(getResources().getColor(R.color.main));
        iv.getChildAt(0).draw(c);
        // Do whatever you want with your bitmap
        //saveImage(bitmap);

    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();   //give read write permission
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {


            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show();
//return true;
            }
            else {
                isStoragePermissionGranted();
            }

        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show();
                return true;
            } else {

                //Toast.makeText(this, "Permission is revoked", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            //Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            //Toast.makeText(this, "Permission: "+permissions[0]+ "was "+grantResults[0], Toast.LENGTH_SHORT).show();
//resume tasks needing this permission
        }
    }

//Pigpen

}
