// EncryptManager.aidl
package com.flysands.binderexample;
import com.flysands.binderexample.MyMessage;

// Declare any non-default types here with import statements

interface EncryptManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String revert(in MyMessage source);

    String append(in MyMessage source);
}
