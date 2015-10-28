//THIS CODE IS BASED ON THE TANGO MOTIONTRACKING TUTORIAL

/*
 * Copyright 2014 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.projecttango.experiments.javamotiontracking;

import com.google.atap.tangoservice.Tango;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import android.widget.ToggleButton;

/**
 * Application's entry point where the user gets to select a certain configuration and start the
 * next activity.
 */
public class StartActivity extends Activity implements View.OnClickListener {
    public static final String KEY_MOTIONTRACKING_AUTORECOVER = 
            "com.projecttango.experiments.javamotiontracking.useautorecover";
    public static final String KEY_RECORDING =
            "com.prejecttango.experiments.javamotiontracking.record";
    private ToggleButton mAutoResetButton;
    private ToggleButton mRecordButton;
    private Button mStartButton;
    private boolean mUseAutoReset;
    private boolean mRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivityForResult(
                Tango.getRequestPermissionIntent(Tango.PERMISSIONTYPE_MOTION_TRACKING),
                Tango.TANGO_INTENT_ACTIVITYCODE);
        setContentView(R.layout.start);
        this.setTitle("UROS Tango Trajectory Export");
        mAutoResetButton = (ToggleButton) findViewById(R.id.autoresetbutton);
        mStartButton = (Button) findViewById(R.id.startbutton);
        mRecordButton = (ToggleButton) findViewById(R.id.recordbutton);
        mAutoResetButton.setOnClickListener(this);
        mRecordButton.setOnClickListener(this);
        mStartButton.setOnClickListener(this);
        mUseAutoReset = mAutoResetButton.isChecked();
        mRecord = mRecordButton.isChecked();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.startbutton:
            startMotionTracking();
            break;
        case R.id.autoresetbutton:
            mUseAutoReset = mAutoResetButton.isChecked();
            break;
            case R.id.recordbutton:
                mRecord = mRecordButton.isChecked();
                Log.e("BUTTONTEST", "TOGGLE WORKS " + mRecord);
                break;
        }
    }

    private void startMotionTracking() {
        //Intent for Auto Recover
        Intent startmotiontracking = new Intent(this, MotionTrackingActivity.class);
        startmotiontracking.putExtra(KEY_MOTIONTRACKING_AUTORECOVER, mUseAutoReset);
        startmotiontracking.putExtra(KEY_RECORDING, mRecord);
        startActivity(startmotiontracking);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == Tango.TANGO_INTENT_ACTIVITYCODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, R.string.motiontrackingpermission, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
