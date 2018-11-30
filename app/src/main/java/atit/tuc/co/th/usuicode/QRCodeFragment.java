package atit.tuc.co.th.usuicode;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeFragment extends Fragment implements ZXingScannerView.ResultHandler { // Alt+Enter

    private ZXingScannerView zXingScannerView;
    private String resultString;



    // Override Alt+Insert
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        zXingScannerView = new ZXingScannerView(getActivity());

        return zXingScannerView;
    } // onCreateView

    // Alt+Insert

    @Override
    public void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    public void handleResult(Result result) {
        resultString = result.toString().trim();
        if (!resultString.isEmpty()) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentServiceFragment, ServiceFragment.serviceInstance(resultString))
                    .commit();

        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                zXingScannerView.resumeCameraPreview(QRCodeFragment.this);
            }
        }, 2000);
    } // handleResult
} // Main Class
