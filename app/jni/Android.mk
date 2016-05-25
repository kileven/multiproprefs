LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := test

LOCAL_C_INCLUDES += $(JNI_H_INCLUDE)
LOCAL_LDLIBS += -llog

LOCAL_CFLAGS += -Wno-psabi
LOCAL_CFLAGS += -D__USE_FILE_OFFSET64
LOCAL_CFLAGS += -D__USE_LARGEFILE64
LOCAL_CFLAGS += -D_FILE_OFFSET_BITS=64
LOCAL_CFLAGS += -D_LARGEFILE64_SOURCE
LOCAL_CFLAGS += -fvisibility=hidden
LOCAL_CFLAGS += -g

#LOCAL_CPPFLAGS := -std=c++11

LOCAL_SRC_FILES := \
Hello.cpp\

include $(BUILD_SHARED_LIBRARY)

