package com.example.chess_online.rest.mapper;

import android.os.Build;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.chess_online.cache.UserCache;
import com.example.chess_online.domain.User;
import com.example.chess_online.fragment.LoginFragment;
import com.example.chess_online.rest.AppApi;
import com.example.chess_online.fragment.RegistrationFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class AppApiVolley implements AppApi {
    private static final String BASE_URL = "http://192.168.0.108:8080";
    private Fragment fragment;
    private Response.ErrorListener errorListener;

    public AppApiVolley(Fragment fragment) {
        this.fragment = fragment;
        errorListener = new ErrorListenerImpl();
    }

    @Override
    public void findUserByUsername(String username, String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(fragment.requireContext());
        String url = BASE_URL + "/user/" + username;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            UserCache.setCurrentUser(UserMapper.getFromJson(response, password));
                        }
                        catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        if (fragment.getClass().equals(LoginFragment.class)) {
                            ((LoginFragment) fragment).login();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("BadCredentialsAppApi", "Неправильный логин или пароль");
                        if (fragment.getClass().equals(LoginFragment.class)) {
                            ((LoginFragment) fragment).makeToastBadCredentials();
                        }
                    }
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new Hashtable<>();
                        String credentials = username + ":"
                                + password;
                        headers.put("Content-Type", "application/json");
                        headers.put("Authorization", "Basic " + Base64.encodeToString(
                                credentials.getBytes(), Base64.NO_WRAP));
                        return headers;
                    }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void insertUser(User user) {
        RequestQueue referenceQueue = Volley.newRequestQueue(fragment.requireContext());

        String url = BASE_URL + "/user";
        JSONObject params = new JSONObject();
        try {
            params.put("email", user.getEmail());
            params.put("fullname", user.getFullname());
            params.put("password", user.getPassword());
            params.put("username", user.getUsername());
        } catch (JSONException e) {
            Log.e("API_TASK_ADD_USER", e.getMessage());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                UserCache.setCurrentUser(user);
                if(fragment.getClass().equals(RegistrationFragment.class))
                    ((RegistrationFragment) fragment).signIn();
                Log.d("API_TEST_ADD_USER", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(fragment.getClass().equals(RegistrationFragment.class))
                    ((RegistrationFragment) fragment).makeToastFailedRegistration();
                Log.i("API_FAILED_REGISTRATION", error.getMessage());
            }
        }
        );
        referenceQueue.add(jsonObjectRequest);

    }

    private class ErrorListenerImpl implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("AppApiErrorResponse", error.getMessage());
        }
    }
    private Map<String, String> getHeadersBasic() throws AuthFailureError {
        Map<String, String> headers = new Hashtable<>();
        String credentials = UserCache.getCurrentUser().getUsername() + ":"
                + UserCache.getCurrentUser().getPassword();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Basic " + Base64.encodeToString(
                credentials.getBytes(), Base64.NO_WRAP));
        return headers;
    }
}
