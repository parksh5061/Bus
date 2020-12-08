package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_email, et_id, et_password, et_password2;
    private Button btn_register;
    private ImageButton btn_loginstage;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //액티비티 시작 시 처음으로 실행되는 생명주기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 아이디 값 찾아주기
        et_email = findViewById(R.id.et_email);
        et_id = findViewById(R.id.et_id);
        et_password = findViewById(R.id.et_password);
        et_password2 = findViewById(R.id.et_password2);

        // 회원가입 버튼 클릭 시 수행
        btn_register = findViewById(R.id.btn_register);
        btn_loginstage = findViewById(R.id.btn_loginstage);

        btn_loginstage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.myapplication.RegisterActivity.this, com.example.myapplication.LoginActivity.class);
                startActivity(intent);
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText 에 현재 입력되어 있는 값을 get해온다.
                String userEmail = et_email.getText().toString();
                String userID = et_id.getText().toString();
                String userPassword = et_password.getText().toString();
                String userPassword2 = et_password2.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { //회원 등록에 성공한 경우
                                Toast.makeText(getApplicationContext(),"회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(com.example.myapplication.RegisterActivity.this, com.example.myapplication.LoginActivity.class);
                                startActivity(intent);
                            } else { //회원등록에 실패한 경우
                                Toast.makeText(getApplicationContext(),"회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };


                // Volley를 이용해 서버로 요청
                com.example.myapplication.RegisterRequest registerRequest = new com.example.myapplication.RegisterRequest(userEmail, userID, userPassword, userPassword2, responseListener);
                RequestQueue queue = Volley.newRequestQueue(com.example.myapplication.RegisterActivity.this);
                queue.add(registerRequest);

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }


        });

    }
}






























