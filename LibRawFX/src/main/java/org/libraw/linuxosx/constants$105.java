// Generated by jextract

package org.libraw.linuxosx;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.lang.foreign.*;
import static java.lang.foreign.ValueLayout.*;
final class constants$105 {

    // Suppresses default constructor, ensuring non-instantiability.
    private constants$105() {}
    static final VarHandle const$0 = constants$98.const$1.varHandle(MemoryLayout.PathElement.groupElement("is_Sony"));
    static final VarHandle const$1 = constants$98.const$1.varHandle(MemoryLayout.PathElement.groupElement("is_pana_raw"));
    static final VarHandle const$2 = constants$98.const$1.varHandle(MemoryLayout.PathElement.groupElement("is_PentaxRicohMakernotes"));
    static final VarHandle const$3 = constants$98.const$1.varHandle(MemoryLayout.PathElement.groupElement("raw_stride"));
    static final StructLayout const$4 = MemoryLayout.structLayout(
        MemoryLayout.structLayout(
            RuntimeHelper.POINTER.withName("input"),
            RuntimeHelper.POINTER.withName("output"),
            JAVA_INT.withName("input_internal"),
            MemoryLayout.paddingLayout(4),
            RuntimeHelper.POINTER.withName("meta_data"),
            JAVA_LONG.withName("profile_offset"),
            JAVA_LONG.withName("toffset"),
            MemoryLayout.sequenceLayout(4, JAVA_INT).withName("pana_black")
        ).withName("internal_data"),
        MemoryLayout.structLayout(
            JAVA_INT.withName("mix_green"),
            JAVA_INT.withName("raw_color"),
            JAVA_INT.withName("zero_is_bad"),
            JAVA_SHORT.withName("shrink"),
            JAVA_SHORT.withName("fuji_width")
        ).withName("internal_output_params"),
        MemoryLayout.structLayout(
            RuntimeHelper.POINTER.withName("histogram"),
            RuntimeHelper.POINTER.withName("oprof")
        ).withName("output_data"),
        MemoryLayout.structLayout(
            JAVA_INT.withName("olympus_exif_cfa"),
            MemoryLayout.paddingLayout(4),
            JAVA_LONG.withName("unique_id"),
            JAVA_LONG.withName("OlyID"),
            JAVA_INT.withName("tiff_nifds"),
            JAVA_INT.withName("tiff_flip"),
            JAVA_INT.withName("metadata_blocks"),
            MemoryLayout.paddingLayout(4)
        ).withName("identify_data"),
        MemoryLayout.structLayout(
            JAVA_SHORT.withName("order"),
            MemoryLayout.sequenceLayout(4, JAVA_SHORT).withName("sraw_mul"),
            MemoryLayout.sequenceLayout(3, JAVA_SHORT).withName("cr2_slice"),
            JAVA_INT.withName("kodak_cbpp"),
            MemoryLayout.paddingLayout(4),
            JAVA_LONG.withName("strip_offset"),
            JAVA_LONG.withName("data_offset"),
            JAVA_LONG.withName("meta_offset"),
            JAVA_LONG.withName("exif_offset"),
            JAVA_LONG.withName("ifd0_offset"),
            JAVA_INT.withName("data_size"),
            JAVA_INT.withName("meta_length"),
            JAVA_INT.withName("cr3_exif_length"),
            JAVA_INT.withName("cr3_ifd0_length"),
            JAVA_INT.withName("thumb_misc"),
            JAVA_INT.withName("fuji_layout"),
            JAVA_INT.withName("tiff_samples"),
            JAVA_INT.withName("tiff_bps"),
            JAVA_INT.withName("tiff_compress"),
            JAVA_INT.withName("tiff_sampleformat"),
            JAVA_INT.withName("zero_after_ff"),
            JAVA_INT.withName("tile_width"),
            JAVA_INT.withName("tile_length"),
            JAVA_INT.withName("load_flags"),
            JAVA_INT.withName("data_error"),
            JAVA_INT.withName("hasselblad_parser_flag"),
            JAVA_LONG.withName("posRAFData"),
            JAVA_INT.withName("lenRAFData"),
            JAVA_INT.withName("fuji_total_lines"),
            JAVA_INT.withName("fuji_total_blocks"),
            JAVA_INT.withName("fuji_block_width"),
            JAVA_INT.withName("fuji_bits"),
            JAVA_INT.withName("fuji_raw_type"),
            JAVA_INT.withName("fuji_lossless"),
            JAVA_INT.withName("pana_encoding"),
            JAVA_INT.withName("pana_bpp"),
            MemoryLayout.paddingLayout(4),
            MemoryLayout.sequenceLayout(16, MemoryLayout.structLayout(
                JAVA_INT.withName("version"),
                JAVA_INT.withName("f_width"),
                JAVA_INT.withName("f_height"),
                JAVA_INT.withName("tileWidth"),
                JAVA_INT.withName("tileHeight"),
                JAVA_INT.withName("nBits"),
                JAVA_INT.withName("nPlanes"),
                JAVA_INT.withName("cfaLayout"),
                JAVA_INT.withName("encType"),
                JAVA_INT.withName("imageLevels"),
                JAVA_INT.withName("hasTileCols"),
                JAVA_INT.withName("hasTileRows"),
                JAVA_INT.withName("mdatHdrSize"),
                JAVA_INT.withName("MediaSize"),
                JAVA_LONG.withName("MediaOffset"),
                JAVA_INT.withName("MediaType"),
                MemoryLayout.paddingLayout(4)
            ).withName("crx_data_header_t")).withName("crx_header"),
            JAVA_INT.withName("crx_track_selected"),
            JAVA_SHORT.withName("CR3_CTMDtag"),
            JAVA_SHORT.withName("CR3_Version"),
            JAVA_INT.withName("CM_found"),
            JAVA_INT.withName("is_NikonTransfer"),
            JAVA_INT.withName("is_Olympus"),
            JAVA_INT.withName("OlympusDNG_SubDirOffsetValid"),
            JAVA_INT.withName("is_Sony"),
            JAVA_INT.withName("is_pana_raw"),
            JAVA_INT.withName("is_PentaxRicohMakernotes"),
            MemoryLayout.sequenceLayout(20, JAVA_INT).withName("dng_frames"),
            JAVA_SHORT.withName("raw_stride"),
            MemoryLayout.paddingLayout(2)
        ).withName("unpacker_data")
    ).withName("libraw_internal_data_t");
    static final StructLayout const$5 = MemoryLayout.structLayout(
        MemoryLayout.sequenceLayout(2, RuntimeHelper.POINTER).withName("branch"),
        JAVA_INT.withName("leaf"),
        MemoryLayout.paddingLayout(4)
    ).withName("decode");
}

