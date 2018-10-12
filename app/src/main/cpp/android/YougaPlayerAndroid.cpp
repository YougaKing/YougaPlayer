//
// Created by YougaKing on 2018/10/12.
//

#include "YougaPlayerAndroid.h"

YougaMediaPlayer *yougamp_android_create(int (*msg_loop)(void *)) {
    YougaMediaPlayer *mp = ijkmp_create(msg_loop);
    if (!mp)
        goto fail;

    fail:
    return nullptr;
}

YougaMediaPlayer *ijkmp_create(int (*msg_loop)(void *)) {

    return NULL;
}
