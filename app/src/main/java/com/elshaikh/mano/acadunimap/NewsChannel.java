package com.elshaikh.mano.acadunimap;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsChannel.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsChannel#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsChannel extends Fragment {
    private View v;
    private String tit = "";
    private String uri = "";
    private NewsChannel local;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NewsChannel() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsChannel.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsChannel newInstance(String param1, String param2) {
        NewsChannel fragment = new NewsChannel();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_news_channel, container, false);
        tit = getArguments().getString("tit");
        uri = getArguments().getString("url");
        System.out.println("KKKKKKK title: "+tit+" URL:"+uri);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandlers(getActivity()));
        local = this;

        GetRSSDataTask task = new GetRSSDataTask();

        // Start process RSS task
        task.execute(uri);
        return v;
        //return inflater.inflate(R.layout.fragment_news_channel, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(tit);
    }
    private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem> > {
        @Override
        protected List<RssItem> doInBackground(String... urls) {
            try {
                // Create RSS reader
                RssReader rssReader = new RssReader(urls[0]);

                // Parse RSS, get items
                System.out.println("RRRRRRR "+rssReader.getItems().get(0).getTitle());
                return rssReader.getItems();

            } catch (Exception e) {
                System.out.println("ZZZZZZZZZZZ 2222"+urls[0]);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<RssItem> result) {

            // Get a ListView from the RSS Channel view
            ListView itcItems = (ListView) v.findViewById(R.id.liststdn);
            // ListView xx = findViewById(android.R.layout.simple_list_item_1);

            // ArrayAdapter<RssItem> ad = new ArrayAdapter<RssItem>(local,android.R.layout.simple)// Create a list adapter

            WeatherAdapter adap = new WeatherAdapter(local.getContext(),R.layout.item_list_content,result);
            //ArrayAdapter<RssItem> adapter = new ArrayAdapter<RssItem>(local,android.R.layout.simple_list_item_1, result);
            //System.out.println("HHHHHHHHHHH SIZE"+result.size());
            // Set list adapter for the ListView
            itcItems.setAdapter(adap);

            // Set list view item click listener
            itcItems.setOnItemClickListener(new ListListener(result, local.getActivity()));
        }
    }
}
