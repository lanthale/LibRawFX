// Generated by jextract

package org.libraw.linuxosx;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$121 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$121() {}
    static final FunctionDescriptor const$0 = FunctionDescriptor.of(RuntimeHelper.POINTER,
        RuntimeHelper.POINTER
    );
    static final MethodHandle const$1 = RuntimeHelper.downcallHandle(
        "libraw_unpack_function_name",
        constants$121.const$0
    );
    static final MethodHandle const$2 = RuntimeHelper.downcallHandle(
        "libraw_get_decoder_info",
        constants$116.const$2
    );
    static final FunctionDescriptor const$3 = FunctionDescriptor.of(JAVA_INT,
        RuntimeHelper.POINTER,
        JAVA_INT,
        JAVA_INT
    );
    static final MethodHandle const$4 = RuntimeHelper.downcallHandle(
        "libraw_COLOR",
        constants$121.const$3
    );
    static final MethodHandle const$5 = RuntimeHelper.downcallHandleVariadic(
        "libraw_capabilities",
        constants$119.const$0
    );
}


