package com.art4muslim.na7ol.ui.chatFragment;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.ChatsModel;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChatFragment extends Fragment implements ChatsView {

    @BindView(R.id.rvChats)
    RecyclerView rvChats;
    @BindView(R.id.noData)
    View noData;
    @Inject
    ChatsPresenter presenter;
    PrefrencesStorage storage;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((Na7ol) getActivity().getApplication()).getApplicationComponent().inject(this);
        if (Utils.getLang(getActivity()).equals("ar")) {
            Utils.chooseLang(getActivity(), "ar");
        } else if (Utils.getLang(getActivity()).equals("en")) {
            Utils.chooseLang(getActivity(), "en");
        } else {
            Utils.chooseLang(getActivity(), "zh");
        }
        presenter.setView(this);
        storage = new PrefrencesStorage(getActivity());
        presenter.getChats(getActivity(), storage.getId());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getChats(List<ChatsModel.ReturnsEntity> returns) {
        if (returns != null) {
            if (returns.size() > 0) {
                noData.setVisibility(View.GONE);
                rvChats.setAdapter(new ChatsAdapter(getActivity(), returns));
                rvChats.setLayoutManager(new LinearLayoutManager(getActivity()));
            } else {
                noData.setVisibility(View.VISIBLE);
            }

        } else {
            noData.setVisibility(View.VISIBLE);
        }
    }
}
