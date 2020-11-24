package com.csd.huelight.ui.mainactivity.lightbulblist;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csd.huelight.R;
import com.csd.huelight.data.LightBulb;
import com.csd.huelight.ui.mainactivity.LightBulbViewModel;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class LightBulbListFragment extends Fragment implements LightBulbClickListener {

    private static final String LOGTAG = LightBulbListFragment.class.getName();
    private LightBulbViewModel lightBulbViewModel;
    private LightBulbRecyclerViewAdapter lightBulbRecyclerViewAdapter;

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

            Configuration configuration = getResources().getConfiguration();
            int screenWidthDp = configuration.screenWidthDp;
//            screenWidthDp -= 16;//padding

            int columns = screenWidthDp / (200);//144 is min width

            recyclerView.setLayoutManager(new GridLayoutManager(context, columns));

            lightBulbViewModel = ViewModelProviders.of(getActivity()).get(LightBulbViewModel.class);

            lightBulbViewModel.init();

            lightBulbViewModel.getLightBulbs().observe(getViewLifecycleOwner(), new Observer<List<LightBulb>>() {
                @Override
                public void onChanged(List<LightBulb> lightBulbs) {
                    lightBulbRecyclerViewAdapter.notifyDataSetChanged();
                }
            });

            lightBulbRecyclerViewAdapter = new LightBulbRecyclerViewAdapter(lightBulbViewModel.getLightBulbs().getValue(), this);

            //TODO een plek maken waar de lampen worden opgeslagen
            recyclerView.setAdapter(lightBulbRecyclerViewAdapter);
        }
        return view;
    }

    private NavController navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onClickPos(int position) {
        //TODO
        Bundle bundle = new Bundle();
        LightBulb lightBulb = lightBulbViewModel.getLightBulbs().getValue().get(position);
        bundle.putSerializable("lightbulb", lightBulb);
        navController.navigate(R.id.action_lightBulbListFragment_to_lightBulbFragment, bundle);
    }

    @Override
    public void onCheckClick(int position) {
        LightBulb lightBulb = lightBulbViewModel.getLightBulbs().getValue().get(position);
        CheckBox checkBox = getActivity().findViewById(R.id.onCB);
        lightBulb.setOn(checkBox.isChecked());
    }
}