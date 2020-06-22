package co.desofsi.tiendavirtual.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.tiendavirtual.R;
import co.desofsi.tiendavirtual.activities.HomeActivity;
import co.desofsi.tiendavirtual.activities.ListCategoriesActivity;
import co.desofsi.tiendavirtual.activities.ListComapiesActivity;
import co.desofsi.tiendavirtual.data.Constant;
import co.desofsi.tiendavirtual.models.Company;
import co.desofsi.tiendavirtual.models.TypeCompany;

public class ListCompanyAdapter extends RecyclerView.Adapter<ListCompanyAdapter.ListCompaniesHolder> {

    private Context context;
    private ArrayList<Company> list;


    public ListCompanyAdapter(Context context, ArrayList<Company> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ListCompaniesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_companies_recycler, parent, false);
        return new ListCompaniesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCompaniesHolder holder, final int position) {

        final Company company = list.get(position);

        Picasso.get().load(Constant.URL + company.getUrl_merchant()).into(holder.imageView_specialty);
        holder.txt_name.setText(company.getCompany_name());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(((ListComapiesActivity)context), ListCategoriesActivity.class);
                intent.putExtra("company_selected",company);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });

        /*
         holder.btn_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("id encontrado"+company.getId());
                Intent intent =  new Intent(((HomeActivity)context), ListComapiesActivity.class);
                intent.putExtra("type_company_selected",company);
                intent.putExtra("position",position);
                context.startActivity(intent);

            }
        });
         */
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class ListCompaniesHolder extends RecyclerView.ViewHolder {

        private TextView txt_name;
        private ImageView imageView_specialty;
        private CardView cardView;

        public ListCompaniesHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.item_list_companies_name_id);
            imageView_specialty = (ImageView) itemView.findViewById(R.id.item_list_companies_image);
            cardView = itemView.findViewById(R.id.item_list_companies_cardview_id);
        }


    }
}
