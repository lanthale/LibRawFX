// Generated by jextract

package org.libraw.win;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.CLinker.*;
class constants$8 {

    static final FunctionDescriptor libraw_dcraw_make_mem_image$FUNC = FunctionDescriptor.of(C_POINTER,
        C_POINTER,
        C_POINTER
    );
    static final MethodHandle libraw_dcraw_make_mem_image$MH = RuntimeHelper.downcallHandle(
        libraw_h.LIBRARIES, "libraw_dcraw_make_mem_image",
        "(Ljdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;)Ljdk/incubator/foreign/MemoryAddress;",
        constants$8.libraw_dcraw_make_mem_image$FUNC, false
    );
    static final FunctionDescriptor libraw_dcraw_make_mem_thumb$FUNC = FunctionDescriptor.of(C_POINTER,
        C_POINTER,
        C_POINTER
    );
    static final MethodHandle libraw_dcraw_make_mem_thumb$MH = RuntimeHelper.downcallHandle(
        libraw_h.LIBRARIES, "libraw_dcraw_make_mem_thumb",
        "(Ljdk/incubator/foreign/MemoryAddress;Ljdk/incubator/foreign/MemoryAddress;)Ljdk/incubator/foreign/MemoryAddress;",
        constants$8.libraw_dcraw_make_mem_thumb$FUNC, false
    );
    static final FunctionDescriptor libraw_dcraw_clear_mem$FUNC = FunctionDescriptor.ofVoid(
        C_POINTER
    );
    static final MethodHandle libraw_dcraw_clear_mem$MH = RuntimeHelper.downcallHandle(
        libraw_h.LIBRARIES, "libraw_dcraw_clear_mem",
        "(Ljdk/incubator/foreign/MemoryAddress;)V",
        constants$8.libraw_dcraw_clear_mem$FUNC, false
    );
    static final FunctionDescriptor libraw_set_demosaic$FUNC = FunctionDescriptor.ofVoid(
        C_POINTER,
        C_INT
    );
    static final MethodHandle libraw_set_demosaic$MH = RuntimeHelper.downcallHandle(
        libraw_h.LIBRARIES, "libraw_set_demosaic",
        "(Ljdk/incubator/foreign/MemoryAddress;I)V",
        constants$8.libraw_set_demosaic$FUNC, false
    );
    static final FunctionDescriptor libraw_set_output_color$FUNC = FunctionDescriptor.ofVoid(
        C_POINTER,
        C_INT
    );
    static final MethodHandle libraw_set_output_color$MH = RuntimeHelper.downcallHandle(
        libraw_h.LIBRARIES, "libraw_set_output_color",
        "(Ljdk/incubator/foreign/MemoryAddress;I)V",
        constants$8.libraw_set_output_color$FUNC, false
    );
    static final FunctionDescriptor libraw_set_user_mul$FUNC = FunctionDescriptor.ofVoid(
        C_POINTER,
        C_INT,
        C_FLOAT
    );
    static final MethodHandle libraw_set_user_mul$MH = RuntimeHelper.downcallHandle(
        libraw_h.LIBRARIES, "libraw_set_user_mul",
        "(Ljdk/incubator/foreign/MemoryAddress;IF)V",
        constants$8.libraw_set_user_mul$FUNC, false
    );
}

