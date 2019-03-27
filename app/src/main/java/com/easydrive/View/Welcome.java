package com.easydrive.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.easydrive.Model.User;
import com.easydrive.R;
import com.easydrive.fragment.MapsActivity;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import dmax.dialog.SpotsDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Welcome extends AppCompatActivity {
    KenBurnsView kenBurnsView;
    ConstraintLayout rootLayout;

    TextView btnSignin,btnSignup;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference users;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/Arkhip_font.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_welcome);
        kenBurnsView = (KenBurnsView) findViewById(R.id.image);
        rootLayout = (ConstraintLayout) findViewById(R.id.rootLayout);
        btnSignin = (TextView) findViewById(R.id.textView2);
        btnSignup = (TextView) findViewById(R.id.textView4);

        //init firebase;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        users = firebaseDatabase.getReference("Users");
    }

    public void btnlogin(View view) {
        showLoginDialog();

//        Intent intent = new Intent(this, Login.class);
//        startActivity(intent);
    }

    private void showLoginDialog() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("SIGN IN");
        alertDialog.setMessage("Please use email to Login  ");

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View login_layout = layoutInflater.inflate(R.layout.layout_login, null);

        final MaterialEditText edtemail = login_layout.findViewById(R.id.edtemail);
        final MaterialEditText edtpass = login_layout.findViewById(R.id.edtpass);


        alertDialog.setView(login_layout);

        //set Button

        alertDialog.setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                dialog.dismiss();
            //set disable button
                btnSignin.setEnabled(false);

                // check for validation
                if (TextUtils.isEmpty(edtemail.getText().toString())) {
                    Snackbar.make(rootLayout, "Please Login with email", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(edtpass.getText().toString())) {
                    Snackbar.make(rootLayout, "Please Login with Password", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (edtpass.getText().toString().length() < 6) {
                    Snackbar.make(rootLayout, "Password too short", Snackbar.LENGTH_LONG).show();
                    return;
                }
                final SpotsDialog waitingDialog = new SpotsDialog(Welcome.this);
                waitingDialog.show();
                //login
                firebaseAuth.signInWithEmailAndPassword(edtemail.getText().toString(), edtpass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                waitingDialog.dismiss();
                                startActivity(new Intent(Welcome.this, MapsActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                waitingDialog.dismiss();
                                Snackbar.make(rootLayout, "Failed" + e.getMessage(), Snackbar.LENGTH_LONG).show();

                                //Active
                                btnSignin.setEnabled(true);
                            }
                        });
                alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

            }


        });

        alertDialog.show();


    }


    public void btnregister(View view) {
        showRegisterDialog();

//        Intent intent = new Intent(this, Register.class);
//        startActivity(intent);
    }

    private void showRegisterDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Register");
        alertDialog.setMessage("Please use email to register  ");

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View register_layout = layoutInflater.inflate(R.layout.layout_register, null);

        final MaterialEditText edtemail = register_layout.findViewById(R.id.edtemail);
        final MaterialEditText edtpass = register_layout.findViewById(R.id.edtpass);
        final MaterialEditText edtphone = register_layout.findViewById(R.id.edtPhone);
        final MaterialEditText edtName = register_layout.findViewById(R.id.edtname);

        alertDialog.setView(register_layout);

        //set Button

        alertDialog.setPositiveButton("Register", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                dialog.dismiss();

                // check for validation
                if (TextUtils.isEmpty(edtemail.getText().toString())) {
                    Snackbar.make(rootLayout, "Please Register with email", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(edtpass.getText().toString())) {
                    Snackbar.make(rootLayout, "Please Register with Password", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (edtpass.getText().toString().length() < 6) {
                    Toast.makeText(getApplicationContext(),"Password too short",Toast.LENGTH_LONG).show();
                    //Snackbar.make(rootLayout, "Password too short", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(edtphone.getText().toString())) {
                    Toast.makeText(getApplicationContext(),"Please Register with Phone Number",Toast.LENGTH_LONG).show();
                    //Snackbar.make(rootLayout, "Please Register with Phone Number", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(edtName.getText().toString())) {
                    Toast.makeText(getApplicationContext(),"Please Register with Name",Toast.LENGTH_LONG).show();
                    //Snackbar.make(rootLayout, "Please Register with Name", Snackbar.LENGTH_LONG).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(edtemail.getText().toString(), edtpass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // save User to db
                        User user = new User();
                        user.setEmail(edtemail.getText().toString());
                        user.setEmail(edtpass.getText().toString());
                        user.setEmail(edtName.getText().toString());
                        user.setEmail(edtphone.getText().toString());

                        //use email to key

                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                       // Toast.makeText(getApplicationContext(),"Successfully Registered",Toast.LENGTH_LONG).show();
                                        Snackbar.make(rootLayout, "Register successfully", Snackbar.LENGTH_LONG).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                       // Toast.makeText(getApplicationContext(),"Failed"+e.getMessage(),Toast.LENGTH_LONG).show();

                                        Snackbar.make(rootLayout, "Failed " + e.getMessage(), Snackbar.LENGTH_LONG).show();
                                    }
                                });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       // Toast.makeText(getApplicationContext(),"Failed"+e.getMessage(),Toast.LENGTH_LONG).show();

                        Snackbar.make(rootLayout, "Failed " + e.getMessage(), Snackbar.LENGTH_LONG).show();

                    }
                });
               // alertDialog.show();
            }
        });

        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });

        alertDialog.show();
    }
}
