package pro.gr.ams.ciphcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//QR Code
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import android.media.MediaScannerConnection;
import android.os.Environment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;
//QR Code

public class QRCodeActivity extends AppCompatActivity implements View.OnClickListener {

    String codename = "QR Code";
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

    //QR Code
    public final static int QRcodeWidth = 500 ;
    private static final String IMAGE_DIRECTORY = "/Pictures/Ciphcode/QR Code";
    Bitmap bitmap ;
    private EditText qrInp;
    private ImageView createdQR;
    private Button genQRBtn;
    private Button scnQRBtn;
    boolean qrGenerated = false;

    private Button qrScanBtn;
    private TextView qrViewName, qrViewAddress;

    private IntentIntegrator qrScan;

    TextView outputScan;

    boolean longPressDisp = false;
    //QR Code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        overridePendingTransition(R.anim.act2_enter,R.anim.act2_exit);

        code();

        info();

        overrideSample();

        hidekeyboardOutputFocus();

        disablePaste();

    }

    //Customized for QR Code outputScan instead of output
    public void  disablePaste() {
        if (outputScan.getCustomSelectionActionModeCallback() == null) {//QR Code
            outputScan.setCustomSelectionActionModeCallback(new ActionMode.Callback() {//QR Code
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
            outputScan.setCustomSelectionActionModeCallback(null);//QR Code
        }
    }

    public void hidekeyboardOutputFocus() {
        output.setOnFocusChangeListener(new View.OnFocusChangeListener() {//QR Code
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

    public void overrideSample() {
        code_sample.setVisibility(View.GONE);
        code_image = (ImageView)findViewById(R.id.code_image);
        code_image.setVisibility(View.VISIBLE);

        String uriString = "android.resource://pro.gr.ams.ciphcode/" + R.raw.qr_code_sample;
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
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),InfoActivity.class);
                i.putExtra("infoHTML",codename);
                startActivity(i);
            }
        });
        input.requestFocus();

        outputScan = (TextView)findViewById(R.id.outputScan);

        qrcodeencrypt();

        qrcodedecrypt();
    }

    public void qrcodeencrypt(){

        LinearLayout processViewDefault = (LinearLayout)findViewById(R.id.processViewDefault);
        final LinearLayout processViewQRCode = (LinearLayout)findViewById(R.id.processViewBarCodes);
        processViewDefault.setVisibility(View.GONE);
        processViewQRCode.setVisibility(View.VISIBLE);

        createdQR = (ImageView) findViewById(R.id.createdQR);
        qrInp = (EditText) findViewById(R.id.qrInp);
        genQRBtn = (Button) findViewById(R.id.genQRBtn);
        scnQRBtn = (Button) findViewById(R.id.scnQRBtn);

        genQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qrInp.getText().toString().trim().length() == 0){
                    Toast.makeText(getApplicationContext(), "Input field is empty!", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        outputScan.setVisibility(View.GONE);
                        createdQR.setVisibility(View.VISIBLE);
                        bitmap = TextToImageEncode(qrInp.getText().toString());
                        createdQR.setImageBitmap(bitmap);
                        //Hide Keyboard
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        //Hide Keyboard
                        //askSave();
                        //String path = saveImage(bitmap);  //give read write permission
                        //Toast.makeText(getApplicationContext(), "QRCode saved to -> "+path, Toast.LENGTH_SHORT).show();
                        qrGenerated = true;
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        createdQR.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (qrGenerated == true) {
                    askSave();
                }
                return false;
            }
        });
    }

    public void qrcodedecrypt(){
        //qrViewName = (TextView) findViewById(R.id.qrViewName);
        //qrViewAddress = (TextView) findViewById(R.id.qrViewAddress);

        qrScan = new IntentIntegrator(this);

        scnQRBtn.setOnClickListener(this);
    }

    public void askSave() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(codename);
        builder.setMessage("Save the image?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                dialog.dismiss();

                String path = saveImage(bitmap);  //give read write permission
                Toast.makeText(getApplicationContext(), "File was saved at "+path, Toast.LENGTH_SHORT).show();
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

    //QR Code
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

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.qrcd):getResources().getColor(R.color.qrbg);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        overridePendingTransition(R.anim.alpha_enter,R.anim.alpha_exit);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan failed.", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    //qrViewName.setText(obj.getString("name"));
                    //qrViewAddress.setText(obj.getString("address"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "Scanned succesfully.", Toast.LENGTH_SHORT).show();
                    createdQR.setVisibility(View.GONE);
                    outputScan.setVisibility(View.VISIBLE);
                    qrInp.setText("");
                    outputScan.setText(result.getContents());
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onClick(View view) {
        //initiating the qr code scan
        qrScan.initiateScan();
        qrScan.setBeepEnabled(true);
        overridePendingTransition(R.anim.alpha_enter,R.anim.alpha_exit);
    }
    //QR Code
}
