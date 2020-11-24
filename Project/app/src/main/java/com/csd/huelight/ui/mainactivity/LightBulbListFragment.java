package com.csd.huelight.ui.mainactivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csd.huelight.R;
import com.csd.huelight.data.TempClass;

/**
 * A fragment representing a list of Items.
 */
public class LightBulbListFragment extends Fragment {


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LightBulbListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static LightBulbListFragment newInstance() {
        LightBulbListFragment fragment = new LightBulbListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lightbulb_list_fragment, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
            //TODO berekenen hoeveel kollomen voor gridlayout

            //TODO een plek maken waar de lampen worden opgeslagen
            recyclerView.setAdapter(new LightBulbRecyclerViewAdapter(TempClass.LightBulbs));
        }
        return view;
    }


}