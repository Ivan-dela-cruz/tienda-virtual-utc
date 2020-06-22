package co.desofsi.tiendavirtual.activities;

import androidx.appcompat.app.AppCompatActivity;
import co.desofsi.tiendavirtual.R;
import co.desofsi.tiendavirtual.data.Constant;
import co.desofsi.tiendavirtual.models.Category;
import co.desofsi.tiendavirtual.models.Product;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ShowProductActivity extends AppCompatActivity {

    private Product product;
    private Category category;
    private SharedPreferences sharedPreferences;

    private ImageView imageView_product;
    private Button btn_addcar_shop;
    private TextView text_name, text_description, textView_cantidad, text_price;
    private ImageButton btn_add_item, btn_min_item;
    private int cant = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        init();

    }

    private void init() {
        product = (Product) getIntent().getExtras().getSerializable("product_selected");
        imageView_product = findViewById(R.id.show_product_image);
        btn_addcar_shop = findViewById(R.id.show_product_btn_add_car_shop);
        text_name = findViewById(R.id.show_product_name);
        text_description = findViewById(R.id.show_product_description);
        text_price = findViewById(R.id.show_product_price);
        textView_cantidad = findViewById(R.id.show_product_text_cantidad);
        btn_add_item = findViewById(R.id.show_product_btn_add);
        btn_min_item = findViewById(R.id.show_product_btn_min);

        //RENDERIZAR LOS DATOS

        Picasso.get().load(Constant.URL + product.getUrl_image()).into(imageView_product);
        text_name.setText(product.getName());
        text_description.setText(product.getDescription());
        text_price.setText("$ "+product.getSale_price());
        textView_cantidad.setText(String.valueOf(cant));

        btn_min_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cant = Integer.parseInt(textView_cantidad.getText().toString());
                    if (cant > 0) {
                        cant--;
                        textView_cantidad.setText(String.valueOf(cant));
                    }

                } catch (Exception e) {

                }

            }
        });
        btn_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cant = Integer.parseInt(textView_cantidad.getText().toString());
                    cant++;
                    textView_cantidad.setText(String.valueOf(cant));


                } catch (Exception e) {

                }

            }
        });


    }
}
