package com.aria.jhcpokemon.minamivote.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aria.jhcpokemon.minamivote.R;
import com.aria.jhcpokemon.minamivote.model.Character;
import com.aria.jhcpokemon.minamivote.weibo.AccessTokenKeeper;
import com.aria.jhcpokemon.minamivote.weibo.Constants;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

public class DespActivity extends AppCompatActivity implements IWeiboHandler.Response {
    private ImageView imageView;
    private TextView textView;
    private Button button;
    private Character character;
    private ScrollView scrollView;
    private AuthInfo authInfo;
    private SsoHandler ssoHandler;
    private IWeiboShareAPI weiboShareAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView = (ImageView) findViewById(R.id.character_pic);
        textView = (TextView) findViewById(R.id.description);
        button = (Button) findViewById(R.id.vote_button);
        character = getIntent().getParcelableExtra("main_activity");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(character.getName());
        }
        imageView.setImageResource(getImgId(this));
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        textView.setText(character.getDescription());
        authInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (weiboShareAPI != null) {
                    weiboShareAPI = WeiboShareSDK.createWeiboAPI(DespActivity.this, Constants.APP_KEY);
                    weiboShareAPI.registerApp();
                    sendSingleMessage();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_about:
                return true;
            case R.id.action_login:
                ssoHandler = new SsoHandler(DespActivity.this, authInfo);
                ssoHandler.authorize(new AuthListener());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public int getImgId(Context context) {
        try {
            return context.getResources().getIdentifier("pic_" + character.getId(), "drawable", context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    private class AuthListener implements WeiboAuthListener {
        @Override
        public void onComplete(Bundle bundle) {
            Oauth2AccessToken token = Oauth2AccessToken.parseAccessToken(bundle);
            if (token != null && token.isSessionValid()) {
                AccessTokenKeeper.writeAccessToken(DespActivity.this, token);
            } else {
                String code = bundle.getString("code", "");
                Toast.makeText(DespActivity.this, code, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(DespActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(DespActivity.this, R.string.login_canceled, Toast.LENGTH_LONG).show();
        }
    }

    private void sendSingleMessage() {
        if (weiboShareAPI == null) {
            Toast.makeText(DespActivity.this, "请先在右上角登陆", Toast.LENGTH_SHORT).show();
        } else {
            WeiboMessage weiboMessage = new WeiboMessage();
            weiboMessage.mediaObject = getTextObject();
            SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
            request.transaction = String.valueOf(System.currentTimeMillis());
            request.message = weiboMessage;
            weiboShareAPI.sendRequest(DespActivity.this, request);
        }
    }

    private TextObject getTextObject() {
        TextObject textObject = new TextObject();
        textObject.text = getSharedText();
        return textObject;
    }

    private String getSharedText() {
        return "投给了" + character.getName() + "一票!";
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        weiboShareAPI.handleWeiboResponse(intent, this);
    }

    @Override
    public void onResponse(BaseResponse response) {
        Toast.makeText(DespActivity.this, response.errCode, Toast.LENGTH_SHORT).show();
    }
}
