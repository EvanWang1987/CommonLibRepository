package com.github.evan.common_utils_demo.ui.fragment;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.evan.common_utils.ui.fragment.BaseFragment;
import com.github.evan.common_utils.utils.StringUtil;
import com.github.evan.common_utils_demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/1/4.
 */
public class CalculatorFragment extends BaseFragment {

    private int mInputtedWarningSoundID;

    enum CalculateMethod {
        ADDITION("+"), SUBTRACTION("-"), MULTIPLICATION("*"), DIVISION("/"), MEMBRANE("%");

        public String value;

        CalculateMethod(String value) {
            this.value = value;
        }
    }


    @BindView(R.id.txt_result)
    TextView mTxtResult;
    boolean mIsDotCheckedOfLastValue = false;
    boolean mIsDotCheckedOfCurrentValue = false;
    boolean mIsInputtedCalculator = false;
    boolean mIsInputtedEqual = false;
    String mLastValue = "";
    String mCurrentValue = "";
    CalculateMethod mCalculateMethod;
    SoundPool mSoundPool;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calculator, null);
        ButterKnife.bind(this, root);
        mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        mInputtedWarningSoundID = mSoundPool.load(getContext(), R.raw.input_warning, 1);
        return root;
    }

    @Override
    protected void loadData() {

    }

    private String calculate(String lastValue, String currentValue, boolean isDotCheckedOfLastValue, boolean isDotCheckedOfCurrentValue, CalculateMethod calculateMethod) {
        boolean isLastValueEmpty = StringUtil.isEmpty(lastValue);
        boolean isCurrentValueEmpty = StringUtil.isEmpty(currentValue);
        if (isLastValueEmpty || isCurrentValueEmpty) {
            return "0";
        }


        String returnValue = null;
        double result = 0d;
        Double lastFloat = Double.valueOf(lastValue);
        Double currentFloat = Double.valueOf(currentValue);
        switch (calculateMethod) {
            case ADDITION:
                result = lastFloat + currentFloat;
                break;

            case SUBTRACTION:
                result = lastFloat - currentFloat;
                break;

            case MULTIPLICATION:
                result = lastFloat * currentFloat;
                break;

            case DIVISION:
                result = lastFloat / currentFloat;
                break;

            case MEMBRANE:
                result = lastFloat % currentFloat;
                break;
        }

        if (!isDotCheckedOfLastValue && !isDotCheckedOfCurrentValue) {
            returnValue = (long) result + "";
            return returnValue;
        }
        returnValue = result + "";
        return returnValue;
    }

    @OnClick({R.id.card_one_calculator, R.id.card_two_calculator, R.id.card_three_calculator, R.id.card_clear_calculator, R.id.card_four_calculator, R.id.card_five_calculator, R.id.card_six_calculator, R.id.card_seven_calculator, R.id.card_eight_calculator, R.id.card_nine_calculator, R.id.card_zero_calculator, R.id.card_dot_calculator, R.id.card_addition_calculator, R.id.card_subtraction_calculator, R.id.card_multiplication_calculator, R.id.card_membrane_calculator, R.id.card_division_calculator, R.id.card_equal_calculator})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_clear_calculator:
                mLastValue = "";
                mCurrentValue = "";
                mIsInputtedCalculator = false;
                mIsInputtedEqual = false;
                mIsDotCheckedOfLastValue = false;
                mIsDotCheckedOfCurrentValue = false;
                mTxtResult.setText("0");
                mCalculateMethod = null;
                break;

            case R.id.card_addition_calculator:
                if (mIsInputtedCalculator) {
                    String result = calculate(mLastValue, mCurrentValue, mIsDotCheckedOfLastValue, mIsDotCheckedOfCurrentValue, mCalculateMethod);
                    mTxtResult.setText(result);
                    mLastValue = result;
                    mCurrentValue = "";
                    mIsDotCheckedOfLastValue = mLastValue.contains(".");
                    mCalculateMethod = CalculateMethod.ADDITION;
                    mIsInputtedCalculator = true;
                    mIsInputtedEqual = false;
                } else {
                    mCalculateMethod = CalculateMethod.ADDITION;
                    mTxtResult.setText(mLastValue + " " + mCalculateMethod.value);
                    mIsInputtedCalculator = true;
                    mIsInputtedEqual = false;
                }

                break;

            case R.id.card_subtraction_calculator:
                if (mIsInputtedCalculator) {
                    String result = calculate(mLastValue, mCurrentValue, mIsDotCheckedOfLastValue, mIsDotCheckedOfCurrentValue, mCalculateMethod);
                    mTxtResult.setText(result);
                    mLastValue = result;
                    mCurrentValue = "";
                    mIsDotCheckedOfLastValue = mLastValue.contains(".");
                    mCalculateMethod = CalculateMethod.SUBTRACTION;
                    mIsInputtedCalculator = true;
                    mIsInputtedEqual = false;
                } else {
                    mCalculateMethod = CalculateMethod.SUBTRACTION;
                    mTxtResult.setText(mLastValue + " " + mCalculateMethod.value);
                    mIsInputtedCalculator = true;
                    mIsInputtedEqual = false;
                }
                break;

            case R.id.card_multiplication_calculator:
                if (mIsInputtedCalculator) {
                    String result = calculate(mLastValue, mCurrentValue, mIsDotCheckedOfLastValue, mIsDotCheckedOfCurrentValue, mCalculateMethod);
                    mTxtResult.setText(result);
                    mLastValue = result;
                    mCurrentValue = "";
                    mIsDotCheckedOfLastValue = mLastValue.contains(".");
                    mCalculateMethod = CalculateMethod.MULTIPLICATION;
                    mIsInputtedCalculator = true;
                    mIsInputtedEqual = false;
                } else {
                    mCalculateMethod = CalculateMethod.MULTIPLICATION;
                    mTxtResult.setText(mLastValue + " " + mCalculateMethod.value);
                    mIsInputtedCalculator = true;
                    mIsInputtedEqual = false;
                }
                break;

            case R.id.card_division_calculator:
                if (mIsInputtedCalculator) {
                    String result = calculate(mLastValue, mCurrentValue, mIsDotCheckedOfLastValue, mIsDotCheckedOfCurrentValue, mCalculateMethod);
                    mTxtResult.setText(result);
                    mLastValue = result;
                    mCurrentValue = "";
                    mIsDotCheckedOfLastValue = mLastValue.contains(".");
                    mCalculateMethod = CalculateMethod.DIVISION;
                    mIsInputtedCalculator = true;
                    mIsInputtedEqual = false;
                } else {
                    mCalculateMethod = CalculateMethod.DIVISION;
                    mTxtResult.setText(mLastValue + " " + mCalculateMethod.value);
                    mIsInputtedCalculator = true;
                    mIsInputtedEqual = false;
                }
                break;

            case R.id.card_membrane_calculator:
                if (mIsInputtedCalculator) {
                    String result = calculate(mLastValue, mCurrentValue, mIsDotCheckedOfLastValue, mIsDotCheckedOfCurrentValue, mCalculateMethod);
                    mTxtResult.setText(result);
                    mLastValue = result;
                    mCurrentValue = "";
                    mIsDotCheckedOfLastValue = mLastValue.contains(".");
                    mCalculateMethod = CalculateMethod.MEMBRANE;
                    mIsInputtedCalculator = true;
                    mIsInputtedEqual = false;
                } else {
                    mCalculateMethod = CalculateMethod.MEMBRANE;
                    mTxtResult.setText(mLastValue + " " + mCalculateMethod.value);
                    mIsInputtedCalculator = true;
                    mIsInputtedEqual = false;
                }
                break;

            case R.id.card_equal_calculator:
                if (!mIsInputtedCalculator) {
                    if (StringUtil.isEmpty(mLastValue)) {
                        break;
                    } else {
                        mTxtResult.setText(mLastValue);
                        break;
                    }
                }
                String result = calculate(mLastValue, mCurrentValue, mIsDotCheckedOfLastValue, mIsDotCheckedOfCurrentValue, mCalculateMethod);
                mTxtResult.setText(result);
                mLastValue = result;
                mCurrentValue = "";
                mIsDotCheckedOfLastValue = mLastValue.contains(".");
                mIsInputtedCalculator = false;
                mIsInputtedEqual = true;
                break;

            case R.id.card_dot_calculator:
                if (!mIsInputtedCalculator) {
                    if (mIsDotCheckedOfLastValue) {
                        mSoundPool.play(mInputtedWarningSoundID, 1f, 1f, 1, 0, 1f);
                        break;
                    }

                    mLastValue += ".";
                    mTxtResult.setText(mLastValue);
                    mIsDotCheckedOfLastValue = true;
                } else {
                    if (mIsDotCheckedOfCurrentValue) {
                        mSoundPool.play(mInputtedWarningSoundID, 1f, 1f, 1, 0, 1f);
                        break;
                    }

                    mCurrentValue += ".";
                    mTxtResult.setText(mCurrentValue);
                    mIsDotCheckedOfCurrentValue = true;
                }
                break;

            case R.id.card_one_calculator:
                if (mIsInputtedEqual) {
                    mLastValue = 1 + "";
                    mTxtResult.setText(mLastValue);
                    mIsInputtedEqual = false;
                    break;
                }
                if (!mIsInputtedCalculator) {
                    mLastValue += 1;
                    mTxtResult.setText(mLastValue);
                } else {
                    mCurrentValue += 1;
                    mTxtResult.setText(mLastValue + " " + mCalculateMethod.value + " " + mCurrentValue);
                }

                break;

            case R.id.card_two_calculator:
                if (mIsInputtedEqual) {
                    mLastValue = 2 + "";
                    mTxtResult.setText(mLastValue);
                    mIsInputtedEqual = false;
                    break;
                }

                if (!mIsInputtedCalculator) {
                    mLastValue += 2;
                    mTxtResult.setText(mLastValue);
                } else {
                    mCurrentValue += 2;
                    mTxtResult.setText(mLastValue + " " + mCalculateMethod.value + " " + mCurrentValue);
                }
                break;

            case R.id.card_three_calculator:
                if (mIsInputtedEqual) {
                    mLastValue = 3 + "";
                    mTxtResult.setText(mLastValue);
                    mIsInputtedEqual = false;
                    break;
                }

                if (!mIsInputtedCalculator) {
                    mLastValue += 3;
                    mTxtResult.setText(mLastValue);
                } else {
                    mCurrentValue += 3;
                    mTxtResult.setText(mLastValue + " " + mCalculateMethod.value + " " + mCurrentValue);
                }
                break;

            case R.id.card_four_calculator:
                if (mIsInputtedEqual) {
                    mLastValue = 4 + "";
                    mTxtResult.setText(mLastValue);
                    mIsInputtedEqual = false;
                    break;
                }

                if (!mIsInputtedCalculator) {
                    mLastValue += 4;
                    mTxtResult.setText(mLastValue);
                } else {
                    mCurrentValue += 4;
                    mTxtResult.setText(mLastValue + " " + mCalculateMethod.value + " " + mCurrentValue);
                }
                break;

            case R.id.card_five_calculator:
                if (mIsInputtedEqual) {
                    mLastValue = 5 + "";
                    mTxtResult.setText(mLastValue);
                    mIsInputtedEqual = false;
                    break;
                }

                if (!mIsInputtedCalculator) {
                    mLastValue += 5;
                    mTxtResult.setText(mLastValue);
                } else {
                    mCurrentValue += 5;
                    mTxtResult.setText(mLastValue + " " + mCalculateMethod.value + " " + mCurrentValue);
                }
                break;

            case R.id.card_six_calculator:
                if (mIsInputtedEqual) {
                    mLastValue = 6 + "";
                    mTxtResult.setText(mLastValue);
                    mIsInputtedEqual = false;
                    break;
                }

                if (!mIsInputtedCalculator) {
                    mLastValue += 6;
                    mTxtResult.setText(mLastValue);
                } else {
                    mCurrentValue += 6;
                    mTxtResult.setText(mLastValue + " " + mCalculateMethod.value + " " + mCurrentValue);
                }
                break;

            case R.id.card_seven_calculator:
                if (mIsInputtedEqual) {
                    mLastValue = 7 + "";
                    mTxtResult.setText(mLastValue);
                    mIsInputtedEqual = false;
                    break;
                }

                if (!mIsInputtedCalculator) {
                    mLastValue += 7;
                    mTxtResult.setText(mLastValue);
                } else {
                    mCurrentValue += 7;
                    mTxtResult.setText(mLastValue + " " + mCalculateMethod.value + " " + mCurrentValue);
                }
                break;

            case R.id.card_eight_calculator:
                if (mIsInputtedEqual) {
                    mLastValue = 8 + "";
                    mTxtResult.setText(mLastValue);
                    mIsInputtedEqual = false;
                    break;
                }

                if (!mIsInputtedCalculator) {
                    mLastValue += 8;
                    mTxtResult.setText(mLastValue);
                } else {
                    mCurrentValue += 8;
                    mTxtResult.setText(mLastValue + " " + mCalculateMethod.value + " " + mCurrentValue);
                }
                break;

            case R.id.card_nine_calculator:
                if (mIsInputtedEqual) {
                    mLastValue = 9 + "";
                    mTxtResult.setText(mLastValue);
                    mIsInputtedEqual = false;
                    break;
                }

                if (!mIsInputtedCalculator) {
                    mLastValue += 9;
                    mTxtResult.setText(mLastValue);
                } else {
                    mCurrentValue += 9;
                    mTxtResult.setText(mLastValue + " " + mCalculateMethod.value + " " + mCurrentValue);
                }
                break;

            case R.id.card_zero_calculator:
                if (mIsInputtedEqual) {
                    mLastValue = 0 + "";
                    mTxtResult.setText(mLastValue);
                    mIsInputtedEqual = false;
                    break;
                }

                if (!mIsInputtedCalculator) {
                    mLastValue += 0;
                    mTxtResult.setText(mLastValue);
                } else {
                    mCurrentValue += 0;
                    mTxtResult.setText(mLastValue + " " + mCalculateMethod.value + " " + mCurrentValue);
                }
                break;

        }
    }
}
