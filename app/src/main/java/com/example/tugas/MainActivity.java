package com.example.tugas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Button bttnDaftar, bttnKembali;
    AlertDialog dialog;
    LayoutInflater inflater;
    View dialogView;
    RadioGroup radioGroupJK, radioGroupAgama;
    RadioButton radioButtonJK, radioButtonAgama;
    TextInputEditText namaDepan, tglLahir, namaBelakang, tempatLahir, alamat, telephone, email, password, repassword;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroupAgama = (RadioGroup) findViewById(R.id.Buttonagama);
        radioGroupJK = (RadioGroup) findViewById(R.id.jenisKelaminRB);
        tglLahir = (TextInputEditText) findViewById(R.id.tglLahir);
        namaDepan = (TextInputEditText) findViewById(R.id.namaDepan);
        namaBelakang = (TextInputEditText) findViewById(R.id.namaBelakang);
        tempatLahir = (TextInputEditText) findViewById(R.id.tempatLahir);
        alamat = (TextInputEditText) findViewById(R.id.alamat);
        telephone = (TextInputEditText) findViewById(R.id.telephone);
        email = (TextInputEditText) findViewById(R.id.email);
        password = (TextInputEditText) findViewById(R.id.password);
        repassword = (TextInputEditText) findViewById(R.id.rePassword);
        bttnDaftar = findViewById(R.id.bttnDaftar);
        bttnKembali = findViewById(R.id.bttnKembali);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.namaDepan,
                RegexTemplate.NOT_EMPTY, R.string.empty_name);

        awesomeValidation.addValidation(this, R.id.namaBelakang,
                RegexTemplate.NOT_EMPTY, R.string.empty_name2);

        awesomeValidation.addValidation(this, R.id.tempatLahir,
                RegexTemplate.NOT_EMPTY, R.string.empty_place);

        awesomeValidation.addValidation(this, R.id.tglLahir,
                RegexTemplate.NOT_EMPTY, R.string.empty_date);

        awesomeValidation.addValidation(this, R.id.alamat,
                RegexTemplate.NOT_EMPTY, R.string.empty_address);

        awesomeValidation.addValidation(this, R.id.telephone,
                RegexTemplate.NOT_EMPTY, R.string.empty_phone);

        awesomeValidation.addValidation(this, R.id.email,
                RegexTemplate.NOT_EMPTY, R.string.empty_email);

        awesomeValidation.addValidation(this, R.id.password,
                RegexTemplate.NOT_EMPTY, R.string.empty_password);

        if(password.getText().equals("")) {
            awesomeValidation.addValidation(this, R.id.password,
                    RegexTemplate.NOT_EMPTY, R.string.empty_password);

        }
        else if (password.length() < 8 && password.length() > 0) {
            awesomeValidation.addValidation(this, R.id.password,
                    ".{8,}", R.string.invalid_password);
        }

        bttnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedIdJK = radioGroupJK.getCheckedRadioButtonId();
                radioButtonJK = (RadioButton) findViewById(selectedIdJK);
                int selectedIdAgama = radioGroupAgama.getCheckedRadioButtonId();
                radioButtonAgama = (RadioButton) findViewById(selectedIdAgama);
                awesomeValidation.validate();
                if (awesomeValidation.validate() == true){
                    if (radioButtonJK == null) {
                        Toast.makeText(getApplicationContext(), "Harap Isi Jenis Kelamin", Toast.LENGTH_LONG).show();
                    }
                    else if (radioButtonAgama == null) {
                        Toast.makeText(getApplicationContext(), "Harap Isi Agama", Toast.LENGTH_LONG).show();
                    }
                    else{
                        alertDIalog();
                    }
                }
                else {

                }
            }
        });

        bttnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });

        tglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH)
                        , myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

    }
    private void updateLabel () {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tglLahir.setText(sdf.format(myCalendar.getTime()));
    }

    private void alertDIalog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Konfirmasi...");
        builder.setMessage("Apakah data yang anda masukkan sudah benar?");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(true);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DialogForm();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void DialogForm() {

        dialog = new AlertDialog.Builder(MainActivity.this).create();
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_data, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Detail Pendaftaran");

        TextView namaDepan2 = dialogView.findViewById(R.id.namaDepan2);
        TextView namaBelakang2 = dialogView.findViewById(R.id.namaBelakang2);
        TextView tTl = dialogView.findViewById(R.id.tempatLahir2);
        TextView Alamat = dialogView.findViewById(R.id.alamat2);
        TextView jenisKelamin2 = dialogView.findViewById(R.id.JK2);
        TextView Agama = dialogView.findViewById(R.id.Agama);
        TextView noTelepon = dialogView.findViewById(R.id.telephone2);
        TextView txtEmail = dialogView.findViewById(R.id.email2);
        int selectedIdJK = radioGroupJK.getCheckedRadioButtonId();
        radioButtonJK = (RadioButton) findViewById(selectedIdJK);
        int selectedIdAgama = radioGroupAgama.getCheckedRadioButtonId();
        radioButtonAgama = (RadioButton) findViewById(selectedIdAgama);

        Button bttnKeluar = dialogView.findViewById(R.id.bttnKeluar);
        Button bttnLanjut = dialogView.findViewById(R.id.bttnLanjut);

        namaDepan2.setText(namaDepan.getText());
        namaBelakang2.setText(namaBelakang.getText());
        tTl.setText(tempatLahir.getText() + ", " + tglLahir.getText());
        Alamat.setText(alamat.getText());
        jenisKelamin2.setText(radioButtonJK.getText().toString());
        Agama.setText(radioButtonAgama.getText().toString());
        noTelepon.setText(telephone.getText());
        txtEmail.setText(email.getText());
        dialog.setView(dialogView);
        dialog.show();

        bttnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Pendaftaran Berhasil", Toast.LENGTH_LONG).show();
            }
        });

        bttnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });
    }
}