// Generated by jextract

package org.libraw.linuxosx;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.CLinker.*;
class constants$6 {

    static final FunctionDescriptor libraw_set_dataerror_handler$FUNC = FunctionDescriptor.ofVoid(
        C_POINTER,
        C_POINTER,
        C_POINTER
    );
    static final MethodHandle libraw_set_dataerror_handler$MH = RuntimeHelper.downcallHandle(
        libraw_h.LIBRARIES, "libraw_set_dataerror_handler",
        "(Ljdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;)V",
        constants$6.libraw_set_dataerror_handler$FUNC, false
    );
    static final FunctionDescriptor libraw_set_progress_handler$FUNC = FunctionDescriptor.ofVoid(
        C_POINTER,
        C_POINTER,
        C_POINTER
    );
    static final MethodHandle libraw_set_progress_handler$MH = RuntimeHelper.downcallHandle(
        libraw_h.LIBRARIES, "libraw_set_progress_handler",
        "(Ljdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;)V",
        constants$6.libraw_set_progress_handler$FUNC, false
    );
    static final FunctionDescriptor libraw_unpack_function_name$FUNC = FunctionDescriptor.of(C_POINTER,
        C_POINTER
    );
    static final MethodHandle libraw_unpack_function_name$MH = RuntimeHelper.downcallHandle(
        libraw_h.LIBRARIES, "libraw_unpack_function_name",
        "(Ljdk/incubator/foreign/MemoryAddress;)Ljdk/incubator/foreign/MemoryAddress;",
        constants$6.libraw_unpack_function_name$FUNC, false
    );
    static final FunctionDescriptor libraw_get_decoder_info$FUNC = FunctionDescriptor.of(C_INT,
        C_POINTER,
        C_POINTER
    );
    static final MethodHandle libraw_get_decoder_info$MH = RuntimeHelper.downcallHandle(
        libraw_h.LIBRARIES, "libraw_get_decoder_info",
        "(Ljdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;)I",
        constants$6.libraw_get_decoder_info$FUNC, false
    );
    static final FunctionDescriptor libraw_COLOR$FUNC = FunctionDescriptor.of(C_INT,
        C_POINTER,
        C_INT,
        C_INT
    );
    static final MethodHandle libraw_COLOR$MH = RuntimeHelper.downcallHandle(
        libraw_h.LIBRARIES, "libraw_COLOR",
        "(Ljdk/incubator/foreign/MemoryAddress;II)I",
        constants$6.libraw_COLOR$FUNC, false
    );
    static final FunctionDescriptor libraw_capabilities$FUNC = FunctionDescriptor.of(C_INT);
    static final MethodHandle libraw_capabilities$MH = RuntimeHelper.downcallHandle(
        libraw_h.LIBRARIES, "libraw_capabilities",
        "([Ljava/lang/Object;)I",
        constants$6.libraw_capabilities$FUNC, true
    );
}

