package converter.tms.com.converter;

import android.widget.Toast;

public class FreeMethods {
    public  MainActivity main;
    public FreeMethods(MainActivity main){
        this.main = main;
    }
        public  String getCalculatingNumber(){
        return main.editText.getText().toString();
        }
        public int getColorNumberByName(String name){
            switch(name){
                case "green": return main.getResources().getColor(R.color.green);
                case "red": return main.getResources().getColor(R.color.red);
                default: throw new RuntimeException("Incorrect colour's name!");
            }
        }
        public void returnMessage(String message){
            Toast.makeText(main.getApplicationContext(),message,Toast.LENGTH_SHORT).show();
        }
        public void saveMemory(int quantity){
            main.save.edit().putString("res"+ quantity,main.listchild).apply();
        }
        public void setLength(int quantity){
            main.save.edit().putInt("length",quantity).apply();
        }
}
