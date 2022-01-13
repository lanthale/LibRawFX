# 1. generate conf file
/Library/Java/jdk-17.jdk/Contents/Home/bin/jextract -d . --source -t org.libraw.linuxosx -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include --dump-includes libraw-osx.conf ./libraw.h
# 2. Edit libraw-osx.conf and remove all with '_' functions (OSX/Linux functions)
# 3.generate source code
/Library/Java/jdk-17.jdk/Contents/Home/bin/jextract -d . --source -t org.libraw.linuxosx -I /Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include @libraw-osx.conf ./libraw.h