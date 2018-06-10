package com.example.klzwii.hit_app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link clas_frag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link clas_frag#newInstance} factory method to
 * create an instance of this fragment.
 */

public class clas_frag extends Fragment implements AdapterView.OnItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mcontext=null;
    private OnFragmentInteractionListener mListener;
    private List<Map<String, String>> list;
    JSONObject json =new JSONObject();
    public clas_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment clas_frag.
     */
    // TODO: Rename and change types and number of parameters
    private ListView listView;
    public static clas_frag newInstance(String param1, String param2) {
        clas_frag fragment = new clas_frag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_clas_frag , container, false);
        listView = (ListView) view.findViewById(R.id.clas_lis);
        list=new ArrayList<Map<String,String>>();
        listView.setAdapter(new list_array(getActivity(), list));
        listView.setOnItemClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        Thread mthread = new clas_re_thread();
        mthread.start();
    }
    public void getData(){
        list=new ArrayList<Map<String,String>>();
        SQLiteOpenHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("clas",null,null,null,null,null,null);
        cursor.moveToFirst();
        Map<String,String>map= new HashMap<>();

        while(!cursor.isAfterLast()){
            map= new HashMap<>();
            String a=cursor.getString(1);
            map.put("info",a);
            list.add(map);
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        dbHelper.close();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext=context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onPause(){
        super.onPause();
        EventBus.getDefault().post(new message_event("stop"));
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(message_event messageEvent) {

        if(messageEvent.getMessage().equals("change")){
            if(messageEvent.getJson()!=json&&messageEvent.getJson()!=null){
                Log.i("asdad","sthhappen");
                json = messageEvent.getJson();
                try{
                    String tots = json.getString("tot");
                    int tot = Integer.parseInt(tots);
                    SQLiteOpenHelper dbHelper = new DatabaseHelper(mcontext);
                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                    db.execSQL("delete from clas");
                    for(int i=1;i<=tot;i++){
                        String a = json.getString("clasn"+i);
                        String bs = json.getString("clas"+i);
                        int b = Integer.parseInt(bs);
                        db.execSQL("insert into clas (id,name) values ('"+b+"','"+a+"')");
                    }
                    db.close();
                    dbHelper.close();
                }catch (JSONException E){
                    E.printStackTrace();
                }
                getData();
                listView.setAdapter(new list_array(getActivity(), list));
                listView.setOnItemClickListener(this);
            }

        }
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
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String asd=(String)((TextView)view.findViewById(R.id.info)).getText();
        Thread a = new get_pas(asd,getContext());
        a.start();
    }
}
