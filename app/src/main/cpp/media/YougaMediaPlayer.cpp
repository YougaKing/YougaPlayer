//
// Created by YougaKing on 2018/10/12.
//
#include <jni.h>
#include "YougaMediaPlayer.h"



jlong YougaMediaPlayer__mNativeMediaPlayer__get__catchAll(JNIEnv *env, jobject thiz) {
    return (*env)->GetLongField(env, thiz, class_YougaMediaPlayer.field_mNativeMediaPlayer);
}
