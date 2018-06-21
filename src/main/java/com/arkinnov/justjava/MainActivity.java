/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.arkinnov.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
// this is a coffee ordering app
public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    int price = 5;
    Boolean chocolateTopping = false;
    Boolean whippedCreamTopping = false;
    int chocolateToppingPrice = 2;
    int whippedCreamToppingPrice = 1;
    String emailRecipient[] = {""};
    String emailSubject = "Coffee Order";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.whippedCreamCheckbox:
                whippedCreamTopping = checked;
                break;
            case R.id.chocolateCheckbox:
                chocolateTopping = checked;
                break;
        }
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int totalPrice = calculatePrice(price, quantity, chocolateTopping, whippedCreamTopping);
        String personName = getPersonName();
        String priceMessage = "Order Summary\nName :" + personName + " \nNo. of coffees : " + quantity + "\nBase Price per cup : Rs " + price + "\nChocolate Topping : " + chocolateTopping
                + "\nWhipped Cream Topping : " + whippedCreamTopping + "\nTotal Price : " + totalPrice;
        mailOrder(emailRecipient, priceMessage);
        displayMessage(priceMessage);

    }

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    public int calculatePrice(int price, int quantity, boolean chocolateTopping, boolean whippedCreamTopping) {
        //do the calculation here
        if (chocolateTopping) {
            price += chocolateToppingPrice;
        }
        if (whippedCreamTopping) {
            price += whippedCreamToppingPrice;
        }
        return price * quantity;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }

    public String getPersonName() {
        //capture name of the user
        EditText nameField;
        nameField = findViewById(R.id.personName);
        return nameField.getText().toString();
    }

    public void mailOrder(String[] emailRecipient, String priceMessage) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:j4jithins@gmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, emailRecipient);
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }



}