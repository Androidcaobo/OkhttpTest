package com.caobo.okhttptest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.caobo.okhttptest.GsonTest.Person;
import com.caobo.okhttptest.okhttpUility.OkHttpClientManager;
import com.squareup.okhttp.Request;

import java.util.List;

/**
 * 1.以上就是发送一个get请求的步骤，首先构造一个Request对象，参数最起码有个url
 * 2.然后通过request的对象去构造得到一个Call对象，类似于将你的请求封装成了任务，既然是任务，就会有execute()和cancel()等方法
 * 3.最后，我们希望以异步的方式去执行请求，所以我们调用的是call.enqueue，将call加入调度队列，然后等待任务执行完成，我们在Callback中即可得到结果
 * 4.每次请求还是比较麻烦的，所以我们自己还是要封装一下
 *
 * eg："http://www.weather.com.cn/data/list3/city.xml"
 */

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.test);

        //okHttp发送网络Get请求,注意这里我们指定的是String类型如：.ResultCallback<String>
        OkHttpClientManager.getAsyn("http://www.weather.com.cn/data/list3/city.xml", new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response) {
                //注意这里是Ui线程
                //mTextView.setText(response);
            }
        });


        //http://www.imooc.com/api/teacher?type=4&num=30
        //整合的Gson,暂时不会用,解析不出来
        OkHttpClientManager.getAsyn("http://www.imooc.com/api/teacher?type=4&num=30",
                new OkHttpClientManager.ResultCallback<List<Person>>()
                {
                    @Override
                    public void onError(Request request, Exception e)
                    {
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(List<Person> us)
                    {
                        Log.e("TAG", us.size() + "");
                        mTextView.setText(us.get(1).getMsg());
                    }
                });
    }
}
