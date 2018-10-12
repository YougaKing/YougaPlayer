#include <jni.h>
#include <string>
#include "misc.cpp"
#include "alog.cpp"
#include "YougaMediaPlayer.h"

#define JNI_YougaMediaPlayer     "youga/player/YougaMediaPlayer"

static JavaVM *g_jvm;


static YougaMediaPlayer *jni_get_media_player(JNIEnv *env, jobject thiz) {


    return NULL;
}


static void message_loop_n(JNIEnv *env, YougaMediaPlayer *mp) {

}

static int message_loop(void *arg) {
    LOGI("%s\n", __func__);

    JNIEnv *env = NULL;


    YougaMediaPlayer *mp = (YougaMediaPlayer *) arg;

    message_loop_n(env, mp);

    return 0;
}

static void YougaMediaPlayer_native_setup(JNIEnv *env, jobject thiz, jobject weak_this) {
    LOGI("%s\n", __func__);
    YougaMediaPlayer *mp = ijkmp_android_create(message_loop);
}

static void YougaMediaPlayer_setDataSourceAndHeaders(
        JNIEnv *env, jobject thiz, jstring path,
        jobjectArray keys, jobjectArray values) {
    LOGI("%s\n", __func__);
    int retval = 0;
    const char *c_path = NULL;
    YougaMediaPlayer *mp = jni_get_media_player(env, thiz);
}

static void YougaMediaPlayer_native_init(JNIEnv *env) {
    LOGI("%s\n", __func__);
}

// ----------------------------------------------------------------------------

static JNINativeMethod g_nativeMethod[] = {
        {
                "_setDataSource",
                                "(Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)V",
                                                         (void *) YougaMediaPlayer_setDataSourceAndHeaders
        },
        {       "native_setup", "(Ljava/lang/Object;)V", (void *) YougaMediaPlayer_native_setup},
        {       "native_init",  "()V",                   (void *) YougaMediaPlayer_native_init},
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
    env->RegisterNatives(jClass, g_nativeMethod, NELEM(g_nativeMethod));
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