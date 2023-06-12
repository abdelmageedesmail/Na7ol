package com.art4muslim.na7ol.ui.myOrders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.ui.Na7ol;
import com.art4muslim.na7ol.ui.home.HomeActivity;
import com.art4muslim.na7ol.utils.MyAmimatorClass;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.art4muslim.na7ol.utils.ProgressLoading;
import com.art4muslim.na7ol.utils.Config;
import com.art4muslim.na7ol.utils.OnListFragmentInteractionListener;
import com.google.gson.Gson;
import com.myfatoorah.sdk.model.executepayment.MFExecutePaymentRequest;
import com.myfatoorah.sdk.model.executepayment_cardinfo.MFCardInfo;
import com.myfatoorah.sdk.model.executepayment_cardinfo.MFDirectPaymentResponse;
import com.myfatoorah.sdk.model.initiatepayment.MFInitiatePaymentRequest;
import com.myfatoorah.sdk.model.initiatepayment.MFInitiatePaymentResponse;
import com.myfatoorah.sdk.model.initiatepayment.PaymentMethod;
import com.myfatoorah.sdk.model.paymentstatus.MFGetPaymentStatusResponse;
import com.myfatoorah.sdk.model.sendpayment.MFSendPaymentRequest;
import com.myfatoorah.sdk.model.sendpayment.MFSendPaymentResponse;
import com.myfatoorah.sdk.utils.MFAPILanguage;
import com.myfatoorah.sdk.utils.MFCurrencyISO;
import com.myfatoorah.sdk.utils.MFMobileISO;
import com.myfatoorah.sdk.utils.MFNotificationOption;
import com.myfatoorah.sdk.views.MFResult;
import com.myfatoorah.sdk.views.MFSDK;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;


public class TransferToAccountActivity extends AppCompatActivity implements OnListFragmentInteractionListener, PaymentView {

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.btnTransfer)
    Button btnTransfer;
    @BindView(R.id.rvPaymentMethod)
    RecyclerView rvPaymentMethod;
    @BindView(R.id.llDirectPaymentLayout)
    LinearLayout llDirectPaymentLayout;
    @Inject
    PaymentPresenter presenter;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.liPayment)
    LinearLayout liPayment;

    PrefrencesStorage storage;
    private PaymentMethod selectedPaymentMethod = null;
    private OnListFragmentInteractionListener listener = null;
    private ProgressLoading loading;
    private MyItemRecyclerViewAdapter adapter;
    private Integer paymentMethodsId;
    private String supplierCode;
    private String offerId;
    private Integer invoiceId1;
    private String billImage, supplierValue, billPrice, vatAmount;
    private String deliveryPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_to_account);
        ButterKnife.bind(this);
        ((Na7ol) getApplication()).getApplicationComponent().inject(this);
        init();
        getIntentData();
        presenter.setView(this::isAccepted);

        // TODO, don't forget to init the MyFatoorah SDK with the following line
        MFSDK.INSTANCE.init(Config.BASE_URL, Config.TOKEN);

        // You can custom your action bar, but this is optional not required to set this line
        MFSDK.INSTANCE.setUpActionBar(getString(R.string.app_name), R.color.black,
                R.color.white, true);

        listener = this;
        initiatePayment();

    }

    void init() {
        storage = new PrefrencesStorage(this);
        loading = new ProgressLoading(this);
    }

    private void getIntentData() {
        billPrice = getIntent().getExtras().getString("billAmount");
        liPayment.setVisibility(View.VISIBLE);
        tvPrice.setText("" + billPrice + "$");

    }

    @OnClick({R.id.ivBack, R.id.btnTransfer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnTransfer:
//                popUpOrderSent();

                if (selectedPaymentMethod == null) {
                    showAlertDialog(getString(R.string.youMustSelectPaymentMeth));
                    return;
                }

                if (!selectedPaymentMethod.getDirectPayment())
                    executePayment(paymentMethodsId);
                else {
                    executePaymentWithCardInfo(paymentMethodsId);
                    sendPayment();

                }
                break;
        }
    }


    private void initiatePayment() {
        loading.showLoading();

        double invoiceAmount = Double.parseDouble(billPrice);
        MFInitiatePaymentRequest request = new MFInitiatePaymentRequest(
                invoiceAmount, MFCurrencyISO.SAUDI_ARABIA_SAR);

        MFSDK.INSTANCE.initiatePayment(request, MFAPILanguage.AR,
                (MFResult<MFInitiatePaymentResponse> result) -> {
                    if (result instanceof MFResult.Success) {
                        Log.d("response", "Response: " + new Gson().toJson(
                                ((MFResult.Success<MFInitiatePaymentResponse>) result).getResponse()));
                        setAvailablePayments((((MFResult.Success<MFInitiatePaymentResponse>) result)
                                .getResponse().getPaymentMethods()));
                    } else if (result instanceof MFResult.Fail) {
                        Log.d("error", "Error: " + new Gson().toJson(((MFResult.Fail) result).getError()));
                    }
                    loading.cancelLoading();

                    return Unit.INSTANCE;
                });
    }

