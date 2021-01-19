package pro.gr.ams.ciphcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent i= getIntent();
        String code =i.getStringExtra("infoHTML");

        WebView view = (WebView) findViewById(R.id.info);

        WebSettings settings = view.getSettings();
        settings.setJavaScriptEnabled(true);

        if (code.equals("Binary Code")) {
            view.loadUrl("file:///android_asset/BinaryCode.html");
        }
        else if (code.equals("Hexadecimal Code")) {
            view.loadUrl("file:///android_asset/HexadecimalCode.html");
        }
        else if (code.equals("Reverse Code")) {
            view.loadUrl("file:///android_asset/ReverseCode.html");
        }
        else if (code.equals("Atbash")) {
            view.loadUrl("file:///android_asset/Atbash.html");
        }
        else if (code.equals("Ultimo-Cifrado")) {
            view.loadUrl("file:///android_asset/UltimoCifrado.html");
        }
        else if (code.equals("Textmate")) {
            view.loadUrl("file:///android_asset/Textmate.html");
        }
        else if (code.equals("Morse Code")) {
            view.loadUrl("file:///android_asset/MorseCode.html");
        }
        else if (code.equals("Hitman Cipher")) {
            view.loadUrl("file:///android_asset/HitmanCipher.html");
        }
        else if (code.equals("Drenzen Cipher")) {
            view.loadUrl("file:///android_asset/DrenzenCipher.html");
        }
        else if (code.equals("Clock Cipher")) {
            view.loadUrl("file:///android_asset/ClockCipher.html");
        }
        else if (code.equals("Cross Code")) {
            view.loadUrl("file:///android_asset/CrossCode.html");
        }
        else if (code.equals("Emoticode")) {
            view.loadUrl("file:///android_asset/Emoticode.html");
        }
        else if (code.equals("Dot Dot Codes")) {
            view.loadUrl("file:///android_asset/DotDotCodes.html");
        }
        else if (code.equals("Kenny Code")) {
            view.loadUrl("file:///android_asset/KennyCode.html");
        }
        else if (code.equals("Tap Code")) {
            view.loadUrl("file:///android_asset/TapCode.html");
        }
        else if (code.equals("Caesar Cipher")) {
            view.loadUrl("file:///android_asset/CaesarCipher.html");
        }
        else if (code.equals("Bacon Cipher")) {
            view.loadUrl("file:///android_asset/BaconCipher.html");
        }
        else if (code.equals("DNA Cipher")) {
            view.loadUrl("file:///android_asset/DNACipher.html");
        }
        else if (code.equals("Phone Cipher")) {
            view.loadUrl("file:///android_asset/PhoneCipher.html");
        }
        else if (code.equals("Kenny Cipher ABC")) {
            view.loadUrl("file:///android_asset/KennyCipherABC.html");
        }
        else if (code.equals("Qwerty Cipher")) {
            view.loadUrl("file:///android_asset/QwertyCipher.html");
        }
        else if (code.equals("Ace Cipher")) {
            view.loadUrl("file:///android_asset/AceCipher.html");
        }
        else if (code.equals("Alphanumeric")) {
            view.loadUrl("file:///android_asset/Alphanumeric.html");
        }
        else if (code.equals("Brackets Code")) {
            view.loadUrl("file:///android_asset/BracketsCode.html");
        }
        else if (code.equals("Compound Code")) {
            view.loadUrl("file:///android_asset/CompoundCode.html");
        }
        else if (code.equals("Homophonic Code")) {
            view.loadUrl("file:///android_asset/HomophonicCode.html");
        }
        else if (code.equals("Leetspeak Code")) {
            view.loadUrl("file:///android_asset/LeetspeakCode.html");
        }
        else if (code.equals("MV Cipher")) {
            view.loadUrl("file:///android_asset/MVCipher.html");
        }
        else if (code.equals("Pentomino Codes")) {
            view.loadUrl("file:///android_asset/PentominoCodes.html");
        }
        else if (code.equals("Permulation Code")) {
            view.loadUrl("file:///android_asset/PermulationCode.html");
        }
        else if (code.equals("Tom Tom Codes")) {
            view.loadUrl("file:///android_asset/TomTomCodes.html");
        }
        else if (code.equals("QR Code")) {
            view.loadUrl("file:///android_asset/QRCode.html");
        }
        else if (code.equals("Polybius Square")) {
            view.loadUrl("file:///android_asset/PolybiusSquare.html");
        }
        else if (code.equals("Decimal")) {
            view.loadUrl("file:///android_asset/Decimal.html");
        }
        else if (code.equals("Octal")) {
            view.loadUrl("file:///android_asset/Octal.html");
        }
        else if (code.equals("Latin Code")) {
            view.loadUrl("file:///android_asset/LatinCode.html");
        }
        else if (code.equals("Pigpen")) {
            view.loadUrl("file:///android_asset/Pigpen.html");
        }
    }
}
