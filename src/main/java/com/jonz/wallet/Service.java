package com.jonz.wallet;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Service {

    private static Gson gson = new Gson();

    private static String makeArray(List<String> dataList) {
        StringBuffer valArray = new StringBuffer("ARRAY['");
        dataList.forEach(it -> valArray.append(it).append("','"));
        valArray.append("']");
        return valArray.toString();
    }

    static Map filterUsed(Request req, Response res) {
        Connection conn = null;
        Statement stmt = null;
        List<String> resAddresses = new ArrayList<>();
        HashMap<String, List<String>> map = new HashMap<>();
        try {
            // 打开链接
            conn = DbcpUtil.getConnection();

            // 拼接查询条件
            String body = req.body();
            Map bodyMap = gson.fromJson(body, Map.class);
            String valArray = Service.makeArray((List<String>) bodyMap.get("addresses"));

            // 执行查询
            stmt = conn.createStatement();
            String sql = String.format("SELECT DISTINCT address FROM tx_addresses WHERE address = ANY(%s)", valArray);
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while (rs.next()) {
                // 通过字段检索
                resAddresses.add(rs.getString("address"));
            }
            map.put("addresses", resAddresses);

            // 完成后关闭
            JdbcUtil.closeAll(conn,rs,stmt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            JdbcUtil.closeConnection(conn);
            JdbcUtil.closeST(stmt);
        }
        return map;
    }
}
