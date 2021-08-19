package com.zs.itking.getapijsondate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zs.itking.getapijsondate.bean.JokeBean;
import com.zs.itking.getapijsondate.bean.PostcodeBean;
import com.zs.itking.getapijsondate.bean.WeatherBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JSONObjectTestActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Handler handler;//定义主线程实例名称handler
    private TextView tv_show;

    //获取邮编地址
    private List<PostcodeBean.Result.list> postCodeLists = new ArrayList<>();
    //获取未来五天的天气数据
    private List<WeatherBean.Result.Future> futureArrayList = new ArrayList<>();
    private List<JokeBean.Result.Data> jokeDataList = new ArrayList<>();
    //    private final int FAILURE_CODE = 1001;//失败
//
//    private final int SUCCESS_CODE = 1000;//成功

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json_object_test_activity);

        initOk();


//        HttpUtil.sendRequestWithOkhttp(url, new okhttp3.Callback() {
////            Message message = new Message();
//
//            @Override
//            public void onFailure(Call call, IOException e) {
////                message.what = FAILURE_CODE;
////                message.obj = e.getMessage();
////                handler.handleMessage(message);
////                Log.d(TAG, "请求失败==="+e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String strJson = response.body().string();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        parseJsonWithJsonObject(strJson);
//                    }
//                });
//
////                    Log.d(TAG, "请求成功==="+strGetJson);
////                    message.what = SUCCESS_CODE;
////                    message.obj = strGetJson;
////                    handler.handleMessage(message);
//            }
//        });
    }

    private void initOk() {
        tv_show = findViewById(R.id.tv_show);
        handler = new Handler(getMainLooper());//创建主线程实例

        String strUrl = "http://apis.juhe.cn/ip/ipNew?ip=117.188.18.162&key=9e445792af18bea1617909d11be6df84";

        OkHttpClient okHttpClient = new OkHttpClient();//创建OkHttp实例
        final Request request = new Request.Builder()
                .url(strUrl)
                .build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() { //子线程中访问网络资源
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    final String str = response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Json转Java对象——转换后数据存放在weatherBean的内存地址中

//                            Gson gson = new Gson();
//
//                            //  这个解析的是外层的     这里传入两个参数
//                            IP test_one = gson.fromJson(str,IP.class);
//                            //这里解析的是内层的 也就是 []  里面的内容
//                            List<IP.result> hitsList = test_one.getResult();
////                            List<IP> contactList = GsonUtil.fromJson(str,new TypeToken<List<IP>>() {}.getType());
////                            List<IP> contactList = gson.fromJson(str,new TypeToken<List<IP>>() {}.getType());
//                            Log.d(TAG, "Json转Java对象: " + hitsList);
//                            tv_show.setText(contactList.toString());
                            JsonParse(str);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * JSONObject原生方式解析Json
     *
     * @param jsonString
     */
    @SuppressLint("SetTextI18n")
    private void JsonParse(String jsonString) {
        if (!TextUtils.isEmpty(jsonString)) {
            try {
                /**
                 * 聚合IP地址
                 * 请求接口：http://apis.juhe.cn/ip/ipNew?ip=117.188.18.162&key=9e445792af18bea1617909d11be6df84
                 * 返回Json格式：
                 * {
                 *     "resultcode": "200",
                 *     "reason": "查询成功",
                 *     "result": {
                 *         "Country": "中国",
                 *         "Province": "贵州省",
                 *         "City": "贵阳市",
                 *         "Isp": "移动"
                 *     },
                 *     "error_code": 0
                 * }
                 */
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONObject jsonObjectResult = jsonObject.getJSONObject("result");
                tv_show.setText("国家:" + jsonObjectResult.getString("Country")
                        + "\n" + "省份:" + jsonObjectResult.getString("Province")
                        + "\n" + "城市:" + jsonObjectResult.getString("City")
                        + "\n" + "运营商:" + jsonObjectResult.getString("Isp"));
                /**
                 * 聚合邮编地址
                 * 请求接口：http://v.juhe.cn/postcode/query?postcode=562100&page=1&pagesize=50&dtype=json&key=2141e5ff96244f04d9923104a8832403
                 * 返回Json格式： 查询562100邮编的地址信息——list有50条数据
                 * {
                 *     "reason": "successed",
                 *     "result": {
                 *         "list": [
                 *             {
                 *                 "PostNumber": "562100",
                 *                 "Province": "贵州省",
                 *                 "City": "安顺市",
                 *                 "District": "普定县",
                 *                 "Address": "城关镇烂坝村"
                 *             },
                 *             {
                 *                 "PostNumber": "562100",
                 *                 "Province": "贵州省",
                 *                 "City": "安顺市",
                 *                 "District": "普定县",
                 *                 "Address": "城关镇新堡村"
                 *             }
                 *         ],
                 *         "totalcount": 68,
                 *         "totalpage": 2,
                 *         "currentpage": 1,
                 *         "pagesize": "50"
                 *     },
                 *     "error_code": 0
                 * }
                 *
                 * 索引 0 开始 查询第50条数据
                 * MainActivity: JsonParse: 邮编=562100==省份=贵州省==城市=安顺市==县城=普定县==乡镇=城关镇新堡村
                 */
//                JSONObject jsonObject = new JSONObject(jsonString);
//                JSONObject jsonObjectResult = jsonObject.getJSONObject("result");
//                JSONArray jsonArrayFuture = jsonObjectResult.getJSONArray("list");
//                for (int i = 0; i < jsonArrayFuture.length(); i++) {
//                    JSONObject object = (JSONObject) jsonArrayFuture.get(i);
//                    PostcodeBean.Result.list list = new PostcodeBean.Result.list();
//                    list.setPostNumber(object.getString("PostNumber"));
//                    list.setProvince(object.getString("Province"));
//                    list.setCity(object.getString("City"));
//                    list.setDistrict(object.getString("District"));
//                    list.setAddress(object.getString("Address"));
//                    postCodeLists.add(list);
//                }
//                tv_show.setText("邮编=" + postCodeLists.get(49).getPostNumber() + "=="
//                        + "省份=" + postCodeLists.get(49).getProvince() + "=="
//                        + "城市=" + postCodeLists.get(49).getCity() + "=="
//                        + "县城=" + postCodeLists.get(49).getDistrict() + "=="
//                        + "乡镇=" + postCodeLists.get(49).getAddress());

                /**
                 * 聚合天气预报
                 * 请求接口：http://apis.juhe.cn/simpleWeather/query?city=贵阳&key=62717577a86a249600831b0661edb84c
                 * 返回Json格式：贵阳天气实时数据
                 * {
                 *     "reason": "查询成功!",
                 *     "result": {
                 *         "city": "贵阳",
                 *         "realtime": {
                 *             "temperature": "23",
                 *             "humidity": "82",
                 *             "info": "多云",
                 *             "wid": "01",
                 *             "direct": "南风",
                 *             "power": "2级",
                 *             "aqi": "28"
                 *         },
                 *         "future": [
                 *             {
                 *                 "date": "2021-08-20",
                 *                 "temperature": "22/30℃",
                 *                 "weather": "多云",
                 *                 "wid": {
                 *                     "day": "01",
                 *                     "night": "01"
                 *                 },
                 *                 "direct": "南风"
                 *             },
                 *             {
                 *                 "date": "2021-08-21",
                 *                 "temperature": "22/31℃",
                 *                 "weather": "多云",
                 *                 "wid": {
                 *                     "day": "01",
                 *                     "night": "01"
                 *                 },
                 *                 "direct": "南风"
                 *             }
                 *         ]
                 *     },
                 *     "error_code": 0
                 * }
                 */
//                //传入OKHttp解析的Json数据
//                JSONObject jsonObject = new JSONObject(jsonString);
//                //获取key为“result”的全部value数据
//                JSONObject jsonObjectResult = jsonObject.getJSONObject("result");
//                //获取当前实时天气:{"temperature":"23","humidity":"82","info":"多云","wid":"01","direct":"南风","power":"2级","aqi":"28"}
//                JSONObject jsonObjectToday = jsonObjectResult.getJSONObject("realtime");
//                tv_show.setText("获取当前实时天气:"+"当前温度="+jsonObjectToday.getString("temperature")
//                        +",当前湿度="+jsonObjectToday.getString("humidity")
//                        +",当前天气="+jsonObjectToday.getString("info")
//                        +",当前风向="+jsonObjectToday.getString("direct")
//                        +",当前风力="+jsonObjectToday.getString("power")
//                        +",当前ID="+jsonObjectToday.getString("wid")
//                        +",当前空气质量指数="+jsonObjectToday.getString("aqi")
//                );
                /**
                 * 获取未来五天的天气数据
                 * Key = "future"
                 * value = "date＂（日期）、"temperature＂（温度）、"weather＂（天气）、"wid＂（ID值）、"direct（风向）＂
                 */
//                JSONObject jsonObject = new JSONObject(jsonString);
//                JSONObject jsonObjectResult = jsonObject.getJSONObject("result");
//                JSONArray jsonObjectFuture = jsonObjectResult.getJSONArray("future");
//                for (int i = 0; i < jsonObjectFuture.length(); i++) {
//                    JSONObject jsonFuture =(JSONObject)jsonObjectFuture.get(i);
//                    WeatherBean.Result.Future future = new WeatherBean.Result.Future();
//                    future.setDate(jsonFuture.getString("date"));
//                    future.setTemperature(jsonFuture.getString("temperature"));
//                    future.setWeather(jsonFuture.getString("weather"));
////                    future.setWid(jsonFuture.getString("wid"));
//                    future.setDirect(jsonFuture.getString("direct"));
//                    futureArrayList.add(future);
//                }
//                tv_show.setText("日期=" + futureArrayList.get(3).getDate() + "=="
//                        + "温度=" + futureArrayList.get(3).getTemperature() + "=="
//                        + "天气=" + futureArrayList.get(3).getWeather() + "=="
////                        + "ID值=" + futureArrayList.get(0).getWid() + "=="
//                        + "风向=" + futureArrayList.get(3).getDirect());

                /**
                 * 聚合最新笑话
                 * 请求接口：http://v.juhe.cn/joke/content/text.php?page=6&pagesize=20&key=6b730ff75e457e11a7d06287ef1738f7
                 * page：当前查询页数，每页20条笑话，pagesize：查询最大20页
                 * 返回Json格式：查询最新笑话信息——data有20条数据
                 * {
                 *     "reason": "Success",
                 *     "result": {
                 *         "data": [
                 *             {
                 *                 "content": "妈妈正在做家务，..孩子突然说：爸爸你真厉害，我真佩服你。..爸爸问为什么？..孩子说：娶了个这么好的老婆，又漂亮，又贤惠，又，，，，，一顿好夸。爸爸正得意呢，..孩子转头对妈妈说：妈妈你就不行了，你看你嫁了个什么玩意儿……",
                 *                 "hashId": "60670173ea60ebfd5d69cffd5ab5716c",
                 *                 "unixtime": 1553662502,
                 *                 "updatetime": "2019-03-27 12:55:02"
                 *             },
                 *             {
                 *                 "content": "朋友开米粉店的，味道一绝，搞的是饥渴营销，宁愿不卖，也不让你打包回去，虽得罪了不少人，却刺激了更多人来品尝，生意很火。 他在大学城旁边又开了一家，没多久就关门大吉了，他说：那些学生过来，每人都要打包几份，宁愿不吃也不愿出来，比我性子硬多了。。。",
                 *                 "hashId": "fb9bb883f90b798f6963bdffdb25c8f6",
                 *                 "unixtime": 1553652002,
                 *                 "updatetime": "2019-03-27 10:00:02"
                 *             },
                 *             {
                 *                 "content": "出门陪女友逛街，内急 实在憋不住了啊 就随便找了个公厕 蹲了下去，完事才发现没带纸，，艾玛 ，拍了拍隔壁 哥们在吗 我没带纸 借我点呗 。 过了十几秒 对面一妹子传来尴尬的声音 大哥，到底是你进错厕所了，还是我进错厕所了，。。。",
                 *                 "hashId": "a889b9f789e5371ba32dee4b75ed313a",
                 *                 "unixtime": 1553652002,
                 *                 "updatetime": "2019-03-27 10:00:02"
                 *             }
                 *         ]
                 *     },
                 *     "error_code": 0
                 * }
                 *
                 * 查询第6页，第5条笑话数据
                 */
//                JSONObject jsonObject = new JSONObject(jsonString);
//                JSONObject jsonObjectResult = jsonObject.getJSONObject("result");
//                JSONArray jsonArray = jsonObjectResult.getJSONArray("data");
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObjectData = (JSONObject) jsonArray.get(i);
//                    JokeBean.Result.Data data = new JokeBean.Result.Data();
//                    data.setContent(jsonObjectData.getString("content"));
//                    data.setHashId(jsonObjectData.getString("hashId"));
//                    data.setUnixtime(jsonObjectData.getInt("unixtime"));
//                    data.setUpdatetime(jsonObjectData.getString("updatetime"));
//                    jokeDataList.add(data);
//                }
//                tv_show.setText("最新笑话：内容=" + jokeDataList.get(4).getContent()
//                        + "笑话ID=" + jokeDataList.get(4).getHashId()
//                        + "时间戳=" + jokeDataList.get(4).getUnixtime()
//                        + "更新日期=" + jokeDataList.get(4).getUpdatetime());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseJsonWithJsonObject(String string) {
        java.util.List weatherBeanList = new ArrayList<>();
        Gson gson = new Gson();

        Type typeList = new TypeToken<java.util.List>() {
        }.getType();

        weatherBeanList = gson.fromJson(string, typeList);

        Log.d(TAG, "parseJsonWithJsonObject: " + weatherBeanList);
        //Log.d(TAG, "weatherBeanList====: "+weatherBeanList);

//        Json转Java对象——转换后数据存放在weatherBean的内存地址中
//        WeatherBean weatherBean = gson.fromJson(strGetJson, WeatherBean.class);
//        Log.d(TAG, "Json转Java对象: " + weatherBean.toString());
//
//        //Java转Json对象
//        String toJson = gson.toJson(weatherBean);
//        Log.d(TAG, "Java转Json对象: " + toJson);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        handler
    }

//    /**
//     * 解析没有数据头的纯数组
//     */
//    private void parseNoHeaderJArray() {
//        //拿到本地JSON 并转成String
//        String strByJson = JsonToStringUtil.getStringByJson(this, R.raw.juser_1);
//
//        //Json的解析类对象
//        JsonParser parser = new JsonParser();
//        //将JSON的String 转成一个JsonArray对象
//        JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();
//
//        Gson gson = new Gson();
//        ArrayList<UserBean> userBeanList = new ArrayList<>();
//
//        //加强for循环遍历JsonArray
//        for (JsonElement user : jsonArray) {
//            //使用GSON，直接转成Bean对象
//            UserBean userBean = gson.fromJson(user, UserBean.class);
//            userBeanList.add(userBean);
//        }
//        mainLView.setAdapter(new UserAdapter(this, userBeanList));
//    }
//
//    /**
//     * 解析有数据头的纯数组
//     */
//    private void parseHaveHeaderJArray() {
//        //拿到本地JSON 并转成String
//        String strByJson = JsonToStringUtil.getStringByJson(this, R.raw.juser_2);
//
//        //先转JsonObject
//        JsonObject jsonObject = new JsonParser().parse(strByJson).getAsJsonObject();
//        //再转JsonArray 加上数据头
//        JsonArray jsonArray = jsonObject.getAsJsonArray("muser");
//
//        Gson gson = new Gson();
//        ArrayList<UserBean> userBeanList = new ArrayList<>();
//
//        //循环遍历
//        for (JsonElement user : jsonArray) {
//            //通过反射 得到UserBean.class
//            UserBean userBean = gson.fromJson(user, new TypeToken<UserBean>() {}.getType());
//            userBeanList.add(userBean);
//        }
//        mainLView.setAdapter(new UserAdapter(this, userBeanList));
//    }
//
//    /**
//     * 有消息头 复杂数据 常规方式
//     */
//    private void parseComplexJArrayByCommon() {
//        //拿到Json字符串
//        String strByJson = JsonToStringUtil.getStringByJson(this, R.raw.juser_3);
//        //GSON直接解析成对象
//        ResultBean resultBean = new Gson().fromJson(strByJson,ResultBean.class);
//        //对象中拿到集合
//        List<ResultBean.UserBean> userBeanList = resultBean.getMuser();
//        //展示到UI中
//        mainLView.setAdapter(new ResultAdapter(this, userBeanList));
//    }
}


