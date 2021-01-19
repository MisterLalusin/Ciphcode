package pro.gr.ams.ciphcode;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {

    private EditText accET;
    private EditText regET;
    private TextView accTV;
    private TextView regTV;
    private ImageView accclickIV;
    private String account;
    private boolean developer = false;
    private TextView devTV;
    private ImageView clickFive;
    private int clickNum = 0;
    private ImageView keyclickIV;
    private String key;


    SQLiteDatabase regdb;
    SQLiteOpenHelper regopenHelper;
    Cursor regcursor;
    private String reg_table = "Registration";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        accET = (EditText)findViewById(R.id.accET);
        regET = (EditText)findViewById(R.id.regET);
        accTV = (TextView)findViewById(R.id.accTV);
        regTV = (TextView)findViewById(R.id.regTV);
        accclickIV = (ImageView)findViewById(R.id.accclickIV);
        devTV = (TextView)findViewById(R.id.devTV);
        clickFive = (ImageView)findViewById(R.id.clickFive);
        keyclickIV = (ImageView)findViewById(R.id.keyclickIV);

        clickFive.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clickNum = clickNum+1;
                if (clickNum == 5) {
                    developer = true;
                    Toast.makeText(RegistrationActivity.this, "Developer options activated.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        accclickIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accET.getText().toString().length()==0) {
                    Toast.makeText(RegistrationActivity.this, "Enter your account.", Toast.LENGTH_SHORT).show();
                }
                else if (accET.getText().toString().length()!=7) {
                    Toast.makeText(RegistrationActivity.this, "Not a valid account.", Toast.LENGTH_SHORT).show();
                }
                else if (accET.getText().toString().toLowerCase()
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
                        .length()!=0) {
                    Toast.makeText(RegistrationActivity.this, "Not a valid account.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat monthmdformat = new SimpleDateFormat("MM");
                    String monthcode = monthmdformat.format(calendar.getTime())
                            .replaceAll("01", "jxK")
                            .replaceAll("02", "DEd")
                            .replaceAll("03", "dRr")
                            .replaceAll("04", "Akr")
                            .replaceAll("05", "rtY")
                            .replaceAll("06", "xCG")
                            .replaceAll("07", "fFY")
                            .replaceAll("08", "wxA")
                            .replaceAll("09", "kAl")
                            .replaceAll("10", "aQJ")
                            .replaceAll("11", "alK")
                            .replaceAll("12", "zkK");

                    String char0 = (accET.getText().toString().charAt(0) +"")
                            .replaceAll("a", "⁞a")
                            .replaceAll("b", "⁞b")
                            .replaceAll("c", "⁞c")
                            .replaceAll("d", "⁞d")
                            .replaceAll("e", "⁞e")
                            .replaceAll("f", "⁞f")
                            .replaceAll("g", "⁞g")
                            .replaceAll("h", "⁞h")
                            .replaceAll("i", "⁞i")
                            .replaceAll("j", "⁞j")
                            .replaceAll("k", "⁞k")
                            .replaceAll("l", "⁞l")
                            .replaceAll("m", "⁞n")
                            .replaceAll("n", "m")
                            .replaceAll("o", "l")
                            .replaceAll("p", "k")
                            .replaceAll("q", "j")
                            .replaceAll("r", "i")
                            .replaceAll("s", "h")
                            .replaceAll("t", "g")
                            .replaceAll("u", "f")
                            .replaceAll("v", "e")
                            .replaceAll("w", "d")
                            .replaceAll("x", "c")
                            .replaceAll("y", "b")
                            .replaceAll("z", "a")

                            .replaceAll("⁞a", "z")
                            .replaceAll("⁞b", "y")
                            .replaceAll("⁞c", "x")
                            .replaceAll("⁞d", "w")
                            .replaceAll("⁞e", "v")
                            .replaceAll("⁞f", "u")
                            .replaceAll("⁞g", "t")
                            .replaceAll("⁞h", "s")
                            .replaceAll("⁞i", "r")
                            .replaceAll("⁞j", "q")
                            .replaceAll("⁞k", "p")
                            .replaceAll("⁞l", "o")
                            .replaceAll("⁞m", "n")
                            .replaceAll("A", "⁞A")

                            .replaceAll("B", "⁞B")
                            .replaceAll("C", "⁞C")
                            .replaceAll("D", "⁞D")
                            .replaceAll("E", "⁞E")
                            .replaceAll("F", "⁞F")
                            .replaceAll("G", "⁞G")
                            .replaceAll("H", "⁞H")
                            .replaceAll("I", "⁞I")
                            .replaceAll("J", "⁞J")
                            .replaceAll("K", "⁞K")
                            .replaceAll("L", "⁞L")
                            .replaceAll("M", "⁞N")
                            .replaceAll("N", "M")
                            .replaceAll("O", "L")
                            .replaceAll("P", "K")
                            .replaceAll("Q", "J")
                            .replaceAll("R", "I")
                            .replaceAll("S", "H")
                            .replaceAll("T", "G")
                            .replaceAll("U", "F")
                            .replaceAll("V", "E")
                            .replaceAll("W", "D")
                            .replaceAll("X", "C")
                            .replaceAll("Y", "B")
                            .replaceAll("Z", "A")

                            .replaceAll("⁞A", "Z")
                            .replaceAll("⁞B", "Y")
                            .replaceAll("⁞C", "X")
                            .replaceAll("⁞D", "W")
                            .replaceAll("⁞E", "V")
                            .replaceAll("⁞F", "U")
                            .replaceAll("⁞G", "T")
                            .replaceAll("⁞H", "S")
                            .replaceAll("⁞I", "R")
                            .replaceAll("⁞J", "Q")
                            .replaceAll("⁞K", "P")
                            .replaceAll("⁞L", "O")
                            .replaceAll("⁞M", "N")
                            ;

                    SimpleDateFormat datemdformat = new SimpleDateFormat("dd");
                    String datecode = datemdformat.format(calendar.getTime())
                            .replaceAll("01", "Ax")
                            .replaceAll("02", "Ax")
                            .replaceAll("03", "Ax")
                            .replaceAll("04", "Ax")
                            .replaceAll("05", "Ax")
                            .replaceAll("06", "Ax")
                            .replaceAll("07", "Ax")
                            .replaceAll("08", "d8")
                            .replaceAll("09", "d8")
                            .replaceAll("10", "d8")
                            .replaceAll("11", "d8")
                            .replaceAll("12", "d8")
                            .replaceAll("13", "d8")
                            .replaceAll("14", "d8")
                            .replaceAll("15", "x9")
                            .replaceAll("16", "x9")
                            .replaceAll("17", "x9")
                            .replaceAll("18", "x9")
                            .replaceAll("19", "x9")
                            .replaceAll("20", "x9")
                            .replaceAll("21", "x9")
                            .replaceAll("22", "43")
                            .replaceAll("23", "43")
                            .replaceAll("24", "43")
                            .replaceAll("25", "43")
                            .replaceAll("26", "43")
                            .replaceAll("27", "43")
                            .replaceAll("28", "43")
                            .replaceAll("29", "Qz")
                            .replaceAll("30", "Qz")
                            .replaceAll("31", "Qz")
                            ;

                    SimpleDateFormat yearmdformat = new SimpleDateFormat("yyyy");
                    String yearcode = ((Integer.parseInt(yearmdformat.format(calendar.getTime()))-69)+"")
                            .replaceAll("0", "1")
                            .replaceAll("1", "a")
                            .replaceAll("2", "2")
                            .replaceAll("3", "b")
                            .replaceAll("4", "3")
                            .replaceAll("5", "c")
                            .replaceAll("6", "4")
                            .replaceAll("7", "d")
                            .replaceAll("8", "5")
                            .replaceAll("9", "e")
                            ;

                    String char1 = (accET.getText().toString().charAt(1) +"")
                            .replaceAll("a", "⁞a")
                            .replaceAll("b", "⁞b")
                            .replaceAll("c", "⁞c")
                            .replaceAll("d", "⁞d")
                            .replaceAll("e", "⁞e")
                            .replaceAll("f", "⁞f")
                            .replaceAll("g", "⁞g")
                            .replaceAll("h", "⁞h")
                            .replaceAll("i", "⁞i")
                            .replaceAll("j", "⁞j")
                            .replaceAll("k", "⁞k")
                            .replaceAll("l", "⁞l")
                            .replaceAll("m", "⁞n")
                            .replaceAll("n", "m")
                            .replaceAll("o", "l")
                            .replaceAll("p", "k")
                            .replaceAll("q", "j")
                            .replaceAll("r", "i")
                            .replaceAll("s", "h")
                            .replaceAll("t", "g")
                            .replaceAll("u", "f")
                            .replaceAll("v", "e")
                            .replaceAll("w", "d")
                            .replaceAll("x", "c")
                            .replaceAll("y", "b")
                            .replaceAll("z", "a")

                            .replaceAll("⁞a", "z")
                            .replaceAll("⁞b", "y")
                            .replaceAll("⁞c", "x")
                            .replaceAll("⁞d", "w")
                            .replaceAll("⁞e", "v")
                            .replaceAll("⁞f", "u")
                            .replaceAll("⁞g", "t")
                            .replaceAll("⁞h", "s")
                            .replaceAll("⁞i", "r")
                            .replaceAll("⁞j", "q")
                            .replaceAll("⁞k", "p")
                            .replaceAll("⁞l", "o")
                            .replaceAll("⁞m", "n")
                            .replaceAll("A", "⁞A")

                            .replaceAll("B", "⁞B")
                            .replaceAll("C", "⁞C")
                            .replaceAll("D", "⁞D")
                            .replaceAll("E", "⁞E")
                            .replaceAll("F", "⁞F")
                            .replaceAll("G", "⁞G")
                            .replaceAll("H", "⁞H")
                            .replaceAll("I", "⁞I")
                            .replaceAll("J", "⁞J")
                            .replaceAll("K", "⁞K")
                            .replaceAll("L", "⁞L")
                            .replaceAll("M", "⁞N")
                            .replaceAll("N", "M")
                            .replaceAll("O", "L")
                            .replaceAll("P", "K")
                            .replaceAll("Q", "J")
                            .replaceAll("R", "I")
                            .replaceAll("S", "H")
                            .replaceAll("T", "G")
                            .replaceAll("U", "F")
                            .replaceAll("V", "E")
                            .replaceAll("W", "D")
                            .replaceAll("X", "C")
                            .replaceAll("Y", "B")
                            .replaceAll("Z", "A")

                            .replaceAll("⁞A", "Z")
                            .replaceAll("⁞B", "Y")
                            .replaceAll("⁞C", "X")
                            .replaceAll("⁞D", "W")
                            .replaceAll("⁞E", "V")
                            .replaceAll("⁞F", "U")
                            .replaceAll("⁞G", "T")
                            .replaceAll("⁞H", "S")
                            .replaceAll("⁞I", "R")
                            .replaceAll("⁞J", "Q")
                            .replaceAll("⁞K", "P")
                            .replaceAll("⁞L", "O")
                            .replaceAll("⁞M", "N")
                            ;

                    String acc = accET.getText().toString().toLowerCase()
                            .replaceAll("a","z")
                            .replaceAll("b","3")
                            .replaceAll("c","v")
                            .replaceAll("d","x")
                            .replaceAll("e","w")
                            .replaceAll("f","y")
                            .replaceAll("g","u")
                            .replaceAll("h","1")
                            .replaceAll("i","s")
                            .replaceAll("j","t")
                            .replaceAll("k","r")
                            .replaceAll("l","4")
                            .replaceAll("m","p")
                            .replaceAll("n","q")
                            .replaceAll("o","2")
                            .replaceAll("p","o")
                            .replaceAll("q","n")
                            .replaceAll("r","m")
                            .replaceAll("s","6")
                            .replaceAll("t","l")
                            .replaceAll("u","k")
                            .replaceAll("v","5")
                            .replaceAll("w","j")
                            .replaceAll("x","i")
                            .replaceAll("y","0")
                            .replaceAll("z","h")
                            .replaceAll("0","g")
                            .replaceAll("1","f")
                            .replaceAll("2","e")
                            .replaceAll("3","7")
                            .replaceAll("4","c")
                            .replaceAll("5","d")
                            .replaceAll("6","b")
                            .replaceAll("7","9")
                            .replaceAll("8","a")
                            .replaceAll("9","8")
                            ;


                    key = monthcode + char0 + datecode + yearcode + char1 + acc;

                    String validity = "";

                    if (datecode.equals("Ax")) {
                        SimpleDateFormat MM = new SimpleDateFormat("MM");
                        String valMM = MM.format(calendar.getTime());
                        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
                        String valyyyy = yyyy.format(calendar.getTime());
                        String valdate = "07";
                        validity = valMM + " / " + valdate + " / " + valyyyy;
                    }
                    else if (datecode.equals("d8")) {
                        SimpleDateFormat MM = new SimpleDateFormat("MM");
                        String valMM = MM.format(calendar.getTime());
                        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
                        String valyyyy = yyyy.format(calendar.getTime());
                        String valdate = "14";
                        validity = valMM + " / " + valdate + " / " + valyyyy;
                    }
                    else if (datecode.equals("x9")) {
                        SimpleDateFormat MM = new SimpleDateFormat("MM");
                        String valMM = MM.format(calendar.getTime());
                        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
                        String valyyyy = yyyy.format(calendar.getTime());
                        String valdate = "21";
                        validity = valMM + " / " + valdate + " / " + valyyyy;
                    }
                    else if (datecode.equals("43")) {
                        SimpleDateFormat MM = new SimpleDateFormat("MM");
                        String valMM = MM.format(calendar.getTime());
                        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
                        String valyyyy = yyyy.format(calendar.getTime());
                        String valdate = "28";
                        validity = valMM + " / " + valdate + " / " + valyyyy;
                    }
                    else if (datecode.equals("Qz")) {
                        SimpleDateFormat MM = new SimpleDateFormat("MM");
                        String valMM = MM.format(calendar.getTime());
                        validity = "End of " +
                                (valMM
                                        .replaceAll("01","January")
                                        .replaceAll("02","February")
                                        .replaceAll("03","March")
                                        .replaceAll("04","April")
                                        .replaceAll("05","May")
                                        .replaceAll("06","June")
                                        .replaceAll("07","July")
                                        .replaceAll("08","August")
                                        .replaceAll("09","September")
                                        .replaceAll("10","October")
                                        .replaceAll("11","November")
                                        .replaceAll("12","December")
                                )
                        ;
                    }

                    account = accET.getText().toString();

                    if (developer == true) {
                        devTV.setText("Account: " + account+ "\nKey: " + key +"\nValid Until: " + validity);
                    }

                    accET.setVisibility(View.GONE);
                    accTV.setVisibility(View.GONE);
                    regET.setVisibility(View.VISIBLE);
                    regTV.setVisibility(View.VISIBLE);
                    accclickIV.setVisibility(View.GONE);
                    keyclickIV.setVisibility(View.VISIBLE);

                }

                keyclickIV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat mdformat = new
                                SimpleDateFormat("yyyy / MM / dd HH:mm:ss");
                        String td = mdformat.format(calendar.getTime());


                        if (regET.getText().toString().length()==0) {
                            Toast.makeText(RegistrationActivity.this, "Enter your key.", Toast.LENGTH_SHORT).show();
                        }
                        else if (key.equals(regET.getText().toString())) {
                            account = accET.getText().toString();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("XternAccount", account);
                            contentValues.put("RegistrationKey", key);
                            contentValues.put("TimeAndDate", td);
                            long id = regdb.insert(reg_table, null, contentValues);
                            Toast.makeText(RegistrationActivity.this, "Account validated", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(RegistrationActivity.this, "Invalid key", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        Reg();
    }


    public void Reg() {
        regopenHelper = new InfoDatabaseHelper(this);
        regdb = regopenHelper.getReadableDatabase();
    }
}
