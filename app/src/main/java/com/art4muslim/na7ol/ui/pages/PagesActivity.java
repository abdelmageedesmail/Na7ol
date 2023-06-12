package com.art4muslim.na7ol.ui.pages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.model.AppDataSocial;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PagesActivity extends AppCompatActivity implements PagesView {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvTitleText)
    TextView tvTitleText;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @Inject
    PagesPresenter presenter;
    @BindView(R.id.ivWhats)
    ImageView ivWhats;
    @BindView(R.id.ivFace)
    ImageView ivFace;
    @BindView(R.id.ivTwitter)
    ImageView ivTwitter;
    @BindView(R.id.ivInsta)
    ImageView ivInsta;
    private AppDataSocial.Return socials;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages);
        ButterKnife.bind(this);
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);
        if (Utils.getLang(this).equals("ar")) {
            Utils.chooseLang(this, "ar");
        } else if (Utils.getLang(this).equals("en")) {
            Utils.chooseLang(this, "en");
        } else {
            Utils.chooseLang(this, "zh");
        }
        presenter.setView(this);
        presenter.getPagesUrls(this);
    }

    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void getPages(String content) {

    }

    @Override
    public void getSocial(AppDataSocial.Return returns) {
        socials = returns;
        if (getIntent().getExtras().getString("from").equals("about")) {
            tvTitle.setText(getString(R.string.aboutNa7ol));
            tvTitleText.setText(getString(R.string.app_name));
            tvContent.setText(Html.fromHtml(returns.getAbout_app()));
        } else {
            tvTitle.setText(getString(R.string.terms));
            tvTitleText.setText(getString(R.string.terms));
            tvContent.setText(Html.fromHtml(returns.getApp_terms()));
        }
    }

    @OnClick({R.id.ivWhats, R.id.ivFace, R.id.ivTwitter, R.id.ivInsta})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivWhats:
                Utils.openWhats(this, socials.getApp_whatsapp());
                break;
            case R.id.ivFace:
                url = socials.getApp_face();
                if (socials.getApp_face().startsWith("http")) {
                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                        url = "http://" + url;
                    openSocial(url);
                } else {
                    Toast.makeText(this, "" + getString(R.string.errorUrl), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ivTwitter:
                url = socials.getApp_twitter();
                if (socials.getApp_twitter().startsWith("http")) {
                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                        url = "http://" + url;
                    openSocial(url);
                } else {
                    Toast.makeText(this, "" + getString(R.string.errorUrl), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ivInsta:
                url = socials.getApp_ins();
                if (socials.getApp_ins().startsWith("http")) {
                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                        url = "http://" + url;
                    openSocial(url);
                } else {
                    Toast.makeText(this, "" + getString(R.string.errorUrl), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void openSocial(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

}