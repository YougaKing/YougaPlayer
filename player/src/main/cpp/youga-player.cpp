//
// Created by YougaKing on 2018/10/22.
//

#include <jni.h>
#include <string>
#include "Misc.h"

#define JNI_YougaMediaPlayer     "youga/com/player/YougaMediaPlayer"

JavaVM *g_jvm;

static void
YougaMediaPlayer_setDataSourceAndHeaders(JNIEnv *env, jobject thiz, jstring path, jobjectArray keys,
                                         jobjectArray values) {
    LOGI("%s path=%s\n", __func__, jstringToChar(env, path));
}

static void YougaMediaPlayer_native_setup(JNIEnv *env, jobject thiz, jobject weak_this) {
    LOGI("%s\n", __func__);

}

static void YougaMediaPlayer_native_init(JNIEnv *env) {
    LOGI("%s\n", __func__);
}

// ----------------------------------------------------------------------------

static JNINativeMethod g_nativeMethod[] = {
        {"native_init",  "()V",                   (void *) YougaMediaPlayer_native_init},
        {"native_setup", "(Ljava/lang/Object;)V", (void *) YougaMediaPlayer_native_setup},
        {
         "_setDataSource",
                         "(Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)V",
                                                  (void *) YougaMediaPlayer_setDataSourceAndHeaders
        }
};


/*
 * 被虚拟机自动调用
 */
jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;

    g_jvm = vm;

    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK)
        return JNI_ERR;

    assert(env != NULL);

    jclass jClass = env->FindClass(JNI_YougaMediaPlayer);
    env->RegisterNatives(jClass, g_nativeMethod, GET_ARRAY_LEN(g_nativeMethod));
    env->DeleteLocalRef(jClass);
    return JNI_VERSION_1_6;
}

void JNI_OnUnload(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    int nJNIVersionOK = vm->GetEnv((void **) &env, JNI_VERSION_1_6);
    jclass jClass = env->FindClass(JNI_YougaMediaPlayer);
    env->UnregisterNatives(jClass);
    env->DeleteLocalRef(jClass);
}