package co.desofsi.tiendavirtual.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import co.desofsi.tiendavirtual.R;
import co.desofsi.tiendavirtual.fragments.AccountFragment;
import co.desofsi.tiendavirtual.fragments.HomeFragment;
import co.desofsi.tiendavirtual.fragments.NotificationFragment;
import co.desofsi.tiendavirtual.fragments.OrderFragment;
import co.desofsi.tiendavirtual.fragments.MerchantHomeFragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class HomeActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private final static int ID_HOME = 1;
    private final static int ID_EXPLORE = 2;
    private final static int ID_MESSAGE = 3;
    private final static int ID_NOTIFICATION = 4;
    private final static int ID_ACCOUNT = 5;
    LocationManager localizar;
    Location llocaliza;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentManager = getSupportFragmentManager();


        final TextView tvSelected = findViewById(R.id.tv_selected);
        tvSelected.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/SourceSansPro-Regular.ttf"));

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.ic_home_black_24dp));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_EXPLORE, R.drawable.ic_baseline_shopping_cart_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_MESSAGE, R.drawable.ic_baseline_directions_bike_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_NOTIFICATION, R.drawable.ic_notifications_black_24dp));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_ACCOUNT, R.drawable.ic_account_circle_black_24dp));

        bottomNavigation.setCount(ID_NOTIFICATION, "115");

        getPositionUser();


        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                // Toast.makeText(HomeActivity.this, "clicked item : " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //   Toast.makeText(HomeActivity.this, "showing item : " + item.getId(), Toast.LENGTH_SHORT).show();

                String name;
                switch (item.getId()) {
                    case ID_HOME:
                        name = "HOME";

                        fragmentManager.beginTransaction().replace(R.id.home_frame_container,new HomeFragment(),HomeFragment.class.getSimpleName()).commit();
                        break;
                    case ID_EXPLORE:
                        name = "EXPLORE";
                        fragmentManager.beginTransaction().replace(R.id.home_frame_container,new OrderFragment(),OrderFragment.class.getSimpleName()).commit();

                        break;
                    case ID_MESSAGE:
                        name = "MESSAGE";
                        fragmentManager.beginTransaction().replace(R.id.home_frame_container,new MerchantHomeFragment(), MerchantHomeFragment.class.getSimpleName()).commit();

                        break;
                    case ID_NOTIFICATION:
                        name = "NOTIFICATION";
                        fragmentManager.beginTransaction().replace(R.id.home_frame_container,new NotificationFragment(),NotificationFragment.class.getSimpleName()).commit();

                        break;
                    case ID_ACCOUNT:
                        name = "ACCOUNT";
                        fragmentManager.beginTransaction().replace(R.id.home_frame_container,new AccountFragment(),AccountFragment.class.getSimpleName()).commit();

                        break;
                    default:
                        name = "";
                }
                tvSelected.setText(getString(R.string.main_page_selected, name));
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                //Toast.makeText(HomeActivity.this, "reselected item : " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setCount(ID_NOTIFICATION, "115");

        bottomNavigation.show(ID_HOME,true);




    }
    public void getPositionUser() {

        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            Toast.makeText(HomeActivity.this, "no hay permiso", Toast.LENGTH_LONG).show();
        else {
            localizar = (LocationManager) HomeActivity.this.getSystemService(Context.LOCATION_SERVICE);
            llocaliza = localizar.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (llocaliza != null) {
                //  txt_lon.setText(String.valueOf(llocaliza.getLongitude()));
                // txt_lati.setText(String.valueOf(llocaliza.getLatitude()));
                //Toast.makeText(MapsActivity.this, " ubicacion  " + llocaliza.getLatitude() + " , " + llocaliza.getLongitude(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(HomeActivity.this, "no se obtuvo tu ubicación", Toast.LENGTH_LONG).show();
            }
        }

    }
}
