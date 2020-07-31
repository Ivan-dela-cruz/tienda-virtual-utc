package co.desofsi.tiendavirtual.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import co.desofsi.tiendavirtual.R;

public class ChangeasswordActivity extends AppCompatActivity {

    private Button btn_save;
    private TextInputEditText password, confirm_password;
    private TextInputLayout password_ly, cofirm_password_ly;
    private ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeassword);
        init();
        eventButtons();
    }

    private void init() {
        btn_save = findViewById(R.id.edit_change_btn_save);
        btn_back = findViewById(R.id.edit_change_btn_back);
        password = findViewById(R.id.edit_change_pass);
        confirm_password = findViewById(R.id.edit_change_con_pass);
        password_ly = findViewById(R.id.edit_change_pass_ly);
        cofirm_password_ly = findViewById(R.id.edit_change_con_pass_ly);
    }

    private void eventButtons() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChangeasswordActivity.this,HomeActivity.class));

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