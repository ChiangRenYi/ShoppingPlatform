package com.example.wmnl_yo.shoppingplatform.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wmnl_yo.shoppingplatform.R;
import com.example.wmnl_yo.shoppingplatform.activity.MainActivity;
import com.example.wmnl_yo.shoppingplatform.database.GetAbsentNoteEntryFragmentTeacherResult;
import com.example.wmnl_yo.shoppingplatform.object.AbsentNoteTeacherObject;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AbsentNoteRecordFragmentTeacher.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AbsentNoteRecordFragmentTeacher#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AbsentNoteRecordFragmentTeacher extends Fragment implements View.OnTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    public static MyAdapter abAdapter;
    TextView name;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AbsentNoteRecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AbsentNoteRecordFragmentTeacher newInstance(String param1, String param2) {
        AbsentNoteRecordFragmentTeacher fragment = new AbsentNoteRecordFragmentTeacher();
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
        // Inflate the layout for this fragment
        GetAbsentNoteEntryFragmentTeacherResult getAbsentNoteEntryFragmentTeacherResult = new GetAbsentNoteEntryFragmentTeacherResult();
        getAbsentNoteEntryFragmentTeacherResult.execute();

        View v = inflater.inflate(R.layout.fragment_absent_note_record, container, false);
        v.setOnTouchListener(this);
        name = (TextView)v.findViewById(R.id.absent_name);
        name.setText("教職工編號");
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_ab);

        abAdapter = new MyAdapter(AbsentNoteTeacherObject.ITEMS);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(abAdapter);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
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

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<AbsentNoteTeacherObject.AbsentNoteTeacherObjectItem> mAbsentTeacherList;
        public class ViewHolder extends RecyclerView.ViewHolder {
            public LinearLayout ll;
            public TextView tvAbsentNoteName,tvAbsentNoteDate, tvAbsentNoteType, tvAbsentNoteState;
            public AbsentNoteTeacherObject.AbsentNoteTeacherObjectItem mItem;


            public ViewHolder(View v) {
                super(v);
                ll = (LinearLayout) v.findViewById(R.id.ll_ab);
                tvAbsentNoteName = (TextView) v.findViewById(R.id.tvAbsentNoteName);
                tvAbsentNoteDate = (TextView) v.findViewById(R.id.tvAbsentNoteDate);
                tvAbsentNoteType = (TextView) v.findViewById(R.id.tvAbsentNoteType);
                tvAbsentNoteState = (TextView) v.findViewById(R.id.tvAbsentNoteState);
            }
        }

        public MyAdapter(List<AbsentNoteTeacherObject.AbsentNoteTeacherObjectItem> AbsentTeacherList) {
            mAbsentTeacherList = AbsentTeacherList;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_recyclerview_absentnote_record, parent, false);
            ViewHolder vh = new ViewHolder(v);

            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("absentNoteRecordDetail", mAbsentTeacherList.get(position));
                    AbsentNoteRecordDetailFragmentTeacher fragobj = new AbsentNoteRecordDetailFragmentTeacher();
                    fragobj.setArguments(bundle);
                    ((MainActivity) getContext()).replaceFragment(AbsentNoteRecordDetailFragmentTeacher.class, fragobj);
                }
            });
            holder.mItem = mAbsentTeacherList.get(position);
            holder.tvAbsentNoteName.setText(mAbsentTeacherList.get(position).asName);
            holder.tvAbsentNoteDate.setText(mAbsentTeacherList.get(position).asSdate);
            holder.tvAbsentNoteType.setText(mAbsentTeacherList.get(position).asType);
            holder.tvAbsentNoteState.setText(mAbsentTeacherList.get(position).asAstatus);
        }

        @Override
        public int getItemCount() {
            return mAbsentTeacherList.size();
        }
    }
}