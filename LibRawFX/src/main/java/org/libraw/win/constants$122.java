// Generated by jextract

package org.libraw.win;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$122 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$122() {}
    static final MethodHandle const$0 = RuntimeHelper.downcallHandle(
        "libraw_adjust_sizes_info_only",
        constants$3.const$5
    );
    static final MethodHandle const$1 = RuntimeHelper.downcallHandle(
        "libraw_dcraw_ppm_tiff_writer",
        constants$116.const$2
    );
    static final MethodHandle const$2 = RuntimeHelper.downcallHandle(
        "libraw_dcraw_thumb_writer",
        constants$116.const$2
    );
    static final MethodHandle const$3 = RuntimeHelper.downcallHandle(
        "libraw_dcraw_process",
        constants$3.const$5
    );
    static final FunctionDescriptor const$4 = FunctionDescriptor.of(RuntimeHelper.POINTER,
        RuntimeHelper.POINTER,
        RuntimeHelper.POINTER
    );
    static final MethodHandle const$5 = RuntimeHelper.downcallHandle(
        "libraw_dcraw_make_mem_image",
        constants$122.const$4
    );
}

