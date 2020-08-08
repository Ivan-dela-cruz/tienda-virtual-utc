package co.desofsi.tiendavirtual.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.desofsi.tiendavirtual.R;
import co.desofsi.tiendavirtual.activities.HomeActivity;
import co.desofsi.tiendavirtual.activities.ListComapiesActivity;
import co.desofsi.tiendavirtual.activities.ShowOrderDetailActivity;
import co.desofsi.tiendavirtual.data.Constant;
import co.desofsi.tiendavirtual.models.DateClass;
import co.desofsi.tiendavirtual.models.Order;
import co.desofsi.tiendavirtual.models.TypeCompany;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.TypeCompanyHolder> {

    private Context context;
    private ArrayList<Order> list;


    public OrderListAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public TypeCompanyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_order_fragment, parent, false);
        return new TypeCompanyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeCompanyHolder holder, final int position) {

        final Order order = list.get(position);

        DateClass dateClass = new DateClass();

        holder.txt_name.setText(order.getName_company());
        holder.text_date.setText(dateClass.time(order.getDate())  + dateClass.dateFormatHuman(order.getDate()));
        holder.text_total.setText(" $ " + order.getTotal());
        holder.text_status.setText(order.getStatus());
        holder.btn_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(((HomeActivity) context), ShowOrderDetailActivity.class);
                intent.putExtra("order", order);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class TypeCompanyHolder extends RecyclerView.ViewHolder {

        private TextView txt_name, text_status, text_total, text_date;

        private ImageView imageView;
        private Button btn_options;
        private CardView cardView;

        public TypeCompanyHolder(@NonNull View itemView) {
            super(itemView);

            txt_name = itemView.findViewById(R.id.order_item_txt_name_company);
            text_status = itemView.findViewById(R.id.order_item_txt_status);
            text_total = itemView.findViewById(R.id.order_item_txt_total);
            text_date = itemView.findViewById(R.id.order_item_txt_date);

            imageView = (ImageView) itemView.findViewById(R.id.order_item_img);
            btn_options = itemView.findViewById(R.id.order_item_button_detail);
            cardView = itemView.findViewById(R.id.order_item_card_view);


        }


    }
}
