package com.example.greencalendar10

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.greencalendar10.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    // 변수 모음
    lateinit var binding: ActivityLoginBinding
    lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("userPref",Context.MODE_PRIVATE)

        // 로그인 한적 있다? 바로 패스
        if(MyApplication.checkAuth()){
            startActivity(Intent(this,MainActivity::class.java))
            Log.d("로그인 성공","로그인 성공")
        }else {
            Toast.makeText(
                baseContext,
                "로그인 실패",
                Toast.LENGTH_SHORT
            ).show()
        }

        // -------------구글 인증 시작 부분----------------
        var requestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )
        {
            // 결과 intent 에서 구글 로그인 결과 가져오기
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!! // 사용자 정보 가져오기
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                // 가져온 정보를 토대로 파이어베이스 auth 실행하기
                MyApplication.auth.signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // 구글 로그인 성공
                            MyApplication.email = account.email
                            Log.d("로그인 성공", "로그인 성공")
                            Toast.makeText(
                                baseContext,
                                "로그인 성공",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            // 구글 로그인 실패
                            Log.d("로그인 실패", "로그인 실패")
                            Toast.makeText(
                                baseContext,
                                "로그인 실패",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } catch (e: ApiException) {
                // 예외 처리
                Log.d("예외 로그인 실패", "예외 로그인 실패")
                Toast.makeText(
                    baseContext,
                    "예외로 인한 로그인 실패",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // -----------------구글 로그인 버튼 누를 때-----------------------
        binding.googleLoginBtn.setOnClickListener {
            // 구글 인증 앱 실행

            val gso = GoogleSignInOptions
                // 앱에 필요한 사용자 데이터를 요청한다.
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                // DEFAULT_SIGN_IN 은 유저의 프로필과 ID를 요청하는데 사용되는 파라미터
                .requestIdToken(getString(R.string.default_web_client_id))
                // IdToken 데이터 요청(사용자의 식별값). default_web_client_id은 google-services.json 파일 내에 선언되어있으므로 안될 경우 rebuild 해줘야 되더라.
                .requestEmail()
                // 이메일 데이터 요청.
                .build()

            // 인텐트 객체 생성 (앱에서 구글 계정을 사용할 것이니 알고 있어라 정도로 봐라)
            val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
            // 인텐트 시작
            requestLauncher.launch(signInIntent)
            val intentToMain = Intent(this,MainActivity::class.java)
            val intentToProfileSetting = Intent(this,ProfileSettingActivity::class.java)

            if (sharedPref.getString("nickname","") == ""){
                startActivity(intentToProfileSetting)
            }else {
                startActivity(intentToMain)
            }
        }

        // 메인 화면 연결
        /*binding.homePageBtn.setOnClickListener{
            val intentToMain = Intent(this, MainActivity::class.java)
            startActivity(intentToMain)
        }


        // 알림창 화면 연결
        binding.notificationListPageBtn.setOnClickListener{
            val intentToNotificationList = Intent(this, NotificationList::class.java)
            startActivity(intentToNotificationList)
        }

        // 프로필 설정 화면 연결
        binding.profileBtn.setOnClickListener {
            val intentToProfileSetting = Intent(this,ProfileSettingActivity::class.java)
            startActivity(intentToProfileSetting)
        }

        // 게시판으로 전환 (임시)
        binding.addActivityBtn.setOnClickListener {
            val intentToBoardActivity = Intent(this,BoardActivity::class.java)
            startActivity(intentToBoardActivity)
        }
        */
    }

}