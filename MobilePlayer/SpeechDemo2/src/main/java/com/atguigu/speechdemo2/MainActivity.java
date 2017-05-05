package com.atguigu.speechdemo2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText et_input;
    private Button btn_voice;
    private Button speektext;

    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_input = (EditText) findViewById(R.id.et_input);
        btn_voice = (Button) findViewById(R.id.btn_voice);
        speektext = (Button) findViewById(R.id.speektext);
        MyOnClickListener myOnClickListener = new MyOnClickListener();
        btn_voice.setOnClickListener(myOnClickListener);
        speektext.setOnClickListener(myOnClickListener);
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_voice://语音输入
                    startVoice();
                    break;
                case R.id.speektext://文字转声音
                    speechText();
                    break;
            }
        }
    }

    /**
     * 把文字转声音
     */
    private void speechText() {
        //1.创建 SpeechSynthesizer 对象, 第二个参数： 本地合成时传 InitListener
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(this, null);
//2.合成参数设置，详见《 MSC Reference Manual》 SpeechSynthesizer 类
//设置发音人（更多在线发音人，用户可参见 附录13.2
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
//设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
//保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
//仅支持保存为 pcm 和 wav 格式， 如果不需要保存合成音频，注释该行代码
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
//3.开始合成
        mTts.startSpeaking(et_input.getText().toString().trim(), mSynListener);
//合成监听器


    }

    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时， error为null
        public void onCompleted(SpeechError error) {
            Toast.makeText(MainActivity.this,"读取结束",Toast.LENGTH_SHORT).show();
        }

        //缓冲进度回调
//percent为缓冲进度0~100， beginPos为缓冲音频在文本中开始位置， endPos表示缓冲音频在
        // 文本中结束位置， info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
            Toast.makeText(MainActivity.this,"开始播放",Toast.LENGTH_SHORT).show();
        }

        //暂停播放
        public void onSpeakPaused() {
            Toast.makeText(MainActivity.this,"暂停播放",Toast.LENGTH_SHORT).show();
        }
//播放进度回调
//percent为播放进度0~100,beginPos为播放音频在文本中开始位置， endPos表示播放音频在文
        //本中结束位置.

        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {

        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };


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
                Toast.makeText(MainActivity.this, "初始化失败", Toast.LENGTH_SHORT).show();
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
        et_input.setText(resultBuffer.toString());
        et_input.setSelection(et_input.length());
    }

}
