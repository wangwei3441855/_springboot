package com.example.demo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.SysUser;
import com.example.demo.service.user.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    protected SparkConf sparkConf;


    @Autowired
    protected UserService userService;

    @Test
    public void contextLoads() {
        PageHelper.startPage(1, 10);
        Map<String, Object> map = new HashMap<String, Object>();
        PageInfo<SysUser> sysUserPageInfo = new PageInfo<>(userService.getList(map));
        System.out.println("data:--->" + JSON.toJSONString(sysUserPageInfo));
    }


    @Test
    public void sparkTest() {
        SparkSession sparkSession = SparkSession.builder().config(sparkConf).getOrCreate();
        String url = "jdbc:mysql://localhost:3306/demo?&useUnicode=true&characterEncoding=UTF-8";
        Dataset<Row> dataset = sparkSession.read().format("jdbc").option("url", url)
                .option("driver", "com.mysql.jdbc.Driver").option("dbtable", "sys_resources")
                .option("user", "root").option("password", "1234").load();

        dataset.sample(false, 0.2).show();
        /*Encoder<SysRsources> personEncoder = Encoders.bean(SysRsources.class);
        Dataset<SysRsources> datasetRes = dataset.as(personEncoder);*/
        StructType schema = dataset.schema();
        StructField[] fields = schema.fields();
        JSONObject result = new JSONObject();
        result.put("total",dataset.count());
        dataset = dataset.limit(2);
        JSONArray columns = new JSONArray();
        for (StructField field : fields) {
            JSONObject obj = new JSONObject();
            String name = field.name();
            DataType dataType = field.dataType();
            boolean nullable = field.nullable();
            obj.put("fieldname", name);
            obj.put("dataType", dataType.typeName());
            obj.put("nullable", nullable);
            columns.add(obj);
        }
        result.put("columns", columns);
        List<Row> rows = dataset.collectAsList();
        JSONArray datas = new JSONArray();
        for (Row row : rows) {
            JSONObject data = new JSONObject();
            for (StructField field : fields) {
                String name = field.name();
                Object val = row.getAs(name);
                data.put(name, val);
            }
            datas.add(data);
        }
        result.put("datas", datas);
        System.out.println("result:--->" + JSON.toJSONString(result, true));
    }
}
