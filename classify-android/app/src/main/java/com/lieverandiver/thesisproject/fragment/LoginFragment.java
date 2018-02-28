package com.lieverandiver.thesisproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lieverandiver.thesisproject.R;

public class LoginFragment extends Fragment implements  View.OnClickListener {

    private static final String TAG = LoginFragment.class.getSimpleName();

    private LoginFragmentListener loginFragmentListener;
    private EditText textUsername;
    private EditText textPassword;
    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPassword;
    private Button buttonLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        final View view = inflater.inflate(R.layout.login_fragment, container, false);
        textUsername = (EditText) view.findViewById(R.id.fragment_login_text_email);
        textPassword = (EditText) view.findViewById(R.id.fragment_login_text_password);
        textInputLayoutUsername = (TextInputLayout)
                view.findViewById(R.id.fragment_login_email_layout);
        textInputLayoutPassword = (TextInputLayout)
                view.findViewById(R.id.fragment_login_password_layout);
        buttonLogin = (Button) view.findViewById(R.id.fragment_login_button_login);
        buttonLogin.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach");
        super.onAttach(context);
        if(context instanceof LoginFragmentListener)
            loginFragmentListener = (LoginFragmentListener) context;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        textUsername = null;
        textPassword = null;

        textInputLayoutUsername = null;
        textInputLayoutPassword = null;

        buttonLogin = null;
        loginFragmentListener = null;
    }

    public boolean isWidgetInputIsValid(){
        boolean isValid = true;
        if(textUsername.getText().toString().trim().equals("")){
            textInputLayoutUsername.setError("Username is empty");
            isValid = false;
        }else {
            textInputLayoutPassword.setErrorEnabled(false);
        }
        if(textPassword.getText().toString().trim().equals("")){
            textInputLayoutPassword.setError("Password is Empty");
            isValid = false;
        }else {
            textInputLayoutPassword.setErrorEnabled(false);
        }
        return isValid;
    }

    @Override
    public void onClick(View v) {
        if(isWidgetInputIsValid()) {
            loginFragmentListener.doLogin(textUsername.getText().toString(), textPassword.getText().toString());
        }
    }

    public interface LoginFragmentListener {
        void doLogin(String username, String password);
    }
}
