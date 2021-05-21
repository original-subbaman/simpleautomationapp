package com.mact.simpleautomationapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mact.simpleautomationapp.Adapters.EmailAdapter;
import com.mact.simpleautomationapp.R;
import com.mact.simpleautomationapp.Room.Entity.Email;
import com.mact.simpleautomationapp.Room.Entity.EmailCred;
import com.mact.simpleautomationapp.Room.ViewModel.EmailCredViewModel;

import java.util.ArrayList;
import java.util.List;

public class SendEmailActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "SendEmailActivity";
    public EditText email, passwd;
    private EmailCredViewModel credViewModel;
    private ArrayList<EmailCred> allEmailCred;
    private Spinner spinner;
    private EmailAdapter emailAdapter;
    private String senderEmailId, senderPasswd;
    private Button sendButton;
    private Email emailToSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        spinner = findViewById(R.id.email_spinner);
        sendButton = findViewById(R.id.email_send_btn);
        sendButton.setOnClickListener(this);

        allEmailCred = new ArrayList<>();
        credViewModel = new ViewModelProvider(this, ViewModelProvider.
                AndroidViewModelFactory.getInstance(getApplication())).get(EmailCredViewModel.class);

        Toolbar toolbar = findViewById(R.id.send_email_toolbar);
        setSupportActionBar(toolbar);

        setRoomObserver();
        setUpSpinner();

    }

    private void setRoomObserver() {
        credViewModel.getAllEmailCred().observe(this, new Observer<List<EmailCred>>() {
            @Override
            public void onChanged(List<EmailCred> emailCreds) {
                allEmailCred.clear();
                allEmailCred.addAll(emailCreds);
                if(allEmailCred.size() == 0){
                    showAddNewEmailDialog();
                }
                emailAdapter.notifyDataSetChanged();

            }
        });
    }

    private void setUpSpinner(){
        emailAdapter = new EmailAdapter(getApplicationContext(), allEmailCred);
        spinner.setAdapter(emailAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EmailCred email = (EmailCred) parent.getItemAtPosition(position);
                senderEmailId = email.getEmailID();
                senderPasswd = email.getPasswd();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        EditText toEmail = findViewById(R.id.to_email_id);
        EditText emailSubject = findViewById(R.id.email_subject);
        EditText emailBody = findViewById(R.id.email_body);

        String to = toEmail.getText().toString();
        String subject = emailSubject.getText().toString();
        String body = emailBody.getText().toString();

        if(isEmailFieldsEmpty(to, subject, body)){
            Toast.makeText(SendEmailActivity.this, R.string.error_empty_field, Toast.LENGTH_LONG).show();
        }else{
            emailToSend = new Email(senderEmailId, senderPasswd, to, subject, body);
            Intent intent = new Intent();
            intent.putExtra("email", emailToSend);
            setResult(RESULT_OK, intent);
            finish();
        }
    }



    private boolean isEmailFieldsEmpty(String to, String subject, String body){
        if(to.trim().isEmpty() || subject.trim().isEmpty() ||
            body.trim().isEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_email_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_new_email){
            //check email, id
            //show dialog if email and id are zero;
            showAddNewEmailDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddNewEmailDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_new_email_dialogue, null);
        email = view.findViewById(R.id.email_dialog_txt);
        passwd = view.findViewById(R.id.passwd_dialog_text);

        builder.setView(view)
                .setCancelable(false)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(isDialogFieldsEmpty()){
                            Toast.makeText(SendEmailActivity.this, R.string.empty_input, Toast.LENGTH_LONG).show();
                        }else {
                            //add to db
                            String emailId = email.getText().toString().trim();
                            String passWd = passwd.getText().toString().trim();
                            credViewModel.insert( new EmailCred(emailId, passWd));
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();

    }

    private boolean isDialogFieldsEmpty(){
        boolean isEmpty = false;
        if(email.getText().toString().trim().isEmpty() || passwd.getText().toString().trim().isEmpty()){
            isEmpty = true;
        }
        return isEmpty;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called in activity");
    }
}