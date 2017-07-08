LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := core
LOCAL_SRC_FILES :=  md5.h md5.c test.c
LOCAL_LDLIBS += -llog

include $(BUILD_SHARED_LIBRARY)


