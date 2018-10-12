//
// Created by YougaKing on 2018/10/12.
//


#ifndef YOUGAPLAYER_YOUGAMEDIAPLAYER_H
#define YOUGAPLAYER_YOUGAMEDIAPLAYER_H


typedef struct YougaMediaPlayer {
    jclass id;

    jfieldID field_mNativeMediaPlayer;
    jfieldID field_mNativeMediaDataSource;
    jfieldID field_mNativeAndroidIO;
    jmethodID method_postEventFromNative;
    jmethodID method_onSelectCodec;
    jmethodID method_onNativeInvoke;
};

static YougaMediaPlayer class_YougaMediaPlayer;

jlong YougaMediaPlayer__mNativeMediaPlayer__get__catchAll(JNIEnv *env, jobject thiz);

#endif //YOUGAPLAYER_YOUGAMEDIAPLAYER_H
