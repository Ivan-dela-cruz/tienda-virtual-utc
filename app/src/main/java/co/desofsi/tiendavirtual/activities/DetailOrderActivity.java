package co.desofsi.tiendavirtual.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.desofsi.tiendavirtual.R;
import co.desofsi.tiendavirtual.adapters.DetailListProductstAdapter;
import co.desofsi.tiendavirtual.adapters.ListProductstAdapter;
import co.desofsi.tiendavirtual.data.Constant;
import co.desofsi.tiendavirtual.init.AuthActivity;
import co.desofsi.tiendavirtual.models.DetailOrder;

public class DetailOrderActivity extends AppCompatActivity {

    public static TextView txt_total;
    private Button btn_confirm;
    public static RecyclerView recycler_items;
    private ImageButton btn_back;
    public static LinearLayout liner_btn;
    public static RelativeLayout relative_empty;
    private ProgressDialog dialog;
    private SharedPreferences userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        init();
        loadDetail();

        eventsButtons();
    }

    public void init() {
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        txt_total = findViewById(R.id.detail_order_txt_total);
        btn_confirm = findViewById(R.id.detail_order_btn_confirm);
        recycler_items = findViewById(R.id.detail_order_recycler_items);
        btn_back = findViewById(R.id.detail_order_btn_back);
        liner_btn = findViewById(R.id.detail_bottom);
        relative_empty = findViewById(R.id.empty_concept);
        dialog = new ProgressDialog(DetailOrderActivity.this);
        dialog.setCancelable(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(DetailOrderActivity.this, LinearLayoutManager.VERTICAL, false);
        recycler_items.setLayoutManager(mLayoutManager);
        txt_total.setText("$ " + loadTotalPay());


        if (ListCategoriesActivity.list_detail.size() == 0) {
            liner_btn.setVisibility(View.GONE);
            relative_empty.setVisibility(View.VISIBLE);
        }


    }


    private void postOrder() {
        dialog.setMessage("Enviando");
        dialog.show();
       // final String id_user = String.valueOf(ListCategoriesActivity.order.getId_user());
        final String id_company = String.valueOf(ListCategoriesActivity.order.getId_company());
        final String total_order = ListCategoriesActivity.order.getTotal();

        final JSONArray array = new JSONArray();
        int i = 0;
        for (DetailOrder detailOrder : ListCategoriesActivity.list_detail) {
            JSONObject object = new JSONObject();
            try {
                object.put("id_product", detailOrder.getId_product());
                object.put("name", detailOrder.getProduct_name());
                object.put("description", detailOrder.getProduct_desc());
                object.put("quanty", detailOrder.getCant());
                object.put("total_price", detailOrder.getPrice_total());
                object.put("price_unit", detailOrder.getPrice_unit());

            } catch (Exception e) {

            }
            array.put(object);
            i++;
        }
        //System.out.println("ARRAY ENVIADO ===>> \n" + array.toString());
        //System.out.println("ENCABEZAADO ENVIADO ===>> \n" +" = "+id_company+" = "+total_order);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.SEND_ORDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("success")) {

                                startActivity(new Intent(DetailOrderActivity.this, ReviewOrderActivity.class));
                                finish();
                                Toast.makeText(DetailOrderActivity.this, "Enviado", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(DetailOrderActivity.this, "Error al enviar el pedido", Toast.LENGTH_SHORT).show();

                        System.out.println(error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = userPref.getString("token", "");
                Map<String, String> map = new HashMap<String, String>();
                map.put("Authorization", "Bearer " + token);
                return map;
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("id_company", id_company);
                map.put("total_order", total_order);
                map.put("detail_order", array.toString());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DetailOrderActivity.this);
        requestQueue.add(stringRequest);


    }

    public static double loadTotalPay() {
        double total = 0;
        try {
            if (ListCategoriesActivity.list_detail.size() > 0) {
                for (DetailOrder detailOrder : ListCategoriesActivity.list_detail) {
                    total += Double.parseDouble(detailOrder.getPrice_total());
                }
            }
            ListCategoriesActivity.order.setTotal("" + total);
            return total;
        } catch (Exception e) {
            return total;
        }


    }

    public void loadDetail() {
        DetailListProductstAdapter listProductstAdapter = new DetailListProductstAdapter(DetailOrderActivity.this, ListCategoriesActivity.list_detail);
        recycler_items.setAdapter(listProductstAdapter);
    }

    public void eventsButtons() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postOrder();
                // startActivity(new Intent(DetailOrderActivity.this, ReviewOrderActivity.class));
            }
        });
    }
}