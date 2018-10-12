#include <jni.h>
#include <string>
#include "Misc.h"
#include "media/YougaMediaPlayer.h"
#include "android/YougaPlayerAndroid.h"

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

static YougaMediaPlayer *jni_set_media_player(JNIEnv* env, jobject thiz, YougaMediaPlayer *mp) {

    YougaMediaPlayer *old = (YougaMediaPlayer*) (intptr_t) YougaMediaPlayer__mNativeMediaPlayer__get__catchAll(env, thiz);
    if (mp) {
        ijkmp_inc_ref(mp);
    }
    J4AC_IjkMediaPlayer__mNativeMediaPlayer__set__catchAll(env, thiz, (intptr_t) mp);

    pthread_mutex_unlock(&g_clazz.mutex);

    // NOTE: ijkmp_dec_ref may block thread
    if (old != NULL ) {
        ijkmp_dec_ref_p(&old);
    }

    return old;
}

static void YougaMediaPlayer_setVideoSurface(JNIEnv *env, jobject thiz, jobject jsurface) {
    LOGI("%s\n", __func__);
    YougaMediaPlayer *mp = jni_get_media_player(env, thiz);

    return;
}


static void YougaMediaPlayer_setDataSourceAndHeaders(
        JNIEnv *env, jobject thiz, jstring path,
        jobjectArray keys, jobjectArray values) {
    LOGI("%s path=%s\n", __func__, jstringToChar(env, path));
    int retval = 0;
    const char *c_path = NULL;
    YougaMediaPlayer *mp = jni_get_media_player(env, thiz);
}

static void YougaMediaPlayer_native_setup(JNIEnv *env, jobject thiz, jobject weak_this) {
    LOGI("%s\n", __func__);
    YougaMediaPlayer *mp = yougamp_android_create(message_loop);

    jni_set_media_player(env, thiz, mp);
    ijkmp_set_weak_thiz(mp, (*env)->NewGlobalRef(env, weak_this));
    ijkmp_set_inject_opaque(mp, ijkmp_get_weak_thiz(mp));
    ijkmp_set_ijkio_inject_opaque(mp, ijkmp_get_weak_thiz(mp));
    ijkmp_android_set_mediacodec_select_callback(mp, mediacodec_select_callback,
                                                 ijkmp_get_weak_thiz(mp));

    LABEL_RETURN:
    ijkmp_dec_ref_p(&mp);
}

static void YougaMediaPlayer_native_init(JNIEnv *env) {
    LOGI("%s\n", __func__);
}

// ----------------------------------------------------------------------------

static JNINativeMethod g_nativeMethod[] = {
        {"native_init",      "()V",                       (void *) YougaMediaPlayer_native_init},
        {"native_setup",     "(Ljava/lang/Object;)V",     (void *) YougaMediaPlayer_native_setup},
        {
         "_setDataSource",
                             "(Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)V",
                                                          (void *) YougaMediaPlayer_setDataSourceAndHeaders
        },
        {"_setVideoSurface", "(Landroid/view/Surface;)V", (void *) YougaMediaPlayer_setVideoSurface},
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