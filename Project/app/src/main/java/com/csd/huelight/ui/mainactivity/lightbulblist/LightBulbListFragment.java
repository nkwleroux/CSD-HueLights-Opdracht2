package com.csd.huelight.ui.mainactivity.lightbulblist;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csd.huelight.R;
import com.csd.huelight.ui.mainactivity.LightBulbViewModel;

/**
 * A fragment representing a list of Items.
 */
public class LightBulbListFragment extends Fragment implements LightBulbClickListener {

    private static final String LOGTAG = LightBulbListFragment.class.getName();
    private LightBulbViewModel lightBulbViewModel;
    private LightBulbRecyclerViewAdapter lightBulbRecyclerViewAdapter;
    private NavController navController;

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
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            Configuration configuration = getResources().getConfiguration();
            int screenWidthDp = configuration.screenWidthDp;
//            screenWidthDp -= 16;//padding

            int columns = screenWidthDp / (200);//144 is min width

            recyclerView.setLayoutManager(new GridLayoutManager(context, columns));

            lightBulbViewModel = ViewModelProviders.of(getActivity()).get(LightBulbViewModel.class);

            lightBulbViewModel.init();

            lightBulbViewModel.getLightBulbs().observe(getViewLifecycleOwner(), (lightBulbs) ->
                    lightBulbRecyclerViewAdapter.updateLightBulbs(lightBulbs));

            lightBulbRecyclerViewAdapter = new LightBulbRecyclerViewAdapter(lightBulbViewModel.getLightBulbs().getValue(), this, lightBulbViewModel);

            //TODO een plek maken waar de lampen worden opgeslagen
            recyclerView.setAdapter(lightBulbRecyclerViewAdapter);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        lightBulbViewModel.retrieveLightBulbs();
    }

    @Override
    public void onClickPos(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("lightbulb",position);
        navController.navigate(R.id.action_lightBulbListFragment_to_lightBulbFragment, bundle);
    }
}