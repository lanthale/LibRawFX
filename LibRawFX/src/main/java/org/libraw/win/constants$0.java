// Generated by jextract

package org.libraw.win;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.CLinker.*;
class constants$0 {

    static final FunctionDescriptor memory_callback$FUNC = FunctionDescriptor.ofVoid(
        C_POINTER,
        C_POINTER,
        C_POINTER
    );
    static final MethodHandle memory_callback$MH = RuntimeHelper.downcallHandle(
        "(Ljdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;)V",
        constants$0.memory_callback$FUNC, false
    );
    static final FunctionDescriptor exif_parser_callback$FUNC = FunctionDescriptor.ofVoid(
        C_POINTER,
        C_INT,
        C_INT,
        C_INT,
        C_INT,
        C_POINTER,
        C_LONG_LONG
    );
    static final MethodHandle exif_parser_callback$MH = RuntimeHelper.downcallHandle(
        "(Ljdk/incubator/foreign/MemoryAddress;IIIILjdk/incubator/foreign/MemoryAddress;J)V",
        constants$0.exif_parser_callback$FUNC, false
    );
    static final FunctionDescriptor default_memory_callback$FUNC = FunctionDescriptor.ofVoid(
        C_POINTER,
        C_POINTER,
        C_POINTER
    );
    static final MethodHandle default_memory_callback$MH = RuntimeHelper.downcallHandle(
        libraw_h.LIBRARIES, "default_memory_callback",
        "(Ljdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;)V",
        constants$0.default_memory_callback$FUNC, false
    );
    static final FunctionDescriptor data_callback$FUNC = FunctionDescriptor.ofVoid(
        C_POINTER,
        C_POINTER,
        C_INT
    );
}

