//
// Created by YougaKing on 2018/10/12.
//
#include <jni.h>
#include <android/log.h>

#ifndef YOUGAPLAYER_MISC_H
#define YOUGAPLAYER_MISC_H

#define GET_ARRAY_LEN(array) {(int) (sizeof(array) / sizeof((array)[0]))}

#define LOG_TAG "Youga_Player"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG ,__VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG ,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,LOG_TAG ,__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG ,__VA_ARGS__)
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,LOG_TAG ,__VA_ARGS__)

char *jstringToChar(JNIEnv *env, jstring jstr);

#endif //YOUGAPLAYER_MISC_H
