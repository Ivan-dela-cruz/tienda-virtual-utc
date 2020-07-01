package co.desofsi.tiendavirtual.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import co.desofsi.tiendavirtual.R;
import co.desofsi.tiendavirtual.activities.HomeActivity;
import co.desofsi.tiendavirtual.data.Constant;
import co.desofsi.tiendavirtual.init.AuthActivity;


public class SingUpFragment extends Fragment {
    private View view;
    private TextInputLayout layout_email, layout_password,layout_cofirm_pass;
    private TextInputEditText txt_email, txt_password,txt_confirm_pass;
    private Button btn_sin_in;
    private TextView txt_sing_in;
    private ProgressDialog dialog;

    public SingUpFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sing_up_layout,container,false);
        init();
        return view;
    }

    public void init() {
        layout_email = view.findViewById(R.id.text_email_layout_sing_up);
        layout_password = view.findViewById(R.id.text_password_layout_sing_up);
        layout_cofirm_pass = view.findViewById(R.id.text_confirm_password_layout_sing_up);
        txt_email = view.findViewById(R.id.text_email_sing_up);
        txt_password = view.findViewById(R.id.text_password_sing_up);
        txt_confirm_pass =  view.findViewById(R.id.text_confirm_password_sing_up);
        btn_sin_in = view.findViewById(R.id.btn_sing_up);
        txt_sing_in = view.findViewById(R.id.text_sing_up);
        dialog =  new ProgressDialog(getContext());
        dialog.setCancelable(false);

        txt_sing_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_auth_container, new SingInFragment()).commit();
            }
        });
        btn_sin_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validated()) {
                    Register();
                }

            }
        });
        txt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txt_email.getText().toString().isEmpty()) {
                    layout_email.setErrorEnabled(false);


                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_password.getText().toString().length()>7) {
                    layout_password.setErrorEnabled(false);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_confirm_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txt_confirm_pass.getText().toString().equals(txt_password.getText().toString())) {
                    layout_cofirm_pass.setErrorEnabled(false);


                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public boolean validated() {

        if (txt_email.getText().toString().isEmpty()) {
            layout_email.setErrorEnabled(true);
            layout_email.setError("El correo es obligatorio");
            return false;
        }
        if (txt_password.getText().toString().length()<7) {
            layout_password.setErrorEnabled(true);
            layout_password.setError("La contraseña debe tener al menos 8 carácteres");
            return false;
        }
        if (!txt_confirm_pass.getText().toString().equals(txt_password.getText().toString())) {
            layout_cofirm_pass.setErrorEnabled(true);
            layout_cofirm_pass.setError("Las contraseñas no son iguales");
            return false;
        }
        return true;

    }
    private void Register() {
        dialog.setMessage("Registrando");
        dialog.show();
        final String email = txt_email.getText().toString().trim();
        final String password = txt_password.getText().toString().trim();

        System.out.println(Constant.REGISTER);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("success")) {
                                JSONObject user = object.getJSONObject("user");
                                SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
                                SharedPreferences.Editor editor = userPref.edit();

                                editor.putString("token", object.getString("token"));
                                editor.putString("name", user.getString("name"));
                                editor.putString("username", user.getString("username"));
                                editor.putString("url_image", user.getString("url_image"));
                                editor.putBoolean("isLoggedIn",true);
                                editor.apply();


                                startActivity(new Intent(((AuthActivity)getContext()), HomeActivity.class));
                                ((AuthActivity)getContext()).finish();
                                Toast.makeText(getContext(), "Conectado", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(getContext(),"Las credenciales no coinciden",Toast.LENGTH_LONG ).show();
                        System.out.println(error);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("email",email);
                map.put("password",password);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
