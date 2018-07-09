package com.example.fnagy.grantpermissiontest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.READ_EXTERNAL_STORAGE);

    @Test
    public void grantPermissionTest() {
        int permission = mActivityTestRule.getActivity().checkSelfPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE);

        File file = new File("/storage/emulated/0/test.file");
        Assert.assertEquals(PackageManager.PERMISSION_GRANTED, permission);
        Assert.assertTrue(file.exists());

        try (FileInputStream input = new FileInputStream(file)) {
            int i = 0;
            while (input.read() != -1)
                i++;

            Log.d("TEST", "test file size: " + i);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TEST", "caught exception: " + e.toString());
            Assert.fail();
        }
    }
}
