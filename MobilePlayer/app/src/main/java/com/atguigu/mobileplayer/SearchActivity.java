package com.atguigu.mobileplayer;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.mobileplayer.Utils.JsonParser;
import com.atguigu.mobileplayer.Utils.Url;
import com.atguigu.mobileplayer.adapter.SearchAdapter;
import com.atguigu.mobileplayer.domain.SearchBean;
import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 作者：杨光福 on 2016/4/30 08:51
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：搜索页面
 */
public class SearchActivity  extends Activity{
    private static final String TAG = SearchActivity.class.getSimpleName();
    private EditText etSerach;
    private ImageView ivVoice;
    private TextView tvSerachGo;
    private ListView listView;
    private TextView tvSerachInfo;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private List<SearchBean.ItemsEntity> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findViews();
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-04-30 09:02:50 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        etSerach = (EditText)findViewById( R.id.et_serach );
        listView = (ListView)findViewById( R.id.listView );
        tvSerachInfo = (TextView)findViewById( R.id.tv_serach_info );
        ivVoice = (ImageView)findViewById( R.id.iv_voice );
        tvSerachGo = (TextView)findViewById( R.id.tv_serach_go );
        MyOnClickListener myOnClickListener = new MyOnClickListener();
        ivVoice.setOnClickListener(myOnClickListener);
        tvSerachGo.setOnClickListener(myOnClickListener);
    }
    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_voice://语音输入
                    startVoice();
                    break;
                case R.id.tv_serach_go://搜索
                    startSearch();
                    break;
            }

        }
    }

    private void startSearch() {
        String word = etSerach.getText().toString().trim();

        if(!TextUtils.isEmpty(word)){
            try {
                word = URLEncoder.encode(word, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String url = Url.NET_SEARCH_URL+word;
            RequestParams params = new RequestParams(url);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e(TAG, "联网成功==" + result);
                    processData(result);

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e(TAG,"onError=="+ex.getMessage());
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Log.e(TAG,"onCancelled=="+cex.getMessage());
                }

                @Override
                public void onFinished() {
                    Log.e(TAG,"onFinished==");

                }
            });
        }else{
            Toast.makeText(SearchActivity.this,"没有输入文本",Toast.LENGTH_SHORT).show();
        }


    }

    private void processData(String json) {
        SearchBean searchBean = new Gson().fromJson(json, SearchBean.class);
        items = searchBean.getItems();
        if(items != null && items.size() > 0){
            //设置适配器
            listView.setAdapter(new SearchAdapter(SearchActivity.this,items));
            tvSerachInfo.setVisibility(View.GONE);
        }else{
            tvSerachInfo.setVisibility(View.VISIBLE);
        }



    }




    public void startVoice() {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(this, new MyInitListener());
        //2.设置accent、 language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");//设置语音
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");//设置普通话
        mDialog.setParameter(SpeechConstant.DOMAIN, "iat");//日常用语
        //若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解
        //结果
        // mDialog.setParameter("asr_sch", "1");
        // mDialog.setParameter("nlp_version", "2.0");
        //3.设置回调接口
        mDialog.setListener(new MyRecognizerDialogListener());
        //4.显示dialog，接收语音输入
        mDialog.show();
    }


    class MyInitListener implements InitListener {

        @Override
        public void onInit(int i) {
            if (i != ErrorCode.SUCCESS) {
                Toast.makeText(SearchActivity.this, "初始化失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class MyRecognizerDialogListener implements RecognizerDialogListener {

        /**
         * 语音输入返回的结果
         *
         * @param recognizerResult
         * @param isLast
         */
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean isLast) {
            Log.e(TAG, "onResult===" + recognizerResult.getResultString());
            printResult(recognizerResult);
        }

        @Override
        public void onError(SpeechError speechError) {

            Log.e(TAG, "onError===" + speechError.getMessage());
        }
    }


    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        Log.e(TAG, "解析好的==" + text);
        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");//1,2
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //把每一句加入到map集合中
        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        //设置到输入框里面
        etSerach.setText(resultBuffer.toString());
        etSerach.setSelection(etSerach.length());
    }



}
