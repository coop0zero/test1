package converter.tms.com.converter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.widget.TextView;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import android.content.SharedPreferences;
import android.widget.Toast;

public class MainActivity extends Activity {

    private String[] ish = {"2-чной","8-чной","10-чной","16-чной"};
    private String[] kon = {"2-чную","8-чную","10-чную","16-чную"};
    public FreeMethods methods;

    ClipboardManager clipboardManager ;
    ClipData clipData;
    TextView textCopy;
    String number;
    String c;
    String listchild;
    SharedPreferences save;
    int a;
    int b;    
    Boolean check;
    EditText editText;
    List<String> Convertations = new ArrayList<>();


    @Override
    /**
     *This method is used to initialize the activity
     * @param savedInstanceState for storing page state
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        methods = new FreeMethods(this);
        setContentView(R.layout.activity_main);
          editText = findViewById(R.id.editTextTextNumber);
          textCopy = findViewById(R.id.result);
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        save = getSharedPreferences("result",Context.MODE_PRIVATE);

        if(save.contains("length")&&Convertations.isEmpty()) {
            for (int c=0;c < save.getInt("length", 0);c++) {
                Convertations.add(c, save.getString("res"+c, ""));
            }
        }




        ArrayAdapter<String> adapt = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, ish);
     adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     Spinner spinner= (Spinner) findViewById(R.id.spinner);
     spinner.setAdapter(adapt);

                    AdapterView.OnItemSelectedListener itemSelectedListener1 = new AdapterView.OnItemSelectedListener() {
                        /**
                         * The method is used to determine the user-selected position and store the original number system in a variable
                         * @param parent Used to represent the adapter
                         * @param spinner Used to communicate with a UI element
                         * @param position1 Number of user-selected position
                         * @param id  Spinner element id
                         */
                        @Override
            public void onItemSelected(AdapterView<?> parent, View spinner, int position1, long id) {

                if (position1 == 0) {
                    a= 2;
                }
                else if (position1 == 1) {
                    a= 8;
                }
                else if (position1 == 2) {
                    a= 10;
                }
                else if (position1 == 3) {
                    a= 16;
                }
            }

                        /**
                         * The method is used to notify that no position has been selected
                         * @param parent Used to represent the adapter
                         * @throw  when no item was selected
                         */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                    throw new UnsupportedOperationException();
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener1);

        ArrayAdapter<String> adapt1 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, kon);
        adapt1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner1= (Spinner) findViewById(R.id.spinner1);
        spinner1.setAdapter(adapt1);
            
            AdapterView.OnItemSelectedListener itemSelectedListener2 = new AdapterView.OnItemSelectedListener() {
                /**
                 * The method is used to determine the position selected by the user and store the final number system in a variable
                 * @param parent Used to represent the adapter
                 * @param spinner1 Used to communicate with a UI element
                 * @param position2 Number of user-selected position
                 * @param id  Spinner element id
                 */
            @Override
            public void onItemSelected(AdapterView<?> parent, View spinner1, int position2, long id) {

                if (position2 == 0) {
                    b= 2;
                }
                else if (position2 == 1) {
                    b= 8;
                }
                else if (position2 == 2) {
                    b= 10;
                }
                else if (position2 == 3) {
                    b= 16;
                }
            }

                /**
                 * The method is used to notify that no position has been selected
                 * @param parent Used to represent the adapter
                 * @throw  when no item was selected
                 */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                    throw new UnsupportedOperationException();
            }
        };


        spinner1.setOnItemSelectedListener(itemSelectedListener2);
            
             

            
    }
    /**
     *This method is used to save result in clipboard
     * @param CopyClick Used to communicate with  copy button
     */
        public void CopyClick(View CopyClick) {
            clipData = ClipData.newPlainText("text", textCopy.getText());
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(getApplicationContext(),"Скопировано",Toast.LENGTH_SHORT).show();

        }
    /**
     *This method is used to convertize number from the original number system to the final
     * @param convert Used to communicate with convertize button
     */
    public void Convertize(View convert) {
        number = methods.getCalculatingNumber();
        if (((a==2)&&(number.matches("[0-1-]+")))||((a==8)&&(number.matches("[0-7-]+")))||((a==10)&&(number.matches("[0-9-]+")))||((a==16)&&(number.matches("[0-9a-fA-F-]+"))))
            {
                textCopy.setTextColor(methods.getColorNumberByName("green"));
                c = String.valueOf( new BigInteger(number, a).toString(b));

                methods.returnMessage("Успешно конвертировано");
                    if (Convertations.size()==0)
                    {
                        Convertations.add(0,c.toUpperCase());
                        listchild = "Перевод числа: "+number+" ("+a+")\nв число:                "+Convertations.get(Convertations.size()-1)+" ("+b+")";
                        methods.saveMemory(0);
                    }
                    else
                    {
                        Convertations.add(Convertations.size(),c.toUpperCase());
                        listchild = "Перевод числа: "+ number+" ("+a+")\nв число:               "+Convertations.get(Convertations.size()-1)+" ("+b+")";
                       methods.saveMemory(Convertations.size()-1);
                    }
                    methods.setLength(Convertations.size());

                //for(int i = Convertations.size()-1; i < Convertations.size(); i++) {
                    //listchild = "Перевод числа "+save.getString("number"+i, "")+" ("+a+"-чная система счисления) в число "+Convertations.get(i)+"("+b+"-чная система счисления);";
                   // save.edit().putString("res"+i,listchild).apply();
                //}



                textCopy.setText(String.valueOf(c.toUpperCase()));
            }
            else{
            methods.returnMessage("Ошибка");
            textCopy.setTextColor(methods.getColorNumberByName("red"));
            textCopy.setText(getResources().getString(R.string.errror));
            }
        }

    /**
     * This method is used to go to the history page
     * @param history Used to communicate with history button
     */
    public void OpenHistory(View history) {
        Intent intent = new Intent(this,HistoryActivity.class);
        startActivity(intent);
    }


}


       /* if (a==8){
            if (number.matches("[0-7-]+"))
            {
                textCopy.setTextColor(getResources().getColor(R.color.green));
                String c = String.valueOf( new BigInteger(number, a).toString(b));
                textCopy.setText(String.valueOf(c.toUpperCase()));
            }
            else{
                textCopy.setTextColor(getResources().getColor(R.color.red));
                textCopy.setText(getResources().getString(R.string.errror));
            }
        }
        if (a==10){
            if (number.matches("[0-9-]+"))
            {
                textCopy.setTextColor(getResources().getColor(R.color.green));
                String c = String.valueOf( new BigInteger(number, a).toString(b));
                textCopy.setText(String.valueOf(c.toUpperCase()));
            }
            else{
                textCopy.setTextColor(getResources().getColor(R.color.red));
                textCopy.setText(getResources().getString(R.string.errror));
            }
        }
        if (a==16){


                check=false;
                if (number.matches("[0-9a-fA-F-]+")) {
                    textCopy.setTextColor(getResources().getColor(R.color.green));
                    String c = String.valueOf( new BigInteger(number, a).toString(b));
                    textCopy.setText(String.valueOf(c.toUpperCase()));
                    check = true;
                }

                if (Boolean.FALSE.equals(check)) {
                    textCopy.setTextColor(getResources().getColor(R.color.red));
                    textCopy.setText(getResources().getString(R.string.errror));
                }
            }*/
