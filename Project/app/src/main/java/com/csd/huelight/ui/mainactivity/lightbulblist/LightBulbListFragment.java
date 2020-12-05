package com.csd.huelight.ui.mainactivity.lightbulblist;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.csd.huelight.R;
import com.csd.huelight.ui.mainactivity.LightBulbViewModel;
import com.google.android.material.navigation.NavigationView;

/**
 * A fragment representing a list of Items.
 */
public class LightBulbListFragment extends Fragment implements LightBulbClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String LOGTAG = LightBulbListFragment.class.getName();
    private LightBulbViewModel lightBulbViewModel;
    private LightBulbRecyclerViewAdapter lightBulbRecyclerViewAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LightBulbListFragment() {
    }

    // TODO: Customize parameter initialization
    public static LightBulbListFragment newInstance() {
        return new LightBulbListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lightbulb_list_fragment, container, false);

        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);

        Configuration configuration = getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp;
//            screenWidthDp -= 16;//padding

        int columns = screenWidthDp / (200);//200 is min width

        recyclerView.setLayoutManager(new GridLayoutManager(context, columns));
        recyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view;
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.design_default_color_primary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        lightBulbViewModel = ViewModelProviders.of(getActivity()).get(LightBulbViewModel.class);

        lightBulbViewModel.getLightBulbs().observe(getViewLifecycleOwner(), (lightBulbs) -> {
            lightBulbRecyclerViewAdapter.updateLightBulbs(lightBulbs);
        });

        lightBulbViewModel.getCalls().observe(getViewLifecycleOwner(), (calls) -> {
            if (calls == 0) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        lightBulbRecyclerViewAdapter = new LightBulbRecyclerViewAdapter(lightBulbViewModel.getLightBulbs().getValue(), this, lightBulbViewModel);

        //TODO een plek maken waar de lampen worden opgeslagen
        recyclerView.setAdapter(lightBulbRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        lightBulbViewModel.retrieveLightBulbs();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClickPos(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("lightbulb", position);
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        if (navigationView.getCheckedItem() != null) {
            navigationView.getCheckedItem().setChecked(false);
        }
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment_container).navigate(R.id.lightBulbFragment, bundle);

    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        lightBulbViewModel.retrieveLightBulbs();
    }

}