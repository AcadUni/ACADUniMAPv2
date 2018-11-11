package com.elshaikh.mano.acadunimap;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static android.content.ContentValues.TAG;

public class FirstPageActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private String loginurl = Configs.web_base_api+"getLoginresulttest.php";
    public static final String LOGIN_FILE = "login_file";
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        UserLoginTask lt = new UserLoginTask("");
        lt.execute();
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position)
            {
                case 0: return SlideNewsFragment.newInstance(position + 1);
                case 1: return SlideCoverFragment.newInstance(position + 1);
                case 2: return SlideRulesFragment.newInstance(position + 1);
                case 3: return SlideSchoolFragment.newInstance(position + 1);
                case 4: return SlideElearningFragment.newInstance(position + 1);
                case 5: return SlideOMRFragment.newInstance(position + 1);
                case 6: return SlideBusFragment.newInstance(position + 1);
                case 7: return SlideTimetableFragment.newInstance(position + 1);
                case 8: return SlideLocationFragment.newInstance(position + 1);
                case 9: return SlideNewsFragment.newInstance(position + 1);

                default: return SlideNewsFragment.newInstance(position + 1);
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 10;
        }
    }
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
    private void writeToFile(String filename, String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }

    }
    private String readFromFile(String filename) {

        String ret = "";

        try {
            InputStream inputStream = this.openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        }

        return ret;
    }
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        String mType;
        String jsonStr = "";

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
            mType = "staff";
            jsonStr = "";
        }
        UserLoginTask(String password) {
            mType = "student";
            mPassword = password;
            mEmail = "";
            jsonStr = "";
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.


            // Log.e(TAG, "Response from url: " + jsonStr);

            // do login returns JSONObject that contains loginresult and list of active cohorts
            try {
                String urlParameters  = "type=student&pass=111111&email=1111111";
                byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
                int    postDataLength = postData.length;
                URL url = new URL(loginurl);
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                conn.setDoOutput( true );
                conn.setInstanceFollowRedirects( false );
                conn.setRequestMethod( "POST" );
                conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty( "charset", "utf-8");
                conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
                conn.setUseCaches( false );
                try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                    wr.write( postData );
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    jsonStr = convertStreamToString(in);
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    System.out.println(jsonStr);
                    //ver_name = jsonObj.getString("vername").toString();
                    //uniname = jsonObj.getString("uniname").toString();
                    System.out.println("XXXXXXXXX"+jsonStr);
                    //ver_num = Double.parseDouble(jsonObj.getString("vernum").toString());
                    return true;
                }
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
            // Making a request to url and getting response
            //String url = ;
            //jsonStr = sh.makeServiceCall(url);

//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(mEmail)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(mPassword);
//                }
//            }

            // TODO: register the new account here.
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            System.out.println("XXXXXX"+jsonStr);
            if(!jsonStr.equals("")) {
                if (jsonStr != null) {

                    writeToFile(LOGIN_FILE, jsonStr);

                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        // Getting JSON Array node
                        if (jsonObj.has("loginresult")) {
                            //JSONObject lr = jsonObj.getJSONObject("loginresult");
                            String loginresult = jsonObj.getString("loginresult");
                            if (!loginresult.equals("BAD")) {
                                Intent myIntent = new Intent(FirstPageActivity.this,
                                        MainOptions.class);

                                startActivity(myIntent);
                                //finish();
                            } else {
                               // mPasswordView.setError("unknown");
                               // mPasswordView.requestFocus();
                            }
                            //Toast.makeText(getApplicationContext(), uniname+"\n"+"VER: " + ver_name + "." + ver_num, Toast.LENGTH_LONG).show();
                        }


                        // looping through All Contacts

                    } catch (final JSONException e) {
                        Toast.makeText(getApplicationContext(), "Json Parsing loginresult", Toast.LENGTH_LONG).show();


                    }


                } else {
                    Toast.makeText(getApplicationContext(),"Json object Null",Toast.LENGTH_LONG).show();
                }



            }else {
               // showProgress(false);
                //Intent myIntent = new Intent(LoginActivity.this,
                //       MainOptions.class);

                // startActivity(myIntent);
               // mPasswordView.setError(getString(R.string.error_incorrect_password));
                //mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}
