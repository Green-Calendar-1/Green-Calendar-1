package com.example.greencalendar10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.greencalendar10.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse

class LoginActivity : AppCompatActivity() {
    // 변수 모음
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

       /*if(MyApplication.checkAuth()){
            var intent = Intent(this, ProfileSettingActivity::class.java)
            Log.d("로그인 성공","로그인 성공")
        }else {
            Toast.makeText(
                baseContext,
                "로그인 실패",
                Toast.LENGTH_SHORT
            ).show()
        }*/

        // -------------구글 인증 시작 부분----------------
        var requestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
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
                            Log.d("로그인 성공","로그인 성공")
                            Toast.makeText(
                                baseContext,
                                "로그인 성공",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            // 구글 로그인 실패
                            Log.d("로그인 실패","로그인 실패")
                            Toast.makeText(
                                baseContext,
                                "로그인 실패",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
            catch (e: ApiException){
                // 예외 처리
                Log.d("예외 로그인 실패","예외 로그인 실패")
                Toast.makeText(
                    baseContext,
                    "예외로 인한 로그인 실패",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // -----------------구글 로그인 버튼 누를 때-----------------------
        binding.googleLoginBtn.setOnClickListener{
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
            val signInIntent=GoogleSignIn.getClient(this,gso).signInIntent
            // 인텐트 시작
            requestLauncher.launch(signInIntent)
        }

        // 메인 화면 연결
        binding.homePageBtn.setOnClickListener{
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



        //-----------------네이버 로그인--------------------
        // 네이버 초기화
        val naverClientId = getString(R.string.social_login_info_naver_client_id)
        val naverClientSecret = getString(R.string.social_login_info_naver_client_secret)
        val naverClientName = getString(R.string.social_login_info_naver_client_name)
        NaverIdLoginSDK.initialize(this, naverClientId, naverClientSecret , naverClientName)

        binding.naverLoginBtn.setOnClickListener {
            startNaverLogin()
            //startActivity(intent)
        }




    }
    private fun startNaverLogin(){
        var naverToken :String? = ""

        val profileCallback = object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(response: NidProfileResponse) {
                val userId = response.profile?.id
                Toast.makeText(this@LoginActivity, "네이버 아이디 로그인 성공!", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(this@LoginActivity, "errorCode: ${errorCode}\n" +
                        "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT).show()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        // OAuthLoginCallback을 authenticate() 메서드 호출 시 파라미터로 전달하거나 NidOAuthLoginButton 객체에 등록하면 인증이 종료되는 것을 확인할 수 있다.
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                naverToken = NaverIdLoginSDK.getAccessToken()


                //로그인 유저 정보 가져오기
                NidOAuthLogin().callProfileApi(profileCallback)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(this@LoginActivity, "errorCode: ${errorCode}\n" +
                        "errorDescription: ${errorDescription}", Toast.LENGTH_SHORT).show()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }

}