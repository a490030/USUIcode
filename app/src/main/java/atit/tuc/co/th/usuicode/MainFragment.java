package atit.tuc.co.th.usuicode;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    } // Constructor

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Register Controller
        registerController();

        // Login Controller
        loginController();


    } // Main Method

    private void loginController() {
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userEditText = getView().findViewById(R.id.edtUser);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);
                String userString = userEditText.getText().toString().trim();
                String passwordString = passwordEditText.getText().toString().trim();
                MyConstant myConstant = new MyConstant();

                if (userString.isEmpty() || passwordString.isEmpty()) {
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.normalDialog("Have Space", "Please fill every blank");
                } else {
                    try {
                        GetUserWhereUser getUserWhereUser = new GetUserWhereUser(getActivity());
                        getUserWhereUser.execute(userString, myConstant.getUrlGetUserWhereUser());
                        String jsonString = getUserWhereUser.get();
                        Log.d("29novV1", "json==>" + jsonString);
                        if (jsonString.equals("null")) {
                            MyAlert myAlert = new MyAlert(getActivity());
                            myAlert.normalDialog("User False", "No User "+userString+" in database");
                        } else {
                            JSONArray jsonArray = new JSONArray(jsonString);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            if (passwordString.equals(jsonObject.getString("password"))) {
                                Intent intent = new Intent(getActivity(), ServiceActivity.class); // Intent
                                startActivity(intent);
                                getActivity().finish(); // Finish Activity
                            } else {
                                MyAlert myAlert = new MyAlert(getActivity());
                                myAlert.normalDialog("Password False", "Please try again");
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void registerController() {
        TextView textView = getView().findViewById(R.id.txtRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Replace Flagment
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentMainFragment, new RegisterFragment())
                        .addToBackStack(null).commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    } // Create View

} // Main Class
