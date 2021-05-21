package com.mact.simpleautomationapp.Utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.mact.simpleautomationapp.Room.Entity.Action;
import com.mact.simpleautomationapp.Room.Entity.Email;
import com.mact.simpleautomationapp.Services.SendMailJobService;

import java.util.Locale;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class RunAutomateTask {

    private Context context;
    private TextToSpeech mtts;
    /*
    * onReceiveCalledCounted is to check whether or not onReceive method of the Broadcast manager is being called
    * the second time. For some reason, it gets called twice.
    * */
    private int onReceiveCounter = 1;
    public RunAutomateTask(Context context)
    {
        this.context = context;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void run(Action autoAction){
        String action = autoAction.getBroadcastAction();
        int finalState = autoAction.getFinalState();
        Intent intent = autoAction.getLaunchAppIntent();
        switch(action){
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                runBluetoothTask(finalState);
                break;
            case WifiManager.WIFI_STATE_CHANGED_ACTION:
                runWifiTask(finalState);
                break;
            case ActionTriggerConstants.LAUNCH_APPLICATION:
                launchApplication(intent);
                break;
            case ActionTriggerConstants.TURN_FLASH_ON:
                turnFlashLightOn();
                break;
            case ActionTriggerConstants.SEND_EMAIL:
                if(onReceiveCounter < 2){
                    sendEmail(autoAction.getEmail());
                    onReceiveCounter++;
                }else{
                    onReceiveCounter = 1;
                }
                break;
            case ActionTriggerConstants.TEXT_TO_SPEECH:
                initTTS(context);
                readText(autoAction.getMessage());
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void turnFlashLightOn() {
        if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY) &&
            context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            try {
                cameraManager.setTorchMode("0", true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void launchApplication(Intent intent){
        if(intent != null){
            context.startActivity(intent);
        }
    }

    private void runWifiTask(int state){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if(state == WifiManager.WIFI_STATE_ENABLED){
            wifiManager.setWifiEnabled(true);
        }else{
            wifiManager.setWifiEnabled(false);
        }
    }

    private void runBluetoothTask(int state){
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if(state == BluetoothAdapter.STATE_ON){
            adapter.enable();
        }else{
            adapter.disable();
        }
    }

    private void sendEmail(Email email){

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //pass sender's gmail email id and password.
                return new PasswordAuthentication(email.getSenderEmailId(), email.getSenderPasswd());
            }
        });

        Message message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(email.getSenderEmailId()));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getRecipientEmailId())); //has to be a string array
            message.setSubject(email.getSubject());
            message.setText(email.getEmailBody());

            SendMailJobService sendMailJobService = new SendMailJobService(message);
            new Thread(sendMailJobService).start();

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private void initTTS(Context context){
         mtts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = mtts.setLanguage(Locale.ENGLISH);
                }
            }
        });
    }

    private void readText(String message){
        mtts.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
    }



}