//    private void executeMyPayment() {
//        AndroidNetworking.initialize(this);
//        AndroidNetworking.post("https://apitest.myfatoorah.com/swagger/docs/v2/SendPayment")
//                .addBodyParameter("CustomerName", "Faza3")
//                .addBodyParameter("CustomerEmail", "test@test.com")
//                .addBodyParameter("CustomerMobile", "12345678")
//                .addBodyParameter("MobileCountryCode", MFMobileISO.SAUDI_ARABIA)
//                .addBodyParameter("SupplierCode", storage.getKey("supplierCode"))
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//
//                    }
//                });
//    }

    private void sendPayment() {

        loading.showLoading();

        double invoiceAmount = Double.parseDouble(billPrice);
        MFSendPaymentRequest request = new MFSendPaymentRequest(invoiceAmount,
                "Customer name", MFNotificationOption.LINK);

        request.setCustomerEmail("test@test.com"); // The email required if you choose MFNotificationOption.ALL or MFNotificationOption.EMAIL
        request.setCustomerMobile("12345678"); // The mobile required if you choose MFNotificationOption.ALL or MFNotificationOption.SMS
        request.setMobileCountryCode(MFMobileISO.SAUDI_ARABIA);


        MFSDK.INSTANCE.sendPayment(request, MFAPILanguage.AR, new Function1<MFResult<MFSendPaymentResponse>, Unit>() {
            @Override
            public Unit invoke(MFResult<MFSendPaymentResponse> result) {
                if (result instanceof MFResult.Success) {
                    Log.d("response", "Response: " + new Gson().toJson(
                            ((MFResult.Success<MFSendPaymentResponse>) result).getResponse()));
                    TransferToAccountActivity.this.showAlertDialog("Invoice created successfully");
                } else if (result instanceof MFResult.Fail) {
                    Log.d("error", "Error: " + new Gson().toJson(((MFResult.Fail) result).getError()));
                    TransferToAccountActivity.this.showAlertDialog("Invoice failed");
                }
                loading.cancelLoading();

                return Unit.INSTANCE;
            }
        });
    }

    private void executePayment(Integer paymentMethod) {

        double invoiceAmount = Double.parseDouble(billPrice);
        MFExecutePaymentRequest request = new MFExecutePaymentRequest(paymentMethod, invoiceAmount);
        request.setDisplayCurrencyIso(MFCurrencyISO.SAUDI_ARABIA_SAR);
        if (supplierCode != null) {
            if (!supplierCode.equals("0")) {
                request.setSupplierCode(Integer.valueOf(supplierCode));
                request.setSupplierValue(Double.parseDouble(supplierValue));
            }
        }

        MFSDK.INSTANCE.executePayment(this, request, MFAPILanguage.AR,
                new Function2<String, MFResult<MFGetPaymentStatusResponse>, Unit>() {
                    @Override
                    public Unit invoke(String invoiceId, MFResult<MFGetPaymentStatusResponse> result) {
                        if (result instanceof MFResult.Success) {
                            Log.d("Response", "Response: " + new Gson().toJson(
                                    ((MFResult.Success<MFGetPaymentStatusResponse>) result).getResponse()));
//                            TransferToAccountActivity.this.showAlertDialog("Payment done successfully");
                            invoiceId1 = ((MFResult.Success<MFGetPaymentStatusResponse>) result).getResponse().getInvoiceId();
                            if (getIntent().getExtras().getString("from").equals("priceOffer")) {
                                presenter.updateAgreementStatus(TransferToAccountActivity.this, storage.getId(), getIntent().getExtras().getString("agrId"), "accepted");
                            }

                            popUpOrderSent();

                        } else if (result instanceof MFResult.Fail) {
                            Log.d("Error", "Error: " + new Gson().toJson(((MFResult.Fail) result).getError()));
                            TransferToAccountActivity.this.showAlertDialog("Payment failed");
                        }
                        Log.d("InvoiceId", "invoiceId: " + invoiceId);

                        loading.cancelLoading();

                        return Unit.INSTANCE;
                    }
                });
    }

    private void executePaymentWithCardInfo(Integer paymentMethod) {
        loading.showLoading();

        double invoiceAmount = Double.parseDouble(billPrice);
        MFExecutePaymentRequest request = new MFExecutePaymentRequest(paymentMethod, invoiceAmount);
        request.setDisplayCurrencyIso(MFCurrencyISO.SAUDI_ARABIA_SAR);


//        MFCardInfo mfCardInfo = new MFCardInfo("Your token here");
        MFCardInfo mfCardInfo = new MFCardInfo("1111111111111111", "02", "25", "123", true);

        MFSDK.INSTANCE.executeDirectPayment(this, request, mfCardInfo, MFAPILanguage.AR,
                (String invoiceId, MFResult<MFDirectPaymentResponse> result) -> {
                    if (result instanceof MFResult.Success) {
                        Log.d("response", "Response: " + new Gson().toJson(
                                ((MFResult.Success<MFDirectPaymentResponse>) result).getResponse()));
                        showAlertDialog("Payment done successfully");
                        if (getIntent().getExtras().getString("from").equals("priceOffer")) {
                            presenter.updateAgreementStatus(TransferToAccountActivity.this, storage.getId(), getIntent().getExtras().getString("agrId"), "accepted");
                        }
                        popUpOrderSent();
                    } else if (result instanceof MFResult.Fail) {
                        Log.d("error", "Error: " + new Gson().toJson(((MFResult.Fail) result).getError()));
                        showAlertDialog("Payment failed");
                    }
                    Log.d("invoiceId", "invoiceId:" + invoiceId);

                    loading.cancelLoading();
                    return Unit.INSTANCE;
                });
    }

