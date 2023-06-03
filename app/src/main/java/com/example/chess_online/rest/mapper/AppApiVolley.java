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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chess_online.cache.UserCache;
import com.example.chess_online.domain.GameState;
import com.example.chess_online.domain.User;
import com.example.chess_online.domain.model.FigureMove;
import com.example.chess_online.fragment.GameFragment;
import com.example.chess_online.fragment.LoginFragment;
import com.example.chess_online.fragment.MatchesFragment;
import com.example.chess_online.rest.AppApi;
import com.example.chess_online.fragment.RegistrationFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new Hashtable<>();
                String credentials = username + ":"
                        + password;
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Basic " + Base64.encodeToString(
                        credentials.getBytes(), Base64.NO_WRAP));
                return headers;
            }
        };
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
            params.put("country_id", 1);
        } catch (JSONException e) {
            Log.e("API_TASK_ADD_USER", e.toString());
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
                Log.i("API_FAILED_REGISTRATION", error.toString());
            }
        }
        );
        referenceQueue.add(jsonObjectRequest);

    }

    public void getMatches(User user) {
        RequestQueue queue = Volley.newRequestQueue(fragment.requireContext());
        String url = BASE_URL + "/api/" + user.getUsername() + "/matches";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<GameState> list = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                GameState gameState = new GameState(object);
                                list.add(gameState);
                                Log.i("MATCH_ID", String.valueOf(gameState.getId()));
                            }
                        }
                        catch (JSONException e) {
                            Log.i("MATCH", e.toString());
                            throw new RuntimeException(e);
                        }
                        ((MatchesFragment) fragment).setMatches(list);
                        Log.i("API_MATCHES", list.toString());
                        if (list.size() != 0 ) {
                            Log.i("API_MATCHES", list.get(0).getFigures().toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ((MatchesFragment) fragment).makeToastFailedLoad();
                    }
                }
        ){
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getHeadersBasic();
            }
        };
        queue.add(jsonObjectRequest);
    }

    public void getMatch(long id, User user, boolean isRefresh) {
        RequestQueue queue = Volley.newRequestQueue(fragment.requireContext());
        String url = BASE_URL + "/api/" + id + "/get";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        GameState gameState = new GameState(response);
                        Log.i("API_MATCH_GET", gameState.toString());
                        ((GameFragment) fragment).setCurrentState(gameState, isRefresh);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("API_MATCHES", "BAD CONNECTION");
                    }
                }
        ){
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getHeadersBasic();
            }
        };
        queue.add(jsonObjectRequest);
    }

    public void applyMove(long id, FigureMove figureMove) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(fragment.requireContext());
        String url = BASE_URL + "/api/" + id + "/move";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                figureMove.toJSON(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getHeadersBasic();
            }
        };
        queue.add(jsonObjectRequest);

    }

    public void getFriends(User user) {
        RequestQueue queue = Volley.newRequestQueue(fragment.requireContext());
//        queue.add();
    }

    public void inviteUser(String username) {
        RequestQueue queue = Volley.newRequestQueue(fragment.requireContext());
        String url = BASE_URL + "/api/invite/" + username;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("FRIENDS_API", "Cant' invite user: " + username);
                    }
                }
        ) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                return getHeadersBasic();
            }
        };
        queue.add(jsonObjectRequest);
    }

    private class ErrorListenerImpl implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("AppApiErrorResponse", error.toString());
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
