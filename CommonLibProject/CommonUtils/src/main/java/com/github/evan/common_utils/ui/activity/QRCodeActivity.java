package com.github.evan.common_utils.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.github.evan.common_utils.R;
import com.github.evan.common_utils.manager.threadManager.ThreadManager;
import com.github.evan.common_utils.ui.view.ScannerView;
import com.github.evan.common_utils.utils.CameraConfigurationUtils;
import com.github.evan.common_utils.utils.CameraUtil;
import com.github.evan.common_utils.utils.DensityUtil;
import com.github.evan.common_utils.utils.Logger;
import com.github.evan.common_utils.utils.ToastUtil;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.IOException;
import java.util.concurrent.Future;

/**
 * Created by Evan on 2017/12/17.
 */
public class QRCodeActivity extends BaseActivity implements SurfaceHolder.Callback, Camera.AutoFocusCallback, View.OnClickListener, CompoundButton.OnCheckedChangeListener, Camera.PreviewCallback {
    public static final String KEY_QR_CODE = "key_qr_code";
    public static final int SCAN_SUCCESS = 10;
    public static final int SCAN_FAIL = 11;
    public static final int SCAN_CANCELD = 12;
    public static final int OTHER_PROCESS_USING_CAMERA = 13;
    public static final int AUTO_FOCUS_DELAY = 100;

    private SurfaceView mSurfaceView;
    private Camera mCamera;
    private ScannerView mScannerView;
    private SoundPool mSoundPool;
    private int mAutoFocusSuccessSoundId;
    private CheckBox mToggleFlash;
    private Future<?> mScanFuture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view_qr_code_activity);
        mScannerView = (ScannerView) findViewById(R.id.scanner_view_qr_code_activity);
        mToggleFlash = (CheckBox) findViewById(R.id.toggle_flash);
        mToggleFlash.setOnCheckedChangeListener(this);
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.addCallback(this);
        mAutoFocusSuccessSoundId = mSoundPool.load(this, R.raw.beep, 1);
        mScannerView.setOnClickListener(this);
        try {
            mCamera = CameraUtil.openCamera(CameraUtil.getBackgroundCameraId());
        } catch (RuntimeException e) {
            e.printStackTrace();
            ToastUtil.showToastWithShortDuration(R.string.open_camera_fail);
            setResult(OTHER_PROCESS_USING_CAMERA, null);
            finish();
            return;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(SCAN_CANCELD, null);
        super.onBackPressed();
    }

    @Override
    public void onHandleMessage(Message message) {
        int what = message.what;
        if (what == LOAD_COMPLETE) {
            mSoundPool.play(mAutoFocusSuccessSoundId, 1f, 1f, 1, 0, 1f);
            String qrCode = (String) message.obj;
            Intent intent = new Intent();
            intent.putExtra(KEY_QR_CODE, qrCode);
            setResult(SCAN_SUCCESS, intent);
            finish();
        } else if (what == AUTO_FOCUS_DELAY) {
            if (null != mCamera) {
                mCamera.autoFocus(this);
            }
        } else if (what == LOAD_ERROR) {

        }
    }

    @Override
    protected void onDestroy() {
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            mCamera.autoFocus(null);
            mCamera.release();
        }
        if (mScanFuture != null && !mScanFuture.isDone()) {
            try {
                mScanFuture.cancel(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mSurfaceView.getHolder().removeCallback(this);
        mSoundPool.release();
        super.onDestroy();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_qr_code;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (null != mCamera) {
            try {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                parameters.setSceneMode(Camera.Parameters.SCENE_MODE_BARCODE);
                boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
                mCamera.setDisplayOrientation(isPortrait ? 90 : 0);
                mCamera.autoFocus(this);
                mCamera.setPreviewDisplay(holder);
                mCamera.setPreviewCallback(this);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mSurfaceView.getHolder() == null) {
            return;
        }
        if (null != mCamera) {
            try {
                boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
                mCamera.setDisplayOrientation(isPortrait ? 90 : 0);
                mCamera.autoFocus(null);
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                mCamera.setPreviewDisplay(mSurfaceView.getHolder());
                mCamera.setPreviewCallback(this);
                mCamera.startPreview();
                mCamera.autoFocus(this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (null != mCamera) {
            mCamera.stopPreview();
        }
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        sendEmptyMessageDelay(AUTO_FOCUS_DELAY, 2000);
    }

    @Override
    public void onClick(View v) {
        int clickViewId = v.getId();
        if (clickViewId == R.id.scanner_view_qr_code_activity) {
            mCamera.autoFocus(this);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFlashMode(isChecked ? Camera.Parameters.FLASH_MODE_TORCH : Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if (mScanFuture != null && !mScanFuture.isDone()) {
            return;
        }
        Point screenResolution = DensityUtil.getScreenResolution();
        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        if (!isPortrait) {
            int x = screenResolution.x;
            int y = screenResolution.y;
            screenResolution.set(y, x);
        }
        Point bestPreviewSizeValue = CameraConfigurationUtils.findBestPreviewSizeValue(mCamera.getParameters(), screenResolution);
        QRDecodeRunnable runnable = new QRDecodeRunnable();
        runnable.data = data;
        runnable.dataWidth = bestPreviewSizeValue.x;
        runnable.dataHeight = bestPreviewSizeValue.y;
        mScanFuture = ThreadManager.getInstance().getQrScanThreadPool().submit(runnable);
    }


    private class QRDecodeRunnable implements Runnable {
        public byte[] data;
        public int dataWidth;
        public int dataHeight;

        @Override
        public void run() {
            Result rawResult = null;
            PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(data, dataWidth, dataHeight, 0, 0, dataWidth, dataHeight, true);
            MultiFormatReader multiFormatReader = new MultiFormatReader();
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            try {
                rawResult = multiFormatReader.decodeWithState(bitmap);
            } catch (ReaderException re) {
                // continue
            } finally {
                multiFormatReader.reset();
            }
            if (rawResult != null) {
                String qrCode = rawResult.getText();
                sendMessage(LOAD_COMPLETE, -1, -1, qrCode, null, -1);
            } else {
                sendEmptyMessage(LOAD_ERROR);
            }
        }
    }
}