//    private void executeRecurringPayment(Integer paymentMethod) {
//        loading.showLoading();
//
//        double invoiceAmount = Double.parseDouble(offerPrice);
//        MFExecutePaymentRequest request = new MFExecutePaymentRequest(paymentMethod, invoiceAmount);
//        request.setDisplayCurrencyIso(MFCurrencyISO.SAUDI_ARABIA_SAR);
//
//        MFCardInfo mfCardInfo = new MFCardInfo(eCardNumber.getText().toString(), etDate.getText().toString(), etDateYear.getText().toString(), etCvv.getText().toString(), true);
//
//        int intervalDays = 5;
//
//        MFSDK.INSTANCE.executeRecurringPayment(request, mfCardInfo, intervalDays, MFAPILanguage.EN,
//                (String invoiceId, MFResult<MFDirectPaymentResponse> result) -> {
//                    if (result instanceof MFResult.Success) {
//
//                        Log.d("response", "Response: " + new Gson().toJson(
//                                ((MFResult.Success<MFDirectPaymentResponse>) result).getResponse()));
//                        showAlertDialog("Payment done successfully");
//                    } else if (result instanceof MFResult.Fail) {
//                        Log.d("error", "Error: " + new Gson().toJson(((MFResult.Fail) result).getError()));
//                        showAlertDialog("Payment failed");
//                    }
//                    Log.d("ivoiceId", "invoiceId:" + invoiceId);
//
//                    loading.cancelLoading();
//
//                    return Unit.INSTANCE;
//                });
//    }
//
//    private void cancelRecurringPayment() {
//        loading.showLoading();
//
//        MFSDK.INSTANCE.cancelRecurringPayment("4WJpq0EmgROY/ynyADYwcA==", MFAPILanguage.EN,
//                (MFResult<Boolean> result) -> {
//                    if (result instanceof MFResult.Success) {
//                        Log.d("response", "Response: " + new Gson().toJson(((MFResult.Success<Boolean>) result).getResponse()));
//                    } else if (result instanceof MFResult.Fail) {
//                        Log.d("error", "Error: " + new Gson().toJson(((MFResult.Fail) result).getError()));
//                    }
//
//                    loading.cancelLoading();
//
//                    return Unit.INSTANCE;
//                });
//    }
//
//    private void cancelToken() {
//        loading.showLoading();
//
//        MFSDK.INSTANCE.cancelToken("dFF2txV3SzqzQpWv9FAd7ILPPgetQ69BIjfVRQPWuIw=", MFAPILanguage.EN,
//                (MFResult<Boolean> result) -> {
//                    if (result instanceof MFResult.Success) {
//                        Log.d("response", "Response: " + new Gson().toJson(((MFResult.Success<Boolean>) result).getResponse()));
//                    } else if (result instanceof MFResult.Fail) {
//                        Log.d("error", "Error: " + new Gson().toJson(((MFResult.Fail) result).getError()));
//                    }
//
//                    loading.cancelLoading();
//
//                    return Unit.INSTANCE;
//                });
//    }

    private void setAvailablePayments(ArrayList<PaymentMethod> paymentMethods) {
        Log.e("paymentMethod", "...." + paymentMethods.size());
        adapter = new MyItemRecyclerViewAdapter(this, paymentMethods, listener);
        rvPaymentMethod.setLayoutManager(new GridLayoutManager(this, 5));
        rvPaymentMethod.setAdapter(adapter);
        adapter.setTransferData(new MyItemRecyclerViewAdapter.GetTransferData() {
            @Override
            public void getPaymentId(PaymentMethod method, int pos) {
                selectedPaymentMethod = method;
                paymentMethodsId = paymentMethods.get(pos).getPaymentMethodId();

            }
        });
    }

    private void showAlertDialog(String text) {
        new AlertDialog.Builder(this)
                .setMessage(text)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    private void popUpOrderSent() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_transfer_sent);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        final Button btnSave = dialog.findViewById(R.id.btnOk);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransferToAccountActivity.this, HomeActivity.class));
            }
        });
        dialog.show();
    }

    @Override
    public void onListFragmentInteraction(int position, PaymentMethod item) {
        selectedPaymentMethod = item;

        if (item.getDirectPayment())
            llDirectPaymentLayout.setVisibility(View.VISIBLE);
        else
            llDirectPaymentLayout.setVisibility(View.GONE);
    }

    @Override
    public void isAccepted(boolean b) {
        if (b) {
            Toast.makeText(this, getString(R.string.priceOfferAccepted), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.errorPleaseTryAgain), Toast.LENGTH_SHORT).show();
        }
    }
}

