//
// Created by YougaKing on 2018/10/12.
//

#ifndef YOUGAPLAYER_YOUGAPLAYERANDROID_H
#define YOUGAPLAYER_YOUGAPLAYERANDROID_H

#include <jni.h>
#include "../media/YougaMediaPlayer.h"

typedef struct yougamp_android_media_format_context {
    const char *mime_type;
    int         profile;
    int         level;
} yougamp_android_media_format_context;

// ref_count is 1 after open
YougaMediaPlayer *yougamp_android_create(int(*msg_loop)(void *));

#endif //YOUGAPLAYER_YOUGAPLAYERANDROID_H
