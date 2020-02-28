package com.example.lajoya;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.lajoya.Model.Products;
import com.example.lajoya.Prevalent.Prevalent;
import com.example.lajoya.ViewHolder.OnSaleViewHolder;
import com.example.lajoya.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String cart_count;
    private DatabaseReference ProductsRef;
    private DatabaseReference cartCountRef, secCountRef;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewCategory;
    private TextView TextViewHotDeals;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView.LayoutManager layoutManager1;

    private TextView cart_badge;
    private String selection;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    private static final int REQUEST_CODE =1000 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPage);
        ImageAdapter adapterView = new ImageAdapter(this);
        mViewPager.setAdapter(adapterView);


        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 5) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);


        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        Paper.init(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        FloatingActionButton voice = findViewById(R.id.voice);
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(HomeActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        final TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        final CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);





        //change for cart count badge starts here



        cartCountRef = FirebaseDatabase.getInstance().getReference().child("Cart Quantity").child(Prevalent.currentOnlineUser.getPhone()).child("quantity");


        cartCountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()){

                    cart_badge.setVisibility(View.VISIBLE);
                    cart_count = dataSnapshot.getValue().toString();
                    cart_badge.setText(String.valueOf(cart_count));
                    int cart_countInt= Integer.valueOf((String)cart_count);

                    Log.d("vallll",cart_count);

                    if(cart_countInt==0){
                        cart_badge.setVisibility(View.INVISIBLE);
                    }
                }





                else{

                    cart_badge.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        //change for cart count badge ends here

        //change for profile image updation starts here


        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    if (dataSnapshot.child("image").exists())


                    {
                        String image = dataSnapshot.child("image").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();


                        Picasso.get().load(image).into(profileImageView);
                        userNameTextView.setText(name);



                    }
                    else{
                        if (dataSnapshot.child("name").exists() && dataSnapshot.child("phoneOrder").exists() && dataSnapshot.child("address").exists())
                        {
                            String name = dataSnapshot.child("name").getValue().toString();
                            userNameTextView.setText(name);

                        }


                    }
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {




            }
        });


//new change ends here


        userNameTextView.setText(Prevalent.currentOnlineUser.getName());
        Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);


        cart_badge=(TextView) findViewById(R.id.cart_badge);
        recyclerView = findViewById(R.id.recyler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewCategory=findViewById(R.id.recycler_category);
        recyclerViewCategory.setHasFixedSize(true);
        layoutManager1 = new GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false);
        recyclerViewCategory.setLayoutManager(layoutManager1);
        TextViewHotDeals= findViewById(R.id.TextViewHotDeals);
    }


    private void speak() {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak something!");

        try {
            startActivityForResult(intent,REQUEST_CODE);
        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (REQUEST_CODE){
            case REQUEST_CODE:{
                if (resultCode==RESULT_OK && null!=data){
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //          tv.setText(result.get(0));
                    String item=result.get(0).toString().trim();
                    Intent intent=new Intent(HomeActivity.this,SearchProductsActivity.class );
                    intent.putExtra("item",item);
                    startActivity(intent);
                }
                break;
            }
        }
    }

    protected void onStart() {
        super.onStart();


    FirebaseRecyclerOptions<Products> options =
            new FirebaseRecyclerOptions.Builder<Products>()
                    .setQuery(ProductsRef, Products.class)
                    .build();



        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {
                        holder.txtProductName.setText(model.getPname());
                       // holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price= " + model.getPrice() + "$");
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent= new Intent(HomeActivity.this, ProductDetailsActivity.class);
                                intent.putExtra("pid", model.getPid());
                                startActivity(intent);
                            }
                        });
                    }



                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(v);

                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();



        //----- recylcler view for on sale

        FirebaseRecyclerOptions<Products> options1 =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef.orderByChild("onSale").equalTo("yes"), Products.class)
                        .build();



        FirebaseRecyclerAdapter<Products, OnSaleViewHolder> adapter1 =
                new FirebaseRecyclerAdapter<Products, OnSaleViewHolder>(options1) {
                    @Override
                    protected void onBindViewHolder(@NonNull OnSaleViewHolder holder, int position, @NonNull final Products model) {
                        holder.txtProductName.setText(model.getPname());
                        // holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price= " + model.getPrice() + "$");
                        holder.txtProductOldPrice.setText("Was " + model.getOldPrice() + "$");
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent= new Intent(HomeActivity.this, ProductDetailsActivity.class);
                                intent.putExtra("pid", model.getPid());
                                startActivity(intent);
                            }
                        });
                    }



                    @NonNull
                    @Override
                    public OnSaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.onsaleproductlayout, parent, false);
                        OnSaleViewHolder holder1 = new OnSaleViewHolder(v1);

                        return holder1;
                    }
                };
        recyclerViewCategory.setAdapter(adapter1);
        adapter1.startListening();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //  return true;
        // }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart) {
            Intent intent= new Intent(HomeActivity.this,CartActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_orders) {

            Intent intent= new Intent(HomeActivity.this,userOrderCategoryActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_search) {
            Intent intent= new Intent(HomeActivity.this,SearchProductsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_categories) {
            final String[] categories= new String[11];
            categories[0] = "All";
            categories[1] = "Sports tShirts";
            categories[2] = "Female Dresses";
            categories[3] = "Sweathers";
            categories[4] = "Glasses";
            categories[5] = "Hats Caps";
            categories[6] = "Wallets Bags Purses";
            categories[7] = "Shoes";
            categories[8] = "HeadPhone HandFree";
            categories[9] = "Watches";
            categories[10] = "tShirts";



            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setTitle("Categories");
            builder.setSingleChoiceItems(categories, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selection= categories[i];
            }
        });

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(HomeActivity.this, "hiii"+selection,Toast.LENGTH_SHORT).show();

                    if(selection=="All") {
                        Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                    else{
                    Intent intent = new Intent(HomeActivity.this, CategoriesActivity.class);
                    intent.putExtra("selection", selection);
                    startActivity(intent);}
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

        builder.show();
        } else if (id == R.id.nav_settings) {

            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);

            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            Paper.book().destroy();
            Intent intent= new Intent(HomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
