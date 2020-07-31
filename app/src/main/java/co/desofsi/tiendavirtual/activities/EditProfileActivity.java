package co.desofsi.tiendavirtual.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import co.desofsi.tiendavirtual.R;

public class EditProfileActivity extends AppCompatActivity {

    private TextView txt_btn_image;
    private Button btn_save;
    private ImageButton btn_back;
    private ImageView img_user;
    private TextInputLayout ly_name, ly_last_name, ly_phone, ly_movil;
    private TextInputEditText name, last_name, phone, movil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
        eventButtos();
    }

    private void init() {
        txt_btn_image = findViewById(R.id.edit_profile_txt_img);
        btn_save = findViewById(R.id.edit_profile_btn_save);
        btn_back = findViewById(R.id.edit_profile_btn_back);
        img_user = findViewById(R.id.edit_profile_img);
        ly_name = findViewById(R.id.edit_profile_name_ly);
        ly_last_name = findViewById(R.id.edit_profile_last_name_ly);
        ly_movil = findViewById(R.id.edit_profile_movil_ly);
        ly_phone = findViewById(R.id.edit_profile_phone_ly);
        name = findViewById(R.id.edit_profile_name);
        last_name = findViewById(R.id.edit_profile_last_name);
        movil = findViewById(R.id.edit_profile_movil);
        phone = findViewById(R.id.edit_profile_phone);
    }

    public void eventButtos() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(EditProfileActivity.this, HomeActivity.class));
            }
        });
        txt_btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}