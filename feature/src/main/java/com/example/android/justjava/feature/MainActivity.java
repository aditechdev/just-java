/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/
package com.example.android.justjava.feature;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    int displayPrice = 10;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view) {
        if (quantity==10){
            Toast toast = Toast.makeText(this,"UF! excess of anything is bad for health",Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
       if (quantity==1){
           Toast toast = Toast.makeText(this,"LOL! You cannot have negative coffee",Toast.LENGTH_SHORT);
           toast.show();
           return;
       }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    public void submitOrder(View view) {

        EditText nameEditText= (EditText) findViewById(R.id.name_text);
        String cutomerName= nameEditText.getText().toString();

        CheckBox hasWhippedCream =(CheckBox) findViewById(R.id.has_whipped_cream);
        boolean addedWhippedCream = hasWhippedCream.isChecked();
        CheckBox hasChocolate =(CheckBox) findViewById(R.id.has_chocolate);
        boolean addedChocolate = hasChocolate.isChecked();
        int price = calculatePrice(addedWhippedCream,addedChocolate);
        String priceMessage = createOrderSummary(price, addedWhippedCream, addedChocolate, cutomerName);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order For " +cutomerName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean addedWhipedCream, boolean addedChocolate) {
      int basePrice =5;
      if (addedWhipedCream==true){
          basePrice = basePrice+1;
      }
      if (addedChocolate==true){
          basePrice = basePrice+2;
      }
      return quantity*basePrice;
    }

    public String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate,String customer) {
        String priceMessage = ""+getText(R.string.customer_name) +customer;
        priceMessage += "\n"+getText(R.string.whipped_cream_summary) +addWhippedCream;
        priceMessage += "\n"+getText(R.string.chocolate_summary) +addChocolate;
        priceMessage = priceMessage + "\n"+getText(R.string.quantity_summary) + quantity;
        priceMessage = priceMessage + "\nTotal: ₹ " + price;
        NumberFormat.getCurrencyInstance().format(price);
        priceMessage = priceMessage + "\n"+getText(R.string.thanks);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    public void displayQuantity(int quant) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quant);
    }
}