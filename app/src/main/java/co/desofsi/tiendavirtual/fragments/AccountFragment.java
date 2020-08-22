package co.desofsi.tiendavirtual.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.desofsi.tiendavirtual.R;
import co.desofsi.tiendavirtual.activities.ChangeasswordActivity;
import co.desofsi.tiendavirtual.activities.EditProfileActivity;
import co.desofsi.tiendavirtual.activities.HomeActivity;
import co.desofsi.tiendavirtual.activities.ListCategoriesActivity;
import co.desofsi.tiendavirtual.deliveryactivities.HomeDeliveryActivity;
import co.desofsi.tiendavirtual.init.AuthActivity;
import co.desofsi.tiendavirtual.models.User;
import co.desofsi.tiendavirtual.routes.Routes;
import de.hdodenhof.circleimageview.CircleImageView;


public class AccountFragment extends Fragment {
    private View view;
    private TextView txt_name, txt_address, txt_phone, txt_ci, txt_email;
    private Button btn_logout, btn_edit;
    private ImageView btn_edit_pass;
    private CircleImageView img_user;
    private ProgressDialog dialog;
    SharedPreferences sharedPreferences;
    private User user_login;

    public AccountFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        init();
        eventsButtons();
        getProfile();
        return view;
    }

    public void init() {
        dialog = new ProgressDialog(getContext());
        user_login = new User();
        dialog.setCancelable(false);
        sharedPreferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        txt_name = view.findViewById(R.id.account_fragment_txt_name);
        txt_address = view.findViewById(R.id.account_fragment_txt_address);
        txt_ci = view.findViewById(R.id.account_fragment_txt_ci);
        txt_phone = view.findViewById(R.id.account_fragment_txt_phone);
        txt_email = view.findViewById(R.id.account_fragment_txt_email);

        btn_edit = view.findViewById(R.id.account_fragment_btn_edit);
        btn_edit_pass = view.findViewById(R.id.account_fragment_btn_edit_pass);
        btn_logout = view.findViewById(R.id.account_fragment_btn_logout);
        txt_address = view.findViewById(R.id.account_fragment_txt_address);
        img_user = view.findViewById(R.id.account_fragment_image);
    }

    public void eventsButtons() {
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(((HomeActivity) getContext()));
                builder.setMessage("¿Desea cerrar sesión?")
                        .setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                logout();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        btn_edit_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ChangeasswordActivity.class));
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                intent.putExtra("user", user_login);
                startActivity(intent);
            }
        });
    }

    public void logout() {
        dialog.setMessage("Cerrando sesión");
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Routes.LOGOUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("success")) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                startActivity(new Intent(((HomeActivity) getContext()), AuthActivity.class));
                                ((HomeActivity) getContext()).finish();
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
                        Toast.makeText(getContext(), "Las credenciales no coinciden", Toast.LENGTH_LONG).show();
                        System.out.println(error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = sharedPreferences.getString("token", "");
                Map<String, String> map = new HashMap<String, String>();
                map.put("Authorization", "Bearer " + token);
                return map;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void getProfile() {
        dialog.setMessage("Cargando");
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Routes.GET_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("success")) {
                                JSONObject profile = object.getJSONObject("profile");
                                JSONObject user = object.getJSONObject("user");

                                User user_profile = new User();
                                user_profile.setId(profile.getInt("id"));
                                user_profile.setId_customer(profile.getInt("id_user"));
                                user_profile.setCi(profile.getString("ci"));
                                user_profile.setRuc(profile.getString("ruc"));
                                user_profile.setName(profile.getString("name"));
                                user_profile.setLast_name(profile.getString("last_name"));
                                user_profile.setAddress(profile.getString("address"));
                                user_profile.setPhone(profile.getString("phone"));
                                user_profile.setEmail(profile.getString("email"));
                                user_profile.setUrl_image(user.getString("url_image"));

                                user_login = user_profile;
                                loadProfile(user_profile);

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
                        Toast.makeText(getContext(), "Las credenciales no coinciden", Toast.LENGTH_LONG).show();
                        System.out.println(error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = sharedPreferences.getString("token", "");
                Map<String, String> map = new HashMap<String, String>();
                map.put("Authorization", "Bearer " + token);
                return map;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void loadProfile(User user) {

        txt_name.setText(user.getName() + " " + user.getLast_name());
        txt_ci.setText(user.getCi());
        txt_address.setText(user.getAddress());
        txt_email.setText(user.getEmail());
        txt_phone.setText(user.getPhone());
        if (!user.getUrl_image().equals("")) {
            Picasso.get().load(Routes.URL + user.getUrl_image()).into(img_user);
        }

    }
}
