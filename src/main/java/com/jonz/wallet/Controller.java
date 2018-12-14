package com.jonz.wallet;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.after;
import static spark.Spark.post;

public class Controller {

    public static void main(String[] args) {
        Gson gson = new Gson();
        post("/addresses/filter-used", Service::filterUsed, gson::toJson);
        post("/txs/utxo-for-addresses", Service::filterUsed);
        post("/txs/utxo-sum-for-addresses", Service::filterUsed);
        post("/txs/history", Service::filterUsed);
        post("/txs/signed", Service::filterUsed);
        after((req, res) -> res.type("application/json"));
    }
}
