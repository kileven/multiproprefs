#adding APP_MODULES lines tells system only compile changed files.
APP_MODULES :=  avcodec avdevice avfilter avformat avresample avutil swresample swscale
APP_MODULES += SDL2 main test
APP_ABI := armeabi-v7a 
APP_PLATFORM := android-9
APP_STL := gnustl_static
