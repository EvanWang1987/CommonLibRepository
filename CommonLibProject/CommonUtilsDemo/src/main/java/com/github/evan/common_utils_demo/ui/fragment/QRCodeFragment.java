package com.github.evan.common_utils_demo.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.evan.common_utils.manager.threadManager.ThreadManager;
import com.github.evan.common_utils.ui.activity.QRCodeActivity;
import com.github.evan.common_utils.ui.dialog.DialogFactory;
import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.utils.QrCodeUtil;
import com.github.evan.common_utils.utils.StringUtil;
import com.github.evan.common_utils.utils.ToastUtil;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2017/12/13.
 */
public class QRCodeFragment extends BaseFragment {
    public static final int SCAN_QR_CODE_REQUEST_CODE = 101;
    public static final int PICK_UP_PHOTO_FROM_GALLERY = 102;
    public static final int DECODE_QR_CODE_FROM_GALLERY_SUCCESS = 103;
    public static final int DECODE_QR_CODE_FROM_GALLERY_FAIL = 104;
    public static final int GENERATE_QR_BITMAP_SUCCESS = 105;
    public static final int GENERATE_QR_BITMAP_FAIL = 106;

    @BindView(R.id.txt_qr_code_result)
    TextView mTxtQrCodeResult;
    @BindView(R.id.etxt_generate_qr_code)
    EditText mEtxtInputQrCode;
    @BindView(R.id.encoded_qr_bitmap)
    ImageView mImgEncodedQrBitmap;
    AlertDialog mLoadingDialog;
    String mDecodeQrCodeFromBitmap;
    private Bitmap mQrImageFromEncode;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_qr_code, null);
        ButterKnife.bind(this, root);
        return root;
    }


    @Override
    protected void loadData() {

    }

    @Override
    public void onHandleMessage(Message message) {
        int what = message.what;
        if(what == DECODE_QR_CODE_FROM_GALLERY_SUCCESS){
            mTxtQrCodeResult.setText(mDecodeQrCodeFromBitmap);
            mLoadingDialog.dismiss();
        }else if(what == DECODE_QR_CODE_FROM_GALLERY_FAIL){
            ToastUtil.showToastWithShortDuration(R.string.decode_fail);
            mLoadingDialog.dismiss();
        }else if(what == GENERATE_QR_BITMAP_SUCCESS){
            mImgEncodedQrBitmap.setImageBitmap(mQrImageFromEncode);
            mLoadingDialog.dismiss();
        }else if(what == GENERATE_QR_BITMAP_FAIL){
            ToastUtil.showToastWithShortDuration(R.string.encode_fail);
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SCAN_QR_CODE_REQUEST_CODE){
            if(resultCode == QRCodeActivity.SCAN_SUCCESS){
                mTxtQrCodeResult.setText(data.getStringExtra(QRCodeActivity.KEY_QR_CODE));
            }
        }else if(requestCode == PICK_UP_PHOTO_FROM_GALLERY){
            if (resultCode == Activity.RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                final String imagePath = selectedImage.getPath();
                CharSequence title = getString(R.string.warning);
                CharSequence message = getString(R.string.decoding_qr_code);
                if(mLoadingDialog == null){
                    mLoadingDialog = DialogFactory.createDesignMessageDialog(getContext(), -1, title, message);
                    mLoadingDialog.setCancelable(false);
                    mLoadingDialog.setCanceledOnTouchOutside(false);
                }else{
                    mLoadingDialog.setMessage(message);
                }
                mLoadingDialog.show();
                Runnable decodeRunnable = new Runnable() {
                    @Override
                    public void run() {
                        String result = QrCodeUtil.decodeQrCodeFromBitmapPath(imagePath);
                        boolean isSuccess = !StringUtil.isEmpty(result);
                        mDecodeQrCodeFromBitmap = result;
                        sendEmptyMessage(isSuccess ? DECODE_QR_CODE_FROM_GALLERY_SUCCESS : DECODE_QR_CODE_FROM_GALLERY_FAIL);
                    }
                };
                ThreadManager.getInstance().getIOThreadPool().execute(decodeRunnable);
            }
        }
    }

    @OnClick({R.id.card_scan_qr_code_image, R.id.card_scan_qr_code_image_from_gallery, R.id.card_generate_qr_code, R.id.card_generate_qr_code_with_logo})
    void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.card_scan_qr_code_image:
                intent.setClass(getContext(), QRCodeActivity.class);
                startActivityForResult(intent, SCAN_QR_CODE_REQUEST_CODE);
                break;

            case R.id.card_scan_qr_code_image_from_gallery:
                intent.setAction(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent, PICK_UP_PHOTO_FROM_GALLERY);

                break;

            case R.id.card_generate_qr_code:
                final String qrCode = mEtxtInputQrCode.getText().toString().trim();
                if(StringUtil.isEmpty(qrCode)){
                    ToastUtil.showToastWithShortDuration(R.string.please_input_your_qr_code);
                    return;
                }
                String title = getString(R.string.warning);
                String message = getString(R.string.encoding_qr_bitmap);
                if(null == mLoadingDialog){
                    mLoadingDialog = DialogFactory.createDesignMessageDialog(getContext(), -1, title, message);
                    mLoadingDialog.setCancelable(false);
                    mLoadingDialog.setCanceledOnTouchOutside(false);
                }else{
                    mLoadingDialog.setMessage(message);
                }
                mLoadingDialog.show();

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Bitmap qrImage = QrCodeUtil.createQRImage(qrCode, 320, 320);
                        boolean isSuccess = null != qrImage && !qrImage.isRecycled();
                        mQrImageFromEncode = qrImage;
                        sendEmptyMessage(isSuccess ? GENERATE_QR_BITMAP_SUCCESS : GENERATE_QR_BITMAP_FAIL);
                    }
                };

                ThreadManager.getInstance().getIOThreadPool().execute(runnable);

                break;

            case R.id.card_generate_qr_code_with_logo:
                final String qrCode2 = mEtxtInputQrCode.getText().toString().trim();
                if(StringUtil.isEmpty(qrCode2)){
                    ToastUtil.showToastWithShortDuration(R.string.please_input_your_qr_code);
                    return;
                }
                String title2 = getString(R.string.warning);
                String message2 = getString(R.string.encoding_qr_bitmap);
                if(null == mLoadingDialog){
                    mLoadingDialog = DialogFactory.createDesignMessageDialog(getContext(), -1, title2, message2);
                    mLoadingDialog.setCancelable(false);
                    mLoadingDialog.setCanceledOnTouchOutside(false);
                }else{
                    mLoadingDialog.setMessage(message2);
                }
                mLoadingDialog.show();

                Runnable runnable2 = new Runnable() {
                    @Override
                    public void run() {
                        Bitmap qrImage = QrCodeUtil.createQRImage(qrCode2, 320, 320);
                        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                        qrImage = QrCodeUtil.addLogo(qrImage, logo);
                        boolean isSuccess = null != qrImage && !qrImage.isRecycled();
                        mQrImageFromEncode = qrImage;
                        sendEmptyMessage(isSuccess ? GENERATE_QR_BITMAP_SUCCESS : GENERATE_QR_BITMAP_FAIL);
                    }
                };

                ThreadManager.getInstance().getIOThreadPool().execute(runnable2);
                break;
        }
    }
}
