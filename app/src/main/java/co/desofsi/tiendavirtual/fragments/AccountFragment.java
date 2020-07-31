package co.desofsi.tiendavirtual.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import co.desofsi.tiendavirtual.R;
import co.desofsi.tiendavirtual.activities.ChangeasswordActivity;
import co.desofsi.tiendavirtual.activities.EditProfileActivity;
import de.hdodenhof.circleimageview.CircleImageView;


public class AccountFragment extends Fragment {
    private View view;
    private TextView txt_name, txt_address, txt_phone, txt_movil;
    private Button btn_logout, btn_edit;
    private ImageView btn_edit_pass;
    private CircleImageView img_user;
    public AccountFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        init();
        eventsButtons();
        return view;
    }
    public void init(){
        txt_address = view.findViewById(R.id.account_fragment_txt_name);
        txt_address = view.findViewById(R.id.account_fragment_txt_address);
        txt_movil = view.findViewById(R.id.account_fragment_txt_movil);
        txt_phone = view.findViewById(R.id.account_fragment_txt_phone);

        btn_edit = view.findViewById(R.id.account_fragment_btn_edit);
        btn_edit_pass = view.findViewById(R.id.account_fragment_btn_edit_pass);
        btn_logout = view.findViewById(R.id.account_fragment_btn_logout);
        txt_address = view.findViewById(R.id.account_fragment_txt_address);
    }
    public void eventsButtons(){
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });
    }
}
