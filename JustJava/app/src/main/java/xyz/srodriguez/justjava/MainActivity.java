package xyz.srodriguez.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 1;

    private int calculatePrice(boolean addMilk, Boolean addChocolate){
        int basePrice = 5;
        if (addMilk) {
            basePrice = basePrice + 1;
        }
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Name
        EditText nameField = (EditText)findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        // Has Milk?
        CheckBox milkCheckBox = (CheckBox) findViewById(R.id.milk_checkbox);
        boolean hasMilk = milkCheckBox.isChecked();

        // Has Chocolate?
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Output Summary
        int price = calculatePrice(hasMilk, hasChocolate);
        String priceMessage = createOrderSummary(name, hasMilk, hasChocolate, price);

        // Send Email
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:seb646@outlook.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String createOrderSummary(String name, boolean addMilk, boolean addChocolate, int price) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nQuantity: " + quantity + " cups";
        priceMessage += "\nAdd milk? " + addMilk;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\n\nThank you!";
        return priceMessage;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1){
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number + " cups");
    }
}