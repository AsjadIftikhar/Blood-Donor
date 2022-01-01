package com.example.blooddonors;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddForm extends AppCompatActivity {

    private TextView f_errors;

    private EditText f_name;
    private EditText f_address;
    private EditText f_phone;

    private Spinner f_avail;
    private Spinner f_status;
    private Spinner f_bloodgroup;

    private Button f_register;
    private Button f_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);

        f_errors = findViewById(R.id.f_errors);

        f_name = findViewById(R.id.f_name);
        f_address = findViewById(R.id.f_address);
        f_phone = findViewById(R.id.f_phone);

        f_avail = findViewById(R.id.f_avail);
        f_status = findViewById(R.id.f_status);
        f_bloodgroup = findViewById(R.id.f_bloodgroup);

        f_register = findViewById(R.id.f_register);
        f_exit = findViewById(R.id.f_exit);

        f_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean status = Boolean.TRUE;
                if (f_status.getSelectedItem().toString().equals("Hidden")) {
                    status = Boolean.FALSE;
                }
                String name = f_name.getText().toString();
                String address = f_address.getText().toString();
                String phone = f_phone.getText().toString();
                String avail = f_avail.getSelectedItem().toString();
                String bloodgroup = f_bloodgroup.getSelectedItem().toString();

                if (name.equals("") || address.equals("") || phone.equals(""))
                    f_errors.setText(R.string.form_error);
                else {
                    System.out.println(avail);
                    Donor donor = new Donor(name, status, address, avail, phone, bloodgroup);
                    Intent intent = new Intent(AddForm.this, MainActivity.class);
                    intent.putExtra("donor", donor);
                    startActivity(intent);
                }


            }
        });

        f_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddForm.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}