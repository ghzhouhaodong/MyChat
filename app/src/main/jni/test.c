#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <android/log.h>
#include <malloc.h>
#include "md5.h"

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, "ProjectName", __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, "ProjectName", __VA_ARGS__)


char *join3(char *, char *);

#define MD5_KEY  "appkey=1DLKlkdd&"

JNIEXPORT jstring

JNICALL Java_com_zhd_lenovo_mychat_core_JNICore_getSign
        (JNIEnv *env, jobject job, jstring strText) {

    char *szText = (char *) (*env)->GetStringUTFChars(env, strText, 0);
    char *key = MD5_KEY;

   // char *str =  join3(szText, key);
     char *str = malloc(strlen(szText) + 16);
     sprintf(str, "%s%s",key, szText );
     LOGD("待加密字符串: %s\n", str);

    MD5_CTX context = {0};
    MD5Init(&context);
    MD5Update(&context, str, strlen(str));
    unsigned char dest[16] = {0};
    MD5Final(dest, &context);
    (*env)->ReleaseStringUTFChars(env, strText, str);
    int i = 0;
    char szMd5[32] = {0};
    for (i = 0; i < 16; i++) {
        sprintf(szMd5, "%s%02x", szMd5, dest[i]);
    }

    return (*env)->NewStringUTF(env, szMd5);

//    return (*env)->NewStringUTF(env,"strTextssssssssss");
}

char *join3(char *s1, char *s2) {
    char *result = malloc(strlen(s1) + strlen(s2) + 1);//+1 for the zero-terminator
    //in real code you would check for errors in malloc here
    if (result == NULL) exit(1);

    strcpy(result, s1);
    strcat(result, s2);

    return result;
}
