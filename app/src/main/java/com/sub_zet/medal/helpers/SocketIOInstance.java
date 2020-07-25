package com.sub_zet.medal.helpers;

import android.util.Log;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.socket.engineio.client.transports.Polling;

public class SocketIOInstance {

    private Socket mSocket;

    public SocketIOInstance(){}


    private final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    }};

    {
        try {
            SSLContext mySSLContext = SSLContext.getInstance("TLS");
            mySSLContext.init(null, trustAllCerts, null);

            Log.i("connectingGG", "Mtav");

            IO.Options opts = new IO.Options();
            opts.reconnection = true;
            opts.transports = new String[]{Polling.NAME};
            opts.sslContext = mySSLContext;
            mSocket = IO.socket("https://medal.fun:3600", opts);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Socket getmSocket() {
        return mSocket;
    }
}
