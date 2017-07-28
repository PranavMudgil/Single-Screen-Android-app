/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 */
package com.example.android.testapp;


import android.content.Context;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;

import static android.R.attr.checked;
import static android.R.attr.duration;
import static android.R.attr.id;
import static android.R.attr.name;
import static android.R.attr.order;
import static android.R.attr.value;
import static android.R.id.message;
import static android.os.Build.VERSION_CODES.M;
import static android.widget.Toast.makeText;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    int quantity = 2, price = 0;
   public void displayPrice(int num) {
        TextView showPrice = (TextView) findViewById(R.id.order_summary_text_view);
        showPrice.setText(""+num);
   }


    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this,"too much coffees ",Toast.LENGTH_LONG).show();
        }
            else{
                quantity = quantity + 1;
                displayQuantity(quantity);

            }

    }


    public void decrement(View view) {


       if(quantity==1) {
           Toast.makeText(this,"cant produce -1 coffee....duh!",Toast.LENGTH_LONG).show();
           return;
       }
            quantity = quantity - 1;
            displayQuantity(quantity);

        

    }






    //This method displays the given quantity value on the screen.

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
public String createOrderSummary(String name,boolean addWhippedCream,boolean addChocolate,int quantity,int price) {
    String priceMessage = getString(R.string.order_summary_username,name);
    priceMessage += "\n"+getString(R.string.whippedcream) + addWhippedCream;
    priceMessage += "\n"+getString(R.string.chocolate)+":" + addChocolate;
    priceMessage += "\n"+getString(R.string.order_summary_quantity,quantity);
    priceMessage += "\nPrice:" + price;
    priceMessage += "\n"+ getString(R.string.thank);

    return priceMessage;
}





    public void submitOrder(View view) {

        EditText username = (EditText) findViewById(R.id.name_text_view);
       String name=  username.getText().toString();
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();
        price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage= createOrderSummary(name,hasWhippedCream,hasChocolate,quantity,price);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT,"Coffee Order for:"+name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

       displayMessage(priceMessage);
    }

    int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int baseprice = 5;
        if (addChocolate) {
            baseprice += 2;
        }
        if (addWhippedCream) {
            baseprice += 1;
        }
        displayPrice(quantity * baseprice);
        return(quantity*baseprice);
    }



    }


