package com.example.wmnl_yo.shoppingplatform.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wmnl_yo.shoppingplatform.R;
import com.example.wmnl_yo.shoppingplatform.activity.MainActivity;
import com.example.wmnl_yo.shoppingplatform.database.GetShoppingMallKind;
import com.example.wmnl_yo.shoppingplatform.database.GetShoppingMallKindsecond;
import com.example.wmnl_yo.shoppingplatform.database.GetShoppingMallSeleteKind;
import com.example.wmnl_yo.shoppingplatform.database.GetShoppingMallSeleteKindsecond;
import com.example.wmnl_yo.shoppingplatform.database.GetShoppingMallSeleteName;
import com.example.wmnl_yo.shoppingplatform.object.ShoppingMallObject;

import java.util.List;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShoppingObjectFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShoppingObjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingObjectFragment extends Fragment implements View.OnTouchListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView mRecyclerView;
    public static MyAdapter rAdapter;
    private OnFragmentInteractionListener mListener;
    private TextView shopping_object_kind,shopping_object_kind_t2,shopping_object_kindsecond_t2;
    public static String db_shopping_object_kind,db_shopping_object_kindsecond,db_shopping_object_search_edittext;
    public static String[] string_shopping_object_kind,string_shopping_object_kindsecond;
    private Button shopping_object_search_button,shopping_object_layout_button;
    private EditText shopping_object_search_edittext;
    private static String type = "list";

    public ShoppingObjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingObjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingObjectFragment newInstance(String param1, String param2) {
        ShoppingObjectFragment fragment = new ShoppingObjectFragment();
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
        Toast.makeText(getActivity(),"請稍後...", Toast.LENGTH_SHORT).show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rAdapter.notifyDataSetChanged();
            }
        }, 300);
        GetShoppingMallKind getShoppingMallKind = new GetShoppingMallKind();
        getShoppingMallKind.execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shopping_object, container, false);
        v.setOnTouchListener(this);
        type = "list";
        mRecyclerView = (RecyclerView) v.findViewById(R.id.sp_object_rv);
        shopping_object_kind_t2 = (TextView) v.findViewById(R.id.shopping_object_kind_t2);
        shopping_object_kindsecond_t2 = (TextView) v.findViewById(R.id.shopping_object_kindsecond_t2);
        shopping_object_search_edittext = (EditText) v.findViewById(R.id.shopping_object_search_edittext);
        shopping_object_search_button = (Button) v.findViewById(R.id.shopping_object_search_button);
        shopping_object_layout_button = (Button) v.findViewById(R.id.shopping_object_layout_button);

        shopping_object_kind_t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberPicker(string_shopping_object_kind);
            }
        });
        shopping_object_kindsecond_t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberPicker2(string_shopping_object_kindsecond);
            }
        });

        shopping_object_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_shopping_object_search_edittext = shopping_object_search_edittext.getText().toString();
                Log.e("55125",db_shopping_object_search_edittext);
                GetShoppingMallSeleteName getShoppingMallSeleteName = new GetShoppingMallSeleteName();
                getShoppingMallSeleteName.execute();
                Toast.makeText(getActivity(),"搜尋"+" "+db_shopping_object_search_edittext+" "+"請稍後...", Toast.LENGTH_SHORT).show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rAdapter.notifyDataSetChanged();
                    }
                }, 300);

            }
        });


        rAdapter = new MyAdapter(ShoppingMallObject.ITEMS);
        LinearLayoutManager layoutManager_list = new LinearLayoutManager(getContext());
        layoutManager_list.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration_list = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager_list.getOrientation());
        mRecyclerView.setLayoutManager(layoutManager_list);
        mRecyclerView.setAdapter(rAdapter);
        mRecyclerView.addItemDecoration(dividerItemDecoration_list);
        shopping_object_layout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("list")){
                    type = "grid";
                    shopping_object_layout_button.setBackgroundDrawable(getResources().getDrawable(R.drawable.list));
                    GridLayoutManager layoutManager_grid = new GridLayoutManager(getContext(),2);
                    layoutManager_grid.setOrientation(GridLayoutManager.VERTICAL);
                    DividerItemDecoration dividerItemDecoration_grid = new DividerItemDecoration(mRecyclerView.getContext(),
                            layoutManager_grid.getOrientation());
                    mRecyclerView.setLayoutManager(layoutManager_grid);
                    mRecyclerView.setAdapter(rAdapter);
                    mRecyclerView.addItemDecoration(dividerItemDecoration_grid);
                    Log.e("55125","grid");
                }else if(type.equals("grid")){
                    type = "list";
                    shopping_object_layout_button.setBackgroundDrawable(getResources().getDrawable(R.drawable.grid));
                    LinearLayoutManager layoutManager_list = new LinearLayoutManager(getContext());
                    layoutManager_list.setOrientation(LinearLayoutManager.VERTICAL);
                    DividerItemDecoration dividerItemDecoration_list = new DividerItemDecoration(mRecyclerView.getContext(),
                            layoutManager_list.getOrientation());
                    mRecyclerView.setLayoutManager(layoutManager_list);
                    mRecyclerView.setAdapter(rAdapter);
                    mRecyclerView.addItemDecoration(dividerItemDecoration_list);
                    Log.e("55125","list");
                }
            }
        });

        return v;
    }

    public void numberPicker(String[] data) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_numberpicker, null);
        Button btnSuccess = (Button) view.findViewById(R.id.btnSuccess);
        final NumberPickerView numberPickerView = (NumberPickerView) view.findViewById(R.id.picker);
        numberPickerView.setDisplayedValues(data);
        numberPickerView.setMaxValue(data.length - 1);
        numberPickerView.setMinValue(0);

        final AlertDialog ad = new AlertDialog.Builder(getContext())
                .setView(view)
                .setCancelable(false).show();

        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();
                shopping_object_kind_t2.setText(numberPickerView.getContentByCurrValue());
                shopping_object_kindsecond_t2.setText("請選擇");
                db_shopping_object_kind = numberPickerView.getContentByCurrValue();
                Log.e("55125",db_shopping_object_kind);

                GetShoppingMallSeleteKind getShoppingMallSeleteKind = new GetShoppingMallSeleteKind();
                getShoppingMallSeleteKind.execute();

                GetShoppingMallKindsecond getShoppingMallKindsecond = new GetShoppingMallKindsecond();
                getShoppingMallKindsecond.execute();

                Toast.makeText(getActivity(),"搜尋"+" "+db_shopping_object_kind+"種類 "+"請稍後...", Toast.LENGTH_SHORT).show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rAdapter.notifyDataSetChanged();
                    }
                }, 300);

            }
        });
    }
    public void numberPicker2(String[] data) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_numberpicker, null);
        Button btnSuccess = (Button) view.findViewById(R.id.btnSuccess);
        final NumberPickerView numberPickerView = (NumberPickerView) view.findViewById(R.id.picker);
        numberPickerView.setDisplayedValues(data);
        numberPickerView.setMaxValue(data.length - 1);
        numberPickerView.setMinValue(0);

        final AlertDialog ad = new AlertDialog.Builder(getContext())
                .setView(view)
                .setCancelable(false).show();

        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();
                shopping_object_kindsecond_t2.setText(numberPickerView.getContentByCurrValue());
                db_shopping_object_kindsecond = numberPickerView.getContentByCurrValue();
                Log.e("55125",db_shopping_object_kindsecond);

                GetShoppingMallSeleteKindsecond getShoppingMallSeleteKindsecond = new GetShoppingMallSeleteKindsecond();
                getShoppingMallSeleteKindsecond.execute();

                Toast.makeText(getActivity(),"搜尋"+" "+db_shopping_object_kindsecond+"種類 "+"請稍後...", Toast.LENGTH_SHORT).show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rAdapter.notifyDataSetChanged();
                    }
                }, 300);

            }
        });
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
    public boolean onTouch(View v, MotionEvent event) {
        return false;
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
        private List<ShoppingMallObject.ShoppingMallObjectItem> mshoppingmallList;
        public class ViewHolder extends RecyclerView.ViewHolder {
            public LinearLayout ll;
            public TextView Shoppingmall_name, Shoppingmall_price, Shoppingmall_amount;
            public ShoppingMallObject.ShoppingMallObjectItem mItem;


            public ViewHolder(View v) {
                super(v);
                ll = (LinearLayout) v.findViewById(R.id.spmall_ll);
                Shoppingmall_name = (TextView) v.findViewById(R.id.shoppingmall_name);
                Shoppingmall_price = (TextView) v.findViewById(R.id.shoppingmall_price);
                Shoppingmall_amount = (TextView) v.findViewById(R.id.shoppingmall_amount);
            }


        }

        public MyAdapter(List<ShoppingMallObject.ShoppingMallObjectItem> shoppingmallList) {
            mshoppingmallList = shoppingmallList;


        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v  = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.custom_recyclerview_shopping_mall, parent, false);
            ViewHolder vh = new ViewHolder(v);
            if(type.equals("list")){
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.custom_recyclerview_shopping_mall, parent, false);
                vh = new ViewHolder(v);
            }else if(type.equals("grid")){
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.custom_recyclerview_shopping_mall_grid, parent, false);
                vh = new ViewHolder(v);
            }

            return vh;
        }



        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("shoppingObjectDetail",mshoppingmallList.get(position));
                    ShoppingObjectDetailFragment fragobj = new ShoppingObjectDetailFragment();
                    fragobj.setArguments(bundle);
                    ((MainActivity) getContext()).replaceFragment(ShoppingObjectDetailFragment.class, fragobj);
                }
            });
            holder.mItem = mshoppingmallList.get(position);
            holder.Shoppingmall_name.setText(mshoppingmallList.get(position).Shoppingmall_name);
            holder.Shoppingmall_price.setText(mshoppingmallList.get(position).Shoppingmall_price);
            if(Integer.valueOf(mshoppingmallList.get(position).Shoppingmall_amount) <= 5)
            {
                holder.Shoppingmall_amount.setTextColor(Color.RED);
                holder.Shoppingmall_amount.setText(mshoppingmallList.get(position).Shoppingmall_amount);
            }else{
                holder.Shoppingmall_amount.setTextColor(Color.BLUE);
                holder.Shoppingmall_amount.setText(mshoppingmallList.get(position).Shoppingmall_amount);
            }
        }

        @Override
        public int getItemCount() {
            return mshoppingmallList.size();
        }
    }
}
