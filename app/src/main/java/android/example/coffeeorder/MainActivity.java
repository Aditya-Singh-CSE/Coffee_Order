package android.example.coffeeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int quantity=0;
    Spinner spinner;
    ArrayList<String> spinnerArrayList;
    ArrayAdapter spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner=findViewById(R.id.spinner);
        spinnerArrayList = new ArrayList<String>();
        spinnerArrayList.add("Whipped Cream");
        spinnerArrayList.add("Chocolate");

        spinnerAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,spinnerArrayList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    public void increment(View view) {
        quantity=quantity+1;
        if(quantity>20)
        {
            quantity=20;
        }
       display(quantity);
    }

    public void decrement(View view) {
        quantity=quantity-1;
        if(quantity<0)
        {
            quantity=0;
        }
        display(quantity);
    }
    //Calculate the price of the order
    private int calculatePrice()
    {  //Price of one cup of coffee
        int basePrice=5;
        // Calculate the total order price by multiplying the quantity
        return quantity*basePrice;
    }



    /**
     * This method displays the given quantity value on the screen.
     */

    private void display(int number) {
        int price=0;
        price=5*quantity;
        TextView quantityTextView = (TextView) findViewById(R.id.quantity);
        quantityTextView.setText("" + number);

        TextView priceTextView = (TextView) findViewById(R.id.price);
        priceTextView.setText("" +price);

    }
    /**
     * Create summary of the Order
     *  price of the order
     * @return text summary
     */
    private String createOrderSummary(String name ,int price) {
        String priceMessage = "Name: "+ name;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price ;
        priceMessage += "\nThank you";
        return priceMessage;
    }

    public void submitOrder(View view) {
        //Find the users name
        EditText nameField  = (EditText)findViewById(R.id.name);
        String name = nameField.getText().toString();

        int price=calculatePrice();
        String priceMessage = createOrderSummary(name ,price);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}